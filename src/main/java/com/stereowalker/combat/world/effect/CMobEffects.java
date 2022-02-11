package com.stereowalker.combat.world.effect;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.IForgeRegistry;

public class CMobEffects {
	public static List<MobEffect> EFFECT = new ArrayList<MobEffect>();
	
	public static final MobEffect VAMPIRISM = register("vampirism", new CMobEffect(MobEffectCategory.NEUTRAL, 0x100000)
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "48d7cedd-8451-4e73-8db6-ffcd0bd4f78a", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributeModifier(Attributes.ARMOR, "7afcd8af-88a8-4473-906a-71ecd1bb75ee", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributeModifier(Attributes.ATTACK_SPEED, "cf1a6c86-5642-4f60-8516-121584fcc46f", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributeModifier(Attributes.MAX_HEALTH, "ce8b6150-06af-495d-b220-fbbc9e5a4a57", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "45382ac8-93b3-4cce-be9f-7565c4dbde3c", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "36f654ca-a323-4047-a0cd-ce153a064860", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE));
	public static final MobEffect PARALYSIS = register("paralysis", new CMobEffect(MobEffectCategory.HARMFUL, 5756160).addAttributeModifier(Attributes.MOVEMENT_SPEED, "48d7cedd-8451-4e73-8db6-ffcd0bd4f78c", -10000.0D, AttributeModifier.Operation.ADDITION));
	public static final MobEffect FLAMMABLE = register("flammable", new CMobEffect(MobEffectCategory.HARMFUL, 12221756));
	public static final MobEffect FROSTBITE = register("frostbite", new CMobEffect(MobEffectCategory.HARMFUL, 6861359)).addAttributeModifier(Attributes.MOVEMENT_SPEED, "017e0934-a424-4310-8a3c-a3ce6018fa83", (double)-0.30F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final MobEffect GRAVITY_PLUS = register("gravity_plus", new CMobEffect(MobEffectCategory.NEUTRAL, 6861359)).addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "017e0934-a424-4310-8a3c-a3ce6018fa84", (double)0.01F, AttributeModifier.Operation.MULTIPLY_BASE);
	public static final MobEffect GRAVITY_MINUS = register("gravity_minus", new CMobEffect(MobEffectCategory.NEUTRAL, 6861359)).addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "017e0934-a424-4310-8a3c-a3ce6018fa85", (double)-0.01F, AttributeModifier.Operation.MULTIPLY_BASE);
	public static final MobEffect FEAR = register("fear", new CMobEffect(MobEffectCategory.HARMFUL, 0));
	public static final MobEffect MAGIC_JAMMING = register("magic_jamming", new CMobEffect(MobEffectCategory.HARMFUL, 0));
	public static final MobEffect INSULATION = register("insulation", new CMobEffect(MobEffectCategory.BENEFICIAL, 14981690));
	public static final MobEffect MANA_REGENERATION = register("mana_regeneration", new CMobEffect(MobEffectCategory.BENEFICIAL, 0x1887A4));
	public static final MobEffect SLOW_SWING = register("slow_swing", new CMobEffect(MobEffectCategory.HARMFUL, 0).addAttributeModifier(Attributes.ATTACK_SPEED, "add33e32-bef5-432a-82c7-0d95dd11792f", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));
	
	public static void registerAll(IForgeRegistry<MobEffect> registry) {
		for(MobEffect effect : EFFECT) {
			registry.register(effect);
			Combat.debug("MobEffect: \""+effect.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Effects Registered");
	}
	
	public static MobEffect register(String name, MobEffect effect) {
		effect.setRegistryName(Combat.getInstance().location(name));
		EFFECT.add(effect);
		return effect;
	}
}
