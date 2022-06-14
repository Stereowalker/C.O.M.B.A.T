package com.stereowalker.combat.compat.curios;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.BackpackModel;
import com.stereowalker.combat.client.model.QuiverModel;
import com.stereowalker.combat.world.inventory.QuiverContainer;
import com.stereowalker.combat.world.inventory.SheathContainer;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.BackpackItem;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.QuiverItem;
import com.stereowalker.combat.world.item.SheathItem;
import com.stereowalker.unionlib.util.ModHelper;
import com.stereowalker.unionlib.world.item.AccessoryItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class CuriosCompat {
	
	@OnlyIn(Dist.CLIENT)
	public static void registerCurioRenders() {
		CuriosRendererRegistry.register(CItems.QUIVER, () -> {
			return new ICurioRenderer() {
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
						PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
						int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
						float headPitch) {
					LivingEntity livingEntity = slotContext.entity();
					ItemStack quiver = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (quiver.getItem() instanceof QuiverItem) {
						QuiverContainer quiverInventory = ((QuiverItem)quiver.getItem()).loadInventory(quiver);
						int totalCount = 0;
						for (int i = 0; i < quiverInventory.getContainerSize(); i ++) {
							totalCount+=quiverInventory.getItem(i).getCount();
						}
						Combat.itemStackRender.quiver.showArrow1 = totalCount > 0*36;
						Combat.itemStackRender.quiver.showArrow2 = totalCount > 1*36;
						Combat.itemStackRender.quiver.showArrow3 = totalCount > 2*36;
						Combat.itemStackRender.quiver.showArrow4 = totalCount > 3*36;
						Combat.itemStackRender.quiver.showArrow5 = totalCount > 4*36;
						Combat.itemStackRender.quiver.showArrow6 = totalCount > 5*36;
						Combat.itemStackRender.quiver.showArrow7 = totalCount > 6*36;
						Combat.itemStackRender.quiver.showArrow8 = totalCount > 7*36;
						Combat.itemStackRender.quiver.showArrow9 = totalCount > 8*36;
						matrixStack.pushPose();
						ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
						ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
						moveWhenSneaking(livingEntity, matrixStack);

						com.mojang.blaze3d.vertex.VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, Combat.itemStackRender.quiver.renderType(QuiverModel.OVERLAY_LOCATION), false, stack.hasFoil());
						Combat.itemStackRender.quiver.renderToBuffer(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

						int i = ((DyeableLeatherItem)quiver.getItem()).getColor(quiver);
						float f = (float)(i >> 16 & 255) / 255.0F;
						float f1 = (float)(i >> 8 & 255) / 255.0F;
						float f2 = (float)(i & 255) / 255.0F;
						com.mojang.blaze3d.vertex.VertexConsumer ivertexbuilder1 = ItemRenderer.getFoilBuffer(renderTypeBuffer, Combat.itemStackRender.quiver.renderType(QuiverModel.TEXTURE_LOCATION), false, stack.hasFoil());
						Combat.itemStackRender.quiver.renderToBuffer(matrixStack, ivertexbuilder1, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
						matrixStack.popPose();

						if (!quiverInventory.getAttachedBow().isEmpty()) {
							matrixStack.pushPose();
							ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
							ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
							moveWhenSneaking(livingEntity, matrixStack);
//							rotateWhenOnRight(matrixStack);
//							matrixStack.translate(0.1D, 0,Minecraft.getInstance().gameSettings.mainHand == HumanoidArm.RIGHT ? -0.16D :  0.16D);
							matrixStack.translate(-0.01D, 0.35D, 0.32D);
							matrixStack.mulPose(Vector3f.YP.rotationDegrees(90));
							matrixStack.mulPose(Vector3f.XP.rotationDegrees(30));
							matrixStack.mulPose(Vector3f.ZN.rotationDegrees(10));
							//Draw Bow
							Minecraft.getInstance().getItemRenderer().renderStatic(quiverInventory.getAttachedBow(), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, 0);
							matrixStack.popPose();
						}
					}
				}
			};
		});
		
		CuriosRendererRegistry.register(CItems.BACKPACK, () -> {
			return new ICurioRenderer() {
				
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
						PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
						int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
						float headPitch) {
					LivingEntity livingEntity = slotContext.entity();
					ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
					ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
					moveWhenSneaking(livingEntity, matrixStack);
					ItemStack backpack = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (backpack.getItem() instanceof BackpackItem) {
						com.mojang.blaze3d.vertex.VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, Combat.itemStackRender.backpack.renderType(BackpackModel.OVERLAY_LOCATION), false, stack.hasFoil());
						Combat.itemStackRender.backpack.renderToBuffer(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

						int i = ((DyeableLeatherItem)backpack.getItem()).getColor(backpack);
						float f = (float)(i >> 16 & 255) / 255.0F;
						float f1 = (float)(i >> 8 & 255) / 255.0F;
						float f2 = (float)(i & 255) / 255.0F;
						com.mojang.blaze3d.vertex.VertexConsumer ivertexbuilder1 = ItemRenderer.getFoilBuffer(renderTypeBuffer, Combat.itemStackRender.backpack.renderType(BackpackModel.TEXTURE_LOCATION), false, stack.hasFoil());
						Combat.itemStackRender.backpack.renderToBuffer(matrixStack, ivertexbuilder1, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
					}
				}
			};
		});
		
		CuriosRendererRegistry.register(CItems.SHEATH, () -> {
			return new ICurioRenderer() {
				
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
						PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
						int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
						float headPitch) {
					LivingEntity livingEntity = slotContext.entity();
					ItemStack sheath = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (sheath.getItem() instanceof SheathItem) {
						SheathContainer sheathInventory = ((SheathItem)sheath.getItem()).loadInventory(sheath);
						//Draw Sword
						matrixStack.pushPose();
						ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
						ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
						moveWhenSneaking(livingEntity, matrixStack);
						rotateWhenOnRight(matrixStack);
						matrixStack.translate(0.1D, 0,Minecraft.getInstance().options.mainHand == HumanoidArm.RIGHT ? -0.16D :  0.16D);
						matrixStack.mulPose(Vector3f.YP.rotationDegrees(90));
						matrixStack.mulPose(Vector3f.XN.rotationDegrees(20));
						if (!sheathInventory.getSheathedSword().isEmpty()) {
							//Draw Sword
							Minecraft.getInstance().getItemRenderer().renderStatic(sheathInventory.getSheathedSword(), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, 0);
						}
						float scale = 1.0f;
						matrixStack.scale(scale+0.1f, scale+0.01f, scale+0.1f);
						matrixStack.translate(0.003, 0.493, 0.065);
						matrixStack.mulPose(Vector3f.XN.rotationDegrees(179));
						Minecraft.getInstance().getItemRenderer().renderStatic(sheath, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, 0);
						matrixStack.popPose();
					}
				}
			};
		});
	}

	public static ICapabilityProvider attachSupportForCurios(ItemStack stack) {
		final ICurio curio;
		if (stack.getItem() instanceof AccessoryItem) {
			AccessoryItem ring = (AccessoryItem) stack.getItem();
			curio = new ICurio() {
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				};
				@Override
				public void curioTick(String identifier, int index, LivingEntity livingEntity) {
					ICurio.super.curioTick(identifier, index, livingEntity);
					ring.accessoryTick(livingEntity.level, livingEntity, stack, -999);
				}
				@Override
				public ItemStack getStack() {
					return stack;
				}
			};
		}
		else if (stack.getItem() instanceof QuiverItem) {
			curio = new ICurio() {
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				};
				@Override
				public ItemStack getStack() {
					return stack;
				};
			};
		}
		else if (stack.getItem() instanceof BackpackItem) {
			curio = new ICurio() {
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				}
				@Override
				public ItemStack getStack() {
					return stack;
				};
			};
		}
		else if (stack.getItem() instanceof SheathItem) {
			curio = new ICurio() {
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				}
				@Override
				public ItemStack getStack() {
					return stack;
				};
			};
		}
		else if (stack.getItem() instanceof AbstractSpellBookItem) {
			curio = new ICurio() {
				@Override
				public ItemStack getStack() {
					return stack;
				}};
		}
		else curio = null;

		if (curio != null) {
			ICapabilityProvider provider = new ICapabilityProvider() {
				private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);
				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
						@Nullable Direction side) {
					return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
				}
			};
			return provider;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	public static boolean shouldAttachCapability(ItemStack stack) {
		return attachSupportForCurios(stack) != ItemStack.EMPTY;
	}

	public static ResourceLocation getIdItem() {
		return CuriosCapability.ID_ITEM;
	}

	@OnlyIn(Dist.CLIENT)
	public static void moveWhenSneaking(LivingEntity entity, com.mojang.blaze3d.vertex.PoseStack stack) {
		if (entity.isCrouching()) stack.translate(0, 0, -0.1D);
	}

	@OnlyIn(Dist.CLIENT)
	public static void rotateWhenOnRight(com.mojang.blaze3d.vertex.PoseStack stack) {
		if (Minecraft.getInstance().options.mainHand == HumanoidArm.RIGHT) stack.mulPose(Vector3f.YN.rotationDegrees(180));
	}

	public static ItemStack getSlotsForType(@Nonnull final LivingEntity livingEntity, String identifier, int slot) {
		if (ModHelper.isCuriosLoaded()) {
			return CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(handler -> {
				ICurioStacksHandler stacks = handler.getCurios().get(identifier);
				return stacks != null ? stacks.getStacks().getStackInSlot(slot) : ItemStack.EMPTY;
			}).orElse(ItemStack.EMPTY);
		} else return ItemStack.EMPTY;
	}
}
