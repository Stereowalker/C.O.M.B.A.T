package com.stereowalker.combat.world.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.network.protocol.game.ClientboundAbominationPacket;
import com.stereowalker.combat.world.damagesource.CDamageSource;
import com.stereowalker.combat.world.entity.AbominationEnums.AbominationType;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.level.CGameRules;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.unionlib.util.NBTHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;

public class CombatEntityStats {
	public static String manaID = "Mana";
	public static String manaColorID = "ManaColor";
	
	
	public static String allyID = "Ally";
	public static String alliesID = "Allies";
	public static String xpStoredID = "XPStored";
	public static String limiterId = "Limiter";
	public static String vampireId = "IsVampire";
	public static String manabornBonusId = "IsManaborn";
	public static String primevalAffinityID = "PrimevalAffinity";
	public static String etherionTowerPositionID = "ETowPos";

	public static String abominationID = "Abominaion";
	
	public static String spellStatsID = "SpellStats";

	public static String acrotlestPortalCounterID = "AcrotlestPortalCounter";
	public static String inAcrotlestPortalID = "InAcrotlestPortal";

	public static String lockedYawID = "lockedYaw";
	public static String lockedPitchID = "lockedPitch";
	
	public static String resistID = "Resist";
	public static String motionID = "Motion";
	
	public static String holdFlagID = "HoldFlag";
	
