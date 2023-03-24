package com.stereowalker.combat.world.item;

import java.util.List;
import java.util.function.Predicate;

import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * This class is pretty much a copy paste of {@link net.minecraft.world.item.BoatItem}. 
 * This will just remain here until Mojang adds a way to register cutsom boats
 * @author Stereowalker
 */
public class BoatCItem extends Item {
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final BoatMod.Type type;

	public BoatCItem(BoatMod.Type pType, Item.Properties pProperties) {
		super(pProperties);
		this.type = pType;
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		HitResult hitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
		if (hitresult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemstack);
		} else {
			Vec3 vec3 = pPlayer.getViewVector(1.0F);
			double d0 = 5.0D;
			List<Entity> list = pLevel.getEntities(pPlayer, pPlayer.getBoundingBox().expandTowards(vec3.scale(d0)).inflate(1.0D), ENTITY_PREDICATE);
			if (!list.isEmpty()) {
				Vec3 vec31 = pPlayer.getEyePosition();

				for(Entity entity : list) {
					AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
					if (aabb.contains(vec31)) {
						return InteractionResultHolder.pass(itemstack);
					}
				}
			}

			if (hitresult.getType() == HitResult.Type.BLOCK) {
				BoatMod boat = new BoatMod(pLevel, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
				boat.setModdedType(this.type);
				boat.setYRot(pPlayer.getYRot());
				if (!pLevel.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
					return InteractionResultHolder.fail(itemstack);
				} else {
					if (!pLevel.isClientSide) {
						pLevel.addFreshEntity(boat);
						pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, new BlockPos(hitresult.getLocation()));
						if (!pPlayer.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
					}

					pPlayer.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
				}
			} else {
				return InteractionResultHolder.pass(itemstack);
			}
		}
	}
}