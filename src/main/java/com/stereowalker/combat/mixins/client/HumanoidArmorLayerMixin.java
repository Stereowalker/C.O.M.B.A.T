package com.stereowalker.combat.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stereowalker.combat.mixinhooks.CombatHooks;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

	@Shadow
	private final A innerModel;
	@Shadow
	private final A outerModel;

	
	public HumanoidArmorLayerMixin(RenderLayerParent<T, M> p_i50936_1_, A p_i50936_2_, A p_i50936_3_) {
		super(p_i50936_1_);
		this.innerModel = p_i50936_2_;
		this.outerModel = p_i50936_3_;
	}

	@Redirect(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IZLnet/minecraft/client/model/HumanoidModel;FFFLnet/minecraft/resources/ResourceLocation;)V"))
	private void overwriteGlint(HumanoidArmorLayer<?,?,?> lays, PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, boolean p_117111_, A p_117112_, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource, PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A p_117124_) {
		VertexConsumer vertexconsumer = CombatHooks.getArmorFoilBuffer(p_117108_, RenderType.armorCutoutNoCull(armorResource), false, p_117111_, p_117121_.getItemBySlot(p_117122_));
		p_117112_.renderToBuffer(p_117107_, vertexconsumer, p_117109_, OverlayTexture.NO_OVERLAY, p_117114_, p_117115_, p_117116_, 1.0F);
	}

	@Shadow
	private boolean usesInnerModel(EquipmentSlot slotIn) {return false;}
}
