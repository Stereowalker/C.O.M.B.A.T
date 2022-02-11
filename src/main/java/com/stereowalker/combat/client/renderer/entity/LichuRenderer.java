package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.LichuModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.monster.Lichu;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LichuRenderer extends MobRenderer<Lichu, LichuModel<Lichu>> {
	private static final ResourceLocation LICHU_TEXTURES = Combat.getInstance().location("textures/entity/lichu.png");

	public LichuRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_, new LichuModel<>(p_173964_.bakeLayer(CModelLayers.LICHU)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(Lichu entity) {
		return LICHU_TEXTURES;
	}
}
