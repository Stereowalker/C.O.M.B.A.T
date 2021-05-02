package com.stereowalker.combat.client.keybindings;

import org.lwjgl.glfw.GLFW;

import com.stereowalker.combat.config.Config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Registers this mod's {@link KeyBinding}s.
 *
 * @author Choonster
 */
public class KeyBindings {

	private static final String CATEGORY = "key.category.combat.general";
	public static final KeyBinding NEXT_SPELL = new KeyBinding("key.combat.next_spell", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_N, CATEGORY);
	public static final KeyBinding PREV_SPELL = new KeyBinding("key.combat.prev_spell", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY);
	public static final KeyBinding RELOAD = new KeyBinding("key.combat.reload", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);
	public static final KeyBinding FIRE = new KeyBinding("key.combat.fire", KeyConflictContext.IN_GAME, InputMappings.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, CATEGORY);
	public static final KeyBinding PLAYER_LEVELS = new KeyBinding("key.combat.player_levels", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, CATEGORY);
	public static final KeyBinding TOGGLE_LIMITER = new KeyBinding("key.combat.toggle_limiter", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY);
	public static final KeyBinding OPEN_BACK_ITEM = new KeyBinding("key.combat.open_back_item", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
	public static final KeyBinding STORE_ITEM = new KeyBinding("key.combat.store_item", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY);
	public static final KeyBinding PRONE = new ToggleableKeyBinding("key.combat.prone", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_LEFT_CONTROL, CATEGORY, () -> {
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
