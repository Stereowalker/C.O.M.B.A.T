package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.monster.ZombieCow;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieCowRenderer extends MobRenderer<ZombieCow, CowModel<ZombieCow>> {
	private static final ResourceLocation COW_TEXTURES = Combat.getInstance().location("textures/entity/zombie_cow.png");

	public ZombieCowRenderer(EntityRendererProvider.Context p_173964_) {
		super(p_173964_, new CowModel<>(p_173964_.bakeLayer(CModelLayers.ZOMBIE_COW)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieCow entity) {
		return COW_TEXTURES;
	}
}