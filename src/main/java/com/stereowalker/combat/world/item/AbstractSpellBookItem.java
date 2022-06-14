package com.stereowalker.combat.world.item;

import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.client.gui.screens.inventory.SpellBookScreen;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.world.spellcraft.Spells;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractSpellBookItem extends Item {
	int number;

	public AbstractSpellBookItem(Properties properties, int number) {
		super(properties.stacksTo(1));
		this.number = number;
	}

	public CompoundTag bookTag(int number) {
		CompoundTag nbt = new CompoundTag();
		for (int i = 0; i < number-1; i++) {
			nbt.putString("Spell"+i, Spells.NONE.getKey());
		}
		nbt.putInt("SpellPos", 1);
		return nbt;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getDefaultInstance() {
		return addPropertiesToBook(new ItemStack(this));
	}

	public ItemStack addPropertiesToBook(ItemStack stack) {
		stack.setTag(bookTag(number));
		return stack;
	}

	/**
	 * Gets the spells in the spell book
	 * @param stack
	 * @param index
	 * @return
	 */
	public Spell getSpell(ItemStack stack, int index) {
		if (index >= 0 && index <= number-1) return SpellUtil.getSpellFromName(stack.getOrCreateTag().getString("Spell"+index));
		return Spells.NONE;
	}

	public Spell getNextSpell(ItemStack stack) {
		if (this.getSpellPos(stack) >=this.getSpellCapacity()-1) {
			return getSpell(stack, 0);
		}
		return getSpell(stack, getSpellPos(stack)+1);
	}

	public Spell getCurrentSpell(ItemStack stack) {
		return getSpell(stack, getSpellPos(stack));
	}

	/**
	 * @param player - The player to check
	 * @return
	 */
	public Spell getCurrentSpell(Player player) {
		return getCurrentSpell(getMainSpellBook(player));
	}

	public Spell getPreviousSpell(ItemStack stack) {
		if (this.getSpellPos(stack) <= 0) {
			return getSpell(stack, this.getSpellCapacity()-1);
		}
		return getSpell(stack, getSpellPos(stack)-1);
	}

	public int getSpellCapacity(){
		return number;
	}

	public int getSpellPos(ItemStack stack) {
		return stack.getOrCreateTag().getInt("SpellPos");
	}

	public void setSpellPos(ItemStack stack, int spellPosIn) {
		stack.getOrCreateTag().putInt("SpellPos", spellPosIn);
	}

	public void scrollSpell(boolean foward, ItemStack stack) {
		if(foward) {
			if(this.getSpellPos(stack) < this.getSpellCapacity()-1) {
				this.setSpellPos(stack, this.getSpellPos(stack) + 1);
			}
			else this.setSpellPos(stack, 0);
		}
		else {
			if(this.getSpellPos(stack) > 0) {
				this.setSpellPos(stack, this.getSpellPos(stack) - 1);
			}
			else this.setSpellPos(stack, this.getSpellCapacity()-1);
		}
	}

	/**
	 * Sets the spell in the spell book
	 * @param stack
	 * @param index
	 * @param spell
	 */
	public void setSpell(ItemStack stack, int index, Spell spell) {
		if (index >= 0 && index <= number-1) stack.getOrCreateTag().putString("Spell"+index, spell.getKey());
	}

	public void setSpellInBook(ItemStack stack, int index, Spell spell) {
		this.setSpell(stack, index, spell);
		this.verifyTagAfterLoad(stack.getTag());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack book = playerIn.getItemInHand(handIn);
		if (worldIn.isClientSide) {
			this.openGrimoire(book);
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, book);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoire(ItemStack book) {
		Minecraft.getInstance().setScreen(new SpellBookScreen(SpellBookScreen.IBookInfo.fromItem(book), book, true));
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoireInSlot(ItemStack book) {
		Minecraft.getInstance().setScreen(new SpellBookScreen(SpellBookScreen.IBookInfo.fromItem(book), book, false));
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoire(ItemStack book, int page) {
		SpellBookScreen screen = new SpellBookScreen(SpellBookScreen.IBookInfo.fromItem(book), book, true);
		Minecraft.getInstance().setScreen(screen);
		screen.showPage(page);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoireInSlot(ItemStack book, int page) {
		SpellBookScreen screen = new SpellBookScreen(SpellBookScreen.IBookInfo.fromItem(book), book, false);
		Minecraft.getInstance().setScreen(screen);
		screen.showPage(page);
	}


	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.bookTag(number));
		}
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		if (stack.getTag() == null) {
			stack.setTag(this.bookTag(number));
		}
	}

	public static ItemStack getBookInInventory(Player player, Item book) {
		if (ItemFilters.SPELLBOOKS.test(new ItemStack(book))) {
			for (ItemStack stack : player.getInventory().offhand) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
			for (ItemStack stack : player.getInventory().items) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
			//Highly unlikely that there's a spellbook here, but still check
			for (ItemStack stack : player.getInventory().armor) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack getMainSpellBook(Player player) {
		if (ModHelper.isCuriosLoaded()) {
			ItemStack stack = CuriosCompat.getSlotsForType(player, "book", 0);
			if (stack.is(ItemCTags.SPELLBOOKS))
				return stack;
			else 
				return ItemStack.EMPTY;
		} else {
			for (ItemStack stack : player.getInventory().offhand) {
				if (stack.is(ItemCTags.SPELLBOOKS)) {
					return stack;
				}
			}
			for (ItemStack stack : player.getInventory().items) {
				if (stack.is(ItemCTags.SPELLBOOKS)) {
					return stack;
				}
			}
			//Highly unlikely that there's a spellbook here, but still check
			for (ItemStack stack : player.getInventory().armor) {
				if (stack.is(ItemCTags.SPELLBOOKS)) {
					return stack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	//	public static ItemStack getMainSpellBook(Player player) {
	//		for (Item item : CTags.ItemCTags.SPELLBOOKS.getAllElements()) {
	//			if (!AbstractSpellBookItem.getBookInInventory(player, item).isEmpty()) {
	//				return AbstractSpellBookItem.getBookInInventory(player, item);
	//			}
	//		}
	//		return ItemStack.EMPTY;
	//	}

	public static AbstractSpellBookItem getMainSpellBookItem(Player playerEntity) {
		if (getMainSpellBook(playerEntity).getItem() instanceof AbstractSpellBookItem)
			return (AbstractSpellBookItem) getMainSpellBook(playerEntity).getItem();
		else return null;
	}

	public static boolean doesSpellBookContainSpell(Player player, Spell spell) {
		ItemStack bookStack = AbstractSpellBookItem.getMainSpellBook(player);
		if (!bookStack.isEmpty()) {
			AbstractSpellBookItem book = (AbstractSpellBookItem) AbstractSpellBookItem.getMainSpellBook(player).getItem();
			for (int i = 0; i < book.number; i++) {
				if (book.getSpell(bookStack, i) == spell) {
					return true;
				}
			}
		}
		return false;
	}

}
