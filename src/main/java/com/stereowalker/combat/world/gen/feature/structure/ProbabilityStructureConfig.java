package com.stereowalker.combat.world.gen.feature.structure;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.IStringSerializable;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ProbabilityStructureConfig implements IFeatureConfig {
	public static final Codec<ProbabilityStructureConfig> CODEC = RecordCodecBuilder.create((p_236543_0_) -> {
		return p_236543_0_.group(Codec.DOUBLE.fieldOf("probability").forGetter((p_236544_0_) -> {
			return p_236544_0_.probability;
		}), DimensionVariant.field_236324_c_.fieldOf("dimensionVariant").forGetter((p_236542_0_) -> {
			return p_236542_0_.dimensionVariant;
		})).apply(p_236543_0_, ProbabilityStructureConfig::new);
	});
	public final double probability;
	public final DimensionVariant dimensionVariant;

	public ProbabilityStructureConfig(double probability, DimensionVariant dimensionVariant) {
		this.probability = probability;
		this.dimensionVariant = dimensionVariant;
	}
	
	public ProbabilityStructureConfig(double probability) {
		this(probability, DimensionVariant.OVERWORLD);
	}
	
	public static enum DimensionVariant implements IStringSerializable {
		OVERWORLD("overworld"),
		ACROLEST("acrotlest");
		
		public static final Codec<DimensionVariant> field_236324_c_ = IStringSerializable.createEnumCodec(DimensionVariant::values, DimensionVariant::byName);
		private static final Map<String, DimensionVariant> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(DimensionVariant::getName, (p_214716_0_) -> {
			return p_214716_0_;
		}));
		private final String name;

		private DimensionVariant(String nameIn) {
			this.name = nameIn;
		}

		public String getName() {
			return this.name;
		}

		public static DimensionVariant byName(String p_214715_0_) {
			return BY_NAME.get(p_214715_0_);
		}

		public static DimensionVariant byId(int id) {
			return id >= 0 && id < values().length ? values()[id] : OVERWORLD;
		}

		@Override
		public String getString() {
			return this.name;
		}
	}
}