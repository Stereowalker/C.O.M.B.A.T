package com.stereowalker.combat.world.level.newbiome.layer;

import java.util.function.LongFunction;

import com.stereowalker.combat.world.level.biome.CBiomeRegistry;
import com.stereowalker.combat.world.level.biome.CBiomes;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.LazyAreaContext;
import net.minecraft.world.level.newbiome.layer.AddDeepOceanLayer;
import net.minecraft.world.level.newbiome.layer.AddEdgeLayer;
import net.minecraft.world.level.newbiome.layer.AddIslandLayer;
import net.minecraft.world.level.newbiome.layer.AddMushroomIslandLayer;
import net.minecraft.world.level.newbiome.layer.AddSnowLayer;
import net.minecraft.world.level.newbiome.layer.BiomeEdgeLayer;
import net.minecraft.world.level.newbiome.layer.BiomeInitLayer;
import net.minecraft.world.level.newbiome.layer.IslandLayer;
import net.minecraft.world.level.newbiome.layer.Layer;
import net.minecraft.world.level.newbiome.layer.LayerBiomes;
import net.minecraft.world.level.newbiome.layer.OceanLayer;
import net.minecraft.world.level.newbiome.layer.OceanMixerLayer;
import net.minecraft.world.level.newbiome.layer.RareBiomeLargeLayer;
import net.minecraft.world.level.newbiome.layer.RareBiomeSpotLayer;
import net.minecraft.world.level.newbiome.layer.RegionHillsLayer;
import net.minecraft.world.level.newbiome.layer.RemoveTooMuchOceanLayer;
import net.minecraft.world.level.newbiome.layer.RiverInitLayer;
import net.minecraft.world.level.newbiome.layer.RiverLayer;
import net.minecraft.world.level.newbiome.layer.RiverMixerLayer;
import net.minecraft.world.level.newbiome.layer.ShoreLayer;
import net.minecraft.world.level.newbiome.layer.SmoothLayer;
import net.minecraft.world.level.newbiome.layer.ZoomLayer;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

public class AcrotlestLayers implements LayerBiomes {
   private static final Int2IntMap field_242937_a = Util.make(new Int2IntOpenHashMap(), (p_242938_0_) -> {
   });

   protected static final int WARM_ID = 1;
   protected static final int MEDIUM_ID = 2;
   protected static final int COLD_ID = 3;
   protected static final int ICE_ID = 4;
   protected static final int SPECIAL_MASK = 3840;
   protected static final int SPECIAL_SHIFT = 8;
   private static final Int2IntMap CATEGORIES = Util.make(new Int2IntOpenHashMap(), (p_76741_) -> {
	   register(p_76741_, AcrotlestLayers.Category.EXTREME_HILLS, CBiomeRegistry.getBiomeIndex(CBiomes.ACROTLEST_MOUNTAINS));
	   register(p_76741_, AcrotlestLayers.Category.FOREST, CBiomeRegistry.getBiomeIndex(CBiomes.ACROTLEST_FOREST));
	   register(p_76741_, AcrotlestLayers.Category.DESERT, CBiomeRegistry.getBiomeIndex(CBiomes.HISOV_SANDS));
	   register(p_76741_, AcrotlestLayers.Category.RIVER, CBiomeRegistry.getBiomeIndex(CBiomes.ACROTLEST_RIVER));
   });

   public static <T extends Area, C extends BigContext<T>> AreaFactory<T> zoom(long pSeed, AreaTransformer1 pParent, AreaFactory<T> pArea, int pCount, LongFunction<C> pContextFactory) {
      AreaFactory<T> areafactory = pArea;

      for(int i = 0; i < pCount; ++i) {
         areafactory = pParent.run(pContextFactory.apply(pSeed + (long)i), areafactory);
      }

      return areafactory;
   }

