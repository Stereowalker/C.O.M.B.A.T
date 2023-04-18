package com.stereowalker.combat.client.events;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.compat.SurviveCompat;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.GunItem;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.combat.world.item.ReinforcedCrossbowItem;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
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
//				transformBlockFirstPerson(event.getPoseStack(), event.getPartialTicks(), handside, stack);
				transformSideFirstPerson(event.getPoseStack(), handside, event.getEquipProgress());
//				Minecraft.getInstance().getItemInHandRenderer().renderItem(player, stack, flag3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
				event.setCanceled(false);
				//				event.getMatrixStack().popPose();
			}
		}
	}

	private static void transformBlockFirstPerson(PoseStack matrixStackIn, float partialTicks, HumanoidArm handIn, ItemStack stack) {
		int i = handIn == HumanoidArm.RIGHT ? 1 : -1;
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(70 * i));
		matrixStackIn.mulPose(Axis.YP.rotationDegrees(90 * i));
		matrixStackIn.translate(0.0d, -0.1d * i, 0.5d);
	}

	private static void transformSideFirstPerson(PoseStack matrixStackIn, HumanoidArm handIn, float equippedProg) {
		int i = handIn == HumanoidArm.RIGHT ? 1 : -1;
		matrixStackIn.translate((double)((float)i * 0.56F), (double)(-0.52F + equippedProg * -0.6F), (double)-0.72F);
	}

//	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void renderHUD(/* RenderGameOverlayEvent.Post event */) {
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
}
