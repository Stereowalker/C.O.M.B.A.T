package com.stereowalker.combat.entity;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.boss.RobinEntity;
import com.stereowalker.combat.entity.item.BlackHoleEntity;
import com.stereowalker.combat.entity.item.BoatModEntity;
import com.stereowalker.combat.entity.item.MeteorEntity;
import com.stereowalker.combat.entity.magic.RayEntity;
import com.stereowalker.combat.entity.magic.SurroundSpellCircleEntity;
import com.stereowalker.combat.entity.magic.TrapSpellCircleEntity;
import com.stereowalker.combat.entity.monster.BiogEntity;
import com.stereowalker.combat.entity.monster.LichuEntity;
import com.stereowalker.combat.entity.monster.RedBiogEntity;
import com.stereowalker.combat.entity.monster.SkeletonMinionEntity;
import com.stereowalker.combat.entity.monster.VampireEntity;
import com.stereowalker.combat.entity.monster.ZombieCowEntity;
import com.stereowalker.combat.entity.monster.ZombieMinionEntity;
import com.stereowalker.combat.entity.projectile.ArchArrowEntity;
import com.stereowalker.combat.entity.projectile.BulletEntity;
import com.stereowalker.combat.entity.projectile.ChakramEntity;
import com.stereowalker.combat.entity.projectile.DaggerEntity;
import com.stereowalker.combat.entity.projectile.DiamondArrowEntity;
import com.stereowalker.combat.entity.projectile.GoldenArrowEntity;
import com.stereowalker.combat.entity.projectile.IronArrowEntity;
import com.stereowalker.combat.entity.projectile.MjolnirLightningEntity;
import com.stereowalker.combat.entity.projectile.ObsidianArrowEntity;
import com.stereowalker.combat.entity.projectile.QuartzArrowEntity;
import com.stereowalker.combat.entity.projectile.ShurikenEntity;
import com.stereowalker.combat.entity.projectile.SoulArrowEntity;
import com.stereowalker.combat.entity.projectile.SpearEntity;
import com.stereowalker.combat.entity.projectile.SpellEntity;
import com.stereowalker.combat.entity.projectile.WoodenArrowEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;

public class CEntityType extends net.minecraftforge.registries.ForgeRegistryEntry<EntityType<?>> {
	private static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<EntityType<?>>();
	
