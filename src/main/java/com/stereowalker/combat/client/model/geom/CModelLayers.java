package com.stereowalker.combat.client.model.geom;

import java.util.Set;

import com.google.common.collect.Sets;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CModelLayers {
	private static final Set<ModelLayerLocation> ALL_CUSTOM_MODELS = Sets.newHashSet();
	public static final ModelLayerLocation BACKPACK = register("backpack");
	public static final ModelLayerLocation BIOG = register("biog");
	public static final ModelLayerLocation RED_BIOG = register("red_biog");
	public static final ModelLayerLocation BLACK_HOLE = register("black_hole");
	public static final ModelLayerLocation LICHU = register("lichu");
	public static final ModelLayerLocation SHURIKEN = register("shuriken");
	public static final ModelLayerLocation SPEAR = register("spear");
	public static final ModelLayerLocation METEOR = register("meteor");
	public static final ModelLayerLocation QUIVER = register("quiver");
	public static final ModelLayerLocation ROBIN = register("robin");
	public static final ModelLayerLocation ROBIN_INNER_ARMOR = registerInnerArmor("robin");
	public static final ModelLayerLocation ROBIN_OUTER_ARMOR = registerOuterArmor("robin");
	public static final ModelLayerLocation ROUND_SHIELD = register("round_shield");
	public static final ModelLayerLocation SPELL_CIRCLE = register("spell_circle");
	public static final ModelLayerLocation VAMPIRE = register("vampire");
	public static final ModelLayerLocation ZOMBIE_COW = register("zombie_cow");

	private static ModelLayerLocation register(String p_171294_) {
		return register(p_171294_, "main");
	}

	private static ModelLayerLocation register(String p_171296_, String p_171297_) {
		ModelLayerLocation modellayerlocation = createLocation(p_171296_, p_171297_);
		if (!ALL_CUSTOM_MODELS.add(modellayerlocation)) {
			throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
		} else {
			return modellayerlocation;
		}
	}

	private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
		return new ModelLayerLocation(new ResourceLocation("combat", p_171301_), p_171302_);
	}

	private static ModelLayerLocation registerInnerArmor(String p_171299_) {
		return register(p_171299_, "inner_armor");
	}

	private static ModelLayerLocation registerOuterArmor(String p_171304_) {
		return register(p_171304_, "outer_armor");
	}

	public static ModelLayerLocation createModdedBoatModelName(BoatMod.Type p_171290_) {
		return createLocation("boat_mod/" + p_171290_.getName(), "main");
	}

	public static ModelLayerLocation createSignModelName(WoodType p_171292_) {
		ResourceLocation location = new ResourceLocation(p_171292_.name());
		return new ModelLayerLocation(new ResourceLocation(location.getNamespace(), "sign/" + location.getPath()), "main");
	}

	public static void init() {
		ModelLayers.ALL_MODELS.addAll(ALL_CUSTOM_MODELS);
	}
}
