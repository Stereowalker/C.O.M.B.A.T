package com.stereowalker.combat.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stereowalker.combat.Combat;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

/**
 * Spear - Stereowalker
 * Created using Tabula 7.0.0
 */
public class SpearModel extends Model {
    public ModelPart handle;
    public ModelPart tip0;
    public ModelPart tip1;
    public ModelPart tip2;

    public SpearModel(ModelPart root) {
    	super(RenderType::entitySolid);
		this.handle = root.getChild("handle");
		this.tip0 = root.getChild("tip0");
		this.tip1 = root.getChild("tip1");
		this.tip2 = root.getChild("tip2");
        this.setRotateAngle(tip2, 0.0F, 1.5707963267948966F, 0.0F);
    }
    
    public static LayerDefinition createLayer() {
    	MeshDefinition meshdefinition = new MeshDefinition();
    	PartDefinition partdefinition = meshdefinition.getRoot();
    	partdefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, -1.3F, -0.5F, 1, 25, 1, new CubeDeformation(0.0F)), PartPose.ZERO);
    	partdefinition.addOrReplaceChild("tip0", CubeListBuilder.create().texOffs(0, 2).addBox(-1.5F, -4.3F, -0.5F, 3, 3, 1, new CubeDeformation(0.0F)), PartPose.ZERO);
    	partdefinition.addOrReplaceChild("tip1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -5.3F, -0.5F, 1, 1, 1, new CubeDeformation(0.0F)), PartPose.ZERO);
    	partdefinition.addOrReplaceChild("tip2", CubeListBuilder.create().texOffs(0, 2).addBox(-1.5F, -4.3F, -0.5F, 3, 3, 1, new CubeDeformation(0.0F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack p_225598_1_, VertexConsumer p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.tip1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handle.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.tip2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.tip0.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
