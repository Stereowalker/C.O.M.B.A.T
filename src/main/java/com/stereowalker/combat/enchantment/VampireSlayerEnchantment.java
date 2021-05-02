package com.stereowalker.combat.enchantment;

import com.stereowalker.combat.entity.monster.VampireEntity;
import com.stereowalker.combat.potion.CEffects;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class VampireSlayerEnchantment extends Enchantment{

	public VampireSlayerEnchantment(Rarity rarityIn, EquipmentSlotType[] slots) {
		super(rarityIn, EnchantmentType.WEAPON, slots);
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) 
	{
		return 20 * enchantmentLevel;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
		return this.getMinEnchantability(enchantmentLevel) + 10;
	}
	
	@Override
	public int getMaxLevel()
	{
		return 5;
	}
	
	@Override
	protected boolean canApplyTogether(Enchantment ench) 
	{
		return super.canApplyTogether(ench) && ench != Enchantments.BANE_OF_ARTHROPODS && ench != Enchantments.SMITE;
	}
	
	public void onEntityDamaged(LivingEntity user, Entity target, int level)
    {
		if (((LivingEntity) target).isPotionActive(CEffects.VAMPIRISM))
	    {
			LivingEntity entitylivingbase = (LivingEntity)target;
        	int i = 20 + user.getRNG().nextInt(10 * level);
	        entitylivingbase.addPotionEffect(new EffectInstance(Effects.SLOWNESS, i, 3));
	        entitylivingbase.setFire(i);
	        entitylivingbase.setHealth(entitylivingbase.getMaxHealth() - i);
        }
		if (target instanceof VampireEntity)
	    {
        	VampireEntity entitylivingbase = (VampireEntity)target;
        	int i = 20 + user.getRNG().nextInt(10 * level);
	        entitylivingbase.addPotionEffect(new EffectInstance(Effects.SLOWNESS, i, 3));
	        entitylivingbase.setFire(i);
	        entitylivingbase.setHealth(entitylivingbase.getMaxHealth() - i);
        }
	}

}
