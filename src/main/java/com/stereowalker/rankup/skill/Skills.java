package com.stereowalker.rankup.skill;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.Skill.Builder;

import net.minecraftforge.registries.IForgeRegistry;

public class Skills {
	public static final List<Skill> SKILL_LIST = new ArrayList<Skill>();
	
	public static final Skill EMPTY = register("empty", new Skill(new Builder()));
	public static final Skill DAGGER_THROW = register("dagger_throw", new Skill(new Builder().setPrimaryColor(0x86F9A1).setSecondaryColor(0x770402).setNoLevels().isSpawnSkill()));
	public static final Skill DAGGER_RETRIEVAL_1 = register("dagger_retrieval_1", new Skill(new Builder().setPrimaryColor(0x3FE49E).setSecondaryColor(0x408E3F).setLevel(1)));
	public static final Skill DAGGER_RETRIEVAL_2 = register("dagger_retrieval_2", new Skill(new Builder().setPrimaryColor(0x40E59F).setSecondaryColor(0x408E3F).setLevel(2).setSuperSkill(DAGGER_RETRIEVAL_1)));
	public static final Skill DAGGER_RETRIEVAL_3 = register("dagger_retrieval_3", new Skill(new Builder().setPrimaryColor(0x41E6A0).setSecondaryColor(0x408E3F).setLevel(3).setSuperSkill(DAGGER_RETRIEVAL_2)));
	public static final Skill PERSEVERANCE_1 = register("perseverance_1", new PerseveranceSkill(new Builder().setPrimaryColor(0xAC8D8A).setSecondaryColor(0xF4FDB9).setLevel(1).isSpawnSkill()));
	public static final Skill PERSEVERANCE_2 = register("perseverance_2", new PerseveranceSkill(new Builder().setPrimaryColor(0xAD8E8B).setSecondaryColor(0xF4FDB9).setLevel(2).setSuperSkill(PERSEVERANCE_1)));
	public static final Skill PERSEVERANCE_3 = register("perseverance_3", new PerseveranceSkill(new Builder().setPrimaryColor(0xAE8F8C).setSecondaryColor(0xF4FDB9).setLevel(3).setSuperSkill(PERSEVERANCE_2)));
	public static final Skill ADRENALINE_RUSH_1 = register("adrenalne_rush_1", new AdreanalineSkill(new Builder().setPrimaryColor(0x63E53A).setSecondaryColor(0x7FCB15).setLevel(1).isSpawnSkill()));
	public static final Skill ADRENALINE_RUSH_2 = register("adrenalne_rush_2", new AdreanalineSkill(new Builder().setPrimaryColor(0x64E63B).setSecondaryColor(0x7FCB15).setLevel(2).setSuperSkill(ADRENALINE_RUSH_1)));
	public static final Skill TREASURE_HUNTER = register("treasure_hunter", new Skill(new Builder().setPrimaryColor(0xAF01E3).setSecondaryColor(0x75F856).setNoLevels()));
	public static final Skill BURNING_STRIKE = register("burning_strike", new BurningStrikeSkill(new Builder().setPrimaryColor(0xAF75EF).setSecondaryColor(0xBEA88B).setNoLevels()));
	public static final Skill FREEZING_STRIKE = register("freezing_strike", new FrozenStrikeSkill(new Builder().setPrimaryColor(0xAA73A8).setSecondaryColor(0x08D86C).setNoLevels()));
	public static final Skill INSIGHT = register("insight", new Skill(new Builder().setPrimaryColor(0x60CA28).setSecondaryColor(0x4CA48D).setNoLevels()));
	public static final Skill LIMITER = register("limiter", new Skill(new Builder().setPrimaryColor(0x60CA28).setSecondaryColor(0x4CA48D).isActiveSkill().setNoLevels()));
	public static final Skill ARCHERS_ELBOW = register("archers_elbow", new Skill(new Builder().setPrimaryColor(0x60CA28).setSecondaryColor(0x4CA48D).isJobSkill().setNoLevels()));
	public static final Skill ARROW_SAVINGS = register("arrow_savings", new Skill(new Builder().setPrimaryColor(0x60CA28).setSecondaryColor(0x4CA48D).isJobSkill().setNoLevels()));
	
	public static Skill register(String name, Skill skill) {
		skill.setRegistryName(Combat.getInstance().location(name));
		SKILL_LIST.add(skill);
		return skill;
	}
	
	public static void registerAll(IForgeRegistry<Skill> registry) {
		for(Skill skill : SKILL_LIST) {
			registry.register(skill);
			Combat.debug("Skill: \""+skill.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Skills Registered");
	}
			
}
