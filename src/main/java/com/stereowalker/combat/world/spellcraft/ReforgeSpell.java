package com.stereowalker.combat.world.spellcraft;

import java.util.function.Predicate;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class ReforgeSpell extends Spell {

	public ReforgeSpell(Rank tier, float cost, int cooldown, int castTime) {
		super(SpellCategory.EARTH, tier, CastType.SELF, cost, cooldown, castTime);
	}

	public static final Predicate<Item> STONE = (stack) -> {
		return stack.asItem() == Items.STONE_SWORD || 
				stack.asItem()  == Items.STONE_PICKAXE || 
				stack.asItem()  == Items.STONE_HOE || 
				stack.asItem()  == Items.STONE_SHOVEL || 
				stack.asItem()  == Items.STONE_AXE || 
				stack.asItem()  == CItems.STONE_HAMMER || 
				stack.asItem()  == CItems.STONE_KATANA || 
				stack.asItem()  == CItems.STONE_DAGGER || 
				stack.asItem()  == CItems.STONE_CHAKRAM;
	};

	public static final Predicate<Item> GOLDEN = (stack) -> {
		return stack.asItem() == Items.GOLDEN_SWORD || 
				stack.asItem()  == Items.GOLDEN_PICKAXE || 
				stack.asItem()  == Items.GOLDEN_HOE || 
				stack.asItem()  == Items.GOLDEN_SHOVEL || 
				stack.asItem()  == Items.GOLDEN_AXE || 
				stack.asItem()  == CItems.GOLDEN_HAMMER || 
				stack.asItem()  == CItems.GOLDEN_KATANA || 
				stack.asItem()  == CItems.GOLDEN_DAGGER || 
				stack.asItem()  == CItems.GOLDEN_CHAKRAM;
	};

	public static final Predicate<Item> IRON = (stack) -> {
		return stack.asItem() == Items.IRON_SWORD || 
				stack.asItem()  == Items.IRON_PICKAXE || 
				stack.asItem()  == Items.IRON_HOE || 
				stack.asItem()  == Items.IRON_SHOVEL || 
				stack.asItem()  == Items.IRON_AXE || 
				stack.asItem()  == CItems.IRON_HAMMER || 
				stack.asItem()  == CItems.IRON_KATANA || 
				stack.asItem()  == CItems.IRON_DAGGER || 
				stack.asItem()  == CItems.IRON_CHAKRAM;
	};

	public static final Predicate<Item> DIAMOND = (stack) -> {
		return stack.asItem() == Items.DIAMOND_SWORD || 
				stack.asItem()  == Items.DIAMOND_PICKAXE || 
				stack.asItem()  == Items.DIAMOND_HOE || 
				stack.asItem()  == Items.DIAMOND_SHOVEL || 
				stack.asItem()  == Items.DIAMOND_AXE || 
				stack.asItem()  == CItems.DIAMOND_HAMMER || 
				stack.asItem()  == CItems.DIAMOND_KATANA || 
				stack.asItem()  == CItems.DIAMOND_DAGGER || 
				stack.asItem()  == CItems.DIAMOND_CHAKRAM;
	};

	public static final Predicate<Item> NETHERITE = (stack) -> {
		return stack.asItem() == Items.NETHERITE_SWORD || 
				stack.asItem()  == Items.NETHERITE_PICKAXE || 
				stack.asItem()  == Items.NETHERITE_HOE || 
				stack.asItem()  == Items.NETHERITE_SHOVEL || 
				stack.asItem()  == Items.NETHERITE_AXE || 
//				stack.getItem()  == CItems.NETHERITE_HAMMER || 
				stack.asItem()  == CItems.NETHERITE_KATANA || 
				stack.asItem()  == CItems.NETHERITE_DAGGER;
//				stack.getItem()  == CItems.NETHERITE_CHAKRAM;
	};

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		//TODO: Use Configs to specify stone, wood, gold and diamond items
		if (this.getRank() == Rank.BASIC && STONE.test(caster.getOffhandItem().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.NOVICE && GOLDEN.test(caster.getOffhandItem().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.APPRENTICE && IRON.test(caster.getOffhandItem().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.ADVANCED && DIAMOND.test(caster.getOffhandItem().getItem())) return reforge(caster, strength);
		if (this.getRank() == Rank.MASTER && NETHERITE.test(caster.getOffhandItem().getItem())) return reforge(caster, strength);
		return false;
	}

	public boolean reforge(LivingEntity caster, double strength) {
		caster.getOffhandItem().setDamageValue(caster.getOffhandItem().getDamageValue()-Mth.floor(100+(100*strength)));
		return true;
	}
	
	@Override
	public Component getFailedMessage(LivingEntity caster) {
		String message = "";
		if (this.getRank() == Rank.BASIC && STONE.test(caster.getOffhandItem().getItem())) message = "No Stone Tool In Offhand";
		if (this.getRank() == Rank.NOVICE && GOLDEN.test(caster.getOffhandItem().getItem())) message = "No Golden Tool In Offhand";
		if (this.getRank() == Rank.APPRENTICE && IRON.test(caster.getOffhandItem().getItem())) message = "No Iron Tool In Offhand";
		if (this.getRank() == Rank.ADVANCED && DIAMOND.test(caster.getOffhandItem().getItem())) message = "No Diamond Tool In Offhand";
		if (this.getRank() == Rank.MASTER && NETHERITE.test(caster.getOffhandItem().getItem())) message = "No Netherite Tool In Offhand";
		return new TranslatableComponent(message);
	}

}
