package com.stereowalker.combat.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.SpellCategory.ClassType;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.AbominationEnums.AbominationType;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.network.server.SAbominationPacket;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.util.CDamageSource;
import com.stereowalker.combat.world.CGameRules;
import com.stereowalker.unionlib.util.NBTHelper;
import com.stereowalker.unionlib.util.math.UnionMathHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

public class CombatEntityStats {
	public static String manaID = "Mana";
	public static String manaColorID = "ManaColor";
	
	
	public static String allyID = "Ally";
	public static String alliesID = "Allies";
	public static String xpStoredID = "XPStored";
	public static String limiterId = "Limiter";
	public static String vampireId = "IsVampire";
	public static String elementalAffinityID = "ElementalAffinity";
	public static String subElementalAffinity1ID = "SubElementalAffinity1";
	public static String subElementalAffinity2ID = "SUbElementalAffinity2";
	public static String lifeAffinityID = "LifeAffinity";
	public static String specialAffinityID = "SpecialAffinity";
	public static String etherionTowerPositionID = "ETowPos";

	public static String abominationID = "Abominaion";
	
	public static String spellStatsID = "SpellStats";

	public static String acrotlestPortalCounterID = "AcrotlestPortalCounter";
	public static String inAcrotlestPortalID = "InAcrotlestPortal";

	public static String lockedYawID = "lockedYaw";
	public static String lockedPitchID = "lockedPitch";
	
	public static String resistID = "Resist";
	
