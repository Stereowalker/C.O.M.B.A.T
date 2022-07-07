package com.stereowalker.combat.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.stereowalker.combat.Combat;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

/**
 * Registers this mod's {@link KeyMapping}s.
 */
public class KeyMappings {

	private static final String CATEGORY = "key.category.combat.general";
	public static final KeyMapping NEXT_SPELL = new KeyMapping("key.combat.next_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, CATEGORY);
	public static final KeyMapping PREV_SPELL = new KeyMapping("key.combat.prev_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY);
	public static final KeyMapping RELOAD = new KeyMapping("key.combat.reload", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);
	public static final KeyMapping FIRE = new KeyMapping("key.combat.fire", KeyConflictContext.IN_GAME, InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, CATEGORY);
	public static final KeyMapping PLAYER_LEVELS = new KeyMapping("key.combat.player_levels", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, CATEGORY);
	public static final KeyMapping OPEN_BACK_ITEM = new KeyMapping("key.combat.open_back_item", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
	public static final KeyMapping STORE_ITEM = new KeyMapping("key.combat.store_item", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY);
	public static final KeyMapping PRONE = new ToggleKeyMapping("key.combat.prone", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_LEFT_ALT, CATEGORY, () -> {
		return Combat.CLIENT_CONFIG.prone_toggle;
	});
}
