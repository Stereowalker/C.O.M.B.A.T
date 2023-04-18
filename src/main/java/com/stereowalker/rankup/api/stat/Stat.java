package com.stereowalker.rankup.api.stat;

import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

public class Stat {

	public static final Codec<Stat> CODEC = RecordCodecBuilder.create((p_67790_) -> {
		return p_67790_.group(
				ResourceLocation.CODEC.fieldOf("type").forGetter((p_160992_) -> {
					return CombatRegistries.STAT_TYPES.getKey(p_160992_.getType());
				}),
				Codec.STRING.fieldOf("uniqueId").forGetter((p_160992_) -> {
					return p_160992_.getUniqueID().toString();
				}), ResourceLocation.CODEC.fieldOf("baseAttribute").forGetter((p_160992_) -> {
					return BuiltInRegistries.ATTRIBUTE.getKey(p_160992_.getBaseAttribute());
				})
				).apply(p_67790_, Stat::new);
	});

	StatType type;
	UUID uniqueId;
	Attribute baseAttribute;

	public Stat(ResourceLocation type, String uniqueId, ResourceLocation baseAttribute) {
		this(CombatRegistries.STAT_TYPES.getValue(type), uniqueId, ForgeRegistries.ATTRIBUTES.getValue(baseAttribute));
	}

	public Stat(StatType type, String uniqueId, Attribute baseAttribute) {
		this.uniqueId = UUID.fromString(uniqueId);
		this.baseAttribute = baseAttribute;
		this.type = type;
	}

	public Stat() {
		this.uniqueId = null;
	}

	public UUID getUniqueID() {
		return uniqueId;
	}

	public Attribute getBaseAttribute() {
		return baseAttribute;
	}

	public StatType getType() {
		return type;
	}

	public static int getExperienceCost(int level) {
		int n = level + 1;
		int a = Combat.RPG_CONFIG.baseXpCost;
		int d = Combat.RPG_CONFIG.xpCostStep;

		int total = (n/2) * ((2*a) + (n - 1) * d);
		return total;
	}

	public static int getExperienceCost(Player player, ResourceKey<Stat> stat) {
		return getExperienceCost(PlayerAttributeLevels.getStatProfile(player, stat).getPoints());
	}

	public int getCurrentPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getPoints();
	}

	public int getAdditionalPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getModifierPoints();
	}

	public int getEffortPoints(Player player) {
		return PlayerAttributeLevels.getStatProfile(player, player.getLevel().registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY).getResourceKey(this).get()).getEffortPoints();
	}
}
