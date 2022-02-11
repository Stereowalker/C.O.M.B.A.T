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
public class QuiverModel extends Model {

	public static final ResourceLocation TEXTURE_LOCATION = Combat.getInstance().location("textures/entity/quiver.png");
	public static final ResourceLocation OVERLAY_LOCATION = Combat.getInstance().location("textures/entity/quiver_overlay.png");	private final ModelPart quiverBody;
	private final ModelPart backStrap;
	private final ModelPart topStrap;
	private final ModelPart frontStrap;
	private final ModelPart bottomStrap;
	private final ModelPart arrowx1;
	private final ModelPart arrowz1;
	private final ModelPart arrowx2;
	private final ModelPart arrowz2;
	private final ModelPart arrowx3;
	private final ModelPart arrowz3;
	private final ModelPart arrowx4;
	private final ModelPart arrowz4;
	private final ModelPart arrowx5;
	private final ModelPart arrowz5;
	private final ModelPart arrowx6;
	private final ModelPart arrowz6;
	private final ModelPart arrowx7;
	private final ModelPart arrowz7;
	private final ModelPart arrowx8;
	private final ModelPart arrowz8;
	private final ModelPart arrowx9;
	private final ModelPart arrowz9;
    public boolean showArrow1;
    public boolean showArrow2;
    public boolean showArrow3;
    public boolean showArrow4;
    public boolean showArrow5;
    public boolean showArrow6;
    public boolean showArrow7;
    public boolean showArrow8;
    public boolean showArrow9;

	public QuiverModel(ModelPart root) {
    	super(RenderType::entityCutout);
		this.quiverBody = root.getChild("quiverBody");
		this.backStrap = root.getChild("backStrap");
		this.topStrap = root.getChild("topStrap");
		this.frontStrap = root.getChild("frontStrap");
		this.bottomStrap = root.getChild("bottomStrap");
		this.arrowx1 = root.getChild("arrowx1");
		this.arrowz1 = root.getChild("arrowz1");
		this.arrowx2 = root.getChild("arrowx2");
		this.arrowz2 = root.getChild("arrowz2");
		this.arrowx3 = root.getChild("arrowx3");
		this.arrowz3 = root.getChild("arrowz3");
		this.arrowx4 = root.getChild("arrowx4");
		this.arrowz4 = root.getChild("arrowz4");
		this.arrowx5 = root.getChild("arrowx5");
		this.arrowz5 = root.getChild("arrowz5");
		this.arrowx6 = root.getChild("arrowx6");
		this.arrowz6 = root.getChild("arrowz6");
		this.arrowx7 = root.getChild("arrowx7");
		this.arrowz7 = root.getChild("arrowz7");
		this.arrowx8 = root.getChild("arrowx8");
		this.arrowz8 = root.getChild("arrowz8");
		this.arrowx9 = root.getChild("arrowx9");
		this.arrowz9 = root.getChild("arrowz9");
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

	@SuppressWarnings("unused")
	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition quiverBody = partdefinition.addOrReplaceChild("quiverBody", CubeListBuilder.create().texOffs(0, 0).addBox(-4.3F, -0.5F, 2.4F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition backStrap = partdefinition.addOrReplaceChild("backStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, -3.2F, 1.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition topStrap = partdefinition.addOrReplaceChild("topStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, -3.2F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition frontStrap = partdefinition.addOrReplaceChild("frontStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, -3.2F, -2.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bottomStrap = partdefinition.addOrReplaceChild("bottomStrap", CubeListBuilder.create().texOffs(0, 17).addBox(-3.5F, 8.8F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition arrowx1 = partdefinition.addOrReplaceChild("arrowx1", CubeListBuilder.create().texOffs(19, 0).addBox(-6.7F, -8.2F, 10.2F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz1 = partdefinition.addOrReplaceChild("arrowz1", CubeListBuilder.create().texOffs(19, -5).addBox(-4.3F, -8.2F, 7.7F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx2 = partdefinition.addOrReplaceChild("arrowx2", CubeListBuilder.create().texOffs(19, 0).addBox(-8.8F, -7.6F, 10.4F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz2 = partdefinition.addOrReplaceChild("arrowz2", CubeListBuilder.create().texOffs(19, -5).addBox(-6.4F, -7.6F, 7.9F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx3 = partdefinition.addOrReplaceChild("arrowx3", CubeListBuilder.create().texOffs(19, 0).addBox(-11.3F, -8.0F, 10.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz3 = partdefinition.addOrReplaceChild("arrowz3", CubeListBuilder.create().texOffs(19, -5).addBox(-8.9F, -8.0F, 8.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx4 = partdefinition.addOrReplaceChild("arrowx4", CubeListBuilder.create().texOffs(19, 0).addBox(-6.9F, -7.5F, 12.2F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz4 = partdefinition.addOrReplaceChild("arrowz4", CubeListBuilder.create().texOffs(19, -5).addBox(-4.5F, -7.5F, 9.7F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx5 = partdefinition.addOrReplaceChild("arrowx5", CubeListBuilder.create().texOffs(19, 0).addBox(-9.6F, -8.1F, 12.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz5 = partdefinition.addOrReplaceChild("arrowz5", CubeListBuilder.create().texOffs(19, -5).addBox(-7.2F, -8.1F, 9.5F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx6 = partdefinition.addOrReplaceChild("arrowx6", CubeListBuilder.create().texOffs(19, 0).addBox(-11.4F, -8.2F, 12.2F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz6 = partdefinition.addOrReplaceChild("arrowz6", CubeListBuilder.create().texOffs(19, -5).addBox(-9.0F, -8.2F, 9.7F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx7 = partdefinition.addOrReplaceChild("arrowx7", CubeListBuilder.create().texOffs(19, 0).addBox(-6.9F, -8.1F, 14.9F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz7 = partdefinition.addOrReplaceChild("arrowz7", CubeListBuilder.create().texOffs(19, -5).addBox(-4.5F, -8.1F, 12.4F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx8 = partdefinition.addOrReplaceChild("arrowx8", CubeListBuilder.create().texOffs(19, 0).addBox(-9.3F, -8.5F, 14.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz8 = partdefinition.addOrReplaceChild("arrowz8", CubeListBuilder.create().texOffs(19, -5).addBox(-6.9F, -8.5F, 12.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		PartDefinition arrowx9 = partdefinition.addOrReplaceChild("arrowx9", CubeListBuilder.create().texOffs(19, 0).addBox(-11.2F, -8.6F, 14.9F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition arrowz9 = partdefinition.addOrReplaceChild("arrowz9", CubeListBuilder.create().texOffs(19, -5).addBox(-8.8F, -8.6F, 12.4F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4712F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

    @Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    	float arrowScale = 0.35F;
    	matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow4) this.arrowx4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        this.frontStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow6) this.arrowz6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow9) this.arrowx9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow8) this.arrowz8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        this.quiverBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow5) this.arrowz5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow4) this.arrowz4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow6) this.arrowx6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow2) this.arrowx2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow9) this.arrowz9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        this.topStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow7) this.arrowx7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow2) this.arrowz2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow3) this.arrowx3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow3) this.arrowz3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        this.bottomStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow1) this.arrowx1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow1) this.arrowz1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow8) this.arrowx8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow5) this.arrowx5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        matrixStackIn.pushPose();
        matrixStackIn.scale(arrowScale, arrowScale, arrowScale);
        if (showArrow7) this.arrowz7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.popPose();
        this.backStrap.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		this.quiverBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}
