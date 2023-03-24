package com.stereowalker.combat.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.world.level.levelgen.feature.AcrotlestMineshaftFeature;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;


public class AcrotlestMineshaftConfiguration implements FeatureConfiguration {

	public static final Codec<AcrotlestMineshaftConfiguration> CODEC = RecordCodecBuilder.create((p_67790_) -> {
	      return p_67790_.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((p_160992_) -> {
	         return p_160992_.probability;
	      }), AcrotlestMineshaftFeature.Type.CODEC.fieldOf("type").forGetter((p_160990_) -> {
	         return p_160990_.type;
	      })).apply(p_67790_, AcrotlestMineshaftConfiguration::new);
	   });
	public final float probability;
	public final AcrotlestMineshaftFeature.Type type;

	public AcrotlestMineshaftConfiguration(float probability, AcrotlestMineshaftFeature.Type type) {
		this.probability = probability;
		this.type = type;
	}
}