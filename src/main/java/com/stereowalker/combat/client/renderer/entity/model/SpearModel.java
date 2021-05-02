package com.stereowalker.combat.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Spear - Stereowalker
 * Created using Tabula 7.0.0
 */
public class SpearModel extends Model {
	public static final ResourceLocation TEXTURE_LOCATION = Combat.getInstance().location("textures/entity/projectiles/spear.png");
    public ModelRenderer handle;
    public ModelRenderer tip0;
    public ModelRenderer tip1;
    public ModelRenderer tip2;

    public SpearModel() {
    	super(RenderType::getEntitySolid);
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.tip1 = new ModelRenderer(this, 0, 0);
        this.tip1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tip1.addBox(-0.5F, -5.3F, -0.5F, 1, 1, 1, 0.0F);
        this.handle = new ModelRenderer(this, 0, 6);
        this.handle.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.handle.addBox(-0.5F, -1.3F, -0.5F, 1, 25, 1, 0.0F);
        this.tip2 = new ModelRenderer(this, 0, 2);
        this.tip2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tip2.addBox(-1.5F, -4.3F, -0.5F, 3, 3, 1, 0.0F);
        this.setRotateAngle(tip2, 0.0F, 1.5707963267948966F, 0.0F);
        this.tip0 = new ModelRenderer(this, 0, 2);
        this.tip0.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.tip0.addBox(-1.5F, -4.3F, -0.5F, 3, 3, 1, 0.0F);
    }

    @Override
    public void render(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.tip1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handle.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.tip2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.tip0.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
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
