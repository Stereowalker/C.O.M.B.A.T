package com.stereowalker.combat.world.level.levelgen.feature.configurations;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class EtherionTowerConfiguration implements FeatureConfiguration {
	public static final Codec<EtherionTowerConfiguration> CODEC = RecordCodecBuilder.create((p_236543_0_) -> {
		return p_236543_0_.group(Codec.DOUBLE.fieldOf("probability").forGetter((p_236544_0_) -> {
			return p_236544_0_.probability;
		}), Variant.field_236324_c_.fieldOf("variant").forGetter((p_236542_0_) -> {
			return p_236542_0_.variant;
		})).apply(p_236543_0_, EtherionTowerConfiguration::new);
	});
	public final double probability;
	public final Variant variant;

	public EtherionTowerConfiguration(double probability, Variant variant) {
		this.probability = probability;
		this.variant = variant;
	}
	
	public static enum Variant implements StringRepresentable {
		BADLANDS("badlands"),
		OVERWORLD("overworld"),
		DESERT("desert"),
		ACROLEST("acrotlest");
		
		public static final Codec<Variant> field_236324_c_ = StringRepresentable.fromEnum(Variant::values, Variant::byName);
		private static final Map<String, Variant> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Variant::getName, (p_214716_0_) -> {
			return p_214716_0_;
		}));
		private final String name;

		private Variant(String nameIn) {
			this.name = nameIn;
		}

		public String getName() {
			return this.name;
		}

		public static Variant byName(String p_214715_0_) {
			return BY_NAME.get(p_214715_0_);
		}

		public static Variant byId(int id) {
			return id >= 0 && id < values().length ? values()[id] : OVERWORLD;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}