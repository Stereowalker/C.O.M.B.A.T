package com.stereowalker.combat.world.level.block;

import java.util.List;

import javax.annotation.Nullable;

import com.stereowalker.combat.world.level.block.entity.CardboardBoxBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class CardboardBoxBlock extends BaseEntityBlock{
	public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

	public CardboardBoxBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if(!worldIn.isClientSide) {
			BlockEntity tile = worldIn.getBlockEntity(pos);
			if(tile == null) {
			}
			if(tile instanceof CardboardBoxBlockEntity) {
				NetworkHooks.openGui((ServerPlayer)player, (CardboardBoxBlockEntity)tile, extraData -> {extraData.writeBlockPos(pos);});
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
	 * this block
	 */
	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof CardboardBoxBlockEntity) {
			CardboardBoxBlockEntity blockEntity = (CardboardBoxBlockEntity)tileentity;
			if (!worldIn.isClientSide /*&& player.isCreative()*/ && !blockEntity.isEmpty()) {
				ItemStack itemstack = new ItemStack(this);
				CompoundTag compoundnbt = blockEntity.saveToTag(new CompoundTag());
				if (!compoundnbt.isEmpty()) {
					itemstack.addTagElement("BlockEntityTag", compoundnbt);
				}

				if (blockEntity.hasCustomName()) {
					itemstack.setHoverName(blockEntity.getCustomName());
				}

				ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
				itementity.setDefaultPickUpDelay();
				worldIn.addFreshEntity(itementity);
			} else {
				blockEntity.unpackLootTable(player);
			}
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		BlockEntity tileentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (tileentity instanceof CardboardBoxBlockEntity) {
			CardboardBoxBlockEntity blockEntity = (CardboardBoxBlockEntity)tileentity;
			builder = builder.withDynamicDrop(CONTENTS, (p_220168_1_, p_220168_2_) -> {
				for(int i = 0; i < blockEntity.getContainerSize(); ++i) {
					p_220168_2_.accept(blockEntity.getItem(i));
				}

			});
		}

		return super.getDrops(state, builder);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if(state.getBlock() != newState.getBlock()) {
			BlockEntity te = worldIn.getBlockEntity(pos);
			if(te instanceof CardboardBoxBlockEntity) {
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}


	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		CompoundTag compoundnbt = stack.getTagElement("BlockEntityTag");
		if (compoundnbt != null) {
			if (compoundnbt.contains("LootTable", 8)) {
				tooltip.add(new TextComponent("???????"));
			}

			if (compoundnbt.contains("Items", 9)) {
				NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
				ContainerHelper.loadAllItems(compoundnbt, nonnulllist);
				int i = 0;
				int j = 0;

				for(ItemStack itemstack : nonnulllist) {
					if (!itemstack.isEmpty()) {
						++j;
						if (i <= 4) {
							++i;
							MutableComponent itextcomponent = itemstack.getDisplayName().copy();
							itextcomponent.append(" x").append(String.valueOf(itemstack.getCount()));
							tooltip.add(itextcomponent);
						}
					}
				}

				if (j - i > 0) {
					tooltip.add((new TranslatableComponent("container.shulkerBox.more", j - i)).withStyle(ChatFormatting.ITALIC));
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		ItemStack itemstack = super.getCloneItemStack(worldIn, pos, state);
		CardboardBoxBlockEntity blockEntity = (CardboardBoxBlockEntity)worldIn.getBlockEntity(pos);
		CompoundTag compoundnbt = blockEntity.saveToTag(new CompoundTag());
		if (!compoundnbt.isEmpty()) {
			itemstack.addTagElement("BlockEntityTag", compoundnbt);
		}

		return itemstack;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CardboardBoxBlockEntity(pPos, pState);
	}

}
