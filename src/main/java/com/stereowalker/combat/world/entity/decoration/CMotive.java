package com.stereowalker.combat.world.entity.decoration;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.registries.IForgeRegistry;

public class CMotive {
	public static List<Motive> PAINTINGS_TYPES = new ArrayList<Motive>();

	public static final Motive PAINTING_A = register("painting_a", 32, 32);
	public static final Motive PAINTING_B = register("painting_b", 32, 32);
	public static final Motive PAINTING_C = register("painting_c", 64, 32);
	public static final Motive PAINTING_D = register("painting_d", 64, 32);
	public static final Motive PAINTING_E = register("painting_e", 32, 32);
	public static final Motive PAINTING_F = register("painting_f", 32, 32);
	public static final Motive PAINTING_G = register("painting_g", 32, 32);
	public static final Motive PAINTING_H = register("painting_h", 64, 32);
	public static final Motive PAINTING_I = register("painting_i", 32, 32);
	public static final Motive PAINTING_J = register("painting_j", 32, 32);
	public static final Motive PAINTING_K = register("painting_k", 64, 32);

	public static void registerAll(IForgeRegistry<Motive> registry) {
		for(Motive paintingTypes : PAINTINGS_TYPES) {
			registry.register(paintingTypes);
			Combat.debug("Painting: \""+paintingTypes.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Paintings Registered");
	}

	public static Motive register(String name, int width, int height) {
		Motive paintingType = new Motive(width, height);
		paintingType.setRegistryName(Combat.getInstance().location(name));
		PAINTINGS_TYPES.add(paintingType);
		return paintingType;
	}
}
