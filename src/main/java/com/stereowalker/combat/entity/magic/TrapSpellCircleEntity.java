package com.stereowalker.combat.entity.magic;

import java.util.List;

import com.stereowalker.combat.api.spell.AbstractTrapSpell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class TrapSpellCircleEntity extends AbstractSpellCircleEntity {

	public TrapSpellCircleEntity(EntityType<? extends TrapSpellCircleEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public TrapSpellCircleEntity(LivingEntity entity, World worldIn, double x, double y, double z) {
		this(CEntityType.TRAP_SPELL_CIRCLE, worldIn);
		this.setOwnerId(entity.getUniqueID());
		this.setPosition(x, y, z);
		this.setRadius(3.0F);
		this.setEffectHeight(0.05F);
	}

	@Override
	public void tick() {
		this.rotationYaw++;
		List<LivingEntity> affectedEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox());
		this.setEffectHeight(1.0F);
		if (!affectedEntities.isEmpty()) {
			//				this.getSpell().getSpell().setLocation(this.getPositionVec());
			LivingEntity living = affectedEntities.get(0);
			if (this.world.isRemote == this.getSpell().getSpell().isClientSpell()) {
				if (this.getSpell().executeExtensionSpell(this.getOwner(), living)) {
					this.remove();
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
