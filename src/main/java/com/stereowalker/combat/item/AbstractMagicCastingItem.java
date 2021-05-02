package com.stereowalker.combat.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.util.UUIDS;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractMagicCastingItem extends Item implements IVanishable {
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
	public ItemStack getDefaultInstance() {
		return addPropertiesToWand(new ItemStack(this));
	}

	public ItemStack addPropertiesToWand(ItemStack stack) {
		stack.setTag(defaultWandTag());
		return stack;
	}

	public CompoundNBT defaultWandTag() {
		CompoundNBT nbt = new CompoundNBT();
		return nbt;
	}

	public abstract boolean canCast(PlayerEntity player, ItemStack wand);

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		Spell currentSpell = AbstractSpellBookItem.getMainSpellBookItem(playerIn)!=null?AbstractSpellBookItem.getMainSpellBookItem(playerIn).getCurrentSpell(playerIn):Spells.NONE;
		boolean flag = !AbstractSpellBookItem.getMainSpellBook(playerIn).isEmpty();
		boolean flag2 = SpellUtil.canEntityCastSpell(playerIn, currentSpell, getCostModifier());
		boolean flag3 = canCast(playerIn, itemstack);
		//		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		//		if (ret != null) return ret;		

		if (!(flag && flag2 && flag3)) {
			if(worldIn.isRemote)SpellUtil.printErrorMessages(playerIn, itemstack, currentSpell);
			return ActionResult.resultFail(itemstack);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemstack);
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerIn = (PlayerEntity)entityLiving;
			ItemStack book = AbstractSpellBookItem.getMainSpellBook(playerIn);
			Spell currentSpell = AbstractSpellBookItem.getMainSpellBookItem(playerIn).getCurrentSpell(book);
			int i = this.getUseDuration(stack) - timeLeft;
			//			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerIn, i, !itemstack.isEmpty() || flag);
			if (i < currentSpell.getCastTime()) return;

			if (!book.isEmpty())
			{
				if (playerIn.getHeldItemOffhand().getItem() instanceof AbstractSpellBookItem) {
					return;
				}
				RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
				currentSpell.setRayTraceResult(raytraceresult);
				double addStrength = (double)CombatEntityStats.getSpellStats(playerIn, currentSpell).getTimesCast()/100000.0d;
				if (!Config.MAGIC_COMMON.enableSpellTraining.get()) addStrength = 0.0d;
				SpellInstance spell = new SpellInstance(currentSpell, playerIn.getAttributeValue(CAttributes.MAGIC_STRENGTH)+addStrength, raytraceresult.getHitVec(), playerIn.getActiveHand(), playerIn.getUniqueID());
				if(spell.executeSpell(playerIn)) {
					if (worldIn.isRemote) SpellUtil.addEffects(playerIn, currentSpell);
					SpellUtil.spellItemEffects(playerIn, stack, currentSpell);
					//TODO:playerIn.addStat(CStats.SPELLS_CASTED);
					stack.damageItem(1, playerIn, (p_220045_0_) -> {
						p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					});
				}
			}
		}
	}

	public boolean updateSpell;
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.defaultWandTag());
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.setTag(this.defaultWandTag());
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		String m = getCostModifier() >= 0 ? "+" : "-"; 
		TextFormatting f = getCostModifier() >= 0 ? TextFormatting.RED : TextFormatting.GREEN; 
		int cost = (int) (MathHelper.abs((float) getCostModifier())*10000);
		tooltip.add(new TranslationTextComponent(m+(float)(cost/(100.0F))+"% Mana Cost").mergeStyle(f));
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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
	}
}
