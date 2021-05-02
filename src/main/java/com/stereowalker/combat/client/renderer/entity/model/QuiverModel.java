package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;



/**
 * ModelQuiver - Stereowalker
 * Created using Tabula 7.0.0
 */
public class QuiverModel extends Model {

	public static final ResourceLocation TEXTURE_LOCATION = Combat.getInstance().location("textures/entity/quiver.png");
	public static final ResourceLocation OVERLAY_LOCATION = Combat.getInstance().location("textures/entity/quiver_overlay.png");
	public ModelRenderer quiverBody;
    public ModelRenderer backStrap;
    public ModelRenderer topStrap;
    public ModelRenderer frontStrap;
    public ModelRenderer bottomStrap;
    public ModelRenderer arrowx1;
    public ModelRenderer arrowz1;
    public ModelRenderer arrowx2;
    public ModelRenderer arrowz2;
    public ModelRenderer arrowx3;
    public ModelRenderer arrowz3;
    public ModelRenderer arrowx4;
    public ModelRenderer arrowz4;
    public ModelRenderer arrowx5;
    public ModelRenderer arrowz5;
    public ModelRenderer arrowx6;
    public ModelRenderer arrowz6;
    public ModelRenderer arrowx7;
    public ModelRenderer arrowz7;
    public ModelRenderer arrowx8;
    public ModelRenderer arrowz8;
    public ModelRenderer arrowx9;
    public ModelRenderer arrowz9;
    public boolean showArrow1;
    public boolean showArrow2;
    public boolean showArrow3;
    public boolean showArrow4;
    public boolean showArrow5;
    public boolean showArrow6;
    public boolean showArrow7;
    public boolean showArrow8;
    public boolean showArrow9;

