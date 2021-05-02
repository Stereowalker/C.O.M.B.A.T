package com.stereowalker.combat.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * @author Stereowalker
 *
 */
public class EnergyUtils {
	public enum EnergyType{
		MAGIC_ENERGY("Magic Energy"),
		TECHNO_ENERGY("Techno Energy");
		
		String displayName;
		String abbreviation;
		String name;
		EnergyType(String displayName){
			this.displayName = displayName;
			String[] a = displayName.split(" ");
			String b = "";
			char[] c = displayName.toCharArray();
			String d = "";
			for (int i = 0; i < a.length; i++) {
				b+=a[i];
			}
			for (int i = 0; i < c.length; i++) {
				if (Character.isUpperCase(c[i])) {
					d+=c[i];
				}
			}
			name = b;
			abbreviation = d;
		}
		
		public String getName() {
			return name;
		}
		
		public String getAbbreviation() {
			return abbreviation;
		}
		
		public String getDisplayName() {
			return displayName;
		}
	}
	
	
	/**
	 * Gets the current amount of energy an Item has
	 * @param stack
	 * @param energyType
	 * @return
	 */
	public static int getEnergy(ItemStack stack, EnergyType energyType) {
		return stack.getOrCreateTag().getInt(energyType.getName());
	}
	
	/**
	 * Adds a specified amount of energy to an item.
	 * @param stack
	 * @param energy
	 * @param energyType
	 */
	public static void addEnergyToItem(ItemStack stack, int energy, EnergyType energyType) {
		stack.getOrCreateTag().putInt(energyType.getName(), Math.min(getEnergy(stack, energyType)+energy, getMaxEnergy(stack, energyType)));
	}
	
	/**
	 * Gets the maximum amount of energy an item has
	 * @param stack
	 * @param energyType
	 * @return
	 */
	public static int getMaxEnergy(ItemStack stack, EnergyType energyType) {
		return stack.getOrCreateTag().getInt("Max"+energyType.getName());
	}
	
	/**
	 * Sets the maximum amount of energy an item can hold
	 * @param stack
	 * @param energy
	 * @param energyType
	 */
	public static void setMaxEnergy(ItemStack stack, int energy, EnergyType energyType) {
		stack.getOrCreateTag().putInt("Max"+energyType.getName(), energy);
	}
	
	public static boolean isFull(ItemStack stack, EnergyType energyType) {
		return getEnergy(stack, energyType) >= getMaxEnergy(stack, energyType);
	}
	
	public static boolean isDrained(ItemStack stack, EnergyType energyType) {
		return getEnergy(stack, energyType) <= 0;
	}
	
	public static void fillToTheBrim(ItemStack stack, EnergyType energyType) {
		addEnergyToItem(stack, getMaxEnergy(stack, energyType), energyType);
	}
	
	public static ITextComponent getEnergyComponent(ItemStack stack, EnergyType energyType) {
		return new StringTextComponent(getEnergy(stack, energyType)+" "+energyType.getAbbreviation());
	}
}
