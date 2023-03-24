package com.stereowalker.combat.world.entity.vehicle;

import java.util.function.Supplier;

import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BoatMod extends Boat {
	private static final EntityDataAccessor<Integer> MODDED_DATA_ID_TYPE = SynchedEntityData.defineId(BoatMod.class, EntityDataSerializers.INT);

	public BoatMod(EntityType<? extends BoatMod> p_38290_, Level p_38291_) {
		super(p_38290_, p_38291_);
	}

	public BoatMod(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
		this(CEntityType.BOAT, p_38293_);
		this.setPos(p_38294_, p_38295_, p_38296_);
		this.xo = p_38294_;
		this.yo = p_38295_;
		this.zo = p_38296_;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MODDED_DATA_ID_TYPE, BoatMod.Type.AUSLDINE.ordinal());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putString("ModdedType", this.getModdedBoatType().getName());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("ModdedType", 8)) {
			this.setModdedType(BoatMod.Type.byName(pCompound.getString("ModdedType")));
		}

	}

	@Override
	protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
		this.lastYd = this.getDeltaMovement().y;
		if (!this.isPassenger()) {
			if (pOnGround) {
				if (this.fallDistance > 3.0F) {
					if (this.status != Boat.Status.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}

					this.causeFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.level.isClientSide && !this.isRemoved()) {
						this.kill();
						if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
							for(int i = 0; i < 3; ++i) {
								this.spawnAtLocation(this.getModdedBoatType().getPlanks());
							}

							for(int j = 0; j < 2; ++j) {
								this.spawnAtLocation(Items.STICK);
							}
						}
					}
				}

				this.fallDistance = 0.0F;
			} else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && pY < 0.0D) {
				this.fallDistance = (float)((double)this.fallDistance - pY);
			}

		}
	}

	@Override
	public Item getDropItem() {
		switch(this.getModdedBoatType()) {
		case AUSLDINE:
		default:
			return CItems.AUSLDINE_BOAT;
		case DEAD_OAK:
			return CItems.DEAD_OAK_BOAT;
		case MONORIS:
			return CItems.MONORIS_BOAT;
		}
	}

	public void setModdedType(BoatMod.Type pBoatType) {
		this.setType(Boat.Type.OAK);
		this.entityData.set(MODDED_DATA_ID_TYPE, pBoatType.ordinal());
	}

	public BoatMod.Type getModdedBoatType() {
		return BoatMod.Type.byId(this.entityData.get(MODDED_DATA_ID_TYPE));
	}

	public static enum Type {
		DEAD_OAK(() ->CBlocks.DEAD_OAK_PLANKS, "dead_oak"),
		AUSLDINE(() ->CBlocks.AUSLDINE_PLANKS, "ausldine"), 
		MONORIS(() ->CBlocks.MONORIS_PLANKS, "monoris");

		private final String name;
		private final Supplier<Block> planks;

		private Type(Supplier<Block> p_38427_, String p_38428_) {
			this.name = p_38428_;
			this.planks = p_38427_;
		}

		public String getName() {
			return this.name;
		}

		public Block getPlanks() {
			return this.planks.get();
		}

		public String toString() {
			return this.name;
		}

		/**
		 * Get a boat type by it's enum ordinal
		 */
		public static BoatMod.Type byId(int pId) {
			BoatMod.Type[] aboat$type = values();
			if (pId < 0 || pId >= aboat$type.length) {
				pId = 0;
			}

			return aboat$type[pId];
		}

		public static BoatMod.Type byName(String pName) {
			BoatMod.Type[] aboat$type = values();

			for(int i = 0; i < aboat$type.length; ++i) {
				if (aboat$type[i].getName().equals(pName)) {
					return aboat$type[i];
				}
			}

			return aboat$type[0];
		}
	}


}