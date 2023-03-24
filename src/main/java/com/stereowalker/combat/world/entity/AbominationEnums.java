package com.stereowalker.combat.world.entity;

public class AbominationEnums {
	public enum AbominationType {
		SPEEDY, STRENGTH, ARMOR, HEALTHY, NORMAL;

		/**
		 * Get a category type by it's enum ordinal
		 */
		public static AbominationType byId(int id) {
			AbominationType[] category = values();
			if (id < 0 || id >= category.length) {
				id = 0;
			}
			return category[id];
		}
	}
	
	public enum AbominationRank {
		NORMAL, RARE;

		/**
		 * Get a category type by it's enum ordinal
		 */
		public static AbominationRank byId(int id) {
			AbominationRank[] category = values();
			if (id < 0 || id >= category.length) {
				id = 0;
			}
			return category[id];
		}
	}
}
