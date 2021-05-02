package com.stereowalker.combat.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.CBlocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class CTileEntityType extends net.minecraftforge.registries.ForgeRegistryEntry<TileEntityType<?>> {
	private static final List<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<TileEntityType<?>>();

	public static final TileEntityType<CardboardBoxTileEntity> CARDBOARD_BOX = register("cardboard_box", TileEntityType.Builder.create(CardboardBoxTileEntity::new, CBlocks.CARDBOARD_BOX));
	public static final TileEntityType<AlloyFurnaceTileEntity> ALLOY_FURNACE = register("alloy_furnace", TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, CBlocks.ALLOY_FURNACE));
	public static final TileEntityType<CSignTileEntity> SIGN = register("sign", TileEntityType.Builder.create(CSignTileEntity::new, CBlocks.AUSLDINE_SIGN, CBlocks.DEAD_OAK_SIGN, CBlocks.AUSLDINE_WALL_SIGN, CBlocks.DEAD_OAK_WALL_SIGN));
	public static final TileEntityType<MezepineFurnaceTileEntity> MEZEPINE_FURNACE = register("acrotlest_furnace", TileEntityType.Builder.create(MezepineFurnaceTileEntity::new, CBlocks.MEZEPINE_FURNACE));
	public static final TileEntityType<ManaGeneratorTileEntity> MANA_GENERATOR = register("mana_generator", TileEntityType.Builder.create(ManaGeneratorTileEntity::new, CBlocks.MANA_GENERATOR));
	public static final TileEntityType<ElectricFurnaceTileEntity> ELECTRIC_FURNACE = register("electric_furnace", TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, CBlocks.ELECTRIC_FURNACE));
	public static final TileEntityType<ConnectorTileEntity> CONNECTOR = register("connector", TileEntityType.Builder.create(ConnectorTileEntity::new, CBlocks.CONNECTOR));
	public static final TileEntityType<DisenchantingTableTileEntity> DISENCHANTING_TABLE = register("disenchanting_table", TileEntityType.Builder.create(DisenchantingTableTileEntity::new, CBlocks.DISENCHANTING_TABLE));
	public static final TileEntityType<PodiumTileEntity> PODIUM = register("podium", TileEntityType.Builder.create(PodiumTileEntity::new, 
			CBlocks.OAK_PODIUM, 
			CBlocks.SPRUCE_PODIUM, 
			CBlocks.JUNGLE_PODIUM, 
			CBlocks.BIRCH_PODIUM, 
			CBlocks.DARK_OAK_PODIUM, 
			CBlocks.CRIMSON_PODIUM, 
			CBlocks.WARPED_PODIUM, 
			CBlocks.ACACIA_PODIUM, 

			CBlocks.DEAD_OAK_PODIUM, 
			CBlocks.AUSLDINE_PODIUM, 
			CBlocks.MONORIS_PODIUM,

			CBlocks.FIR_PODIUM, 
			CBlocks.REDWOOD_PODIUM, 
			CBlocks.CHERRY_PODIUM, 
			CBlocks.MAHOGANY_PODIUM, 
			CBlocks.JACARANDA_PODIUM, 
			CBlocks.PALM_PODIUM, 
			CBlocks.WILLOW_PODIUM, 
			CBlocks.DEAD_PODIUM, 
			CBlocks.MAGIC_PODIUM, 
			CBlocks.UMBRAN_PODIUM, 
			CBlocks.HELLBARK_PODIUM));
	public static final TileEntityType<BatteryChargerTileEntity> BATTERY_CHARGER = register("battery_charger", TileEntityType.Builder.create(BatteryChargerTileEntity::new, CBlocks.BATTERY_CHARGER));
			

	public static void registerAll(IForgeRegistry<TileEntityType<?>> registry) {
		for(TileEntityType<?> entitytype: TILE_ENTITY_TYPES) {
			registry.register(entitytype);
			Combat.debug("Block Entity: \""+entitytype.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Block Entities Registered");
	}

	private static <T extends TileEntity> TileEntityType<T> register(String name, TileEntityType.Builder<T> builder){
		TileEntityType<T> type = builder.build(null);
		type.setRegistryName(Combat.getInstance().location(name));
		TILE_ENTITY_TYPES.add(type);
		return type;
	}
}
