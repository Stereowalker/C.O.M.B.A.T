//package com.stereowalker.combat.config;
//
//import java.util.List;
//
//import com.stereowalker.combat.Combat;
//
//import net.minecraftforge.common.ForgeConfigSpec;
//
//public class CombatValues {
//	public static class ConfigValue<T> {
//		ForgeConfigSpec.ConfigValue<T> valu;
//		T defaultValue;
//		
//		public ConfigValue(ForgeConfigSpec.ConfigValue<T> valu, T def) {
//			this.valu = valu;
//			this.defaultValue = def;
//		}
//		
//		public static <T> ConfigValue<T> build(ForgeConfigSpec.ConfigValue<T> valu) {
//			return new ConfigValue<T>(valu, null);
//		}
//		
//		public T get() {
//			return Combat.disableConfig() ? defaultValue : valu.get();
//		}
//		
//		public void set(T value) {
//			if (!Combat.disableConfig()) 
//				valu.set(value);
//		}
//		
//		public List<String> getPath() {
//			return valu.getPath();
//		}
//	}
//	
//	//TODO: Implement Default Values To Fall Back On
//	public static class BooleanValue extends ConfigValue<Boolean> {
//		
//		public BooleanValue(ForgeConfigSpec.BooleanValue bool, boolean def) {
//			super(bool, def);
//		}
//		
//		public static BooleanValue build(ForgeConfigSpec.BooleanValue bool) {
//			return new BooleanValue(bool, true);
//		}
//	}
//	
//	public static class DoubleValue extends ConfigValue<Double> {
//		
//		public DoubleValue(ForgeConfigSpec.DoubleValue doub, double def) {
//			super(doub, def);
//		}
//		
//		public static DoubleValue build(ForgeConfigSpec.DoubleValue doub) {
//			return new DoubleValue(doub, 1.0D);
//		}
//	}
//	
//	public static class IntValue extends ConfigValue<Integer> {
//		
//		public IntValue(ForgeConfigSpec.IntValue in, int def) {
//			super(in, def);
//		}
//		
//		public static IntValue build(ForgeConfigSpec.IntValue doub) {
//			return new IntValue(doub, 1);
//		}
//	}
//	
//	public static class EnumValue<T extends Enum<T>> extends ConfigValue<T> {
//		
//		public EnumValue(ForgeConfigSpec.EnumValue<T> enumV, T def) {
//			super(enumV, def);
//		}
//		
//		public static <T extends Enum<T>> EnumValue<T> build(ForgeConfigSpec.EnumValue<T> valu) {
//			return new EnumValue<T>(valu, null);
//		}
//	}
//}
