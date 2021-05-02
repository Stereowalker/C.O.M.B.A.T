package com.stereowalker.combat.item;

import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.api.spell.SpellUtil;
import com.stereowalker.combat.client.gui.screen.SpellBookScreen;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.spell.Spells;
import com.stereowalker.combat.tags.CTags;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractSpellBookItem extends Item{
	int number;

	public AbstractSpellBookItem(Properties properties, int number) {
		super(properties.maxStackSize(1));
		this.number = number;
	}

	public CompoundNBT bookTag(int number) {
		CompoundNBT nbt = new CompoundNBT();
		for (int i = 0; i < number-1; i++) {
			nbt.putString("Spell"+i, Spells.NONE.getKey());
		}
		nbt.putInt("SpellPos", 1);
		return nbt;
	}

	@OnlyIn(Dist.CLIENT)
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
	public Spell getCurrentSpell(PlayerEntity player) {
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
		this.updateItemStackNBT(stack.getTag());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack book = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote) {
			this.openGrimoire(book);
		}
		return new ActionResult<>(ActionResultType.SUCCESS, book);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoire(ItemStack book) {
		SpellBookScreen screen = new SpellBookScreen(SpellBookScreen.IBookInfo.func_216917_a(book), book, true);
		Minecraft.getInstance().displayGuiScreen(screen);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoireInSlot(ItemStack book) {
		SpellBookScreen screen = new SpellBookScreen(SpellBookScreen.IBookInfo.func_216917_a(book), book, false);
		Minecraft.getInstance().displayGuiScreen(screen);
	}

	@OnlyIn(Dist.CLIENT)
	public void openGrimoire(ItemStack book, int page) {
		SpellBookScreen screen = new SpellBookScreen(SpellBookScreen.IBookInfo.func_216917_a(book), book, true);
		Minecraft.getInstance().displayGuiScreen(screen);
		screen.showPage(page);
	}


	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.getTag() == null) {
			stack.setTag(this.bookTag(number));
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		if (stack.getTag() == null) {
			stack.setTag(this.bookTag(number));
		}
	}

	public static ItemStack getBookInInventory(PlayerEntity player, Item book) {
		if (ItemFilters.SPELLBOOKS.test(new ItemStack(book))) {
			for (ItemStack stack : player.inventory.offHandInventory) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
			for (ItemStack stack : player.inventory.mainInventory) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
			//Highly unlikely that there's a spellbook here, but still check
			for (ItemStack stack : player.inventory.armorInventory) {
				if (stack.getItem() == book) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack getMainSpellBook(PlayerEntity player) {
		if (ModHelper.isCuriosLoaded()) {
			ItemStack stack = CuriosCompat.getSlotsForType(player, "book", 0);
			if (CTags.ItemCTags.SPELLBOOKS.contains(stack.getItem()))
				return stack;
			else 
				return ItemStack.EMPTY;
		} else {
			for (ItemStack stack : player.inventory.offHandInventory) {
				if (CTags.ItemCTags.SPELLBOOKS.contains(stack.getItem())) {
					return stack;
				}
			}
			for (ItemStack stack : player.inventory.mainInventory) {
				if (CTags.ItemCTags.SPELLBOOKS.contains(stack.getItem())) {
					return stack;
				}
			}
			//Highly unlikely that there's a spellbook here, but still check
			for (ItemStack stack : player.inventory.armorInventory) {
				if (CTags.ItemCTags.SPELLBOOKS.contains(stack.getItem())) {
					return stack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	//	public static ItemStack getMainSpellBook(PlayerEntity player) {
	//		for (Item item : CTags.ItemCTags.SPELLBOOKS.getAllElements()) {
	//			if (!AbstractSpellBookItem.getBookInInventory(player, item).isEmpty()) {
	//				return AbstractSpellBookItem.getBookInInventory(player, item);
	//			}
	//		}
	//		return ItemStack.EMPTY;
	//	}

	public static AbstractSpellBookItem getMainSpellBookItem(PlayerEntity playerEntity) {
		if (getMainSpellBook(playerEntity).getItem() instanceof AbstractSpellBookItem)
			return (AbstractSpellBookItem) getMainSpellBook(playerEntity).getItem();
		else return null;
	}

	public static boolean doesSpellBookContainSpell(PlayerEntity player, Spell spell) {
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
