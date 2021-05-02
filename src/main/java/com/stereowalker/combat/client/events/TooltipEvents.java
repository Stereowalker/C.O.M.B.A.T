package com.stereowalker.combat.client.events;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Multimap;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.entity.ai.CAttributes;
import com.stereowalker.combat.item.ItemFilters;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.unionlib.item.AccessoryItem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = "combat", value = Dist.CLIENT)
public class TooltipEvents {

	@OnlyIn(Dist.CLIENT)
	public static void atributeTooltips(ItemStack stack, List<ITextComponent> tooltip, PlayerEntity player) {
		int i = stack.func_242393_J();
		if (ItemStack.func_242394_a(i, ItemStack.TooltipDisplayFlags.MODIFIERS)) {
			for(EquipmentSlotType equipmentslottype : EquipmentSlotType.values()) {
				Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(equipmentslottype);
				if (!multimap.isEmpty()) {
					for(Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
						AttributeModifier attributemodifier = entry.getValue();
						double d0 = attributemodifier.getAmount();
						double d2 = d0;
						boolean flag = false;
						if (player != null) {
							//TODO: Change on UnionLib update
							if (attributemodifier.getID() == UUIDS.MAGIC_STRENGTH_MODIFIER) {
								d0 = d0 + CAttributes.MAGIC_STRENGTH.getDefaultValue();
								//								d0 = d0 + EntityHelper.getBaseAttributeValue(player, CAttributes.MAGIC_STRENGTH);
								//								d0 = d0 + (double)EnchantmentHelper.getModifierForCreature(stack, CreatureAttribute.UNDEFINED);
								flag = true;
							} else if (attributemodifier.getID() == UUIDS.ATTACK_REACH_MODIFIER) {
								d0 = d0 + CAttributes.ATTACK_REACH.getDefaultValue();
								//								d0 = d0 + EntityHelper.getBaseAttributeValue(player, CAttributes.ATTACK_REACH);
								flag = true;
							}
						}

						double d1;
						if (attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
							if (entry.getKey().equals(Attributes.KNOCKBACK_RESISTANCE)) {
								d1 = d0 * 10.0D;
							} else {
								d1 = d0;
							}
						} else {
							d1 = d0 * 100.0D;
						}
						for (int j = 0; j < tooltip.size(); j++) {
							ITextComponent tip = tooltip.get(j);
							ITextComponent newTip = (new StringTextComponent(" ")).appendSibling(new TranslationTextComponent("attribute.modifier.equals." + attributemodifier.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent(entry.getKey().getAttributeName()))).mergeStyle(TextFormatting.DARK_GREEN);
							if (flag) {
								if (d0 > 0.0D) {
									if (tip.equals((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d2), new TranslationTextComponent(entry.getKey().getAttributeName()))).mergeStyle(TextFormatting.BLUE))) {
										tooltip.set(j, newTip);
									}
								} else if (d0 < 0.0D) {
									d1 = d1 * -1.0D;
									if (tip.equals((new TranslationTextComponent("attribute.modifier.take." + attributemodifier.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d2), new TranslationTextComponent(entry.getKey().getAttributeName()))).mergeStyle(TextFormatting.RED))) {
										tooltip.set(j, newTip);
									}
								}
							}

						}

					}
				}
			}
		}
	}

	public static void weaponTooltips(ItemStack stack, List<ITextComponent> tooltip) {
		int state = GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
		if (state == GLFW.GLFW_PRESS) {
			if (ItemFilters.TWO_HANDED_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Two Handed: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" -50% Attack Speed When There Is An Item In Offhand").mergeStyle(TextFormatting.GOLD));
			}
			if (ItemFilters.THROWABLE_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Throwable: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" Can Be Thrown").mergeStyle(TextFormatting.GOLD));
			}
			if (ItemFilters.SINGLE_EDGE_CURVED_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Single-Edge & Curved: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" +50% Attack Damage When Opponent Is Not Wearing Chest Armor").mergeStyle(TextFormatting.GOLD));
			}
			if (ItemFilters.DOUBLE_EDGE_STRAIGHT_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Double-Edge & Straight: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" +25% Attack Speed When There Is No Item In Offhand").mergeStyle(TextFormatting.GOLD));
			}
			if (ItemFilters.EDGELESS_THRUSTING_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Edgeless & Thrusting: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" Critical Hits Deal x3 Damage").mergeStyle(TextFormatting.GOLD));
			}
			if (ItemFilters.HEAVY_WEAPONS.test(stack)) {
				tooltip.add(1, new TranslationTextComponent("Heavy: ").mergeStyle(TextFormatting.DARK_GRAY));
				tooltip.add(2, new TranslationTextComponent(" Attacks Have A Chance Of Knockback").mergeStyle(TextFormatting.GOLD));
			}
		}
		else {
			tooltip.add(1, ((new TranslationTextComponent("Press ").mergeStyle(TextFormatting.DARK_GRAY))
					.appendSibling((new TranslationTextComponent("[Left Shift]").mergeStyle(TextFormatting.GOLD)))).appendSibling(new TranslationTextComponent(" For More Info")));
		}
	}

	@SuppressWarnings("deprecation")
	public static void enchantmentTooltips(ItemStack stack, List<ITextComponent> tooltip) {
		ListNBT enchantList = null;
		if (stack.getItem() instanceof EnchantedBookItem) enchantList = EnchantedBookItem.getEnchantments(stack);
		if (stack.isEnchanted()) enchantList = stack.getEnchantmentTagList();
		//Tooltip for Enchanted Books
		if (enchantList != null) {
			int state = GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
			if (state == GLFW.GLFW_PRESS) {
				for(int i = 0; i < enchantList.size(); ++i) {
					CompoundNBT compoundnbt = enchantList.getCompound(i);
					Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundnbt.getString("id"))).ifPresent((enc) -> {
						TextFormatting col = enc.isCurse() ? TextFormatting.RED : TextFormatting.GRAY;
						for (int j = 0; j < tooltip.size(); j++) {
							ITextComponent tip = tooltip.get(j);
							ITextComponent description = new TranslationTextComponent(enc.getName()+".desc");
							if (description.getString().equals(enc.getName()+".desc")) {
								List<ModInfo> mods = Collections.unmodifiableList(ModList.get().getMods());
								for (ModInfo mod : mods) {
									if (mod.getModId().equals(enc.getRegistryName().getNamespace())) {
										description = new StringTextComponent("Ask the author of ")
												.appendSibling(new StringTextComponent(mod.getDisplayName()).mergeStyle(TextFormatting.BLUE))
												.appendSibling(new StringTextComponent(" for the description. The translation key is ")
														.appendSibling(new StringTextComponent(enc.getName()+".desc").mergeStyle(TextFormatting.BLUE)));
									}
								}
							}
							if (tip.equals(enc.getDisplayName(compoundnbt.getInt("lvl")))) {
								tooltip.set(j, (((IFormattableTextComponent)enc.getDisplayName(compoundnbt.getInt("lvl"))).mergeStyle(TextFormatting.LIGHT_PURPLE))
										.appendString(": ").appendSibling(enc.getMaxLevel() > 1 ? ((IFormattableTextComponent) description).mergeStyle(col).appendString(". Max Level ").appendSibling(new TranslationTextComponent("enchantment.level." + enc.getMaxLevel()).mergeStyle(TextFormatting.GREEN)) : ((IFormattableTextComponent) description).mergeStyle(col)));
							}
						}
					});
				}
			}
			else {
				tooltip.add(1, ((new TranslationTextComponent("Press ").mergeStyle(TextFormatting.GRAY))
						.appendSibling((new TranslationTextComponent("[Left Shift]").mergeStyle(TextFormatting.LIGHT_PURPLE)))).appendSibling(new TranslationTextComponent(" For Enchantment Description")));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void accessoryTooltip(ItemStack stack, List<ITextComponent> tooltip) {
		if (((AccessoryItem)stack.getItem()).accessoryInformation() != null) {
			int state = GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT);
			if (state == GLFW.GLFW_PRESS) {
				tooltip.add(1, ((AccessoryItem)stack.getItem()).accessoryInformation());
			}
			else {
				tooltip.add(1, ((new TranslationTextComponent("Press ").mergeStyle(TextFormatting.GRAY))
						.appendSibling((new TranslationTextComponent("[Left Shift]").mergeStyle(TextFormatting.GREEN)))).appendSibling(new TranslationTextComponent(" For More Info")));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void tooltips(ItemTooltipEvent event) {
		atributeTooltips(event.getItemStack(), event.getToolTip(), event.getPlayer());

		if (Config.CLIENT.enable_enchantment_descriptions.get()) {
			enchantmentTooltips(event.getItemStack(), event.getToolTip());
		}

		if (ItemFilters.MELEE_WEAPONS.test(event.getItemStack())) {
			weaponTooltips(event.getItemStack(), event.getToolTip());
		}

		if (event.getItemStack().getItem() instanceof AccessoryItem) {
			accessoryTooltip(event.getItemStack(), event.getToolTip());
		}
	}
}
