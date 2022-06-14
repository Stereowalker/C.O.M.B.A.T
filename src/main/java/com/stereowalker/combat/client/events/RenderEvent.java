package com.stereowalker.combat.client.events;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.compat.SurviveCompat;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.GunItem;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.combat.world.item.ReinforcedCrossbowItem;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class RenderEvent {
	public static final ResourceLocation GUI_ICONS = Combat.getInstance().location("textures/gui/icons.png");
	public static final ResourceLocation FROSTBITE_OVERLAY = Combat.getInstance().location("textures/misc/frostbite_overlay.png");

	static Minecraft mc = Minecraft.getInstance();
	static Gui gui() {
		return mc.gui;
	}

	static long healthUpdateCounter;
	static int playerHealth;
	static long lastSystemTime;
	static int lastPlayerHealth;

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void renderLayer(RenderLivingEvent<?, ?> event) {
		if (event.getEntity() instanceof LocalPlayer) {
			LocalPlayer clientPlayer = (LocalPlayer) event.getEntity();
			ItemStack itemstack = clientPlayer.getMainHandItem();
			ItemStack itemstack1 = clientPlayer.getOffhandItem();
			if ((itemstack.getItem() instanceof ReinforcedCrossbowItem ||itemstack.getItem() instanceof GunItem) 
					|| (itemstack1.getItem() instanceof ReinforcedCrossbowItem ||itemstack1.getItem() instanceof GunItem)) {
				PlayerRenderer playerRenderer = (PlayerRenderer) event.getRenderer();
				PlayerModel<AbstractClientPlayer> playermodel = playerRenderer.getModel();
				HumanoidModel.ArmPose bipedmodel$armpose = getArmPose(clientPlayer, itemstack, itemstack1, InteractionHand.MAIN_HAND);
				HumanoidModel.ArmPose bipedmodel$armpose1 = getArmPose(clientPlayer, itemstack, itemstack1, InteractionHand.OFF_HAND);
				if (clientPlayer.getMainArm() == HumanoidArm.RIGHT) {
					if (!bipedmodel$armpose.equals(HumanoidModel.ArmPose.EMPTY) && !bipedmodel$armpose.equals(HumanoidModel.ArmPose.ITEM)) playermodel.rightArmPose = bipedmodel$armpose;
					if (!bipedmodel$armpose1.equals(HumanoidModel.ArmPose.EMPTY) && !bipedmodel$armpose1.equals(HumanoidModel.ArmPose.ITEM)) playermodel.leftArmPose = bipedmodel$armpose1;
				} else {
					if (!bipedmodel$armpose1.equals(HumanoidModel.ArmPose.EMPTY) && !bipedmodel$armpose1.equals(HumanoidModel.ArmPose.ITEM)) playermodel.rightArmPose = bipedmodel$armpose1;
					if (!bipedmodel$armpose.equals(HumanoidModel.ArmPose.EMPTY) && !bipedmodel$armpose.equals(HumanoidModel.ArmPose.ITEM)) playermodel.leftArmPose = bipedmodel$armpose;
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer player, ItemStack itemStackMain, ItemStack itemStackOff, InteractionHand handIn) {
		HumanoidModel.ArmPose bipedmodel$armpose = HumanoidModel.ArmPose.EMPTY;
		ItemStack itemstack = handIn == InteractionHand.MAIN_HAND ? itemStackMain : itemStackOff;
		if (!itemstack.isEmpty()) {
			bipedmodel$armpose = HumanoidModel.ArmPose.ITEM;
			if (itemstack.getItem() instanceof GunItem && !player.isSprinting()) {
				bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
			}
			else {
				boolean flag3 = itemStackMain.getItem() instanceof ReinforcedCrossbowItem;
				boolean flag = CrossbowItem.isCharged(itemStackMain);
				boolean flag1 = itemStackOff.getItem() instanceof ReinforcedCrossbowItem;
				boolean flag2 = CrossbowItem.isCharged(itemStackOff);
				if (flag3 && flag) {
					bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
				}

				if (flag1 && flag2 && itemStackMain.getItem().getUseAnimation(itemStackMain) == UseAnim.NONE) {
					bipedmodel$armpose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
				}
			}
		}

		return bipedmodel$armpose;
	}

	//	@OnlyIn(Dist.CLIENT)
	//	@SubscribeEvent
	//	public static void addLayers(RenderLivingEvent<?, ?> event) {
	//		//	for(EntityRenderer<? extends Entity> ent : Minecraft.getInstance().getRenderManager().renderers.values()) {
	//		if(event.getRenderer() instanceof LivingRenderer) {
	//			//			(event.getRenderer()).addLayer(new ArrowLayer(event.getRenderer()));
	//		}
	//		//	}
	//	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void swordBlock(RenderHandEvent event) {
		LocalPlayer player = Minecraft.getInstance().player;
		ItemStack stack = event.getItemStack();
		boolean flag = event.getHand() == InteractionHand.MAIN_HAND;
		HumanoidArm handside = flag ? player.getMainArm() : player.getMainArm().getOpposite();
		if (Combat.BATTLE_CONFIG.swordBlocking && ItemFilters.BLOCKABLE_WEAPONS.test(stack)) {
			if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == event.getHand()) {
				//				event.getMatrixStack().pushPose();
				boolean flag3 = handside == HumanoidArm.RIGHT;
				transformBlockFirstPerson(event.getPoseStack(), event.getPartialTicks(), handside, stack);
				transformSideFirstPerson(event.getPoseStack(), handside, event.getEquipProgress());
				Minecraft.getInstance().getItemInHandRenderer().renderItem(player, stack, flag3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
				event.setCanceled(false);
				//				event.getMatrixStack().popPose();
			}
		}
	}

	private static void transformBlockFirstPerson(PoseStack matrixStackIn, float partialTicks, HumanoidArm handIn, ItemStack stack) {
		int i = handIn == HumanoidArm.RIGHT ? 1 : -1;
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(70 * i));
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90 * i));
		matrixStackIn.translate(0.0d, -0.1d * i, 0.5d);
	}

	private static void transformSideFirstPerson(PoseStack matrixStackIn, HumanoidArm handIn, float equippedProg) {
		int i = handIn == HumanoidArm.RIGHT ? 1 : -1;
		matrixStackIn.translate((double)((float)i * 0.56F), (double)(-0.52F + equippedProg * -0.6F), (double)-0.72F);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void renderHUD(RenderGameOverlayEvent.Post event) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		Random rand = new Random();
		//Render Mana Bar
		Player playerentity = gui().getCameraPlayer();
		LivingEntity living;
		if (playerentity != null) {
			Entity entity = playerentity.getVehicle();
			if (entity == null) {
				living = null;
			}

			if (entity instanceof LivingEntity) {
				living = (LivingEntity)entity;
			}
		}

		living = null;
		if (playerentity != null) {
			AttributeInstance iattributemaxHealth = playerentity.getAttribute(Attributes.MAX_HEALTH);
			AttributeInstance iattributemaxMana = playerentity.getAttribute(CAttributes.MAX_MANA);
			double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
			int absorption = ModHelper.isMantleLoaded() ? Math.min(Mth.ceil(playerentity.getAbsorptionAmount()), 20) : Mth.ceil(playerentity.getAbsorptionAmount());
			double totalHealth = maxHealth + absorption;
			int i1 = gui().screenWidth / 2 - 91;
			int j1 = gui().screenWidth / 2 + 91;
			int k1 = gui().screenHeight - 39;
			float f = (float)maxHealth;
			int l1 = absorption;
			int i2 = Mth.ceil((f + (float)l1) / 2.0F / 10.0F);
			int j2 = Math.max(10 - (i2 - 2), 3);
			int k2 = k1 - (i2 - 1) * j2 - 10;
			int j3 = playerentity.getArmorValue();
			if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
				renderArmorOverlay(event.getMatrixStack(), maxHealth, absorption, playerentity, k1, i1);
			}
			if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
				renderSaturationOverlay(event.getMatrixStack(), living, playerentity, j1, k1, rand);
			}
			boolean needsAir = false;
			int l6 = playerentity.getAirSupply();
			int j7 = playerentity.getMaxAirSupply();
			if (playerentity.isEyeInFluid(FluidTags.WATER) || l6 < j7) {
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

			int x = gui().screenWidth / 2 - 91;
			if (event.getType() == RenderGameOverlayEvent.ElementType.ALL || event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
				renderManaOverlay(event.getMatrixStack(), needsAir, iattributemaxMana, playerentity, totalHealth, x, j3, k2);
			}

			if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
				renderSpellOverlay(event.getMatrixStack(), playerentity, k2);
			}
			if (playerentity.getMainHandItem().getItem() instanceof GunItem) {
				ItemStack itemStack = playerentity.getMainHandItem();
				GunItem item = (GunItem)playerentity.getMainHandItem().getItem();
				if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
					mc.getProfiler().push("ammo");
					String s = item.getAmmo(itemStack) + "/" + item.getMagazineCapacity();
					int i10 = gui().screenWidth - mc.font.width(s);
					if(Minecraft.renderNames() && !mc.gameMode.isAlwaysFlying()) {
						mc.font.draw(event.getMatrixStack(), s, (float)(i10 - 10), 10, ChatFormatting.YELLOW.getColor());
					}
					mc.getProfiler().pop();
				}
			}

			//Render Acrotlest Portal Overlay
			if (playerentity.getFeetBlockState().getBlock() == CBlocks.ACROTLEST_RUINED_PORTAL || playerentity.getFeetBlockState().getBlock() == CBlocks.ACROTLEST_PORTAL) {
				if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
					if (!mc.player.hasEffect(MobEffects.CONFUSION)) {
						float f1 = Mth.lerp(event.getPartialTicks(), mc.player.oPortalTime, mc.player.portalTime);
						if (f1 > 0.0F) {
							renderPortal(f1);
						}
					}
				}
			}
			//RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			//RenderSystem.enableBlend();
			//RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			//RenderSystem.disableAlphaTest();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderArmorOverlay(PoseStack matrixStack, double maxHealth, int absorption, Player playerentity, int k1, int i1) {
		float f0 = Combat.isFirstAidLoaded() ? 0.0f : (float)maxHealth;
		int l10 = Combat.isFirstAidLoaded() ? 0 : absorption;
		int i20 = Mth.ceil((f0 + (float)l10) / 2.0F / 10.0F);
		int j20 = Math.max(10 - (i20 - 2), 3);
		int k20 = k1 - (i20 - 1) * j20 - 10;
		int armorY = Combat.isFirstAidLoaded() ? k20-2 : k20;
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 20, "betterArmor1", 141, i1, armorY, playerentity);
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 40, "betterArmor2", 150, i1, armorY, playerentity);
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue(), 0, "armorToughness", 114, i1, armorY, playerentity);
		renderArmor(matrixStack, CombatEntityStats.getResist(playerentity), 0, "knockbackResistance", 123, i1, armorY, playerentity);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderSaturationOverlay(PoseStack matrixStack, LivingEntity living, Player playerentity, int j1, int k1, Random rand) {
		LivingEntity livingentity = living;
		int l = (int) (playerentity.getFoodData().getSaturationLevel());
		int j6 = func_212306_a(livingentity);
		if (j6 == 0) {
			push("saturation");
			for(int k6 = 0; k6 < 10; ++k6) {
				int i7 = k1;
				int k7 = 16;
				int i8 = 0;
				if (playerentity.getFoodData().getSaturationLevel() <= 0.0F && gui().getGuiTicks() % (l * 3 + 1) == 0) {
					i7 = k1 + (rand.nextInt(3) - 1);
				}

				int k8 = j1 - k6 * 8 - 9;
				gui().blit(matrixStack, k8, i7, 16 + i8 * 9, 132, 9, 9);
				if (k6 * 2 + 1 < l) {
					gui().blit(matrixStack, k8, i7, k7 + 36, 132, 9, 9);
				}

				if (k6 * 2 + 1 == l) {
					gui().blit(matrixStack, k8, i7, k7 + 45, 132, 9, 9);
				}
			}
			popPose();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderManaOverlay(PoseStack matrixStack, boolean needsAir, AttributeInstance iattributemaxMana, Player playerentity, double totalHealth, int x, int j3, int k2) {
		int moveUp = 0;
		if (Combat.isSurviveLoaded()) {
			if (SurviveCompat.isThirstEnabled() && SurviveCompat.isStaminaEnabled()) {
				moveUp += 20;
				if (needsAir && totalHealth < 60.0D) moveUp += 10;
				if (totalHealth > 20.0D) moveUp -= 10;
				if (totalHealth > 40.0D && j3 == 0) moveUp -= 10;
			}
			else if (SurviveCompat.isThirstEnabled() || SurviveCompat.isStaminaEnabled()) {
				moveUp += 10;
				if (needsAir && totalHealth < 40.0D) moveUp += 10;
				if (totalHealth > 20.0D && j3 == 0) moveUp -= 10;
			}
			else {
				if (j3 > 0 || needsAir && totalHealth < 20.0D) moveUp += 10;
			}
		}
		else {
			if (j3 > 0 || needsAir && totalHealth < 20.0D) moveUp += 10;
		}
		push("manaBar");

		int mana = Mth.floor(CombatEntityStats.getMana(playerentity));
		int manaCap = iattributemaxMana != null ? Mth.floor(iattributemaxMana.getValue()) : 1;
		int primevalAffinity = 0;
		int elementalAffinitySprite = 0;
		SpellCategory elementalAffinity = SpellCategory.getStrongestElementalAffinity(playerentity);
		if (!Combat.MAGIC_CONFIG.toggle_affinities) {
			primevalAffinity = 0;
			elementalAffinitySprite = 0;
		}
		else {
			if (CombatEntityStats.getPrimevalAffinity(playerentity) == SpellCategory.NONE) {
				primevalAffinity = 7;
			} 
			if (elementalAffinity == SpellCategory.NONE) {
				elementalAffinitySprite = 7;
			} 
		}
		int i = manaCap;
		if (i > 0) {
			matrixStack.pushPose();
			int k = Mth.clamp((int)(mana * 183.0F / manaCap), 0, 183);
			if(Minecraft.renderNames() && mc.gameMode.hasExperience())gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 0, 182, 6);
			if (k > 0) {
				if (mc.gameMode.hasExperience()) {
					RenderSystem.setShaderColor(elementalAffinity.getrCOlor(), elementalAffinity.getgCOlor(), elementalAffinity.getbCOlor(), 1.0F);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 21 + elementalAffinitySprite, k, 6);
				}
				if (mc.gameMode.hasExperience()) {
					RenderSystem.setShaderColor(CombatEntityStats.getPrimevalAffinity(playerentity).getrCOlor(), CombatEntityStats.getPrimevalAffinity(playerentity).getgCOlor(), CombatEntityStats.getPrimevalAffinity(playerentity).getbCOlor(), 1.0F);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 7 + primevalAffinity, k, 6);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 35 + primevalAffinity, k, 6);
				}
				if (mc.gameMode.hasExperience()) {
					RenderSystem.setShaderColor(CombatEntityStats.getPrimevalAffinity(playerentity).getrCOlor(), CombatEntityStats.getPrimevalAffinity(playerentity).getgCOlor(), CombatEntityStats.getPrimevalAffinity(playerentity).getbCOlor(), 1.0F);
				}
			}
			matrixStack.popPose();
		}
		popPose();
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderSpellOverlay(PoseStack matrixStack, Player playerentity, int k2) {
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
				int i10 = (int) ((gui().screenWidth/scale) - mc.font.width(s));
				int i11 = (int) ((gui().screenWidth/scale2) - mc.font.width(s1));
				int i12 = (int) ((gui().screenWidth/scale2) - mc.font.width(s2));
				if(Minecraft.renderNames() && !mc.gameMode.isAlwaysFlying()) {
					gui().blit(matrixStack, gui().screenWidth - 61, j10 - offset, 60, 49, 60, 30);
					int w = gui().screenWidth-1;
					int h = j10 - offset;
					int r = Mth.floor(q*255)%255;
					Gui.fill(matrixStack, w - cWidth, h + 10, w - (cWidth * -2), h + 10 + 10, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - nWidth, h + 21, w - (nWidth * -2), h + 21 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - pWidth, h + 01, w - (pWidth * -2), h + 01 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, w - tWidth, h + 10, w - (tWidth * -2), h + 10 + 10, FastColor.ARGB32.color(72, 255 - r, r, 0));

					matrixStack.pushPose();
					matrixStack.scale(scale, scale, scale);
					mc.font.draw(matrixStack, s, (float)i10 - (2 / scale) , (float)j10 / scale, cSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					mc.font.draw(matrixStack, s1, (float)i11 - (2 / scale2), (float)(j10 + 10) / scale2, nSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					mc.font.draw(matrixStack, s2, (float)i12 - (2 / scale2), (float)(j10 - 10) / scale2, pSpell.getCategory().getColor().getValue());
					matrixStack.popPose();
				}
				popPose();
			}

			else if (playerentity.getOffhandItem().getItem() instanceof AbstractMagicCastingItem && !AbstractSpellBookItem.getMainSpellBook(playerentity).isEmpty()) {
				int time = playerentity.getUseItem() != playerentity.getOffhandItem() ? 0 : (playerentity.getOffhandItem().getUseDuration() - playerentity.getUseItemRemainingTicks());
				float q = (float)time/(float)cSpell.getCastTime();
				int tWidth = Mth.clamp((int) (q * 60),0,60);
				push("spellRight");
				if(Minecraft.renderNames() && !mc.gameMode.isAlwaysFlying()) {
					gui().blit(matrixStack, 1, j10 - offset, 0, 49, 60, 30);

					int h = j10 - offset;
					int r = Mth.floor(q*255)%255;
					Gui.fill(matrixStack, 1, h + 10, 1 + cWidth, h + 10 + 10, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 21, 1 + nWidth, h + 21 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 01, 1 + pWidth, h + 01 +  8, FastColor.ARGB32.color(36, 255, 255, 255));
					Gui.fill(matrixStack, 1, h + 10, 1 + tWidth, h + 10 + 10, FastColor.ARGB32.color(72, 255 - r, r, 0));

					matrixStack.pushPose();
					matrixStack.scale(scale, scale, scale);
					mc.font.draw(matrixStack, s, 2 / scale, (float)j10 / scale, cSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					mc.font.draw(matrixStack, s1, 2 / scale2, (float)(j10 + 10) / scale2, nSpell.getCategory().getColor().getValue());
					matrixStack.popPose();

					matrixStack.pushPose();
					matrixStack.scale(scale2, scale2, scale2);
					mc.font.draw(matrixStack, s2, 2 / scale2, (float)(j10 - 10) / scale2, pSpell.getCategory().getColor().getValue());
					matrixStack.popPose();
				}
				popPose();
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderArmor(PoseStack matrixStack, double attributeValue, int armorSub, String section, int yValue, int i1, int k2, Player playerentity) {
		int j3 = Mth.floor(attributeValue);
		int subbedArmor = j3 - armorSub;
		push(section);
		for(int l3 = 0; l3 < 10; ++l3) {
			if (subbedArmor > 0) {
				int i4 = i1 + l3 * 8;
				if (l3 * 2 + 1 < subbedArmor) {
					gui().blit(matrixStack, i4, k2, 34, yValue, 9, 9);
				}

				if (l3 * 2 + 1 == subbedArmor) {
					gui().blit(matrixStack, i4, k2, 25, yValue, 9, 9);
				}

				if (l3 * 2 + 1 > subbedArmor) {
					gui().blit(matrixStack, i4, k2, 16, yValue, 9, 9);
				}
			}
		}
		popPose();
	}

	public static void push(String section) {
		mc.getProfiler().push(section);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUI_ICONS);
	}

	public static void popPose() {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getProfiler().pop();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
	}

	private static int func_212306_a(LivingEntity p_212306_1_) {
		if (p_212306_1_ != null && p_212306_1_.showVehicleHealth()) {
			float f = p_212306_1_.getMaxHealth();
			int i = (int)(f + 0.5F) / 2;
			if (i > 30) {
				i = 30;
			}

			return i;
		} else {
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	protected static void renderPortal(float pTimeInPortal) {
		int screenWidth = mc.getWindow().getGuiScaledWidth();
		int screenHeight = mc.getWindow().getGuiScaledHeight();
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
		TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(CBlocks.ACROTLEST_RUINED_PORTAL.defaultBlockState());
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
}
