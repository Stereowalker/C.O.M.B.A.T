package com.stereowalker.combat.world.level.material;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CMaterial {
	public static final Material OIL = (new Material.Builder(MaterialColor.TERRACOTTA_ORANGE)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
	public static final Material BIABLE = (new Material.Builder(MaterialColor.WATER)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
}