	public static SpellStats getSpellStats(LivingEntity entity, Spell spell) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(spellStatsID)) {
				ListNBT stats = compound.getList(spellStatsID, 10);
				for (int i = 0; i < stats.size(); i++) {
					CompoundNBT nbt = stats.getCompound(i);
					if (SpellUtil.getSpellFromName(nbt.getString("Spell")) == spell)
						return SpellStats.read(nbt);
				}
			}
		}
		return new SpellStats(spell, false, 0, 0, 0);
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
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(resistID)) {
				return compound.getFloat(resistID);
			}
		}
		return 0;
	}
	public static void setResist(LivingEntity entity, double mana) {
		CompoundNBT compound = getModNBT(entity);
		compound.putDouble(resistID, mana);
	}
	//Getters

	public static float getMana(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(manaID)) {
				return compound.getFloat(manaID);
			}
		}
		return 0;
	}
	
	public static int getManaColor(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
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
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(xpStoredID)) {
				return compound.getInt(xpStoredID);
			}
		}
		return 0;
	}

	public static boolean isLimiterOn(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			return compound.getBoolean(limiterId);
		}
		return false;
	}

	public static boolean isVampire(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			return compound.getBoolean(vampireId);
		}
		return false;
	}

	public static AbominationType getAbomination(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			return AbominationType.byId(compound.getInt(abominationID));
		}
		return AbominationType.NORMAL;
	}

	public static SpellCategory getElementalAffinity(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(elementalAffinityID)) {
				return SpellCategory.byId(compound.getInt(elementalAffinityID));
			}
		}
		return SpellCategory.NONE;
	}

	public static SpellCategory getSubElementalAffinity1(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(subElementalAffinity1ID)) {
				return SpellCategory.byId(compound.getInt(subElementalAffinity1ID));
			}
		}
		return SpellCategory.NONE;
	}

	public static SpellCategory getSubElementalAffinity2(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(subElementalAffinity2ID)) {
				return SpellCategory.byId(compound.getInt(subElementalAffinity2ID));
			}
		}
		return SpellCategory.NONE;
	}

	public static SpellCategory getLifeAffinity(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(lifeAffinityID)) {
				return SpellCategory.byId(compound.getInt(lifeAffinityID));
			}
		}
		return SpellCategory.NONE;
	}

	public static SpellCategory getSpecialAffinity(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(specialAffinityID)) {
				return SpellCategory.byId(compound.getInt(specialAffinityID));
			}
		}
		return SpellCategory.NONE;
	}

	public static List<PlayerEntity> getAllies(LivingEntity entity) {
		List<PlayerEntity> allies = new ArrayList<PlayerEntity>();
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(alliesID)) {
				ListNBT list = compound.getList(alliesID, 10);
				for (int i = 0; i < list.size(); i++) {
					CompoundNBT nbt = list.getCompound(i);
					allies.add(entity.world.getPlayerByUuid(nbt.getUniqueId(allyID+i)));
				}
			}
		}
		return allies;
	}

	public static BlockPos getNearestEtherionTowerPos(Entity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT((LivingEntity) entity);
			ListNBT listnbt = compound.getList(etherionTowerPositionID, 6);
			if (compound != null && compound.contains(etherionTowerPositionID)) {
				return new BlockPos(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
			}
		}
		return BlockPos.ZERO;
	}

	public static boolean isInAcrotlestPortal(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			return compound.getBoolean(inAcrotlestPortalID);
		}
		return false;
	}

	public static int getAcrotlestPortalCounter(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(acrotlestPortalCounterID)) {
				return compound.getInt(acrotlestPortalCounterID);
			}
		}
		return 0;
	}

	public static float getLockedYaw(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(lockedYawID)) {
				return compound.getFloat(lockedYawID);
			}
		}
		return 0;
	}

	public static float getLockedPitch(LivingEntity entity) {
		if(entity != null) {
			CompoundNBT compound = getModNBT(entity);
			if (compound != null && compound.contains(lockedPitchID)) {
				return compound.getFloat(lockedPitchID);
			}
		}
		return 0;
	}

	//--Setters--\\
	
	public static void setSpellStats(LivingEntity entity, SpellStats spellStats) {
		CompoundNBT compound = getModNBT(entity);
		
		ListNBT list = new ListNBT();
		
		for (Spell spell2 : CombatRegistries.SPELLS) {
			if (spell2 == spellStats.getSpell()) {
				list.add(spellStats.write(new CompoundNBT()));
			} else {
				list.add(getSpellStats(entity, spell2).write(new CompoundNBT()));
			}
		}
		
		compound.put(spellStatsID, list);
	}

	public static void setMana(LivingEntity entity, float mana) {
		CompoundNBT compound = getModNBT(entity);
		compound.putFloat(manaID, mana);
	}

	public static void setManaColor(LivingEntity entity, int mana) {
		CompoundNBT compound = getModNBT(entity);
		compound.putInt(manaColorID, mana);
	}

	public static void setStoredXP(LivingEntity entity, int mana) {
		CompoundNBT compound = getModNBT(entity);
		compound.putInt(xpStoredID, mana);
	}

	public static void setLimiter(LivingEntity entity, boolean speedActivated) {
		CompoundNBT compound = getModNBT(entity);
		compound.putBoolean(limiterId, speedActivated);
	}

	public static void setVampire(LivingEntity entity, boolean vampire) {
		CompoundNBT compound = getModNBT(entity);
		compound.putBoolean(vampireId, vampire);
	}

	public static void setAbomination(LivingEntity entity, AbominationType category) {
		CompoundNBT compound = getModNBT(entity);
		compound.putInt(abominationID, category.ordinal());
	}

	public static void setElementalAffinity(LivingEntity entity, SpellCategory category) {
		CompoundNBT compound = getModNBT(entity);
		if (category.getClassType() == ClassType.ELEMENTAL || category == SpellCategory.NONE) compound.putInt(elementalAffinityID, category.ordinal());
	}

	public static void setSubElementalAffinity1(LivingEntity entity, SpellCategory category) {
		CompoundNBT compound = getModNBT(entity);
		if (category.getClassType() == ClassType.ELEMENTAL || category == SpellCategory.NONE) compound.putInt(subElementalAffinity1ID, category.ordinal());
	}

	public static void setSubElementalAffinity2(LivingEntity entity, SpellCategory category) {
		CompoundNBT compound = getModNBT(entity);
		if (category.getClassType() == ClassType.ELEMENTAL || category == SpellCategory.NONE) compound.putInt(subElementalAffinity2ID, category.ordinal());
	}

	public static void setLifeAffinity(LivingEntity entity, SpellCategory category) {
		CompoundNBT compound = getModNBT(entity);
		if (category.getClassType() == ClassType.LIFE || category == SpellCategory.NONE) compound.putInt(lifeAffinityID, category.ordinal());
	}

	public static void setSpecialAffinity(LivingEntity entity, SpellCategory category) {
		CompoundNBT compound = getModNBT(entity);
		if (category.getClassType() == ClassType.SPECIAL || category == SpellCategory.NONE) compound.putInt(specialAffinityID, category.ordinal());
	}

	public static void setAllies(LivingEntity entity, List<PlayerEntity> allies) {
		CompoundNBT compound = getModNBT(entity);
		ListNBT list = new ListNBT();
		for (int i = 0; i < allies.size(); i++) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putUniqueId(allyID+i, allies.get(i).getUniqueID());
			list.add(nbt);
		}
		compound.put(alliesID, list);
	}

	public static void setNearestEtherionTowerPos(LivingEntity entity, BlockPos pos) {
		CompoundNBT compound = getModNBT(entity);
		compound.put(etherionTowerPositionID, NBTHelper.newDoubleNBTList(pos.getX(), pos.getY(), pos.getZ()));
	}

	public static void setInAcrotlestPortal(Entity entity, boolean inPortal) {
		CompoundNBT compound = getModNBT(entity);
		compound.putBoolean(inAcrotlestPortalID, inPortal);
	}

	public static void setAcrotlestPortalCounter(LivingEntity entity, int mana) {
		CompoundNBT compound = getModNBT(entity);
		compound.putInt(acrotlestPortalCounterID, mana);
	}

	public static void setLockedYaw(LivingEntity entity, float rotationYaw) {
		CompoundNBT compound = getModNBT(entity);
		compound.putFloat(lockedYawID, rotationYaw);
	}

	public static void setLockedPitch(LivingEntity entity, float rotationPitch) {
		CompoundNBT compound = getModNBT(entity);
		compound.putFloat(lockedPitchID, rotationPitch);
	}

	//Adders

	public static boolean addMana(LivingEntity entity, float mana) {
		if (entity != null) {
			if (!entity.world.getGameRules().getBoolean(CGameRules.DO_INFINITE_MANA)) {
				float newValue = getMana(entity)+mana;
				setMana(entity, (float) MathHelper.clamp(newValue, 0, entity.getAttributeValue(CAttributes.MAX_MANA)));
				if (newValue < 0) {
					entity.attackEntityFrom(CDamageSource.MANA_DRAIN, -mana);
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

	public static void addStatsToPlayerOnSpawn(PlayerEntity player) {
		if (player != null) {
			CompoundNBT compound;
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
					setSpellStats(player, new SpellStats(Spells.NONE, false, 0, 0, 0));
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
				if (!compound.contains(vampireId)) {
					setVampire(player, false);
				}
				if (!compound.contains(resistID)) {
					setResist(player, 0.0D);
				}
				if (!compound.contains(etherionTowerPositionID)) {
					setNearestEtherionTowerPos(player, BlockPos.ZERO);
				}
				if (!compound.contains(inAcrotlestPortalID)) {
					setInAcrotlestPortal(player, false);
				}
				if (!compound.contains(acrotlestPortalCounterID)) {
					setAcrotlestPortalCounter(player, 0);
				}
				Random random = new Random();
				if (compound.contains(elementalAffinityID)) {
					if (getElementalAffinity(player) != SpellCategory.NONE) {
						int elemental = 0;
						for (SpellCategory cat : SpellCategory.values()) {
							if (getElementalAffinity(player) == cat) break;
							elemental++;
						}
						elemental-=1;
						if ((!compound.contains(subElementalAffinity1ID) && !compound.contains(subElementalAffinity2ID)) || (getSubElementalAffinity1(player) == SpellCategory.NONE && getSubElementalAffinity2(player) == SpellCategory.NONE)) {
							int a = random.nextInt(5);
							while (a == elemental) a = random.nextInt(5);
							compound.putInt(subElementalAffinity1ID, a+1);
							Combat.debug("Set " + name + "'s 1st Sub Elemental Affinity to " + getSubElementalAffinity1(player));
							int b = random.nextInt(5);
							while (b == a || b == elemental) b = random.nextInt(5);
							compound.putInt(subElementalAffinity2ID, b+1);
							Combat.debug("Set " + name + "'s 2nd Elemental Affinity to " + getSubElementalAffinity2(player));
						}
					}
				}
			}
		}
	}

	public static void addStatsToMonstersOnSpawn(MonsterEntity monster, Random random) {
		CompoundNBT compound;
		compound = getOrCreateModNBT(monster);
		if(monster.isAlive()) {
			if(monster.isServerWorld()) {
				if (!compound.contains(CombatEntityStats.abominationID)) {
					boolean isAbomination = UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.abominationChance.get());
					boolean isRareAbomination = UnionMathHelper.probabilityCheck(Config.MAGIC_COMMON.rareAbominationChance.get());
					if (isAbomination) {
						int abominationRand = random.nextInt(AbominationType.values().length-1);
						compound.putInt(CombatEntityStats.abominationID, abominationRand);
						Combat.debug("A "+AbominationType.byId(abominationRand)+" TYPE ABOMINATION SPAWNED");
						if (isRareAbomination)Combat.debug("THIS ONE IS A RARE");
						String apendedName = isRareAbomination ? "Rare Abomination" : "Abomination";
						monster.setCustomName(new StringTextComponent(apendedName+" ").appendSibling(monster.getName()));
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
					List<PlayerEntity> visiblePlayers = monster.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(monster.getPosition().offset(Direction.NORTH, visualRadius).offset(Direction.WEST, visualRadius).offset(Direction.UP, visualRadius), monster.getPosition().offset(Direction.SOUTH, visualRadius).offset(Direction.EAST, visualRadius).offset(Direction.DOWN, visualRadius)));
					for (PlayerEntity player : monster.world.getPlayers()) {
						if (visiblePlayers.contains(player)) {
							Combat.getInstance().channel.sendTo(new SAbominationPacket(monster.getPosXRandom(0.5D), monster.getPosYRandom() - 0.25D, monster.getPosZRandom(0.5D)), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
						}
					}
				}
			}
		}
	}

	public static String getModDataString() {
		return Combat.getInstance().locationString("PlayerData");
	}

	public static CompoundNBT getModNBT(Entity entity) {
		return entity.getPersistentData().getCompound(getModDataString());
	}

	public static CompoundNBT getOrCreateModNBT(Entity entity) {
		if (!entity.getPersistentData().contains(getModDataString(), 10)) {
			entity.getPersistentData().put(getModDataString(), new CompoundNBT());
		}
		return entity.getPersistentData().getCompound(getModDataString());
	}

	public static void setModNBT(CompoundNBT nbt, Entity entity) {
		entity.getPersistentData().put(getModDataString(), nbt);
	}
}
