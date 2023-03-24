package com.stereowalker.combat.config;

import com.stereowalker.unionlib.config.ConfigObject;
import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(folder = "Combat Config", name = "battle", translatableName = "config.combat.battle.file", autoReload = true)
public class BattleConfig implements ConfigObject {
	@UnionConfig.Entry(name = "Enable Sword Blocking", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Simply enable blocking attacks with the sword"})
	public boolean swordBlocking = true;
//	public UnionValues.ConfigValue<List<String>> rangedWeapons;
}
