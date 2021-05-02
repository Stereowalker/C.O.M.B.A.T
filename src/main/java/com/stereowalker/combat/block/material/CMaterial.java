package com.stereowalker.combat.block.material;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CMaterial {
	public static final Material OIL = (new Material.Builder(MaterialColor.ORANGE_TERRACOTTA)).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().liquid().build();
	public static final Material BIABLE = (new Material.Builder(MaterialColor.WATER)).doesNotBlockMovement().notOpaque().notSolid().pushDestroys().replaceable().liquid().build();
}
