package com.stereowalker.combat.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.client.events.RenderEvent;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.Util;
import net.minecraft.client.gui.Gui;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

@OnlyIn(Dist.CLIENT)
public class GuiHelper {
	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays() {
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, "Mana Bar New", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!Combat.CLIENT_CONFIG.use_older_mana_bar && !gui.minecraft.options.hideGui && gui.shouldDrawSurvivalElements())
			{
				gui.setupOverlayRenderState(true, false);
				renderMana(gui, screenWidth, screenHeight, poseStack);
			}
		});
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, "Mana Bar Old", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
			if (Combat.CLIENT_CONFIG.use_older_mana_bar) {
				AttributeInstance iattributemaxHealth = gui.getCameraPlayer().getAttribute(Attributes.MAX_HEALTH);
				AttributeInstance iattributemaxMana = gui.getCameraPlayer().getAttribute(CAttributes.MAX_MANA);
				double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
				int absorption = ModHelper.isMantleLoaded() ? Math.min(Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount()), 20) : Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount());
				double totalHealth = maxHealth + absorption;
				boolean needsAir = false;
				int l6 = gui.getCameraPlayer().getAirSupply();
				int j7 = gui.getCameraPlayer().getMaxAirSupply();
				if (gui.getCameraPlayer().isEyeInFluid(FluidTags.WATER) || l6 < j7) {
					int j8 = Mth.ceil((double)(l6 - 2) * 10.0D / (double)j7);
					int l8 = Mth.ceil((double)l6 * 10.0D / (double)j7) - j8;
					
					for(int k5 = 0; k5 < j8 + l8; ++k5) {
						if (k5 < j8) {
							needsAir = true;
						} else {
							needsAir = true;
						}
					}
				}
				int x = gui.screenWidth / 2 - 91;
				int k1 = gui.screenHeight - 39;
				float f = (float)maxHealth;
				int l1 = absorption;
				int i2 = Mth.ceil((f + (float)l1) / 2.0F / 10.0F);
				int j2 = Math.max(10 - (i2 - 2), 3);
				int k2 = k1 - (i2 - 1) * j2 - 10;
				int j3 = gui.getCameraPlayer().getArmorValue();
				RenderEvent.renderManaOverlay(mStack, needsAir, iattributemaxMana, gui.getCameraPlayer(), totalHealth, x, j3, k2);
			}
		});
	}

	protected static int lastMana;
	protected static int displayMana;
	/** The last recorded system time */
	protected static long lastManaTime;
	/** Used with updateCounter to make the heart bar flash */
	protected static long manaBlinkTime;
	public static void renderMana(ForgeIngameGui gui, int width, int height, PoseStack pStack)
	{
		RenderEvent.push("new_mana");
		RenderSystem.enableBlend();
		float mul = 2.0f;
		Player player = (Player)gui.minecraft.getCameraEntity();
//		int health = Mth.ceil(player.getHealth()*mul);
		int health = Mth.ceil(CombatEntityStats.getMana(player)*mul);
		boolean highlight = manaBlinkTime > (long)gui.tickCount && (manaBlinkTime - (long)gui.tickCount) / 3L %2L == 1L;

		if (health < lastMana && player.invulnerableTime > 0)
		{
			lastManaTime = Util.getMillis();
			manaBlinkTime = (long)(gui.tickCount + 20);
		}
		else if (health > lastMana && player.invulnerableTime > 0)
		{
			lastManaTime = Util.getMillis();
			manaBlinkTime = (long)(gui.tickCount + 10);
		}

		if (Util.getMillis() - lastManaTime > 1000L)
		{
			lastMana = health;
			displayMana = health;
			lastManaTime = Util.getMillis();
		}

		lastMana = health;
		int healthLast = displayMana;

//		AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		AttributeInstance attrMaxHealth = player.getAttribute(CAttributes.MAX_MANA);
		float healthMax = Math.max((float)attrMaxHealth.getValue()*mul, Math.max(healthLast, health));
//		int absorb = Mth.ceil(player.getAbsorptionAmount()*mul);
		int absorb = Mth.ceil(0*mul);

		int healthRows = Mth.ceil(((healthMax/mul) + absorb) / 2.0F / 10.0F);
		int rowHeight = Math.max(10 - (healthRows - 2), 3);

		gui.random.setSeed((long)(gui.tickCount * 312871));

		int left = width / 2 - 91;
		int top = height - gui.left_height;
		gui.left_height += (healthRows * rowHeight);
		if (rowHeight != 10) gui.left_height += 10 - rowHeight;

		int regen = -1;
//		if (player.hasEffect(MobEffects.REGENERATION))
		if (player.hasEffect(CMobEffects.MANA_REGENERATION))
		{
			regen = gui.tickCount % Mth.ceil((healthMax/mul) + 5.0F);
		}
		renderStars(gui, pStack, player, left, top, rowHeight, regen, healthMax, health, healthLast, absorb, highlight);

		RenderSystem.disableBlend();
		RenderEvent.popPose();
	}

	protected static void renderStars(Gui gui, PoseStack pStack, Player player, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float manaMax, int mana, int manaLast, int absorb, boolean p_168699_) {
		int mul = 2;
		int i = 105;
		int j = Mth.ceil((double)manaMax / 2.0D);
		int k = Mth.ceil((double)absorb / 2.0D);
		int l = j * 2;

		for(int i1 = (j/mul) + (k/mul) - 1; i1 >= 0; --i1) {
			int j1 = i1 / 10;
			int k1 = i1 % 10;
			int l1 = p_168691_ + k1 * 8;
			int i2 = p_168692_ - j1 * p_168693_;
			if (mana + absorb <= 4) {
				i2 += gui.random.nextInt(2);
			}

			if (i1 < j && i1 == p_168694_) {
				i2 -= 2;
			}
			
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0F);
			//Draws the containers
			gui.blit(pStack, l1, i2, 16, i, 9, 9);
			int j2 = i1 * (2 * mul);
			boolean flag = i1 >= j;
			if (flag) {
				int k2 = j2 - l;
				if (k2 < absorb) {
					boolean flag1 = k2 + 1 == absorb;
//					this.renderHeart(pStack, gui$hearttype == Gui.HeartType.WITHERED ? gui$hearttype : Gui.HeartType.ABSORBING, l1, i2, i, false, flag1);
				}
			}
			
			SpellCategory elementalAffinity = SpellCategory.getStrongestElementalAffinity(player);
			if (i1%2 == 0)
				RenderSystem.setShaderColor(CombatEntityStats.getPrimevalAffinity(player).getrCOlor(), CombatEntityStats.getPrimevalAffinity(player).getgCOlor(), CombatEntityStats.getPrimevalAffinity(player).getbCOlor(), 1.0F);
			if ((i1+1)%2 == 0)
				RenderSystem.setShaderColor(elementalAffinity.getrCOlor(), elementalAffinity.getgCOlor(), elementalAffinity.getbCOlor(), 1.0F);
			if (p_168699_ && j2 < manaLast) {
				boolean flag3_1 = j2 + 1 == manaLast;
				boolean flag3_2 = j2 + 2 == manaLast;
				boolean flag3_3 = j2 + 3 == manaLast;
				if (flag3_1)
					gui.blit(pStack, l1, i2, 52+36, i, 9, 9);
				else if (flag3_2)
					gui.blit(pStack, l1, i2, 43+36, i, 9, 9);
				else if (flag3_3)
					gui.blit(pStack, l1, i2, 34+36, i, 9, 9);
				else
					gui.blit(pStack, l1, i2, 25+36, i, 9, 9);
			}

			if (j2 < mana) {
				boolean flag3_1 = j2 + 1 == mana;
				boolean flag3_2 = j2 + 2 == mana;
				boolean flag3_3 = j2 + 3 == mana;
				if (flag3_1)
					gui.blit(pStack, l1, i2, 52, i, 9, 9);
				else if (flag3_2)
					gui.blit(pStack, l1, i2, 43, i, 9, 9);
				else if (flag3_3)
					gui.blit(pStack, l1, i2, 34, i, 9, 9);
				else
					gui.blit(pStack, l1, i2, 25, i, 9, 9);
			}
		}

	}
}
