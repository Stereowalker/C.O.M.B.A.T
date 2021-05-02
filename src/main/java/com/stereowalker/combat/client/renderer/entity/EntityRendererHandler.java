package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.entity.CEntityType;
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

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntityRendererHandler 
{
	@OnlyIn(Dist.CLIENT)
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.VAMPIRE, new IRenderFactory<VampireEntity>()
		{
			@Override
			public EntityRenderer<? super VampireEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new VampireRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.BOAT, new IRenderFactory<BoatModEntity>()
		{
			@Override
			public EntityRenderer<? super BoatModEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new BoatModRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.METEOR, new IRenderFactory<MeteorEntity>()
		{
			@Override
			public EntityRenderer<? super MeteorEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new MeteorRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.TRAP_SPELL_CIRCLE, new IRenderFactory<TrapSpellCircleEntity>()
		{
			@Override
			public EntityRenderer<? super TrapSpellCircleEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SpellCircleRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SURROUND_SPELL_CIRCLE, new IRenderFactory<SurroundSpellCircleEntity>()
		{
			@Override
			public EntityRenderer<? super SurroundSpellCircleEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SpellCircleRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.BLACK_HOLE, new IRenderFactory<BlackHoleEntity>()
		{
			@Override
			public EntityRenderer<? super BlackHoleEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new BlackHoleRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SPEAR, new IRenderFactory<SpearEntity>()
		{
			@Override
			public EntityRenderer<? super SpearEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SpearRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.ZOMBIE_COW, new IRenderFactory<ZombieCowEntity>()
		{
			@Override
			public EntityRenderer<? super ZombieCowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ZombieCowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.BULLET, new IRenderFactory<BulletEntity>()
		{
			@Override
			public EntityRenderer<? super BulletEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new BulletRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.ARCH_ARROW, new IRenderFactory<ArchArrowEntity>()
		{
			@Override
			public EntityRenderer<? super ArchArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ArchArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SOUL_ARROW, new IRenderFactory<SoulArrowEntity>()
		{
			@Override
			public EntityRenderer<? super SoulArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SoulArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.ROBIN, new IRenderFactory<RobinEntity>()
		{
			@Override
			public EntityRenderer<? super RobinEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new RobinRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.ZOMBIE_MINION, new IRenderFactory<ZombieMinionEntity>()
		{
			@Override
			public EntityRenderer<? super ZombieMinionEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ZombieRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SKELETON_MINION, new IRenderFactory<SkeletonMinionEntity>()
		{
			@Override
			public EntityRenderer<? super SkeletonMinionEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SkeletonRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SPELL, new IRenderFactory<SpellEntity>()
		{
			@Override
			public EntityRenderer<? super SpellEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new SpellRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.MJOLNIR_LIGHTNING, new IRenderFactory<MjolnirLightningEntity>()
		{
			@Override
			public EntityRenderer<? super MjolnirLightningEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new MjolnirLightningRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.BIOG, new IRenderFactory<BiogEntity>()
		{
			@Override
			public EntityRenderer<? super BiogEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new BiogRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.RED_BIOG, new IRenderFactory<RedBiogEntity>()
		{
			@Override
			public EntityRenderer<? super RedBiogEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new RedBiogRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.LICHU, new IRenderFactory<LichuEntity>()
		{
			@Override
			public EntityRenderer<? super LichuEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new LichuRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.RAY, new IRenderFactory<RayEntity>()
		{
			@Override
			public EntityRenderer<? super RayEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new RayRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.CHAKRAM, new IRenderFactory<ChakramEntity>()
		{
			@Override
			public EntityRenderer<? super ChakramEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ChakramRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.SHURIKEN, new IRenderFactory<ShurikenEntity>()
		{
			@Override
			public EntityRenderer<? super ShurikenEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ShurikenRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.DAGGER, new IRenderFactory<DaggerEntity>()
		{
			@Override
			public EntityRenderer<? super DaggerEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new DaggerRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.WOODEN_ARROW, new IRenderFactory<WoodenArrowEntity>()
		{
			@Override
			public EntityRenderer<? super WoodenArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new WoodenArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.GOLDEN_ARROW, new IRenderFactory<GoldenArrowEntity>()
		{
			@Override
			public EntityRenderer<? super GoldenArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new GoldenArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.DIAMOND_ARROW, new IRenderFactory<DiamondArrowEntity>()
		{
			@Override
			public EntityRenderer<? super DiamondArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new DiamondArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.QUARTZ_ARROW, new IRenderFactory<QuartzArrowEntity>()
		{
			@Override
			public EntityRenderer<? super QuartzArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new QuartzArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.IRON_ARROW, new IRenderFactory<IronArrowEntity>()
		{
			@Override
			public EntityRenderer<? super IronArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new IronArrowRenderer(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(CEntityType.OBSIDIAN_ARROW, new IRenderFactory<ObsidianArrowEntity>()
		{
			@Override
			public EntityRenderer<? super ObsidianArrowEntity> createRenderFor(EntityRendererManager manager) 
			{
				return new ObsidianArrowRenderer(manager);
			}
		});
	}
}
