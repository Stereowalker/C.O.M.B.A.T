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
 * ModelQuiver - Stereowalker
 * Created using Tabula 7.0.0
 */
public class BackpackModel extends Model {

	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Combat.getInstance().getModid(), "textures/entity/backpack.png");
	public static final ResourceLocation OVERLAY_LOCATION = new ResourceLocation(Combat.getInstance().getModid(), "textures/entity/backpack_overlay.png");
	private final ModelPart backpackBody;
	private final ModelPart rightTopStrap;
	private final ModelPart rightFrontStrap;
	private final ModelPart rightBottomStrap;
	private final ModelPart leftTopStrap;
	private final ModelPart leftFrontStrap;
	private final ModelPart leftBottomStrap;
	private final ModelPart buckle;

	public BackpackModel(ModelPart root) {
		super(RenderType::entityCutout);
		this.backpackBody = root.getChild("backpackBody");
		this.rightTopStrap = root.getChild("rightTopStrap");
		this.rightFrontStrap = root.getChild("rightFrontStrap");
		this.rightBottomStrap = root.getChild("rightBottomStrap");
		this.leftTopStrap = root.getChild("leftTopStrap");
		this.leftFrontStrap = root.getChild("leftFrontStrap");
		this.leftBottomStrap = root.getChild("leftBottomStrap");
		this.buckle = root.getChild("buckle");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition backpackBody = partdefinition.addOrReplaceChild("backpackBody", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.5F, 2.4F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightTopStrap = partdefinition.addOrReplaceChild("rightTopStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightFrontStrap = partdefinition.addOrReplaceChild("rightFrontStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -0.5F, -2.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightBottomStrap = partdefinition.addOrReplaceChild("rightBottomStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 7.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftTopStrap = partdefinition.addOrReplaceChild("leftTopStrap", CubeListBuilder.create().texOffs(0, 17).addBox(3.0F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftFrontStrap = partdefinition.addOrReplaceChild("leftFrontStrap", CubeListBuilder.create().texOffs(0, 17).addBox(3.0F, -0.5F, -2.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftBottomStrap = partdefinition.addOrReplaceChild("leftBottomStrap", CubeListBuilder.create().texOffs(0, 17).addBox(3.0F, 7.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition buckle = partdefinition.addOrReplaceChild("buckle", CubeListBuilder.create().texOffs(20, 18).addBox(-1.0F, 1.5F, 6.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

    @Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
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
}
