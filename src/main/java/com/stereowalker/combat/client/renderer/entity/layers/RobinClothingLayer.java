package com.stereowalker.combat.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.RobinModel;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobinClothingLayer <T extends MobEntity & IRangedAttackMob, M extends EntityModel<T>> extends LayerRenderer<T, M> {
	private static final ResourceLocation ROBIN_CLOTHES_TEXTURES = Combat.getInstance().location("textures/entity/arch_robin/arch_robin_overlay.png");
	private final RobinModel<T> layerModel = new RobinModel<>(0.25F, true);

	public RobinClothingLayer(IEntityRenderer<T, M> p_i50919_1_) {
		super(p_i50919_1_);
	}

	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
	      renderCopyCutoutModel(this.getEntityModel(), this.layerModel, ROBIN_CLOTHES_TEXTURES, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1.0F, 1.0F, 1.0F);
	   }
}