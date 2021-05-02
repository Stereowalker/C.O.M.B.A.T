package com.stereowalker.combat.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.hooks.CombatHooks;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Mixin(BipedArmorLayer.class)
public abstract class BipedArmorLayerMixin<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {

	@Shadow
	private final A modelLeggings;
	@Shadow
	private final A modelArmor;

	
	public BipedArmorLayerMixin(IEntityRenderer<T, M> p_i50936_1_, A p_i50936_2_, A p_i50936_3_) {
		super(p_i50936_1_);
		this.modelLeggings = p_i50936_2_;
		this.modelArmor = p_i50936_3_;
	}

	@Overwrite
	private void func_241739_a_(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, T p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, A p_241739_6_) {
		ItemStack itemstack = p_241739_3_.getItemStackFromSlot(p_241739_4_);
		if (itemstack.getItem() instanceof ArmorItem) {
			ArmorItem armoritem = (ArmorItem)itemstack.getItem();
			if (armoritem.getEquipmentSlot() == p_241739_4_) {
				p_241739_6_ = getArmorModelHook(p_241739_3_, itemstack, p_241739_4_, p_241739_6_);
				this.getEntityModel().setModelAttributes(p_241739_6_);
				this.setModelSlotVisible(p_241739_6_, p_241739_4_);
				boolean flag = this.isLegSlot(p_241739_4_);
				boolean flag1 = itemstack.hasEffect();
				if (armoritem instanceof net.minecraft.item.IDyeableArmorItem) {
					int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
					float f = (float)(i >> 16 & 255) / 255.0F;
					float f1 = (float)(i >> 8 & 255) / 255.0F;
					float f2 = (float)(i & 255) / 255.0F;
					this.OverwriteGlint(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, f, f1, f2, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null), itemstack);
					this.OverwriteGlint(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, "overlay"), itemstack);
				} else {
					this.OverwriteGlint(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null), itemstack);
				}

			}
		}
	}


	private void OverwriteGlint(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource, ItemStack stack) {
		IVertexBuilder ivertexbuilder = CombatHooks.getArmorVertexBuilder(p_241738_2_, RenderType.getArmorCutoutNoCull(armorResource), false, p_241738_5_, stack);
		p_241738_6_.render(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
	}

	@Shadow
	protected void setModelSlotVisible(A modelIn, EquipmentSlotType slotIn) {}

	@Shadow
	private boolean isLegSlot(EquipmentSlotType slotIn) {return false;}

	@Shadow
	protected A getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlotType slot, A model) {return null;}

	@Shadow
	public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @Nullable String type) {return null;}
}
