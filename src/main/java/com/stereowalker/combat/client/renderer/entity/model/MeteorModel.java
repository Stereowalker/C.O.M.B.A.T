package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.entity.item.MeteorEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Meteor - Stereowalker
 * Created using Tabula 7.0.0
 */
public class MeteorModel extends EntityModel<MeteorEntity> {
    public ModelRenderer meteor;

    public MeteorModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.meteor = new ModelRenderer(this, 0, 0);
        this.meteor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.meteor.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.meteor.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

	@Override
	public void setRotationAngles(MeteorEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
	}
}
