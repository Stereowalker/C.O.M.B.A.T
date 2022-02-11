package com.stereowalker.old.combat.world.oldbiome.provider;
//package com.stereowalker.combat.world.biome.provider;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//import com.stereowalker.combat.Combat;
//
//import net.minecraft.world.biome.provider.BiomeProvider;
//import net.minecraft.world.biome.provider.BiomeProviderType;
//import net.minecraft.world.biome.provider.IBiomeProviderSettings;
//import net.minecraft.world.storage.WorldInfo;
//import net.minecraftforge.registries.IForgeRegistry;
//
//public class CBiomeProviderType {
//	public static final List<BiomeProviderType<?, ?>> BIOME_PROVIDER_TYPES = new ArrayList<BiomeProviderType<?, ?>>();
//	public static final BiomeProviderType<AcrotlestBiomeProviderSettings, AcrotlestBiomeProvider> ACROTLEST = register("acrotlest", AcrotlestBiomeProvider::new, AcrotlestBiomeProviderSettings::new);
//	
//	private static <C extends IBiomeProviderSettings, T extends BiomeProvider> BiomeProviderType<C, T> register(String key, Function<C, T> biomePorvider, Function<WorldInfo, C> biomeProviderSettings) {
//		BiomeProviderType<C, T> biomeProviderType = new BiomeProviderType<C, T>(biomePorvider, biomeProviderSettings);
//		biomeProviderType.setRegistryName(Combat.location(key));
//		BIOME_PROVIDER_TYPES.add(biomeProviderType);
//		return biomeProviderType;
//	}
//	
//	
//
//	public static void registerAll(IForgeRegistry<BiomeProviderType<?, ?>> registry) {
//		for(BiomeProviderType<?, ?> particle: BIOME_PROVIDER_TYPES) {
//			registry.register(particle);
//			Combat.debug("Biome Provider Type: \""+particle.getRegistryName().toString()+"\" registered");
//		}
//		Combat.debug("All Biome Provider Type Registered");
//	}
//
//}
