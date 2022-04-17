package com.stereowalker.combat.config;

import java.util.List;

import com.google.common.collect.Lists;
import com.stereowalker.unionlib.config.ConfigObject;
import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(folder = "Combat Config", name = "battle", translatableName = "config.combat.battle.file", autoReload = true)
public class BattleConfig implements ConfigObject {
	@UnionConfig.Entry(name = "Enable Sword Blocking", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"Simply enable blocking attacks with the sword"})
	public boolean swordBlocking = true;
	
	@UnionConfig.Entry(name = "Double Edge and Straight Weapons", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"This is a list of swords that can be classified as double edge and straight weapons.","Entries are in the format \"namespace:path\""})
	public List<String> doubleEdgeStraightWeapons = Lists.newArrayList(
			"combat:bronze_sword", "combat:steel_sword", "combat:pasquem_sword", "combat:pelgan_sword", "combat:lozyne_sword", "combat:serable_sword", 
			"combat:etherion_sword", "combat:blood_sword", "combat:soul_sword");
	
	@UnionConfig.Entry(name = "Single Edge and Curved Weapons", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"This is a list of swords that can be classified as single edge and curved weapons.","Entries are in the format \"namespace:path\""})
	public List<String> singleEdgeCurvedWeapons = Lists.newArrayList(
			"combat:bronze_katana", "combat:steel_katana", "combat:pasquem_katana", "combat:pelgan_katana", "combat:lozyne_katana", "combat:serable_katana", 
			"combat:etherion_katana", "combat:blood_katana", "combat:soul_katana");
	
	@UnionConfig.Entry(name = "Edgeless and Thrusting Weapons", type = Type.COMMON)
	@UnionConfig.Comment(comment = {"This is a list of swords that can be classified as edgeless and thrusting weapons.","Entries are in the format \"namespace:path\""})
	public List<String> edgelessThrustingWeapons = Lists.newArrayList(
			"combat:bronze_rapier", "combat:steel_rapier", "combat:pasquem_rapier", "combat:pelgan_rapier", "combat:lozyne_rapier", "combat:serable_rapier", 
			"combat:etherion_rapier", "combat:blood_rapier", "combat:soul_rapier");
//	public UnionValues.ConfigValue<List<String>> rangedWeapons;
}
