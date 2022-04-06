package com.stereowalker.combat.world.spellcraft;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.EffectSpell;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.Spell.CastType;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.IForgeRegistry;

public class Spells {
	public static List<Spell> SPELLS = new ArrayList<Spell>();
	public static final Spell NONE = register("none", new EmptySpell(SpellCategory.NONE, Rank.NONE, CastType.SELF, 0.0F, 0, 0));
//----------------------------------------[Fire]----------------------------------------\\
	//Basic
	public static final Spell EXPLOSION = register("explosion", new ExplosionSpell(SpellCategory.FIRE, Rank.BASIC, CastType.BLOCK, 0.5F, 0, 20));
	public static final Spell IGNITE = register("ignite", new IgniteSpell(SpellCategory.FIRE, Rank.BASIC, 0.5F, 0, 20));
	//Novice
	public static final Spell EXPLOSION_TRAP = register("explosion_trap", new ExplosionTrapSpell(SpellCategory.FIRE, Rank.NOVICE, CastType.TRAP, 2.0F, 5, 20));
	public static final Spell FLAME_BURST = register("flame_burst", new FlameBurstSpell(SpellCategory.FIRE, Rank.NOVICE, CastType.TRAP, 2.0F, 5, 20));
	public static final Spell FIRE_BOLT = register("fire_bolt", new BoltSpell(SpellCategory.FIRE, Rank.NOVICE, 0.2F, 0));
	//Apprentice
	public static final Spell FIRE_SKIN = register("fire_skin", new EffectSpell(SpellCategory.FIRE, Rank.APPRENTICE, 1.5F, MobEffects.FIRE_RESISTANCE, 0, 20));
	public static final Spell RING_OF_FIRE = register("ring_of_fire", new RingOfFireSpell(SpellCategory.FIRE, Rank.APPRENTICE, 0.04F, 60, 1, false, 20));
	//Master
	public static final Spell MASTER_FIRE_BOLT = register("master_fire_bolt", new BoltSpell(SpellCategory.FIRE, Rank.MASTER, 2.0F, 1));
	//God
//----------------------------------------[Water]----------------------------------------\\
	//Novice
	public static final Spell ICE_SPIKE = register("ice_spike", new BoltSpell(SpellCategory.WATER, Rank.NOVICE, 0.2F, 0));
	//Apprentice
	public static final Spell WATER_BREATHING = register("water_breathing", new EffectSpell(SpellCategory.WATER, Rank.APPRENTICE, 2.5F, MobEffects.WATER_BREATHING, 0, 20));
	public static final Spell WATER_BULLET = register("water_bullet", new BoltSpell(SpellCategory.WATER, Rank.APPRENTICE, 0.4F, 0));
	public static final Spell RING_OF_ICE = register("ring_of_ice", new RingOfIceSpell(SpellCategory.WATER, Rank.APPRENTICE, 0.04F, 60, 1, 20));
	//God
	public static final Spell RAIN = register("rain", new RainSpell(SpellCategory.WATER, Rank.GOD, CastType.SELF, 20.0F, 150, 20));
//----------------------------------------[Lightning]----------------------------------------\\
	//Novice
	public static final Spell LIGHTNING_BOLT = register("lightning_bolt", new BoltSpell(SpellCategory.LIGHTNING, Rank.NOVICE, 0.2F, 0));
	//Apprentice
	public static final Spell LIGHTNING_FIELD = register("lightning_field", new LightningFieldSpell(SpellCategory.LIGHTNING, Rank.APPRENTICE, 0.04F, 60, 1, false, 20));
	//Advanced
	public static final Spell LIGHTNING = register("lightning", new LightningSpell(SpellCategory.LIGHTNING, Rank.ADVANCED, CastType.BLOCK, 5.0F, 0, 20));
	//Master
	public static final Spell THUNDER_CLAP = register("thunder_clap", new ThunderClapSpell(SpellCategory.LIGHTNING, Rank.MASTER, 10.0F, 15, 20, false, 20));
	//God
	public static final Spell STORM = register("storm", new StormSpell(SpellCategory.LIGHTNING, Rank.GOD, CastType.SELF, 20.0F, 150, 20));
//----------------------------------------[Restoration]----------------------------------------\\
	//Novice
	public static final Spell REGENERATION = register("regeneration", new EffectSpell(SpellCategory.RESTORATION, Rank.NOVICE, 1.64F, MobEffects.REGENERATION, 0, 20));
	//Apprentice
	public static final Spell HEALING = register("healing", new HealingSpell(SpellCategory.RESTORATION, Rank.APPRENTICE, CastType.SELF, 5.0F, 10, 20));
	//Advanced
	public static final Spell REPLENISH_HUNGER = register("replenish_hunger", new ReplenishHungerSpell(SpellCategory.RESTORATION, Rank.ADVANCED, CastType.SELF, 0.8F, 10, 20));
	public static final Spell HEALING_AURA = register("healing_aura", new HealingAuraSpell(SpellCategory.RESTORATION, Rank.ADVANCED, 0.04F, 60, 20, true, 20));
	public static final Spell LIFE_DRAIN = register("life_drain", new LifeDrainSpell(SpellCategory.RESTORATION, Rank.ADVANCED, 0.1F, 20));
	//Master
	public static final Spell AOE_HEALING = register("aoe_healing", new AOEEffectSpell(SpellCategory.RESTORATION, Rank.MASTER, 8.0F, 1, 20, () -> MobEffects.HEAL, 2, true, 20));
	public static final Spell AOE_REGENERATION = register("aoe_regeneration", new AOEEffectSpell(SpellCategory.RESTORATION, Rank.MASTER, 8.0F, 1, 20, () -> MobEffects.REGENERATION, 2, true, 20));
//----------------------------------------[Conjuration]----------------------------------------\\
	//Basic
	public static final Spell CONJOUR_AXE = register("conjour_axe", new SummonItemSpell(SpellCategory.CONJURATION, Rank.BASIC, 0.0F, 0, () -> CItems.SOUL_AXE, 20));
	public static final Spell CONJOUR_SHOVEL = register("conjour_shovel", new SummonItemSpell(SpellCategory.CONJURATION, Rank.BASIC, 0.0F, 0, () -> CItems.SOUL_SHOVEL, 20));
	public static final Spell CONJOUR_HOE = register("conjour_hoe", new SummonItemSpell(SpellCategory.CONJURATION, Rank.BASIC, 0.0F, 0, () -> CItems.SOUL_HOE, 20));
	public static final Spell CONJOUR_PICKAXE = register("conjour_pickaxe", new SummonItemSpell(SpellCategory.CONJURATION, Rank.BASIC, 0.0F, 0, () -> CItems.SOUL_PICKAXE, 20));
	public static final Spell FEAR = register("fear", new AOEEffectSpell(SpellCategory.CONJURATION, Rank.BASIC, 0.8F, 1, 20, () -> CMobEffects.FEAR, 1, false, 20));
	//Novice
	public static final Spell CONJOUR_SWORD = register("conjour_sword", new SummonItemSpell(SpellCategory.CONJURATION, Rank.NOVICE, 0.0F, 0, () -> CItems.SOUL_SWORD, 20));
	public static final Spell CONJOUR_BOW = register("conjour_bow", new SummonItemSpell(SpellCategory.CONJURATION, Rank.NOVICE, 0.0F, 0, () -> CItems.SOUL_BOW, 20));
	//Apprentice
	public static final Spell CONJOUR_ARMOR = register("conjour_armor", new SummonArmorSpell(SpellCategory.CONJURATION, Rank.APPRENTICE, 0.0F, 0, () -> CItems.SOUL_HELMET, () -> CItems.SOUL_CHESTPLATE, () -> CItems.SOUL_LEGGINGS, () -> CItems.SOUL_BOOTS, 20));
	//Advanced
	public static final Spell SUMMON_ZOMBIE = register("summon_zombie", new SummonEntitySpell(SpellCategory.CONJURATION, Rank.ADVANCED, 0.0F, 0, CEntityType.ZOMBIE_MINION, 20));
	public static final Spell SUMMON_SKELETON = register("summon_skeleton", new SummonEntitySpell(SpellCategory.CONJURATION, Rank.ADVANCED, 0.0F, 0, CEntityType.SKELETON_MINION, 20));
//----------------------------------------[Earth]----------------------------------------\\
	//Basic
	public static final Spell ROCK_SHOT = register("rock_shot", new BoltSpell(SpellCategory.EARTH, Rank.BASIC, 2.5F, 0));
	public static final Spell REFORGE_STONE = register("reforge_stone", new ReforgeSpell(Rank.BASIC, 2.5F, 0, 20));
	public static final Spell STONE_SWORD_SHOT = register("stone_sword_shot", new SwordShotSpell(SpellCategory.EARTH, Rank.BASIC, CastType.SELF, 9.0F, 18, 20, Items.STONE_SWORD));
	//Novice
	public static final Spell SMALL_METEOR = register("small_meteor", new MeteorSpell(Rank.NOVICE, 2.5F, 0, 20));
	public static final Spell REFORGE_GOLD = register("reforge_gold", new ReforgeSpell(Rank.NOVICE, 5.0F, 0, 20));
	public static final Spell GOLD_SWORD_SHOT = register("gold_sword_shot", new SwordShotSpell(SpellCategory.EARTH, Rank.NOVICE, CastType.SELF, 9.0F*2, 18, 20, Items.GOLDEN_SWORD));
	//Apprentice
	public static final Spell METEOR = register("meteor", new MeteorSpell(Rank.APPRENTICE, 5.0F, 0, 20));
	public static final Spell REFORGE_IRON = register("reforge_iron", new ReforgeSpell(Rank.APPRENTICE, 10.0F, 0, 20));
	public static final Spell IRON_SWORD_SHOT = register("iron_sword_shot", new SwordShotSpell(SpellCategory.EARTH, Rank.APPRENTICE, CastType.SELF, 9.0F*4, 18, 20, Items.IRON_SWORD));
	//Advanced
	public static final Spell LARGE_METEOR = register("large_meteor", new MeteorSpell(Rank.ADVANCED, 10.0F, 0, 20));
	public static final Spell REFORGE_DIAMOND = register("reforge_diamond", new ReforgeSpell(Rank.ADVANCED, 20.0F, 0, 20));
	public static final Spell DIAMOND_SWORD_SHOT = register("diamond_sword_shot", new SwordShotSpell(SpellCategory.EARTH, Rank.ADVANCED, CastType.SELF, 9.0F*8, 18, 20, Items.DIAMOND_SWORD));
	//Master
	public static final Spell REFORGE_NETHERITE = register("reforge_netherite", new ReforgeSpell(Rank.MASTER, 40.0F, 0, 20));
	public static final Spell NETHERITE_SWORD_SHOT = register("netherite_sword_shot", new SwordShotSpell(SpellCategory.EARTH, Rank.MASTER, CastType.SELF, 9.0F*16, 18, 20, Items.NETHERITE_SWORD));
	//God
	public static final Spell METEOR_SHOWER = register("meteor_shower", new MeteorSpell(Rank.GOD, 40.0F, 150, 20));
//----------------------------------------[Wind]----------------------------------------\\
	//Basic
	public static final Spell LEVITATION = register("levitation", new LevitationSpell(SpellCategory.WIND, Rank.BASIC, CastType.SELF, 0.8F, 1, 20));
	//Novice
	public static final Spell AIR_BULLET = register("air_bullet", new BoltSpell(SpellCategory.WIND, Rank.NOVICE, 0.2F, 0));
	public static final Spell AIR_BURST = register("air_burst", new AirBurstSpell(SpellCategory.WIND, Rank.NOVICE, CastType.TRAP, 2.0F, 5, 20));
//----------------------------------------[Nature]----------------------------------------\\
	//Novice
	public static final Spell POISON = register("poison", new PoisonSpell(SpellCategory.NATURE, Rank.NOVICE, 0.8F, 0));
	//Apprentice
	public static final Spell GROW = register("grow", new GrowSpell(SpellCategory.NATURE, Rank.APPRENTICE, 0.2F, 0, 20));
	public static final Spell WITCHES_GARDEN = register("witches_garden", new WitchesGardenSpell(SpellCategory.NATURE, Rank.APPRENTICE, 0.04F, 60, 1, false, 20));
	//Master
	public static final Spell PARALYSIS = register("paralysis", new ParalysisSpell(SpellCategory.NATURE, Rank.MASTER, 1.0F, 0));
//----------------------------------------[Space]----------------------------------------\\
	//Novice
	public static final Spell TELEPORT = register("teleport", new TeleportSpell(SpellCategory.SPACE, Rank.NOVICE, CastType.BLOCK, 0.2F, 3, 20));
	//Advanced
	public static final Spell RESPAWN = register("respawn", new RespawnSpell(SpellCategory.SPACE, Rank.ADVANCED, CastType.SELF, 5.0F, 100, 20));
	public static final Spell BLACK_HOLE = register("black_hole", new SummonEntitySpell(SpellCategory.SPACE, Rank.ADVANCED, 0.0F, 1, CEntityType.BLACK_HOLE, 20));
//----------------------------------------[Enhancement]----------------------------------------\\
	//Basic
	public static final Spell BASIC_SPEED_BOOST = register("basic_speed_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.BASIC, 0.6F, MobEffects.MOVEMENT_SPEED, 0, 20));
	public static final Spell BASIC_JUMP_BOOST = register("basic_jump_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.BASIC, 0.6F, MobEffects.JUMP, 0, 20));
	public static final Spell BASIC_STRENGTH_BOOST = register("basic_strength_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.BASIC, 0.6F, MobEffects.DAMAGE_BOOST, 0, 20));
	//Novice
	public static final Spell OAKFLESH = register("oakflesh", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.NOVICE, 1.2F, MobEffects.DAMAGE_RESISTANCE, 0, 20));
	public static final Spell NOVICE_SPEED_BOOST = register("novice_speed_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.NOVICE, 1.2F, MobEffects.MOVEMENT_SPEED, 1, 20));
	public static final Spell NOVICE_JUMP_BOOST = register("novice_jump_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.NOVICE, 1.2F, MobEffects.JUMP, 1, 20));
	public static final Spell NOVICE_STRENGTH_BOOST = register("novice_strength_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.NOVICE, 1.2F, MobEffects.DAMAGE_BOOST, 1, 20));
	//Apprentice
	public static final Spell STONEFLESH = register("stoneflesh", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 2.4F, MobEffects.DAMAGE_RESISTANCE, 1, 20));
	public static final Spell APPRENTICE_SPEED_BOOST = register("apprentice_speed_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 2.4F, MobEffects.MOVEMENT_SPEED, 2, 20));
	public static final Spell APPRENTICE_JUMP_BOOST = register("apprentice_jump_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 2.4F, MobEffects.JUMP, 2, 20));
	public static final Spell APPRENTICE_STRENGTH_BOOST = register("apprentice_strength_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 2.4F, MobEffects.DAMAGE_BOOST, 2, 20));
	//Advanced
	public static final Spell IRONFLESH = register("ironflesh", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 4.8F, MobEffects.DAMAGE_RESISTANCE, 2, 20));
	public static final Spell ADVANCED_SPEED_BOOST = register("advanced_speed_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 4.8F, MobEffects.MOVEMENT_SPEED, 3, 20));
	public static final Spell ADVANCED_JUMP_BOOST = register("advanced_jump_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 4.8F, MobEffects.JUMP, 3, 20));
	public static final Spell ADVANCED_STRENGTH_BOOST = register("advanced_strength_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.APPRENTICE, 4.8F, MobEffects.DAMAGE_BOOST, 3, 20));
	//Master
	public static final Spell DIAMONDFLESH = register("diamondflesh", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.MASTER, 9.6F, MobEffects.DAMAGE_RESISTANCE, 3, 20));
	public static final Spell MASTER_SPEED_BOOST = register("master_speed_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.MASTER, 9.6F, MobEffects.MOVEMENT_SPEED, 4, 20));
	public static final Spell MASTER_JUMP_BOOST = register("master_jump_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.MASTER, 9.6F, MobEffects.JUMP, 4, 20));
	public static final Spell MASTER_STRENGTH_BOOST = register("master_strength_boost", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.MASTER, 9.6F, MobEffects.DAMAGE_BOOST, 4, 20));
	//God
	public static final Spell GOD_SKIN = register("god_skin", new EffectSpell(SpellCategory.ENHANCEMENT, Rank.GOD, 40.0F, MobEffects.DAMAGE_RESISTANCE, 200, 20));
	//----------------------------------------[Blood]----------------------------------------\\
	public static final Spell BLOOD_SWORD = register("blood_sword", new SummonItemSpell(SpellCategory.BLOOD, Rank.BASIC, 0.12F, 0, () -> CItems.BLOOD_SWORD, 20));
	public static final Spell BLOOD_AXE = register("blood_axe", new SummonItemSpell(SpellCategory.BLOOD, Rank.BASIC, 0.14F, 0, () -> CItems.BLOOD_AXE, 20));
	
	public static Spell register(String name, Spell spell) {
		spell.setRegistryName(Combat.getInstance().location(name));
		SPELLS.add(spell);
		return spell;
	}
	
	public static void registerAll(IForgeRegistry<Spell> registry) {
		for(Spell spell : SPELLS) {
			registry.register(spell);
			Combat.debug("Spell: \""+spell.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Spells Registered");
	}
}
