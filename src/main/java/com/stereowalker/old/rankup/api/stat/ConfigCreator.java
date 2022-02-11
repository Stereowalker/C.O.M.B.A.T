package com.stereowalker.old.rankup.api.stat;

import com.stereowalker.unionlib.util.TextHelper;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigCreator {

	public static IntValue numberedValue(String category, String name, ForgeConfigSpec.Builder builder, int defaultValue, int min, int max, String... text){
		String comment = "";
		for(String re : text) {
			comment = comment+"\n"+re;
		}
		return builder
				.comment(comment
						+ "\nDefault: "+defaultValue+"")
				.defineInRange(category+"."+name, defaultValue, min, max);
	}

	//Structure Gen
	public static DoubleValue structureGen(String structureName, ForgeConfigSpec.Builder builder, double defaultValue){
		String category = "Structure Generation";
		return builder
				.comment( "\nThe probability that "+ TextHelper.articulatedText(structureName, false)+" will spawn in the world if the spawn conditions are met"
						+ "\nThe higher the number the greater the chance"
						+ "\nDefault: "+defaultValue+"")
				.defineInRange(category+"."+structureName+" Spawn Probability", defaultValue, 0.1D, 1.0D);
	}
}
