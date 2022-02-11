package com.stereowalker.combat.world.item;

import com.stereowalker.combat.world.entity.projectile.MjolnirLightning;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MjolnirItem extends Item implements LegendaryGear {

	public MjolnirItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (entity != null && player != null) {
			if(player.level instanceof ServerLevel) {
				BlockPos blockpos = new BlockPos(entity.blockPosition());
				if (player.level.canSeeSky(blockpos)) {
					LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(player.level);
					lightningboltentity.moveTo(Vec3.atBottomCenterOf(blockpos));
					lightningboltentity.setCause(player instanceof ServerPlayer ? (ServerPlayer)player : null);
					player.level.addFreshEntity(lightningboltentity);
					return true;
				}
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//		ItemStack stack = playerIn.getHeldItem(handIn);
		if(!worldIn.isClientSide) {
			MjolnirLightning entitymjolnirlighning = new MjolnirLightning(worldIn, playerIn);
			entitymjolnirlighning.shoot(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 10.5F, 1.0F);
			worldIn.addFreshEntity(entitymjolnirlighning);
		}
		return super.use(worldIn, playerIn, handIn);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return getRarity();
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		legendaryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void legendaryAbilityTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot) {

	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

}
