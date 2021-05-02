package com.stereowalker.combat.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VampireModel<T extends Entity> extends VillagerModel<T> {

	public VampireModel(float scale) {
		super(scale, 64, 128);
	}
}