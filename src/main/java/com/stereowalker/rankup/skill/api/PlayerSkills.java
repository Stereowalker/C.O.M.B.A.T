package com.stereowalker.rankup.skill.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.world.job.JobEvents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerSkills {
	public static boolean hasSkill(LivingEntity entity, Skill skill) {
		if(entity != null) {
			CompoundTag compound = getRankSkillNBT(entity);
			return compound.getBoolean(CombatRegistries.SKILLS.get().getKey(skill).getPath());
		}
		return false;
	}
	
	public static boolean isSkillActive(LivingEntity entity, Skill skill) {
		if(entity != null && hasSkill(entity, skill)) {
			CompoundTag compound = getRankSkillNBT(entity);
			return compound.getBoolean("skill_active_"+CombatRegistries.SKILLS.get().getKey(skill).getPath());
		}
		return false;
	}
	
	public static Skill getStartingSkill(Player player) {
		if (player != null) {
			CompoundTag compoundNBT = getRankSkillNBT(player);
			return SkillUtil.getSkillFromNBT(compoundNBT, "StartingSkill");
		}
		return Skills.EMPTY;
	}
	
	public static void setStartingSkill(Player player, Skill skill) {
		CompoundTag compound = getRankSkillNBT(player);
		compound.putString("StartingSkill", skill.getKey());
	}
	
	public static void setSkill(Player player, Skill skill, boolean flag) {
		CompoundTag compound = getRankSkillNBT(player);
		compound.putBoolean(CombatRegistries.SKILLS.get().getKey(skill).getPath(), flag);
	}
	
	public static void setSkillActive(Player player, Skill skill, boolean flag) {
		CompoundTag compound = getRankSkillNBT(player);
		compound.putBoolean("skill_active_"+CombatRegistries.SKILLS.get().getKey(skill).getPath(), flag);
	}

	public static enum SkillGrantAction {
		SUCCESS("success"),
		FAILURE("failure"),
		REQUIRES_SUPER_SKILL("requires_skill"),
		CANNOT_GIVE_EMPTY_SKILL("cannot_give_empty_skill"),
		ALREADY_HAS_SKILL("already_has_skill");
		
		
		String name;
		
		private SkillGrantAction(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static enum SkillGrantReason {
		STARTER,
		ITEM,
		COMMAND,
		JOB,
		LIMITER,
		NONE;
	}
	
	public static SkillGrantAction grantSkill(Player player, Skill skill, SkillGrantReason reason) {
		if (skill == Skills.EMPTY) {
			return SkillGrantAction.CANNOT_GIVE_EMPTY_SKILL;
		} else if (hasSkill(player, skill)) {
			return SkillGrantAction.ALREADY_HAS_SKILL;
		} else if (skill.isSubSkill() && !hasSkill(player, skill.getSuperSkill())){
			return SkillGrantAction.REQUIRES_SUPER_SKILL;
		} else {
			if (reason != SkillGrantReason.NONE) setSkill(player, skill, true);
			if (reason == SkillGrantReason.ITEM && player instanceof ServerPlayer) {
				((ServerPlayer)player).getServer().getPlayerList().broadcastSystemMessage(Component.translatable("chat.skill.earned", player.getDisplayName(), JobEvents.jobDisplay(skill.getName(), skill.getDescription())), false);
			}
			return SkillGrantAction.SUCCESS;
		}
	}
	
	public static void grantRandomSkill(Player player, SkillGrantReason reason) {
		SkillGrantAction action = SkillGrantAction.FAILURE;
		int attemps = 0;
		boolean flag = false;
		while(action != SkillGrantAction.SUCCESS || flag) {
			attemps++;
			Combat.debug("Attempt "+attemps+" To Award Random Skill");
			List<Skill> elegibleSkills = new ArrayList<Skill>();
			for (Skill skill : CombatRegistries.SKILLS.get()) {
				if (!hasSkill(player, skill)) {
					elegibleSkills.add(skill);
				}
			}
			
			int skillsAvailable = elegibleSkills.size();
			
			Random random = new Random();
			
			int randomNumber = random.nextInt(skillsAvailable);
			
			if (elegibleSkills.size() == 1 && elegibleSkills.contains(Skills.EMPTY)) {
				flag = true;
			}
			
			action = grantSkill(player, elegibleSkills.get(randomNumber), reason);
			Combat.debug("Tried to give player "+CombatRegistries.SKILLS.get().getKey(elegibleSkills.get(randomNumber)).getPath()+" skill. Resulted in "+action);
		}
	}
	
	public static Skill generateRandomSkill(Player player, boolean onlyStartSkills) {
		Skill generatedSkill = null;
		SkillGrantAction action = SkillGrantAction.FAILURE;
		int attemps = 0;
		boolean flag = false;
		while(!flag) {
			attemps++;
			Combat.debug("Attempt "+attemps+" To Award Random Skill");
			List<Skill> elegibleSkills = new ArrayList<Skill>();
			for (Skill skill : CombatRegistries.SKILLS.get()) {
				if (!skill.isJobSkill() && !hasSkill(player, skill) && (onlyStartSkills?skill.isSpawnSkill():true)) {
					elegibleSkills.add(skill);
				}
			}
			
			int skillsAvailable = elegibleSkills.size();
			Random random = new Random();
			int randomNumber = random.nextInt(skillsAvailable);
			
			generatedSkill = elegibleSkills.get(randomNumber);
			action = grantSkill(player, generatedSkill, SkillGrantReason.NONE);
			Combat.debug("Size: "+elegibleSkills.size()+" Contains Empty: "+elegibleSkills.contains(Skills.EMPTY));
			Combat.debug("Tried to generate "+CombatRegistries.SKILLS.get().getKey(elegibleSkills.get(randomNumber)).getPath()+" skill for player. Resulted in "+action);
			
			if ((elegibleSkills.size() == 1 && elegibleSkills.contains(Skills.EMPTY)) || action == SkillGrantAction.SUCCESS) {
				flag = true;
				if (elegibleSkills.size() == 1 && elegibleSkills.contains(Skills.EMPTY)) generatedSkill = Skills.EMPTY;
			}
		}
		
//		if (flag == true) {
//			generatedSkill = Skills.EMPTY;
//		}
		return generatedSkill;
	}
	
	public static boolean revokeSkill(Player player, Skill skill) {
		if (!hasSkill(player, skill)) {
			return false;
		} else {
			setSkill(player, skill, false);
			for (Skill subSkill : CombatRegistries.SKILLS.get()) {
				if (subSkill.getSuperSkill() == skill) {
					revokeSkill(player, subSkill);
				}
			}
			return true;
		}
	}
	
//	public static 

	public static void addSkillsOnSpawn(Player player) {
		CompoundTag compound;
		compound = getOrCreateRankSkillNBT(player);
		String name = player.getScoreboardName();
		if(player.isAlive()) {
			for (Skill skill : CombatRegistries.SKILLS.get()) {
				if (!compound.contains(CombatRegistries.SKILLS.get().getKey(skill).getPath())) {
					setSkill(player, skill, false);
					setSkillActive(player, skill, true);
					Combat.debug("Set " + name + "'s "+CombatRegistries.SKILLS.get().getKey(skill).getPath()+" skill to " + hasSkill(player, skill));
				}
			}
			if(!compound.contains("StartingSkill")) {
				Skill rgs = generateRandomSkill(player, true);
				int attempts = 0;
				while(!rgs.isSpawnSkill()) {
					attempts++;
					Combat.debug("Attempt: "+attempts+", Currently Generated Skill: "+rgs.getKey());
					rgs = generateRandomSkill(player, true);
				}
				setStartingSkill(player, rgs);
			}
		}
	}

	public static String getRankSkillDataString() {
		return Combat.getInstance().locationString("RankUp_Skills");
	}

	public static CompoundTag getRankSkillNBT(Entity entity) {
		return entity.getPersistentData().getCompound(getRankSkillDataString());
	}

	public static CompoundTag getOrCreateRankSkillNBT(Player entity) {
		if (!entity.getPersistentData().contains(getRankSkillDataString(), 10)) {
			entity.getPersistentData().put(getRankSkillDataString(), new CompoundTag());
		}
		return entity.getPersistentData().getCompound(getRankSkillDataString());
	}

	public static void setRankSkillNBT(CompoundTag nbt, Entity entity) {
		entity.getPersistentData().put(getRankSkillDataString(), nbt);
	}
}
