package com.stereowalker.combat.event;

import org.lwjgl.glfw.GLFW;

import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.combat.item.GunItem;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class GunEvents {

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void fireGun(PlayerInteractEvent.RightClickItem event) {
		if (event.getPlayer().getHeldItemMainhand().getItem() instanceof GunItem && KeyBindings.FIRE.getKey().getKeyCode() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			event.setCanceled(true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void fireGun(PlayerInteractEvent.LeftClickBlock event) {
		if (event.getPlayer().getHeldItemMainhand().getItem() instanceof GunItem && KeyBindings.FIRE.getKey().getKeyCode() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			event.setCanceled(true);
		}
	}
}
