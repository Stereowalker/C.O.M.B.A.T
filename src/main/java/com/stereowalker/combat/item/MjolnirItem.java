package com.stereowalker.combat.item;

import com.stereowalker.combat.entity.projectile.MjolnirLightningEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MjolnirItem extends Item implements ILegendaryGear{

	public MjolnirItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		if (entity != null && player != null) {
			if(player.world instanceof ServerWorld) {
				BlockPos blockpos = new BlockPos(entity.getPosition());
				if (player.world.canSeeSky(blockpos)) {
					LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(player.world);
					lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));
					lightningboltentity.setCaster(player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null);
					player.world.addEntity(lightningboltentity);
					return true;
				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//		ItemStack stack = playerIn.getHeldItem(handIn);
		if(!worldIn.isRemote) {
			MjolnirLightningEntity entitymjolnirlighning = new MjolnirLightningEntity(worldIn, playerIn);
			entitymjolnirlighning.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 10.5F, 1.0F);
			worldIn.addEntity(entitymjolnirlighning);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot) {

	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

}
