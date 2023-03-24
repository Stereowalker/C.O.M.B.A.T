package com.stereowalker.old.combat.config;

import com.stereowalker.old.rankup.api.stat.ConfigCreator;
import com.stereowalker.unionlib.config.UnionValues;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	//Misc
	public  UnionValues.BooleanValue enable_fletching;
	public final UnionValues.BooleanValue load_guns;
	public  UnionValues.BooleanValue debug_mode;
	public  UnionValues.IntValue snowPileupRate;

	CommonConfig(ForgeConfigSpec.Builder common) {
		if (common != null) {
			snowPileupRate = UnionValues.IntValue.build(ConfigCreator.numberedValue("Miscellaneous", "Snow Pileup Rate", common, 10, 0, 1000, "The rate at which snow layers will increase","The lower this number, the quicker snow layers will increase","Set this to zero to disable this feature"));
			
			load_guns = UnionValues.BooleanValue.build(common
					.comment("Disabling this would prevent the guns in this mod from loading in your game")
					.define("Miscellaneous.Load Guns", true));
			enable_fletching = UnionValues.BooleanValue.build(common
					.comment("Disabling this will prevent you from using the fletching table to create arrows from this mod.")
					.define("Miscellaneous.Enable Fletching", true));
			debug_mode = UnionValues.BooleanValue.build(common
					.comment("Enable to see debug messages sent by this mod in the console")
					.define("Miscellaneous.Debug Mode", false));
		}
		else {
			load_guns = new UnionValues.BooleanValue(null, true);
		}
	}
}
