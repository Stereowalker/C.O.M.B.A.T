package com.stereowalker.combat;

import java.util.Map;
import java.util.SortedMap;

import com.google.common.collect.ImmutableMap.Builder;
import com.stereowalker.combat.block.CBlocks;
import com.stereowalker.combat.block.PyraniteFireBlock;
import com.stereowalker.combat.client.gui.screen.CombatConfigScreen;
import com.stereowalker.combat.client.gui.screen.inventory.CScreens;
import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.combat.client.renderer.CRenderType;
import com.stereowalker.combat.client.renderer.entity.EntityRendererHandler;
import com.stereowalker.combat.client.renderer.tileentity.CombatTileEntityRender;
import com.stereowalker.combat.command.CCommands;
import com.stereowalker.combat.compat.curios.CuriosEvents;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.fluid.CFluids;
import com.stereowalker.combat.item.CItemModelsProperties;
import com.stereowalker.combat.loot.functions.CLootFunctionManager;
import com.stereowalker.combat.network.client.play.CBackItemPacket;
import com.stereowalker.combat.network.client.play.CGunPacket;
import com.stereowalker.combat.network.client.play.CHeldItemStackNBTPacket;
import com.stereowalker.combat.network.client.play.CMageSetupPacket;
import com.stereowalker.combat.network.client.play.CPronePacket;
import com.stereowalker.combat.network.client.play.CRequestStatsPacket;
import com.stereowalker.combat.network.client.play.CSetLimiterPacket;
import com.stereowalker.combat.network.client.play.CSpellbookNBTPacket;
import com.stereowalker.combat.network.client.play.CStoreItemPacket;
import com.stereowalker.combat.network.server.SAbominationPacket;
import com.stereowalker.combat.network.server.SPlayerStatsPacket;
import com.stereowalker.combat.potion.BrewingPotion;
import com.stereowalker.combat.registries.RegistryOverrides;
import com.stereowalker.combat.tags.CEntityTypeTags;
import com.stereowalker.combat.tags.CTags;
import com.stereowalker.combat.world.CDImensionRenderInfo;
import com.stereowalker.combat.world.CDimensionType;
import com.stereowalker.combat.world.CGameRules;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.unionlib.mod.UnionMod;
import com.stereowalker.unionlib.network.PacketRegistry;
import com.stereowalker.unionlib.util.ModHelper;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(value = "combat")
public class Combat extends UnionMod
{
	private static Combat combatInstance;
	public static Rankup rankupInstance;

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
		super("combat", new ResourceLocation("combat", "textures/icon.png"), LoadType.BOTH);
		combatInstance = this;
		rankupInstance = new Rankup();
		CLootFunctionManager.registerAll();
		RegistryOverrides.override();
		if (!Combat.disableConfig()) Config.registerConfigs();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
		//MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void registerMessages(SimpleChannel channel) {
		int netID = -1;
		//server
		channel.registerMessage(netID++, SAbominationPacket.class, SAbominationPacket::encode, SAbominationPacket::decode, SAbominationPacket::handle);
		channel.registerMessage(netID++, SPlayerStatsPacket.class, SPlayerStatsPacket::encode, SPlayerStatsPacket::decode, SPlayerStatsPacket::handle);
		//client
		PacketRegistry.registerMessage(channel, netID++, CBackItemPacket.class, (packetBuffer) -> {return new CBackItemPacket(packetBuffer);});
		PacketRegistry.registerMessage(channel, netID++, CGunPacket.class, (packetBuffer) -> {return new CGunPacket(packetBuffer);});
		PacketRegistry.registerMessage(channel, netID++, CMageSetupPacket.class, (packetBuffer) -> {return new CMageSetupPacket(packetBuffer);});
		PacketRegistry.registerMessage(channel, netID++, CSetLimiterPacket.class, (packetBuffer) -> {return new CSetLimiterPacket(packetBuffer);});
		PacketRegistry.registerMessage(channel, netID++, CStoreItemPacket.class, (packetBuffer) -> {return new CStoreItemPacket(packetBuffer);});
		PacketRegistry.registerMessage(channel, netID++, CPronePacket.class, (packetBuffer) -> {return new CPronePacket(packetBuffer);});
		channel.registerMessage(netID++, CHeldItemStackNBTPacket.class, CHeldItemStackNBTPacket::encode, CHeldItemStackNBTPacket::decode, CHeldItemStackNBTPacket::handle);
		channel.registerMessage(netID++, CRequestStatsPacket.class, CRequestStatsPacket::encode, CRequestStatsPacket::decode, CRequestStatsPacket::handle);
		channel.registerMessage(netID++, CSpellbookNBTPacket.class, CSpellbookNBTPacket::encode, CSpellbookNBTPacket::decode, CSpellbookNBTPacket::handle);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Screen getConfigScreen(Minecraft mc, Screen previousScreen) {
		return new CombatConfigScreen(mc, previousScreen);
	}