	public static SpellStats getSpellStats(LivingEntity entity, Spell spell) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(spellStatsID)) {
				ListTag stats = compound.getList(spellStatsID, 10);
				for (int i = 0; i < stats.size(); i++) {
					CompoundTag nbt = stats.getCompound(i);
					if (SpellUtil.getSpellFromName(nbt.getString("Spell")) == spell)
						return SpellStats.read(nbt);
				}
			}
		}
		return new SpellStats(spell, false, false, 0, 0, 0);
	}
	
	public static Map<Spell,SpellStats> getSpellStats(LivingEntity entity) {
		Map<Spell,SpellStats> map = new HashMap<Spell, SpellStats>();
		for (Spell spell : CombatRegistries.SPELLS) {
			map.put(spell, getSpellStats(entity, spell));
		}
		return map;
	}
	
	public static double getResist(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(resistID)) {
				return compound.getFloat(resistID);
			}
		}
		return 0;
	}
	public static void setResist(LivingEntity entity, double mana) {
		CompoundTag compound = getModNBT(entity);
		compound.putDouble(resistID, mana);
	}
	//Getters

	public static float getMana(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(manaID)) {
				return compound.getFloat(manaID);
			}
		}
		return 0;
	}
	
	public static int getManaColor(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(manaColorID)) {
				return compound.getInt(manaColorID);
			}
		}
		return 0;
	}
	
	public static int getManaColorR(LivingEntity entity) {
		return getManaColor(entity) >> 16;
	}
	
	public static int getManaColorG(LivingEntity entity) {
		return getManaColor(entity) >> 8 & 255;
	}
	
	public static int getManaColorB(LivingEntity entity) {
		return getManaColor(entity) & 255;
	}

	public static int getStoredXP(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(xpStoredID)) {
				return compound.getInt(xpStoredID);
			}
		}
		return 0;
	}

	public static boolean isLimiterOn(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return compound.getBoolean(limiterId);
		}
		return false;
	}

	public static boolean isHoldFlagActive(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return compound.getBoolean(holdFlagID);
		}
		return false;
	}

	public static boolean isVampire(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return compound.getBoolean(vampireId);
		}
		return false;
	}

	public static boolean hasManabornBonus(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return compound.getBoolean(manabornBonusId);
		}
		return false;
	}

	public static AbominationType getAbomination(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return AbominationType.byId(compound.getInt(abominationID));
		}
		return AbominationType.NORMAL;
	}

	public static SpellCategory getPrimevalAffinity(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(primevalAffinityID)) {
				return SpellCategory.byId(compound.getInt(primevalAffinityID));
			}
		}
		return SpellCategory.NONE;
	}

	public static List<Player> getAllies(LivingEntity entity) {
		List<Player> allies = new ArrayList<Player>();
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(alliesID)) {
				ListTag list = compound.getList(alliesID, 10);
				for (int i = 0; i < list.size(); i++) {
					CompoundTag nbt = list.getCompound(i);
					allies.add(entity.level.getPlayerByUUID(nbt.getUUID(allyID+i)));
				}
			}
		}
		return allies;
	}

	public static BlockPos getNearestEtherionTowerPos(Entity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT((LivingEntity) entity);
			ListTag listnbt = compound.getList(etherionTowerPositionID, 6);
			if (compound != null && compound.contains(etherionTowerPositionID)) {
				return new BlockPos(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
			}
		}
		return BlockPos.ZERO;
	}

	public static boolean isInAcrotlestPortal(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			return compound.getBoolean(inAcrotlestPortalID);
		}
		return false;
	}

	public static int getAcrotlestPortalCounter(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(acrotlestPortalCounterID)) {
				return compound.getInt(acrotlestPortalCounterID);
			}
		}
		return 0;
	}

	public static float getLockedYaw(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(lockedYawID)) {
				return compound.getFloat(lockedYawID);
			}
		}
		return 0;
	}

	public static float getLockedPitch(LivingEntity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT(entity);
			if (compound != null && compound.contains(lockedPitchID)) {
				return compound.getFloat(lockedPitchID);
			}
		}
		return 0;
	}

	public static Vec3 getClientMotion(Entity entity) {
		if(entity != null) {
			CompoundTag compound = getModNBT((LivingEntity) entity);
			ListTag listnbt = compound.getList(motionID, 6);
			if (compound != null && compound.contains(motionID)) {
				return new Vec3(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
			}
		}
		return Vec3.ZERO;
	}

	//--Setters--\\
	
	public static void setSpellStats(LivingEntity entity, SpellStats spellStats) {
		CompoundTag compound = getModNBT(entity);
		
		ListTag list = new ListTag();
		
		for (Spell spell2 : CombatRegistries.SPELLS) {
			if (spell2 == spellStats.getSpell()) {
				list.add(spellStats.write(new CompoundTag()));
			} else {
				list.add(getSpellStats(entity, spell2).write(new CompoundTag()));
			}
		}
		
		compound.put(spellStatsID, list);
	}

	public static void setMana(LivingEntity entity, float mana) {
		CompoundTag compound = getModNBT(entity);
		compound.putFloat(manaID, mana);
	}

	public static void setManaColor(LivingEntity entity, int mana) {
		CompoundTag compound = getModNBT(entity);
		compound.putInt(manaColorID, mana);
	}

	public static void setStoredXP(LivingEntity entity, int mana) {
		CompoundTag compound = getModNBT(entity);
		compound.putInt(xpStoredID, mana);
	}

	public static void setLimiter(LivingEntity entity, boolean speedActivated) {
		CompoundTag compound = getModNBT(entity);
		compound.putBoolean(limiterId, speedActivated);
	}

	public static void setVampire(LivingEntity entity, boolean vampire) {
		CompoundTag compound = getModNBT(entity);
		compound.putBoolean(vampireId, vampire);
	}

	public static void setManabornBonus(LivingEntity entity, boolean manaborn) {
		CompoundTag compound = getModNBT(entity);
		compound.putBoolean(manabornBonusId, manaborn);
	}

	public static void setAbomination(LivingEntity entity, AbominationType category) {
		CompoundTag compound = getModNBT(entity);
		compound.putInt(abominationID, category.ordinal());
	}

	public static void setPrimevalAffinity(LivingEntity entity, SpellCategory category) {
		CompoundTag compound = getModNBT(entity);
		if (category.getClassType() == ClassType.PRIMEVAL || category == SpellCategory.NONE) compound.putInt(primevalAffinityID, category.ordinal());
	}

	public static void setAllies(LivingEntity entity, List<Player> allies) {
		CompoundTag compound = getModNBT(entity);
		ListTag list = new ListTag();
		for (int i = 0; i < allies.size(); i++) {
			CompoundTag nbt = new CompoundTag();
			nbt.putUUID(allyID+i, allies.get(i).getUUID());
			list.add(nbt);
		}
		compound.put(alliesID, list);
	}

	public static void setNearestEtherionTowerPos(LivingEntity entity, BlockPos pos) {
		CompoundTag compound = getModNBT(entity);
		compound.put(etherionTowerPositionID, NBTHelper.newDoubleNBTList(pos.getX(), pos.getY(), pos.getZ()));
	}

	public static void setInAcrotlestPortal(Entity entity, boolean inPortal) {
		CompoundTag compound = getModNBT(entity);
		compound.putBoolean(inAcrotlestPortalID, inPortal);
	}

	public static void setAcrotlestPortalCounter(LivingEntity entity, int mana) {
		CompoundTag compound = getModNBT(entity);
		compound.putInt(acrotlestPortalCounterID, mana);
	}

	public static void setLockedYaw(LivingEntity entity, float rotationYaw) {
		CompoundTag compound = getModNBT(entity);
		compound.putFloat(lockedYawID, rotationYaw);
	}

	public static void setLockedPitch(LivingEntity entity, float rotationPitch) {
		CompoundTag compound = getModNBT(entity);
		compound.putFloat(lockedPitchID, rotationPitch);
	}

	public static void setHoldFlag(Entity entity, boolean hold) {
		CompoundTag compound = getModNBT(entity);
		compound.putBoolean(holdFlagID, hold);
	}

	public static void setClientMotion(LivingEntity entity, Vec3 pos) {
		CompoundTag compound = getModNBT(entity);
		compound.put(motionID, NBTHelper.newDoubleNBTList(pos.x(), pos.y(), pos.z()));
	}

	//Adders

	public static boolean addMana(LivingEntity entity, float mana) {
		if (entity != null) {
			if (!entity.level.getGameRules().getBoolean(CGameRules.DO_INFINITE_MANA)) {
				float newValue = getMana(entity)+mana;
				setMana(entity, (float) Mth.clamp(newValue, 0, entity.getAttributeValue(CAttributes.MAX_MANA)));
				if (newValue < 0) {
					entity.hurt(CDamageSource.MANA_DRAIN, -mana);
				}
				return true;
			} else {
				return true;
			}
		}
		return false;
	}

	public static boolean addStoredXP(LivingEntity entity, int xp) {
		setStoredXP(entity, getStoredXP(entity)+xp);
		return true;
	}

	public static void addStatsToPlayerOnSpawn(Player player) {
		if (player != null) {
			CompoundTag compound;
			compound = getOrCreateModNBT(player);
			String name = player.getScoreboardName();
			if(player.isAlive()) {
				if (!compound.contains(manaID)) {
					setMana(player, (float)player.getAttributeValue(CAttributes.MAX_MANA));
					Combat.debug("Set " + name + "'s mana to " + getMana(player));
				}
				if (!compound.contains(manaColorID)) {
					setManaColor(player, 0);
				}
				if (!compound.contains(lockedYawID)) {
					setLockedYaw(player, 0);
				}
				if (!compound.contains(spellStatsID)) {
					setSpellStats(player, new SpellStats(Spells.NONE, false, false, 0, 0, 0));
				}
				if (!compound.contains(lockedPitchID)) {
					setLockedPitch(player, 0);
				}
				if (!compound.contains(xpStoredID)) {
					setStoredXP(player, 0);
					Combat.debug("Set " + name + "'s stored xp to " + getStoredXP(player));
				}
				if (!compound.contains(limiterId)) {
					setLimiter(player, false);
				}
				if (!compound.contains(holdFlagID)) {
					setHoldFlag(player, false);
				}
				if (!compound.contains(vampireId)) {
					setVampire(player, false);
				}
				if (!compound.contains(resistID)) {
					setResist(player, 0.0D);
				}
				if (!compound.contains(manabornBonusId)) {
					setManabornBonus(player, false);
				}
				if (!compound.contains(etherionTowerPositionID)) {
					setNearestEtherionTowerPos(player, BlockPos.ZERO);
				}
				if (!compound.contains(motionID)) {
					setClientMotion(player, Vec3.ZERO);
				}
				if (!compound.contains(inAcrotlestPortalID)) {
					setInAcrotlestPortal(player, false);
				}
				if (!compound.contains(acrotlestPortalCounterID)) {
					setAcrotlestPortalCounter(player, 0);
				}
			}
		}
	}

	public static void addStatsToMonstersOnSpawn(Monster monster, Random random) {
		CompoundTag compound;
		compound = getOrCreateModNBT(monster);
		if(monster.isAlive()) {
			if(monster.isEffectiveAi()) {
				if (!compound.contains(CombatEntityStats.abominationID)) {
					boolean isAbomination = UnionMathHelper.probabilityCheck(Combat.MAGIC_CONFIG.abominationChance);
					boolean isRareAbomination = UnionMathHelper.probabilityCheck(Combat.MAGIC_CONFIG.rareAbominationChance);
					if (isAbomination) {
						int abominationRand = random.nextInt(AbominationType.values().length-1);
						compound.putInt(CombatEntityStats.abominationID, abominationRand);
						Combat.debug("A "+AbominationType.byId(abominationRand)+" TYPE ABOMINATION SPAWNED");
						if (isRareAbomination)Combat.debug("THIS ONE IS A RARE");
						String apendedName = isRareAbomination ? "Rare Abomination" : "Abomination";
						monster.setCustomName(new TextComponent(apendedName+" ").append(monster.getName()));
						double defaultValue = isRareAbomination ? 5.0D : 2.5D;
						double enhancedValue = isRareAbomination ? 10.0D : 6.0D;
						if (!AbominationType.byId(abominationRand).equals(AbominationType.NORMAL)) {
							monster.getAttribute(Attributes.MAX_HEALTH).setBaseValue(monster.getAttribute(Attributes.MAX_HEALTH).getBaseValue() * (AbominationType.byId(abominationRand).equals(AbominationType.HEALTHY) ? enhancedValue : defaultValue));
							monster.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() * (AbominationType.byId(abominationRand).equals(AbominationType.SPEEDY) ? enhancedValue : defaultValue));
							monster.getAttribute(Attributes.ARMOR).setBaseValue(monster.getAttribute(Attributes.ARMOR).getBaseValue() * (AbominationType.byId(abominationRand).equals(AbominationType.ARMOR) ? enhancedValue : defaultValue));
							monster.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(monster.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() * (AbominationType.byId(abominationRand).equals(AbominationType.STRENGTH) ? enhancedValue : defaultValue));
							monster.setHealth((float) monster.getAttribute(Attributes.MAX_HEALTH).getValue());
						}
					} else {
						compound.putInt(CombatEntityStats.abominationID, AbominationType.values().length-1);
					}
				}
				if (CombatEntityStats.getAbomination(monster) != AbominationType.NORMAL) {
					int visualRadius = 10;
					List<Player> visiblePlayers = monster.level.getEntitiesOfClass(Player.class, new AABB(monster.blockPosition().relative(Direction.NORTH, visualRadius).relative(Direction.WEST, visualRadius).relative(Direction.UP, visualRadius), monster.blockPosition().relative(Direction.SOUTH, visualRadius).relative(Direction.EAST, visualRadius).relative(Direction.DOWN, visualRadius)));
					for (Player player : monster.level.players()) {
						if (visiblePlayers.contains(player)) {
							Combat.getInstance().channel.sendTo(new ClientboundAbominationPacket(monster.getRandomX(0.5D), monster.getRandomY() - 0.25D, monster.getRandomZ(0.5D)), ((ServerPlayer) player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
						}
					}
				}
			}
		}
	}

	public static String getModDataString() {
		return Combat.getInstance().locationString("PlayerData");
	}

	public static CompoundTag getModNBT(Entity entity) {
		return entity.getPersistentData().getCompound(getModDataString());
	}

	public static CompoundTag getOrCreateModNBT(Entity entity) {
		if (!entity.getPersistentData().contains(getModDataString(), 10)) {
			entity.getPersistentData().put(getModDataString(), new CompoundTag());
		}
		return entity.getPersistentData().getCompound(getModDataString());
	}

	public static void setModNBT(CompoundTag nbt, Entity entity) {
		entity.getPersistentData().put(getModDataString(), nbt);
	}
}
