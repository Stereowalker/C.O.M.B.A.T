package com.stereowalker.combat.spell;

import java.util.function.Predicate;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.item.CItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ReforgeSpell extends Spell {

	public ReforgeSpell(Rank tier, float cost, int cooldown, int castTime) {
		super(SpellCategory.EARTH, tier, CastType.SELF, cost, cooldown, castTime);
	}

	public static final Predicate<Item> STONE = (stack) -> {
		return stack.getItem() == Items.STONE_SWORD || 
				stack.getItem()  == Items.STONE_PICKAXE || 
				stack.getItem()  == Items.STONE_HOE || 
				stack.getItem()  == Items.STONE_SHOVEL || 
				stack.getItem()  == Items.STONE_AXE || 
				stack.getItem()  == CItems.STONE_HAMMER || 
				stack.getItem()  == CItems.STONE_KATANA || 
				stack.getItem()  == CItems.STONE_DAGGER || 
				stack.getItem()  == CItems.STONE_CHAKRAM;
	};

	public static final Predicate<Item> GOLDEN = (stack) -> {
		return stack.getItem() == Items.GOLDEN_SWORD || 
				stack.getItem()  == Items.GOLDEN_PICKAXE || 
				stack.getItem()  == Items.GOLDEN_HOE || 
				stack.getItem()  == Items.GOLDEN_SHOVEL || 
				stack.getItem()  == Items.GOLDEN_AXE || 
				stack.getItem()  == CItems.GOLDEN_HAMMER || 
				stack.getItem()  == CItems.GOLDEN_KATANA || 
				stack.getItem()  == CItems.GOLDEN_DAGGER || 
				stack.getItem()  == CItems.GOLDEN_CHAKRAM;
	};

	public static final Predicate<Item> IRON = (stack) -> {
		return stack.getItem() == Items.IRON_SWORD || 
				stack.getItem()  == Items.IRON_PICKAXE || 
				stack.getItem()  == Items.IRON_HOE || 
				stack.getItem()  == Items.IRON_SHOVEL || 
				stack.getItem()  == Items.IRON_AXE || 
				stack.getItem()  == CItems.IRON_HAMMER || 
				stack.getItem()  == CItems.IRON_KATANA || 
				stack.getItem()  == CItems.IRON_DAGGER || 
				stack.getItem()  == CItems.IRON_CHAKRAM;
	};

	public static final Predicate<Item> DIAMOND = (stack) -> {
		return stack.getItem() == Items.DIAMOND_SWORD || 
				stack.getItem()  == Items.DIAMOND_PICKAXE || 
				stack.getItem()  == Items.DIAMOND_HOE || 
				stack.getItem()  == Items.DIAMOND_SHOVEL || 
				stack.getItem()  == Items.DIAMOND_AXE || 
				stack.getItem()  == CItems.DIAMOND_HAMMER || 
				stack.getItem()  == CItems.DIAMOND_KATANA || 
				stack.getItem()  == CItems.DIAMOND_DAGGER || 
				stack.getItem()  == CItems.DIAMOND_CHAKRAM;
	};

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		//TODO: Use Configs to specify stone, wood, gold and diamond items
		if (this.getRank() == Rank.NOVICE && STONE.test(caster.getHeldItemOffhand().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.APPRENTICE && GOLDEN.test(caster.getHeldItemOffhand().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.ADVANCED && IRON.test(caster.getHeldItemOffhand().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.MASTER && DIAMOND.test(caster.getHeldItemOffhand().getItem())) return reforge(caster, strength);
		return false;
	}

	public boolean reforge(LivingEntity caster, double strength) {
		caster.getHeldItemOffhand().setDamage(caster.getHeldItemOffhand().getDamage()-MathHelper.floor(100+(100*strength)));
		return true;
	}
	
	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		String message = "";
		if (this.getRank() == Rank.NOVICE && !STONE.test(caster.getHeldItemOffhand().getItem())) message = "No Stone Tool In Offhand";
		if (this.getRank() == Rank.APPRENTICE && !GOLDEN.test(caster.getHeldItemOffhand().getItem())) message = "No Golden Tool In Offhand";
		if (this.getRank() == Rank.ADVANCED && !IRON.test(caster.getHeldItemOffhand().getItem())) message = "No Iron Tool In Offhand";
		if (this.getRank() == Rank.MASTER && !DIAMOND.test(caster.getHeldItemOffhand().getItem())) message = "No Diamond Tool In Offhand";
		return new TranslationTextComponent(message);
	}

}
