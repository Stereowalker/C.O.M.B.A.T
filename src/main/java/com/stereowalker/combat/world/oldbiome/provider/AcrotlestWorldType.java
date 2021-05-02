package com.stereowalker.combat.world.oldbiome.provider;
//package com.stereowalker.combat.world.biome.provider;
//
//import java.util.function.LongFunction;
//
//import com.stereowalker.combat.world.gen.AcrotlestGenSettings;
//import com.stereowalker.combat.world.gen.layer.AcrotlestBiomeGenLayer;
//import com.stereowalker.combat.world.gen.layer.AcrotlestLayerUtil;
//
//import net.minecraft.world.WorldType;
//import net.minecraft.world.gen.IExtendedNoiseRandom;
//import net.minecraft.world.gen.OverworldGenSettings;
//import net.minecraft.world.gen.area.IArea;
//import net.minecraft.world.gen.area.IAreaFactory;
//import net.minecraft.world.gen.layer.EdgeBiomeLayer;
//import net.minecraft.world.gen.layer.ZoomLayer;
//
//public class AcrotlestWorldType extends WorldType {
//
//	public AcrotlestWorldType() {
//		super("acrotlest");
//	}
//	
//	@Override
//	public <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> getBiomeLayer(
//			IAreaFactory<T> parentLayer, OverworldGenSettings chunkSettings, LongFunction<C> contextFactory) {
//		if (chunkSettings instanceof AcrotlestGenSettings) {
//			parentLayer = (new AcrotlestBiomeGenLayer(getWorldType(), (AcrotlestGenSettings)chunkSettings)).apply(contextFactory.apply(200L), parentLayer);
//		}
//		parentLayer = AcrotlestLayerUtil.repeat(1000L, ZoomLayer.NORMAL, parentLayer, 2, contextFactory);
//		parentLayer = EdgeBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), parentLayer);
//		return parentLayer;
//	}
//
//}
