package com.stereowalker.old.combat.config;

import com.stereowalker.unionlib.config.UnionValues;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
	//Customizations
	public UnionValues.BooleanValue toggle_custom_main_menu;
	public UnionValues.BooleanValue enable_enchantment_descriptions;
	public UnionValues.BooleanValue prone_toggle;

	ClientConfig(ForgeConfigSpec.Builder client) {
		if (client != null) {
			toggle_custom_main_menu = UnionValues.BooleanValue.build(client
					.comment("Would you prefer to use the mods main menu panorama")
					.define("Customization.Use Custom Main Menu", true));
			enable_enchantment_descriptions = UnionValues.BooleanValue.build(client
					.comment("Disabling This Would Prevent Enchantment Descriptions From Showing on Enchanted Books")
					.define("Customization.Enable Enchantment Descriptions", true));
			prone_toggle = UnionValues.BooleanValue.build(client
					.comment("Enabling this will allow you to toggle the prone keybind")
					.define("Customization.Should Prone Be Toggled?", false));
		}
	}
}
