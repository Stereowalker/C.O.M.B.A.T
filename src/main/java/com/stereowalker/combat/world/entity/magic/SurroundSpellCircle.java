package com.stereowalker.combat.world.entity.magic;

import java.util.List;

import com.stereowalker.combat.api.world.spellcraft.AbstractSurroundSpell;
import com.stereowalker.combat.api.world.spellcraft.SpellInstance;
import com.stereowalker.combat.world.entity.CEntityType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SurroundSpellCircle extends AbstractSpellCircle {
	private int ticks = 0;

	public SurroundSpellCircle(EntityType<? extends SurroundSpellCircle> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public SurroundSpellCircle(LivingEntity entity, Level worldIn, double x, double y, double z) {
		this(CEntityType.SURROUND_SPELL_CIRCLE, worldIn);
		this.setOwnerId(entity.getUUID());
		this.setPos(x, y, z);
		this.setRadius(3.0F);
		this.setEffectHeight(0.05F);
	}

	@Override
	public void tick() {
		ticks++;
		int maxLife = this.getSpell().getSpell().getCooldown()*20;
		this.setRadius(10.0F);
		if (ticks >= maxLife) {
			this.discard();
		}
		this.setYRot(this.getYRot()+1);
//		if (this.getSpell().getSpell() instanceof AbstractSurroundSpell) {

		if(!this.level.isClientSide && this.getOwner() != null) this.teleportTo(this.getOwner().getX(), this.getOwner().getY(), this.getOwner().getZ());
//		this.getSpell().getSpell().setCaster((LivingEntity) this.getOwner());
		List<LivingEntity> affectedEntities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
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
