package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.BiogModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.monster.Biog;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiogRenderer extends MobRenderer<Biog, BiogModel<Biog>> {
	private static final ResourceLocation BIOG_TEXTURES = Combat.getInstance().location("textures/entity/biog.png");

	public BiogRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_, new BiogModel<>(p_173964_.bakeLayer(CModelLayers.BIOG)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(Biog entity) {
		return BIOG_TEXTURES;
	}
}
