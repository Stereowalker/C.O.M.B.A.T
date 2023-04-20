package com.stereowalker.rankup.world.stat;

import com.stereowalker.rankup.api.stat.StatType;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class VitalityStat extends StatType {

	public VitalityStat() {
	}

	@Override
	public void init(LivingEntity entity, Attribute attribute, double baseValue, double points) {
		if (!entity.level.isClientSide) {
			if (attribute == Attributes.MAX_HEALTH) {
				entity.setHealth((float) baseValue);
			}
		}
		super.init(entity, attribute, baseValue, points);
	}

}

