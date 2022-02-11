package com.stereowalker.combat.client.model;
//Made with Blockbench

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stereowalker.combat.world.entity.monster.Biog;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiogModel<T extends Biog> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart pincer1;
	private final ModelPart pincer2;
	private final ModelPart frontLeftLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart backRightLeg;

	public BiogModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.body = root.getChild("body");
		this.neck = body.getChild("neck");
		this.head = neck.getChild("head");
		this.pincer1 = head.getChild("pincer1");
		this.pincer2 = head.getChild("pincer2");
		this.frontLeftLeg = body.getChild("frontLeftLeg");
		this.frontRightLeg = body.getChild("frontRightLeg");
		this.backLeftLeg = body.getChild("backLeftLeg");
		this.backRightLeg = body.getChild("backRightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(31, 9).addBox(-1.0F, -9.9043F, -2.5683F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(9, 0).addBox(-2.0F, -3.5F, -2.8333F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.4043F, -0.735F, -0.3491F, 0.0F, 0.0F));

		PartDefinition pincer1 = head.addOrReplaceChild("pincer1", CubeListBuilder.create().texOffs(24, 5).addBox(-7.5F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 12).addBox(-8.5F, -0.5F, -7.5F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(7, 23).addBox(-8.0F, 0.0F, -7.0F, 8.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0F, -2.3333F, 0.0F, -0.6109F, 0.0F));

		PartDefinition pincer2 = head.addOrReplaceChild("pincer2", CubeListBuilder.create().texOffs(24, 5).addBox(-7.5F, -0.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 12).addBox(-8.5F, -0.5F, -0.5F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(7, 31).addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0F, -2.3333F, 0.0F, -2.7053F, 0.0F));

		PartDefinition frontLeftLeg = body.addOrReplaceChild("frontLeftLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0017F, -0.9983F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, -4.0F));

		PartDefinition frontRightLeg = body.addOrReplaceChild("frontRightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.0017F, -1.0017F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.0F, -4.0F));

		PartDefinition backLeftLeg = body.addOrReplaceChild("backLeftLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0017F, 0.0035F, -0.9983F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, 3.0F));

		PartDefinition backRightLeg = body.addOrReplaceChild("backRightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0017F, 0.0F, -1.0017F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
	
	
	
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.backRightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.backLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.frontRightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.frontLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		if (entityIn.isAggressive()) {
			this.pincer1.yRot = /*-0.9599F*/-0.4363F + ((Mth.cos(limbSwing * 0.6662F)/2)+0.5F)*-0.5236F;
			this.pincer2.yRot = /*-2.1817F*/-2.7053F + ((Mth.cos(limbSwing * 0.6662F)/2)+0.5F)*0.5236F;
		}
		else {
			this.pincer1.yRot = -0.4363F;
			this.pincer2.yRot = -2.7053F;
		}
	}
}