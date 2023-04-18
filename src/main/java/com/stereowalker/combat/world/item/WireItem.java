package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.level.block.entity.ConnectorBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class WireItem extends Item {

	public WireItem(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("resource")
	@Override
	public InteractionResult useOn(UseOnContext context) {
		ItemStack stack = context.getItemInHand();
		if (!context.getLevel().isClientSide) {
			if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ConnectorBlockEntity) {
				ConnectorBlockEntity con = (ConnectorBlockEntity)context.getLevel().getBlockEntity(context.getClickedPos());
				if (con.isConnected()) {
					context.getPlayer().sendSystemMessage(Component.literal("There are already wires here"));
				}
				else {
					if (connectionLocation(stack) == null) {
						setConnectionLocation(stack, context.getClickedPos());
						context.getPlayer().sendSystemMessage(Component.literal("Set first end"));
					} else {
						if ((connectionLocation(stack).getX() != context.getClickedPos().getX()) || (connectionLocation(stack).getY() != context.getClickedPos().getY()) || (connectionLocation(stack).getZ() != context.getClickedPos().getZ())) {
							ConnectorBlockEntity con2 = (ConnectorBlockEntity)context.getLevel().getBlockEntity(connectionLocation(stack));
							con.setConnection(connectionLocation(stack));
							con2.setConnection(context.getClickedPos());
							context.getPlayer().sendSystemMessage(Component.literal("Set Other end"));
							removeConnectionLocation(stack);
							stack.shrink(1);
						} else {
							context.getPlayer().sendSystemMessage(Component.literal("Removing connection"));
							removeConnectionLocation(stack);
						}
					}
				}
			}
		}
		return super.useOn(context);
	}

	public static BlockPos connectionLocation(ItemStack stack) {
		if (stack.getOrCreateTag().contains("conX") && stack.getOrCreateTag().contains("conY") && stack.getOrCreateTag().contains("conZ")) {
			int x = stack.getTag().getInt("conX");
			int y = stack.getTag().getInt("conY");
			int z = stack.getTag().getInt("conZ");
			return new BlockPos(x,y,z);
		}
		return null;
	}

	public static void setConnectionLocation(ItemStack stack, BlockPos pos) {
		if (!stack.getOrCreateTag().contains("conX") && !stack.getOrCreateTag().contains("conY") && !stack.getOrCreateTag().contains("conZ")) {
			stack.getTag().putInt("conX", pos.getX());
			stack.getTag().putInt("conY", pos.getY());
			stack.getTag().putInt("conZ", pos.getZ());
		}
	}

	public static void removeConnectionLocation(ItemStack stack) {
		if (stack.getOrCreateTag().contains("conX") && stack.getOrCreateTag().contains("conY") && stack.getOrCreateTag().contains("conZ")) {
			stack.getTag().remove("conX");
			stack.getTag().remove("conY");
			stack.getTag().remove("conZ");
		}
	}

	//	public static boolean attachToConector(PlayerEntity player, World worldIn, BlockPos fence) {
	//	      ConnectorEntity leashknotentity = null;
	//	      boolean flag = false;
	//	      double d0 = 7.0D;
	//	      int i = fence.getX();
	//	      int j = fence.getY();
	//	      int k = fence.getZ();
	//
	//	      for(MobEntity mobentity : worldIn.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
	//	         if (mobentity.getLeashHolder() == player) {
	//	            if (leashknotentity == null) {
	//	               leashknotentity = LeashKnotEntity.create(worldIn, fence);
	//	            }
	//
	//	            mobentity.setLeashHolder(leashknotentity, true);
	//	            flag = true;
	//	         }
	//	      }
	//
	//	      return flag;
	//	   }

}