	public static final EntityType<VampireEntity> VAMPIRE = register("vampire", EntityType.Builder.<VampireEntity>create(VampireEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F));
	public static final EntityType<ZombieCowEntity> ZOMBIE_COW = register("zombie_cow", EntityType.Builder.<ZombieCowEntity>create(ZombieCowEntity::new, EntityClassification.MONSTER).size(0.9F, 1.4F));
	public static final EntityType<RobinEntity> ROBIN = register("robin", EntityType.Builder.<RobinEntity>create(RobinEntity::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
	public static final EntityType<BulletEntity> BULLET = register("bullet", EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BULLET.create(world)));
	public static final EntityType<ArchArrowEntity> ARCH_ARROW = register("arch_arrow", EntityType.Builder.<ArchArrowEntity>create(ArchArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.ARCH_ARROW.create(world)));
	public static final EntityType<BoatModEntity> BOAT = register("boat_mod", EntityType.Builder.<BoatModEntity>create(BoatModEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BOAT.create(world)));
	public static final EntityType<MjolnirLightningEntity> MJOLNIR_LIGHTNING = register("mjolnir_lightning", EntityType.Builder.<MjolnirLightningEntity>create(MjolnirLightningEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.MJOLNIR_LIGHTNING.create(world)));
	public static final EntityType<SpearEntity> SPEAR = register("spear", EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SPEAR.create(world)));
	public static final EntityType<MeteorEntity> METEOR = register("meteor", EntityType.Builder.<MeteorEntity>create(MeteorEntity::new, EntityClassification.MISC).size(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> CEntityType.METEOR.create(world)));
	public static final EntityType<TrapSpellCircleEntity> TRAP_SPELL_CIRCLE = register("trap_spell_circle", EntityType.Builder.<TrapSpellCircleEntity>create(TrapSpellCircleEntity::new, EntityClassification.MISC).size(3.0F, 0.05F).setCustomClientFactory((spawnEntity, world) -> CEntityType.TRAP_SPELL_CIRCLE.create(world)));
	public static final EntityType<SurroundSpellCircleEntity> SURROUND_SPELL_CIRCLE = register("surround_spell_circle", EntityType.Builder.<SurroundSpellCircleEntity>create(SurroundSpellCircleEntity::new, EntityClassification.MISC).size(3.0F, 0.05F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SURROUND_SPELL_CIRCLE.create(world)));
	public static final EntityType<SoulArrowEntity> SOUL_ARROW = register("soul_arrow", EntityType.Builder.<SoulArrowEntity>create(SoulArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SOUL_ARROW.create(world)));
	public static final EntityType<ZombieMinionEntity> ZOMBIE_MINION = register("zombie_minion", EntityType.Builder.<ZombieMinionEntity>create(ZombieMinionEntity::new, EntityClassification.MONSTER).size(0.6F, 1.95F));
	public static final EntityType<SkeletonMinionEntity> SKELETON_MINION = register("skeleton_minion", EntityType.Builder.<SkeletonMinionEntity>create(SkeletonMinionEntity::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
	public static final EntityType<SpellEntity> SPELL = register("spell", EntityType.Builder.<SpellEntity>create(SpellEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SPELL.create(world)));
	public static final EntityType<BlackHoleEntity> BLACK_HOLE = register("black_hole", EntityType.Builder.<BlackHoleEntity>create(BlackHoleEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.BLACK_HOLE.create(world)));
	public static final EntityType<RayEntity> RAY = register("ray", EntityType.Builder.<RayEntity>create(RayEntity::new, EntityClassification.MISC).size(0.125F, 0.125F).setCustomClientFactory((spawnEntity, world) -> CEntityType.RAY.create(world)));
	public static final EntityType<BiogEntity> BIOG = register("biog", EntityType.Builder.<BiogEntity>create(BiogEntity::new, EntityClassification.MONSTER).size(1.0F, 1.0F));
	public static final EntityType<LichuEntity> LICHU = register("lichu", EntityType.Builder.<LichuEntity>create(LichuEntity::new, EntityClassification.MONSTER).size(0.8F, 2.1F));
	public static final EntityType<RedBiogEntity> RED_BIOG = register("red_biog", EntityType.Builder.<RedBiogEntity>create(RedBiogEntity::new, EntityClassification.MONSTER).size(1.0F, 1.0F));
	public static final EntityType<ChakramEntity> CHAKRAM = register("chakram", EntityType.Builder.<ChakramEntity>create(ChakramEntity::new, EntityClassification.MISC).size(0.9F, 0.9F).setCustomClientFactory((spawnEntity, world) -> CEntityType.CHAKRAM.create(world)));
	public static final EntityType<ShurikenEntity> SHURIKEN = register("shuriken", EntityType.Builder.<ShurikenEntity>create(ShurikenEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.SHURIKEN.create(world)));
	public static final EntityType<DaggerEntity> DAGGER = register("dagger", EntityType.Builder.<DaggerEntity>create(DaggerEntity::new, EntityClassification.MISC).size(0.9F, 0.9F).setCustomClientFactory((spawnEntity, world) -> CEntityType.DAGGER.create(world)));
	public static final EntityType<WoodenArrowEntity> WOODEN_ARROW = register("wooden_arrow", EntityType.Builder.<WoodenArrowEntity>create(WoodenArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.WOODEN_ARROW.create(world)));
	public static final EntityType<GoldenArrowEntity> GOLDEN_ARROW = register("golden_arrow", EntityType.Builder.<GoldenArrowEntity>create(GoldenArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.GOLDEN_ARROW.create(world)));
	public static final EntityType<DiamondArrowEntity> DIAMOND_ARROW = register("diamond_arrow", EntityType.Builder.<DiamondArrowEntity>create(DiamondArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.DIAMOND_ARROW.create(world)));
	public static final EntityType<QuartzArrowEntity> QUARTZ_ARROW = register("quartz_arrow", EntityType.Builder.<QuartzArrowEntity>create(QuartzArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.QUARTZ_ARROW.create(world)));
	public static final EntityType<IronArrowEntity> IRON_ARROW = register("iron_arrow", EntityType.Builder.<IronArrowEntity>create(IronArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.IRON_ARROW.create(world)));
	public static final EntityType<ObsidianArrowEntity> OBSIDIAN_ARROW = register("obsidian_arrow", EntityType.Builder.<ObsidianArrowEntity>create(ObsidianArrowEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> CEntityType.OBSIDIAN_ARROW.create(world)));
	
	public static void registerAll(IForgeRegistry<EntityType<?>> registry) {
		for(EntityType<?> entitytype: ENTITY_TYPES) {
			registry.register(entitytype);
			Combat.debug("Entity: \""+entitytype.getRegistryName().toString()+"\" registered");
		}
		EntitySpawnPlacementRegistry.register(CEntityType.ZOMBIE_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieCowEntity::canZombieCowSpawn);
		EntitySpawnPlacementRegistry.register(CEntityType.VAMPIRE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
		EntitySpawnPlacementRegistry.register(CEntityType.BIOG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(CEntityType.LICHU, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canSpawnOn);
		EntitySpawnPlacementRegistry.register(CEntityType.RED_BIOG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canSpawnOn);
		Combat.debug("All Entities Registered");
	}
	
	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder){
		EntityType<T> type = builder.build(name);
		type.setRegistryName(Combat.getInstance().location(name));
		ENTITY_TYPES.add(type);
		return type;
	}
}
