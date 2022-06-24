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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

/**
 * Loads stat settings from json files
 * @author Stereowalker
 */
public class StatsManager implements IResourceReloadListener<Map<ResourceLocation,StatSettings>> {
	private static final Logger LOGGER = LogManager.getLogger();
	public ImmutableMap<ResourceLocation,StatSettings> STATS = ImmutableMap.of();

	@Override
	public CompletableFuture<Map<ResourceLocation,StatSettings>> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			Map<ResourceLocation,StatSettings> statMap = new HashMap<>();
			
			for (Stat stat : CombatRegistries.STATS) {
				statMap.put(stat.getRegistryName(), null);
			}
			for (ResourceLocation id : manager.listResources("combat/stat_settings/", (s) -> s.endsWith(".json"))) {
				ResourceLocation statId = new ResourceLocation(
						id.getNamespace(),
						id.getPath().replace("combat/stat_settings/", "").replace(".json", "")
						);

				
				if (CombatRegistries.STATS.containsKey(statId)) {
					LOGGER.warn("Overrriding existing stat " + statId + "");
				} else {
					LOGGER.warn("No such stat exists with the id " + statId + ", registering a new one");
				}
				
				try {
					Resource resource = manager.getResource(id);
					try (InputStream stream = resource.getInputStream(); 
							InputStreamReader reader = new InputStreamReader(stream)) {
						
						JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
						StatSettings statSettings = new StatSettings(object, statId);
						LOGGER.info("Found stat settings for "+statId);
						
						statMap.put(statId, statSettings);
					}
				} catch (Exception e) {
					LOGGER.warn("Error reading stat settings " + statId + "!", e);
				}
			}

			return statMap;
		});
	}

	@Override
	public CompletableFuture<Void> apply(Map<ResourceLocation,StatSettings> data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> {
			Map<ResourceLocation,StatSettings> statMap = new HashMap<>();
			data.forEach((stat,setting) -> {
				if (setting == null) {
					LOGGER.warn("No settings included for the " + stat + " stat! Add these settings to enable it");
					statMap.put(stat, new StatSettings(null, stat));
				} else {
					statMap.put(stat, setting);
				}
			});
			
			STATS = ImmutableMap.copyOf(statMap);
		});
	}
}
