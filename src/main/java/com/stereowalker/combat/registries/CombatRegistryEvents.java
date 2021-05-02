package com.stereowalker.combat.registries;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.client.particle.AcrotlestPortalParticle;
import com.stereowalker.combat.client.particle.CDripParticle;
import com.stereowalker.combat.enchantment.CEnchantments;
import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.entity.boss.RobinEntity;
import com.stereowalker.combat.entity.item.CPaintingType;
import com.stereowalker.combat.entity.monster.BiogEntity;
import com.stereowalker.combat.entity.monster.LichuEntity;
import com.stereowalker.combat.entity.monster.RedBiogEntity;
import com.stereowalker.combat.entity.monster.VampireEntity;
import com.stereowalker.combat.entity.monster.ZombieCowEntity;
import com.stereowalker.combat.fluid.CFluids;
import com.stereowalker.combat.inventory.container.CContainerType;
import com.stereowalker.combat.item.CItems;
import com.stereowalker.combat.item.IDyeableWeaponItem;
import com.stereowalker.combat.item.crafting.CRecipeSerializer;
import com.stereowalker.combat.particles.CParticleTypes;
import com.stereowalker.combat.potion.CEffects;
import com.stereowalker.combat.potion.CPotions;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.tileentity.CTileEntityType;
import com.stereowalker.combat.util.CSoundEvents;
import com.stereowalker.combat.village.CPointOfInterestType;
import com.stereowalker.combat.world.biome.CBiomeRegistry;
import com.stereowalker.combat.world.gen.carver.CWorldCarver;
import com.stereowalker.combat.world.gen.feature.CFeature;
import com.stereowalker.combat.world.gen.feature.structure.CStructure;
import com.stereowalker.combat.world.gen.placement.CPlacement;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.rankup.stat.Stats;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.Placement;
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
import net.minecraftforge.registries.ForgeRegistries;
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
		if(event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE))
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
		event.put(CEntityType.ROBIN, RobinEntity.registerAttributes().create());
		event.put(CEntityType.BIOG, BiogEntity.registerAttributes().create());
		event.put(CEntityType.LICHU, LichuEntity.registerAttributes().create());
		event.put(CEntityType.RED_BIOG, RedBiogEntity.registerAttributes().create());
		event.put(CEntityType.VAMPIRE, VampireEntity.registerAttributes().create());
		event.put(CEntityType.ZOMBIE_COW, ZombieCowEntity.registerAttributes().create());
		event.put(CEntityType.SKELETON_MINION, AbstractSkeletonEntity.registerAttributes().create());
		event.put(CEntityType.ZOMBIE_MINION, ZombieEntity.func_234342_eQ_().create());
	}
	
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CAttributes.HEALTH_REGENERATION);
		event.add(EntityType.PLAYER, CAttributes.JUMP_STRENGTH);
		event.add(EntityType.PLAYER, CAttributes.MAGIC_STRENGTH);
		event.add(EntityType.PLAYER, CAttributes.MANA_REGENERATION);
		event.add(EntityType.PLAYER, CAttributes.MAX_MANA);
		event.add(EntityType.PLAYER, CAttributes.ATTACK_REACH);
		
		ForgeRegistries.ENTITIES.forEach((type) -> {
			if (GlobalEntityTypeAttributes.doesEntityHaveAttributes(type)) {
				event.add((EntityType<? extends LivingEntity>) type, CAttributes.PHYSICAL_RESISTANCE);
			}
		});
		

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
			return tintIndex > 0 ? -1 : ((IDyeableArmorItem)stack.getItem()).getColor(stack);
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
			return tintIndex == 0 ? -1 : ((IDyeableWeaponItem)stack.getItem()).getColor(stack);
		}, CItems.LIGHT_SABER, CItems.MAGISTEEL_SWORD, CItems.MAGISTEEL_AXE);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerBlockColors(ColorHandlerEvent.Block event) {
		event.getBlockColors().register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
		}, CBlocks.REZAL_LEAVES);
	}

	@SubscribeEvent
	public static void registerEffects(final RegistryEvent.Register<Effect> event) {
		CEffects.registerAll(event.getRegistry());
		Effects.JUMP_BOOST.addAttributesModifier(CAttributes.JUMP_STRENGTH, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A0", 0.1D, AttributeModifier.Operation.ADDITION);
		Effects.RESISTANCE.addAttributesModifier(CAttributes.PHYSICAL_RESISTANCE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A1", 5.0D, AttributeModifier.Operation.ADDITION);
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
	public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
		CTileEntityType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		ParticleManager manager = Minecraft.getInstance().particles;
		manager.registerFactory(CParticleTypes.ACROTLEST_PORTAL, AcrotlestPortalParticle.Factory::new);
		manager.registerFactory(CParticleTypes.PYRANITE_FLAME, FlameParticle.Factory::new);
		manager.registerFactory(CParticleTypes.DRIPPING_OIL, CDripParticle.DrippingOilFactory::new);
		manager.registerFactory(CParticleTypes.FALLING_OIL, CDripParticle.FallingOilFactory::new);
		manager.registerFactory(CParticleTypes.DRIPPING_BIABLE, CDripParticle.DrippingBiableFactory::new);
		manager.registerFactory(CParticleTypes.FALLING_BIABLE, CDripParticle.FallingBiableFactory::new);
		manager.registerFactory(CParticleTypes.OIL_SPLASH, SplashParticle.Factory::new);
		manager.registerFactory(CParticleTypes.BIABLE_SPLASH, SplashParticle.Factory::new);
	}

	@SubscribeEvent
	public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event) {
		CParticleTypes.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
		CContainerType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerPaintings(final RegistryEvent.Register<PaintingType> event) {
		CPaintingType.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
		CRecipeSerializer.registerAll(event.getRegistry());
	}

	//	@SubscribeEvent
	//	public static void registerStats(final RegistryEvent.Register<StatType<?>> event) {
	//		CStats.registerAll(event.getRegistry());
	//	}


	//Village Regirtres
	//	@SubscribeEvent
	//	public static void registerProfessions(final RegistryEvent.Register<VillagerProfession> event) {
	//		event.getRegistry().registerAll(
	//
	//				);
	//		Combat.debug("No Professions to register");
	//	}

	@SubscribeEvent
	public static void registerPOITypes(final RegistryEvent.Register<PointOfInterestType> event) {
		CPointOfInterestType.registerAll(event.getRegistry());
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
	public static void registerStructures(final RegistryEvent.Register<Structure<?>> event) {
		CStructure.registerAll(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerDecorators(final RegistryEvent.Register<Placement<?>> event) {
		CPlacement.registerAll(event.getRegistry());
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
