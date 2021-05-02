package com.stereowalker.combat.potion;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.IForgeRegistry;

public class CEffects {
	public static List<Effect> EFFECT = new ArrayList<Effect>();
	
	public static final Effect VAMPIRISM = register("vampirism", new CEffect(EffectType.NEUTRAL, 0x100000)
			.addAttributesModifier(Attributes.MOVEMENT_SPEED, "48d7cedd-8451-4e73-8db6-ffcd0bd4f78a", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributesModifier(Attributes.ARMOR, "7afcd8af-88a8-4473-906a-71ecd1bb75ee", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributesModifier(Attributes.ATTACK_SPEED, "cf1a6c86-5642-4f60-8516-121584fcc46f", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributesModifier(Attributes.MAX_HEALTH, "ce8b6150-06af-495d-b220-fbbc9e5a4a57", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "45382ac8-93b3-4cce-be9f-7565c4dbde3c", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE)
			.addAttributesModifier(Attributes.ATTACK_DAMAGE, "36f654ca-a323-4047-a0cd-ce153a064860", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE));
	public static final Effect PARALYSIS = register("paralysis", new CEffect(EffectType.HARMFUL, 5756160).addAttributesModifier(Attributes.MOVEMENT_SPEED, "48d7cedd-8451-4e73-8db6-ffcd0bd4f78c", -10000.0D, AttributeModifier.Operation.ADDITION));
	public static final Effect FLAMMABLE = register("flammable", new CEffect(EffectType.HARMFUL, 12221756));
	public static final Effect FROSTBITE = register("frostbite", new CEffect(EffectType.HARMFUL, 6861359)).addAttributesModifier(Attributes.MOVEMENT_SPEED, "017e0934-a424-4310-8a3c-a3ce6018fa83", (double)-0.30F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	public static final Effect GRAVITY_PLUS = register("gravity_plus", new CEffect(EffectType.NEUTRAL, 6861359)).addAttributesModifier(ForgeMod.ENTITY_GRAVITY.get(), "017e0934-a424-4310-8a3c-a3ce6018fa84", (double)0.01F, AttributeModifier.Operation.MULTIPLY_BASE);
	public static final Effect GRAVITY_MINUS = register("gravity_minus", new CEffect(EffectType.NEUTRAL, 6861359)).addAttributesModifier(ForgeMod.ENTITY_GRAVITY.get(), "017e0934-a424-4310-8a3c-a3ce6018fa85", (double)-0.01F, AttributeModifier.Operation.MULTIPLY_BASE);
	public static final Effect FEAR = register("fear", new CEffect(EffectType.HARMFUL, 0));
	public static final Effect MAGIC_JAMMING = register("magic_jamming", new CEffect(EffectType.HARMFUL, 0));
	public static final Effect INSULATION = register("insulation", new CEffect(EffectType.BENEFICIAL, 14981690));
	public static final Effect MANA_REGENERATION = register("mana_regeneration", new CEffect(EffectType.BENEFICIAL, 0x1887A4));
	public static final Effect SLOW_SWING = register("slow_swing", new CEffect(EffectType.HARMFUL, 0).addAttributesModifier(Attributes.ATTACK_SPEED, "add33e32-bef5-432a-82c7-0d95dd11792f", -0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL));
	
	public static void registerAll(IForgeRegistry<Effect> registry) {
		for(Effect effect : EFFECT) {
			registry.register(effect);
			Combat.debug("Effect: \""+effect.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Effects Registered");
	}
	
	public static Effect register(String name, Effect effect) {
		effect.setRegistryName(Combat.getInstance().location(name));
		EFFECT.add(effect);
		return effect;
	}
}
