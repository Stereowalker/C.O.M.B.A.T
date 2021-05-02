package com.stereowalker.combat.entity.magic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.stereowalker.combat.api.spell.AbstractRaySpell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.entity.CEntityType;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.item.AbstractMagicCastingItem;
import com.stereowalker.combat.item.AbstractSpellBookItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RayEntity extends Entity {
	protected static final DataParameter<CompoundNBT> SPELL = EntityDataManager.createKey(RayEntity.class, DataSerializers.COMPOUND_NBT);
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(RayEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public RayEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public RayEntity(LivingEntity entity, World worldIn, double x, double y, double z) {
		this(CEntityType.RAY, worldIn);
		this.setOwnerId(entity.getUniqueID());
		this.setPosition(x, y, z);
	}

	@Override
	protected void registerData() {
		this.dataManager.register(SPELL, new CompoundNBT());
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
	}

	public void setSpell(SpellInstance spell) {
		if (spell.getSpell() instanceof AbstractRaySpell) {
			this.dataManager.set(SPELL, spell.write(new CompoundNBT()));
		}
	}

	public SpellInstance getSpell() {
		return SpellInstance.read(this.dataManager.get(SPELL));
	}

	/**
	 * Gets the EntityRayTraceResult representing the entity hit
	 */
	@Nullable
	public static EntityRayTraceResult rayTraceEntities(Entity shooter, Vector3d startVec, Vector3d endVec, AxisAlignedBB boundingBox, Predicate<Entity> filter, double distance) {
		World world = shooter.world;
		double d0 = distance;
		Entity entity = null;
		Vector3d vec3d = null;

		for(Entity entity1 : world.getEntitiesInAABBexcluding(shooter, boundingBox, filter)) {
			AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)entity1.getCollisionBorderSize());
			Optional<Vector3d> optional = axisalignedbb.rayTrace(startVec, endVec);
			if (axisalignedbb.contains(startVec)) {
				if (d0 >= 0.0D) {
					entity = entity1;
					vec3d = optional.orElse(startVec);
					d0 = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vector3d vec3d1 = optional.get();
				double d1 = startVec.squareDistanceTo(vec3d1);
				if (d1 < d0 || d0 == 0.0D) {
					if (entity1.getLowestRidingEntity() == shooter.getLowestRidingEntity() && !entity1.canRiderInteract()) {
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

		return entity == null ? null : new EntityRayTraceResult(entity, vec3d);
	}

	private int ticks = 0;
	@Override
	public void tick() {
		int secs = ticks%20;
		if (secs == 0) {
			if ((this.getOwner() instanceof PlayerEntity && !((PlayerEntity)this.getOwner()).abilities.isCreativeMode) || !(this.getOwner() instanceof PlayerEntity)) {
				if (!CombatEntityStats.addMana(this.getOwner(), -this.getSpell().getSpell().getCost())) {
					this.remove();
				}
			}
		}
		if (this.getOwner() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)this.getOwner();
			List<Entity> surroundingEntities = this.world.getEntitiesWithinAABB(Entity.class, this.getBoundingBox());
			surroundingEntities.remove(this);
			for(Entity entity : surroundingEntities) {
				if(entity instanceof RayEntity) {
					RayEntity ray = (RayEntity)entity;
					if(ray.getOwnerId() == this.getOwnerId() && ray != this) {
						ray.remove();
						this.remove();
					}
				}
			}
			AbstractMagicCastingItem wand;
			Hand hand;
			if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AbstractMagicCastingItem) {
				wand = (AbstractMagicCastingItem)player.getHeldItem(Hand.MAIN_HAND).getItem();
				hand = Hand.MAIN_HAND;
			} else if (player.getHeldItem(Hand.OFF_HAND).getItem() instanceof AbstractMagicCastingItem) {
				wand = (AbstractMagicCastingItem)player.getHeldItem(Hand.OFF_HAND).getItem();
				hand = Hand.OFF_HAND;
			} else {
				wand = null;
				hand = null;
			}
			if (hand != null && wand != null) {
				if(AbstractSpellBookItem.getMainSpellBookItem(player).getCurrentSpell(player.getHeldItem(hand)) == this.getSpell().getSpell()) {
					int distance = 3;
					Vector3d vec3d = player.getEyePosition(1.0F);
					Vector3d vec3d1 = player.getLook(1.0F).scale((double)distance);
					Vector3d vec3d2 = vec3d.add(vec3d1);
					AxisAlignedBB axisalignedbb = player.getBoundingBox().expand(vec3d1).grow(1.0D);
					int i = distance * distance;
					Predicate<Entity> predicate = (p_217727_0_) -> {
						return !p_217727_0_.isSpectator() && p_217727_0_.canBeCollidedWith();
					};
					EntityRayTraceResult entityraytraceresult = rayTraceEntities(player, vec3d, vec3d2, axisalignedbb, predicate, (double)i);
					RayTraceResult res;
					if (entityraytraceresult != null) {
						res = entityraytraceresult;
					}
					else res = player.pick(distance, 1.0F, false);
					this.setPosition(res.getHitVec().x, res.getHitVec().y, res.getHitVec().z);
					if (res.getType() == RayTraceResult.Type.ENTITY) {
						if (((EntityRayTraceResult)res).getEntity() instanceof LivingEntity && ((EntityRayTraceResult)res).getEntity()!=null) {
							List<LivingEntity> affectedEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(new Vector3d(this.getPosX() - 0.25D, this.getPosY() - 0.25D, this.getPosZ() - 0.25D), new Vector3d(this.getPosX() + 0.25D, this.getPosY() + 0.25D, this.getPosZ() + 0.25D)));
							affectedEntities.remove(this.getOwner());
							for (LivingEntity living : affectedEntities) {
								this.getSpell().executeExtensionSpell(player, living);
							}
						}
					}
				} else {
					this.remove();
				}
				if(player.isSneaking()) {
					this.remove();
				}
			} else {
				this.remove();
			}
		}
		super.tick();
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		UUID uuid;
		if (compound.hasUniqueId("Owner")) {
			uuid = compound.getUniqueId("Owner");
		} else {
			String s = compound.getString("Owner");
			uuid = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s);
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
	protected void writeAdditional(CompoundNBT compound) {
		if (this.getOwnerId() == null) {
			compound.putString("OwnerUUID", "");
		} else {
			compound.putString("OwnerUUID", this.getOwnerId().toString());
		}
		compound.put("Spell", this.getSpell().write(new CompoundNBT()));
	}

	@Nullable
	public UUID getOwnerId() {
		return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID)null);
	}

	public void setOwnerId(@Nullable UUID p_184754_1_) {
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerId();
			return uuid == null ? null : this.world.getPlayerByUuid(uuid);
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

	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (SPELL.equals(key))
		{
			this.setSpell(this.getSpell());
		}
		super.notifyDataManagerChange(key);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
