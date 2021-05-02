package com.stereowalker.combat.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.block.AlloyFurnaceBlock;
import com.stereowalker.combat.inventory.container.AlloyFurnaceContainer;
import com.stereowalker.combat.item.crafting.AbstractAlloyFurnaceRecipe;
import com.stereowalker.combat.item.crafting.CRecipeType;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class AlloyFurnaceTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);
	protected String customName;
	private int burnTime;
	private int recipesUsed;
	private int cookTime;
	private int cookTimeTotal = 200;

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	protected final IIntArray furnaceData = new IIntArray() {
		public int get(int index) {
			switch(index) {
			case 0:
				return AlloyFurnaceTileEntity.this.burnTime;
			case 1:
				return AlloyFurnaceTileEntity.this.recipesUsed;
			case 2:
				return AlloyFurnaceTileEntity.this.cookTime;
			case 3:
				return AlloyFurnaceTileEntity.this.cookTimeTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				AlloyFurnaceTileEntity.this.burnTime = value;
				break;
			case 1:
				AlloyFurnaceTileEntity.this.recipesUsed = value;
				break;
			case 2:
				AlloyFurnaceTileEntity.this.cookTime = value;
				break;
			case 3:
				AlloyFurnaceTileEntity.this.cookTimeTotal = value;
			}

		}

		public int size() {
			return 4;
		}
	};

	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	public AlloyFurnaceTileEntity() {
		super(CTileEntityType.ALLOY_FURNACE);
	}


	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container." + getNameString());
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);
		this.items = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.burnTime = compound.getInt("BurnTime");
		this.cookTime = compound.getInt("CookTime");
		this.cookTimeTotal = compound.getInt("CookTimeTotal");
		this.recipesUsed = this.getBurnTime(this.items.get(1));
		CompoundNBT compoundnbt = compound.getCompound("RecipesUsed");

		for(String s : compoundnbt.keySet()) {
			this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}
	}

	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.items);
		compound.putInt("BurnTime", this.burnTime);
		compound.putInt("CookTime", this.cookTime);
		compound.putInt("CookTimeTotal", this.cookTimeTotal);
		CompoundNBT compoundnbt = new CompoundNBT();
		this.recipes.forEach((recipeId, craftedAmount) -> {
			compoundnbt.putInt(recipeId.toString(), craftedAmount);
		});
		compound.put("RecipesUsed", compoundnbt);
		return compound;
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	//	@Override
	//	protected NonNullList<ItemStack> getItems() {
	//		return this.inventory;
	//	}

	//	@Override
	//	protected void setItems(NonNullList<ItemStack> itemsIn) {
	//
	//	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(this.hasCustomName() ? this.customName : "container.alloy_furnace");
	}

	public String getNameString() {
		return "alloy_furnace";
	}

	@Override
	protected Container createMenu(int id, PlayerInventory playerInventory) {
		//		this.fillWithLoot(playerInventory.player);
		return new AlloyFurnaceContainer(id, this, playerInventory, furnaceData);
	}

	public boolean hasInputSlotsFilled() {
		return 
				(!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty()) || 
				(!this.items.get(0).isEmpty() && !this.items.get(2).isEmpty()) || 
				(!this.items.get(1).isEmpty() && !this.items.get(0).isEmpty()) || 
				(!this.items.get(1).isEmpty() && !this.items.get(2).isEmpty()) || 
				(!this.items.get(2).isEmpty() && !this.items.get(0).isEmpty()) || 
				(!this.items.get(2).isEmpty() && !this.items.get(1).isEmpty());
	}


	@Override
	public void tick() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		if (this.isBurning()) {
			--this.burnTime;
		}

		if (!this.world.isRemote) {
			ItemStack itemstack = this.items.get(3);
			if (this.isBurning() || !itemstack.isEmpty() && hasInputSlotsFilled()) {
				AbstractAlloyFurnaceRecipe irecipe = this.world.getRecipeManager().getRecipe(CRecipeType.ALLOY_SMELTING, this, this.world).orElse(null);
				if (!this.isBurning() && this.canSmelt(irecipe)) {
					this.burnTime = this.getBurnTime(itemstack);
					this.recipesUsed = this.burnTime;
					if (this.isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem())
							this.items.set(3, itemstack.getContainerItem());
						else
							if (!itemstack.isEmpty()) {
								itemstack.shrink(1);
								if (itemstack.isEmpty()) {
									this.items.set(3, itemstack.getContainerItem());
								}
							}
					}
				}

				if (this.isBurning() && this.canSmelt(irecipe)) {
					++this.cookTime;
					if (this.cookTime == this.cookTimeTotal) {
						this.cookTime = 0;
						this.cookTimeTotal = this.getCookTime(irecipe);
						this.smelt(irecipe);
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
			}

			if (flag != this.isBurning()) {
				flag1 = true;
				this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AlloyFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
			}
		}

		if (flag1) {
			this.markDirty();
		}

	}

	protected int getCookTime(AbstractAlloyFurnaceRecipe irecipe) {
		if (irecipe != null) {
			return irecipe.getCookTime();
		}
		else {
			return 200;
		}
		//		if (!defaultI)
		//			//TODO: Find a way to return only this and nver the default
		//			return this.world.getRecipeManager().getRecipe(CRecipeType.ALLOY_SMELTING, this, this.world).map(AlloyFurnaceRecipe::getCookTime).orElse(200);
		//		else 
		//			return 200;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		if (this.world != null) {
			AbstractAlloyFurnaceRecipe irecipe = 
					this.
					world
					.getRecipeManager()
					.getRecipe(CRecipeType.ALLOY_SMELTING
							, this
							, this
							.world)
					.orElse(
							null);

			if (irecipe != null && hasInputSlotsFilled() && (index == 0 || index == 1 || index == 2) && !flag) {
				this.cookTimeTotal = getCookTime(irecipe);
				this.cookTime = 0;
				this.markDirty();
			}
		} else {
			if (hasInputSlotsFilled() && (index == 0 || index == 1 || index == 2) && !flag) {
				this.cookTimeTotal = 200;
				this.cookTime = 0;
				this.markDirty();
			}
		}
		Combat.debug("World is: "+this.world != null+" Cook: "+cookTimeTotal);

	}

	protected int getBurnTime(ItemStack fuel) {
		if (fuel.isEmpty()) {
			return 0;
		} else {
			return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
		}
	}

	protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		if (hasInputSlotsFilled() && recipeIn instanceof AbstractAlloyFurnaceRecipe) {
			AbstractAlloyFurnaceRecipe alloyFurnaceRecipe = (AbstractAlloyFurnaceRecipe) recipeIn;
			ItemStack itemstack = alloyFurnaceRecipe.getRecipeOutput();
			ItemStack itemstacko = alloyFurnaceRecipe.getRecipeOutput2();
			if (itemstack.isEmpty() && itemstacko.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.items.get(4);
				ItemStack itemstacko1 = this.items.get(5);
				if (itemstack1.isEmpty() && itemstacko1.isEmpty()) {
					return true;
				} else if (!itemstack1.isItemEqual(itemstack) && !itemstacko1.isItemEqual(itemstacko)) {
					return false;
				} else if ((itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) && (itemstacko1.getCount() + itemstacko.getCount() <= this.getInventoryStackLimit() && itemstacko1.getCount() + itemstacko.getCount() <= itemstacko1.getMaxStackSize())) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return (itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize()) && (itemstacko1.getCount() + itemstacko.getCount() <= itemstacko.getMaxStackSize()); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	private void smelt(@Nullable IRecipe<?> recipe) {
		if (recipe instanceof AbstractAlloyFurnaceRecipe && this.canSmelt(recipe)) {
			AbstractAlloyFurnaceRecipe alloyFurnaceRecipe = (AbstractAlloyFurnaceRecipe) recipe;
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack4 = this.items.get(1);
			ItemStack itemstack5 = this.items.get(2);

			ItemStack itemstack2 = this.items.get(4);
			ItemStack itemstack3 = this.items.get(5);

			ItemStack result1 = alloyFurnaceRecipe.getCraftingResult(this);
			ItemStack result2 = alloyFurnaceRecipe.getCraftingResult2(this);
			if (itemstack2.isEmpty()) {
				this.items.set(4, result1.copy());
			} else if (itemstack2.getItem() == result1.getItem()) {
				itemstack2.grow(result1.getCount());
			}

			if (itemstack3.isEmpty()) {
				this.items.set(5, result2.copy());
			} else if (itemstack3.getItem() == result2.getItem()) {
				itemstack3.grow(result2.getCount());
			}

			if (!this.world.isRemote) {
				this.setRecipeUsed(recipe);
			}

			itemstack.shrink(1);
			itemstack4.shrink(1);
			itemstack5.shrink(1);
		}
	}

	@Override
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	public void onCrafting(PlayerEntity player) {
	}

	@Override
	public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			this.recipes.addTo(resourcelocation, 1);
		}

	}

	public void unlockRecipes(PlayerEntity player) {
		List<IRecipe<?>> list = this.grantStoredRecipeExperience(player.world, player.getPositionVec());
		player.unlockRecipes(list);
		this.recipes.clear();
	}

	public List<IRecipe<?>> grantStoredRecipeExperience(World world, Vector3d pos) {
		List<IRecipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
			world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				AbstractFurnaceTileEntity.splitAndSpawnExperience(world, pos, entry.getIntValue(), ((AbstractAlloyFurnaceRecipe)recipe).getExperience());
			});
		}

		return list;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for(ItemStack itemstack : this.items) {
			helper.accountStack(itemstack);
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 1) {
			Item item = stack.getItem();
			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

}
