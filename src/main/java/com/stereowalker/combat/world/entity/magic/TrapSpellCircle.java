package com.stereowalker.combat.world.entity.magic;

import java.util.List;

import com.stereowalker.combat.api.world.spellcraft.AbstractTrapSpell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class TrapSpellCircle extends AbstractSpellCircle {

	public TrapSpellCircle(EntityType<? extends TrapSpellCircle> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public TrapSpellCircle(LivingEntity entity, Level worldIn, double x, double y, double z) {
		this(CEntityType.TRAP_SPELL_CIRCLE, worldIn);
		this.setOwnerId(entity.getUUID());
		this.setPos(x, y, z);
		this.setRadius(3.0F);
		this.setEffectHeight(0.05F);
	}

	@Override
	public void tick() {
		this.setYRot(this.getYRot()+1);
		List<LivingEntity> affectedEntities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
		this.setEffectHeight(1.0F);
		if (!affectedEntities.isEmpty()) {
			//				this.getSpell().getSpell().setLocation(this.getPositionVec());
			LivingEntity living = affectedEntities.get(0);
			if (this.level.isClientSide == this.getSpell().getSpell().isClientSpell()) {
				if (this.getSpell().executeExtensionSpell(this.getOwner(), living)) {
					this.discard();
				}
			}
		}
	}

	public void setSpell(SpellInstance spell) {
		if (spell.getSpell() instanceof AbstractTrapSpell) {
			super.setSpell(spell);
		}
	}
}