	public static void debug(Object message) {
		if (Config.COMMON.debug_mode.get())Combat.getInstance().LOGGER.debug(message);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		if (ModHelper.isCuriosLoaded())
			MinecraftForge.EVENT_BUS.register(CuriosEvents.class);
		BrewingPotion.registerBrewing();
		setBurnableBlocks();
		CTags.init();
		CEntityTypeTags.init();
		CGameRules.init();
		Map<Block, Block> STRIP_MAP = (new Builder<Block, Block>())
				.put(CBlocks.AUSLDINE_WOOD, CBlocks.STRIPPED_AUSLDINE_WOOD).put(CBlocks.AUSLDINE_LOG, CBlocks.STRIPPED_AUSLDINE_LOG)
				.put(CBlocks.DEAD_OAK_WOOD, CBlocks.STRIPPED_DEAD_OAK_WOOD).put(CBlocks.DEAD_OAK_LOG, CBlocks.STRIPPED_DEAD_OAK_LOG)
				.put(CBlocks.MONORIS_WOOD, CBlocks.STRIPPED_MONORIS_WOOD).put(CBlocks.MONORIS_LOG, CBlocks.STRIPPED_MONORIS_LOG)
				.putAll(AxeItem.BLOCK_STRIPPING_MAP)
				.build();
		AxeItem.BLOCK_STRIPPING_MAP = STRIP_MAP;
	}

