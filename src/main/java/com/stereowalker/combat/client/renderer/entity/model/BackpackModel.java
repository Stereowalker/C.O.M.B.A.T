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
public class BackpackModel extends Model {

	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Combat.getInstance().getModid(), "textures/entity/backpack.png");
	public static final ResourceLocation OVERLAY_LOCATION = new ResourceLocation(Combat.getInstance().getModid(), "textures/entity/backpack_overlay.png");
	public ModelRenderer backpackBody;
    public ModelRenderer rightTopStrap;
    public ModelRenderer rightFrontStrap;
    public ModelRenderer rightBottomStrap;
    public ModelRenderer leftTopStrap;
    public ModelRenderer leftFrontStrap;
    public ModelRenderer leftBottomStrap;
    public ModelRenderer buckle;

    public BackpackModel() {
    	super(RenderType::getEntityCutout);
    	this.textureWidth = 32;
        this.textureHeight = 32;
        this.rightFrontStrap = new ModelRenderer(this, 0, 17);
        this.rightFrontStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightFrontStrap.addBox(-4.0F, -0.5F, -2.5F, 1, 9, 1, 0.0F);
        this.rightBottomStrap = new ModelRenderer(this, 0, 17);
        this.rightBottomStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightBottomStrap.addBox(-4.0F, 7.5F, -2.5F, 1, 1, 5, 0.0F);
        this.buckle = new ModelRenderer(this, 20, 18);
        this.buckle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.buckle.addBox(-1.0F, 1.5F, 6.0F, 2, 3, 1, 0.0F);
        this.leftFrontStrap = new ModelRenderer(this, 0, 17);
        this.leftFrontStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftFrontStrap.addBox(3.0F, -0.5F, -2.5F, 1, 9, 1, 0.0F);
        this.leftTopStrap = new ModelRenderer(this, 0, 17);
        this.leftTopStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftTopStrap.addBox(3.0F, -0.5F, -2.5F, 1, 1, 5, 0.0F);
        this.rightTopStrap = new ModelRenderer(this, 0, 17);
        this.rightTopStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightTopStrap.addBox(-4.0F, -0.5F, -2.5F, 1, 1, 5, 0.0F);
        this.backpackBody = new ModelRenderer(this, 0, 0);
        this.backpackBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backpackBody.addBox(-4.0F, -0.5F, 2.4F, 8, 9, 4, 0.0F);
        this.leftBottomStrap = new ModelRenderer(this, 0, 17);
        this.leftBottomStrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftBottomStrap.addBox(3.0F, 7.5F, -2.5F, 1, 1, 5, 0.0F);
    }

    @Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    	this.rightFrontStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightBottomStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.buckle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftFrontStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftTopStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightTopStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.backpackBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftBottomStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.backpackBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
