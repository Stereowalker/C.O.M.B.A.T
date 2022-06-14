package com.stereowalker.combat.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap.Builder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.sounds.CSoundEvents;
import com.stereowalker.combat.world.level.block.CBlocks;
import com.stereowalker.combat.world.level.material.CFluids;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;
import com.stereowalker.combat.world.food.CFoodProperties;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public class CItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	//Block Items
	public static final Item CALTAS = register(CBlocks.CALTAS, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MEZEPINE = register(CBlocks.MEZEPINE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SLYAPHY = register(CBlocks.SLYAPHY, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item COBBLED_SLYAPHY = register(CBlocks.COBBLED_SLYAPHY, CCreativeModeTab.BUILDING_BLOCKS);
	//Ores
	public static final Item CASSITERITE = register(CBlocks.CASSITERITE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PASQUEM_ORE = register(CBlocks.PASQUEM_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item TRIDOX_ORE = register(CBlocks.TRIDOX_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item RUBY_ORE = register(CBlocks.RUBY_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PELGAN_ORE = register(CBlocks.PELGAN_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SLYAPHY_PELGAN_ORE = register(CBlocks.SLYAPHY_PELGAN_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item LOZYNE_ORE = register(CBlocks.LOZYNE_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SLYAPHY_LOZYNE_ORE = register(CBlocks.SLYAPHY_LOZYNE_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PYRANITE_ORE = register(CBlocks.PYRANITE_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SERABLE_ORE = register(CBlocks.SERABLE_ORE, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PASQUEM_BLOCK = register(CBlocks.PASQUEM_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item TRIDOX_BLOCK = register(CBlocks.TRIDOX_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item RUBY_BLOCK = register(CBlocks.RUBY_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PELGAN_BLOCK = register(CBlocks.PELGAN_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item LOZYNE_BLOCK = register(CBlocks.LOZYNE_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item STEEL_BLOCK = register(CBlocks.STEEL_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item BRONZE_BLOCK = register(CBlocks.BRONZE_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item ETHERION_BLOCK = register(CBlocks.ETHERION_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SERABLE_BLOCK = register(CBlocks.SERABLE_BLOCK, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item COPPER_BARS = register(CBlocks.COPPER_BARS, CCreativeModeTab.COMBAT_TAB_MISC);
	public static final Item COPPER_DOOR = register(new DoubleHighBlockItem(CBlocks.COPPER_DOOR, (new Item.Properties()).tab(CreativeModeTab.TAB_REDSTONE)));
	public static final Item COPPER_TRAPDOOR = register(CBlocks.COPPER_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);

	public static final Item AUSLDINE_BUTTON = register(CBlocks.AUSLDINE_BUTTON, CCreativeModeTab.BUILDING_BLOCKS);
	//Decorations
	public static final Item WOODCUTTER = register(CBlocks.WOODCUTTER, CreativeModeTab.TAB_DECORATIONS);
	public static final Item ALLOY_FURNACE = register(CBlocks.ALLOY_FURNACE, CreativeModeTab.TAB_DECORATIONS);

	public static final Item ARCANE_WORKBENCH = register(CBlocks.ARCANE_WORKBENCH, CCreativeModeTab.MAGIC);
	public static final Item DISENCHANTING_TABLE = register(CBlocks.DISENCHANTING_TABLE, CCreativeModeTab.MAGIC);
	public static final Item CARDBOARD_BOX = register(new BlockItem(CBlocks.CARDBOARD_BOX, (new Item.Properties()).stacksTo(1).tab(CCreativeModeTab.TECHNOLOGY)));
	public static final Item MANA_GENERATOR = register(CBlocks.MANA_GENERATOR, CCreativeModeTab.TECHNOLOGY);
	public static final Item CONNECTOR = register(CBlocks.CONNECTOR, CCreativeModeTab.TECHNOLOGY);
	public static final Item ELECTRIC_FURNACE = register(CBlocks.ELECTRIC_FURNACE, CCreativeModeTab.TECHNOLOGY);
	public static final Item MYTHRIL_CHARGER = register(CBlocks.MYTHRIL_CHARGER, CCreativeModeTab.TECHNOLOGY);
	public static final Item GROPAPY = register(CBlocks.GROPAPY, CCreativeModeTab.COMBAT_TAB_MISC);
	public static final Item MEZEPINE_BRICKS = register(CBlocks.MEZEPINE_BRICKS, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MEZEPINE_SLAB = register(CBlocks.MEZEPINE_SLAB, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MEZEPINE_BRICK_SLAB = register(CBlocks.MEZEPINE_BRICK_SLAB, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MEZEPINE_BRICK_STAIRS = register(CBlocks.MEZEPINE_BRICK_STAIRS, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MAGIC_STONE = register(CBlocks.MAGIC_STONE, CCreativeModeTab.MAGIC);
	public static final Item YELLOW_MAGIC_CLUSTER = register(CBlocks.YELLOW_MAGIC_CLUSTER, CCreativeModeTab.MAGIC);
	public static final Item PYRANITE_LANTERN = register(CBlocks.PYRANITE_LANTERN, CreativeModeTab.TAB_DECORATIONS);
	public static final Item HOMSE = register(CBlocks.HOMSE, CCreativeModeTab.BUILDING_BLOCKS);

	public static final Item OAK_PODIUM = register(CBlocks.OAK_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SPRUCE_PODIUM = register(CBlocks.SPRUCE_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item BIRCH_PODIUM = register(CBlocks.BIRCH_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item JUNGLE_PODIUM = register(CBlocks.JUNGLE_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item ACACIA_PODIUM = register(CBlocks.ACACIA_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DARK_OAK_PODIUM = register(CBlocks.DARK_OAK_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item CRIMSON_PODIUM = register(CBlocks.CRIMSON_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item WARPED_PODIUM = register(CBlocks.WARPED_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item AUSLDINE_PODIUM = register(CBlocks.AUSLDINE_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DEAD_OAK_PODIUM = register(CBlocks.DEAD_OAK_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MONORIS_PODIUM = register(CBlocks.MONORIS_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);

	public static final Item FIR_PODIUM = registerBOP(CBlocks.FIR_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item REDWOOD_PODIUM = registerBOP(CBlocks.REDWOOD_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item CHERRY_PODIUM = registerBOP(CBlocks.CHERRY_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MAHOGANY_PODIUM = registerBOP(CBlocks.MAHOGANY_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item JACARANDA_PODIUM = registerBOP(CBlocks.JACARANDA_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PALM_PODIUM = registerBOP(CBlocks.PALM_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item WILLOW_PODIUM = registerBOP(CBlocks.WILLOW_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DEAD_PODIUM = registerBOP(CBlocks.DEAD_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MAGIC_PODIUM = registerBOP(CBlocks.MAGIC_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item UMBRAN_PODIUM = registerBOP(CBlocks.UMBRAN_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item HELLBARK_PODIUM = registerBOP(CBlocks.HELLBARK_PODIUM, CCreativeModeTab.BUILDING_BLOCKS);
	//Minecraft
	public static final Item OAK_BEAM = register(CBlocks.OAK_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item SPRUCE_BEAM = register(CBlocks.SPRUCE_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item BIRCH_BEAM = register(CBlocks.BIRCH_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item JUNGLE_BEAM = register(CBlocks.JUNGLE_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item ACACIA_BEAM = register(CBlocks.ACACIA_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DARK_OAK_BEAM = register(CBlocks.DARK_OAK_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item CRIMSON_BEAM = register(CBlocks.CRIMSON_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item WARPED_BEAM = register(CBlocks.WARPED_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	//Combat
	public static final Item AUSLDINE_BEAM = register(CBlocks.AUSLDINE_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DEAD_OAK_BEAM = register(CBlocks.DEAD_OAK_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MONORIS_BEAM = register(CBlocks.MONORIS_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	//BOP
	public static final Item FIR_BEAM = registerBOP(CBlocks.FIR_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item REDWOOD_BEAM = registerBOP(CBlocks.REDWOOD_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item CHERRY_BEAM = registerBOP(CBlocks.CHERRY_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MAHOGANY_BEAM = registerBOP(CBlocks.MAHOGANY_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item JACARANDA_BEAM = registerBOP(CBlocks.JACARANDA_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item PALM_BEAM = registerBOP(CBlocks.PALM_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item WILLOW_BEAM = registerBOP(CBlocks.WILLOW_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item DEAD_BEAM = registerBOP(CBlocks.DEAD_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item MAGIC_BEAM = registerBOP(CBlocks.MAGIC_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item UMBRAN_BEAM = registerBOP(CBlocks.UMBRAN_BEAM, CCreativeModeTab.BUILDING_BLOCKS);
	public static final Item HELLBARK_BEAM = registerBOP(CBlocks.HELLBARK_BEAM, CCreativeModeTab.BUILDING_BLOCKS);

	//Miscellaneous
	public static final Item VAMPIRE_BLOOD = register("vampire_blood", new VampireBloodItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BLOOD = register("blood", new BloodItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item CORN_SEEDS = register("corn_seeds", new ItemNameBlockItem(CBlocks.CORN, new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item GOLD_ROD = register("gold_rod", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item IRON_ROD = register("iron_rod", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item STEEL_ROD = register("steel_rod", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item SERABLE_ROD = register("serable_rod", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item COPPER_INGOT = register("copper_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item QUIVER = register("quiver", new QuiverItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC).stacksTo(1)));
	public static final Item BACKPACK = register("backpack", new BackpackItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC).stacksTo(1)));
	public static final Item SHEATH = register("sheath", new SheathItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC).stacksTo(1)));
	public static final Item TIN_INGOT = register("tin_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BRONZE_INGOT = register("bronze_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item PASQUEM_INGOT = register("pasquem_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item STEEL_INGOT = register("steel_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item PELGAN_INGOT = register("pelgan_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item RAW_LOZYNE = register("raw_lozyne", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item LOZYNE_INGOT = register("lozyne_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item SERABLE_INGOT = register("serable_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item ETHERION_INGOT = register("etherion_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item MAGISTEEL_INGOT = register("magisteel_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item MYTHRIL_INGOT = register("mythril_ingot", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item COPPER_NUGGET = register("copper_nugget", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BRONZE_NUGGET = register("bronze_nugget", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item SERABLE_NUGGET = register("serable_nugget", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item TIN_NUGGET = register("tin_nugget", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item SLAG = register("slag", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item OIL_BUCKET = register("oil_bucket", new BucketItem(() -> CFluids.OIL, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BIABLE_BUCKET = register("biable_bucket", new BucketItem(() -> CFluids.BIABLE, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item CARDBOARD_PAPER = register("cardboard_paper", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item PEBBLE = register("pebble", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BIOG_PELT = register("biog_pelt", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item VAMPIRE_SPAWN_EGG = register("vampire_spawn_egg", new SpawnEggItem(CEntityType.VAMPIRE, 0x240202, 0xb00000, (new Item.Properties()).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item ZOMBIE_COW_SPAWN_EGG = register("zombie_cow_spawn_egg", new SpawnEggItem(CEntityType.ZOMBIE_COW, 0x6d970f, 0x0f972a, (new Item.Properties()).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BIOG_SPAWN_EGG = register("biog_spawn_egg", new SpawnEggItem(CEntityType.BIOG, 0x2986a8, 0xd9ea1f, (new Item.Properties()).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item RED_BIOG_SPAWN_EGG = register("red_biog_spawn_egg", new SpawnEggItem(CEntityType.RED_BIOG, 0xe5172a, 0x17d3e5, (new Item.Properties()).tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item PYRANITE = register("pyranite", new PyraniteItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item WHITE_ETHERION = register("white_etherion", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BLUE_ETHERION = register("blue_etherion", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item RUBY = register("ruby", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item LIMESTONE_ROCK = register("limestone_rock", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item ETHERION_COMPASS = register("etherion_compass", new EtherionCompassItem(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item BRONZE_HORSE_ARMOR = register("bronze_horse_armor", new HorseArmorItem(6, Combat.getInstance().location("textures/entity/horse/armor/horse_armor_" + "bronze" + ".png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
	public static final Item SERABLE_HORSE_ARMOR = register("serable_horse_armor", new HorseArmorItem(6, Combat.getInstance().location("textures/entity/horse/armor/horse_armor_" + "serable" + ".png"), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
	public static final Item BRONZE_SHEARS = register("bronze_shears", new ShearsItem((new Item.Properties()).durability(238).tab(CreativeModeTab.TAB_TOOLS)));

	//Tech
	public static final Item LIGHT_SABER = register("light_saber", new LightSaberItem(7, 3.0F, new Item.Properties().tab(CCreativeModeTab.TECHNOLOGY).durability(500)));

	//Magic
	public static final Item BASIC_STICK_WAND = register("basic_stick_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.BASIC, WandTiers.STICK));
	public static final Item NOVICE_STICK_WAND = register("novice_stick_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.NOVICE, WandTiers.STICK));
	public static final Item APPRENTICE_STICK_WAND = register("apprentice_stick_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.APPRENTICE, WandTiers.STICK));
	public static final Item ADVANCED_STICK_WAND = register("advanced_stick_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.ADVANCED, WandTiers.STICK));
	public static final Item MASTER_STICK_WAND = register("master_stick_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.MASTER, WandTiers.STICK));

	public static final Item BASIC_BLAZE_WAND = register("basic_blaze_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.BASIC, WandTiers.BLAZE));
	public static final Item NOVICE_BLAZE_WAND = register("novice_blaze_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.NOVICE, WandTiers.BLAZE));
	public static final Item APPRENTICE_BLAZE_WAND = register("apprentice_blaze_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.APPRENTICE, WandTiers.BLAZE));
	public static final Item ADVANCED_BLAZE_WAND = register("advanced_blaze_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.ADVANCED, WandTiers.BLAZE));
	public static final Item MASTER_BLAZE_WAND = register("master_blaze_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.MASTER, WandTiers.BLAZE));

	public static final Item BASIC_GOLDEN_WAND = register("basic_golden_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.BASIC, WandTiers.GOLD));
	public static final Item NOVICE_GOLDEN_WAND = register("novice_golden_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.NOVICE, WandTiers.GOLD));
	public static final Item APPRENTICE_GOLDEN_WAND = register("apprentice_golden_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.APPRENTICE, WandTiers.GOLD));
	public static final Item ADVANCED_GOLDEN_WAND = register("advanced_golden_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.ADVANCED, WandTiers.GOLD));
	public static final Item MASTER_GOLDEN_WAND = register("master_golden_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.MASTER, WandTiers.GOLD));

	public static final Item BASIC_SERABLE_WAND = register("basic_serable_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.BASIC, WandTiers.SERABLE));
	public static final Item NOVICE_SERABLE_WAND = register("novice_serable_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.NOVICE, WandTiers.SERABLE));
	public static final Item APPRENTICE_SERABLE_WAND = register("apprentice_serable_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.APPRENTICE, WandTiers.SERABLE));
	public static final Item ADVANCED_SERABLE_WAND = register("advanced_serable_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.ADVANCED, WandTiers.SERABLE));
	public static final Item MASTER_SERABLE_WAND = register("master_serable_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.MASTER, WandTiers.SERABLE));

	public static final Item BASIC_SHULKER_WAND = register("basic_shulker_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.BASIC, WandTiers.SHULKER));
	public static final Item NOVICE_SHULKER_WAND = register("novice_shulker_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.NOVICE, WandTiers.SHULKER));
	public static final Item APPRENTICE_SHULKER_WAND = register("apprentice_shulker_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.APPRENTICE, WandTiers.SHULKER));
	public static final Item ADVANCED_SHULKER_WAND = register("advanced_shulker_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.ADVANCED, WandTiers.SHULKER));
	public static final Item MASTER_SHULKER_WAND = register("master_shulker_wand", new TieredWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC), Rank.MASTER, WandTiers.SHULKER));

	public static final Item RESEARCH_SCROLL = register("research_scroll",new ResearchScrollItem( new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item SCROLL = register("scroll",new ScrollItem(ClassType.ELEMENTAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
	public static final Item ANCIENT_SCROLL = register("ancient_scroll",new ScrollItem(ClassType.PRIMEVAL, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
	public static final Item GRIMOIRE = register("grimoire", new GrimoireItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item NECRONOMICON = register("necronomicon", new NecronomiconItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item TRIDOX = register("tridox", new Item(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item SOUL_SWORD = register("soul_sword", new SoulSwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_HOE = register("soul_hoe", new SoulHoeItem(Tiers.IRON, 0, -1.0F, new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_PICKAXE = register("soul_pickaxe", new SoulPickaxeItem(Tiers.IRON, 1, -2.8F, new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_SHOVEL = register("soul_shovel", new SoulShovelItem(Tiers.IRON, 1.5F, -3.0F, new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_AXE = register("soul_axe", new SoulAxeItem(Tiers.IRON, 6.0F, -3.1F, new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_BOW = register("soul_bow", new SoulBowItem(new Item.Properties().defaultDurability(60)));
	public static final Item SOUL_HELMET = register("soul_helmet", new SoulArmorItem(CArmorMaterials.SOUL, EquipmentSlot.HEAD, new Item.Properties()));
	public static final Item SOUL_CHESTPLATE = register("soul_chestplate", new SoulArmorItem(CArmorMaterials.SOUL, EquipmentSlot.CHEST, new Item.Properties()));
	public static final Item SOUL_LEGGINGS = register("soul_leggings", new SoulArmorItem(CArmorMaterials.SOUL, EquipmentSlot.LEGS, new Item.Properties()));
	public static final Item SOUL_BOOTS = register("soul_boots", new SoulArmorItem(CArmorMaterials.SOUL, EquipmentSlot.FEET, new Item.Properties()));
	public static final Item MANA_PILL = register("mana_pill", new ManaPillIem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(16)));
	public static final Item EMPTY_SOUL_GEM = register("empty_soul_gem", new EmptySoulGemItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item SOUL_GEM = register("soul_gem", new Item(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item MANA_ORB = register("mana_orb", new ManaOrbItem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item MAGIC_ORB = register("magic_orb", new MagicOrbItem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item PURPLE_MAGIC_STONE = register("purple_magic_stone", new MagicStoneItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item RED_MAGIC_STONE = register("red_magic_stone", new MagicStoneItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item YELLOW_MAGIC_STONE = register("yellow_magic_stone", new MagicStoneItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item PYROMANCER_RING = register("pyromancer_ring", new AffinityRingItem(SpellCategory.FIRE, new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item HYDROMANCER_RING = register("hydromancer_ring", new AffinityRingItem(SpellCategory.WATER, new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item ELECTROMANCER_RING = register("electromancer_ring", new AffinityRingItem(SpellCategory.LIGHTNING, new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item TERRAMANCER_RING = register("terramancer_ring", new AffinityRingItem(SpellCategory.EARTH, new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item AEROMANCER_RING = register("aeromancer_ring", new AffinityRingItem(SpellCategory.WIND, new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item BLOOD_SWORD = register("blood_sword", new BloodSwordItem(CTiers.BLOOD, 3, -2.4F, new Item.Properties().defaultDurability(60)));
	public static final Item BLOOD_AXE = register("blood_axe", new AxeItem(CTiers.BLOOD, 6.0F, -3.2F, new Item.Properties()));
	public static final Item XP_STORAGE_RING = register("xp_storage_ring", new XPRingItem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item POISON_CLEANSING_AMULET = register("poison_cleansing_amulet", new PoisonClensingAmulet(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item RECONFIGURATION_SOUP = register("reconfiguration_soup", new ReconfigurationSoupItem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));
	public static final Item SKILL_RUNESTONE = register("skill_runestone", new SkillRunestoneItem(new Item.Properties().tab(CCreativeModeTab.MAGIC).stacksTo(1)));

	//Legendary Items
	public static final Item DESERT_DRAGON = register("desert_dragon", new DesertDragonItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item ARCH = register("arch", new ArchItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item MOON_BOOTS = register("moon_boots", new MoonBootsItem(EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item MJOLNIR = register("mjolnir", new MjolnirItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item WRATH = register("wrath", new WrathItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item FROSTBITE_SWORD = register("frostbite_sword", new FrostbiteSword(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item MANA_CUTTER = register("mana_cutter", new ManaCutter(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item GOD_WAND = register("god_wand", new GodWandItem(new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	//	public static final Item ANGELS_WINGS = register("angels_wings", new FrostbiteSword(6, 6, new Item.Properties().tab(CItemGroup.LEGENDARY).defaultDurability(1000)));
	//	public static final Item DEATH_VEST = register("death_vest", new FrostbiteSword(6, 6, new Item.Properties().tab(CItemGroup.LEGENDARY).defaultDurability(1000)));
	//Battle
	public static final Item DAGGER_HANDLE = register("dagger_handle", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item SWORD_HANDLE = register("sword_handle", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item KATANA_HANDLE = register("katana_handle", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));
	public static final Item RAPIER_HANDLE = register("rapier_handle", new Item(new Item.Properties().tab(CCreativeModeTab.COMBAT_TAB_MISC)));

	public static final Item SPEAR = register("spear", new SpearItem(new Item.Properties().tab(CCreativeModeTab.BATTLE).defaultDurability(250)));
	public static final Item ARCH_EXPLOSION_GEM = register("arch_explosion_gem", new ArchSourceItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), ArchSourceItem.ArchType.EXPLOSIVE));
	public static final Item ARCH_FLAME_GEM = register("arch_flame_gem", new ArchSourceItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), ArchSourceItem.ArchType.FLAME));
	public static final Item ARCH_FREEZE_GEM = register("arch_freeze_gem", new ArchSourceItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), ArchSourceItem.ArchType.FREEZE));
	public static final Item ARCH_TELEPORT_GEM = register("arch_teleport_gem", new ArchSourceItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), ArchSourceItem.ArchType.TELEPORT));

	public static final Item BRONZE_SWORD = register("bronze_sword", new SwordItem(CTiers.BRONZE, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_SWORD = register("steel_sword", new SwordItem(CTiers.STEEL, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item MAGISTEEL_SWORD = register("magisteel_sword", new MagisteelSwordItem(CTiers.MAGISTEEL, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item PASQUEM_SWORD = register("pasquem_sword", new SwordItem(CTiers.PASQUEM, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_SWORD = register("pelgan_sword", new SwordItem(CTiers.PELGAN, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_SWORD = register("serable_sword", new SwordItem(CTiers.SERABLE, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_SWORD = register("lozyne_sword", new SwordItem(CTiers.LOZYNE, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item MYTHRIL_SWORD = register("mythril_sword", new MythrilSwordItem(CTiers.MYTHRIL, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));
	public static final Item ETHERION_SWORD = register("etherion_sword", new SwordItem(CTiers.ETHERION, 3, -2.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));

	public static final Item WOODEN_DAGGER = register("wooden_dagger", new DaggerItem(Tiers.WOOD, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_DAGGER = register("stone_dagger", new DaggerItem(Tiers.STONE, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_DAGGER = register("iron_dagger", new DaggerItem(Tiers.IRON, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_DAGGER = register("golden_dagger", new DaggerItem(Tiers.GOLD, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_DAGGER = register("diamond_dagger", new DaggerItem(Tiers.DIAMOND, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item NETHERITE_DAGGER = register("netherite_dagger", new DaggerItem(Tiers.NETHERITE, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));
	public static final Item BRONZE_DAGGER = register("bronze_dagger", new DaggerItem(CTiers.BRONZE, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_DAGGER = register("steel_dagger", new DaggerItem(CTiers.STEEL, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_DAGGER = register("pasquem_dagger", new DaggerItem(CTiers.PASQUEM, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_DAGGER = register("pelgan_dagger", new DaggerItem(CTiers.PELGAN, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_DAGGER = register("serable_dagger", new DaggerItem(CTiers.SERABLE, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_DAGGER = register("lozyne_dagger", new DaggerItem(CTiers.LOZYNE, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_DAGGER = register("etherion_dagger", new DaggerItem(CTiers.ETHERION, 1, -1.2F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));

	public static final Item WOODEN_HAMMER = register("wooden_hammer", new HammerItem(Tiers.WOOD, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_HAMMER = register("stone_hammer", new HammerItem(Tiers.STONE, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_HAMMER = register("iron_hammer", new HammerItem(Tiers.IRON, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_HAMMER = register("golden_hammer", new HammerItem(Tiers.GOLD, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_HAMMER = register("diamond_hammer", new HammerItem(Tiers.DIAMOND, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_HAMMER = register("bronze_hammer", new HammerItem(CTiers.BRONZE, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_HAMMER = register("steel_hammer", new HammerItem(CTiers.STEEL, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_HAMMER = register("pasquem_hammer", new HammerItem(CTiers.PASQUEM, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_HAMMER = register("pelgan_hammer", new HammerItem(CTiers.PELGAN, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_HAMMER = register("serable_hammer", new HammerItem(CTiers.SERABLE, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_HAMMER = register("lozyne_hammer", new HammerItem(CTiers.LOZYNE, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_HAMMER = register("etherion_hammer", new HammerItem(CTiers.ETHERION, 4, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));

	public static final Item WOODEN_CHAKRAM = register("wooden_chakram", new ChakramItem(Tiers.WOOD, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_CHAKRAM = register("stone_chakram", new ChakramItem(Tiers.STONE, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_CHAKRAM = register("iron_chakram", new ChakramItem(Tiers.IRON, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_CHAKRAM = register("golden_chakram", new ChakramItem(Tiers.GOLD, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_CHAKRAM = register("diamond_chakram", new ChakramItem(Tiers.DIAMOND, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_CHAKRAM = register("bronze_chakram", new ChakramItem(CTiers.BRONZE, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_CHAKRAM = register("steel_chakram", new ChakramItem(CTiers.STEEL, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_CHAKRAM = register("pasquem_chakram", new ChakramItem(CTiers.PASQUEM, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_CHAKRAM = register("pelgan_chakram", new ChakramItem(CTiers.PELGAN, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_CHAKRAM = register("serable_chakram", new ChakramItem(CTiers.SERABLE, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_CHAKRAM = register("lozyne_chakram", new ChakramItem(CTiers.LOZYNE, 3, -2.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));

	public static final Item WOODEN_KATANA = register("wooden_katana", new KatanaItem(Tiers.WOOD, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_KATANA = register("stone_katana", new KatanaItem(Tiers.STONE, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_KATANA = register("iron_katana", new KatanaItem(Tiers.IRON, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_KATANA = register("golden_katana", new KatanaItem(Tiers.GOLD, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_KATANA = register("diamond_katana", new KatanaItem(Tiers.DIAMOND, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item NETHERITE_KATANA = register("netherite_katana", new KatanaItem(Tiers.NETHERITE, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));
	public static final Item BRONZE_KATANA = register("bronze_katana", new KatanaItem(CTiers.BRONZE, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_KATANA = register("steel_katana", new KatanaItem(CTiers.STEEL, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_KATANA = register("pasquem_katana", new KatanaItem(CTiers.PASQUEM, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_KATANA = register("pelgan_katana", new KatanaItem(CTiers.PELGAN, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_KATANA = register("serable_katana", new KatanaItem(CTiers.SERABLE, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_KATANA = register("lozyne_katana", new KatanaItem(CTiers.LOZYNE, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_KATANA = register("etherion_katana", new KatanaItem(CTiers.ETHERION, 3, -1.8F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));

	public static final Item WOODEN_RAPIER = register("wooden_rapier", new RapierItem(Tiers.WOOD, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_RAPIER = register("stone_rapier", new RapierItem(Tiers.STONE, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_RAPIER = register("iron_rapier", new RapierItem(Tiers.IRON, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_RAPIER = register("golden_rapier", new RapierItem(Tiers.GOLD, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_RAPIER = register("diamond_rapier", new RapierItem(Tiers.DIAMOND, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item NETHERITE_RAPIER = register("netherite_rapier", new RapierItem(Tiers.NETHERITE, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));
	public static final Item BRONZE_RAPIER = register("bronze_rapier", new RapierItem(CTiers.BRONZE, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_RAPIER = register("steel_rapier", new RapierItem(CTiers.STEEL, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_RAPIER = register("pasquem_rapier", new RapierItem(CTiers.PASQUEM, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_RAPIER = register("pelgan_rapier", new RapierItem(CTiers.PELGAN, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_RAPIER = register("serable_rapier", new RapierItem(CTiers.SERABLE, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_RAPIER = register("lozyne_rapier", new RapierItem(CTiers.LOZYNE, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_RAPIER = register("etherion_rapier", new RapierItem(CTiers.ETHERION, 2, -1.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));

	public static final Item WOODEN_HALBERD = register("wooden_halberd", new HalberdItem(Tiers.WOOD, 12.0F, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_HALBERD = register("stone_halberd", new HalberdItem(Tiers.STONE, 14.0F, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_HALBERD = register("iron_halberd", new HalberdItem(Tiers.IRON, 12.0F, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_HALBERD = register("golden_halberd", new HalberdItem(Tiers.GOLD, 12.0F, -3.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_HALBERD = register("diamond_halberd", new HalberdItem(Tiers.DIAMOND, 10.0F, -3.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_HALBERD = register("bronze_halberd", new HalberdItem(CTiers.BRONZE, 12.0F, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_HALBERD = register("steel_halberd", new HalberdItem(CTiers.STEEL, 10.0F, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_HALBERD = register("pasquem_halberd", new HalberdItem(CTiers.PASQUEM, 14.0F, -3.6F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_HALBERD = register("pelgan_halberd", new HalberdItem(CTiers.PELGAN, 12.0F, -3.5F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_HALBERD = register("serable_halberd", new HalberdItem(CTiers.SERABLE, 12.0F, -3.4F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_HALBERD = register("lozyne_halberd", new HalberdItem(CTiers.LOZYNE, 14.0F, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));

	public static final Item WOODEN_SCYTHE = register("wooden_scythe", new ScytheItem(Tiers.WOOD, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STONE_SCYTHE = register("stone_scythe", new ScytheItem(Tiers.STONE, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item IRON_SCYTHE = register("iron_scythe", new ScytheItem(Tiers.IRON, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item GOLDEN_SCYTHE = register("golden_scythe", new ScytheItem(Tiers.GOLD, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item DIAMOND_SCYTHE = register("diamond_scythe", new ScytheItem(Tiers.DIAMOND, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item NETHERITE_SCYTHE = register("netherite_scythe", new ScytheItem(Tiers.NETHERITE, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE).fireResistant()));
	public static final Item BRONZE_SCYTHE = register("bronze_scythe", new ScytheItem(CTiers.BRONZE, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_SCYTHE = register("steel_scythe", new ScytheItem(CTiers.STEEL, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_SCYTHE = register("pasquem_scythe", new ScytheItem(CTiers.PASQUEM, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_SCYTHE = register("pelgan_scythe", new ScytheItem(CTiers.PELGAN, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_SCYTHE = register("serable_scythe", new ScytheItem(CTiers.SERABLE, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_SCYTHE = register("lozyne_scythe", new ScytheItem(CTiers.LOZYNE, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_SCYTHE = register("etherion_scythe", new ScytheItem(CTiers.ETHERION, 12, -3.7F, new Item.Properties().tab(CCreativeModeTab.BATTLE)));

	public static final Item BRONZE_HELMET = register("bronze_helmet", new ArmorItem(CArmorMaterials.BRONZE, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_CHESTPLATE = register("bronze_chestplate", new ArmorItem(CArmorMaterials.BRONZE, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_LEGGINGS = register("bronze_leggings", new ArmorItem(CArmorMaterials.BRONZE, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item BRONZE_BOOTS = register("bronze_boots", new ArmorItem(CArmorMaterials.BRONZE, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_HELMET = register("pasquem_helmet", new ArmorItem(CArmorMaterials.PASQUEM, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_CHESTPLATE = register("pasquem_chestplate", new ArmorItem(CArmorMaterials.PASQUEM, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_LEGGINGS = register("pasquem_leggings", new ArmorItem(CArmorMaterials.PASQUEM, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PASQUEM_BOOTS = register("pasquem_boots", new ArmorItem(CArmorMaterials.PASQUEM, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_HELMET = register("steel_helmet", new ArmorItem(CArmorMaterials.STEEL, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_CHESTPLATE = register("steel_chestplate", new ArmorItem(CArmorMaterials.STEEL, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_LEGGINGS = register("steel_leggings", new ArmorItem(CArmorMaterials.STEEL, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item STEEL_BOOTS = register("steel_boots", new ArmorItem(CArmorMaterials.STEEL, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_HELMET = register("pelgan_helmet", new ArmorItem(CArmorMaterials.PELGAN, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_CHESTPLATE = register("pelgan_chestplate", new ArmorItem(CArmorMaterials.PELGAN, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_LEGGINGS = register("pelgan_leggings", new ArmorItem(CArmorMaterials.PELGAN, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item PELGAN_BOOTS = register("pelgan_boots", new ArmorItem(CArmorMaterials.PELGAN, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_HELMET = register("serable_helmet", new ArmorItem(CArmorMaterials.SERABLE, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_CHESTPLATE = register("serable_chestplate", new ArmorItem(CArmorMaterials.SERABLE, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_LEGGINGS = register("serable_leggings", new ArmorItem(CArmorMaterials.SERABLE, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item SERABLE_BOOTS = register("serable_boots", new ArmorItem(CArmorMaterials.SERABLE, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_HELMET = register("lozyne_helmet", new ArmorItem(CArmorMaterials.LOZYNE, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_CHESTPLATE = register("lozyne_chestplate", new ArmorItem(CArmorMaterials.LOZYNE, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_LEGGINGS = register("lozyne_leggings", new ArmorItem(CArmorMaterials.LOZYNE, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item LOZYNE_BOOTS = register("lozyne_boots", new ArmorItem(CArmorMaterials.LOZYNE, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_HELMET = register("etherion_helmet", new ArmorItem(CArmorMaterials.ETHERION, EquipmentSlot.HEAD, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_CHESTPLATE = register("etherion_chestplate", new ArmorItem(CArmorMaterials.ETHERION, EquipmentSlot.CHEST, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_LEGGINGS = register("etherion_leggings", new ArmorItem(CArmorMaterials.ETHERION, EquipmentSlot.LEGS, new Item.Properties().tab(CCreativeModeTab.BATTLE)));
	public static final Item ETHERION_BOOTS = register("etherion_boots", new ArmorItem(CArmorMaterials.ETHERION, EquipmentSlot.FEET, new Item.Properties().tab(CCreativeModeTab.BATTLE)));

	public static final Item LONGBOW = register("longbow", new LongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE).durability(282)));
	public static final Item ROUND_SHIELD = register("round_shield", new ShieldCItem((new Item.Properties()).durability(336).tab(CCreativeModeTab.BATTLE)));
	public static final Item SHURIKEN = register("shuriken", new ShurikenItem(new Item.Properties().tab(CCreativeModeTab.BATTLE)));


	public static final Item IRON_BOW = register("iron_bow", new ReinforcedBowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.IRON));
	public static final Item GOLDEN_BOW = register("golden_bow", new ReinforcedBowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.GOLD));
	public static final Item BRONZE_BOW = register("bronze_bow", new ReinforcedBowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.BRONZE));
	public static final Item STEEL_BOW = register("steel_bow", new ReinforcedBowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.STEEL));
	public static final Item LOZYNE_BOW = register("lozyne_bow", new ReinforcedBowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.LOZYNE));

	public static final Item IRON_CROSSBOW = register("iron_crossbow", new ReinforcedCrossbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.IRON));
	public static final Item GOLDEN_CROSSBOW = register("golden_crossbow", new ReinforcedCrossbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.GOLD));
	public static final Item BRONZE_CROSSBOW = register("bronze_crossbow", new ReinforcedCrossbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.BRONZE));
	public static final Item STEEL_CROSSBOW = register("steel_crossbow", new ReinforcedCrossbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.STEEL));
	public static final Item LOZYNE_CROSSBOW = register("lozyne_crossbow", new ReinforcedCrossbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.LOZYNE));

	public static final Item IRON_LONGBOW = register("iron_longbow", new ReinforcedLongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.IRON));
	public static final Item GOLDEN_LONGBOW = register("golden_longbow", new ReinforcedLongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), Tiers.GOLD));
	public static final Item BRONZE_LONGBOW = register("bronze_longbow", new ReinforcedLongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.BRONZE));
	public static final Item STEEL_LONGBOW = register("steel_longbow", new ReinforcedLongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.STEEL));
	public static final Item LOZYNE_LONGBOW = register("lozyne_longbow", new ReinforcedLongbowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), CTiers.LOZYNE));

	public static final Item IRON_TOWER_SHIELD = register("iron_tower_shield", new ReinforcedTowerShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), Tiers.IRON));
	public static final Item IRON_ROUND_SHIELD = register("iron_round_shield", new ReinforcedRoundShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), Tiers.IRON));
	public static final Item GOLDEN_TOWER_SHIELD = register("golden_tower_shield", new ReinforcedTowerShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), Tiers.GOLD));
	public static final Item GOLDEN_ROUND_SHIELD = register("golden_round_shield", new ReinforcedRoundShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), Tiers.GOLD));
	public static final Item BRONZE_TOWER_SHIELD = register("bronze_tower_shield", new ReinforcedTowerShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.BRONZE));
	public static final Item BRONZE_ROUND_SHIELD = register("bronze_round_shield", new ReinforcedRoundShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.BRONZE));
	public static final Item STEEL_TOWER_SHIELD = register("steel_tower_shield", new ReinforcedTowerShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.STEEL));
	public static final Item STEEL_ROUND_SHIELD = register("steel_round_shield", new ReinforcedRoundShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.STEEL));
	public static final Item LOZYNE_TOWER_SHIELD = register("lozyne_tower_shield", new ReinforcedTowerShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.LOZYNE));
	public static final Item LOZYNE_ROUND_SHIELD = register("lozyne_round_shield", new ReinforcedRoundShieldItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), CTiers.LOZYNE));

	public static final Item WOODEN_ARROW = register("wooden_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.WOODEN)); 
	public static final Item GOLDEN_ARROW = register("golden_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.GOLDEN));
	public static final Item QUARTZ_ARROW = register("quartz_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.QUARTZ));
	public static final Item IRON_ARROW = register("iron_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.IRON));
	public static final Item DIAMOND_ARROW = register("diamond_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.DIAMOND));
	public static final Item OBSIDIAN_ARROW = register("obsidian_arrow", new TieredArrowItem(new Item.Properties().tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.OBSIDIAN));

	public static final Item WOODEN_TIPPED_ARROW = register("wooden_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.WOODEN));
	public static final Item GOLDEN_TIPPED_ARROW = register("golden_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.GOLDEN));
	public static final Item QUARTZ_TIPPED_ARROW = register("quartz_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.QUARTZ));
	public static final Item IRON_TIPPED_ARROW = register("iron_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.IRON));
	public static final Item DIAMOND_TIPPED_ARROW = register("diamond_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.DIAMOND));
	public static final Item OBSIDIAN_TIPPED_ARROW = register("obsidian_tipped_arrow", new TieredTippedArrowItem((new Item.Properties()).tab(CCreativeModeTab.BATTLE), TieredArrowItem.ArrowType.OBSIDIAN));

	//Tools
	public static final Item BRONZE_AXE = register("bronze_axe", new AxeItem(CTiers.BRONZE, 6.0F, -3.2F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item STEEL_AXE = register("steel_axe", new AxeItem(CTiers.STEEL, 5.0F, -3.2F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item MAGISTEEL_AXE = register("magisteel_axe", new MagisteelAxeItem(CTiers.MAGISTEEL, 5.0F, -3.2F, new Item.Properties().tab(CCreativeModeTab.MAGIC)));
	public static final Item PASQUEM_AXE = register("pasquem_axe", new AxeItem(CTiers.PASQUEM, 7.0F, -3.1F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PELGAN_AXE = register("pelgan_axe", new AxeItem(CTiers.PELGAN, 6.0F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item SERABLE_AXE = register("serable_axe", new AxeItem(CTiers.SERABLE, 5.0F, -2.9F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item LOZYNE_AXE = register("lozyne_axe", new AxeItem(CTiers.LOZYNE, 7.0F, -3.2F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item MYTHRIL_AXE = register("mythril_axe", new MythrilAxeItem(CTiers.MYTHRIL, 7.0F, -3.1F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));
	public static final Item ETHERION_AXE = register("etherion_axe", new AxeItem(CTiers.ETHERION, 6.0F, -3.1F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));

	public static final Item BRONZE_HOE = register("bronze_hoe", new HoeItem(CTiers.BRONZE, -2, -2.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item STEEL_HOE = register("steel_hoe", new HoeItem(CTiers.STEEL, -2, -1.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PASQUEM_HOE = register("pasquem_hoe", new HoeItem(CTiers.PASQUEM, -3, 0.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PELGAN_HOE = register("pelgan_hoe", new HoeItem(CTiers.PELGAN, -1, -1.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item SERABLE_HOE = register("serable_hoe", new HoeItem(CTiers.SERABLE, 0, -1.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item LOZYNE_HOE = register("lozyne_hoe", new HoeItem(CTiers.LOZYNE, -4, 0.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item MYTHRIL_HOE = register("mythril_hoe", new MythrilHoeItem(CTiers.MYTHRIL, -7, 0.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));
	public static final Item ETHERION_HOE = register("etherion_hoe", new HoeItem(CTiers.ETHERION, -6, 0.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));

	public static final Item BRONZE_PICKAXE = register("bronze_pickaxe", new PickaxeItem(CTiers.BRONZE, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item STEEL_PICKAXE = register("steel_pickaxe", new PickaxeItem(CTiers.STEEL, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PASQUEM_PICKAXE = register("pasquem_pickaxe", new PickaxeItem(CTiers.PASQUEM, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PELGAN_PICKAXE = register("pelgan_pickaxe", new PickaxeItem(CTiers.PELGAN, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item SERABLE_PICKAXE = register("serable_pickaxe", new PickaxeItem(CTiers.SERABLE, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item LOZYNE_PICKAXE = register("lozyne_pickaxe", new PickaxeItem(CTiers.LOZYNE, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item MYTHRIL_PICKAXE = register("mythril_pickaxe", new MythrilPickaxeItem(CTiers.MYTHRIL, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));
	public static final Item ETHERION_PICKAXE = register("etherion_pickaxe", new PickaxeItem(CTiers.ETHERION, 1, -2.8F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));

	public static final Item BRONZE_SHOVEL = register("bronze_shovel", new ShovelItem(CTiers.BRONZE, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item STEEL_SHOVEL = register("steel_shovel", new ShovelItem(CTiers.STEEL, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PASQUEM_SHOVEL = register("pasquem_shovel", new ShovelItem(CTiers.PASQUEM, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item PELGAN_SHOVEL = register("pelgan_shovel", new ShovelItem(CTiers.PELGAN, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item SERABLE_SHOVEL = register("serable_shovel", new ShovelItem(CTiers.SERABLE, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item LOZYNE_SHOVEL = register("lozyne_shovel", new ShovelItem(CTiers.LOZYNE, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS)));
	public static final Item ETHERION_SHOVEL = register("etherion_shovel", new ShovelItem(CTiers.ETHERION, 1.5F, -3.0F, new Item.Properties().tab(CCreativeModeTab.TAB_TOOLS).fireResistant()));


	public static final Item COPPER_WIRE = register("copper_wire", new WireItem(new Item.Properties().tab(CCreativeModeTab.TECHNOLOGY)));
	//Food
	public static final Item CORN = register("corn", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(CFoodProperties.CORN)));
	public static final Item ROAST_CORN = register("roast_corn", new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(CFoodProperties.ROAST_CORN)));
	//Redstone
	public static final Item TORCH_LEVER = register("torch_lever", new StandingAndWallBlockItem(CBlocks.TORCH_LEVER, CBlocks.WALL_TORCH_LEVER, (new Item.Properties()).tab(CreativeModeTab.TAB_REDSTONE)));
	//Transportation
	public static final Item AUSLDINE_BOAT = register("ausldine_boat", new BoatCItem(BoatMod.Type.AUSLDINE, new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION)));
	public static final Item DEAD_OAK_BOAT = register("dead_oak_boat", new BoatCItem(BoatMod.Type.DEAD_OAK, new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION)));
	public static final Item MONORIS_BOAT = register("monoris_boat", new BoatCItem(BoatMod.Type.MONORIS, new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION)));
	public static final Item REZAL_BOAT = register("rezal_boat", new BoatCItem(BoatMod.Type.MONORIS, new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION)));
	//Decorations
	public static final Item PYRANITE_TORCH = register(new StandingAndWallBlockItem(CBlocks.PYRANITE_TORCH, CBlocks.PYRANITE_WALL_TORCH, (new Item.Properties()).tab(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item AUSLDINE_SIGN = register("ausldine_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), CBlocks.AUSLDINE_SIGN, CBlocks.AUSLDINE_WALL_SIGN));
	public static final Item DEAD_OAK_SIGN = register("dead_oak_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), CBlocks.DEAD_OAK_SIGN, CBlocks.DEAD_OAK_WALL_SIGN));
	//Guns
	public static final Item PISTOL_MAGAZINE = registerGun("pistol_magazine", new Item(new Item.Properties().stacksTo(16).tab(CCreativeModeTab.BATTLE)));
	public static final Item PISTOL_EMPTY_MAGAZINE = registerGun("empty_pistol_magazine", new Item(new Item.Properties().stacksTo(16).tab(CCreativeModeTab.BATTLE)));

	public static final Item GLOCK_17 = registerGun("glock_17", new PistolItem(17, 1181.1F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));
	public static final Item SIG_SAUER_P320 = registerGun("sig_sauer_p320", new PistolItem(17, 1181.1F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));
	public static final Item BERETTA_92 = registerGun("beretta_92", new PistolItem(15, 1279.53F, CSoundEvents.BERETTA_92_FIRE, CSoundEvents.BERETTA_92_RELOAD));
	public static final Item WALTHER_P99 = registerGun("walther_p99", new PistolItem(10, 1000.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));
	public static final Item SIG_SAUER_P226 = registerGun("sig_sauer_p226", new PistolItem(10, 1000.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));
	public static final Item CZ_75 = registerGun("cz_75", new PistolItem(10, 1000.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));
	public static final Item BERETTA_PX4_STORM = registerGun("beretta_px4_storm", new PistolItem(10, 1000.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_RELOAD));

	public static final Item AK_103 = registerGun("ak_103", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item STEYR_AUG = registerGun("steyr_aug", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item FN_SCAR = registerGun("fn_scar", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item SIG_SG550 = registerGun("sig_sg550", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item GALIL = registerGun("galil", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item M16 = registerGun("m16", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item HK_416 = registerGun("hk_416", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item HK_G3 = registerGun("hk_g3", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));
	public static final Item HK_G36 = registerGun("hk_g36", new AssaultRifleItem(30, 7, 1500.0F, CSoundEvents.GLOCK_17_FIRE, CSoundEvents.GLOCK_17_FIRE));

	public static final Map<Item, Item> ARROW_MAP = (new Builder<Item, Item>())
			.put(Items.ARROW, Items.TIPPED_ARROW)
			.put(CItems.DIAMOND_ARROW, CItems.DIAMOND_TIPPED_ARROW)
			.put(CItems.OBSIDIAN_ARROW, CItems.OBSIDIAN_TIPPED_ARROW)
			.put(CItems.IRON_ARROW, CItems.IRON_TIPPED_ARROW)
			.put(CItems.QUARTZ_ARROW, CItems.QUARTZ_TIPPED_ARROW)
			.put(CItems.WOODEN_ARROW, CItems.WOODEN_TIPPED_ARROW)
			.put(CItems.GOLDEN_ARROW, CItems.GOLDEN_TIPPED_ARROW)
			.build();

	//	private static Item register(Block block) {
	//		return register(new BlockItem(block, new Item.Properties()));
	//	}

	private static Item register(Block block, CreativeModeTab itemGroup) {
		return register(new BlockItem(block, (new Item.Properties()).tab(itemGroup)));
	}

	private static Item registerBOP(Block block, CreativeModeTab itemGroup) {
		return register(block, Combat.isBOPLoaded(false) ? itemGroup : null)/* : new BlockItem(block, (new Item.Properties()).tab(itemGroup))*/;
	}

	private static Item register(BlockItem p_221543_0_) {
		return register(p_221543_0_.getBlock(), p_221543_0_);
	}

	protected static Item register(Block block, Item p_221546_1_) {
		return register(block.getRegistryName().getPath(), p_221546_1_);
	}

	private static Item registerGun(String name, Item item) {
		return Config.COMMON.load_guns.get() ? register(name, item) : item; 
	}

	private static Item register(String name, Item item) {
		item.setRegistryName(Combat.getInstance().location(name));
		CItems.ITEMS.add(item);
		return item;
	}

	public static void registerAll(IForgeRegistry<Item> registry) {
		int i = 0;
		for(Item item : ITEMS) {
			registry.register(item);
			Combat.debug("Item: \""+item.getRegistryName().toString()+"\" registered");
		}
		for(Block block : CBlocks.BLOCKITEMS) {
			Item item = new BlockItem(block, new Item.Properties().tab(CBlocks.CREATIVE_MODE_TABS.get(i))).setRegistryName(block.getRegistryName());
			i++;
			registry.register(item);
			Combat.debug("Block Item: \""+block.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Items Registered");
	}

	//	@Override
	//	public boolean isBeaconPayment(ItemStack stack) {
	//		return this == CItems.COPPER_INGOT;
	//	}
}
