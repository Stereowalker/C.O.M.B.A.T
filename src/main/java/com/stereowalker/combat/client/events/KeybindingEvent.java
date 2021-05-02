package com.stereowalker.combat.client.events;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.config.Config;
import com.stereowalker.combat.item.AbstractMagicCastingItem;
import com.stereowalker.combat.item.AbstractSpellBookItem;
import com.stereowalker.combat.item.GunItem;
import com.stereowalker.combat.item.GunItem.GunType;
import com.stereowalker.combat.network.client.play.CBackItemPacket;
import com.stereowalker.combat.network.client.play.CGunPacket;
import com.stereowalker.combat.network.client.play.CGunPacket.GunAction;
import com.stereowalker.combat.network.client.play.CPronePacket;
import com.stereowalker.combat.network.client.play.CSetLimiterPacket;
import com.stereowalker.combat.network.client.play.CSpellbookNBTPacket;
import com.stereowalker.combat.network.client.play.CStoreItemPacket;
import com.stereowalker.combat.spell.SpellStats;
import com.stereowalker.rankup.client.gui.screen.PlayerLevelsScreen;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Pose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class KeybindingEvent {
	static int fire = 0;
	static int fireR = 0;
	static Minecraft mc = Minecraft.getInstance();
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void keybindTick(ClientTickEvent event) {
//		if (!Minecraft.func_71410_x().func_195544_aj() */)
//			return;
		if (event.phase == Phase.START) {
			ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;
			if (mc.loadingGui == null && (mc.currentScreen == null
					|| mc.currentScreen.passEvents)/* && clientPlayer.getHeldItemMainhand().getItem() != Items.STICK */) {
				ItemStack stackMain = clientPlayer.getHeldItem(Hand.MAIN_HAND);
				ItemStack stackOff = clientPlayer.getHeldItem(Hand.OFF_HAND);

				if (Config.COMMON.load_guns.get()) {
					if(stackMain.getItem() instanceof GunItem && !clientPlayer.isSprinting()) {
						GunItem gun = (GunItem)stackMain.getItem();
						//For Semi Automatic Guns
						if (KeyBindings.FIRE.isKeyDown() && gun.getGunType() == GunType.SEMI_AUTOMATIC) fire += 1;
						if (!(KeyBindings.FIRE.isKeyDown()) && gun.getGunType() == GunType.SEMI_AUTOMATIC) fire = 0;
						if (fire > 5) {
							fire = 5;
						}
						if(fire == 1) {
							Combat.getInstance().channel.sendTo(new CGunPacket(GunAction.FIRE), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
						}
						//For Automatic Guns
						if (KeyBindings.FIRE.isKeyDown() && gun.getGunType() == GunType.AUTOMATIC) {
							fireR++;
							if(fireR > gun.getFireRate()) {
								fireR = 0;
								Combat.getInstance().channel.sendTo(new CGunPacket(GunAction.FIRE), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);

							}
						}

						while (KeyBindings.RELOAD.isPressed()) {
							Combat.getInstance().channel.sendTo(new CGunPacket(GunAction.RELOAD), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
						}
					}
				}

				if (clientPlayer.getHeldItemMainhand().getItem() instanceof AbstractMagicCastingItem || clientPlayer.getHeldItemOffhand().getItem() instanceof AbstractMagicCastingItem) {
					if (!AbstractSpellBookItem.getMainSpellBook(clientPlayer).isEmpty()) {
						int time = clientPlayer.getActiveItemStack() != stackMain ? (clientPlayer.getActiveItemStack() != stackOff ? 0 : (stackOff.getUseDuration() - clientPlayer.getItemInUseCount())) : (stackMain.getUseDuration() - clientPlayer.getItemInUseCount());
						float q = (float)time/(float)AbstractSpellBookItem.getMainSpellBookItem(clientPlayer).getCurrentSpell(clientPlayer).getCastTime();

						while (KeyBindings.NEXT_SPELL.isPressed() && q == 0) {
							changeSpell(clientPlayer, true);
						}

						while (KeyBindings.PREV_SPELL.isPressed() && q == 0) {
							changeSpell(clientPlayer, false);
						}

					}
				}

				if (Config.RPG_COMMON.enableLevelingSystem.get()) {
					while (KeyBindings.PLAYER_LEVELS.isPressed()) {
						mc.displayGuiScreen(new PlayerLevelsScreen(mc));
					}

					while (KeyBindings.TOGGLE_LIMITER.isPressed()) {
						new CSetLimiterPacket().send();
					}
				}

				if (ModHelper.isCuriosLoaded() && !CuriosCompat.getSlotsForType(clientPlayer, "back", 0).isEmpty()) {
					while (KeyBindings.OPEN_BACK_ITEM.isPressed()) {
						new CBackItemPacket().send();
					}
					while (KeyBindings.STORE_ITEM.isPressed()) {
						new CStoreItemPacket().send();
					}
				}
				
//				clientPlayer.setForcedPose(Pose.SWIMMING);
				boolean isProne = KeyBindings.PRONE.isKeyDown();
				new CPronePacket(isProne).send();
				if (isProne && clientPlayer.getForcedPose() != Pose.SWIMMING) clientPlayer.setForcedPose(Pose.SWIMMING);
				else if (!isProne && clientPlayer.getForcedPose() == Pose.SWIMMING) clientPlayer.setForcedPose(null);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void changeSpell(ClientPlayerEntity clientPlayer, boolean forward) {
		ItemStack stack = AbstractSpellBookItem.getMainSpellBook(clientPlayer);
		AbstractSpellBookItem book = AbstractSpellBookItem.getMainSpellBookItem(clientPlayer);
		book.scrollSpell(forward, stack);
		Combat.getInstance().channel.sendTo(new CSpellbookNBTPacket(stack.getTag(), clientPlayer.getUniqueID()), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
		Minecraft.getInstance().player.sendStatusMessage(book.getCurrentSpell(stack).getColoredName(SpellStats.getSpellKnowledge(clientPlayer, book.getCurrentSpell(stack))), true);
	}
}
