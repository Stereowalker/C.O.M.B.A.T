package com.stereowalker.combat.entity.item;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.registries.IForgeRegistry;

public class CPaintingType {
	public static List<PaintingType> PAINTINGS_TYPES = new ArrayList<PaintingType>();

	public static final PaintingType PAINTING_A = register("painting_a", 32, 32);
	public static final PaintingType PAINTING_B = register("painting_b", 32, 32);
	public static final PaintingType PAINTING_C = register("painting_c", 64, 32);
	public static final PaintingType PAINTING_D = register("painting_d", 64, 32);
	public static final PaintingType PAINTING_E = register("painting_e", 32, 32);
	public static final PaintingType PAINTING_F = register("painting_f", 32, 32);
	public static final PaintingType PAINTING_G = register("painting_g", 32, 32);
	public static final PaintingType PAINTING_H = register("painting_h", 64, 32);
	public static final PaintingType PAINTING_I = register("painting_i", 32, 32);
	public static final PaintingType PAINTING_J = register("painting_j", 32, 32);
	public static final PaintingType PAINTING_K = register("painting_k", 64, 32);

	public static void registerAll(IForgeRegistry<PaintingType> registry) {
		for(PaintingType paintingTypes : PAINTINGS_TYPES) {
			registry.register(paintingTypes);
			Combat.debug("Painting: \""+paintingTypes.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Paintings Registered");
	}

	public static PaintingType register(String name, int width, int height) {
		PaintingType paintingType = new PaintingType(width, height);
		paintingType.setRegistryName(Combat.getInstance().location(name));
		PAINTINGS_TYPES.add(paintingType);
		return paintingType;
	}
}