   private static <T extends Area, C extends BigContext<T>> AreaFactory<T> getDefaultLayer(boolean pLegacyBiomes, int pBiomeSize, int pRiverSize, LongFunction<C> pContextFactory) {
      AreaFactory<T> areafactory = IslandLayer.INSTANCE.run(pContextFactory.apply(1L));
      areafactory = ZoomLayer.FUZZY.run(pContextFactory.apply(2000L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(1L), areafactory);
      areafactory = ZoomLayer.NORMAL.run(pContextFactory.apply(2001L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(2L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(50L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(70L), areafactory);
      areafactory = RemoveTooMuchOceanLayer.INSTANCE.run(pContextFactory.apply(2L), areafactory);
      AreaFactory<T> areafactory1 = OceanLayer.INSTANCE.run(pContextFactory.apply(2L));
      areafactory1 = zoom(2001L, ZoomLayer.NORMAL, areafactory1, 6, pContextFactory);
      areafactory = AddSnowLayer.INSTANCE.run(pContextFactory.apply(2L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(3L), areafactory);
      areafactory = AddEdgeLayer.CoolWarm.INSTANCE.run(pContextFactory.apply(2L), areafactory);
      areafactory = AddEdgeLayer.HeatIce.INSTANCE.run(pContextFactory.apply(2L), areafactory);
      areafactory = AddEdgeLayer.IntroduceSpecial.INSTANCE.run(pContextFactory.apply(3L), areafactory);
      areafactory = ZoomLayer.NORMAL.run(pContextFactory.apply(2002L), areafactory);
      areafactory = ZoomLayer.NORMAL.run(pContextFactory.apply(2003L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.run(pContextFactory.apply(4L), areafactory);
      areafactory = AddMushroomIslandLayer.INSTANCE.run(pContextFactory.apply(5L), areafactory);
      areafactory = AddDeepOceanLayer.INSTANCE.run(pContextFactory.apply(4L), areafactory);
      areafactory = zoom(1000L, ZoomLayer.NORMAL, areafactory, 0, pContextFactory);
      AreaFactory<T> areafactory2 = zoom(1000L, ZoomLayer.NORMAL, areafactory, 0, pContextFactory);
      areafactory2 = RiverInitLayer.INSTANCE.run(pContextFactory.apply(100L), areafactory2);
      AreaFactory<T> areafactory3 = (new BiomeInitLayer(pLegacyBiomes)).run(pContextFactory.apply(200L), areafactory);
      areafactory3 = RareBiomeLargeLayer.INSTANCE.run(pContextFactory.apply(1001L), areafactory3);
      areafactory3 = zoom(1000L, ZoomLayer.NORMAL, areafactory3, 2, pContextFactory);
      areafactory3 = BiomeEdgeLayer.INSTANCE.run(pContextFactory.apply(1000L), areafactory3);
      AreaFactory<T> areafactory4 = zoom(1000L, ZoomLayer.NORMAL, areafactory2, 2, pContextFactory);
      areafactory3 = RegionHillsLayer.INSTANCE.run(pContextFactory.apply(1000L), areafactory3, areafactory4);
      areafactory2 = zoom(1000L, ZoomLayer.NORMAL, areafactory2, 2, pContextFactory);
      areafactory2 = zoom(1000L, ZoomLayer.NORMAL, areafactory2, pRiverSize, pContextFactory);
      areafactory2 = RiverLayer.INSTANCE.run(pContextFactory.apply(1L), areafactory2);
      areafactory2 = SmoothLayer.INSTANCE.run(pContextFactory.apply(1000L), areafactory2);
      areafactory3 = RareBiomeSpotLayer.INSTANCE.run(pContextFactory.apply(1001L), areafactory3);

      for(int i = 0; i < pBiomeSize; ++i) {
         areafactory3 = ZoomLayer.NORMAL.run(pContextFactory.apply((long)(1000 + i)), areafactory3);
         if (i == 0) {
            areafactory3 = AddIslandLayer.INSTANCE.run(pContextFactory.apply(3L), areafactory3);
         }

         if (i == 1 || pBiomeSize == 1) {
            areafactory3 = ShoreLayer.INSTANCE.run(pContextFactory.apply(1000L), areafactory3);
         }
      }

      areafactory3 = SmoothLayer.INSTANCE.run(pContextFactory.apply(1000L), areafactory3);
      areafactory3 = RiverMixerLayer.INSTANCE.run(pContextFactory.apply(100L), areafactory3, areafactory2);
      return OceanMixerLayer.INSTANCE.run(pContextFactory.apply(100L), areafactory3, areafactory1);
   }

   public static Layer getDefaultLayer(long pSeed, boolean pLegacyBiomes, int pBiomeSize, int pRiverSize) {
      int i = 25;
      AreaFactory<LazyArea> areafactory = getDefaultLayer(pLegacyBiomes, pBiomeSize, pRiverSize, (p_76728_) -> {
         return new LazyAreaContext(25, pSeed, p_76728_);
      });
      return new Layer(areafactory);
   }

   /**
    * @return {@code true} if biomes are reported as the same biome or category.
    */
   public static boolean isSame(int pLeft, int pRight) {
      if (pLeft == pRight) {
         return true;
      } else {
         return CATEGORIES.get(pLeft) == CATEGORIES.get(pRight);
      }
   }

   private static void register(Int2IntOpenHashMap pCategories, AcrotlestLayers.Category pCategory, int pId) {
      pCategories.put(pId, pCategory.ordinal());
   }

   protected static boolean isOcean(int pBiome) {
      return pBiome == 44 || pBiome == 45 || pBiome == 0 || pBiome == 46 || pBiome == 10 || pBiome == 47 || pBiome == 48 || pBiome == 24 || pBiome == 49 || pBiome == 50;
   }

   protected static boolean isShallowOcean(int pBiome) {
      return pBiome == 44 || pBiome == 45 || pBiome == 0 || pBiome == 46 || pBiome == 10;
   }

   static enum Category {
      NONE,
      TAIGA,
      EXTREME_HILLS,
      JUNGLE,
      MESA,
      BADLANDS_PLATEAU,
      PLAINS,
      SAVANNA,
      ICY,
      BEACH,
      FOREST,
      OCEAN,
      DESERT,
      RIVER,
      SWAMP,
      MUSHROOM;
   }
}