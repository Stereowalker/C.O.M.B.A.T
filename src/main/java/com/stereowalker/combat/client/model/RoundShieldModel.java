package com.stereowalker.combat.client.model;
//Made with Blockbench

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RoundShieldModel extends Model {
	private final ModelPart root;
	private final ModelPart plate;
	private final ModelPart handle;

	public RoundShieldModel(ModelPart root) {
		super(RenderType::entityCutout);
		this.root = root;
		this.plate = root.getChild("plate");
		this.handle = root.getChild("handle");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition plate = partdefinition.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition corners = plate.addOrReplaceChild("corners", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, -3.0F, -2.0F, 16.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-6.0F, -6.0F, -2.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 17).addBox(-5.0F, -7.0F, -2.0F, 10.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-7.0F, -5.0F, -2.0F, 14.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition handle = partdefinition.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(34, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public ModelPart plate() {
		return this.plate;
	}

	public ModelPart handle() {
		return this.handle;
	}

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
		this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}
}