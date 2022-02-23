package com.stereowalker.combat.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.stereowalker.old.combat.config.Config;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

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
	public static final KeyMapping TOGGLE_LIMITER = new KeyMapping("key.combat.toggle_limiter", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY);
	public static final KeyMapping OPEN_BACK_ITEM = new KeyMapping("key.combat.open_back_item", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
	public static final KeyMapping STORE_ITEM = new KeyMapping("key.combat.store_item", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY);
	public static final KeyMapping PRONE = new ToggleKeyMapping("key.combat.prone", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_LEFT_CONTROL, CATEGORY, () -> {
		return Config.CLIENT.prone_toggle.get();
	});

	@OnlyIn(Dist.CLIENT)
	public static void registerKeyBindings() {
		ClientRegistry.registerKeyBinding(NEXT_SPELL);
		ClientRegistry.registerKeyBinding(PREV_SPELL);
		ClientRegistry.registerKeyBinding(PLAYER_LEVELS);
		ClientRegistry.registerKeyBinding(TOGGLE_LIMITER);
		ClientRegistry.registerKeyBinding(RELOAD);
		ClientRegistry.registerKeyBinding(FIRE);
		ClientRegistry.registerKeyBinding(OPEN_BACK_ITEM);
		ClientRegistry.registerKeyBinding(STORE_ITEM);
		ClientRegistry.registerKeyBinding(PRONE);
	}
}