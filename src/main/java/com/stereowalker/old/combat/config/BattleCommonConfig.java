package com.stereowalker.old.combat.config;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.unionlib.config.UnionValues;
import com.stereowalker.unionlib.util.ConfigHelper;

import net.minecraftforge.common.ForgeConfigSpec;

public class BattleCommonConfig {
	//Battle
	public UnionValues.BooleanValue swordBlocking;
	public UnionValues.ConfigValue<List<String>> doubleEdgeStraightWeapons;
	public UnionValues.ConfigValue<List<String>> singleEdgeCurvedWeapons;
	public UnionValues.ConfigValue<List<String>> edgelessThrustingWeapons;
	public UnionValues.ConfigValue<List<String>> rangedWeapons;

	BattleCommonConfig(ForgeConfigSpec.Builder common) {
		swordBlocking = UnionValues.BooleanValue.build(common
				.comment("Simply enable blocking attacks with the sword")
				.define("Battle.Enable Sword Blocking", true));
		List<String> swords = new ArrayList<String>();
		swords.add("combat:bronze_sword");
		swords.add("combat:steel_sword");
		swords.add("combat:pasquem_sword");
		swords.add("combat:pelgan_sword");
		swords.add("combat:lozyne_sword");
		swords.add("combat:serable_sword");
		swords.add("combat:etherion_sword");
		swords.add("combat:blood_sword");
		swords.add("combat:soul_sword");
		List<String> doubleEdge = new ArrayList<String>();
		doubleEdgeStraightWeapons = UnionValues.ConfigValue.build(ConfigHelper.listValue("Battle", "Double Edge and Straight Weapons", common, doubleEdge, "This is a list of swords that can be classified as double edge and straight weapons.","Entries are in the format \"namespace:path\""));
		doubleEdge.add("combat:bronze_katana");
		doubleEdge.add("combat:steel_katana");
		doubleEdge.add("combat:pasquem_katana");
		doubleEdge.add("combat:pelgan_katana");
		doubleEdge.add("combat:lozyne_katana");
		doubleEdge.add("combat:serable_katana");
		doubleEdge.add("combat:etherion_katana");
		doubleEdge.add("combat:blood_katana");
		doubleEdge.add("combat:soul_katana");
		singleEdgeCurvedWeapons = UnionValues.ConfigValue.build(ConfigHelper.listValue("Battle", "Single Edge and Curved Weapons", common, doubleEdge, "This is a list of swords that can be classified as single edge and curved weapons.","Entries are in the format \"namespace:path\""));
		doubleEdge.add("combat:bronze_rapier");
		doubleEdge.add("combat:steel_rapier");
		doubleEdge.add("combat:pasquem_rapier");
		doubleEdge.add("combat:pelgan_rapier");
		doubleEdge.add("combat:lozyne_rapier");
		doubleEdge.add("combat:serable_rapier");
		doubleEdge.add("combat:etherion_rapier");
		doubleEdge.add("combat:blood_rapier");
		doubleEdge.add("combat:soul_rapier");
		edgelessThrustingWeapons = UnionValues.ConfigValue.build(ConfigHelper.listValue("Battle", "Edgeless and Thrusting Weapons", common, doubleEdge, "This is a list of swords that can be classified as edgeless and thrusting weapons.","Entries are in the format \"namespace:path\""));
		//TODO: Add Mapped Values
		//			Map<String,Integer> weapons = new HashMap<String,Integer>();
		//			weapons.add("combat:bronze_sword");
		//			weapons.add("combat:steel_sword");
		//			weapons.add("combat:pasquem_sword");
		//			weapons.add("combat:pelgan_sword");
		//			weapons.add("combat:lozyne_sword");
		//			weapons.add("combat:serable_sword");
		//			weapons.add("combat:etherion_sword");
		//			weapons.add("combat:blood_sword");
		//			weapons.add("combat:soul_sword");
		//			doubleEdgeStraightWeapons = UnionValues.ConfigValue.build(ConfigHelper.mapValue("Battle", "Weapons With Range", common, weapons, "This is a list of items that should have the Attack Reach attribute. Items can also be overriden in this list","Entries are in the format \"namespace:path,reach\""));
	}
}
