package com.stereowalker.combat.core.registries;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.client.particle.AcrotlestPortalParticle;
import com.stereowalker.combat.client.particle.CDripParticle;
import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.data.worldgen.CStructureSets;
import com.stereowalker.combat.data.worldgen.CStructures;
import com.stereowalker.combat.data.worldgen.features.COreFeatures;
import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;
import com.stereowalker.combat.data.worldgen.features.CVegetationFeatures;
import com.stereowalker.combat.data.worldgen.features.MiscAcrotlestFeatures;
import com.stereowalker.combat.data.worldgen.placement.COrePlacements;
import com.stereowalker.combat.data.worldgen.placement.CTreePlacements;
import com.stereowalker.combat.data.worldgen.placement.CVegetationPlacements;
import com.stereowalker.combat.data.worldgen.placement.MiscAcrotlestPlacements;
import com.stereowalker.combat.event.handler.ModdedBiomeGeneration;
import com.stereowalker.combat.sounds.CSoundEvents;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.ai.village.poi.CPoiTypes;
import com.stereowalker.combat.world.entity.boss.robin.RobinBoss;
import com.stereowalker.combat.world.entity.decoration.CPaintingVariants;
import com.stereowalker.combat.world.entity.monster.Biog;
import com.stereowalker.combat.world.entity.monster.Lichu;
import com.stereowalker.combat.world.entity.monster.RedBiog;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.entity.monster.ZombieCow;
import com.stereowalker.combat.world.inventory.CMenuType;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.DyeableWeaponItem;
import com.stereowalker.combat.world.item.SoulGemItem;
import com.stereowalker.combat.world.item.alchemy.CPotions;
import com.stereowalker.combat.world.item.crafting.CRecipeSerializer;
import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.combat.world.level.biome.CBiomeRegistry;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;
import com.stereowalker.combat.world.level.levelgen.carver.CWorldCarver;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.levelgen.feature.StructurePieceTypes;
import com.stereowalker.combat.world.level.levelgen.structure.CStructureType;
import com.stereowalker.combat.world.level.material.BiableFluid;
import com.stereowalker.combat.world.level.material.OilFluid;
import com.stereowalker.combat.world.level.storage.loot.functions.CLootItemFunctions;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.api.stat.StatType;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.rankup.world.job.Jobs;
import com.stereowalker.rankup.world.stat.StatTypes;
import com.stereowalker.rankup.world.stat.Stats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = "combat")
public class CombatRegistryEvents
{
	private static final int MAX_VARINT = Integer.MAX_VALUE - 1;

