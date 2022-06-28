package com.stereowalker.rankup.resource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.world.stat.StatSettings;
import com.stereowalker.unionlib.resource.IResourceReloadListener;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

/**
 * Loads stat settings from json files
 * @author Stereowalker
 */
public class StatsManager implements IResourceReloadListener<Map<ResourceKey<Stat>,StatSettings>> {
	private static final Logger LOGGER = LogManager.getLogger();
	public ImmutableMap<ResourceKey<Stat>,StatSettings> STATS = ImmutableMap.of();

	@Override
	public CompletableFuture<Map<ResourceKey<Stat>,StatSettings>> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			Map<ResourceKey<Stat>,StatSettings> statMap = new HashMap<>();
			
			for (Stat stat : CombatRegistries.STATS) {
				statMap.put(ResourceKey.create(CombatRegistries.STATS_REGISTRY, stat.getRegistryName()), null);
			}
			for (ResourceLocation id : manager.listResources("combat/stat_settings/", (s) -> s.endsWith(".json"))) {
				ResourceLocation statId = new ResourceLocation(
						id.getNamespace(),
						id.getPath().replace("combat/stat_settings/", "").replace(".json", "")
						);

				
				if (CombatRegistries.STATS.containsKey(statId)) {
					LOGGER.info("Found the stat, " + statId + ", in the registry. The creation if it's settings can proceed as usual");
				} else {
					LOGGER.info("No such stat exists with the id, " + statId + ", in the registry. Will still create it's settings, but the stat should be created in the datapack in order for this setting to be used");
				}
				
				try {
					Resource resource = manager.getResource(id);
					try (InputStream stream = resource.getInputStream(); 
							InputStreamReader reader = new InputStreamReader(stream)) {
						
						JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
						StatSettings statSettings = new StatSettings(object, statId);
						LOGGER.info("Found stat settings for "+statId);
						
						statMap.put(ResourceKey.create(CombatRegistries.STATS_REGISTRY, statId), statSettings);
					}
				} catch (Exception e) {
					LOGGER.warn("Error reading stat settings " + statId + "!", e);
				}
			}

			return statMap;
		});
	}

	@Override
	public CompletableFuture<Void> apply(Map<ResourceKey<Stat>,StatSettings> data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> {
			Map<ResourceKey<Stat>,StatSettings> statMap = new HashMap<>();
			data.forEach((stat,setting) -> {
				if (setting == null) {
					LOGGER.warn("No settings included for the " + stat + " stat! Add these settings to enable it");
					statMap.put(stat, new StatSettings(null, stat.location()));
				} else {
					statMap.put(stat, setting);
				}
			});
			
			STATS = ImmutableMap.copyOf(statMap);
		});
	}
}
