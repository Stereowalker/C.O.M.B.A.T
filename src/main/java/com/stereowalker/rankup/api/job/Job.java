package com.stereowalker.rankup.api.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Job extends ForgeRegistryEntry<Job>{
	
	public static final Codec<Job> CODEC = RecordCodecBuilder.create((p_67790_) -> {
		return p_67790_.group(ResourceLocation.CODEC.fieldOf("statToTrack").forGetter((p_160992_) -> {
			return p_160992_.getStatToTrack();
		}), Codec.INT.fieldOf("amountToUnlock").forGetter((p_160992_) -> {
			return p_160992_.getAmountToUnlock();
		}), Codec.INT.fieldOf("amountForExperience").forGetter((p_160992_) -> {
			return p_160992_.getAmountForExperience();
		}), Codec.INT.listOf().fieldOf("in").forGetter((p_160992_) -> {
			return p_160992_.levels;
		}), ResourceLocation.CODEC.listOf().fieldOf("jobSkills").forGetter((p_160992_) -> {
			return p_160992_.jobSkills;
		})).apply(p_67790_, Job::new);
	});
	
	private ResourceLocation statToTrack;
	private int amountToUnlock;
	private int amountForExperience;
	public List<Integer> levels;
	public List<ResourceLocation> jobSkills;
	private boolean handleGivingStatXP = false;
	
	public Job(ResourceLocation statToTrack, int amountToUnlock, int amountForExperience, int[] levels, List<Skill> jobSkills, boolean handleGivingStatXP) {
		this(statToTrack, amountToUnlock, amountForExperience, Lists.newArrayList(ArrayUtils.toObject(levels)), new ArrayList<>());
		jobSkills.forEach((skill) ->{
			this.jobSkills.add(skill.getRegistryName());
		});
		this.handleGivingStatXP = handleGivingStatXP;
	}
	
	public Job(ResourceLocation statToTrack, int amountToUnlock, int amountForExperience, List<Integer> levels, List<ResourceLocation> jobSkills) {
		this.statToTrack = statToTrack;
		this.amountToUnlock = amountToUnlock;
		this.amountForExperience = amountForExperience;
		this.levels = levels;
		this.jobSkills = jobSkills;
	}
	
	public int getAmountToUnlock() {
		return amountToUnlock;
	}
	
	public int getAmountForExperience() {
		return amountForExperience;
	}
	
	public ResourceLocation getStatToTrack() {
		return statToTrack;
	}
	
	public boolean handleGivingStatXP() {
		return handleGivingStatXP;
	}
	
	public int getMaximumLevel() {
		return this.levels.size()+1;
	}
	
}
