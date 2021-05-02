package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.entity.item.BlackHoleEntity;
import com.stereowalker.combat.entity.monster.SkeletonMinionEntity;
import com.stereowalker.combat.entity.monster.ZombieMinionEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class SummonEntitySpell extends Spell {
	private EntityType<?> entityType;

	protected SummonEntitySpell(SpellCategory category, Rank tier, float cost, int cooldown, EntityType<?> entityType, int castTime) {
		super(category, tier, CastType.BLOCK, cost, cooldown, castTime);
		this.entityType = entityType;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (caster.world instanceof ServerWorld) {
			BlockPos pos = new BlockPos(location);
			Entity entity = entityType.spawn((ServerWorld)caster.world, null, (PlayerEntity) caster, pos, SpawnReason.MOB_SUMMONED, true, true); 
			if(entity instanceof ZombieMinionEntity)((ZombieMinionEntity) entity).setOwnerId(caster.getUniqueID());
			if(entity instanceof SkeletonMinionEntity) {
				((SkeletonMinionEntity) entity).setOwnerId(caster.getUniqueID());
				((SkeletonMinionEntity) entity).setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
			}
			if(entity instanceof BlackHoleEntity)((BlackHoleEntity) entity).setOwnerId(caster.getUniqueID());
			if(entity != null) {
				return true;
			} else return false;
		}
		return true;
	}
}
