package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.entity.item.BlackHoleEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Black Hole - Stereowalker
 * Created using Tabula 7.0.0
 */
public class BlackHoleModel extends EntityModel<BlackHoleEntity> {
    public ModelRenderer main_body;
    public ModelRenderer fan0;
    public ModelRenderer fan1;
    public ModelRenderer fan2;
    public ModelRenderer fan3;
    public ModelRenderer fan4;
    public ModelRenderer fan5;
    public ModelRenderer fan6;
    public ModelRenderer fan7;
    public ModelRenderer fan8;
    public ModelRenderer fan9;
    public ModelRenderer fan10;
    public ModelRenderer fan11;

    public BlackHoleModel() {
    	this.textureWidth = 128;
        this.textureHeight = 16;
        this.fan8 = new ModelRenderer(this, -4, 0);
        this.fan8.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan8.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan8, 2.530727415391778F, 0.5759586531581287F, -1.5707963267948966F);
        this.fan2 = new ModelRenderer(this, -4, 0);
        this.fan2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan2.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan2, -2.3387411976724017F, -0.5759586531581287F, -1.8151424220741026F);
        this.fan0 = new ModelRenderer(this, -4, 0);
        this.fan0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan0.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.fan10 = new ModelRenderer(this, -4, 0);
        this.fan10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan10.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan10, 1.5533430342749532F, -0.8028514559173915F, 0.05235987755982988F);
        this.fan1 = new ModelRenderer(this, -4, 0);
        this.fan1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan1.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan1, 1.4486232791552935F, 0.47123889803846897F, -0.6457718232379019F);
        this.main_body = new ModelRenderer(this, 0, 4);
        this.main_body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.main_body.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.fan6 = new ModelRenderer(this, -4, 0);
        this.fan6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan6.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan6, 0.5235987755982988F, -3.089232776029963F, -1.3788101090755203F);
        this.fan5 = new ModelRenderer(this, -4, 0);
        this.fan5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan5.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan5, 0.10471975511965977F, 2.7576202181510405F, 1.4311699866353502F);
        this.fan11 = new ModelRenderer(this, -4, 0);
        this.fan11.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan11.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan11, -2.1467549799530254F, -2.6878070480712672F, 1.48352986419518F);
        this.fan9 = new ModelRenderer(this, -4, 0);
        this.fan9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan9.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan9, -0.5759586531581287F, 0.0F, -1.9198621771937625F);
        this.fan4 = new ModelRenderer(this, -4, 0);
        this.fan4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan4.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan4, 1.5009831567151235F, -2.897246558310587F, -2.1467549799530254F);
        this.fan3 = new ModelRenderer(this, -4, 0);
        this.fan3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan3.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan3, -2.0943951023931953F, -2.6354471705114375F, 1.2915436464758039F);
        this.fan7 = new ModelRenderer(this, -4, 0);
        this.fan7.mirror = true;
        this.fan7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.fan7.addBox(-32.0F, 0.0F, -2.0F, 64, 0, 4, 0.0F);
        this.setRotateAngle(fan7, -2.548180707911721F, -1.0297442586766543F, -2.426007660272118F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.fan8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan0.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.main_body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan11.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.fan7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

	@Override
	public void setRotationAngles(BlackHoleEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
	}
}
