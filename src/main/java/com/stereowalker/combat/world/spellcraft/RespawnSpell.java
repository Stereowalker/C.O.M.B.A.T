package com.stereowalker.combat.world.spellcraft;

import com.stereowalker.combat.api.world.spellcraft.Rank;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RespawnSpell extends Spell {


	protected RespawnSpell(SpellCategory category, Rank tier, CastType type, float cost, int cooldown, int castTime) {
		super(category, tier, type, cost, cooldown, castTime);
	}

	@Override
	public boolean spellProgram(LivingEntity caster, double strength, Vec3 location, InteractionHand hand) {
		if (caster instanceof Player) {
			if(!caster.level.isClientSide) {
				ServerPlayer player = (ServerPlayer) caster;
				ServerLevel respawnServerworld = ((ServerLevel)caster.level).getServer().getLevel(player.getRespawnDimension());
				int x, y, z;
				
				
				if (caster.level.dimension().equals(player.getRespawnDimension()) && player.getRespawnPosition() != null) {
					x = player.getRespawnPosition().getX();
					y = player.getRespawnPosition().getY();
					z = player.getRespawnPosition().getZ();
					caster.teleportTo(x, y, z);
					return true;
				}
				else if (!caster.level.dimension().equals(player.getRespawnDimension()) && player.getRespawnPosition() != null){
					x = player.getRespawnPosition().getX();
					y = player.getRespawnPosition().getY();
					z = player.getRespawnPosition().getZ();
					caster.changeDimension(respawnServerworld);
					caster.teleportTo(x, y, z);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Component getFailedMessage(LivingEntity caster) {
		return new TranslatableComponent("Your bed seems to be missing");
	}

}
