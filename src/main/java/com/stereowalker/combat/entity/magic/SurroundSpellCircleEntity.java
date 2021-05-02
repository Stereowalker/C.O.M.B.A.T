package com.stereowalker.combat.entity.magic;

import java.util.List;

import com.stereowalker.combat.api.spell.AbstractSurroundSpell;
import com.stereowalker.combat.api.spell.SpellInstance;
import com.stereowalker.combat.entity.CEntityType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class SurroundSpellCircleEntity extends AbstractSpellCircleEntity {
	private int ticks = 0;

	public SurroundSpellCircleEntity(EntityType<? extends SurroundSpellCircleEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public SurroundSpellCircleEntity(LivingEntity entity, World worldIn, double x, double y, double z) {
		this(CEntityType.SURROUND_SPELL_CIRCLE, worldIn);
		this.setOwnerId(entity.getUniqueID());
		this.setPosition(x, y, z);
		this.setRadius(3.0F);
		this.setEffectHeight(0.05F);
	}

	@Override
	public void tick() {
		ticks++;
		int maxLife = this.getSpell().getSpell().getCooldown()*20;
		this.setRadius(10.0F);
		if (ticks >= maxLife) {
			this.remove();
		}
		this.rotationYaw++;
//		if (this.getSpell().getSpell() instanceof AbstractSurroundSpell) {

		if(!this.world.isRemote && this.getOwner() != null) this.setPositionAndUpdate(this.getOwner().getPosX(), this.getOwner().getPosY(), this.getOwner().getPosZ());
//		this.getSpell().getSpell().setCaster((LivingEntity) this.getOwner());
		List<LivingEntity> affectedEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox());
		this.setEffectHeight(1.0F);
		int esecs = ticks%((AbstractSurroundSpell) this.getSpell().getSpell()).getEffectInterval();
		if (esecs == 0) {
			for (LivingEntity living : affectedEntities) {
//				if (this.getSpell().getSpell().isClient() == this.getSpell().getSpell().isClientSpell()) {
					this.getSpell().executeExtensionSpell(this.getOwner(), living);
//				}
			}
		}
//		}
	}
	
	public void setSpell(SpellInstance spell) {
		if (spell.getSpell() instanceof AbstractSurroundSpell) {
			super.setSpell(spell);
		}
	}

}
