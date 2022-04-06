package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.world.entity.misc.BlackHole;
import com.stereowalker.combat.world.entity.monster.Minion;
import com.stereowalker.combat.world.entity.monster.SkeletonMinion;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class SummonEntitySpell extends Spell {
	private EntityType<?> entityType;

	protected SummonEntitySpell(SpellCategory category, Rank tier, float cost, int cooldown, EntityType<?> entityType, int castTime) {
		super(category, tier, CastType.BLOCK, cost, cooldown, castTime);
		this.entityType = entityType;
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (caster.level instanceof ServerLevel) {
			BlockPos pos = new BlockPos(location);
			Entity entity = entityType.spawn((ServerLevel)caster.level, null, (Player) caster, pos, MobSpawnType.MOB_SUMMONED, true, true); 
			if(entity instanceof Minion)((Minion<?>) entity).setMasterId(caster.getUUID());
			if(entity instanceof SkeletonMinion) {
				((SkeletonMinion) entity).setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
			}
			if(entity instanceof BlackHole)((BlackHole) entity).setOwnerId(caster.getUUID());
			if(entity != null) {
				return true;
			} else return false;
		}
		return true;
	}
}