    public QuiverModel() {
    	super(RenderType::getEntityCutout);
    	this.textureWidth = 32;
        this.textureHeight = 32;
        this.topStrap = new ModelRenderer(this, 0, 17);
        this.topStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.topStrap.addBox(-3.5F, -3.2F, -2.5F, 1, 1, 5, 0.0F);
        this.setRotateAngle(topStrap, 0.0F, 0.0F, -0.7853981633974483F);
        this.arrowx7 = new ModelRenderer(this, 19, 0);
        this.arrowx7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx7.addBox(-6.900000000000003F, -8.099999999999996F, 14.899999999999991F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx7, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowx8 = new ModelRenderer(this, 19, 0);
        this.arrowx8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx8.addBox(-9.300000000000008F, -8.499999999999995F, 14.499999999999993F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx8, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowx4 = new ModelRenderer(this, 19, 0);
        this.arrowx4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx4.addBox(-6.900000000000003F, -7.499999999999997F, 12.200000000000012F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx4, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowz1 = new ModelRenderer(this, 19, -5);
        this.arrowz1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz1.addBox(-4.300000000000004F, -8.200000000000001F, 7.700000000000007F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz1, 0.0F, 0.0F, -0.47123889803846897F);
        this.bottomStrap = new ModelRenderer(this, 0, 17);
        this.bottomStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bottomStrap.addBox(-3.5F, 8.8F, -2.5F, 1, 1, 5, 0.0F);
        this.setRotateAngle(bottomStrap, 0.0F, 0.0F, -0.7853981633974483F);
        this.arrowz2 = new ModelRenderer(this, 19, -5);
        this.arrowz2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz2.addBox(-6.400000000000007F, -7.6000000000000005F, 7.900000000000007F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz2, 0.0F, 0.0F, -0.47123889803846897F);
        this.arrowz7 = new ModelRenderer(this, 19, -5);
        this.arrowz7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz7.addBox(-4.500000000000003F, -8.099999999999996F, 12.400000000000013F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz7, 0.0F, 0.0F, -0.47123889803846897F);
        this.backStrap = new ModelRenderer(this, 0, 17);
        this.backStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backStrap.addBox(-3.5F, -3.2F, 1.5F, 1, 13, 1, 0.0F);
        this.setRotateAngle(backStrap, 0.0F, 0.0F, -0.7853981633974483F);
        this.quiverBody = new ModelRenderer(this, 0, 0);
        this.quiverBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.quiverBody.addBox(-4.3F, -0.5F, 2.4F, 4, 9, 4, 0.0F);
        this.setRotateAngle(quiverBody, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowz3 = new ModelRenderer(this, 19, -5);
        this.arrowz3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz3.addBox(-8.900000000000011F, -7.999999999999998F, 8.000000000000007F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz3, 0.0F, 0.0F, -0.47123889803846897F);
        this.frontStrap = new ModelRenderer(this, 0, 17);
        this.frontStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontStrap.addBox(-3.5F, -3.2F, -2.5F, 1, 13, 1, 0.0F);
        this.setRotateAngle(frontStrap, 0.0F, 0.0F, -0.7853981633974483F);
        this.arrowz5 = new ModelRenderer(this, 19, -5);
        this.arrowz5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz5.addBox(-7.2000000000000055F, -8.099999999999996F, 9.500000000000012F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz5, 0.0F, 0.0F, -0.47123889803846897F);
        this.arrowx3 = new ModelRenderer(this, 19, 0);
        this.arrowx3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx3.addBox(-11.300000000000011F, -7.999999999999998F, 10.500000000000007F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx3, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowx1 = new ModelRenderer(this, 19, 0);
        this.arrowx1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx1.addBox(-6.700000000000005F, -8.200000000000001F, 10.200000000000008F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx1, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowx9 = new ModelRenderer(this, 19, 0);
        this.arrowx9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx9.addBox(-11.200000000000012F, -8.599999999999996F, 14.899999999999995F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx9, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowz4 = new ModelRenderer(this, 19, -5);
        this.arrowz4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz4.addBox(-4.500000000000003F, -7.499999999999997F, 9.700000000000012F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz4, 0.0F, 0.0F, -0.47123889803846897F);
        this.arrowx5 = new ModelRenderer(this, 19, 0);
        this.arrowx5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx5.addBox(-9.600000000000007F, -8.099999999999996F, 12.000000000000012F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx5, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowz8 = new ModelRenderer(this, 19, -5);
        this.arrowz8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz8.addBox(-6.900000000000006F, -8.499999999999995F, 12.000000000000014F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz8, 0.0F, 0.0F, -0.47123889803846897F);
        this.arrowx2 = new ModelRenderer(this, 19, 0);
        this.arrowx2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx2.addBox(-8.80000000000001F, -7.6000000000000005F, 10.400000000000007F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx2, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowx6 = new ModelRenderer(this, 19, 0);
        this.arrowx6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowx6.addBox(-11.400000000000006F, -8.199999999999996F, 12.200000000000012F, 5, 7, 0, 0.0F);
        this.setRotateAngle(arrowx6, 0.0F, 0.0F, -0.5235987755982988F);
        this.arrowz6 = new ModelRenderer(this, 19, -5);
        this.arrowz6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz6.addBox(-9.000000000000005F, -8.199999999999996F, 9.700000000000017F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz6, 0.0F, 0.0F, -0.47123889803846897F);
        this.arrowz9 = new ModelRenderer(this, 19, -5);
        this.arrowz9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arrowz9.addBox(-8.800000000000011F, -8.599999999999996F, 12.400000000000013F, 0, 7, 5, 0.0F);
        this.setRotateAngle(arrowz9, 0.0F, 0.0F, -0.47123889803846897F);
        this.showArrow1 = true;
        this.showArrow2 = true;
        this.showArrow3 = true;
        this.showArrow4 = true;
        this.showArrow5 = true;
        this.showArrow6 = true;
        this.showArrow7 = true;
        this.showArrow8 = true;
        this.showArrow9 = true;
    }

    @Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    	float arrowScale = 0.35F;
    	matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow4) this.arrowx4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        this.frontStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow6) this.arrowz6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow9) this.arrowx9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow8) this.arrowz8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        this.quiverBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow5) this.arrowz5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow4) this.arrowz4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow6) this.arrowx6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow2) this.arrowx2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow9) this.arrowz9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        this.topStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow7) this.arrowx7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow2) this.arrowz2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow3) this.arrowx3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow3) this.arrowz3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        this.bottomStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow1) this.arrowx1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow1) this.arrowz1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow8) this.arrowx8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow5) this.arrowx5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        matrixStackIn.push();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow7) this.arrowz7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
        this.backStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.quiverBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
