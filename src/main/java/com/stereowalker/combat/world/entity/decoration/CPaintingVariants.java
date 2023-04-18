package com.stereowalker.combat.world.entity.decoration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.stereowalker.combat.Combat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.RegisterEvent.RegisterHelper;

public class CPaintingVariants {
	public static Map<ResourceLocation,PaintingVariant> PAINTINGS_TYPES = new HashMap<ResourceLocation,PaintingVariant>();

	public static final PaintingVariant PAINTING_A = register("painting_a", 32, 32);
	public static final PaintingVariant PAINTING_B = register("painting_b", 32, 32);
	public static final PaintingVariant PAINTING_C = register("painting_c", 64, 32);
	public static final PaintingVariant PAINTING_D = register("painting_d", 64, 32);
	public static final PaintingVariant PAINTING_E = register("painting_e", 32, 32);
	public static final PaintingVariant PAINTING_F = register("painting_f", 32, 32);
	public static final PaintingVariant PAINTING_G = register("painting_g", 32, 32);
	public static final PaintingVariant PAINTING_H = register("painting_h", 64, 32);
	public static final PaintingVariant PAINTING_I = register("painting_i", 32, 32);
	public static final PaintingVariant PAINTING_J = register("painting_j", 32, 32);
	public static final PaintingVariant PAINTING_K = register("painting_k", 64, 32);

	public static void registerAll(RegisterHelper<PaintingVariant> registry) {
		for(Entry<ResourceLocation, PaintingVariant> paintingTypes : PAINTINGS_TYPES.entrySet()) {
			registry.register(paintingTypes.getKey(), paintingTypes.getValue());
			Combat.debug("Painting: \""+paintingTypes.getKey().toString()+"\" registered");
		}
		Combat.debug("All Paintings Registered");
	}

	public static PaintingVariant register(String name, int width, int height) {
		PaintingVariant paintingType = new PaintingVariant(width, height);
		PAINTINGS_TYPES.put(Combat.getInstance().location(name), paintingType);
		return paintingType;
	}
}
