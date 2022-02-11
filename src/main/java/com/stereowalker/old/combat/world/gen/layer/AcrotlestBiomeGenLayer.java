package com.stereowalker.old.combat.world.gen.layer;
//package com.stereowalker.combat.world.gen.layer;
//
//import java.util.ArrayList;
//
//import com.google.common.collect.ImmutableList;
//import com.stereowalker.combat.world.biome.CBiomes;
//import com.stereowalker.combat.world.gen.AcrotlestGenSettings;
//
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.WorldType;
//import net.minecraft.world.gen.INoiseRandom;
//import net.minecraft.world.gen.layer.traits.IC0Transformer;
//import net.minecraftforge.common.BiomeManager;
//import net.minecraftforge.common.BiomeManager.BiomeEntry;
//import net.minecraftforge.common.BiomeManager.BiomeType;
//
//public class AcrotlestBiomeGenLayer implements IC0Transformer {
//	public static ImmutableList<BiomeManager.BiomeEntry> getBiomesToGeneration() {
//		ArrayList<BiomeManager.BiomeEntry> list = new ArrayList<>();
//		list.add(new BiomeManager.BiomeEntry(CBiomes.ACROTLEST_FOREST, 50));
//		list.add(new BiomeManager.BiomeEntry(CBiomes.HISOV_SANDS, 25));
//		list.add(new BiomeManager.BiomeEntry(CBiomes.ACROTLEST_MOUNTAINS, 100));
//		return ImmutableList.copyOf(list);
//	}
//	@SuppressWarnings("unchecked")
//	private java.util.List<BiomeManager.BiomeEntry>[] biomes = new java.util.ArrayList[BiomeManager.BiomeType.values().length];
//	private final AcrotlestGenSettings settings;
//
//	public AcrotlestBiomeGenLayer(WorldType p_i48641_1_, AcrotlestGenSettings p_i48641_2_) {
//		for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
//			ImmutableList<BiomeManager.BiomeEntry> biomesToAdd = getBiomesToGeneration();
//			int idx = type.ordinal();
//
//			if (biomes[idx] == null) biomes[idx] = new java.util.ArrayList<net.minecraftforge.common.BiomeManager.BiomeEntry>();
//			if (biomesToAdd != null) biomes[idx].addAll(biomesToAdd);
//		}
//
//		if (p_i48641_1_ == WorldType.DEFAULT_1_1) {
//			this.settings = null;
//		} else {
//			this.settings = p_i48641_2_;
//		}
//
//	}
//
//	@SuppressWarnings("deprecation")
//	public int apply(INoiseRandom context, int value) {
//		if (this.settings != null && this.settings.getBiomeId() >= 0) {
//			return this.settings.getBiomeId();
//		} else {
//			int i = (value & 3840) >> 8;
//			value = value & -3841;
//			//			         if (!LayerUtil.isOcean(value) && value != MUSHROOM_FIELDS) {
//			//            switch(value) {
//			//            case 1:
//			//               if (i > 0) {
//			//                  return context.random(3) == 0 ? BADLANDS_PLATEAU : WOODED_BADLANDS_PLATEAU;
//			//               }
//			//
//			//               return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT, context).biome);
//			//            case 2:
//			//               if (i > 0) {
//			//                  return JUNGLE;
//			//               }
//			//
//			//               return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM, context).biome);
//			//            case 3:
//			//               if (i > 0) {
//			//                  return GIANT_TREE_TAIGA;
//			//               }
//			//
//			return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL, context).biome);
//			//            case 4:
//			//               return Registry.BIOME.getId(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY, context).biome);
//			//            default:
//			//               return MUSHROOM_FIELDS;
//			//            }
//			//         } else {
//			//            return value;
//			//         }
//		}
//	}
//
//	protected BiomeEntry getWeightedBiomeEntry(BiomeType type, INoiseRandom context) {
//		java.util.List<net.minecraftforge.common.BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
//		int totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(biomeList);
//		int weight = net.minecraftforge.common.BiomeManager.isTypeListModded(type)?context.random(totalWeight):context.random(totalWeight / 10) * 10;
//		return (net.minecraftforge.common.BiomeManager.BiomeEntry)net.minecraft.util.WeightedRandom.getRandomItem(biomeList, weight);
//	}
//}