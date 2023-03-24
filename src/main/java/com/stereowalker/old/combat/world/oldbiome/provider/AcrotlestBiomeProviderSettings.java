package com.stereowalker.old.combat.world.oldbiome.provider;
//package com.stereowalker.combat.world.biome.provider;
//
//import com.stereowalker.combat.world.gen.AcrotlestGenSettings;
//
//import net.minecraft.world.WorldType;
//import net.minecraft.world.biome.provider.IBiomeProviderSettings;
//import net.minecraft.world.storage.WorldInfo;
//
//public class AcrotlestBiomeProviderSettings implements IBiomeProviderSettings{
//	private final long seed;
//	private final WorldType worldType;
//	private WorldInfo worldInfo;
//	private AcrotlestGenSettings generatorSettings;
//	
//	public AcrotlestBiomeProviderSettings(WorldInfo worldInfo) {
//		worldInfo.setGenerator(new AcrotlestWorldType());
//		this.seed = worldInfo.getSeed();
//		this.worldType = new AcrotlestWorldType();
//	}
//	
//	public AcrotlestBiomeProviderSettings setWorldInfo(WorldInfo worldInfo) {
//		worldInfo.setGenerator(new AcrotlestWorldType());
//		this.worldInfo = worldInfo;
//		return this;
//	}
//	
//	public AcrotlestBiomeProviderSettings setGeneratorSettings(AcrotlestGenSettings generatorSettings) {
//		this.generatorSettings = generatorSettings;
//		return this;
//	}
//	
//	public WorldInfo getWorldInfo() {
//		return worldInfo;
//	}
//	
//	public AcrotlestGenSettings getGeneratorSettings() {
//		return generatorSettings;
//	}
//
//	public long getSeed() {
//		return seed;
//	}
//
//	public WorldType getWorldType() {
//		return worldType;
//	}
//}
