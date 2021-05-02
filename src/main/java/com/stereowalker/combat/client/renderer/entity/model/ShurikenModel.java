package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * ShurikenModel - Stereowalker
 * Created using Tabula 7.0.0
 */
public class ShurikenModel extends Model {
    public ModelRenderer star;

    public ShurikenModel() {
    	super(RenderType::getEntityTranslucent);
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.star = new ModelRenderer(this, 0, 0);
        this.star.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.star.addBox(-8.0F, 0.0F, -8.0F, 16, 0, 16, 0.0F);
    }

    @Override
    public void render(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        p_225598_1_.push();
//        p_225598_1_.translate(this.star.offsetX, this.star.offsetY, this.star.offsetZ);
//        p_225598_1_.translate(this.star.rotationPointX * f5, this.star.rotationPointY * f5, this.star.rotationPointZ * f5);
        p_225598_1_.scale(0.5F, 1.0F, 0.5F);
//        p_225598_1_.translate(-this.star.offsetX, -this.star.offsetY, -this.star.offsetZ);
//        p_225598_1_.translate(-this.star.rotationPointX * f5, -this.star.rotationPointY * f5, -this.star.rotationPointZ * f5);
        this.star.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        p_225598_1_.pop();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
