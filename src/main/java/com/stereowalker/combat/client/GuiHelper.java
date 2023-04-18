package com.stereowalker.combat.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.client.events.RenderEvent;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.GunItem;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class GuiHelper {
	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "mana_bar_new", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!Combat.CLIENT_CONFIG.use_older_mana_bar && !gui.minecraft.options.hideGui && gui.shouldDrawSurvivalElements())
			{
				gui.setupOverlayRenderState(true, false);
				renderMana(gui, screenWidth, screenHeight, poseStack);
			}
		});
		event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), "better_armor", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!gui.minecraft.options.hideGui && gui.shouldDrawSurvivalElements())
			{
				gui.setupOverlayRenderState(true, false);
				AttributeInstance iattributemaxHealth = gui.getCameraPlayer().getAttribute(Attributes.MAX_HEALTH);
				double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
				int absorption = ModHelper.isMantleLoaded() ? Math.min(Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount()), 20) : Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount());
				int i1 = gui.screenWidth / 2 - 91;
				int k1 = gui.screenHeight - 39;
				renderArmorOverlay(gui, poseStack, maxHealth, absorption, gui.getCameraPlayer(), k1, i1);
			}
		});
		event.registerAboveAll("portal_spin", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!gui.minecraft.options.hideGui)
			{
				gui.setupOverlayRenderState(true, false);
				if (gui.getCameraPlayer().getFeetBlockState().getBlock() == CBlocks.ACROTLEST_RUINED_PORTAL || gui.getCameraPlayer().getFeetBlockState().getBlock() == CBlocks.ACROTLEST_PORTAL) {
					if (!gui.getCameraPlayer().hasEffect(MobEffects.CONFUSION)) {
						float f1 = Mth.lerp(partialTicks, /*gui.getCameraPlayer().oPortalTime*/0, /*gui.getCameraPlayer().portalTime*/1);
						if (f1 > 0.0F) {
							renderPortal(f1);
						}
					}
				}
			}
		});
		event.registerAboveAll("spell_overlay", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!gui.minecraft.options.hideGui)
			{
				gui.setupOverlayRenderState(true, false);
				AttributeInstance iattributemaxHealth = gui.getCameraPlayer().getAttribute(Attributes.MAX_HEALTH);
				double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
				int absorption = ModHelper.isMantleLoaded() ? Math.min(Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount()), 20) : Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount());
				int k1 = gui.screenHeight - 39;
				float f = (float)maxHealth;
				int l1 = absorption;
				int i2 = Mth.ceil((f + (float)l1) / 2.0F / 10.0F);
				int j2 = Math.max(10 - (i2 - 2), 3);
				int k2 = k1 - (i2 - 1) * j2 - 10;
				renderSpellOverlay(gui, poseStack, gui.getCameraPlayer(), k2);
			}
		});
		event.registerAboveAll("gun", (gui, poseStack, partialTicks, screenWidth, screenHeight) -> {
			if (!gui.minecraft.options.hideGui)
			{
				gui.setupOverlayRenderState(true, false);


				if (gui.getCameraPlayer().getMainHandItem().getItem() instanceof GunItem) {
					ItemStack itemStack = gui.getCameraPlayer().getMainHandItem();
					GunItem item = (GunItem)gui.getCameraPlayer().getMainHandItem().getItem();
					Minecraft.getInstance().getProfiler().push("ammo");
					String s = item.getAmmo(itemStack) + "/" + item.getMagazineCapacity();
					int i10 = gui.screenWidth - Minecraft.getInstance().font.width(s);
					if(Minecraft.renderNames() && !Minecraft.getInstance().gameMode.isAlwaysFlying()) {
						Minecraft.getInstance().font.draw(poseStack, s, (float)(i10 - 10), 10, ChatFormatting.YELLOW.getColor());
					}
					Minecraft.getInstance().getProfiler().pop();
				}
			}
		});
		event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "mana_bar_old", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
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
	public static void renderMana(ForgeGui gui, int width, int height, PoseStack pStack)
	{
		push("new_mana");
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
		int top = height - gui.leftHeight;
		gui.leftHeight += (healthRows * rowHeight);
		if (rowHeight != 10) gui.leftHeight += 10 - rowHeight;

		int regen = -1;
		//		if (player.hasEffect(MobEffects.REGENERATION))
		if (player.hasEffect(CMobEffects.MANA_REGENERATION))
		{
			regen = gui.tickCount % Mth.ceil((healthMax/mul) + 5.0F);
		}
		renderStars(gui, pStack, player, left, top, rowHeight, regen, healthMax, health, healthLast, absorb, highlight);

		RenderSystem.disableBlend();
		popPose();
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

	@OnlyIn(Dist.CLIENT)
	public static void renderArmorOverlay(Gui gui, PoseStack matrixStack, double maxHealth, int absorption, Player playerentity, int k1, int i1) {
		float f0 = Combat.isFirstAidLoaded() ? 0.0f : (float)maxHealth;
		int l10 = Combat.isFirstAidLoaded() ? 0 : absorption;
		int i20 = Mth.ceil((f0 + (float)l10) / 2.0F / 10.0F);
		int j20 = Math.max(10 - (i20 - 2), 3);
		int k20 = k1 - (i20 - 1) * j20 - 10;
		int armorY = Combat.isFirstAidLoaded() ? k20-2 : k20;
		renderArmor(gui, matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 20, "betterArmor1", 141, i1, armorY, playerentity);
		renderArmor(gui, matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 40, "betterArmor2", 150, i1, armorY, playerentity);
		renderArmor(gui, matrixStack, playerentity.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue(), 0, "armorToughness", 114, i1, armorY, playerentity);
		renderArmor(gui, matrixStack, CombatEntityStats.getResist(playerentity), 0, "knockbackResistance", 123, i1, armorY, playerentity);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderArmor(Gui gui, PoseStack matrixStack, double attributeValue, int armorSub, String section, int yValue, int i1, int k2, Player playerentity) {
		int j3 = Mth.floor(attributeValue);
		int subbedArmor = j3 - armorSub;
		push(section);
		for(int l3 = 0; l3 < 10; ++l3) {
			if (subbedArmor > 0) {
				int i4 = i1 + l3 * 8;
				if (l3 * 2 + 1 < subbedArmor) {
					gui.blit(matrixStack, i4, k2, 34, yValue, 9, 9);
				}

				if (l3 * 2 + 1 == subbedArmor) {
					gui.blit(matrixStack, i4, k2, 25, yValue, 9, 9);
				}

				if (l3 * 2 + 1 > subbedArmor) {
					gui.blit(matrixStack, i4, k2, 16, yValue, 9, 9);
				}
			}
		}
		popPose();
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderSpellOverlay(Gui gui, PoseStack matrixStack, Player playerentity, int k2) {
		float scale = 0.8F;
		float scale2 = scale-0.39F;
		//Render Gun Ammo Overlay
		int offset = 13;
		int j10 = k2+1 + offset;
		if (!AbstractSpellBookItem.getMainSpellBook(playerentity).isEmpty()) {
			ItemStack itemStack = AbstractSpellBookItem.getMainSpellBook(playerentity);
			AbstractSpellBookItem item = AbstractSpellBookItem.getMainSpellBookItem(playerentity);
			Spell cSpell = item.getCurrentSpell(itemStack);
			Spell nSpell = item.getNextSpell(itemStack);
			Spell pSpell = item.getPreviousSpell(itemStack);
			Component s = cSpell.getName(SpellStats.getSpellKnowledge(playerentity, cSpell));
			Component s1 = nSpell.getName(SpellStats.getSpellKnowledge(playerentity, nSpell));
			Component s2 = pSpell.getName(SpellStats.getSpellKnowledge(playerentity, pSpell));
			int cWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, cSpell).cooldownColmpetion())*60);
			int nWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, nSpell).cooldownColmpetion())*60);
			int pWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, pSpell).cooldownColmpetion())*60);

			if (playerentity.getMainHandItem().getItem() instanceof AbstractMagicCastingItem) {
				int time = playerentity.getUseItem() != playerentity.getMainHandItem() ? 0 : (playerentity.getMainHandItem().getUseDuration() - playerentity.getUseItemRemainingTicks());
				float q = (float)time/(float)cSpell.getCastTime();
				if (CombatEntityStats.getSpellStats(playerentity, cSpell).isPrimed()) {
					q = 1;
				}
				int tWidth = Mth.clamp((int) (q * 60),0,60);
				push("spellLeft");
				int i10 = (int) ((gui.screenWidth/scale) - Minecraft.getInstance().font.width(s));
				int i11 = (int) ((gui.screenWidth/scale2) - Minecraft.getInstance().font.width(s1));
				int i12 = (int) ((gui.screenWidth/scale2) - Minecraft.getInstance().font.width(s2));
				if(Minecraft.renderNames() && !Minecraft.getInstance().gameMode.isAlwaysFlying()) {
					gui.blit(matrixStack, gui.screenWidth - 61, j10 - offset, 60, 49, 60, 30);
					int w = gui.screenWidth-1;
					int h = j10 - offset;
					int r = Mth.floor(q*255)%255;
					Gui.fill(matrixStack, w - cWidth, h + 10, w - (cWidth * -2), h + 10 + 10, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - nWidth, h + 21, w - (nWidth * -2), h + 21 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - pWidth, h + 01, w - (pWidth * -2), h + 01 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - tWidth, h + 10, w - (tWidth * -2), h + 10 + 10, FastColor.ARGB32.color(72, 255 - r, r, 0));

					matrixStack.pushPose();
					matrixStack.scale(scale, scale, scale);
					Minecraft.getInstance().font.draw(matrixStack, s, (float)i10 - (2 / scale) , (float)j10 / scale, cSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					Minecraft.getInstance().font.draw(matrixStack, s1, (float)i11 - (2 / scale2), (float)(j10 + 10) / scale2, nSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					Minecraft.getInstance().font.draw(matrixStack, s2, (float)i12 - (2 / scale2), (float)(j10 - 10) / scale2, pSpell.getCategory().getColor().getValue());
					matrixStack.popPose();
				}
				popPose();
			}

			else if (playerentity.getOffhandItem().getItem() instanceof AbstractMagicCastingItem && !AbstractSpellBookItem.getMainSpellBook(playerentity).isEmpty()) {
				int time = playerentity.getUseItem() != playerentity.getOffhandItem() ? 0 : (playerentity.getOffhandItem().getUseDuration() - playerentity.getUseItemRemainingTicks());
				float q = (float)time/(float)cSpell.getCastTime();
				int tWidth = Mth.clamp((int) (q * 60),0,60);
				push("spellRight");
				if(Minecraft.renderNames() && !Minecraft.getInstance().gameMode.isAlwaysFlying()) {
					gui.blit(matrixStack, 1, j10 - offset, 0, 49, 60, 30);

					int h = j10 - offset;
					int r = Mth.floor(q*255)%255;
					Gui.fill(matrixStack, 1, h + 10, 1 + cWidth, h + 10 + 10, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 21, 1 + nWidth, h + 21 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 01, 1 + pWidth, h + 01 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 10, 1 + tWidth, h + 10 + 10, FastColor.ARGB32.color(72, 255 - r, r, 0));

					matrixStack.pushPose();
					matrixStack.scale(scale, scale, scale);
					Minecraft.getInstance().font.draw(matrixStack, s, 2 / scale, (float)j10 / scale, cSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					Minecraft.getInstance().font.draw(matrixStack, s1, 2 / scale2, (float)(j10 + 10) / scale2, nSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					Minecraft.getInstance().font.draw(matrixStack, s2, 2 / scale2, (float)(j10 - 10) / scale2, pSpell.getCategory().getColor().getValue());
					matrixStack.popPose();
				}
				popPose();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	protected static void renderPortal(float pTimeInPortal) {
		int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		if (pTimeInPortal < 1.0F) {
			pTimeInPortal = pTimeInPortal * pTimeInPortal;
			pTimeInPortal = pTimeInPortal * pTimeInPortal;
			pTimeInPortal = pTimeInPortal * 0.8F + 0.2F;
		}

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, pTimeInPortal);
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(CBlocks.ACROTLEST_RUINED_PORTAL.defaultBlockState());
		float f = textureatlassprite.getU0();
		float f1 = textureatlassprite.getV0();
		float f2 = textureatlassprite.getU1();
		float f3 = textureatlassprite.getV1();
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(0.0D, (double)screenHeight, -90.0D).uv(f, f3).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0D).uv(f2, f3).endVertex();
		bufferbuilder.vertex((double)screenWidth, 0.0D, -90.0D).uv(f2, f1).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
		tesselator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void push(String section) {
		Minecraft.getInstance().getProfiler().push(section);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, RenderEvent.GUI_ICONS);
	}

	public static void popPose() {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getProfiler().pop();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
	}
}
