package com.stereowalker.combat.spell;

import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.api.spell.Rank;
import com.stereowalker.combat.api.spell.Spell;
import com.stereowalker.combat.entity.item.MeteorEntity;
import com.stereowalker.combat.entity.item.MeteorEntity.MeteorSize;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MeteorSpell extends Spell {

	protected MeteorSpell(Rank tier, float cost, int cooldown, int castTime) {
		super(SpellCategory.EARTH, tier, CastType.BLOCK, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vector3d location, Hand hand) {
		if (location != null) {
			if(!caster.world.isRemote) {
				if(this.getRank() == Rank.NOVICE) {
					MeteorEntity meteor = new MeteorEntity(caster.world, MeteorSize.SMALL, location.x, location.y + 200.0F, location.z);
					return caster.world.addEntity(meteor);
				}
				if(this.getRank() == Rank.APPRENTICE) {
					MeteorEntity meteor = new MeteorEntity(caster.world, MeteorSize.MEDIUM, location.x, location.y + 200.0F, location.z);
					return caster.world.addEntity(meteor);
				}
				if(this.getRank() == Rank.ADVANCED) {
					MeteorEntity meteor = new MeteorEntity(caster.world, MeteorSize.LARGE, location.x, location.y + 200.0F, location.z);
					return caster.world.addEntity(meteor);
				}
				if(this.getRank() == Rank.GOD) {
					for (int i = 0; i < 30; i++) {
						MeteorEntity meteor0 = new MeteorEntity(caster.world, MeteorSize.SMALL, location.x + (caster.world.rand.nextInt(20) - 10), location.y + 200.0F + caster.world.rand.nextInt(200), location.z + (caster.world.rand.nextInt(20) - 10));
						MeteorEntity meteor1 = new MeteorEntity(caster.world, MeteorSize.MEDIUM, location.x + (caster.world.rand.nextInt(20) - 10), location.y + 200.0F + caster.world.rand.nextInt(200), location.z + (caster.world.rand.nextInt(20) - 10));
						MeteorEntity meteor2 = new MeteorEntity(caster.world, MeteorSize.LARGE, location.x + (caster.world.rand.nextInt(20) - 10), location.y + 200.0F + caster.world.rand.nextInt(200), location.z + (caster.world.rand.nextInt(20) - 10));
						int j = caster.world.rand.nextInt(3);
						if(j == 0) {
							caster.world.addEntity(meteor0);
						}
						if(j == 1) {
							caster.world.addEntity(meteor1);
						}
						if(j == 2) {
							caster.world.addEntity(meteor2);
						}

					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ITextComponent getFailedMessage(LivingEntity caster) {
		return new TranslationTextComponent("No location was selected");
	}

}
