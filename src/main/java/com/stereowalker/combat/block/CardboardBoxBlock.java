package com.stereowalker.combat.block;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.tileentity.CardboardBoxTileEntity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class CardboardBoxBlock extends ContainerBlock{
	public static final ResourceLocation field_220169_b = new ResourceLocation("contents");

	public CardboardBoxBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile == null) {
			}
			if(tile instanceof CardboardBoxTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity)player, (CardboardBoxTileEntity)tile, extraData -> {extraData.writeBlockPos(pos);});
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
	 * this block
	 */
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof CardboardBoxTileEntity) {
			CardboardBoxTileEntity CardboardBoxTileEntity = (CardboardBoxTileEntity)tileentity;
			if (!worldIn.isRemote /*&& player.isCreative()*/ && !CardboardBoxTileEntity.isEmpty()) {
				ItemStack itemstack = new ItemStack(this);
				CompoundNBT compoundnbt = CardboardBoxTileEntity.saveToNbt(new CompoundNBT());
				if (!compoundnbt.isEmpty()) {
					itemstack.setTagInfo("BlockEntityTag", compoundnbt);
				}

				if (CardboardBoxTileEntity.hasCustomName()) {
					itemstack.setDisplayName(CardboardBoxTileEntity.getCustomName());
				}

				ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
				itementity.setDefaultPickupDelay();
				worldIn.addEntity(itementity);
			} else {
				CardboardBoxTileEntity.fillWithLoot(player);
			}
		}

		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@SuppressWarnings("deprecation")
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
		if (tileentity instanceof CardboardBoxTileEntity) {
			CardboardBoxTileEntity CardboardBoxTileEntity = (CardboardBoxTileEntity)tileentity;
			builder = builder.withDynamicDrop(field_220169_b, (p_220168_1_, p_220168_2_) -> {
				for(int i = 0; i < CardboardBoxTileEntity.getSizeInventory(); ++i) {
					p_220168_2_.accept(CardboardBoxTileEntity.getStackInSlot(i));
				}

			});
		}

		return super.getDrops(state, builder);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof CardboardBoxTileEntity) {
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}


	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
		if (compoundnbt != null) {
			if (compoundnbt.contains("LootTable", 8)) {
				tooltip.add(new StringTextComponent("???????"));
			}

			if (compoundnbt.contains("Items", 9)) {
				NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
				ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
				int i = 0;
				int j = 0;

				for(ItemStack itemstack : nonnulllist) {
					if (!itemstack.isEmpty()) {
						++j;
						if (i <= 4) {
							++i;
							IFormattableTextComponent itextcomponent = itemstack.getDisplayName().deepCopy();
							itextcomponent.appendString(" x").appendString(String.valueOf(itemstack.getCount()));
							tooltip.add(itextcomponent);
						}
					}
				}

				if (j - i > 0) {
					tooltip.add((new TranslationTextComponent("container.shulkerBox.more", j - i)).mergeStyle(TextFormatting.ITALIC));
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		ItemStack itemstack = super.getItem(worldIn, pos, state);
		CardboardBoxTileEntity CardboardBoxTileEntity = (CardboardBoxTileEntity)worldIn.getTileEntity(pos);
		CompoundNBT compoundnbt = CardboardBoxTileEntity.saveToNbt(new CompoundNBT());
		if (!compoundnbt.isEmpty()) {
			itemstack.setTagInfo("BlockEntityTag", compoundnbt);
		}

		return itemstack;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new CardboardBoxTileEntity();
	}

}
