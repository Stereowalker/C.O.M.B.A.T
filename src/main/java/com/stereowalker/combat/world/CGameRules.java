package com.stereowalker.combat.world;

import net.minecraft.world.GameRules;

public class CGameRules {
	public static GameRules.RuleKey<GameRules.BooleanValue> DO_INFINITE_MANA;
	public static GameRules.RuleKey<GameRules.BooleanValue> DO_MAGIC_FRIENDLY_FIRE;

	public static void init(){
		CGameRules.DO_INFINITE_MANA = GameRules.register("doInfiniteMana", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
		CGameRules.DO_MAGIC_FRIENDLY_FIRE = GameRules.register("doMagicFriendlyFire", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
	}
}
