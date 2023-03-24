package com.stereowalker.combat.config;

import com.stereowalker.unionlib.config.ConfigObject;
import com.stereowalker.unionlib.config.UnionConfig;

import net.minecraftforge.fml.config.ModConfig.Type;

@UnionConfig(folder = "Combat Config", name = "client", translatableName = "config.combat.client.file", autoReload = true)
public class ClientConfig implements ConfigObject {
	@UnionConfig.Entry(name = "Use Custom Main Menu", type = Type.CLIENT)
	@UnionConfig.Comment(comment = {"Would you prefer to use the mods main menu panorama"})
	public boolean toggle_custom_main_menu = true;
	
	@UnionConfig.Entry(name = "Enable Enchantment Descriptions", type = Type.CLIENT)
	@UnionConfig.Comment(comment = {"Disabling This Would Prevent Enchantment Descriptions From Showing on Enchanted Books"})
	public boolean enable_enchantment_descriptions = true;
	
	@UnionConfig.Entry(name = "Should Prone Be Toggled", type = Type.CLIENT)
	@UnionConfig.Comment(comment = {"Enabling this will allow you to toggle the prone keybind"})
	public boolean prone_toggle = true;
	
	@UnionConfig.Entry(name = "Use Older Mana Bar", type = Type.CLIENT)
	@UnionConfig.Comment(comment = {"This reverts to the previous mana bar style from the new mana star style"})
	public boolean use_older_mana_bar = false;
}
