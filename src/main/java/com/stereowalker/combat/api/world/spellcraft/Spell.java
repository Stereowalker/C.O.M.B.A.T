package com.stereowalker.combat.api.world.spellcraft;

import java.util.Random;

import com.stereowalker.combat.api.registries.CombatRegistries;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class Spell {
	public Random rand;
	private String translationKey;
	private SpellCategory category;
	private Rank tier;
	private CastType type;
	private int cooldown;
	private float cost;
	private int castTime;
	//Locations
	private HitResult rayTraceResult;

	public Spell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		this.category = category;
		this.tier = tier;
		this.type = type;
		this.cost = cost;
		this.cooldown = cooldown;
		this.rayTraceResult = null;
		this.castTime = castTime;
	}

	public String getTranslationKey() {
		return this.getDefaultTranslationKey();
	}

	public String getKey() {
		return getTranslationKey();
	}

	public MutableComponent getKnownName() {
		return Component.translatable(this.getTranslationKey());
	}
	
	public MutableComponent getKnownColoredName() {
		return getKnownName().withStyle((Style t) -> t.withColor(category.getColor())); 
	}

	@OnlyIn(Dist.CLIENT)
	public MutableComponent getName(boolean isKnown) {
		return isKnown ? getKnownName() : SpellGalaticNames.getInstance().getGalacticSpellName(Minecraft.getInstance().font, 1000, getKnownName());
	}

	@OnlyIn(Dist.CLIENT)
	public MutableComponent getColoredName(boolean isKnown) {
		return isKnown ? getKnownColoredName() : SpellGalaticNames.getInstance().getGalacticSpellName(Minecraft.getInstance().font, 1000, getKnownName()).withStyle((Style t) -> t.withColor(category.getColor()));
	}

	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeDescriptionId("spell", CombatRegistries.SPELLS.getKey(this));
		}

		return this.translationKey;
	}

	public SpellCategory getCategory() {
		return category;
	}

	public Rank getRank() {
		return tier;
	}
	
	public int getWeight() {
		return getRank().getWeight();
	}

	public CastType getCastType() {
		return type;
	}

	public float getCost() {
		return cost;
	}

	public int getCooldown() {
		return cooldown;
	}
	
	public int getCastTime() {
		return Math.max(castTime,1);
	}
	
	public void setRayTraceResult(HitResult rayTraceResult) {
		this.rayTraceResult = rayTraceResult;
	}
	
	public HitResult getRayTraceResult() {
		return rayTraceResult;
	}

	public boolean isClientSpell() {
		return false;
	}
	
	public boolean isHeld() {
		return false;
	}
	
	public boolean canBePrimed() {
		return false;
	}

	public boolean primingProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		return true;
	}
	public abstract boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand);

	public Component getFailedMessage(LivingEntity caster) {
		return null;
	}

	public String toString() {
		return CombatRegistries.SPELLS.getKey(this).getPath() + " " + this.getCategory().getName()+" "+this.getRank().getName();
	}

	public Component getDescription() {
		return Component.translatable(this.getDefaultTranslationKey()+".desc");
	}

	public static enum CastType {
		SELF,
		AREA_OF_EFFECT,
		RAY,
		PROJECTILE,
		TRAP,
		SURROUND,
		BLOCK;

	}

}
