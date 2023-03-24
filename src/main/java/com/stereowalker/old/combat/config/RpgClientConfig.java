package com.stereowalker.old.combat.config;

import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(name = "rpg-client", autoReload = true, appendWithType = false, folder = "combat")
public class RpgClientConfig {

	@UnionConfig.Entry(group = "World Gen" , name = "Random World Names", type = Type.CLIENT)
	@UnionConfig.Comment(comment = {"This generates random world names","This works similar to the way Terraria generates world names"})
	public static boolean randomWorldNames = true;
}
