package com.stereowalker.combat.world.entity;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.boss.robin.RobinBoss;
import com.stereowalker.combat.world.entity.magic.Ray;
import com.stereowalker.combat.world.entity.magic.SurroundSpellCircle;
import com.stereowalker.combat.world.entity.magic.TrapSpellCircle;
import com.stereowalker.combat.world.entity.misc.BlackHole;
import com.stereowalker.combat.world.entity.misc.Meteor;
import com.stereowalker.combat.world.entity.monster.Biog;
import com.stereowalker.combat.world.entity.monster.Lichu;
import com.stereowalker.combat.world.entity.monster.RedBiog;
import com.stereowalker.combat.world.entity.monster.SkeletonMinion;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.entity.monster.ZombieCow;
import com.stereowalker.combat.world.entity.monster.ZombieMinion;
import com.stereowalker.combat.world.entity.projectile.ArchArrow;
import com.stereowalker.combat.world.entity.projectile.Bullet;
import com.stereowalker.combat.world.entity.projectile.DiamondArrow;
import com.stereowalker.combat.world.entity.projectile.GoldenArrow;
import com.stereowalker.combat.world.entity.projectile.IronArrow;
import com.stereowalker.combat.world.entity.projectile.MjolnirLightning;
import com.stereowalker.combat.world.entity.projectile.ObsidianArrow;
import com.stereowalker.combat.world.entity.projectile.ProjectileSpell;
import com.stereowalker.combat.world.entity.projectile.ProjectileSword;
import com.stereowalker.combat.world.entity.projectile.QuartzArrow;
import com.stereowalker.combat.world.entity.projectile.Shuriken;
import com.stereowalker.combat.world.entity.projectile.SoulArrow;
import com.stereowalker.combat.world.entity.projectile.ThrownChakram;
import com.stereowalker.combat.world.entity.projectile.ThrownDagger;
import com.stereowalker.combat.world.entity.projectile.ThrownSpear;
import com.stereowalker.combat.world.entity.projectile.WoodenArrow;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;

public class CEntityType extends net.minecraftforge.registries.ForgeRegistryEntry<EntityType<?>> {
	private static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<EntityType<?>>();
	
