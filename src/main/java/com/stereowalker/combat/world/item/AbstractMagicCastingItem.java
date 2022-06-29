package com.stereowalker.combat.world.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.stats.CStats;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.spellcraft.Spells;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMagicCastingItem extends Item implements Vanishable {
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	private Rank tier;
	private double strengthModifier;
	private double costModifier;
	private double castSpeed;

	public AbstractMagicCastingItem(Properties properties, Rank tier, double strengthModifier, double costModifier) {
		super(properties);
		this.tier = tier;
		this.strengthModifier = strengthModifier;
		this.costModifier = costModifier;
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(CAttributes.MAGIC_STRENGTH, new AttributeModifier(UUIDS.MAGIC_STRENGTH_MODIFIER, "Wand modifier", (double)this.strengthModifier, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public Rank getTier() {
		return tier;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getDefaultInstance() {
		return addPropertiesToWand(new ItemStack(this));
	}

	public ItemStack addPropertiesToWand(ItemStack stack) {
		stack.setTag(defaultWandTag());
		return stack;
	}

	public CompoundTag defaultWandTag() {
		CompoundTag nbt = new CompoundTag();
		return nbt;
	}
	
	public abstract boolean canCast(Player player, ItemStack wand);

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		Spell currentSpell = AbstractSpellBookItem.getMainSpellBookItem(playerIn)!=null?AbstractSpellBookItem.getMainSpellBookItem(playerIn).getCurrentSpell(playerIn):Spells.NONE;
		if (currentSpell.isHeld()) {
			ItemStack book = AbstractSpellBookItem.getMainSpellBook(playerIn);
			if (!book.isEmpty()/* && CombatEntityStats.isHoldFlagActive(playerIn) */)
			{
				if (playerIn.getOffhandItem().getItem() instanceof AbstractSpellBookItem) {
					return InteractionResultHolder.fail(itemstack);
				}
				HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
				currentSpell.setRayTraceResult(raytraceresult);
				double addStrength = (double)CombatEntityStats.getSpellStats(playerIn, currentSpell).getTimesCast()/100000.0d;
				if (!Combat.MAGIC_CONFIG.enableSpellTraining) addStrength = 0.0d;
				double multiplier = currentSpell.getCategory().getAttachedAttribute() == null ? 1 : playerIn.getAttributeValue(currentSpell.getCategory().getAttachedAttribute());
				SpellInstance spell = new SpellInstance(currentSpell, (playerIn.getAttributeValue(CAttributes.MAGIC_STRENGTH)+addStrength)*multiplier, raytraceresult.getLocation(), playerIn.getUsedItemHand(), playerIn.getUUID());
				if(spell.executeSpell(playerIn)) {
					if (worldIn.isClientSide) SpellUtil.addEffects(playerIn, currentSpell);
					SpellUtil.spellItemEffects(playerIn, itemstack, currentSpell);
					playerIn.awardStat(CStats.SPELLS_CASTED);
					itemstack.hurtAndBreak(1, playerIn, (p_220045_0_) -> {
						p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
					});
					CombatEntityStats.setHoldFlag(playerIn, true);
					playerIn.startUsingItem(handIn);
					return InteractionResultHolder.consume(itemstack);
				} else {
					return InteractionResultHolder.fail(itemstack);
				}
			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		} else {
			boolean flag = !AbstractSpellBookItem.getMainSpellBook(playerIn).isEmpty();
			boolean flag2 = SpellUtil.canEntityCastSpell(playerIn, currentSpell, getCostModifier());
			boolean flag3 = canCast(playerIn, itemstack);
			//		InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
			//		if (ret != null) return ret;		
			
			if (!(flag && flag2 && flag3)) {
				if(worldIn.isClientSide)SpellUtil.printErrorMessages(playerIn, itemstack, currentSpell);
				return InteractionResultHolder.fail(itemstack);
			} else {
				playerIn.startUsingItem(handIn);
				return InteractionResultHolder.consume(itemstack);
			}
		}
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			CombatEntityStats.setHoldFlag(entityLiving, false);
			Player playerIn = (Player)entityLiving;
			ItemStack book = AbstractSpellBookItem.getMainSpellBook(playerIn);
			Spell currentSpell = AbstractSpellBookItem.getMainSpellBookItem(playerIn).getCurrentSpell(book);
			int i = this.getUseDuration(stack) - timeLeft;
			//			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerIn, i, !itemstack.isEmpty() || flag);
			if (i < currentSpell.getCastTime() && !CombatEntityStats.getSpellStats(playerIn, currentSpell).isPrimed()) return;

			if (!book.isEmpty() && !currentSpell.isHeld())
			{
				if (playerIn.getOffhandItem().getItem() instanceof AbstractSpellBookItem) {
					return;
				}
				HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
				currentSpell.setRayTraceResult(raytraceresult);
				double addStrength = (double)CombatEntityStats.getSpellStats(playerIn, currentSpell).getTimesCast()/100000.0d;
				if (!Combat.MAGIC_CONFIG.enableSpellTraining) addStrength = 0.0d;
				double multiplier = currentSpell.getCategory().getAttachedAttribute() == null ? 1 : playerIn.getAttributeValue(currentSpell.getCategory().getAttachedAttribute());
				SpellInstance spell = new SpellInstance(currentSpell, (playerIn.getAttributeValue(CAttributes.MAGIC_STRENGTH)+addStrength)*multiplier, raytraceresult.getLocation(), playerIn.getUsedItemHand(), playerIn.getUUID());
				if(spell.executeSpell(playerIn)) {
					if (worldIn.isClientSide) SpellUtil.addEffects(playerIn, currentSpell);
					SpellUtil.spellItemEffects(playerIn, stack, currentSpell);
					playerIn.awardStat(CStats.SPELLS_CASTED);
					stack.hurtAndBreak(1, playerIn, (p_220045_0_) -> {
						p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
					});
				}
			}
		}
	}

	public boolean updateSpell;
	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.defaultWandTag());
		}
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.setTag(this.defaultWandTag());
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		String m = getCostModifier() >= 0 ? "+" : "-"; 
		ChatFormatting f = getCostModifier() >= 0 ? ChatFormatting.RED : ChatFormatting.GREEN; 
		int cost = (int) (Mth.abs((float) getCostModifier())*10000);
		tooltip.add(new TranslatableComponent(m+(float)(cost/(100.0F))+"% Mana Cost").withStyle(f));
	}


	public double getStrengthModifier() {
		return strengthModifier;
	}

	public double getCostModifier() {
		return costModifier;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
	}
}
