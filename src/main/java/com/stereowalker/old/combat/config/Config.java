package com.stereowalker.old.combat.config;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import com.stereowalker.combat.Combat;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config
{
    static final ForgeConfigSpec commonSpec;
    public static final CommonConfig COMMON;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = Combat.disableConfig() ? null : specPair.getRight();
        COMMON = Combat.disableConfig() ? new CommonConfig(new Builder()) : specPair.getLeft();
    }

    static final ForgeConfigSpec serverSpec;
    public static final ServerConfig SERVER;
    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        serverSpec = Combat.disableConfig() ? null : specPair.getRight();
        SERVER = Combat.disableConfig() ? new ServerConfig(new Builder()) : specPair.getLeft();
    }

    public static void registerConfigs() {
    	File configFile = new File(FMLPaths.CONFIGDIR.get().toString()+"//combat", "magic.toml");
    	configFile.getParentFile().mkdirs();
    	ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
    }
    
//    @SubscribeEvent
//    public static void onLoad(final ModConfig.Loading configEvent) {
//        LogManager.getLogger().debug(FORGEMOD, "Loaded combat config file {}", configEvent.getConfig().getFileName());
//    }
//
//    @SubscribeEvent
//    public static void onFileChange(final ModConfig.Reloading configEvent) {
//        LogManager.getLogger().debug(FORGEMOD, "Combat config just got changed on the file system!");
//    }
}