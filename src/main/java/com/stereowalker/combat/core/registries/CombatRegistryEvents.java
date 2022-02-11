package com.stereowalker.combat.core.registries;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.client.particle.AcrotlestPortalParticle;
import com.stereowalker.combat.client.particle.CDripParticle;
import com.stereowalker.combat.core.particles.CParticleTypes;
import com.stereowalker.combat.sounds.CSoundEvents;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.entity.ai.village.poi.CPoiType;
import com.stereowalker.combat.world.entity.boss.robin.RobinBoss;
import com.stereowalker.combat.world.entity.decoration.CMotive;
import com.stereowalker.combat.world.entity.monster.Biog;
import com.stereowalker.combat.world.entity.monster.Lichu;
import com.stereowalker.combat.world.entity.monster.RedBiog;
import com.stereowalker.combat.world.entity.monster.Vampire;
import com.stereowalker.combat.world.entity.monster.ZombieCow;
import com.stereowalker.combat.world.inventory.CMenuType;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.DyeableWeaponItem;
import com.stereowalker.combat.world.item.alchemy.CPotions;
import com.stereowalker.combat.world.item.crafting.CRecipeSerializer;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;
import com.stereowalker.combat.world.level.biome.CBiomeRegistry;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;
import com.stereowalker.combat.world.level.levelgen.carver.CWorldCarver;
import com.stereowalker.combat.world.level.levelgen.feature.CFeature;
import com.stereowalker.combat.world.level.levelgen.feature.CStructureFeature;
import com.stereowalker.combat.world.level.levelgen.placement.CFeatureDecorator;
import com.stereowalker.combat.world.level.material.CFluids;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.rankup.world.stat.Stats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid = "combat")
public class CombatRegistryEvents
{
	private static final int MAX_VARINT = Integer.MAX_VALUE - 1;

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
		if(event.getMap().location().equals(TextureAtlas.LOCATION_BLOCKS))
		{
			event.addSprite(Combat.getInstance().EMPTY_SPELLBOOK_SLOT);
		}
	}

	//Game Object Registries
	@SubscribeEvent
	public static void registerAttributes(final RegistryEvent.Register<Attribute> event) 
	{
		RegistryOverrides.overrideAttributes(event.getRegistry());
		CAttributes.registerAll(event.getRegistry());
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
		event.add(EntityType.PLAYER, CAttributes.ATTACK_REACH);
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) 
	{
		RegistryOverrides.overrideBlocks(event.getRegistry());
		CBlocks.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerFluids(final RegistryEvent.Register<Fluid> event) {
		CFluids.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		RegistryOverrides.overrideItems(event.getRegistry());
		CItems.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex == 0 ? PotionUtils.getColor(stack) : -1;
		}, CItems.WOODEN_TIPPED_ARROW, CItems.GOLDEN_TIPPED_ARROW, CItems.QUARTZ_TIPPED_ARROW, CItems.DIAMOND_TIPPED_ARROW, CItems.OBSIDIAN_TIPPED_ARROW, CItems.IRON_TIPPED_ARROW);
		event.getItemColors().register((stack, tintIndex) -> {
			return tintIndex == 0 ? SpellUtil.getSpellFromItem(stack).getCategory().getTextFormatting().getColor() : -1;
		}, CItems.SCROLL);
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
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(ColorHandlerEvent.Block event) {
		event.getBlockColors().register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
		}, CBlocks.REZAL_LEAVES);
	}

	@SubscribeEvent
	public static void registerEffects(final RegistryEvent.Register<MobEffect> event) {
		CMobEffects.registerAll(event.getRegistry());
		MobEffects.JUMP.addAttributeModifier(CAttributes.JUMP_STRENGTH, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A0", 0.1D, AttributeModifier.Operation.ADDITION);
		MobEffects.DAMAGE_RESISTANCE.addAttributeModifier(CAttributes.PHYSICAL_RESISTANCE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A1", 5.0D, AttributeModifier.Operation.ADDITION);
	}

	@SubscribeEvent
	public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
		CSoundEvents.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerPotions(final RegistryEvent.Register<Potion> event) {
		CPotions.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
		CEnchantments.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerEntity(final RegistryEvent.Register<EntityType<?>> event) {
		CEntityType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerBlockEntities(final RegistryEvent.Register<BlockEntityType<?>> event) {
		CBlockEntityType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		@SuppressWarnings("resource")
		ParticleEngine manager = Minecraft.getInstance().particleEngine;
		manager.register(CParticleTypes.ACROTLEST_PORTAL, AcrotlestPortalParticle.Provider::new);
		manager.register(CParticleTypes.PYRANITE_FLAME, FlameParticle.Provider::new);
		manager.register(CParticleTypes.DRIPPING_OIL, CDripParticle.DrippingOilProvider::new);
		manager.register(CParticleTypes.FALLING_OIL, CDripParticle.FallingOilProvider::new);
		manager.register(CParticleTypes.DRIPPING_BIABLE, CDripParticle.DrippingBiableProvider::new);
		manager.register(CParticleTypes.FALLING_BIABLE, CDripParticle.FallingBiableProvider::new);
		manager.register(CParticleTypes.OIL_SPLASH, SplashParticle.Provider::new);
		manager.register(CParticleTypes.BIABLE_SPLASH, SplashParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event) {
		CParticleTypes.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerContainers(final RegistryEvent.Register<MenuType<?>> event) {
		CMenuType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerMotives(final RegistryEvent.Register<Motive> event) {
		CMotive.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(final RegistryEvent.Register<RecipeSerializer<?>> event) {
		CRecipeSerializer.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerStats(final RegistryEvent.Register<StatType<?>> event) {
		CStats.registerAll(event.getRegistry());
	}


	//Village Regirtres
	//	@SubscribeEvent
	//	public static void registerProfessions(final RegistryEvent.Register<VillagerProfession> event) {
	//		event.getRegistry().registerAll(
	//
	//				);
	//		Combat.debug("No Professions to register");
	//	}

	@SubscribeEvent
	public static void registerPOITypes(final RegistryEvent.Register<PoiType> event) {
		CPoiType.registerAll(event.getRegistry());
	}


	//Worldgen Registries
	@SubscribeEvent
	public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
		CBiomeRegistry.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerWorldCarvers(final RegistryEvent.Register<WorldCarver<?>> event) {
		CWorldCarver.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
		CFeature.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerStructures(final RegistryEvent.Register<StructureFeature<?>> event) {
		CStructureFeature.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerFeatureDecorators(final RegistryEvent.Register<FeatureDecorator<?>> event) {
		CFeatureDecorator.registerAll(event.getRegistry());
	}

	//	@SubscribeEvent
	//	public static void registerDataSerializers(final RegistryEvent.Register<DataSerializerEntry> event) {
	//		event.getRegistry().registerAll(
	//
	//				);
	//		Combat.debug("No Data Serializers to register");
	//	}


	@SubscribeEvent
	public static void registerCombatRegistries(final RegistryEvent.NewRegistry event) {
		new RegistryBuilder<Spell>().setName(Combat.getInstance().location("spell")).setType(Spell.class).setMaxID(MAX_VARINT).create();
		new RegistryBuilder<Skill>().setName(Combat.getInstance().location("skill")).setType(Skill.class).setMaxID(MAX_VARINT).create();
		new RegistryBuilder<Stat>().setName(Combat.getInstance().location("upgradeable_attribute")).setType(Stat.class).setMaxID(MAX_VARINT).create();
	}

	//Custom C.O.M.B.A.T. Registries
	@SubscribeEvent
	public static void registerSpells(final RegistryEvent.Register<Spell> event) {
		Spells.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerSkills(final RegistryEvent.Register<Skill> event) {
		Skills.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerLeveledStats(final RegistryEvent.Register<Stat> event) {
		Stats.registerAll(event.getRegistry());
	}
}
