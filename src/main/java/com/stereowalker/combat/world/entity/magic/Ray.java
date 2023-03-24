package com.stereowalker.combat.world.entity.magic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.world.spellcraft.AbstractRaySpell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.entity.CEntityType;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Ray extends Entity {
	protected static final EntityDataAccessor<CompoundTag> SPELL = SynchedEntityData.defineId(Ray.class, EntityDataSerializers.COMPOUND_TAG);
	protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(Ray.class, EntityDataSerializers.OPTIONAL_UUID);

	public Ray(EntityType<?> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	public Ray(LivingEntity entity, Level worldIn, double x, double y, double z) {
		this(CEntityType.RAY, worldIn);
		this.setOwnerId(entity.getUUID());
		this.setPos(x, y, z);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(SPELL, new CompoundTag());
		this.entityData.define(OWNER_UNIQUE_ID, Optional.empty());
	}

	public void setSpell(SpellInstance spell) {
		if (spell.getSpell() instanceof AbstractRaySpell) {
			this.entityData.set(SPELL, spell.write(new CompoundTag()));
		}
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.entityData.get(SPELL));
	}

	/**
	 * Gets the EntityHitResult representing the entity hit
	 */
	@Nullable
	public static EntityHitResult rayTraceEntities(Entity shooter, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter, double distance) {
		Level world = shooter.level;
		double d0 = distance;
		Entity entity = null;
		Vec3 vec3d = null;

		for(Entity entity1 : world.getEntities(shooter, boundingBox, filter)) {
			AABB axisalignedbb = entity1.getBoundingBox().inflate((double)entity1.getPickRadius());
			Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
			if (axisalignedbb.contains(startVec)) {
				if (d0 >= 0.0D) {
					entity = entity1;
					vec3d = optional.orElse(startVec);
					d0 = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vec3 vec3d1 = optional.get();
				double d1 = startVec.distanceToSqr(vec3d1);
				if (d1 < d0 || d0 == 0.0D) {
					if (entity1.getRootVehicle() == shooter.getRootVehicle() && !entity1.canRiderInteract()) {
						if (d0 == 0.0D) {
							entity = entity1;
							vec3d = vec3d1;
						}
					} else {
						entity = entity1;
						vec3d = vec3d1;
						d0 = d1;
					}
				}
			}
		}

		return entity == null ? null : new EntityHitResult(entity, vec3d);
	}

	private int ticks = 0;
	@Override
	public void tick() {
		if (!this.level.isClientSide) {
			int secs = ticks%20;
			if (secs == 0) {
				if ((this.getOwner() instanceof Player && !((Player)this.getOwner()).getAbilities().instabuild) || !(this.getOwner() instanceof Player)) {
					if (!CombatEntityStats.addMana(this.getOwner(), -this.getSpell().getSpell().getCost())) {
						this.discard();
					}
				}
			}
			if (this.getOwner() instanceof Player) {
				Player player = (Player)this.getOwner();
				List<Entity> surroundingEntities = this.level.getEntitiesOfClass(Entity.class, this.getBoundingBox());
				surroundingEntities.remove(this);
				for(Entity entity : surroundingEntities) {
					if(entity instanceof Ray) {
						Ray ray = (Ray)entity;
						if(ray.getOwnerId() == this.getOwnerId() && ray != this) {
							ray.discard();
							this.discard();
						}
					}
				}
				AbstractMagicCastingItem wand;
				InteractionHand hand;
				if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AbstractMagicCastingItem) {
					wand = (AbstractMagicCastingItem)player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
					hand = InteractionHand.MAIN_HAND;
				} else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof AbstractMagicCastingItem) {
					wand = (AbstractMagicCastingItem)player.getItemInHand(InteractionHand.OFF_HAND).getItem();
					hand = InteractionHand.OFF_HAND;
				} else {
					wand = null;
					hand = null;
				}
				if (hand != null && wand != null) {
					ItemStack book = AbstractSpellBookItem.getMainSpellBook(player);
					if (!CombatEntityStats.isHoldFlagActive(player)) {
						this.discard();
					} else if(AbstractSpellBookItem.getMainSpellBookItem(player).getCurrentSpell(book) == this.getSpell().getSpell()) {
						int distance = 3;
						Vec3 vec3d = player.getEyePosition(1.0F);
						Vec3 vec3d1 = player.getViewVector(1.0F).scale((double)distance);
						Vec3 vec3d2 = vec3d.add(vec3d1);
						AABB axisalignedbb = player.getBoundingBox().expandTowards(vec3d1).inflate(1.0D);
						int i = distance * distance;
						Predicate<Entity> predicate = (p_217727_0_) -> {
							return !p_217727_0_.isSpectator() && p_217727_0_.canBeCollidedWith();
						};
						EntityHitResult entityraytraceresult = rayTraceEntities(player, vec3d, vec3d2, axisalignedbb, predicate, (double)i);
						HitResult res;
						if (entityraytraceresult != null) {
							res = entityraytraceresult;
						}
						else res = player.pick(distance, 1.0F, false);
						this.setPos(res.getLocation().x, res.getLocation().y, res.getLocation().z);
						if (res.getType() == HitResult.Type.ENTITY) {
							if (((EntityHitResult)res).getEntity() instanceof LivingEntity && ((EntityHitResult)res).getEntity()!=null) {
								List<LivingEntity> affectedEntities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(new Vec3(this.getX() - 0.25D, this.getY() - 0.25D, this.getZ() - 0.25D), new Vec3(this.getX() + 0.25D, this.getY() + 0.25D, this.getZ() + 0.25D)));
								affectedEntities.remove(this.getOwner());
								for (LivingEntity living : affectedEntities) {
									this.getSpell().executeExtensionSpell(player, living);
								}
							}
						}
					} else {
						this.discard();
					}
					if(player.isShiftKeyDown()) {
						this.discard();
					}
				} else {
					this.discard();
				}
			}
			super.tick();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		UUID uuid;
		if (compound.hasUUID("Owner")) {
			uuid = compound.getUUID("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
		}

		if (uuid != null) {
			try {
				this.setOwnerId(uuid);
			} catch (Throwable throwable) {
			}
		}
		if (compound.contains("Spell")) {
			this.setSpell(SpellInstance.read(compound.getCompound("Spell")));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
		compound.put("Spell", this.getSpell().write(new CompoundTag()));
	}

	@Nullable
	public UUID getOwnerId() {
		return this.entityData.get(OWNER_UNIQUE_ID).orElse((UUID)null);
	}

	public void setOwnerId(@Nullable UUID p_184754_1_) {
		this.entityData.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerId();
			return uuid == null ? null : this.level.getPlayerByUUID(uuid);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}

	public boolean isOwner(LivingEntity entityIn) {
		return entityIn == this.getOwner();
	}

	public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
		return true;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key)
	{
		if (SPELL.equals(key))
		{
			this.setSpell(this.getSpell());
		}
		super.onSyncedDataUpdated(key);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
