package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityRendererHandler 
{
	@OnlyIn(Dist.CLIENT)
	public static void bootStrap()
	{
		EntityRenderers.register(CEntityType.VAMPIRE, VampireRenderer::new);
		EntityRenderers.register(CEntityType.BOAT, BoatModRenderer::new);
		EntityRenderers.register(CEntityType.METEOR, MeteorRenderer::new);
		EntityRenderers.register(CEntityType.TRAP_SPELL_CIRCLE, SpellCircleRenderer::new);
		EntityRenderers.register(CEntityType.SURROUND_SPELL_CIRCLE, SpellCircleRenderer::new);
		EntityRenderers.register(CEntityType.BLACK_HOLE, BlackHoleRenderer::new);
		EntityRenderers.register(CEntityType.SPEAR, SpearRenderer::new);
		EntityRenderers.register(CEntityType.ZOMBIE_COW, ZombieCowRenderer::new);
		EntityRenderers.register(CEntityType.BULLET, BulletRenderer::new);
		EntityRenderers.register(CEntityType.ARCH_ARROW, ArchArrowRenderer::new);
		EntityRenderers.register(CEntityType.SOUL_ARROW, SoulArrowRenderer::new);
		EntityRenderers.register(CEntityType.ROBIN, RobinRenderer::new);
		EntityRenderers.register(CEntityType.ZOMBIE_MINION, ZombieRenderer::new);
		EntityRenderers.register(CEntityType.SKELETON_MINION, SkeletonRenderer::new);
		EntityRenderers.register(CEntityType.SPELL, SpellRenderer::new);
		EntityRenderers.register(CEntityType.MJOLNIR_LIGHTNING, MjolnirLightningRenderer::new);
		EntityRenderers.register(CEntityType.PROJECTILE_SWORD, ProjectileSwordRenderer::new);
		EntityRenderers.register(CEntityType.BIOG, BiogRenderer::new);
		EntityRenderers.register(CEntityType.RED_BIOG, RedBiogRenderer::new);
		EntityRenderers.register(CEntityType.LICHU, LichuRenderer::new);
		EntityRenderers.register(CEntityType.RAY, RayRenderer::new);
		EntityRenderers.register(CEntityType.CHAKRAM, ThrownChakramRenderer::new);
		EntityRenderers.register(CEntityType.SHURIKEN, ShurikenRenderer::new);
		EntityRenderers.register(CEntityType.DAGGER, ThrownDaggerRenderer::new);
		EntityRenderers.register(CEntityType.WOODEN_ARROW, WoodenArrowRenderer::new);
		EntityRenderers.register(CEntityType.GOLDEN_ARROW, GoldenArrowRenderer::new);
		EntityRenderers.register(CEntityType.DIAMOND_ARROW, DiamondArrowRenderer::new);
		EntityRenderers.register(CEntityType.QUARTZ_ARROW, QuartzArrowRenderer::new);
		EntityRenderers.register(CEntityType.IRON_ARROW, IronArrowRenderer::new);
		EntityRenderers.register(CEntityType.OBSIDIAN_ARROW, ObsidianArrowRenderer::new);
	}
}
