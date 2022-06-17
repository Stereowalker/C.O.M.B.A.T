package com.stereowalker.combat;

import java.util.Map;
import java.util.SortedMap;

import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.stereowalker.combat.advancements.CCriteriaTriggers;
import com.stereowalker.combat.client.KeyMappings;
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
import com.stereowalker.combat.config.BattleConfig;
import com.stereowalker.combat.config.MagicConfig;
import com.stereowalker.combat.config.RpgConfig;
import com.stereowalker.combat.core.registries.RegistryOverrides;
import com.stereowalker.combat.data.worldgen.features.COreFeatures;
import com.stereowalker.combat.data.worldgen.features.CTreeFeatures;
import com.stereowalker.combat.data.worldgen.features.CVegetationFeatures;
import com.stereowalker.combat.data.worldgen.placement.COrePlacements;
import com.stereowalker.combat.data.worldgen.placement.CTreePlacements;
import com.stereowalker.combat.data.worldgen.placement.CVegetationPlacements;
import com.stereowalker.combat.network.protocol.game.ClientboundAbominationPacket;
import com.stereowalker.combat.network.protocol.game.ClientboundPlayerStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundBackItemPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundClientMotionPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundGunPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundHeldItemStackNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundMageSetupPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundPronePacket;
import com.stereowalker.combat.network.protocol.game.ServerboundRequestStatsPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSetLimiterPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSpellbookNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundStoreItemPacket;
import com.stereowalker.combat.tags.BiomeCTags;
import com.stereowalker.combat.tags.BlockCTags;
import com.stereowalker.combat.tags.CTags;
import com.stereowalker.combat.tags.EntityTypeCTags;
import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.world.item.CTiers;
import com.stereowalker.combat.world.item.alchemy.BrewingPotion;
import com.stereowalker.combat.world.level.CGameRules;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.block.PyraniteFireBlock;
import com.stereowalker.combat.world.level.dimension.CDimensionType;
import com.stereowalker.combat.world.level.levelgen.CNoiseGeneratorSettings;
import com.stereowalker.combat.world.level.material.CFluids;
import com.stereowalker.combat.world.level.storage.loot.functions.CLootItemFunctions;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.old.combat.config.RpgClientConfig;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.network.protocol.game.RankupNetRegistry;
import com.stereowalker.unionlib.client.gui.screens.config.MinecraftModConfigsScreen;
import com.stereowalker.unionlib.config.ConfigBuilder;
import com.stereowalker.unionlib.mod.IPacketHolder;
import com.stereowalker.unionlib.mod.MinecraftMod;
import com.stereowalker.unionlib.network.PacketRegistry;
import com.stereowalker.unionlib.util.ModHelper;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
//import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;
//import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(Combat.MODID)
public class Combat extends MinecraftMod implements IPacketHolder
{
	public static final String MODID = "combat";
	private static Combat combatInstance;
	public static Rankup rankupInstance;
	public static final RpgConfig RPG_CONFIG = new RpgConfig();
	public static final BattleConfig BATTLE_CONFIG = new BattleConfig();
	public static final MagicConfig MAGIC_CONFIG = new MagicConfig();
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
		RegistryOverrides.override();
		if (!Combat.disableConfig()) Config.registerConfigs();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		//MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public KeyMapping[] getModKeyMappings() {
		return new KeyMapping[] {KeyMappings.NEXT_SPELL, KeyMappings.PREV_SPELL, KeyMappings.PLAYER_LEVELS, KeyMappings.TOGGLE_LIMITER,
				KeyMappings.RELOAD, KeyMappings.FIRE, KeyMappings.OPEN_BACK_ITEM, KeyMappings.STORE_ITEM, KeyMappings.PRONE};
	}

	@Override
	public void registerClientboundPackets(SimpleChannel arg0) {
		RankupNetRegistry.registerMessages(arg0);
		arg0.registerMessage(0, ClientboundAbominationPacket.class, ClientboundAbominationPacket::encode, ClientboundAbominationPacket::decode, ClientboundAbominationPacket::handle);
		arg0.registerMessage(1, ClientboundPlayerStatsPacket.class, ClientboundPlayerStatsPacket::encode, ClientboundPlayerStatsPacket::decode, ClientboundPlayerStatsPacket::handle);
		
	}

	@Override
	public void registerServerboundPackets(SimpleChannel arg0) {
		int netID = 2;
		//client
		PacketRegistry.registerMessage(arg0, 2, ServerboundBackItemPacket.class, (packetBuffer) -> {return new ServerboundBackItemPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundGunPacket.class, (packetBuffer) -> {return new ServerboundGunPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundMageSetupPacket.class, (packetBuffer) -> {return new ServerboundMageSetupPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundSetLimiterPacket.class, (packetBuffer) -> {return new ServerboundSetLimiterPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundStoreItemPacket.class, (packetBuffer) -> {return new ServerboundStoreItemPacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundPronePacket.class, (packetBuffer) -> {return new ServerboundPronePacket(packetBuffer);});
		PacketRegistry.registerMessage(arg0, netID++, ServerboundClientMotionPacket.class, (packetBuffer) -> {return new ServerboundClientMotionPacket(packetBuffer);});
		arg0.registerMessage(netID++, ServerboundHeldItemStackNBTPacket.class, ServerboundHeldItemStackNBTPacket::encode, ServerboundHeldItemStackNBTPacket::decode, ServerboundHeldItemStackNBTPacket::handle);
		arg0.registerMessage(netID++, ServerboundRequestStatsPacket.class, ServerboundRequestStatsPacket::encode, ServerboundRequestStatsPacket::decode, ServerboundRequestStatsPacket::handle);
		arg0.registerMessage(netID++, ServerboundSpellbookNBTPacket.class, ServerboundSpellbookNBTPacket::encode, ServerboundSpellbookNBTPacket::decode, ServerboundSpellbookNBTPacket::handle);
		
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Screen getConfigScreen(Minecraft mc, Screen previousScreen) {
		return new MinecraftModConfigsScreen(previousScreen, new TranslatableComponent("gui.combat.config.title"), RPG_CONFIG, BATTLE_CONFIG, MAGIC_CONFIG);
		
	}

	public static void debug(Object message) {
		if (Config.COMMON.debug_mode.get())Combat.getInstance().getLogger().info(message);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
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
		if (Config.CLIENT.toggle_custom_main_menu.get())
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

		DimensionSpecialEffects.EFFECTS.put(CDimensionType.ACROTLEST_ID, new CDimensionSpecialEffects.Acrotlest());
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
