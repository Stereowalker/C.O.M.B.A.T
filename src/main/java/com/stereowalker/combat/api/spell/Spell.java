package com.stereowalker.combat.api.spell;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Spell extends ForgeRegistryEntry<Spell>{
	public Random rand;
	private String translationKey;
	private SpellCategory category;
	private Rank tier;
	private CastType type;
	private int cooldown;
	private float cost;
	private int castTime;
	//Locations
	private RayTraceResult rayTraceResult;

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

	public IFormattableTextComponent getKnownName() {
		return new TranslationTextComponent(this.getTranslationKey());
	}
	
	public IFormattableTextComponent getKnownColoredName() {
		return getKnownName().mergeStyle(category.getTextFormatting()); 
	}

	@OnlyIn(Dist.CLIENT)
	public IFormattableTextComponent getName(boolean isKnown) {
		return isKnown ? getKnownName() : SpellGalaticNames.getInstance().getGalacticSpellName(Minecraft.getInstance().fontRenderer, 1000, getKnownName());
	}

	@OnlyIn(Dist.CLIENT)
	public IFormattableTextComponent getColoredName(boolean isKnown) {
		return isKnown ? getKnownColoredName() : SpellGalaticNames.getInstance().getGalacticSpellName(Minecraft.getInstance().fontRenderer, 1000, getKnownName()).mergeStyle(category.getTextFormatting());
	}

	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeTranslationKey("spell", this.getRegistryName());
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
	
	public void setRayTraceResult(RayTraceResult rayTraceResult) {
		this.rayTraceResult = rayTraceResult;
	}
	
	public RayTraceResult getRayTraceResult() {
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

	public boolean primingProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		return true;
	}
	public abstract boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand);

	public ITextComponent getFailedMessage(LivingEntity caster) {
		return null;
	}

	public String toString() {
		return this.getRegistryName().getPath() + " " + this.getCategory().getName()+" "+this.getRank().getName();
	}

	public ITextComponent getDescription() {
		return new TranslationTextComponent(this.getDefaultTranslationKey()+".desc");
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
