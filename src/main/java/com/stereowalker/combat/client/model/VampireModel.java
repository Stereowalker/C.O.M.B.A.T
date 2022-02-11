package com.stereowalker.combat.client.model;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VampireModel<T extends Entity> extends VillagerModel<T> {

	public VampireModel(ModelPart p_171051_) {
		super(p_171051_);
	}
}