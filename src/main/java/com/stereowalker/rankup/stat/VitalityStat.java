package com.stereowalker.rankup.stat;

import com.stereowalker.rankup.api.stat.Stat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;

public class VitalityStat extends Stat {

	public VitalityStat() {
		super("0abbb72f-760a-46ed-8ae3-57fb010a4507");
	}

	@Override
	public void init(LivingEntity entity, Attribute attribute, double baseValue, double points) {
		if (!entity.world.isRemote) {
			if (attribute == Attributes.MAX_HEALTH) {
				entity.setHealth((float) baseValue);
			}
		}
		super.init(entity, attribute, baseValue, points);
	}

}

