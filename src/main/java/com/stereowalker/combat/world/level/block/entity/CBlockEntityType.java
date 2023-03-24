package com.stereowalker.combat.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class CBlockEntityType extends net.minecraftforge.registries.ForgeRegistryEntry<BlockEntityType<?>> {
	private static final List<BlockEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<BlockEntityType<?>>();

	public static final BlockEntityType<CardboardBoxBlockEntity> CARDBOARD_BOX = register("cardboard_box", BlockEntityType.Builder.of(CardboardBoxBlockEntity::new, CBlocks.CARDBOARD_BOX));
	public static final BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE = register("alloy_furnace", BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, CBlocks.ALLOY_FURNACE));
	public static final BlockEntityType<CSignBlockEntity> SIGN = register("sign", BlockEntityType.Builder.of(CSignBlockEntity::new, CBlocks.AUSLDINE_SIGN, CBlocks.DEAD_OAK_SIGN, CBlocks.AUSLDINE_WALL_SIGN, CBlocks.DEAD_OAK_WALL_SIGN));
	public static final BlockEntityType<MezepineFurnaceBlockEntity> MEZEPINE_FURNACE = register("acrotlest_furnace", BlockEntityType.Builder.of(MezepineFurnaceBlockEntity::new, CBlocks.MEZEPINE_FURNACE));
	public static final BlockEntityType<ManaGeneratorBlockEntity> MANA_GENERATOR = register("mana_generator", BlockEntityType.Builder.of(ManaGeneratorBlockEntity::new, CBlocks.MANA_GENERATOR));
	public static final BlockEntityType<ElectricFurnaceBlockEntity> ELECTRIC_FURNACE = register("electric_furnace", BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, CBlocks.ELECTRIC_FURNACE));
	public static final BlockEntityType<ConnectorBlockEntity> CONNECTOR = register("connector", BlockEntityType.Builder.of(ConnectorBlockEntity::new, CBlocks.CONNECTOR));
	public static final BlockEntityType<DisenchantmentTableBlockEntity> DISENCHANTING_TABLE = register("disenchanting_table", BlockEntityType.Builder.of(DisenchantmentTableBlockEntity::new, CBlocks.DISENCHANTING_TABLE));
	public static final BlockEntityType<PodiumBlockEntity> PODIUM = register("podium", BlockEntityType.Builder.of(PodiumBlockEntity::new, 
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
	public static final BlockEntityType<MythrilChargerBlockEntity> MYTHRIL_CHARGER = register("mythril_charger", BlockEntityType.Builder.of(MythrilChargerBlockEntity::new, CBlocks.MYTHRIL_CHARGER));
			

	public static void registerAll(IForgeRegistry<BlockEntityType<?>> registry) {
		for(BlockEntityType<?> entitytype: TILE_ENTITY_TYPES) {
			registry.register(entitytype);
			Combat.debug("Block Entity: \""+entitytype.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Block Entities Registered");
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder){
		BlockEntityType<T> type = builder.build(null);
		type.setRegistryName(Combat.getInstance().location(name));
		TILE_ENTITY_TYPES.add(type);
		return type;
	}
}
