package com.stereowalker.combat.world.level.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.stereowalker.combat.world.level.block.AbstractElectricFurnaceBlock;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractElectricFurnaceBlockEntity extends AbstractEnergyConsumerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
	private static final int[] SLOTS_INPUT = new int[]{0};
	private static final int[] SLOTS_OUTPUT = new int[]{1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
	private int recipesUsed;
	private int cookTime;
	private int cookTimeTotal;
	protected final ContainerData furnaceData = new ContainerData() {
		public int get(int index) {
			switch(index) {
			case 0:
				return AbstractElectricFurnaceBlockEntity.this.recipesUsed;
			case 1:
				return AbstractElectricFurnaceBlockEntity.this.cookTime;
			case 2:
				return AbstractElectricFurnaceBlockEntity.this.cookTimeTotal;
			case 3:
				return AbstractElectricFurnaceBlockEntity.this.getEnergyStored();
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				AbstractElectricFurnaceBlockEntity.this.recipesUsed = value;
				break;
			case 1:
				AbstractElectricFurnaceBlockEntity.this.cookTime = value;
				break;
			case 2:
				AbstractElectricFurnaceBlockEntity.this.cookTimeTotal = value;
				break;
			case 3:
				AbstractElectricFurnaceBlockEntity.this.setEnergy(value);
			}

		}

		public int getCount() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
	//	private final Map<ResourceLocation, Integer> recipes = Maps.newHashMap();
	protected final RecipeType<? extends AbstractCookingRecipe> recipeType;

	protected AbstractElectricFurnaceBlockEntity(BlockEntityType<?> tileTypeIn, BlockPos pWorldPosition, BlockState pBlockState, RecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(tileTypeIn, pWorldPosition, pBlockState, 300);
		this.recipeType = recipeTypeIn;
	}

	protected boolean isBurning() {
		return !this.isDrained() && this.getLevel().hasNeighborSignal(getBlockPos());
	}

	@Override
	public void load(CompoundTag nbt) { //TODO: MARK
		super.load(nbt);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, this.items);
		this.cookTime = nbt.getInt("CookTime");
		this.cookTimeTotal = nbt.getInt("CookTimeTotal");
//		this.recipesUsed = this.getBurnTime(this.items.get(1));
		CompoundTag compoundnbt = nbt.getCompound("RecipesUsed");

		for(String s : compoundnbt.getAllKeys()) {
			this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
		}

	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		compound.putInt("CookTime", this.cookTime);
		compound.putInt("CookTimeTotal", this.cookTimeTotal);
		ContainerHelper.saveAllItems(compound, this.items);
		CompoundTag compoundnbt = new CompoundTag();
		this.recipes.forEach((recipeId, craftedAmount) -> {
			compoundnbt.putInt(recipeId.toString(), craftedAmount);
		});
		compound.put("RecipesUsed", compoundnbt);
		return compound;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AbstractElectricFurnaceBlockEntity pBlockEntity) {
		boolean flag = pBlockEntity.isBurning();
		boolean flag1 = false;

		if (!pBlockEntity.getLevel().isClientSide) {
			ItemStack itemstack = pBlockEntity.items.get(1);
			if (pBlockEntity.isBurning() || !itemstack.isEmpty() && !pBlockEntity.items.get(0).isEmpty()) {
				Recipe<?> irecipe = pBlockEntity.getLevel().getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)pBlockEntity.recipeType, pBlockEntity, pBlockEntity.getLevel()).orElse(null);
				if (!pBlockEntity.isBurning() && pBlockEntity.canSmelt(irecipe)) {
					if (pBlockEntity.isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem())
							pBlockEntity.items.set(1, itemstack.getContainerItem());
						else
							if (!itemstack.isEmpty()) {
								itemstack.shrink(1);
								if (itemstack.isEmpty()) {
									pBlockEntity.items.set(1, itemstack.getContainerItem());
								}
							}
					}
				}

				if (pBlockEntity.isBurning() && pBlockEntity.canSmelt(irecipe)) {
					pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getLevel().getBlockState(pBlockEntity.getBlockPos()).setValue(AbstractElectricFurnaceBlock.LIT, Boolean.valueOf(true)), 3);
					++pBlockEntity.cookTime;
					pBlockEntity.energy--;
					if (pBlockEntity.cookTime == pBlockEntity.cookTimeTotal) {
						pBlockEntity.cookTime = 0;
						pBlockEntity.cookTimeTotal = AbstractFurnaceBlockEntity.getTotalCookTime(pLevel, pBlockEntity.recipeType, pBlockEntity);
						pBlockEntity.burn(irecipe);
						flag1 = true;
					}
				} else {
					pBlockEntity.getLevel().setBlock(pBlockEntity.getBlockPos(), pBlockEntity.getLevel().getBlockState(pBlockEntity.getBlockPos()).setValue(AbstractElectricFurnaceBlock.LIT, Boolean.valueOf(false)), 3);
					pBlockEntity.cookTime = 0;
				}
			} else if (!pBlockEntity.isBurning() && pBlockEntity.cookTime > 0) {
				pBlockEntity.cookTime = Mth.clamp(pBlockEntity.cookTime - 2, 0, pBlockEntity.cookTimeTotal);
			}

			if (flag != pBlockEntity.isBurning()) {
				flag1 = true;
			}
		}

		if (flag1) {
			pBlockEntity.setChanged();
		}

	}

	protected boolean canSmelt(@Nullable Recipe<?> recipeIn) {
		if (!this.items.get(0).isEmpty() && recipeIn != null) {
			ItemStack itemstack = recipeIn.getResultItem();
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.items.get(1);
				if (itemstack1.isEmpty()) {
					return true;
				} else if (!itemstack1.sameItem(itemstack)) {
					return false;
				} else if (itemstack1.getCount() + itemstack.getCount() <= this.getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("resource")
	private void burn(@Nullable Recipe<?> p_214007_1_) {
		if (p_214007_1_ != null && this.canSmelt(p_214007_1_)) {
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack1 = p_214007_1_.getResultItem();
			ItemStack itemstack2 = this.items.get(1);
			if (itemstack2.isEmpty()) {
				this.items.set(1, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (!this.getLevel().isClientSide) {
				this.setRecipeUsed(p_214007_1_);
			}

			itemstack.shrink(1);
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_OUTPUT;
		} else {
			return SLOTS_INPUT;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
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

	@Override
	public int getContainerSize() {
		return this.items.size();
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
	public void setItem(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}

		if (index == 0 && !flag) {
			this.cookTimeTotal = AbstractFurnaceBlockEntity.getTotalCookTime(level, recipeType, this);
			this.cookTime = 0;
			this.setChanged();
		}

	}

	@Override
	public boolean stillValid(Player player) {
		if (this.getLevel().getBlockEntity(this.getBlockPos()) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.getBlockPos().getX() + 0.5D, (double)this.getBlockPos().getY() + 0.5D, (double)this.getBlockPos().getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 1) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	public void setRecipeUsed(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			this.recipes.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
				return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
			});
		}

	}

	@Nullable
	public Recipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public void awardUsedRecipes(Player player) {
	}

	public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
		List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.getLevel(), player.position());
		player.awardRecipes(list);
		this.recipes.clear();
	}

	public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel world, Vec3 pos) {
		List<Recipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation> entry : this.recipes.object2IntEntrySet()) {
			world.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
				list.add(recipe);
				 AbstractFurnaceBlockEntity.createExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
			});
		}

		return list;
	}

	@Override
	public void fillStackedContents(StackedContents helper) {
		for(ItemStack itemstack : this.items) {
			helper.accountStack(itemstack);
		}

	}

	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
			net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	/**
	 * invalidates a tile entity
	 */
	@Override
	public void setRemoved() {
		super.setRemoved();
		for (int x = 0; x < handlers.length; x++)
			handlers[x].invalidate();
	}
}