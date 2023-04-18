package com.stereowalker.combat.client.events;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Multimap;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.tags.ItemCTags;
import com.stereowalker.combat.util.UUIDS;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.combat.world.item.ItemFilters;
import com.stereowalker.unionlib.world.item.AccessoryItem;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

@OnlyIn(Dist.CLIENT)
public class TooltipEvents {

	@OnlyIn(Dist.CLIENT)
	public static void atributeTooltips(ItemStack stack, List<Component> tooltip, Player player) {
		int i = stack.getHideFlags();
		if (ItemStack.shouldShowInTooltip(i, ItemStack.TooltipPart.MODIFIERS)) {
			for(EquipmentSlot equipmentslottype : EquipmentSlot.values()) {
				Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(equipmentslottype);
				if (!multimap.isEmpty()) {
					for(Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
						AttributeModifier attributemodifier = entry.getValue();
						double d0 = attributemodifier.getAmount();
						double d2 = d0;
						boolean flag = false;
						if (player != null) {
							//TODO: Change on UnionLib update
							if (attributemodifier.getId() == UUIDS.MAGIC_STRENGTH_MODIFIER) {
								d0 = d0 + CAttributes.MAGIC_STRENGTH.getDefaultValue();
								//								d0 = d0 + EntityHelper.getBaseAttributeValue(player, CAttributes.MAGIC_STRENGTH);
								//								d0 = d0 + (double)EnchantmentHelper.getModifierForCreature(stack, CreatureAttribute.UNDEFINED);
								flag = true;
							} else if (attributemodifier.getId() == UUIDS.ATTACK_REACH_MODIFIER) {
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
							Component tip = tooltip.get(j);
							Component newTip = (Component.literal(" ")).append(Component.translatable("attribute.modifier.equals." + attributemodifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId()))).withStyle(ChatFormatting.DARK_GREEN);
							if (flag) {
								if (d0 > 0.0D) {
									if (tip.equals((Component.translatable("attribute.modifier.plus." + attributemodifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d2), Component.translatable(entry.getKey().getDescriptionId()))).withStyle(ChatFormatting.BLUE))) {
										tooltip.set(j, newTip);
									}
								} else if (d0 < 0.0D) {
									d1 = d1 * -1.0D;
									if (tip.equals((Component.translatable("attribute.modifier.take." + attributemodifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d2), Component.translatable(entry.getKey().getDescriptionId()))).withStyle(ChatFormatting.RED))) {
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

	public static void weaponTooltips(ItemStack stack, List<Component> tooltip) {
		int state = GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
		if (state == GLFW.GLFW_PRESS) {
			if (ItemFilters.TWO_HANDED_WEAPONS.test(stack)) {
				tooltip.add(1, Component.translatable("Two Handed: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" -50% Attack Speed When There Is An Item In Offhand").withStyle(ChatFormatting.GOLD));
			}
			if (ItemFilters.THROWABLE_WEAPONS.test(stack)) {
				tooltip.add(1, Component.translatable("Throwable: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" Can Be Thrown").withStyle(ChatFormatting.GOLD));
			}
			if (stack.is(ItemCTags.SINGLE_EDGE_CURVED_WEAPON)) {
				tooltip.add(1, Component.translatable("Single-Edge & Curved: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" +50% Attack Damage When Opponent Is Not Wearing Chest Armor").withStyle(ChatFormatting.GOLD));
			}
			if (stack.is(ItemCTags.DOUBLE_EDGE_STRAIGHT_WEAPON)) {
				tooltip.add(1, Component.translatable("Double-Edge & Straight: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" +25% Attack Speed When There Is No Item In Offhand").withStyle(ChatFormatting.GOLD));
			}
			if (stack.is(ItemCTags.EDGELESS_THRUSTING_WEAPON)) {
				tooltip.add(1, Component.translatable("Edgeless & Thrusting: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" Critical Hits Deal x3 Damage").withStyle(ChatFormatting.GOLD));
			}
			if (ItemFilters.HEAVY_WEAPONS.test(stack)) {
				tooltip.add(1, Component.translatable("Heavy: ").withStyle(ChatFormatting.DARK_GRAY));
				tooltip.add(2, Component.translatable(" Attacks Have A Chance Of Knockback").withStyle(ChatFormatting.GOLD));
			}
		}
		else {
			tooltip.add(1, ((Component.translatable("Press ").withStyle(ChatFormatting.DARK_GRAY))
					.append((Component.translatable("[Left Shift]").withStyle(ChatFormatting.GOLD)))).append(Component.translatable(" For More Info")));
		}
	}

	@SuppressWarnings("deprecation")
	public static void enchantmentTooltips(ItemStack stack, List<Component> tooltip) {
		ListTag enchantList = null;
		if (stack.getItem() instanceof EnchantedBookItem) enchantList = EnchantedBookItem.getEnchantments(stack);
		if (stack.isEnchanted()) enchantList = stack.getEnchantmentTags();
		//Tooltip for Enchanted Books
		if (enchantList != null) {
			int state = GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
			if (state == GLFW.GLFW_PRESS) {
				for(int i = 0; i < enchantList.size(); ++i) {
					CompoundTag compoundnbt = enchantList.getCompound(i);
					BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(compoundnbt.getString("id"))).ifPresent((enc) -> {
						ChatFormatting col = enc.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY;
						for (int j = 0; j < tooltip.size(); j++) {
							Component tip = tooltip.get(j);
							Component description = Component.translatable(enc.getDescriptionId()+".desc");
							if (description.getString().equals(enc.getDescriptionId()+".desc")) {
								List<IModInfo> mods = Collections.unmodifiableList(ModList.get().getMods());
								for (IModInfo mod : mods) {
									if (mod.getModId().equals(BuiltInRegistries.ENCHANTMENT.getKey(enc).getNamespace())) {
										description = Component.literal("Ask the author of ")
												.append(Component.literal(mod.getDisplayName()).withStyle(ChatFormatting.BLUE))
												.append(Component.literal(" for the description. The translation key is ")
														.append(Component.literal(enc.getDescriptionId()+".desc").withStyle(ChatFormatting.BLUE)));
									}
								}
							}
							if (tip.equals(enc.getFullname(compoundnbt.getInt("lvl")))) {
								tooltip.set(j, (((MutableComponent)enc.getFullname(compoundnbt.getInt("lvl"))).withStyle(ChatFormatting.LIGHT_PURPLE))
										.append(": ").append(enc.getMaxLevel() > 1 ? ((MutableComponent) description).withStyle(col).append(". Max Level ").append(Component.translatable("enchantment.level." + enc.getMaxLevel()).withStyle(ChatFormatting.GREEN)) : ((MutableComponent) description).withStyle(col)));
							}
						}
					});
				}
			}
			else {
				tooltip.add(1, ((Component.translatable("Press ").withStyle(ChatFormatting.GRAY))
						.append((Component.translatable("[Left Shift]").withStyle(ChatFormatting.LIGHT_PURPLE)))).append(Component.translatable(" For Enchantment Description")));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void accessoryTooltip(ItemStack stack, List<Component> tooltip) {
		if (((AccessoryItem)stack.getItem()).accessoryInformation() != null) {
			int state = GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
			if (state == GLFW.GLFW_PRESS) {
				tooltip.add(1, ((AccessoryItem)stack.getItem()).accessoryInformation());
			}
			else {
				tooltip.add(1, ((Component.translatable("Press ").withStyle(ChatFormatting.GRAY))
						.append((Component.translatable("[Left Shift]").withStyle(ChatFormatting.GREEN)))).append(Component.translatable(" For More Info")));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void tooltips(ItemStack itemStack, Player player, List<Component> list, TooltipFlag flags) {
		atributeTooltips(itemStack, list, player);

		if (Combat.CLIENT_CONFIG.enable_enchantment_descriptions) {
			enchantmentTooltips(itemStack, list);
		}

		if (ItemFilters.MELEE_WEAPONS.test(itemStack)) {
			weaponTooltips(itemStack, list);
		}

		if (itemStack.getItem() instanceof AccessoryItem) {
			accessoryTooltip(itemStack, list);
		}
	}
}
