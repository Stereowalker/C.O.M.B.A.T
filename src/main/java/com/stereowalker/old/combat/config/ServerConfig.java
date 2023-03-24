package com.stereowalker.old.combat.config;

import com.stereowalker.old.rankup.api.stat.ConfigCreator;
import com.stereowalker.unionlib.config.UnionValues;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
	//OreGen
	public  UnionValues.IntValue tridoxChance;
	public  UnionValues.IntValue oilChance;
	public  UnionValues.BooleanValue generate_limestone;
	public  UnionValues.BooleanValue generate_cassiterite;
	public  UnionValues.BooleanValue generate_ruby_ore;
	public  UnionValues.BooleanValue generate_oil;
	//StrutureGen
	public  UnionValues.DoubleValue etherionTowerProbability;
	public  UnionValues.DoubleValue magicStoneDepositProbability;
	public  UnionValues.DoubleValue acrotlestPortalProbability;

	ServerConfig(ForgeConfigSpec.Builder server) {
		if (server != null) {
			generate_limestone = UnionValues.BooleanValue.build(server
					.comment("Should C.O.M.B.A.T. limestone spawn ingame")
					.define("Oregen.Generate Limestone", true));
			generate_cassiterite = UnionValues.BooleanValue.build(server
					.comment("Should C.O.M.B.A.T. cassiterite spawn ingame")
					.define("Oregen.Generate Cassiterite", true));
			generate_ruby_ore = UnionValues.BooleanValue.build(server
					.comment("Should C.O.M.B.A.T. ruby ores spawn ingame")
					.define("Oregen.Generate Ruby Ore", true));
			generate_oil = UnionValues.BooleanValue.build(server
					.comment("Should C.O.M.B.A.T. oil spawn ingame")
					.define("Oregen.Generate Oil", true));
			oilChance = UnionValues.IntValue.build(server
					.comment("Max number of oil veins that can spawn in one chunk")
					.defineInRange("Oregen.Oil Chance", 1, 1, 100));
			tridoxChance = UnionValues.IntValue.build(server
					.comment("Max number of tridox ore veins that can spawn in one chunk")
					.defineInRange("oregen.Tridox Chance", 20, 1, 100));

			etherionTowerProbability = UnionValues.DoubleValue.build(ConfigCreator.structureGen("Etherion Tower", server, 0.5D));
			magicStoneDepositProbability = UnionValues.DoubleValue.build(ConfigCreator.structureGen("Magic Stone Deposit", server, 0.5D));
			acrotlestPortalProbability = UnionValues.DoubleValue.build(ConfigCreator.structureGen("Acrotlest Portal", server, 0.5D));
		}
		else {
			generate_limestone = new UnionValues.BooleanValue(null, true);
			generate_cassiterite = new UnionValues.BooleanValue(null, true);
			generate_ruby_ore = new UnionValues.BooleanValue(null, true);
			generate_oil = new UnionValues.BooleanValue(null, true);
			oilChance = new UnionValues.IntValue(null, 1);
			tridoxChance = new UnionValues.IntValue(null, 20);

			etherionTowerProbability = new UnionValues.DoubleValue(null, 0.5D);
			magicStoneDepositProbability = new UnionValues.DoubleValue(null, 0.5D);
			acrotlestPortalProbability = new UnionValues.DoubleValue(null, 0.5D);
		}
	}
}
