package com.stereowalker.combat.enchantment;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.registries.IForgeRegistry;

public class CEnchantments {
	public static List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
	
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
	private static final EquipmentSlotType[] HAND_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND};
	public static final Enchantment SUN_SHIELD = register("sun_shield", new SunShieldEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.HEAD}));
	public static final Enchantment MAGMA_WALKER = register("magma_walker", new MagmaWalkerEnchantment(Rarity.RARE, new EquipmentSlotType[] {EquipmentSlotType.FEET}));
	public static final Enchantment VAMPIRE_SLAYER = register("vampire_slayer", new VampireSlayerEnchantment(Rarity.VERY_RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment INCENDIARY = register("incendiary", new IncendiaryEnchantment(Rarity.RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment BOOMERANG = register("boomerang", new BoomerangEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment PENETRATION = register("penetration", new PenetrationEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment HOLLOWED_FLIGHT = register("hollowed_flight", new HollowedFlightEnchantment(Rarity.VERY_RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment SHORT_THROW = register("short_throw", new ShortThrowEnchantment(Rarity.COMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment QUICK_DRAW = register("quick_draw", new QuickDrawEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment CONTAINER_EXTRACTION = register("container_extraction", new ContainerExtractionEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment SOUL_SEALING = register("soul_sealing", new SoulSealingEnchantment(Rarity.RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND}));
	public static final Enchantment ICE_ASPECT = register("ice_aspect", new IceAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
	public static final Enchantment BURNING_THORNS = register("burning_thorns", new BurningThornsEnchantment(Rarity.VERY_RARE, ARMOR_SLOTS));
	//Shield
	public static final Enchantment SPIKES = register("spikes", new SpikesEnchantment(Rarity.RARE, HAND_SLOTS));
	public static final Enchantment BURNING_SPIKES = register("burning_spikes", new SpikesEnchantment(Rarity.RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
	//Wand
	public static final Enchantment COOLDOWN_REDUCTION = register("cooldown_reduction", new CooldownReductionEnchantment(Rarity.UNCOMMON, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
	public static final Enchantment NO_COOLDOWN = register("no_cooldown", new NoCooldownEnchantment(Rarity.VERY_RARE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}));
	//All
	public static final Enchantment RETAINING = register("retaining", new RestoringEnchantment(Rarity.RARE, EquipmentSlotType.values()));
	public static final Enchantment RESTORING = register("restoring", new RetainingEnchantment(Rarity.RARE, EquipmentSlotType.values()));
	//Armor
	public static final Enchantment ABSORPTION = register("absorption", new AbsorptionEnchantment(Rarity.VERY_RARE, ARMOR_SLOTS));
	
	
	public static Enchantment register(String name, Enchantment enchantment) {
		enchantment.setRegistryName(Combat.getInstance().location(name));
		ENCHANTMENTS.add(enchantment);
		return enchantment;
	}
	
	public static void registerAll(IForgeRegistry<Enchantment> registry) {
		for(Enchantment enchantment : ENCHANTMENTS) {
			registry.register(enchantment);
			Combat.debug("Enchantment: \""+enchantment.getRegistryName().toString()+"\" registered");
		}
		Combat.debug("All Enchantments Registered");
	}
}
