package com.stereowalker.combat.compat.curios;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.stereowalker.combat.client.renderer.entity.model.BackpackModel;
import com.stereowalker.combat.client.renderer.entity.model.QuiverModel;
import com.stereowalker.combat.inventory.QuiverInventory;
import com.stereowalker.combat.inventory.SheathInventory;
import com.stereowalker.combat.item.AbstractSpellBookItem;
import com.stereowalker.combat.item.BackpackItem;
import com.stereowalker.combat.item.QuiverItem;
import com.stereowalker.combat.item.SheathItem;
import com.stereowalker.unionlib.item.AccessoryItem;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class CuriosCompat {

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
					ring.accessoryTick(livingEntity.world, livingEntity, stack, -999);
				}
			};
		}
		else if (stack.getItem() instanceof QuiverItem) {
			curio = new ICurio() {
				@Override
				public boolean canRender(String identifier, int index, LivingEntity livingEntity) { 
					return true;
				}
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				};
				@Override
				@OnlyIn(Dist.CLIENT)
				public void render(String identifier, int index, com.mojang.blaze3d.matrix.MatrixStack matrixStack, net.minecraft.client.renderer.IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					QuiverModel quiverModel = new QuiverModel();
					ItemStack quiver = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (quiver.getItem() instanceof QuiverItem) {
						QuiverInventory quiverInventory = ((QuiverItem)quiver.getItem()).loadInventory(quiver);
						int totalCount = 0;
						for (int i = 0; i < quiverInventory.getSizeInventory(); i ++) {
							totalCount+=quiverInventory.getStackInSlot(i).getCount();
						}
						quiverModel.showArrow1 = totalCount > 0*36;
						quiverModel.showArrow2 = totalCount > 1*36;
						quiverModel.showArrow3 = totalCount > 2*36;
						quiverModel.showArrow4 = totalCount > 3*36;
						quiverModel.showArrow5 = totalCount > 4*36;
						quiverModel.showArrow6 = totalCount > 5*36;
						quiverModel.showArrow7 = totalCount > 6*36;
						quiverModel.showArrow8 = totalCount > 7*36;
						quiverModel.showArrow9 = totalCount > 8*36;
						matrixStack.push();
						ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
						ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
						moveWhenSneaking(livingEntity, matrixStack);

						com.mojang.blaze3d.vertex.IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(renderTypeBuffer, quiverModel.getRenderType(QuiverModel.OVERLAY_LOCATION), false, stack.hasEffect());
						quiverModel.render(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

						int i = ((net.minecraft.item.IDyeableArmorItem)quiver.getItem()).getColor(quiver);
						float f = (float)(i >> 16 & 255) / 255.0F;
						float f1 = (float)(i >> 8 & 255) / 255.0F;
						float f2 = (float)(i & 255) / 255.0F;
						com.mojang.blaze3d.vertex.IVertexBuilder ivertexbuilder1 = ItemRenderer.getBuffer(renderTypeBuffer, quiverModel.getRenderType(QuiverModel.TEXTURE_LOCATION), false, stack.hasEffect());
						quiverModel.render(matrixStack, ivertexbuilder1, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
						matrixStack.pop();

						if (!quiverInventory.getAttachedBow().isEmpty()) {
							matrixStack.push();
							ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
							ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
							moveWhenSneaking(livingEntity, matrixStack);
//							rotateWhenOnRight(matrixStack);
//							matrixStack.translate(0.1D, 0,Minecraft.getInstance().gameSettings.mainHand == HandSide.RIGHT ? -0.16D :  0.16D);
							matrixStack.translate(-0.01D, 0.35D, 0.32D);
							matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
							matrixStack.rotate(Vector3f.XP.rotationDegrees(30));
							matrixStack.rotate(Vector3f.ZN.rotationDegrees(10));
							//Draw Bow
							Minecraft.getInstance().getItemRenderer().renderItem(quiverInventory.getAttachedBow(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
							matrixStack.pop();
						}
					}
				};
			};
		}
		else if (stack.getItem() instanceof BackpackItem) {
			curio = new ICurio() {
				@Override
				public boolean canRender(String identifier, int index, LivingEntity livingEntity) { 
					return true;
				}
				@Override
				public boolean canRightClickEquip() {
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				};
				@Override
				@OnlyIn(Dist.CLIENT)
				public void render(String identifier, int index, com.mojang.blaze3d.matrix.MatrixStack matrixStack, net.minecraft.client.renderer.IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					BackpackModel backpackModel = new BackpackModel();
					ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
					ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
					moveWhenSneaking(livingEntity, matrixStack);
					ItemStack backpack = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (backpack.getItem() instanceof BackpackItem) {
						com.mojang.blaze3d.vertex.IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(renderTypeBuffer, backpackModel.getRenderType(BackpackModel.OVERLAY_LOCATION), false, stack.hasEffect());
						backpackModel.render(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

						int i = ((net.minecraft.item.IDyeableArmorItem)backpack.getItem()).getColor(backpack);
						float f = (float)(i >> 16 & 255) / 255.0F;
						float f1 = (float)(i >> 8 & 255) / 255.0F;
						float f2 = (float)(i & 255) / 255.0F;
						com.mojang.blaze3d.vertex.IVertexBuilder ivertexbuilder1 = ItemRenderer.getBuffer(renderTypeBuffer, backpackModel.getRenderType(BackpackModel.TEXTURE_LOCATION), false, stack.hasEffect());
						backpackModel.render(matrixStack, ivertexbuilder1, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
					}
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
				public boolean canRender(String identifier, int index, LivingEntity livingEntity) { 
					return true;
				}
				@Override
				public boolean canEquip(String identifier, LivingEntity livingEntity) {
					return !livingEntity.isCrouching();
				};
				@Override
				@OnlyIn(Dist.CLIENT)
				public void render(String identifier, int index, com.mojang.blaze3d.matrix.MatrixStack matrixStack, net.minecraft.client.renderer.IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					ItemStack sheath = CuriosCompat.getSlotsForType(livingEntity, "back", 0);
					if (sheath.getItem() instanceof SheathItem) {
						SheathInventory sheathInventory = ((SheathItem)sheath.getItem()).loadInventory(sheath);
						//Draw Sword
						matrixStack.push();
						ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
						ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
						moveWhenSneaking(livingEntity, matrixStack);
						rotateWhenOnRight(matrixStack);
						matrixStack.translate(0.1D, 0,Minecraft.getInstance().gameSettings.mainHand == HandSide.RIGHT ? -0.16D :  0.16D);
						matrixStack.rotate(Vector3f.YP.rotationDegrees(90));
						matrixStack.rotate(Vector3f.XN.rotationDegrees(20));
						if (!sheathInventory.getSheathedSword().isEmpty()) {
							//Draw Sword
							Minecraft.getInstance().getItemRenderer().renderItem(sheathInventory.getSheathedSword(), ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
						}
						float scale = 1.0f;
						matrixStack.scale(scale+0.1f, scale+0.01f, scale+0.1f);
						matrixStack.translate(0.003, 0.493, 0.065);
						matrixStack.rotate(Vector3f.XN.rotationDegrees(179));
						Minecraft.getInstance().getItemRenderer().renderItem(sheath, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
						matrixStack.pop();
					}
				}
			};
		}
		else if (stack.getItem() instanceof AbstractSpellBookItem) {
			curio = new ICurio() {};
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
	public static void moveWhenSneaking(LivingEntity entity, com.mojang.blaze3d.matrix.MatrixStack stack) {
		if (entity.isCrouching()) stack.translate(0, 0, -0.1D);
	}

	@OnlyIn(Dist.CLIENT)
	public static void rotateWhenOnRight(com.mojang.blaze3d.matrix.MatrixStack stack) {
		if (Minecraft.getInstance().gameSettings.mainHand == HandSide.RIGHT) stack.rotate(Vector3f.YN.rotationDegrees(180));
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
