package com.stereowalker.combat.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.RobinModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobinClothingLayer <T extends Mob & RangedAttackMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation ROBIN_CLOTHES_TEXTURES = Combat.getInstance().location("textures/entity/arch_robin/arch_robin_overlay.png");
	private final RobinModel<T> layerModel;

	public RobinClothingLayer(RenderLayerParent<T, M> p_i50919_1_, EntityModelSet p_174545_) {
		super(p_i50919_1_);
		this.layerModel = new RobinModel<>(p_174545_.bakeLayer(CModelLayers.ROBIN));
	}

	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.layerModel, ROBIN_CLOTHES_TEXTURES, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
	}
}