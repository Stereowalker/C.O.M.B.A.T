package com.stereowalker.combat.world.item.enchantment;

import java.util.ArrayList;
import java.util.List;

import com.stereowalker.combat.Combat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.IForgeRegistry;

public class CEnchantments {
	public static List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
	
	private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
	private static final EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
	public static final Enchantment SUN_SHIELD = register("sun_shield", new SunShieldEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.HEAD}));
	public static final Enchantment MAGMA_WALKER = register("magma_walker", new MagmaWalkerEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.FEET}));
	public static final Enchantment VAMPIRE_SLAYER = register("vampire_slayer", new VampireSlayerEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment INCENDIARY = register("incendiary", new IncendiaryEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment BOOMERANG = register("boomerang", new BoomerangEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment PENETRATION = register("penetration", new PenetrationEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment HOLLOWED_FLIGHT = register("hollowed_flight", new HollowedFlightEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment SHORT_THROW = register("short_throw", new ShortThrowEnchantment(Rarity.COMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment QUICK_DRAW = register("quick_draw", new QuickDrawEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment CONTAINER_EXTRACTION = register("container_extraction", new ContainerExtractionEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment SOUL_SEALING = register("soul_sealing", new SoulSealingEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
	public static final Enchantment ICE_ASPECT = register("ice_aspect", new IceAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
	public static final Enchantment BURNING_THORNS = register("burning_thorns", new BurningThornsEnchantment(Rarity.VERY_RARE, ARMOR_SLOTS));
	//Shield
	public static final Enchantment SPIKES = register("spikes", new SpikesEnchantment(Rarity.RARE, HAND_SLOTS));
	public static final Enchantment BURNING_SPIKES = register("burning_spikes", new BurningSpikesEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
	//Wand
	public static final Enchantment COOLDOWN_REDUCTION = register("cooldown_reduction", new CooldownReductionEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
	public static final Enchantment NO_COOLDOWN = register("no_cooldown", new NoCooldownEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
	//All
	public static final Enchantment RETAINING = register("retaining", new RetainingEnchantment(Rarity.RARE, EquipmentSlot.values()));
	public static final Enchantment RESTORING = register("restoring", new RestoringEnchantment(Rarity.RARE, EquipmentSlot.values()));
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
