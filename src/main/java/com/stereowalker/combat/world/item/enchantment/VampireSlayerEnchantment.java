package com.stereowalker.combat.world.item.enchantment;

import com.stereowalker.combat.world.effect.CMobEffects;
import com.stereowalker.combat.world.entity.monster.Vampire;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class VampireSlayerEnchantment extends Enchantment {

	public VampireSlayerEnchantment(Rarity rarityIn, EquipmentSlot[] slots) {
		super(rarityIn, EnchantmentCategory.WEAPON, slots);
	}
	
	@Override
	public int getMinCost(int enchantmentLevel) 
	{
		return 20 * enchantmentLevel;
	}
	
	@Override
	public int getMaxCost(int enchantmentLevel)
	{
		return this.getMinCost(enchantmentLevel) + 10;
	}
	
	@Override
	public int getMaxLevel()
	{
		return 5;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment ench) 
	{
		return super.checkCompatibility(ench) && ench != Enchantments.BANE_OF_ARTHROPODS && ench != Enchantments.SMITE;
	}
	
	@Override
	public void doPostAttack(LivingEntity user, Entity target, int level)
    {
		if (((LivingEntity) target).hasEffect(CMobEffects.VAMPIRISM))
	    {
			LivingEntity entitylivingbase = (LivingEntity)target;
        	int i = 20 + user.getRandom().nextInt(10 * level);
	        entitylivingbase.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
	        entitylivingbase.setSecondsOnFire(i);
	        entitylivingbase.setHealth(entitylivingbase.getMaxHealth() - i);
        }
		if (target instanceof Vampire)
	    {
        	Vampire entitylivingbase = (Vampire)target;
        	int i = 20 + user.getRandom().nextInt(10 * level);
	        entitylivingbase.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i, 3));
	        entitylivingbase.setSecondsOnFire(i);
	        entitylivingbase.setHealth(entitylivingbase.getMaxHealth() - i);
        }
	}

}
