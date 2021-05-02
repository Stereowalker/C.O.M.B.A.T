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
import com.stereowalker.rankup.stat.StatSettings;
import com.stereowalker.unionlib.resource.IResourceReloadListener;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

/**
 * Loads stat settings from json files
 * @author Stereowalker
 */
public class StatsManager implements IResourceReloadListener<Map<Stat,StatSettings>> {
	private static final JsonParser parser = new JsonParser();
	private static final Logger LOGGER = LogManager.getLogger();
	public ImmutableMap<Stat,StatSettings> STATS = ImmutableMap.of();

	@Override
	public CompletableFuture<Map<Stat,StatSettings>> load(IResourceManager manager, IProfiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			Map<Stat,StatSettings> statMap = new HashMap<>();
			
			for (Stat stat : CombatRegistries.STATS) {
				statMap.put(stat, null);
			}

			for (ResourceLocation id : manager.getAllResourceLocations("combat_stats", (s) -> s.endsWith(".json"))) {
				ResourceLocation statId = new ResourceLocation(
						id.getNamespace(),
						id.getPath().replace("combat_stats/", "").replace(".json", "")
						);

				if (CombatRegistries.STATS.containsKey(statId)) {
					try {
						IResource resource = manager.getResource(id);
						try (InputStream stream = resource.getInputStream(); 
								InputStreamReader reader = new InputStreamReader(stream)) {
							
							JsonObject object = parser.parse(reader).getAsJsonObject();
							StatSettings statSettings = new StatSettings(object, statId);
							LOGGER.info("Found stat settings for "+statId);
							
							statMap.put(CombatRegistries.STATS.getValue(statId), statSettings);
						}
					} catch (Exception e) {
						LOGGER.warn("Error reading stat settings " + statId + "!", e);
					}
				} else {
					LOGGER.warn("No such stat exists with the id " + statId + "!");
				}
			}

			return statMap;
		});
	}

	@Override
	public CompletableFuture<Void> apply(Map<Stat,StatSettings> data, IResourceManager manager, IProfiler profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> {
			Map<Stat,StatSettings> statMap = new HashMap<>();
			data.forEach((stat,setting) -> {
				if (setting == null) {
					LOGGER.warn("No settings included for the " + stat.getRegistryName() + " stat! Add these settings to enable it");
					statMap.put(stat, new StatSettings(null, stat.getRegistryName()));
				} else {
					statMap.put(stat, setting);
				}
			});
			
			STATS = ImmutableMap.copyOf(statMap);
		});
	}
}
