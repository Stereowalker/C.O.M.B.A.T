package com.stereowalker.combat.state.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;

public class BlockStatePropertiesList {
	public static final EnumProperty<VerticalSlabType> VERTICAL_SLAB_TYPE = EnumProperty.create("type", VerticalSlabType.class);
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");
	public static final EnumProperty<CableConnectionType> NORTH_CONNECTION = EnumProperty.create("north_connection", CableConnectionType.class);
	public static final EnumProperty<CableConnectionType> SOUTH_CONNECTION = EnumProperty.create("south_connection", CableConnectionType.class);
	public static final EnumProperty<CableConnectionType> EAST_CONNECTION = EnumProperty.create("east_connection", CableConnectionType.class);
	public static final EnumProperty<CableConnectionType> WEST_CONNECTION = EnumProperty.create("west_connection", CableConnectionType.class);
	public static final EnumProperty<CableConnectionType> DOWN_CONNECTION = EnumProperty.create("down_connection", CableConnectionType.class);
	public static final EnumProperty<CableConnectionType> UP_CONNECTION = EnumProperty.create("up_connection", CableConnectionType.class);
}
