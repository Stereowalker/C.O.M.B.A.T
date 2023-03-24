package com.stereowalker.rankup.world.job;

import java.util.Map.Entry;

import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.network.protocol.game.ClientboundPlayerJobsPacket;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.PlayerSkills.SkillGrantReason;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;

public class JobEvents {
	public static void playerToClient(ServerPlayer player) {
		new ClientboundPlayerJobsPacket(player).send(player);
	}
	
	public static void registerEntityStats(ServerPlayer entity) {
		PlayerJobs.addLevelsOnSpawn(entity);
	}

	public static void restoreJobs(Player player, Player original, boolean wasDeath) {
		for (Entry<ResourceKey<Job>, Job> levels : player.getLevel().registryAccess().registryOrThrow(CombatRegistries.JOBS_REGISTRY).entrySet()) {
			PlayerJobs.setJobProfile(player, levels.getKey(), PlayerJobs.getJobProfile(original, levels.getKey()));
		}
	}

	public static void jobUpdate(ServerPlayer player, PlayerList list) {
		Registry<Job> registry = player.getLevel().registryAccess().registryOrThrow(CombatRegistries.JOBS_REGISTRY);
		for (Entry<ResourceKey<Job>, Job> entry : registry.entrySet()) {
			Job job = entry.getValue();
			boolean madeChange = false;
			JobProfile jobProfile = PlayerJobs.getJobProfile(player, entry.getKey());
			ResourceLocation statToTrack = player.getLevel().registryAccess().registryOrThrow(Registry.CUSTOM_STAT_REGISTRY).get(job.getStatToTrack());
			int statValue = player.getStats().getValue(Stats.CUSTOM, statToTrack);
			MutableComponent jobName = new TranslatableComponent(Util.makeDescriptionId("job", entry.getKey().location()));
			MutableComponent jobDesc = new TranslatableComponent(Util.makeDescriptionId("job", entry.getKey().location())+".desc");
			//If they meet the specified criteria...
			if (statToTrack != null) {
				if (!jobProfile.hasJob()) {
					//Give the player the job
					if (statValue >= job.getAmountToUnlock()) {
						jobProfile.giveJob();
						list.broadcastMessage(new TranslatableComponent("chat.job.earned", player.getDisplayName(), jobDisplay(jobName, jobDesc)), ChatType.SYSTEM, Util.NIL_UUID);
						PlayerSkills.grantSkill(player, CombatRegistries.SKILLS.getValue(job.jobSkills.get(0)), SkillGrantReason.JOB);
						madeChange = true;
					}
				} else {
					//Add experience into the job
					if (statValue - job.getAmountToUnlock() > (jobProfile.getExperience() * job.getAmountForExperience())) {
						jobProfile.addExperience(1);
						if (job.handleGivingStatXP())
							PlayerAttributeLevels.addExperience(player, jobProfile.getLevel());
						madeChange = true;
					}
					//Increase the level of the job
					if (jobProfile.getLevel() < job.getMaximumLevel() && jobProfile.getExperience() >= job.levels.get(jobProfile.getLevel()-1)) {
						jobProfile.addLevel(1);
						int jobLevel = jobProfile.getLevel();
						list.broadcastMessage(new TranslatableComponent("chat.job.upgraded", player.getDisplayName(), jobDisplay(jobName, jobDesc), jobLevel), ChatType.SYSTEM, Util.NIL_UUID);
						if (job.jobSkills.size() >= jobLevel && job.jobSkills.get(jobLevel-1) != null) {
							PlayerSkills.grantSkill(player, CombatRegistries.SKILLS.getValue(job.jobSkills.get(jobLevel-1)), SkillGrantReason.JOB);
						}
						madeChange = true;
					}
				}
			}

			if (madeChange)
				PlayerJobs.setJobProfile(player, entry.getKey(), jobProfile);
		}
	}
	
	public static Component jobDisplay(MutableComponent jobName, MutableComponent jobDescription) {
		Component component = jobName;
        ChatFormatting chatformatting = /*pDisplay.getFrame().getChatColor()*/ChatFormatting.GREEN;
        Component component1 = ComponentUtils.mergeStyles(component.copy(), Style.EMPTY.withColor(chatformatting)).append("\n").append(jobDescription);
        Component component2 = component.copy().withStyle((p_138316_) -> {
           return p_138316_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component1));
        });
        return ComponentUtils.wrapInSquareBrackets(component2).withStyle(chatformatting);
	}
}
