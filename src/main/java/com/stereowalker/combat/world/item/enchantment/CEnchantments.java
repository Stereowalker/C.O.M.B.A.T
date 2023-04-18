package com.stereowalker.combat.world.item.enchantment;

import com.stereowalker.unionlib.core.registries.RegistryHolder;
import com.stereowalker.unionlib.core.registries.RegistryObject;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@RegistryHolder(namespace = "combat", registry = Enchantment.class)
public class CEnchantments {
	private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
	private static final EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
	@RegistryObject("sun_shield")
	public static final Enchantment SUN_SHIELD = new SunShieldEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.HEAD});
	@RegistryObject("magma_walker")
	public static final Enchantment MAGMA_WALKER = new MagmaWalkerEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.FEET});
	@RegistryObject("vampire_slayer")
	public static final Enchantment VAMPIRE_SLAYER = new VampireSlayerEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("incendiary")
	public static final Enchantment INCENDIARY = new IncendiaryEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("boomerang")
	public static final Enchantment BOOMERANG = new BoomerangEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("penetration")
	public static final Enchantment PENETRATION = new PenetrationEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("hollowed_flight")
	public static final Enchantment HOLLOWED_FLIGHT = new HollowedFlightEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("short_throw")
	public static final Enchantment SHORT_THROW = new ShortThrowEnchantment(Rarity.COMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("quick_draw")
	public static final Enchantment QUICK_DRAW = new QuickDrawEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("container_extraction")
	public static final Enchantment CONTAINER_EXTRACTION = new ContainerExtractionEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("soul_sealing")
	public static final Enchantment SOUL_SEALING = new SoulSealingEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	@RegistryObject("ice_aspect")
	public static final Enchantment ICE_ASPECT = new IceAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
	@RegistryObject("burning_thorns")
	public static final Enchantment BURNING_THORNS = new BurningThornsEnchantment(Rarity.VERY_RARE, ARMOR_SLOTS);
	//Shield
	@RegistryObject("spikes")
	public static final Enchantment SPIKES = new SpikesEnchantment(Rarity.RARE, HAND_SLOTS);
	@RegistryObject("burning_spikes")
	public static final Enchantment BURNING_SPIKES = new BurningSpikesEnchantment(Rarity.RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	//Wand
	@RegistryObject("cooldown_reduction")
	public static final Enchantment COOLDOWN_REDUCTION = new CooldownReductionEnchantment(Rarity.UNCOMMON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	@RegistryObject("no_cooldown")
	public static final Enchantment NO_COOLDOWN = new NoCooldownEnchantment(Rarity.VERY_RARE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	//All
	@RegistryObject("retaining")
	public static final Enchantment RETAINING = new RetainingEnchantment(Rarity.RARE, EquipmentSlot.values());
	@RegistryObject("restoring")
	public static final Enchantment RESTORING = new RestoringEnchantment(Rarity.RARE, EquipmentSlot.values());
	//Armor
	@RegistryObject("absorption")
	public static final Enchantment ABSORPTION = new AbsorptionEnchantment(Rarity.VERY_RARE, ARMOR_SLOTS);
}
