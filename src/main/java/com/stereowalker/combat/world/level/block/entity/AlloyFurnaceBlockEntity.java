package com.stereowalker.combat.world.level.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.inventory.AlloyFurnaceMenu;
import com.stereowalker.combat.world.item.crafting.AbstractAlloyFurnaceRecipe;
import com.stereowalker.combat.world.item.crafting.CRecipeType;
import com.stereowalker.combat.world.level.block.AlloyFurnaceBlock;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AlloyFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
	private NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);
	protected String customName;
	private int burnTime;
	private int recipesUsed;
	private int cookTime;
	private int cookTimeTotal = 200;

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	protected final ContainerData furnaceData = new ContainerData() {
		@Override
		public int get(int index) {
			switch(index) {
			case 0:
				return AlloyFurnaceBlockEntity.this.burnTime;
			case 1:
				return AlloyFurnaceBlockEntity.this.recipesUsed;
			case 2:
				return AlloyFurnaceBlockEntity.this.cookTime;
			case 3:
				return AlloyFurnaceBlockEntity.this.cookTimeTotal;
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch(index) {
			case 0:
				AlloyFurnaceBlockEntity.this.burnTime = value;
				break;
			case 1:
				AlloyFurnaceBlockEntity.this.recipesUsed = value;
				break;
			case 2:
				AlloyFurnaceBlockEntity.this.cookTime = value;
				break;
			case 3:
				AlloyFurnaceBlockEntity.this.cookTimeTotal = value;
			}

		}

		@Override
		public int getCount() {
			return 4;
		}
	};

	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	private final RecipeType<? extends AbstractAlloyFurnaceRecipe> recipeType;

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	public AlloyFurnaceBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(CBlockEntityType.ALLOY_FURNACE, pWorldPosition, pBlockState);
		this.recipeType = CRecipeType.ALLOY_SMELTING;
	}


	@Override
	public AABB getRenderBoundingBox() {
		return new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("container." + getNameString());
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.items = NonNullList.<ItemStack>withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.items);
		ContainerHelper.loadAllItems(compound, this.items);
		this.burnTime = compound.getInt("BurnTime");
		this.cookTime = compound.getInt("CookTime");
		this.cookTimeTotal = compound.getInt("CookTimeTotal");
		this.recipesUsed = this.getBurnTime(this.items.get(1));
		CompoundTag compoundnbt = compound.getCompound("RecipesUsed");

		for(String s : compoundnbt.getAllKeys()) {
			this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		ContainerHelper.saveAllItems(compound, this.items);
		compound.putInt("BurnTime", this.burnTime);
		compound.putInt("CookTime", this.cookTime);
		compound.putInt("CookTimeTotal", this.cookTimeTotal);
		CompoundTag compoundnbt = new CompoundTag();
		this.recipes.forEach((recipeId, craftedAmount) -> {
			compoundnbt.putInt(recipeId.toString(), craftedAmount);
		});
		compound.put("RecipesUsed", compoundnbt);
	}

	@Override
	public int getContainerSize() {
		return 6;
	}

	@Override
	public int getMaxStackSize() {
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
	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.getBlockPos()) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 0.5D, (double)this.getBlockPos().getZ() + 0.5D) <= 64.0D;
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
	protected Component getDefaultName() {
		return Component.translatable(this.hasCustomName() ? this.customName : "container.alloy_furnace");
	}

	public String getNameString() {
		return "alloy_furnace";
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
		//		this.fillWithLoot(playerInventory.player);
		return new AlloyFurnaceMenu(id, this, playerInventory, furnaceData);
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


	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AlloyFurnaceBlockEntity pBlockEntity) {
		boolean flag = pBlockEntity.isBurning();
		boolean flag1 = false;
		if (pBlockEntity.isBurning()) {
			--pBlockEntity.burnTime;
		}

		if (!pBlockEntity.level.isClientSide) {
			ItemStack itemstack = pBlockEntity.items.get(3);
			if (pBlockEntity.isBurning() || !itemstack.isEmpty() && pBlockEntity.hasInputSlotsFilled()) {
				AbstractAlloyFurnaceRecipe irecipe = pBlockEntity.level.getRecipeManager().getRecipeFor(CRecipeType.ALLOY_SMELTING, pBlockEntity, pBlockEntity.level).orElse(null);
				if (!pBlockEntity.isBurning() && pBlockEntity.canSmelt(irecipe)) {
					pBlockEntity.burnTime = pBlockEntity.getBurnTime(itemstack);
					pBlockEntity.recipesUsed = pBlockEntity.burnTime;
					if (pBlockEntity.isBurning()) {
						flag1 = true;
						if (itemstack.hasCraftingRemainingItem())
							pBlockEntity.items.set(3, itemstack.getCraftingRemainingItem());
						else
							if (!itemstack.isEmpty()) {
								itemstack.shrink(1);
								if (itemstack.isEmpty()) {
									pBlockEntity.items.set(3, itemstack.getCraftingRemainingItem());
								}
							}
					}
				}

				if (pBlockEntity.isBurning() && pBlockEntity.canSmelt(irecipe)) {
					++pBlockEntity.cookTime;
					if (pBlockEntity.cookTime == pBlockEntity.cookTimeTotal) {
						pBlockEntity.cookTime = 0;
						pBlockEntity.cookTimeTotal = pBlockEntity.getCookTime(irecipe);
						pBlockEntity.smelt(irecipe);
						flag1 = true;
					}
				} else {
					pBlockEntity.cookTime = 0;
				}
			} else if (!pBlockEntity.isBurning() && pBlockEntity.cookTime > 0) {
				pBlockEntity.cookTime = Mth.clamp(pBlockEntity.cookTime - 2, 0, pBlockEntity.cookTimeTotal);
			}

			if (flag != pBlockEntity.isBurning()) {
				flag1 = true;
				pBlockEntity.level.setBlock(pBlockEntity.getBlockPos(), pBlockEntity.level.getBlockState(pBlockEntity.getBlockPos()).setValue(AlloyFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isBurning())), 3);
			}
		}

		if (flag1) {
			pBlockEntity.setChanged();
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
		//			return this.world.getRecipeManager().byKey(CRecipeType.ALLOY_SMELTING, this, this.world).map(AlloyFurnaceRecipe::getCookTime).orElse(200);
		//		else 
		//			return 200;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}
		if (this.level != null) {
			AbstractAlloyFurnaceRecipe irecipe = 
					this.
					level
					.getRecipeManager()
					.getRecipeFor(CRecipeType.ALLOY_SMELTING
							, this
							, this
							.level)
					.orElse(
							null);

			if (irecipe != null && hasInputSlotsFilled() && (index == 0 || index == 1 || index == 2) && !flag) {
				this.cookTimeTotal = getCookTime(irecipe);
				this.cookTime = 0;
				this.setChanged();
			}
		} else {
			if (hasInputSlotsFilled() && (index == 0 || index == 1 || index == 2) && !flag) {
				this.cookTimeTotal = 200;
				this.cookTime = 0;
				this.setChanged();
			}
		}
		Combat.debug("Level is: "+this.level != null+" Cook: "+cookTimeTotal);

	}

	protected int getBurnTime(ItemStack fuel) {
		if (fuel.isEmpty()) {
			return 0;
		} else {
			return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel, this.recipeType);
		}
	}

	protected boolean canSmelt(@Nullable Recipe<?> recipeIn) {
		if (hasInputSlotsFilled() && recipeIn instanceof AbstractAlloyFurnaceRecipe) {
			AbstractAlloyFurnaceRecipe alloyFurnaceRecipe = (AbstractAlloyFurnaceRecipe) recipeIn;
			ItemStack itemstack = alloyFurnaceRecipe.getResultItem();
			ItemStack itemstacko = alloyFurnaceRecipe.getResultItem2();
			if (itemstack.isEmpty() && itemstacko.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.items.get(4);
				ItemStack itemstacko1 = this.items.get(5);
				if (itemstack1.isEmpty() && itemstacko1.isEmpty()) {
					return true;
				} else if (!itemstack1.sameItem(itemstack) && !itemstacko1.sameItem(itemstacko)) {
					return false;
				} else if ((itemstack1.getCount() + itemstack.getCount() <= this.getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) && (itemstacko1.getCount() + itemstacko.getCount() <= this.getMaxStackSize() && itemstacko1.getCount() + itemstacko.getCount() <= itemstacko1.getMaxStackSize())) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return (itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize()) && (itemstacko1.getCount() + itemstacko.getCount() <= itemstacko.getMaxStackSize()); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	private void smelt(@Nullable Recipe<?> recipe) {
		if (recipe instanceof AbstractAlloyFurnaceRecipe && this.canSmelt(recipe)) {
			AbstractAlloyFurnaceRecipe alloyFurnaceRecipe = (AbstractAlloyFurnaceRecipe) recipe;
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack4 = this.items.get(1);
			ItemStack itemstack5 = this.items.get(2);

			ItemStack itemstack2 = this.items.get(4);
			ItemStack itemstack3 = this.items.get(5);

			ItemStack result1 = alloyFurnaceRecipe.assemble(this);
			ItemStack result2 = alloyFurnaceRecipe.assemble2(this);
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

			if (!this.level.isClientSide) {
				this.setRecipeUsed(recipe);
			}

			itemstack.shrink(1);
			itemstack4.shrink(1);
			itemstack5.shrink(1);
		}
	}

	@Override
	public Recipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void awardUsedRecipes(Player player) {
	}

	@Override
	public void setRecipeUsed(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation resourcelocation = recipe.getId();
			this.recipes.addTo(resourcelocation, 1);
		}

	}

	public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
		List<Recipe<?>> list = this.grantStoredRecipeExperience(player.getLevel(), player.position());
		player.awardRecipes(list);
		this.recipes.clear();
	}

	public List<Recipe<?>> grantStoredRecipeExperience(ServerLevel world, Vec3 pos) {
		List<Recipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
			world.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				AbstractFurnaceBlockEntity.createExperience(world, pos, entry.getIntValue(), ((AbstractAlloyFurnaceRecipe)recipe).getExperience());
			});
		}

		return list;
	}

	@Override
	public ItemStack getItem(int index) {
		return this.items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.items, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.items, index);
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	@Override
	public void fillStackedContents(StackedContents helper) {
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
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) {
		return this.canPlaceItem(index, itemStackIn);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 1) {
			Item item = stack.getItem();
			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

}
