package com.stereowalker.combat;

import java.util.Map;
import java.util.SortedMap;

import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.stereowalker.combat.advancements.CCriteriaTriggers;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.GuiHelper;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.combat.client.events.TooltipEvents;
import com.stereowalker.combat.client.gui.screens.inventory.CScreens;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.client.renderer.CDimensionSpecialEffects;
import com.stereowalker.combat.client.renderer.CRenderType;
import com.stereowalker.combat.client.renderer.CombatBlockEntityWithoutLevelRenderer;
import com.stereowalker.combat.client.renderer.blockentity.BlockEntityRenderHandler;
import com.stereowalker.combat.client.renderer.entity.EntityRendererHandler;
import com.stereowalker.combat.client.renderer.item.CItemProperties;
import com.stereowalker.combat.commands.CCommands;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.compat.curios.CuriosEvents;
import com.stereowalker.combat.compat.terrablender.CombatR;
import com.stereowalker.combat.config.BattleConfig;
import com.stereowalker.combat.config.ClientConfig;
import com.stereowalker.combat.config.MagicConfig;
import com.stereowalker.combat.config.RpgConfig;
import com.stereowalker.combat.event.AttackTargetEntityWithCurrentItemEvent;
import com.stereowalker.combat.event.EffectEvents;
import com.stereowalker.combat.event.EnchantmentEvents;
import com.stereowalker.combat.event.handler.GameEvents;
import com.stereowalker.combat.event.handler.VampireEvents;
import com.stereowalker.combat.event.handler.WeaponTypeEvents;
import com.stereowalker.combat.network.protocol.game.ClientboundAbominationPacket;
import com.stereowalker.combat.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundBackItemPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundClientMotionPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundGunPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundHeldItemStackNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundMageSetupPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundPronePacket;
import com.stereowalker.combat.network.protocol.game.ServerboundRequestStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSpellbookNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundStoreItemPacket;
import com.stereowalker.combat.tags.BiomeCTags;
import com.stereowalker.combat.tags.BlockCTags;
import com.stereowalker.combat.tags.CTags;
import com.stereowalker.combat.tags.EntityTypeCTags;
import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.CCreativeModeTab;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.CTiers;
import com.stereowalker.combat.world.item.alchemy.BrewingPotion;
import com.stereowalker.combat.world.item.enchantment.CEnchantments;
import com.stereowalker.combat.world.level.CGameRules;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.block.PyraniteFireBlock;
import com.stereowalker.combat.world.level.dimension.CDimensionType;
import com.stereowalker.combat.world.level.material.CFluids;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.old.combat.config.RpgClientConfig;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.events.RankEvents;
import com.stereowalker.rankup.events.UnionEvents;
import com.stereowalker.rankup.network.protocol.game.RankupNetRegistry;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.SkillsEvents;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.rankup.skill.api.SkillUtil;
import com.stereowalker.rankup.world.stat.StatEvents;
import com.stereowalker.unionlib.api.collectors.DefaultAttributeModifier;
import com.stereowalker.unionlib.api.collectors.InsertCollector;
import com.stereowalker.unionlib.api.collectors.PacketCollector;
import com.stereowalker.unionlib.api.collectors.ReloadListeners;
import com.stereowalker.unionlib.api.creativetabs.CreativeTabBuilder;
import com.stereowalker.unionlib.api.creativetabs.CreativeTabPopulator;
import com.stereowalker.unionlib.api.keymaps.KeyMappingCollector;
import com.stereowalker.unionlib.api.registries.RegistryCollector;
import com.stereowalker.unionlib.client.gui.screens.config.MinecraftModConfigsScreen;
import com.stereowalker.unionlib.config.ConfigBuilder;
import com.stereowalker.unionlib.insert.Inserts;
import com.stereowalker.unionlib.mod.MinecraftMod;
import com.stereowalker.unionlib.mod.PacketHolder;
import com.stereowalker.unionlib.util.ModHelper;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.Regions;
//import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;
//import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Combat.MODID)
public class Combat extends MinecraftMod implements PacketHolder 
{
	public static final String MODID = "combat";
	private static Combat combatInstance;
	public static Rankup rankupInstance;
	public static final RpgConfig RPG_CONFIG = new RpgConfig();
	public static final BattleConfig BATTLE_CONFIG = new BattleConfig();
	public static final MagicConfig MAGIC_CONFIG = new MagicConfig();
	public static final ClientConfig CLIENT_CONFIG = new ClientConfig();
	public static CombatBlockEntityWithoutLevelRenderer itemStackRender;

	public static boolean disableConfig() {
		return false;
	}

	//TODO: Change to ModHelper after UnionLib update
	public static boolean isBOPLoaded(boolean shouldReturnTrue) {
		if (!shouldReturnTrue)return ModList.get().isLoaded("biomesoplenty");
		else return true;
	}
	public static boolean isFirstAidLoaded() {
		return ModList.get().isLoaded("firstaid");
	}
	public static boolean isSurviveLoaded() {
		return ModList.get().isLoaded("survive");
	}

	public final ResourceLocation EMPTY_SPELLBOOK_SLOT = this.location("item/empty_spellbook_slot");

