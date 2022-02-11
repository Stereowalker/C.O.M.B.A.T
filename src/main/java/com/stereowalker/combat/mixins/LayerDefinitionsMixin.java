package com.stereowalker.combat.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.google.common.collect.ImmutableMap.Builder;
import com.stereowalker.combat.client.model.BackpackModel;
import com.stereowalker.combat.client.model.BiogModel;
import com.stereowalker.combat.client.model.BlackHoleModel;
import com.stereowalker.combat.client.model.LichuModel;
import com.stereowalker.combat.client.model.MeteorModel;
import com.stereowalker.combat.client.model.QuiverModel;
import com.stereowalker.combat.client.model.RobinModel;
import com.stereowalker.combat.client.model.RoundShieldModel;
import com.stereowalker.combat.client.model.ShurikenModel;
import com.stereowalker.combat.client.model.SpearModel;
import com.stereowalker.combat.client.model.SpellCircleModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {

	@Inject(method = "createRoots", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/model/ArmorStandModel;createBodyLayer()Lnet/minecraft/client/model/geom/builders/LayerDefinition;"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void inject_createRoots(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir, Builder<ModelLayerLocation, LayerDefinition> builder, LayerDefinition layerdefinition, LayerDefinition layerdefinition1, LayerDefinition layerdefinition2, LayerDefinition layerdefinition3) {
		builder.put(CModelLayers.BACKPACK, BackpackModel.createLayer());
		builder.put(CModelLayers.BIOG, BiogModel.createBodyLayer());
		builder.put(CModelLayers.RED_BIOG, BiogModel.createBodyLayer());
		builder.put(CModelLayers.BLACK_HOLE, BlackHoleModel.createBodyLayer());
		builder.put(CModelLayers.LICHU, LichuModel.createBodyLayer());
		builder.put(CModelLayers.METEOR, MeteorModel.createBodyLayer());
		builder.put(CModelLayers.QUIVER, QuiverModel.createLayer());
		builder.put(CModelLayers.ROBIN, RobinModel.createBodyLayer());
		builder.put(CModelLayers.ROBIN_INNER_ARMOR, layerdefinition3);
		builder.put(CModelLayers.ROBIN_OUTER_ARMOR, layerdefinition1);
		builder.put(CModelLayers.SHURIKEN, ShurikenModel.createLayer());
		builder.put(CModelLayers.SPEAR, SpearModel.createLayer());
		builder.put(CModelLayers.SPELL_CIRCLE, SpellCircleModel.createBodyLayer());
		builder.put(CModelLayers.ROUND_SHIELD, RoundShieldModel.createLayer());
		builder.put(CModelLayers.VAMPIRE, LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64));
		builder.put(CModelLayers.ZOMBIE_COW, CowModel.createBodyLayer());
		LayerDefinition layerdefinition18 = BoatModel.createBodyModel();

		for(BoatMod.Type boat$type : BoatMod.Type.values()) {
			builder.put(CModelLayers.createModdedBoatModelName(boat$type), layerdefinition18);
		}
	}

}
