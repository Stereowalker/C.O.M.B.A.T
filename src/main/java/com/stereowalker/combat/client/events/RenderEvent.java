package com.stereowalker.combat.client.events;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.compat.SurviveCompat;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.item.AbstractMagicCastingItem;
import com.stereowalker.combat.item.AbstractSpellBookItem;
import com.stereowalker.combat.item.GunItem;
import com.stereowalker.combat.item.ItemFilters;
import com.stereowalker.combat.item.ReinforcedCrossbowItem;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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
	static IngameGui gui() {
		return mc.ingameGUI;
	}

	static long healthUpdateCounter;
	static int playerHealth;
	static long lastSystemTime;
	static int lastPlayerHealth;

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void renderLayer(RenderLivingEvent<?, ?> event) {
		if (event.getEntity() instanceof ClientPlayerEntity) {
			ClientPlayerEntity clientPlayer = (ClientPlayerEntity) event.getEntity();
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			if ((itemstack.getItem() instanceof ReinforcedCrossbowItem ||itemstack.getItem() instanceof GunItem) 
					|| (itemstack1.getItem() instanceof ReinforcedCrossbowItem ||itemstack1.getItem() instanceof GunItem)) {
				PlayerRenderer playerRenderer = (PlayerRenderer) event.getRenderer();
				PlayerModel<AbstractClientPlayerEntity> playermodel = playerRenderer.getEntityModel();
				BipedModel.ArmPose bipedmodel$armpose = getArmPose(clientPlayer, itemstack, itemstack1, Hand.MAIN_HAND);
				BipedModel.ArmPose bipedmodel$armpose1 = getArmPose(clientPlayer, itemstack, itemstack1, Hand.OFF_HAND);
				if (clientPlayer.getPrimaryHand() == HandSide.RIGHT) {
					if (!bipedmodel$armpose.equals(BipedModel.ArmPose.EMPTY) && !bipedmodel$armpose.equals(BipedModel.ArmPose.ITEM)) playermodel.rightArmPose = bipedmodel$armpose;
					if (!bipedmodel$armpose1.equals(BipedModel.ArmPose.EMPTY) && !bipedmodel$armpose1.equals(BipedModel.ArmPose.ITEM)) playermodel.leftArmPose = bipedmodel$armpose1;
				} else {
					if (!bipedmodel$armpose1.equals(BipedModel.ArmPose.EMPTY) && !bipedmodel$armpose1.equals(BipedModel.ArmPose.ITEM)) playermodel.rightArmPose = bipedmodel$armpose1;
					if (!bipedmodel$armpose.equals(BipedModel.ArmPose.EMPTY) && !bipedmodel$armpose.equals(BipedModel.ArmPose.ITEM)) playermodel.leftArmPose = bipedmodel$armpose;
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static BipedModel.ArmPose getArmPose(AbstractClientPlayerEntity player, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn) {
		BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
		ItemStack itemstack = handIn == Hand.MAIN_HAND ? itemStackMain : itemStackOff;
		if (!itemstack.isEmpty()) {
			bipedmodel$armpose = BipedModel.ArmPose.ITEM;
			if (itemstack.getItem() instanceof GunItem && !player.isSprinting()) {
				bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
			}
			else {
				boolean flag3 = itemStackMain.getItem() instanceof ReinforcedCrossbowItem;
				boolean flag = CrossbowItem.isCharged(itemStackMain);
				boolean flag1 = itemStackOff.getItem() instanceof ReinforcedCrossbowItem;
				boolean flag2 = CrossbowItem.isCharged(itemStackOff);
				if (flag3 && flag) {
					bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
				}

				if (flag1 && flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
					bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
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

	@SubscribeEvent
	public static void swordBlock(RenderHandEvent event) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack stack = event.getItemStack();
		boolean flag = event.getHand() == Hand.MAIN_HAND;
		HandSide handside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
		if (Config.BATTLE_COMMON.swordBlocking.get() && (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack) || ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack))) {
			if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == event.getHand()) {
//				event.getMatrixStack().push();
				boolean flag3 = handside == HandSide.RIGHT;
				transformBlockFirstPerson(event.getMatrixStack(), event.getPartialTicks(), handside, stack);
				transformSideFirstPerson(event.getMatrixStack(), handside, event.getEquipProgress());
				Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(player, stack, flag3 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, event.getMatrixStack(), event.getBuffers(), event.getLight());
				event.setCanceled(false);
//				event.getMatrixStack().pop();
			}
		}
	}

	private static void transformBlockFirstPerson(MatrixStack matrixStackIn, float partialTicks, HandSide handIn, ItemStack stack) {
		int i = handIn == HandSide.RIGHT ? 1 : -1;
		matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(70 * i));
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90 * i));
		matrixStackIn.translate(0.0d, -0.1d * i, 0.5d);
	}

	private static void transformSideFirstPerson(MatrixStack matrixStackIn, HandSide handIn, float equippedProg) {
		int i = handIn == HandSide.RIGHT ? 1 : -1;
		matrixStackIn.translate((double)((float)i * 0.56F), (double)(-0.52F + equippedProg * -0.6F), (double)-0.72F);
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void renderHUD(RenderGameOverlayEvent.Post event) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Random rand = new Random();
		//Render Mana Bar
		PlayerEntity playerentity = gui().getRenderViewPlayer();
		LivingEntity living;
		if (playerentity != null) {
			Entity entity = playerentity.getRidingEntity();
			if (entity == null) {
				living = null;
			}

			if (entity instanceof LivingEntity) {
				living = (LivingEntity)entity;
			}
		}

		living = null;
		if (playerentity != null) {
			ModifiableAttributeInstance iattributemaxHealth = playerentity.getAttribute(Attributes.MAX_HEALTH);
			ModifiableAttributeInstance iattributemaxMana = playerentity.getAttribute(CAttributes.MAX_MANA);
			double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
			int absorption = ModHelper.isMantleLoaded() ? Math.min(MathHelper.ceil(playerentity.getAbsorptionAmount()), 20) : MathHelper.ceil(playerentity.getAbsorptionAmount());
			double totalHealth = maxHealth + absorption;
			int i1 = gui().scaledWidth / 2 - 91;
			int j1 = gui().scaledWidth / 2 + 91;
			int k1 = gui().scaledHeight - 39;
			float f = (float)maxHealth;
			int l1 = absorption;
			int i2 = MathHelper.ceil((f + (float)l1) / 2.0F / 10.0F);
			int j2 = Math.max(10 - (i2 - 2), 3);
			int k2 = k1 - (i2 - 1) * j2 - 10;
			int j3 = playerentity.getTotalArmorValue();
			if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR) {
				renderArmorOverlay(event.getMatrixStack(), maxHealth, absorption, playerentity, k1, i1);
			}
			if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
				renderSaturationOverlay(event.getMatrixStack(), living, playerentity, j1, k1, rand);
			}
			boolean needsAir = false;
			int l6 = playerentity.getAir();
			int j7 = playerentity.getMaxAir();
			if (playerentity.areEyesInFluid(FluidTags.WATER) || l6 < j7) {
				int j8 = MathHelper.ceil((double)(l6 - 2) * 10.0D / (double)j7);
				int l8 = MathHelper.ceil((double)l6 * 10.0D / (double)j7) - j8;

				for(int k5 = 0; k5 < j8 + l8; ++k5) {
					if (k5 < j8) {
						needsAir = true;
					} else {
						needsAir = true;
					}
				}
			}

			int x = gui().scaledWidth / 2 - 91;
			if (event.getType() == RenderGameOverlayEvent.ElementType.ARMOR || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
				renderManaOverlay(event.getMatrixStack(), needsAir, iattributemaxMana, playerentity, totalHealth, x, j3, k2);
			}

			if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
				renderSpellOverlay(event.getMatrixStack(), playerentity, k2);
			}
			if (playerentity.getHeldItemMainhand().getItem() instanceof GunItem) {
				ItemStack itemStack = playerentity.getHeldItemMainhand();
				GunItem item = (GunItem)playerentity.getHeldItemMainhand().getItem();
				if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
					mc.getProfiler().startSection("ammo");
					String s = item.getAmmo(itemStack) + "/" + item.getMagazineCapacity();
					int i10 = gui().scaledWidth - mc.fontRenderer.getStringWidth(s);
					if(Minecraft.isGuiEnabled() && !mc.playerController.isSpectatorMode()) {
						mc.fontRenderer.drawString(event.getMatrixStack(), s, (float)(i10 - 10), 10, TextFormatting.YELLOW.getColor());
					}
					mc.getProfiler().endSection();
				}
			}

			//Render Frostbite Overlay
			if (event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE) {
				if(playerentity.isPotionActive(CEffects.FROSTBITE)) {
					renderFrostbiteOverlay();
				}
			}

			//Render Acrotlest Portal Overlay
			if (playerentity.getBlockState().getBlock() == CBlocks.ACROTLEST_RUINED_PORTAL || playerentity.getBlockState().getBlock() == CBlocks.ACROTLEST_PORTAL) {
				if (event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
					if (!mc.player.isPotionActive(Effects.NAUSEA)) {
						float f1 = MathHelper.lerp(event.getPartialTicks(), mc.player.prevTimeInPortal, mc.player.timeInPortal);
						if (f1 > 0.0F) {
							renderPortal(f1);
						}
					}
				}
			}
			//RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			//RenderSystem.enableBlend();
			//RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			//RenderSystem.disableAlphaTest();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderArmorOverlay(MatrixStack matrixStack, double maxHealth, int absorption, PlayerEntity playerentity, int k1, int i1) {
		float f0 = Combat.isFirstAidLoaded() ? 0.0f : (float)maxHealth;
		int l10 = Combat.isFirstAidLoaded() ? 0 : absorption;
		int i20 = MathHelper.ceil((f0 + (float)l10) / 2.0F / 10.0F);
		int j20 = Math.max(10 - (i20 - 2), 3);
		int k20 = k1 - (i20 - 1) * j20 - 10;
		int armorY = Combat.isFirstAidLoaded() ? k20-2 : k20;
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 20, "betterArmor1", 141, i1, armorY, playerentity);
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR).getValue(), 40, "betterArmor2", 150, i1, armorY, playerentity);
		renderArmor(matrixStack, playerentity.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue(), 0, "armorToughness", 114, i1, armorY, playerentity);
		renderArmor(matrixStack, CombatEntityStats.getResist(playerentity), 0, "knockbackResistance", 123, i1, armorY, playerentity);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderSaturationOverlay(MatrixStack matrixStack, LivingEntity living, PlayerEntity playerentity, int j1, int k1, Random rand) {
		LivingEntity livingentity = living;
		int l = (int) (playerentity.getFoodStats().getSaturationLevel());
		int j6 = func_212306_a(livingentity);
		if (j6 == 0) {
			startSection("saturation");
			for(int k6 = 0; k6 < 10; ++k6) {
				int i7 = k1;
				int k7 = 16;
				int i8 = 0;
				if (playerentity.getFoodStats().getSaturationLevel() <= 0.0F && gui().getTicks() % (l * 3 + 1) == 0) {
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
			endSection();
		}
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public static void renderManaOverlay(MatrixStack matrixStack, boolean needsAir, ModifiableAttributeInstance iattributemaxMana, PlayerEntity playerentity, double totalHealth, int x, int j3, int k2) {
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
		startSection("manaBar");

		int mana = MathHelper.floor(CombatEntityStats.getMana(playerentity));
		int manaCap = iattributemaxMana != null ? MathHelper.floor(iattributemaxMana.getValue()) : 1;
		int specialAffinity = 0;
		int elementalAffinity = 0;
		int lifeAffinity = 0;
		if (!Config.MAGIC_COMMON.toggle_affinities.get()) {
			specialAffinity = 0;
			elementalAffinity = 0;
			lifeAffinity = 0;
		}
		else {
			if (CombatEntityStats.getSpecialAffinity(playerentity) == SpellCategory.NONE) {
				specialAffinity = 7;
			} 
			if (CombatEntityStats.getElementalAffinity(playerentity) == SpellCategory.NONE) {
				elementalAffinity = 7;
			} 
			if (CombatEntityStats.getLifeAffinity(playerentity) == SpellCategory.NONE) {
				lifeAffinity = 7;
			} 
		}
		int i = manaCap;
		if (i > 0) {
			matrixStack.push();
			int k = MathHelper.clamp((int)(mana * 183.0F / manaCap), 0, 183);
			if(Minecraft.isGuiEnabled() && mc.playerController.gameIsSurvivalOrAdventure())gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 0, 182, 6);
			if (k > 0) {
				if (mc.playerController.gameIsSurvivalOrAdventure()) {
					RenderSystem.color4f(CombatEntityStats.getElementalAffinity(playerentity).getrCOlor(), CombatEntityStats.getElementalAffinity(playerentity).getgCOlor(), CombatEntityStats.getElementalAffinity(playerentity).getbCOlor(), 1.0F);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 21 + elementalAffinity, k, 6);
				}
				if (mc.playerController.gameIsSurvivalOrAdventure()) {
					RenderSystem.color4f(CombatEntityStats.getSpecialAffinity(playerentity).getrCOlor(), CombatEntityStats.getSpecialAffinity(playerentity).getgCOlor(), CombatEntityStats.getSpecialAffinity(playerentity).getbCOlor(), 1.0F);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 7 + specialAffinity, k, 6);
				}
				if (mc.playerController.gameIsSurvivalOrAdventure()) {
					RenderSystem.color4f(CombatEntityStats.getLifeAffinity(playerentity).getrCOlor(), CombatEntityStats.getLifeAffinity(playerentity).getgCOlor(), CombatEntityStats.getLifeAffinity(playerentity).getbCOlor(), 1.0F);
					gui().blit(matrixStack, x, k2 - moveUp + 2, 0, 35 + lifeAffinity, k, 6);
				}
			}
			matrixStack.pop();
		}
		endSection();
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public static void renderSpellOverlay(MatrixStack matrixStack, PlayerEntity playerentity, int k2) {
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
			ITextComponent s = cSpell.getName(SpellStats.getSpellKnowledge(playerentity, cSpell));
			ITextComponent s1 = nSpell.getName(SpellStats.getSpellKnowledge(playerentity, nSpell));
			ITextComponent s2 = pSpell.getName(SpellStats.getSpellKnowledge(playerentity, pSpell));
			int cWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, cSpell).cooldownColmpetion())*60);
			int nWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, nSpell).cooldownColmpetion())*60);
			int pWidth = (int) ((CombatEntityStats.getSpellStats(playerentity, pSpell).cooldownColmpetion())*60);

			if (playerentity.getHeldItemMainhand().getItem() instanceof AbstractMagicCastingItem) {
				int time = playerentity.getActiveItemStack() != playerentity.getHeldItemMainhand() ? 0 : (playerentity.getHeldItemMainhand().getUseDuration() - playerentity.getItemInUseCount());
				float q = (float)time/(float)cSpell.getCastTime();
				int tWidth = MathHelper.clamp((int) (q * 60),0,60);
				startSection("spellLeft");
				int i10 = (int) ((gui().scaledWidth/scale) - mc.fontRenderer.getStringPropertyWidth(s));
				int i11 = (int) ((gui().scaledWidth/scale2) - mc.fontRenderer.getStringPropertyWidth(s1));
				int i12 = (int) ((gui().scaledWidth/scale2) - mc.fontRenderer.getStringPropertyWidth(s2));
				if(Minecraft.isGuiEnabled() && !mc.playerController.isSpectatorMode()) {
					gui().blit(matrixStack, gui().scaledWidth - 61, j10 - offset, 60, 49, 60, 30);

					RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.101F);
					gui().blit(matrixStack, gui().scaledWidth - cWidth-1, j10 - offset + 10, 0, 82, cWidth, 10);
					gui().blit(matrixStack, gui().scaledWidth - nWidth-1, j10 - offset + 21, 0, 82, nWidth, 8);
					gui().blit(matrixStack, gui().scaledWidth - pWidth-1, j10 - offset + 01, 0, 82, pWidth, 8);
					RenderSystem.color4f(1.0f-q, q, 0.0f, 0.101F);
					gui().blit(matrixStack, gui().scaledWidth - tWidth-1, j10 - offset + 10, 0, 82, tWidth, 10);

					matrixStack.push();
					matrixStack.scale(scale, scale, scale);
					mc.fontRenderer.drawText(matrixStack, s, (float)i10 - (2 / scale) , (float)j10 / scale, cSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();

					matrixStack.push();
					matrixStack.scale(scale2, scale2, scale2);
					mc.fontRenderer.drawText(matrixStack, s1, (float)i11 - (2 / scale2), (float)(j10 + 10) / scale2, nSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();

					matrixStack.push();
					matrixStack.scale(scale2, scale2, scale2);
					mc.fontRenderer.drawText(matrixStack, s2, (float)i12 - (2 / scale2), (float)(j10 - 10) / scale2, pSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();
				}
				endSection();
			}

			else if (playerentity.getHeldItemOffhand().getItem() instanceof AbstractMagicCastingItem && !AbstractSpellBookItem.getMainSpellBook(playerentity).isEmpty()) {
				int time = playerentity.getActiveItemStack() != playerentity.getHeldItemOffhand() ? 0 : (playerentity.getHeldItemOffhand().getUseDuration() - playerentity.getItemInUseCount());
				float q = (float)time/(float)cSpell.getCastTime();
				int tWidth = MathHelper.clamp((int) (q * 60),0,60);
				startSection("spellRight");
				if(Minecraft.isGuiEnabled() && !mc.playerController.isSpectatorMode()) {
					gui().blit(matrixStack, 1, j10 - offset, 0, 49, 60, 30);

					RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.101F);
					gui().blit(matrixStack, 1, j10 - offset + 10, 0, 82, cWidth, 10);
					gui().blit(matrixStack, 1, j10 - offset + 21, 0, 82, nWidth, 8);
					gui().blit(matrixStack, 1, j10 - offset + 01, 0, 82, pWidth, 8);
					RenderSystem.color4f(1.0f-q, q, 0.0f, 0.101F);
					gui().blit(matrixStack, 1, j10 - offset + 10, 0, 82, tWidth, 10);

					matrixStack.push();
					matrixStack.scale(scale, scale, scale);
					mc.fontRenderer.drawText(matrixStack, s, 2 / scale, (float)j10 / scale, cSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();

					matrixStack.push();
					matrixStack.scale(scale2, scale2, scale2);
					mc.fontRenderer.drawText(matrixStack, s1, 2 / scale2, (float)(j10 + 10) / scale2, nSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();

					matrixStack.push();
					matrixStack.scale(scale2, scale2, scale2);
					mc.fontRenderer.drawText(matrixStack, s2, 2 / scale2, (float)(j10 - 10) / scale2, pSpell.getCategory().getTextFormatting().getColor());
					matrixStack.pop();
				}
				endSection();
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderArmor(MatrixStack matrixStack, double attributeValue, int armorSub, String section, int yValue, int i1, int k2, PlayerEntity playerentity) {
		int j3 = MathHelper.floor(attributeValue);
		int subbedArmor = j3 - armorSub;
		startSection(section);
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
		endSection();
	}

	public static void startSection(String section) {
		mc.getProfiler().startSection(section);
		mc.getTextureManager().bindTexture(GUI_ICONS);
	}

	@SuppressWarnings("deprecation")
	public static void endSection() {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getProfiler().endSection();
		mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
	}

	private static int func_212306_a(LivingEntity p_212306_1_) {
		if (p_212306_1_ != null && p_212306_1_.isLiving()) {
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
	protected static void renderFrostbiteOverlay() {
		int scaledWidth = mc.getMainWindow().getScaledWidth();
		int scaledHeight = mc.getMainWindow().getScaledHeight();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableAlphaTest();
		mc.getTextureManager().bindTexture(FROSTBITE_OVERLAY);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0.0D, (double)scaledHeight, -90.0D).tex(0.0F, 1.0F).endVertex();
		bufferbuilder.pos((double)scaledWidth, (double)scaledHeight, -90.0D).tex(1.0F, 1.0F).endVertex();
		bufferbuilder.pos((double)scaledWidth, 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	protected static void renderPortal(float timeInPortal) {
		int scaledWidth = mc.getMainWindow().getScaledWidth();
		int scaledHeight = mc.getMainWindow().getScaledHeight();
		if (timeInPortal < 1.0F) {
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * 0.8F + 0.2F;
		}

		RenderSystem.disableAlphaTest();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, timeInPortal);
		mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite textureatlassprite = mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(CBlocks.ACROTLEST_RUINED_PORTAL.getDefaultState());
		float f = textureatlassprite.getMinU();
		float f1 = textureatlassprite.getMinV();
		float f2 = textureatlassprite.getMaxU();
		float f3 = textureatlassprite.getMaxV();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0.0D, (double)scaledHeight, -90.0D).tex(f, f3).endVertex();
		bufferbuilder.pos((double)scaledWidth, (double)scaledHeight, -90.0D).tex(f2, f3).endVertex();
		bufferbuilder.pos((double)scaledWidth, 0.0D, -90.0D).tex(f2, f1).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