	public Combat() 
	{
		super(MODID, new ResourceLocation(MODID, "textures/icon.png"), LoadType.BOTH);
		combatInstance = this;
		rankupInstance = new Rankup();
		ConfigBuilder.registerConfig(RpgClientConfig.class);
		ConfigBuilder.registerConfig(RPG_CONFIG);
		ConfigBuilder.registerConfig(BATTLE_CONFIG);
		ConfigBuilder.registerConfig(MAGIC_CONFIG);
		ConfigBuilder.registerConfig(CLIENT_CONFIG);
		if (!Combat.disableConfig()) Config.registerConfigs();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		eventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> ()-> {
			eventBus().addListener(this::effects);
			eventBus().addListener(GuiHelper::registerOverlays);
		});
		//MinecraftForge.EVENT_BUS.register(this);
	}

	@OnlyIn(Dist.CLIENT)
	public void effects(RegisterDimensionSpecialEffectsEvent event) {
		/*DimensionSpecialEffects.EFFECTS.put*/
					event.register(CDimensionType.ACROTLEST_ID, new CDimensionSpecialEffects.Acrotlest());
	}

	@Override
	public void setupKeymappings(KeyMappingCollector collector) {
		collector.addKeyMapping(KeyMappings.NEXT_SPELL);
		collector.addKeyMapping(KeyMappings.PREV_SPELL);
		collector.addKeyMapping(KeyMappings.PLAYER_LEVELS);
		collector.addKeyMapping(KeyMappings.RELOAD);
		collector.addKeyMapping(KeyMappings.FIRE);
		collector.addKeyMapping(KeyMappings.OPEN_BACK_ITEM);
		collector.addKeyMapping(KeyMappings.STORE_ITEM);
		collector.addKeyMapping(KeyMappings.PRONE);
	}

	@Override
	public void registerClientboundPackets(PacketCollector collector) {
		RankupNetRegistry.registerMessages(collector);
		collector.registerPacket(ClientboundAbominationPacket.class, (packetBuffer) -> {return new ClientboundAbominationPacket(packetBuffer);});
		collector.registerPacket(ClientboundPlayerStatsPacket.class, (packetBuffer) -> {return new ClientboundPlayerStatsPacket(packetBuffer);});

	}

	@Override
	public ResourceLocation channelName() {
		return new ResourceLocation("combat:game_pack");
	}

	@Override
	public void registerServerboundPackets(PacketCollector collector) {
		//client
		collector.registerPacket(ServerboundGunPacket.class, (packetBuffer) -> {return new ServerboundGunPacket(packetBuffer);});
		collector.registerPacket(ServerboundBackItemPacket.class, (packetBuffer) -> {return new ServerboundBackItemPacket(packetBuffer);});
		collector.registerPacket(ServerboundMageSetupPacket.class, (packetBuffer) -> {return new ServerboundMageSetupPacket(packetBuffer);});
		collector.registerPacket(ServerboundStoreItemPacket.class, (packetBuffer) -> {return new ServerboundStoreItemPacket(packetBuffer);});
		collector.registerPacket(ServerboundPronePacket.class, (packetBuffer) -> {return new ServerboundPronePacket(packetBuffer);});
		collector.registerPacket(ServerboundClientMotionPacket.class, (packetBuffer) -> {return new ServerboundClientMotionPacket(packetBuffer);});
		collector.registerPacket(ServerboundHeldItemStackNBTPacket.class, (packetBuffer) -> {return new ServerboundHeldItemStackNBTPacket(packetBuffer);});
		collector.registerPacket(ServerboundRequestStatsPacket.class, (packetBuffer) -> {return new ServerboundRequestStatsPacket(packetBuffer);});
		collector.registerPacket(ServerboundSpellbookNBTPacket.class, (packetBuffer) -> {return new ServerboundSpellbookNBTPacket(packetBuffer);});

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Screen getConfigScreen(Minecraft mc, Screen previousScreen) {
		return new MinecraftModConfigsScreen(previousScreen, Component.translatable("gui.combat.config.title"), CLIENT_CONFIG, RPG_CONFIG, BATTLE_CONFIG, MAGIC_CONFIG);

	}

	@Override
	public void registerServerRelaodableResources(ReloadListeners reloadListener) {
		reloadListener.listenTo(Rankup.statsManager);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClientRelaodableResources(ReloadListeners reloadListener) {
		itemStackRender = new CombatBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		reloadListener.listenTo(itemStackRender);
	}

	@Override
	public void modifyDefaultEntityAttributes(DefaultAttributeModifier modifier) {
		modifier.addToEntity(EntityType.PLAYER, CAttributes.FIRE_AFFINITY, CAttributes.EARTH_AFFINITY);
		modifier.addToEntity(EntityType.PLAYER, CAttributes.WATER_AFFINITY, CAttributes.LIGHTNING_AFFINITY);
		modifier.addToEntity(EntityType.PLAYER, CAttributes.WIND_AFFINITY, CAttributes.ATTACK_REACH);
		modifier.addToEntity(EntityType.ZOMBIE, CAttributes.PHYSICAL_RESISTANCE);
	}

	@Override
	public void registerInserts(InsertCollector collector) {
		collector.addInsert(Inserts.ENTITY_JOIN_LEVEL, GameEvents::entityJoinWorld);
		collector.addInsert(Inserts.ENTITY_JOIN_LEVEL, RankEvents::createPlayer);
		collector.addInsert(Inserts.FOV, GameEvents::fovUpdate);
		collector.addInsert(Inserts.FINISH_SLEEP, GameEvents::replenishManaOnSleep);
		collector.addInsert(Inserts.LEVEL_SAVE, RankEvents::syncToClientOnSave);
		collector.addInsert(Inserts.LIVING_ATTACK, VampireEvents::woodenToolAttack);
		collector.addInsert(Inserts.LIVING_ATTACK, EnchantmentEvents::enchantmentBurningSpikes);
		collector.addInsert(Inserts.LIVING_ATTACK, EnchantmentEvents::enchantmentSpikes);
		collector.addInsert(Inserts.LIVING_ATTACK, EnchantmentEvents::enchantmentAbsorption);
		collector.addInsert(Inserts.LIVING_DEATH, GameEvents::entityKill);
		collector.addInsert(Inserts.LIVING_FALL, EffectEvents::gravityPlusFall);
		collector.addInsert(Inserts.LIVING_FALL, EffectEvents::gravityMinusFall);
		collector.addInsert(Inserts.LIVING_FALL, VampireEvents::vampireFall);
		collector.addInsert(Inserts.LIVING_HEAL, GameEvents::setupHealthRegenerationAttribute);
		collector.addInsert(Inserts.LIVING_JUMP, GameEvents::setupJumpHeightAttribute);
		collector.addInsert(Inserts.LIVING_TICK, CuriosEvents::addEffectsToAccessories);
		collector.addInsert(Inserts.LIVING_TICK, CuriosEvents::addModifiers);
		collector.addInsert(Inserts.LIVING_TICK, EffectEvents::effectParalysis);
		collector.addInsert(Inserts.LIVING_TICK, EffectEvents::effectVampirism);
		collector.addInsert(Inserts.LIVING_TICK, EffectEvents::effectFlammable);
		collector.addInsert(Inserts.LIVING_TICK, EffectEvents::effectFear);
		collector.addInsert(Inserts.LIVING_TICK, EffectEvents::effectFrostbite);
		collector.addInsert(Inserts.LIVING_TICK, EnchantmentEvents::enchantmentRestoring);
		collector.addInsert(Inserts.LIVING_TICK, EnchantmentEvents::enchantmentQuickSwing);
		collector.addInsert(Inserts.LIVING_TICK, GameEvents::livingUpdate);
		collector.addInsert(Inserts.LIVING_TICK, RankEvents::living);
		collector.addInsert(Inserts.LIVING_TICK, UnionEvents::addEffectsToAccessories);
		collector.addInsert(Inserts.LIVING_TICK, UnionEvents::addModifiers);
		collector.addInsert(Inserts.LIVING_TICK, VampireEvents::vampireTick);
		collector.addInsert(Inserts.LIVING_TICK, WeaponTypeEvents::weaponUpdate);
		collector.addInsert(Inserts.LOGGED_IN, RankEvents::playerJoin);
		collector.addInsert(Inserts.MENU_OPEN, UnionEvents::addEffectsToChestItems);
		collector.addInsert(Inserts.PLAYER_ATTACK, WeaponTypeEvents::singleEdgeCurvedWeaponUpdate);
		collector.addInsert(Inserts.PLAYER_ATTACK, WeaponTypeEvents::heavyWeaponUpdate);
		collector.addInsert(Inserts.PLAYER_ATTACK, EnchantmentEvents::enchantmentIceAspect);
		collector.addInsert(Inserts.PLAYER_ATTACK, SkillsEvents::attackSkillUpdate);
		collector.addInsert(Inserts.PLAYER_ATTACK, VampireEvents::vampireEatEntities);
		collector.addInsert(Inserts.PLAYER_ATTACK, AttackTargetEntityWithCurrentItemEvent::attackTargetEntityWithCurrentItem);
		collector.addInsert(Inserts.INTERACT_WITH_BLOCK, GameEvents::openFletchingTable);
		collector.addInsert(Inserts.INTERACT_WITH_ENTITY, GameEvents::collectBlood);
		collector.addInsert(Inserts.ITEM_USE_FINISH, VampireEvents::vampireEatFood);
		collector.addInsert(Inserts.PLAYER_RESTORE, GameEvents::restoreStats);
		collector.addInsert(Inserts.PLAYER_RESTORE, StatEvents::restoreStats);
		collector.addInsert(Inserts.PLAYER_RESTORE, RankEvents::clonePlayer);
		collector.addInsert(Inserts.PLAYER_RESTORE, EnchantmentEvents::enchantmentClone);
		collector.addInsert(Inserts.XP_PICKUP, GameEvents::xpRingStorage);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> ()-> {
			collector.addInsert(Inserts.LIVING_TICK, GameEvents::livingUpdateOnClient);
			collector.addInsert(Inserts.ITEM_TOOLTIP, TooltipEvents::tooltips);
			collector.addInsert(Inserts.ITEM_TOOLTIP, UnionEvents::modifierTooltip);
		});
	}

	@Override
	public void registerCreativeTabs(CreativeTabBuilder builder) {
		builder.addTab(new ResourceLocation("combat:building_blocks"), CCreativeModeTab.BUILDING_BLOCKS);
		builder.addTab(new ResourceLocation("combat:magic"), CCreativeModeTab.MAGIC);
		builder.addTab(new ResourceLocation("combat:battle"), CCreativeModeTab.BATTLE);
		builder.addTab(new ResourceLocation("combat:technology"), CCreativeModeTab.TECHNOLOGY);
		builder.addTab(new ResourceLocation("combat:misc"), CCreativeModeTab.COMBAT_TAB_MISC);
		builder.addTab(new ResourceLocation("combat:tools"), CCreativeModeTab.TOOLS);
	}

	@Override
	public void setupRegistries(RegistryCollector collector) {
		collector.addRegistryHolder(CAttributes.class);
		collector.addRegistryHolder(CFluids.class);
		collector.addRegistryHolder(CMobEffects.class);
		collector.addRegistryHolder(CEnchantments.class);
	}

	@Override
	public void populateCreativeTabs(CreativeTabPopulator populator) {
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.MAGIC.getDisplayName())) {
			populator.addItems(Lists.newArrayList(
					CItems.ARCANE_WORKBENCH, CItems.DISENCHANTING_TABLE, CItems.MAGIC_STONE, CItems.YELLOW_MAGIC_CLUSTER,
					CItems.BASIC_STICK_WAND, CItems.NOVICE_STICK_WAND, CItems.APPRENTICE_STICK_WAND, CItems.ADVANCED_STICK_WAND, CItems.MASTER_STICK_WAND,
					CItems.BASIC_BLAZE_WAND, CItems.NOVICE_BLAZE_WAND, CItems.APPRENTICE_BLAZE_WAND, CItems.ADVANCED_BLAZE_WAND, CItems.MASTER_BLAZE_WAND,
					CItems.BASIC_GOLDEN_WAND, CItems.NOVICE_GOLDEN_WAND, CItems.APPRENTICE_GOLDEN_WAND, CItems.ADVANCED_GOLDEN_WAND, CItems.MASTER_GOLDEN_WAND,
					CItems.BASIC_SERABLE_WAND, CItems.NOVICE_SERABLE_WAND, CItems.APPRENTICE_SERABLE_WAND, CItems.ADVANCED_SERABLE_WAND, CItems.MASTER_SERABLE_WAND,
					CItems.BASIC_SHULKER_WAND, CItems.NOVICE_SHULKER_WAND, CItems.APPRENTICE_SHULKER_WAND, CItems.ADVANCED_SHULKER_WAND, CItems.MASTER_SHULKER_WAND));
			populator.addItems(CItems.RESEARCH_SCROLL, CItems.SCROLL, CItems.ANCIENT_SCROLL, CItems.GRIMOIRE, CItems.TRIDOX, CItems.MANA_PILL);
			populator.addItems(CItems.EMPTY_SOUL_GEM, CItems.SOUL_GEM, CItems.SCORCHED_SOUL_GEM, CItems.CRUSHED_SOUL_GEM, CItems.DROWNED_SOUL_GEM);
			populator.addItems(CItems.SHOCKED_SOUL_GEM, CItems.SHREDDED_SOUL_GEM, CItems.MANA_ORB, CItems.MAGIC_ORB, CItems.PURPLE_MAGIC_STONE);
			populator.addItems(CItems.RED_MAGIC_STONE, CItems.YELLOW_MAGIC_STONE, CItems.PYROMANCER_RING, CItems.HYDROMANCER_RING, CItems.ELECTROMANCER_RING);
			populator.addItems(CItems.TERRAMANCER_RING, CItems.AEROMANCER_RING, CItems.XP_STORAGE_RING, CItems.POISON_CLEANSING_AMULET);
			populator.addItems(CItems.RECONFIGURATION_SOUP);
			for(Skill spell : CombatRegistries.SKILLS.get()) {
				if (spell != Skills.EMPTY) {
					populator.getOutput().accept(SkillUtil.addSkillToItemStack(new ItemStack(CItems.SKILL_RUNESTONE), spell));
				}
			}
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.MAGIC.getDisplayName())) {
			populator.addItems(CItems.DESERT_DRAGON, CItems.ARCH, CItems.MOON_BOOTS, CItems.MJOLNIR, CItems.WRATH, CItems.FROSTBITE_SWORD, CItems.MANA_CUTTER, CItems.GOD_WAND);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.COMBAT_TAB_MISC.getDisplayName())) {
			populator.addItems(CItems.DAGGER_HANDLE, CItems.SWORD_HANDLE, CItems.KATANA_HANDLE, CItems.RAPIER_HANDLE);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.BATTLE.getDisplayName())) {
			populator.addItems(CItems.SPEAR, CItems.ARCH_EXPLOSION_GEM, CItems.ARCH_FLAME_GEM, CItems.ARCH_FREEZE_GEM, CItems.ARCH_TELEPORT_GEM);
			populator.addItems(CItems.BRONZE_SWORD, CItems.STEEL_SWORD, CItems.MAGISTEEL_SWORD, CItems.PASQUEM_SWORD, CItems.SERABLE_SWORD);
			populator.addItems(CItems.LOZYNE_SWORD, CItems.MYTHRIL_SWORD, CItems.ETHERION_SWORD);

			populator.addItems(CItems.WOODEN_DAGGER, CItems.STONE_DAGGER, CItems.IRON_DAGGER, CItems.GOLDEN_DAGGER, CItems.DIAMOND_DAGGER, CItems.NETHERITE_DAGGER);
			populator.addItems(CItems.BRONZE_DAGGER, CItems.STEEL_DAGGER, /*CItems.MAGISTEEL_DAGGER, */CItems.PASQUEM_DAGGER, CItems.SERABLE_DAGGER);
			populator.addItems(CItems.LOZYNE_DAGGER, CItems.MYTHRIL_DAGGER, CItems.ETHERION_DAGGER);

			populator.addItems(CItems.WOODEN_HAMMER, CItems.STONE_HAMMER, CItems.IRON_HAMMER, CItems.GOLDEN_HAMMER, CItems.DIAMOND_HAMMER/*, CItems.NETHERITE_HAMMER*/);
			populator.addItems(CItems.BRONZE_HAMMER, CItems.STEEL_HAMMER, /*CItems.MAGISTEEL_HAMMER, */CItems.PASQUEM_HAMMER, CItems.SERABLE_HAMMER);
			populator.addItems(CItems.LOZYNE_HAMMER, CItems.MYTHRIL_HAMMER, CItems.ETHERION_HAMMER);

			populator.addItems(CItems.WOODEN_CHAKRAM, CItems.STONE_CHAKRAM, CItems.IRON_CHAKRAM, CItems.GOLDEN_CHAKRAM, CItems.DIAMOND_CHAKRAM/*, CItems.NETHERITE_CHAKRAM*/);
			populator.addItems(CItems.BRONZE_CHAKRAM, CItems.STEEL_CHAKRAM, /*CItems.MAGISTEEL_CHAKRAM, */CItems.PASQUEM_CHAKRAM, CItems.SERABLE_CHAKRAM);
			populator.addItems(CItems.LOZYNE_CHAKRAM, CItems.MYTHRIL_CHAKRAM/*, CItems.ETHERION_CHAKRAM*/);

			populator.addItems(CItems.WOODEN_KATANA, CItems.STONE_KATANA, CItems.IRON_KATANA, CItems.GOLDEN_KATANA, CItems.DIAMOND_KATANA, CItems.NETHERITE_KATANA);
			populator.addItems(CItems.BRONZE_KATANA, CItems.STEEL_KATANA, /*CItems.MAGISTEEL_KATANA, */CItems.PASQUEM_KATANA, CItems.SERABLE_KATANA);
			populator.addItems(CItems.LOZYNE_KATANA, CItems.MYTHRIL_KATANA, CItems.ETHERION_KATANA);
			populator.getOutput().accept(CItems.WOODEN_RAPIER);
			populator.getOutput().accept(CItems.STONE_RAPIER);
			populator.getOutput().accept(CItems.IRON_RAPIER);
			populator.getOutput().accept(CItems.GOLDEN_RAPIER);
			populator.getOutput().accept(CItems.DIAMOND_RAPIER);
			populator.getOutput().accept(CItems.NETHERITE_RAPIER);
			populator.getOutput().accept(CItems.BRONZE_RAPIER);
			populator.getOutput().accept(CItems.STEEL_RAPIER);
			//			populator.getOutput().accept(CItems.MAGISTEEL_RAPIER);
			populator.getOutput().accept(CItems.PASQUEM_RAPIER);
			populator.getOutput().accept(CItems.SERABLE_RAPIER);
			populator.getOutput().accept(CItems.LOZYNE_RAPIER);
			populator.getOutput().accept(CItems.MYTHRIL_RAPIER);
			populator.getOutput().accept(CItems.ETHERION_RAPIER);
			populator.getOutput().accept(CItems.WOODEN_HALBERD);
			populator.getOutput().accept(CItems.STONE_HALBERD);
			populator.getOutput().accept(CItems.IRON_HALBERD);
			populator.getOutput().accept(CItems.GOLDEN_HALBERD);
			populator.getOutput().accept(CItems.DIAMOND_HALBERD);
			//			populator.getOutput().accept(CItems.NETHERITE_HALBERD);
			populator.getOutput().accept(CItems.BRONZE_HALBERD);
			populator.getOutput().accept(CItems.STEEL_HALBERD);
			//			populator.getOutput().accept(CItems.MAGISTEEL_HALBERD);
			populator.getOutput().accept(CItems.PASQUEM_HALBERD);
			populator.getOutput().accept(CItems.SERABLE_HALBERD);
			populator.getOutput().accept(CItems.LOZYNE_HALBERD);
			populator.getOutput().accept(CItems.MYTHRIL_HALBERD);
			//			populator.getOutput().accept(CItems.ETHERION_HALBERD);
			populator.getOutput().accept(CItems.WOODEN_SCYTHE);
			populator.getOutput().accept(CItems.STONE_SCYTHE);
			populator.getOutput().accept(CItems.IRON_SCYTHE);
			populator.getOutput().accept(CItems.GOLDEN_SCYTHE);
			populator.getOutput().accept(CItems.DIAMOND_SCYTHE);
			populator.getOutput().accept(CItems.NETHERITE_SCYTHE);
			populator.getOutput().accept(CItems.BRONZE_SCYTHE);
			populator.getOutput().accept(CItems.STEEL_SCYTHE);
			//			populator.getOutput().accept(CItems.MAGISTEEL_SCYTHE);
			populator.getOutput().accept(CItems.PASQUEM_SCYTHE);
			populator.getOutput().accept(CItems.SERABLE_SCYTHE);
			populator.getOutput().accept(CItems.LOZYNE_SCYTHE);
			populator.getOutput().accept(CItems.MYTHRIL_SCYTHE);
			populator.getOutput().accept(CItems.ETHERION_SCYTHE);
			populator.getOutput().accept(CItems.BRONZE_HELMET);
			populator.getOutput().accept(CItems.BRONZE_CHESTPLATE);
			populator.getOutput().accept(CItems.BRONZE_LEGGINGS);
			populator.getOutput().accept(CItems.BRONZE_BOOTS);
			populator.getOutput().accept(CItems.PASQUEM_HELMET);
			populator.getOutput().accept(CItems.PASQUEM_CHESTPLATE);
			populator.getOutput().accept(CItems.PASQUEM_LEGGINGS);
			populator.getOutput().accept(CItems.PASQUEM_BOOTS);
			populator.getOutput().accept(CItems.STEEL_HELMET);
			populator.getOutput().accept(CItems.STEEL_CHESTPLATE);
			populator.getOutput().accept(CItems.STEEL_LEGGINGS);
			populator.getOutput().accept(CItems.STEEL_BOOTS);
			populator.getOutput().accept(CItems.SERABLE_HELMET);
			populator.getOutput().accept(CItems.SERABLE_CHESTPLATE);
			populator.getOutput().accept(CItems.SERABLE_LEGGINGS);
			populator.getOutput().accept(CItems.SERABLE_BOOTS);
			populator.getOutput().accept(CItems.LOZYNE_HELMET);
			populator.getOutput().accept(CItems.LOZYNE_CHESTPLATE);
			populator.getOutput().accept(CItems.LOZYNE_LEGGINGS);
			populator.getOutput().accept(CItems.LOZYNE_BOOTS);
			populator.getOutput().accept(CItems.ETHERION_HELMET);
			populator.getOutput().accept(CItems.ETHERION_CHESTPLATE);
			populator.getOutput().accept(CItems.ETHERION_LEGGINGS);
			populator.getOutput().accept(CItems.ETHERION_BOOTS);
			populator.getOutput().accept(CItems.LONGBOW);
			populator.getOutput().accept(CItems.ROUND_SHIELD);
			populator.getOutput().accept(CItems.SHURIKEN);
			populator.getOutput().accept(CItems.IRON_BOW);
			populator.getOutput().accept(CItems.GOLDEN_BOW);
			populator.getOutput().accept(CItems.BRONZE_BOW);
			populator.getOutput().accept(CItems.STEEL_BOW);
			populator.getOutput().accept(CItems.LOZYNE_BOW);
			populator.getOutput().accept(CItems.IRON_CROSSBOW);
			populator.getOutput().accept(CItems.GOLDEN_CROSSBOW);
			populator.getOutput().accept(CItems.BRONZE_CROSSBOW);
			populator.getOutput().accept(CItems.STEEL_CROSSBOW);
			populator.getOutput().accept(CItems.LOZYNE_CROSSBOW);
			populator.getOutput().accept(CItems.IRON_LONGBOW);
			populator.getOutput().accept(CItems.GOLDEN_LONGBOW);
			populator.getOutput().accept(CItems.BRONZE_LONGBOW);
			populator.getOutput().accept(CItems.STEEL_LONGBOW);
			populator.getOutput().accept(CItems.LOZYNE_LONGBOW);
			populator.getOutput().accept(CItems.IRON_TOWER_SHIELD);
			populator.getOutput().accept(CItems.IRON_ROUND_SHIELD);
			populator.getOutput().accept(CItems.GOLDEN_TOWER_SHIELD);
			populator.getOutput().accept(CItems.GOLDEN_ROUND_SHIELD);
			populator.getOutput().accept(CItems.BRONZE_TOWER_SHIELD);
			populator.getOutput().accept(CItems.BRONZE_ROUND_SHIELD);
			populator.getOutput().accept(CItems.STEEL_TOWER_SHIELD);
			populator.getOutput().accept(CItems.STEEL_ROUND_SHIELD);
			populator.getOutput().accept(CItems.LOZYNE_TOWER_SHIELD);
			populator.getOutput().accept(CItems.LOZYNE_ROUND_SHIELD);
			populator.getOutput().accept(CItems.WOODEN_ARROW);
			populator.getOutput().accept(CItems.GOLDEN_ARROW);
			populator.getOutput().accept(CItems.QUARTZ_ARROW);
			populator.getOutput().accept(CItems.IRON_ARROW);
			populator.getOutput().accept(CItems.DIAMOND_ARROW);
			populator.getOutput().accept(CItems.OBSIDIAN_ARROW);
			populator.getOutput().accept(CItems.WOODEN_TIPPED_ARROW);
			populator.getOutput().accept(CItems.GOLDEN_TIPPED_ARROW);
			populator.getOutput().accept(CItems.QUARTZ_TIPPED_ARROW);
			populator.getOutput().accept(CItems.IRON_TIPPED_ARROW);
			populator.getOutput().accept(CItems.DIAMOND_TIPPED_ARROW);
			populator.getOutput().accept(CItems.OBSIDIAN_TIPPED_ARROW);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.BATTLE.getDisplayName()) && Config.COMMON.load_guns.get()) {
			populator.getOutput().accept(CItems.PISTOL_MAGAZINE);
			populator.getOutput().accept(CItems.PISTOL_EMPTY_MAGAZINE);
			populator.getOutput().accept(CItems.GLOCK_17);
			populator.getOutput().accept(CItems.SIG_SAUER_P320);
			populator.getOutput().accept(CItems.BERETTA_92);
			populator.getOutput().accept(CItems.WALTHER_P99);
			populator.getOutput().accept(CItems.SIG_SAUER_P226);
			populator.getOutput().accept(CItems.CZ_75);
			populator.getOutput().accept(CItems.BERETTA_PX4_STORM);
			populator.getOutput().accept(CItems.AK_103);
			populator.getOutput().accept(CItems.STEYR_AUG);
			populator.getOutput().accept(CItems.FN_SCAR);
			populator.getOutput().accept(CItems.SIG_SG550);
			populator.getOutput().accept(CItems.GALIL);
			populator.getOutput().accept(CItems.M16);
			populator.getOutput().accept(CItems.HK_416);
			populator.getOutput().accept(CItems.HK_G3);
			populator.getOutput().accept(CItems.HK_G36);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.BUILDING_BLOCKS.getDisplayName())) {
			populator.getOutput().accept(CItems.AUSLDINE_BUTTON);
			populator.getOutput().accept(CItems.AUSLDINE_DOOR);
			populator.getOutput().accept(CItems.AUSLDINE_FENCE_GATE);
			populator.getOutput().accept(CItems.AUSLDINE_FENCE);
			populator.getOutput().accept(CItems.AUSLDINE_LEAVES);
			populator.getOutput().accept(CItems.AUSLDINE_LOG);
			populator.getOutput().accept(CItems.AUSLDINE_PLANKS);
			populator.getOutput().accept(CItems.AUSLDINE_PRESSURE_PLATE);
			populator.getOutput().accept(CItems.AUSLDINE_SAPLING);
			populator.getOutput().accept(CItems.AUSLDINE_SLAB);
			populator.getOutput().accept(CItems.AUSLDINE_STAIRS);
			populator.getOutput().accept(CItems.AUSLDINE_TRAPDOOR);
			populator.getOutput().accept(CItems.AUSLDINE_WOOD);
			populator.getOutput().accept(CItems.STRIPPED_AUSLDINE_LOG);
			populator.getOutput().accept(CItems.STRIPPED_AUSLDINE_WOOD);
			populator.getOutput().accept(CItems.DEAD_OAK_BUTTON);
			populator.getOutput().accept(CItems.DEAD_OAK_DOOR);
			populator.getOutput().accept(CItems.DEAD_OAK_FENCE_GATE);
			populator.getOutput().accept(CItems.DEAD_OAK_FENCE);
			populator.getOutput().accept(CItems.DEAD_OAK_LOG);
			populator.getOutput().accept(CItems.DEAD_OAK_PLANKS);
			populator.getOutput().accept(CItems.DEAD_OAK_PRESSURE_PLATE);
			populator.getOutput().accept(CItems.DEAD_OAK_SLAB);
			populator.getOutput().accept(CItems.DEAD_OAK_STAIRS);
			populator.getOutput().accept(CItems.DEAD_OAK_TRAPDOOR);
			populator.getOutput().accept(CItems.DEAD_OAK_WOOD);
			populator.getOutput().accept(CItems.STRIPPED_DEAD_OAK_LOG);
			populator.getOutput().accept(CItems.STRIPPED_DEAD_OAK_WOOD);
			populator.getOutput().accept(CItems.MONORIS_BUTTON);
			populator.getOutput().accept(CItems.MONORIS_DOOR);
			populator.getOutput().accept(CItems.MONORIS_FENCE_GATE);
			populator.getOutput().accept(CItems.MONORIS_FENCE);
			populator.getOutput().accept(CItems.MONORIS_LEAVES);
			populator.getOutput().accept(CItems.MONORIS_LOG);
			populator.getOutput().accept(CItems.MONORIS_PLANKS);
			//			populator.getOutput().accept(CItems.MONORIS_PRESSURE_PLATE);
			populator.getOutput().accept(CItems.MONORIS_SAPLING);
			populator.getOutput().accept(CItems.MONORIS_SLAB);
			populator.getOutput().accept(CItems.MONORIS_STAIRS);
			//			populator.getOutput().accept(CItems.MONORIS_TRAPDOOR);
			populator.getOutput().accept(CItems.MONORIS_WOOD);
			populator.getOutput().accept(CItems.STRIPPED_MONORIS_LOG);
			populator.getOutput().accept(CItems.STRIPPED_MONORIS_WOOD);
			populator.getOutput().accept(CItems.REZAL_LEAVES);
			populator.getOutput().accept(CItems.REZAL_LOG);
			populator.getOutput().accept(CItems.REZAL_PLANKS);
			populator.getOutput().accept(CItems.REZAL_WOOD);
			populator.getOutput().accept(CItems.REZAL_SAPLING);
			populator.getOutput().accept(CItems.COPPER_DOOR);
			populator.getOutput().accept(CItems.CALTAS);
			populator.getOutput().accept(CItems.MEZEPINE);
			populator.getOutput().accept(CItems.SLYAPHY);
			populator.getOutput().accept(CItems.COBBLED_SLYAPHY);
			populator.getOutput().accept(CItems.CASSITERITE);
			populator.getOutput().accept(CItems.PASQUEM_ORE);
			populator.getOutput().accept(CItems.TRIDOX_ORE);
			populator.getOutput().accept(CItems.RUBY_ORE);
			populator.getOutput().accept(CItems.PELGAN_ORE);
			populator.getOutput().accept(CItems.SLYAPHY_PELGAN_ORE);
			populator.getOutput().accept(CItems.LOZYNE_ORE);
			populator.getOutput().accept(CItems.SLYAPHY_LOZYNE_ORE);
			populator.getOutput().accept(CItems.PYRANITE_ORE);
			populator.getOutput().accept(CItems.SERABLE_ORE);
			populator.getOutput().accept(CItems.PASQUEM_BLOCK);
			populator.getOutput().accept(CItems.TRIDOX_BLOCK);
			populator.getOutput().accept(CItems.RUBY_BLOCK);
			populator.getOutput().accept(CItems.PELGAN_BLOCK);
			populator.getOutput().accept(CItems.LOZYNE_BLOCK);
			populator.getOutput().accept(CItems.STEEL_BLOCK);
			populator.getOutput().accept(CItems.BRONZE_BLOCK);
			populator.getOutput().accept(CItems.ETHERION_BLOCK);
			populator.getOutput().accept(CItems.SERABLE_BLOCK);
			populator.getOutput().accept(CItems.MEZEPINE_BRICKS);
			populator.getOutput().accept(CItems.MEZEPINE_SLAB);
			populator.getOutput().accept(CItems.MEZEPINE_BRICK_SLAB);
			populator.getOutput().accept(CItems.MEZEPINE_BRICK_STAIRS);
			populator.getOutput().accept(CItems.HOMSE);
			populator.getOutput().accept(CItems.OAK_PODIUM);
			populator.getOutput().accept(CItems.SPRUCE_PODIUM);
			populator.getOutput().accept(CItems.BIRCH_PODIUM);
			populator.getOutput().accept(CItems.JUNGLE_PODIUM);
			populator.getOutput().accept(CItems.ACACIA_PODIUM);
			populator.getOutput().accept(CItems.DARK_OAK_PODIUM);
			populator.getOutput().accept(CItems.CRIMSON_PODIUM);
			populator.getOutput().accept(CItems.WARPED_PODIUM);
			populator.getOutput().accept(CItems.AUSLDINE_PODIUM);
			populator.getOutput().accept(CItems.DEAD_OAK_PODIUM);
			populator.getOutput().accept(CItems.MONORIS_PODIUM);
			populator.getOutput().accept(CItems.OAK_BEAM);
			populator.getOutput().accept(CItems.SPRUCE_BEAM);
			populator.getOutput().accept(CItems.BIRCH_BEAM);
			populator.getOutput().accept(CItems.JUNGLE_BEAM);
			populator.getOutput().accept(CItems.ACACIA_BEAM);
			populator.getOutput().accept(CItems.DARK_OAK_BEAM);
			populator.getOutput().accept(CItems.CRIMSON_BEAM);
			populator.getOutput().accept(CItems.WARPED_BEAM);
			populator.getOutput().accept(CItems.AUSLDINE_BEAM);
			populator.getOutput().accept(CItems.DEAD_OAK_BEAM);
			populator.getOutput().accept(CItems.MONORIS_BEAM);
			populator.getOutput().accept(CItems.WHITE_TSUNE);
			populator.getOutput().accept(CItems.ORANGE_TSUNE);
			populator.getOutput().accept(CItems.MAGENTA_TSUNE);
			populator.getOutput().accept(CItems.LIGHT_BLUE_TSUNE);
			populator.getOutput().accept(CItems.YELLOW_TSUNE);
			populator.getOutput().accept(CItems.LIME_TSUNE);
			populator.getOutput().accept(CItems.PINK_TSUNE);
			populator.getOutput().accept(CItems.GRAY_TSUNE);
			populator.getOutput().accept(CItems.LIGHT_GRAY_TSUNE);
			populator.getOutput().accept(CItems.CYAN_TSUNE);
			populator.getOutput().accept(CItems.PURPLE_TSUNE);
			populator.getOutput().accept(CItems.BLUE_TSUNE);
			populator.getOutput().accept(CItems.BROWN_TSUNE);
			populator.getOutput().accept(CItems.GREEN_TSUNE);
			populator.getOutput().accept(CItems.RED_TSUNE);
			populator.getOutput().accept(CItems.BLACK_TSUNE);
			populator.getOutput().accept(CItems.LIMESTONE);
			populator.getOutput().accept(CItems.PURIFIED_DIRT);
			populator.getOutput().accept(CItems.PURIFIED_GRASS_BLOCK);
			populator.getOutput().accept(CItems.ELYCEN_BLOCK);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.BUILDING_BLOCKS.getDisplayName()) && Combat.isBOPLoaded(false)) {
			populator.getOutput().accept(CItems.FIR_PODIUM);
			populator.getOutput().accept(CItems.REDWOOD_PODIUM);
			populator.getOutput().accept(CItems.CHERRY_PODIUM);
			populator.getOutput().accept(CItems.MAHOGANY_PODIUM);
			populator.getOutput().accept(CItems.JACARANDA_PODIUM);
			populator.getOutput().accept(CItems.PALM_PODIUM);
			populator.getOutput().accept(CItems.WILLOW_PODIUM);
			populator.getOutput().accept(CItems.DEAD_PODIUM);
			populator.getOutput().accept(CItems.MAGIC_PODIUM);
			populator.getOutput().accept(CItems.UMBRAN_PODIUM);
			populator.getOutput().accept(CItems.HELLBARK_PODIUM);
			populator.getOutput().accept(CItems.FIR_BEAM);
			populator.getOutput().accept(CItems.REDWOOD_BEAM);
			populator.getOutput().accept(CItems.CHERRY_BEAM);
			populator.getOutput().accept(CItems.MAHOGANY_BEAM);
			populator.getOutput().accept(CItems.JACARANDA_BEAM);
			populator.getOutput().accept(CItems.PALM_BEAM);
			populator.getOutput().accept(CItems.WILLOW_BEAM);
			populator.getOutput().accept(CItems.DEAD_BEAM);
			populator.getOutput().accept(CItems.MAGIC_BEAM);
			populator.getOutput().accept(CItems.UMBRAN_BEAM);
			populator.getOutput().accept(CItems.HELLBARK_BEAM);
		}
		if (populator.getTab() == CCreativeModeTab.TOOLS) {
			populator.getOutput().accept(CItems.BRONZE_AXE);
			populator.getOutput().accept(CItems.STEEL_AXE);
			populator.getOutput().accept(CItems.MAGISTEEL_AXE);
			populator.getOutput().accept(CItems.PASQUEM_AXE);
			populator.getOutput().accept(CItems.SERABLE_AXE);
			populator.getOutput().accept(CItems.LOZYNE_AXE);
			populator.getOutput().accept(CItems.MYTHRIL_AXE);
			populator.getOutput().accept(CItems.ETHERION_AXE);
			populator.getOutput().accept(CItems.BRONZE_HOE);
			populator.getOutput().accept(CItems.STEEL_HOE);
			//			populator.getOutput().accept(CItems.MAGISTEEL_HOE);
			populator.getOutput().accept(CItems.PASQUEM_HOE);
			populator.getOutput().accept(CItems.SERABLE_HOE);
			populator.getOutput().accept(CItems.LOZYNE_HOE);
			populator.getOutput().accept(CItems.MYTHRIL_HOE);
			populator.getOutput().accept(CItems.ETHERION_HOE);
			populator.getOutput().accept(CItems.BRONZE_PICKAXE);
			populator.getOutput().accept(CItems.STEEL_PICKAXE);
			//			populator.getOutput().accept(CItems.MAGISTEEL_PICKAXE);
			populator.getOutput().accept(CItems.PASQUEM_PICKAXE);
			populator.getOutput().accept(CItems.SERABLE_PICKAXE);
			populator.getOutput().accept(CItems.LOZYNE_PICKAXE);
			populator.getOutput().accept(CItems.MYTHRIL_PICKAXE);
			populator.getOutput().accept(CItems.ETHERION_PICKAXE);
			populator.getOutput().accept(CItems.BRONZE_SHOVEL);
			populator.getOutput().accept(CItems.STEEL_SHOVEL);
			//			populator.getOutput().accept(CItems.MAGISTEEL_SHOVEL);
			populator.getOutput().accept(CItems.PASQUEM_SHOVEL);
			populator.getOutput().accept(CItems.SERABLE_SHOVEL);
			populator.getOutput().accept(CItems.LOZYNE_SHOVEL);
			populator.getOutput().accept(CItems.MYTHRIL_SHOVEL);
			populator.getOutput().accept(CItems.ETHERION_SHOVEL);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.COMBAT_TAB_MISC.getDisplayName())) {
			populator.getOutput().accept(CItems.COPPER_BARS);
			populator.getOutput().accept(CItems.GROPAPY);
			populator.getOutput().accept(CItems.VAMPIRE_BLOOD);
			populator.getOutput().accept(CItems.BLOOD);
			populator.getOutput().accept(CItems.CORN_SEEDS);
			populator.getOutput().accept(CItems.GOLD_ROD);
			populator.getOutput().accept(CItems.IRON_ROD);
			populator.getOutput().accept(CItems.STEEL_ROD);
			populator.getOutput().accept(CItems.SERABLE_ROD);
			populator.getOutput().accept(CItems.QUIVER);
			populator.getOutput().accept(CItems.BACKPACK);
			populator.getOutput().accept(CItems.SHEATH);
			populator.getOutput().accept(CItems.TIN_INGOT);
			populator.getOutput().accept(CItems.BRONZE_INGOT);
			populator.getOutput().accept(CItems.PASQUEM_INGOT);
			populator.getOutput().accept(CItems.STEEL_INGOT);
			populator.getOutput().accept(CItems.PELGAN_INGOT);
			populator.getOutput().accept(CItems.RAW_LOZYNE);
			populator.getOutput().accept(CItems.LOZYNE_INGOT);
			populator.getOutput().accept(CItems.SERABLE_INGOT);
			populator.getOutput().accept(CItems.ETHERION_INGOT);
			populator.getOutput().accept(CItems.MAGISTEEL_INGOT);
			populator.getOutput().accept(CItems.MYTHRIL_INGOT);
			populator.getOutput().accept(CItems.COPPER_NUGGET);
			populator.getOutput().accept(CItems.BRONZE_NUGGET);
			populator.getOutput().accept(CItems.SERABLE_NUGGET);
			populator.getOutput().accept(CItems.TIN_NUGGET);
			populator.getOutput().accept(CItems.SLAG);
			populator.getOutput().accept(CItems.OIL_BUCKET);
			populator.getOutput().accept(CItems.BIABLE_BUCKET);
			populator.getOutput().accept(CItems.CARDBOARD_PAPER);
			populator.getOutput().accept(CItems.PEBBLE);
			populator.getOutput().accept(CItems.BIOG_PELT);
			populator.getOutput().accept(CItems.VAMPIRE_SPAWN_EGG);
			populator.getOutput().accept(CItems.ZOMBIE_COW_SPAWN_EGG);
			populator.getOutput().accept(CItems.BIOG_SPAWN_EGG);
			populator.getOutput().accept(CItems.RED_BIOG_SPAWN_EGG);
			populator.getOutput().accept(CItems.PYRANITE);
			populator.getOutput().accept(CItems.WHITE_ETHERION);
			populator.getOutput().accept(CItems.BLUE_ETHERION);
			populator.getOutput().accept(CItems.RUBY);
			populator.getOutput().accept(CItems.LIMESTONE_ROCK);
			populator.getOutput().accept(CItems.ETHERION_COMPASS);
			populator.getOutput().accept(CItems.WARPED_BEAM);
			populator.getOutput().accept(CItems.BRONZE_HORSE_ARMOR);
			populator.getOutput().accept(CItems.SERABLE_HORSE_ARMOR);
			populator.getOutput().accept(CItems.ROBIN_SUMMONER);
			populator.getOutput().accept(CItems.EMPTY_ROBIN_SUMMONER);
			populator.getOutput().accept(CItems.MEZEPINE_FURNACE);
		}
		if (populator.getTab().getDisplayName().equals(CCreativeModeTab.TECHNOLOGY.getDisplayName())) {
			populator.getOutput().accept(CItems.CARDBOARD_BOX);
			populator.getOutput().accept(CItems.MANA_GENERATOR);
			populator.getOutput().accept(CItems.CONNECTOR);
			populator.getOutput().accept(CItems.ELECTRIC_FURNACE);
			populator.getOutput().accept(CItems.MYTHRIL_CHARGER);
			populator.getOutput().accept(CItems.LIGHT_SABER);
			populator.getOutput().accept(CItems.COPPER_WIRE);
		}
		if (populator.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			populator.getOutput().accept(CItems.BRONZE_SHEARS);
			populator.getOutput().accept(CItems.AUSLDINE_BOAT);
			populator.getOutput().accept(CItems.DEAD_OAK_BOAT);
			populator.getOutput().accept(CItems.MONORIS_BOAT);
			populator.getOutput().accept(CItems.REZAL_BOAT);
		}
		if (populator.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
			populator.getOutput().accept(CItems.CORN);
			populator.getOutput().accept(CItems.ROAST_CORN);
		}
		if (populator.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
			populator.getOutput().accept(CItems.COPPER_TRAPDOOR);
			populator.getOutput().accept(CItems.TORCH_LEVER);
		}
		if (populator.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			populator.getOutput().accept(CItems.WOODCUTTER);
			populator.getOutput().accept(CItems.ALLOY_FURNACE);
			populator.getOutput().accept(CItems.PYRANITE_TORCH);
			populator.getOutput().accept(CItems.PYRANITE_LANTERN);
			populator.getOutput().accept(CItems.AUSLDINE_SIGN);
			populator.getOutput().accept(CItems.DEAD_OAK_SIGN);
		}
	}

	public static void debug(Object message) {
		if (/*Config.COMMON.debug_mode.get()*/false)Combat.getInstance().getLogger().info(message);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> {
			Regions.register(new CombatR(new ResourceLocation("combat:replace"), 2));
		});
		if (ModHelper.isCuriosLoaded())
			MinecraftForge.EVENT_BUS.register(CuriosEvents.class);
		BrewingPotion.registerBrewing();
		CCriteriaTriggers.registerAll();
		setBurnableBlocks();
		PyraniteFireBlock.bootStrap();
		CTags.init();
		new BlockCTags();
		new ItemCTags();
		new EntityTypeCTags();
		new BiomeCTags();
		CTiers.handleModdedTiers();
		CGameRules.init();
		Map<Block, Block> STRIP_MAP = (new Builder<Block, Block>())
				.put(CBlocks.AUSLDINE_WOOD, CBlocks.STRIPPED_AUSLDINE_WOOD).put(CBlocks.AUSLDINE_LOG, CBlocks.STRIPPED_AUSLDINE_LOG)
				.put(CBlocks.DEAD_OAK_WOOD, CBlocks.STRIPPED_DEAD_OAK_WOOD).put(CBlocks.DEAD_OAK_LOG, CBlocks.STRIPPED_DEAD_OAK_LOG)
				.put(CBlocks.MONORIS_WOOD, CBlocks.STRIPPED_MONORIS_WOOD).put(CBlocks.MONORIS_LOG, CBlocks.STRIPPED_MONORIS_LOG)
				.putAll(AxeItem.STRIPPABLES)
				.build();
		AxeItem.STRIPPABLES = STRIP_MAP;
	}

	@SuppressWarnings("resource")
	public void clientRegistries(final FMLClientSetupEvent event)
	{
		CItemProperties.register();
		boolean useMain = false;
		CModelLayers.init();
		EntityRendererHandler.bootStrap();
		BlockEntityRenderHandler.registerRenders();
		CScreens.registerScreens();
		if (CLIENT_CONFIG.toggle_custom_main_menu)
			TitleScreen.CUBE_MAP = new CubeMap(Combat.getInstance().location("textures/gui/title/background/panorama"));
		if (useMain) TitleScreen.PANORAMA_OVERLAY = Combat.getInstance().location("textures/gui/title/background/combat_overlay.png");
		RenderType rendertype = RenderType.cutoutMipped();
		ItemBlockRenderTypes.setRenderLayer(CBlocks.AUSLDINE_LEAVES, rendertype);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.MONORIS_LEAVES, rendertype);
		RenderType rendertype1 = RenderType.cutout();
		ItemBlockRenderTypes.setRenderLayer(CBlocks.EMPTY_ROBIN_SUMMONER, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.AUSLDINE_SAPLING, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.MONORIS_SAPLING, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.POTTED_AUSLDINE_SAPLING, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.POTTED_MONORIS_SAPLING, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.PYRANITE_FIRE, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.TORCH_LEVER, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.WALL_TORCH_LEVER, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.CORN, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.WOODCUTTER, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.PYRANITE_TORCH, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.PYRANITE_WALL_TORCH, rendertype1);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.PYRANITE_LANTERN, rendertype1);
		RenderType rendertype2 = RenderType.translucent();
		ItemBlockRenderTypes.setRenderLayer(CBlocks.ACROTLEST_RUINED_PORTAL, rendertype2);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.ACROTLEST_PORTAL, rendertype2);
		ItemBlockRenderTypes.setRenderLayer(CBlocks.GROPAPY, rendertype2);

		RenderType frendertype = RenderType.translucent();
		ItemBlockRenderTypes.setRenderLayer(CFluids.BIABLE, frendertype);
		ItemBlockRenderTypes.setRenderLayer(CFluids.OIL, frendertype);

		SortedMap<RenderType, BufferBuilder> fixedBuffers = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (p_228485_1_) -> {
			put(p_228485_1_, CRenderType.getLegendaryArmorGlint());
			put(p_228485_1_, CRenderType.getLegendaryArmorEntityGlint());
			put(p_228485_1_, CRenderType.getLegendaryGlint());
			put(p_228485_1_, CRenderType.getLegendaryGlintDirect());
			put(p_228485_1_, CRenderType.getLegendaryGlintTranslucent());
			put(p_228485_1_, CRenderType.getLegendaryEntityGlint());
			put(p_228485_1_, CRenderType.getLegendaryEntityGlintDirect());

			put(p_228485_1_, CRenderType.getMythrilArmorGlint());
			put(p_228485_1_, CRenderType.getMythrilArmorEntityGlint());
			put(p_228485_1_, CRenderType.getMythrilGlint());
			put(p_228485_1_, CRenderType.getMythrilGlintDirect());
			put(p_228485_1_, CRenderType.getMythrilGlintTranslucent());
			put(p_228485_1_, CRenderType.getMythrilEntityGlint());
			put(p_228485_1_, CRenderType.getMythrilEntityGlintDirect());

			put(p_228485_1_, CRenderType.getEncMythrilArmorGlint());
			put(p_228485_1_, CRenderType.getEncMythrilArmorEntityGlint());
			put(p_228485_1_, CRenderType.getEncMythrilGlint());
			put(p_228485_1_, CRenderType.getEncMythrilGlintDirect());
			put(p_228485_1_, CRenderType.getEncMythrilGlintTranslucent());
			put(p_228485_1_, CRenderType.getEnchantedMythrilEntityGlint());
			put(p_228485_1_, CRenderType.getEncMythrilEntityGlintDirect());
		});
		Minecraft.getInstance().renderBuffers().fixedBuffers.putAll(fixedBuffers);
		if (ModHelper.isCuriosLoaded())
			CuriosCompat.registerCurioRenders();
	}

	@OnlyIn(Dist.CLIENT)
	public void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> mapBuildersIn, RenderType renderTypeIn) {
		mapBuildersIn.put(renderTypeIn, new BufferBuilder(renderTypeIn.bufferSize()));
	}

	public void enqueue(final InterModEnqueueEvent event) {
		if (ModHelper.isCuriosLoaded()) {
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().size(1).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(1).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("book").size(1).icon(EMPTY_SPELLBOOK_SLOT).build());
		}
	} 

	public void registerCommands(RegisterCommandsEvent event) {
		CCommands.registerCommands(event.getDispatcher());
	}

	//	@SubscribeEvent
	//	public void onServerStarting(FMLServerStartingEvent event) {
	//	}
	//
	//	@SubscribeEvent
	//	public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
	//	}

	public static void setBurnableBlocks() {
		setFlammable(CBlocks.AUSLDINE_PLANKS, 5, 20);
		setFlammable(CBlocks.DEAD_OAK_PLANKS, 5, 20);
		setFlammable(CBlocks.AUSLDINE_SLAB, 5, 20);
		setFlammable(CBlocks.DEAD_OAK_SLAB, 5, 20);
		setFlammable(CBlocks.AUSLDINE_FENCE_GATE, 5, 20);
		setFlammable(CBlocks.DEAD_OAK_FENCE_GATE, 5, 20);
		setFlammable(CBlocks.AUSLDINE_FENCE, 5, 20);
		setFlammable(CBlocks.DEAD_OAK_FENCE, 5, 20);
		setFlammable(CBlocks.AUSLDINE_STAIRS, 5, 20);
		setFlammable(CBlocks.DEAD_OAK_STAIRS, 5, 20);
		setFlammable(CBlocks.AUSLDINE_LOG, 5, 5);
		setFlammable(CBlocks.DEAD_OAK_LOG, 5, 5);
		setFlammable(CBlocks.STRIPPED_AUSLDINE_LOG, 5, 5);
		setFlammable(CBlocks.STRIPPED_DEAD_OAK_LOG, 5, 5);
		setFlammable(CBlocks.STRIPPED_AUSLDINE_WOOD, 5, 5);
		setFlammable(CBlocks.STRIPPED_DEAD_OAK_WOOD, 5, 5);
		setFlammable(CBlocks.AUSLDINE_WOOD, 5, 5);
		setFlammable(CBlocks.DEAD_OAK_WOOD, 5, 5);
		setFlammable(CBlocks.AUSLDINE_LEAVES, 30, 60);
		setFlammable(CBlocks.OIL, 50, 100);
	}

	public static void setFlammable(Block blockIn, int encouragement, int flammability) {
		PyraniteFireBlock pyranitefire = (PyraniteFireBlock)CBlocks.PYRANITE_FIRE;
		pyranitefire.setFlammable(blockIn, encouragement, flammability);
		FireBlock fire = (FireBlock)Blocks.FIRE;
		fire.setFlammable(blockIn, encouragement, flammability);
	}

	public static Combat getInstance() {
		return combatInstance;
	}

	public class Locations {
		public static final ResourceLocation SPEAR_TEXTURE = new ResourceLocation(Combat.MODID, "textures/entity/projectiles/spear.png");
	}
}
