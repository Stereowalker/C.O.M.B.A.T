package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.RobinModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.client.renderer.entity.layers.RobinClothingLayer;
import com.stereowalker.combat.world.entity.boss.robin.RobinBoss;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RobinRenderer extends HumanoidMobRenderer<RobinBoss, RobinModel<RobinBoss>> {
	private static final ResourceLocation SKELETON_TEXTURES = Combat.getInstance().location("textures/entity/arch_robin/arch_robin.png");

	public RobinRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_, new RobinModel<>(p_173964_.bakeLayer(CModelLayers.ROBIN)), 0.5F);
		//		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new HumanoidArmorLayer<>(this, new RobinModel<RobinBoss>(p_173964_.bakeLayer(CModelLayers.ROBIN_INNER_ARMOR)), new RobinModel<RobinBoss>(p_173964_.bakeLayer(CModelLayers.ROBIN_OUTER_ARMOR))));
		this.addLayer(new RobinClothingLayer<>(this, p_173964_.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(RobinBoss entity) {
		return SKELETON_TEXTURES;
	}
}