package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * ChakramModel - Stereowalker
 * Created using Tabula 7.0.0
 */
public class ChakramModel extends Model {
    public ModelRenderer side0;
    public ModelRenderer side1;
    public ModelRenderer side2;
    public ModelRenderer side3;
    public ModelRenderer corner0;
    public ModelRenderer corner1;
    public ModelRenderer corner2;
    public ModelRenderer corner3;
    public ModelRenderer edge0;
    public ModelRenderer edge1;
    public ModelRenderer edge2;
    public ModelRenderer edge3;
    public ModelRenderer edge4;
    public ModelRenderer edge5;
    public ModelRenderer edge6;
    public ModelRenderer edge7;

    public ChakramModel() {
    	super(RenderType::getEntityTranslucent);
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.corner3 = new ModelRenderer(this, 0, 3);
        this.corner3.mirror = true;
        this.corner3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.corner3.addBox(-5.5F, 0.0F, -5.5F, 2, 1, 2, 0.0F);
        this.side3 = new ModelRenderer(this, 0, 6);
        this.side3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.side3.addBox(-7.5F, 0.0F, -1.5F, 2, 1, 2, 0.0F);
        this.corner1 = new ModelRenderer(this, 8, 12);
        this.corner1.setRotationPoint(2.5F, 0.0F, 2.5F);
        this.corner1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2, 0.0F);
        this.edge2 = new ModelRenderer(this, 8, 9);
        this.edge2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge2.addBox(3.5F, 0.0F, 0.5F, 2, 1, 2, 0.0F);
        this.edge7 = new ModelRenderer(this, 0, 0);
        this.edge7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge7.addBox(-3.5F, 0.0F, -6.5F, 2, 1, 2, 0.0F);
        this.edge3 = new ModelRenderer(this, 8, 0);
        this.edge3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge3.addBox(0.5F, 0.0F, 3.5F, 2, 1, 2, 0.0F);
        this.corner0 = new ModelRenderer(this, 0, 3);
        this.corner0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.corner0.addBox(2.5F, 0.0F, -5.5F, 2, 1, 2, 0.0F);
        this.edge5 = new ModelRenderer(this, 0, 9);
        this.edge5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge5.addBox(-6.5F, 0.0F, 0.5F, 2, 1, 2, 0.0F);
        this.side2 = new ModelRenderer(this, 8, 0);
        this.side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.side2.addBox(-1.5F, 0.0F, 4.5F, 2, 1, 2, 0.0F);
        this.edge1 = new ModelRenderer(this, 8, 3);
        this.edge1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge1.addBox(3.5F, 0.0F, -3.5F, 2, 1, 2, 0.0F);
        this.edge6 = new ModelRenderer(this, 8, 3);
        this.edge6.mirror = true;
        this.edge6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge6.addBox(-6.5F, 0.0F, -3.5F, 2, 1, 2, 0.0F);
        this.side0 = new ModelRenderer(this, 0, 0);
        this.side0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.side0.addBox(-1.5F, 0.0F, -7.5F, 2, 1, 2, 0.0F);
        this.edge4 = new ModelRenderer(this, 8, 0);
        this.edge4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge4.addBox(-3.5F, 0.0F, 3.5F, 2, 1, 2, 0.0F);
        this.edge0 = new ModelRenderer(this, 0, 0);
        this.edge0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.edge0.addBox(0.5F, 0.0F, -6.5F, 2, 1, 2, 0.0F);
        this.side1 = new ModelRenderer(this, 8, 6);
        this.side1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.side1.addBox(4.5F, 0.0F, -1.5F, 2, 1, 2, 0.0F);
        this.corner2 = new ModelRenderer(this, 0, 12);
        this.corner2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.corner2.addBox(-5.5F, 0.0F, 2.5F, 2, 1, 2, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.corner3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.side3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.corner1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.corner0.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.side2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.side0.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.edge0.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.side1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.corner2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
