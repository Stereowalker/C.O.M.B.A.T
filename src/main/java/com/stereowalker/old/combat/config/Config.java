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
    static final ForgeConfigSpec clientSpec;
    public static final ClientConfig CLIENT;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        clientSpec = Combat.disableConfig() ? null : specPair.getRight();
        CLIENT = Combat.disableConfig() ? new ClientConfig(new Builder()) : specPair.getLeft();
    }

    static final ForgeConfigSpec commonSpec;
    public static final CommonConfig COMMON;
    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        commonSpec = Combat.disableConfig() ? null : specPair.getRight();
        COMMON = Combat.disableConfig() ? new CommonConfig(new Builder()) : specPair.getLeft();
    }

    static final ForgeConfigSpec magicCommonSpec;
    public static final MagicCommonConfig MAGIC_COMMON;
    static {
        final Pair<MagicCommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MagicCommonConfig::new);
        magicCommonSpec = Combat.disableConfig() ? null : specPair.getRight();
        MAGIC_COMMON = Combat.disableConfig() ? new MagicCommonConfig(new Builder()) : specPair.getLeft();
    }

    static final ForgeConfigSpec rpgCommonSpec;
    public static final RpgCommonConfig RPG_COMMON;
    static {
        final Pair<RpgCommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(RpgCommonConfig::new);
        rpgCommonSpec = Combat.disableConfig() ? null : specPair.getRight();
        RPG_COMMON = Combat.disableConfig() ? new RpgCommonConfig(new Builder()) : specPair.getLeft();
    }

    static final ForgeConfigSpec battleCommonSpec;
    public static final BattleCommonConfig BATTLE_COMMON;
    static {
        final Pair<BattleCommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BattleCommonConfig::new);
        battleCommonSpec = Combat.disableConfig() ? null : specPair.getRight();
        BATTLE_COMMON = Combat.disableConfig() ? new BattleCommonConfig(new Builder()) : specPair.getLeft();
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
    	ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.magicCommonSpec, "combat\\magic.toml");
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.battleCommonSpec, "combat\\battle.toml");
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.rpgCommonSpec, "combat\\rpg.toml");
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