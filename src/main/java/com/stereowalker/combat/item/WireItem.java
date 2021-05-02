package com.stereowalker.combat.item;

import com.stereowalker.combat.tileentity.ConnectorTileEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class WireItem extends Item {

	public WireItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ItemStack stack = context.getItem();
		if (!context.getWorld().isRemote) {
			if (context.getWorld().getTileEntity(context.getPos()) instanceof ConnectorTileEntity) {
				ConnectorTileEntity con = (ConnectorTileEntity)context.getWorld().getTileEntity(context.getPos());
				if (con.isConnected()) {
					context.getPlayer().sendMessage(new StringTextComponent("There are already wires here"), Util.DUMMY_UUID);
				}
				else {
					if (connectionLocation(stack) == null) {
						setConnectionLocation(stack, context.getPos());
						context.getPlayer().sendMessage(new StringTextComponent("Set first end"), Util.DUMMY_UUID);
					} else {
						if ((connectionLocation(stack).getX() != context.getPos().getX()) || (connectionLocation(stack).getY() != context.getPos().getY()) || (connectionLocation(stack).getZ() != context.getPos().getZ())) {
							ConnectorTileEntity con2 = (ConnectorTileEntity)context.getWorld().getTileEntity(connectionLocation(stack));
							con.setConnection(connectionLocation(stack));
							con2.setConnection(context.getPos());
							context.getPlayer().sendMessage(new StringTextComponent("Set Other end"), Util.DUMMY_UUID);
							removeConnectionLocation(stack);
							stack.shrink(1);
						} else {
							context.getPlayer().sendMessage(new StringTextComponent("Removing connection"), Util.DUMMY_UUID);
							removeConnectionLocation(stack);
						}
					}
				}
			}
		}
		return super.onItemUse(context);
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