	//Game Object Registries
	@SubscribeEvent
	public static void registerAttributes(final RegisterEvent event) 
	{
		event.register(Registries.SOUND_EVENT, CSoundEvents::registerAll);
		event.register(Registries.ATTRIBUTE, RegistryOverrides::overrideAttributes);
		event.register(Registries.BLOCK, CBlocks::registerAll);
		event.register(Registries.BLOCK, RegistryOverrides::overrideBlocks);
		event.register(Registries.ENTITY_TYPE, CEntityType::registerAll);
		event.register(Registries.ITEM, CItems::registerAll);
		event.register(Registries.ITEM, RegistryOverrides::overrideItems);
		event.register(Registries.BLOCK_ENTITY_TYPE, CBlockEntityType::registerAll);
		event.register(ForgeRegistries.Keys.FLUID_TYPES, (helper) -> {
			helper.register(new ResourceLocation("combat:biable"), BiableFluid.TYPE);
			helper.register(new ResourceLocation("combat:oil"), OilFluid.TYPE);
		});
		event.register(Registries.RECIPE_TYPE, CRecipeType::registerAll);
		event.register(Registries.RECIPE_SERIALIZER, CRecipeSerializer::registerAll);
		event.register(Registries.POTION, CPotions::registerAll);
		event.register(Registries.PARTICLE_TYPE, CParticleTypes::registerAll);
		event.register(Registries.PAINTING_VARIANT, CPaintingVariants::registerAll);
		event.register(Registries.MENU, CMenuType::registerAll);
		event.register(Registries.CARVER, CWorldCarver::registerAll);
		event.register(Registries.FEATURE, CFeature::registerAll);
		StructurePieceTypes.init();
		CStructures.init();
		CStructureType.init();
		CStructureSets.init();
		CTreeFeatures.init();
		CTreePlacements.init();
		CVegetationFeatures.init();
		CVegetationPlacements.init();
		MiscAcrotlestFeatures.init();
		MiscAcrotlestPlacements.init();
		event.register(Registries.BIOME, CBiomeRegistry::registerAll);
		event.register(Registries.POINT_OF_INTEREST_TYPE, CPoiTypes::registerAll);
		event.register(Registries.STAT_TYPE, CStats::registerAll);
		event.register(Registries.CUSTOM_STAT, CStats::registerAllCustom);
//		NoiseGeneratorSettings.register(CNoiseGeneratorSettings.ACROTLEST, CNoiseGeneratorSettings.acrotlestSettings(false));
		CLootItemFunctions.registerAll();
		new COreFeatures();
		new COrePlacements();
		event.register(CombatRegistries.SKILLS_REGISTRY, Skills::registerAll);
		event.register(CombatRegistries.STAT_TYPES_REGISTRY, StatTypes::registerAll);
		event.register(CombatRegistries.STATS_REGISTRY, Stats::registerAll);
		event.register(CombatRegistries.JOBS_REGISTRY, Jobs::registerAll);
		event.register(CombatRegistries.SPELLS_REGISTRY, Spells::registerAll);
		event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, (helper) -> helper.register("custom", ModdedBiomeGeneration.EXAMPLE_CODEC));
		MobEffects.JUMP.addAttributeModifier(CAttributes.JUMP_STRENGTH, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A0", 0.1D, AttributeModifier.Operation.ADDITION);
		MobEffects.DAMAGE_RESISTANCE.addAttributeModifier(CAttributes.PHYSICAL_RESISTANCE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A1", 5.0D, AttributeModifier.Operation.ADDITION);
	}
	
	@SubscribeEvent
	public static void registerAttributes(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(CombatRegistries.JOBS_REGISTRY, Job.CODEC, Job.CODEC);
		event.dataPackRegistry(CombatRegistries.STATS_REGISTRY, Stat.CODEC, Stat.CODEC);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(CEntityType.ROBIN, RobinBoss.createAttributes().build());
		event.put(CEntityType.BIOG, Biog.createAttributes().build());
		event.put(CEntityType.LICHU, Lichu.createAttributes().build());
		event.put(CEntityType.RED_BIOG, RedBiog.createAttributes().build());
		event.put(CEntityType.VAMPIRE, Vampire.createAttributes().build());
		event.put(CEntityType.ZOMBIE_COW, ZombieCow.createAttributes().build());
		event.put(CEntityType.SKELETON_MINION, AbstractSkeleton.createAttributes().build());
		event.put(CEntityType.ZOMBIE_MINION, Zombie.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CAttributes.HEALTH_REGENERATION);
		event.add(EntityType.PLAYER, CAttributes.JUMP_STRENGTH);
		event.add(EntityType.PLAYER, CAttributes.MAGIC_STRENGTH);
		event.add(EntityType.PLAYER, CAttributes.MANA_REGENERATION);
		event.add(EntityType.PLAYER, CAttributes.MAX_MANA);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex == 0 ? PotionUtils.getColor(stack) : -1;
		}, CItems.WOODEN_TIPPED_ARROW, CItems.GOLDEN_TIPPED_ARROW, CItems.QUARTZ_TIPPED_ARROW, CItems.DIAMOND_TIPPED_ARROW, CItems.OBSIDIAN_TIPPED_ARROW, CItems.IRON_TIPPED_ARROW);
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex == 0 ? SpellUtil.getSpellFromItem(stack).getCategory().getColor().getValue() : -1;
		}, CItems.SCROLL, CItems.ANCIENT_SCROLL);
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack);
		}, CItems.BACKPACK, CItems.QUIVER);
		event.getItemColors().register((stack, tintIndex) -> {
			if (tintIndex == 0) {
				return SkillUtil.getSkillFromItem(stack).getPrimaryColor();
			} else if (tintIndex == 1) {
				return SkillUtil.getSkillFromItem(stack).getSecondaryColor();
			} else {
				return -1;
			}
		}, CItems.SKILL_RUNESTONE);
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex == 0 ? -1 : ((DyeableWeaponItem)stack.getItem()).getColor(stack);
		}, CItems.LIGHT_SABER, CItems.MAGISTEEL_SWORD, CItems.MAGISTEEL_AXE);
		event.getItemColors().register((stack, tintIndex) -> {
			return ((SoulGemItem)stack.getItem()).getCat() == SpellCategory.NONE ? 0x56a6bf : ((SoulGemItem)stack.getItem()).getCat().getColor().getValue();
		}, CItems.SOUL_GEM, CItems.CRUSHED_SOUL_GEM, CItems.DROWNED_SOUL_GEM, CItems.SCORCHED_SOUL_GEM, CItems.SHOCKED_SOUL_GEM, CItems.SHREDDED_SOUL_GEM);
		event.getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
			return event.getBlockColors().getColor(blockstate, (BlockAndTintGetter)null, (BlockPos)null, p_92688_);
		}, CBlocks.REZAL_LEAVES);

	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.getBlockColors().register((state, reader, pos, color) -> {
			int originalRGB = reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
			return (0xFFFFFF - originalRGB) | 0xFF000000;
		}, CBlocks.REZAL_LEAVES);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		@SuppressWarnings("resource")
		ParticleEngine manager = Minecraft.getInstance().particleEngine;
		event.register(CParticleTypes.ACROTLEST_PORTAL, AcrotlestPortalParticle.Provider::new);
		event.register(CParticleTypes.PYRANITE_FLAME, FlameParticle.Provider::new);
		event.register(CParticleTypes.DRIPPING_OIL, CDripParticle.DrippingOilProvider::new);
		event.register(CParticleTypes.FALLING_OIL, CDripParticle.FallingOilProvider::new);
		event.register(CParticleTypes.DRIPPING_BIABLE, CDripParticle.DrippingBiableProvider::new);
		event.register(CParticleTypes.FALLING_BIABLE, CDripParticle.FallingBiableProvider::new);
		event.register(CParticleTypes.OIL_SPLASH, SplashParticle.Provider::new);
		manager.register(CParticleTypes.BIABLE_SPLASH, SplashParticle.Provider::new);
	}

	//Village Regirtres
	//	@SubscribeEvent
	//	public static void registerProfessions(final RegistryEvent.Register<VillagerProfession> event) {
	//		event.getRegistry().registerAll(
	//
	//				);
	//		Combat.debug("No Professions to register");
	//	}

	//Worldgen Registries
//	@SubscribeEvent
//	public static void registerStructures(final RegistryEvent.Register<StructureFeature<?>> event) {
//		CStructureFeature.registerAll(event.getRegistry());
//	}



	@SubscribeEvent
	public static void registerCombatRegistries(NewRegistryEvent event) {
		event.create(new RegistryBuilder<Spell>().setName(Combat.getInstance().location("spell")).setMaxID(MAX_VARINT), (x) -> CombatRegistries.SPELLS = x);
		CombatRegistries.SKILLS = event.create(new RegistryBuilder<Skill>().setName(CombatRegistries.SKILLS_REGISTRY.location()).setMaxID(MAX_VARINT));
		event.create(new RegistryBuilder<StatType>().setName(CombatRegistries.STAT_TYPES_REGISTRY.location()).setMaxID(MAX_VARINT), (x) -> CombatRegistries.STAT_TYPES = x);
		event.create(new RegistryBuilder<Stat>().setName(CombatRegistries.STATS_REGISTRY.location()).setMaxID(MAX_VARINT));
		event.create(new RegistryBuilder<Job>().setName(CombatRegistries.JOBS_REGISTRY.location()).setMaxID(MAX_VARINT), (x) -> CombatRegistries.JOBS = x);
	}
}