	public void clientRegistries(final FMLClientSetupEvent event)
	{
		CItemModelsProperties.register();
		boolean useMain = false;
		EntityRendererHandler.registerEntityRenders();
		KeyBindings.registerKeyBindings();
		CombatTileEntityRender.registerRenders();
		CScreens.registerScreens();
		if (Config.CLIENT.toggle_custom_main_menu.get())
			MainMenuScreen.PANORAMA_RESOURCES = new RenderSkyboxCube(Combat.getInstance().location("textures/gui/title/background/panorama"));
		if (useMain) MainMenuScreen.PANORAMA_OVERLAY_TEXTURES = Combat.getInstance().location("textures/gui/title/background/combat_overlay.png");
		RenderType rendertype = RenderType.getCutoutMipped();
		RenderTypeLookup.setRenderLayer(CBlocks.AUSLDINE_LEAVES, rendertype);
		RenderTypeLookup.setRenderLayer(CBlocks.MONORIS_LEAVES, rendertype);
		RenderType rendertype1 = RenderType.getCutout();
		RenderTypeLookup.setRenderLayer(CBlocks.EMPTY_ROBIN_SUMMONER, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.AUSLDINE_SAPLING, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.MONORIS_SAPLING, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.POTTED_AUSLDINE_SAPLING, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.POTTED_MONORIS_SAPLING, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.PYRANITE_FIRE, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.TORCH_LEVER, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.WALL_TORCH_LEVER, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.CORN, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.WOODCUTTER, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.PYRANITE_TORCH, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.PYRANITE_WALL_TORCH, rendertype1);
		RenderTypeLookup.setRenderLayer(CBlocks.PYRANITE_LANTERN, rendertype1);
		RenderType rendertype2 = RenderType.getTranslucent();
		RenderTypeLookup.setRenderLayer(CBlocks.ACROTLEST_RUINED_PORTAL, rendertype2);
		RenderTypeLookup.setRenderLayer(CBlocks.ACROTLEST_PORTAL, rendertype2);
		RenderTypeLookup.setRenderLayer(CBlocks.GROPAPY, rendertype2);

		RenderType frendertype = RenderType.getTranslucent();
		RenderTypeLookup.setRenderLayer(CFluids.BIABLE, frendertype);
		RenderTypeLookup.setRenderLayer(CFluids.OIL, frendertype);

		DimensionRenderInfo.field_239208_a_.put(CDimensionType.ACROTLEST_ID, new CDImensionRenderInfo.Acrotlest());
		SortedMap<RenderType, BufferBuilder> fixedBuffers = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (p_228485_1_) -> {
			put(p_228485_1_, CRenderType.getArmorGlint());
			put(p_228485_1_, CRenderType.getArmorEntityGlint());
			put(p_228485_1_, CRenderType.getGlint());
			put(p_228485_1_, CRenderType.getGlintDirect());
			put(p_228485_1_, CRenderType.getGlintTranslucent());
			put(p_228485_1_, CRenderType.getEntityGlint());
			put(p_228485_1_, CRenderType.getEntityGlintDirect());
		});
		Minecraft.getInstance().getRenderTypeBuffers().fixedBuffers.putAll(fixedBuffers);
	}

	@OnlyIn(Dist.CLIENT)
	public void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> mapBuildersIn, RenderType renderTypeIn) {
		mapBuildersIn.put(renderTypeIn, new BufferBuilder(renderTypeIn.getBufferSize()));
	}

	public void enqueue(final InterModEnqueueEvent event) {
		if (ModHelper.isCuriosLoaded()) {
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BACK.getMessageBuilder().size(1).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(1).build());
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("book").size(1).icon(EMPTY_SPELLBOOK_SLOT).build());
		}
	} 

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		CCommands.registerAll(event.getDispatcher());
	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
	}

	@SubscribeEvent
	public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
	}

	public static void setBurnableBlocks() {
		FireBlock fire = (FireBlock)Blocks.FIRE;
		fire.setFireInfo(CBlocks.AUSLDINE_PLANKS, 5, 20);
		fire.setFireInfo(CBlocks.DEAD_OAK_PLANKS, 5, 20);
		fire.setFireInfo(CBlocks.AUSLDINE_SLAB, 5, 20);
		fire.setFireInfo(CBlocks.DEAD_OAK_SLAB, 5, 20);
		fire.setFireInfo(CBlocks.AUSLDINE_FENCE_GATE, 5, 20);
		fire.setFireInfo(CBlocks.DEAD_OAK_FENCE_GATE, 5, 20);
		fire.setFireInfo(CBlocks.AUSLDINE_FENCE, 5, 20);
		fire.setFireInfo(CBlocks.DEAD_OAK_FENCE, 5, 20);
		fire.setFireInfo(CBlocks.AUSLDINE_STAIRS, 5, 20);
		fire.setFireInfo(CBlocks.DEAD_OAK_STAIRS, 5, 20);
		fire.setFireInfo(CBlocks.AUSLDINE_LOG, 5, 5);
		fire.setFireInfo(CBlocks.DEAD_OAK_LOG, 5, 5);
		fire.setFireInfo(CBlocks.STRIPPED_AUSLDINE_LOG, 5, 5);
		fire.setFireInfo(CBlocks.STRIPPED_DEAD_OAK_LOG, 5, 5);
		fire.setFireInfo(CBlocks.STRIPPED_AUSLDINE_WOOD, 5, 5);
		fire.setFireInfo(CBlocks.STRIPPED_DEAD_OAK_WOOD, 5, 5);
		fire.setFireInfo(CBlocks.AUSLDINE_WOOD, 5, 5);
		fire.setFireInfo(CBlocks.DEAD_OAK_WOOD, 5, 5);
		fire.setFireInfo(CBlocks.AUSLDINE_LEAVES, 30, 60);
		fire.setFireInfo(CBlocks.OIL, 50, 100);
		PyraniteFireBlock pyranitefire = (PyraniteFireBlock)CBlocks.PYRANITE_FIRE;
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_PLANKS, 5, 20);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_PLANKS, 5, 20);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_SLAB, 5, 20);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_SLAB, 5, 20);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_FENCE_GATE, 5, 20);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_FENCE_GATE, 5, 20);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_FENCE, 5, 20);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_FENCE, 5, 20);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_STAIRS, 5, 20);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_STAIRS, 5, 20);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_LOG, 5, 5);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_LOG, 5, 5);
		pyranitefire.setFireInfo(CBlocks.STRIPPED_AUSLDINE_LOG, 5, 5);
		pyranitefire.setFireInfo(CBlocks.STRIPPED_DEAD_OAK_LOG, 5, 5);
		pyranitefire.setFireInfo(CBlocks.STRIPPED_AUSLDINE_WOOD, 5, 5);
		pyranitefire.setFireInfo(CBlocks.STRIPPED_DEAD_OAK_WOOD, 5, 5);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_WOOD, 5, 5);
		pyranitefire.setFireInfo(CBlocks.DEAD_OAK_WOOD, 5, 5);
		pyranitefire.setFireInfo(CBlocks.AUSLDINE_LEAVES, 30, 60);
		pyranitefire.setFireInfo(CBlocks.OIL, 50, 100);
	}
	
	public static Combat getInstance() {
		return combatInstance;
	}
}