	public static final EntityType<Vampire> VAMPIRE = register("vampire", EntityType.Builder.<Vampire>of(Vampire::new, MobCategory.MONSTER).sized(0.6F, 1.95F));
	public static final EntityType<ZombieCow> ZOMBIE_COW = register("zombie_cow", EntityType.Builder.<ZombieCow>of(ZombieCow::new, MobCategory.MONSTER).sized(0.9F, 1.4F));
	public static final EntityType<RobinBoss> ROBIN = register("robin", EntityType.Builder.<RobinBoss>of(RobinBoss::new, MobCategory.MONSTER).sized(0.6F, 1.99F));
	public static final EntityType<Bullet> BULLET = register("bullet", EntityType.Builder.<Bullet>of(Bullet::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BULLET.create(world)));
	public static final EntityType<ArchArrow> ARCH_ARROW = register("arch_arrow", EntityType.Builder.<ArchArrow>of(ArchArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.ARCH_ARROW.create(world)));
	public static final EntityType<BoatMod> BOAT = register("boat_mod", EntityType.Builder.<BoatMod>of(BoatMod::new, MobCategory.MISC).sized(1.375F, 0.5625F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BOAT.create(world)));
	public static final EntityType<MjolnirLightning> MJOLNIR_LIGHTNING = register("mjolnir_lightning", EntityType.Builder.<MjolnirLightning>of(MjolnirLightning::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.MJOLNIR_LIGHTNING.create(world)));
	public static final EntityType<ProjectileSword> PROJECTILE_SWORD = register("projectile_sword", EntityType.Builder.<ProjectileSword>of(ProjectileSword::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.PROJECTILE_SWORD.create(world)));
	public static final EntityType<ThrownSpear> SPEAR = register("spear", EntityType.Builder.<ThrownSpear>of(ThrownSpear::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SPEAR.create(world)));
	public static final EntityType<Meteor> METEOR = register("meteor", EntityType.Builder.<Meteor>of(Meteor::new, MobCategory.MISC).sized(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> CEntityType.METEOR.create(world)));
	public static final EntityType<TrapSpellCircle> TRAP_SPELL_CIRCLE = register("trap_spell_circle", EntityType.Builder.<TrapSpellCircle>of(TrapSpellCircle::new, MobCategory.MISC).sized(3.0F, 0.05F).setCustomClientFactory((spawnEntity, world) -> CEntityType.TRAP_SPELL_CIRCLE.create(world)));
	public static final EntityType<SurroundSpellCircle> SURROUND_SPELL_CIRCLE = register("surround_spell_circle", EntityType.Builder.<SurroundSpellCircle>of(SurroundSpellCircle::new, MobCategory.MISC).sized(3.0F, 0.05F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SURROUND_SPELL_CIRCLE.create(world)));
	public static final EntityType<SoulArrow> SOUL_ARROW = register("soul_arrow", EntityType.Builder.<SoulArrow>of(SoulArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SOUL_ARROW.create(world)));
	public static final EntityType<ZombieMinion> ZOMBIE_MINION = register("zombie_minion", EntityType.Builder.<ZombieMinion>of(ZombieMinion::new, MobCategory.MONSTER).sized(0.6F, 1.95F));
	public static final EntityType<SkeletonMinion> SKELETON_MINION = register("skeleton_minion", EntityType.Builder.<SkeletonMinion>of(SkeletonMinion::new, MobCategory.MONSTER).sized(0.6F, 1.99F));
	public static final EntityType<ProjectileSpell> SPELL = register("spell", EntityType.Builder.<ProjectileSpell>of(ProjectileSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SPELL.create(world)));
	public static final EntityType<BlackHole> BLACK_HOLE = register("black_hole", EntityType.Builder.<BlackHole>of(BlackHole::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BLACK_HOLE.create(world)));
	public static final EntityType<Ray> RAY = register("ray", EntityType.Builder.<Ray>of(Ray::new, MobCategory.MISC).sized(0.125F, 0.125F).setCustomClientFactory((spawnEntity, world) -> CEntityType.RAY.create(world)));
	public static final EntityType<Biog> BIOG = register("biog", EntityType.Builder.<Biog>of(Biog::new, MobCategory.MONSTER).sized(1.0F, 1.0F));
	public static final EntityType<Lichu> LICHU = register("lichu", EntityType.Builder.<Lichu>of(Lichu::new, MobCategory.MONSTER).sized(0.8F, 2.1F));
	public static final EntityType<RedBiog> RED_BIOG = register("red_biog", EntityType.Builder.<RedBiog>of(RedBiog::new, MobCategory.MONSTER).sized(1.0F, 1.0F));
	public static final EntityType<ThrownChakram> CHAKRAM = register("chakram", EntityType.Builder.<ThrownChakram>of(ThrownChakram::new, MobCategory.MISC).sized(0.9F, 0.9F).setCustomClientFactory((spawnEntity, world) -> CEntityType.CHAKRAM.create(world)));
	public static final EntityType<Shuriken> SHURIKEN = register("shuriken", EntityType.Builder.<Shuriken>of(Shuriken::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SHURIKEN.create(world)));
	public static final EntityType<ThrownDagger> DAGGER = register("dagger", EntityType.Builder.<ThrownDagger>of(ThrownDagger::new, MobCategory.MISC).sized(0.9F, 0.9F).setCustomClientFactory((spawnEntity, world) -> CEntityType.DAGGER.create(world)));
	public static final EntityType<WoodenArrow> WOODEN_ARROW = register("wooden_arrow", EntityType.Builder.<WoodenArrow>of(WoodenArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.WOODEN_ARROW.create(world)));
	public static final EntityType<GoldenArrow> GOLDEN_ARROW = register("golden_arrow", EntityType.Builder.<GoldenArrow>of(GoldenArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.GOLDEN_ARROW.create(world)));
	public static final EntityType<DiamondArrow> DIAMOND_ARROW = register("diamond_arrow", EntityType.Builder.<DiamondArrow>of(DiamondArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.DIAMOND_ARROW.create(world)));
	public static final EntityType<QuartzArrow> QUARTZ_ARROW = register("quartz_arrow", EntityType.Builder.<QuartzArrow>of(QuartzArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.QUARTZ_ARROW.create(world)));
	public static final EntityType<IronArrow> IRON_ARROW = register("iron_arrow", EntityType.Builder.<IronArrow>of(IronArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.IRON_ARROW.create(world)));
	public static final EntityType<ObsidianArrow> OBSIDIAN_ARROW = register("obsidian_arrow", EntityType.Builder.<ObsidianArrow>of(ObsidianArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.OBSIDIAN_ARROW.create(world)));
	
	public static void registerAll(IForgeRegistry<EntityType<?>> registry) {
		for(EntityType<?> entitytype: ENTITY_TYPES) {
			registry.register(entitytype);
			Combat.debug("Entity: \""+entitytype.getRegistryName().toString()+"\" registered");
		}
		SpawnPlacements.register(CEntityType.ZOMBIE_COW, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieCow::canZombieCowSpawn);
		SpawnPlacements.register(CEntityType.VAMPIRE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(CEntityType.BIOG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules);
		SpawnPlacements.register(CEntityType.LICHU, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules);
		SpawnPlacements.register(CEntityType.RED_BIOG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMobSpawnRules);
		Combat.debug("All Entities Registered");
	}
	
	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder){
		EntityType<T> type = builder.build(name);
		type.setRegistryName(Combat.getInstance().location(name));
		ENTITY_TYPES.add(type);
		return type;
	}
}
