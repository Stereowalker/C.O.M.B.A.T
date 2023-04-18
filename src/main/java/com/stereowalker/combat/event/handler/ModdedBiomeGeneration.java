package com.stereowalker.combat.event.handler;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.data.worldgen.BiomeCombatFeatures;
import com.stereowalker.combat.tags.BiomeCTags;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class ModdedBiomeGeneration 
{
	public static Codec<ExampleBiomeModifier> EXAMPLE_CODEC = RecordCodecBuilder.create(builder -> builder.group(
			// declare fields
			Biome.LIST_CODEC.fieldOf("biomes").forGetter(ExampleBiomeModifier::biomes),
			PlacedFeature.CODEC.fieldOf("feature").forGetter(ExampleBiomeModifier::feature)
			// declare constructor
			).apply(builder, ExampleBiomeModifier::new));

	public record ExampleBiomeModifier(HolderSet<Biome> biomes, Holder<PlacedFeature> feature) implements BiomeModifier
	{
		public void modify(Holder<Biome> biome, Phase phase, BiomeInfo.Builder builder)
		{
			if (phase == Phase.ADD && biome.is(BiomeTags.IS_OVERWORLD) && biomes.contains(biome)) {
				builder.getGenerationSettings().addFeature(Decoration.UNDERGROUND_ORES, feature);
				if (biome.is(BiomeCTags.IS_WARM_OCEAN)) {
//					BiomeCombatFeatures.addLimestone(builder.getGenerationSettings());
				}
//				BiomeCombatFeatures.addPasquem(builder.getGenerationSettings(), biome.is(Tags.Biomes.IS_DEAD));
//				BiomeCombatFeatures.addTridox(builder.getGenerationSettings(), biome.is(Tags.Biomes.IS_MAGICAL));
//				BiomeCombatFeatures.addYellowMagicClusters(builder.getGenerationSettings(), biome.is(Tags.Biomes.IS_MAGICAL));
//				BiomeCombatFeatures.addCassiterite(builder.getGenerationSettings());

				if (biome.is(BiomeTags.IS_FOREST)) 
					BiomeCombatFeatures.addVampires(builder.getMobSpawnSettings());

//				if (biome.is(Tags.Biomes.IS_PLAINS) || biome.is(BiomeTags.IS_FOREST) || biome.is(Tags.Biomes.IS_DESERT) || biome.is(BiomeTags.IS_TAIGA)) 
//					BiomeCombatFeatures.addRubies(builder.getGenerationSettings());
			}
		}

		public Codec<? extends BiomeModifier> codec()
		{
			return EXAMPLE_CODEC;
		}
	}
}
