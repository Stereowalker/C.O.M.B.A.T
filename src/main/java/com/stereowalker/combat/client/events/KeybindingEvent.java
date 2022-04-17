package com.stereowalker.combat.client.events;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.network.protocol.game.ServerboundBackItemPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundGunPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundGunPacket.GunAction;
import com.stereowalker.combat.network.protocol.game.ServerboundPronePacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSetLimiterPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSpellbookNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundStoreItemPacket;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.GunItem;
import com.stereowalker.combat.world.item.GunItem.GunType;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.client.gui.screens.stats.PlayerLevelsScreen;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class KeybindingEvent {
	static int fire = 0;
	static int fireR = 0;
	static Minecraft mc = Minecraft.getInstance();
	@SuppressWarnings("resource")
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void keybindTick(ClientTickEvent event) {
//		if (!Minecraft.func_71410_x().func_195544_aj() */)
//			return;
		if (event.phase == Phase.START) {
			LocalPlayer clientPlayer = Minecraft.getInstance().player;
			if (mc.getOverlay() == null && (mc.screen == null
					|| mc.screen.passEvents)/* && clientPlayer.getMainHandItem().getItem() != Items.STICK */) {
				ItemStack stackMain = clientPlayer.getItemInHand(InteractionHand.MAIN_HAND);
				ItemStack stackOff = clientPlayer.getItemInHand(InteractionHand.OFF_HAND);

				if (Config.COMMON.load_guns.get()) {
					if(stackMain.getItem() instanceof GunItem && !clientPlayer.isSprinting()) {
						GunItem gun = (GunItem)stackMain.getItem();
						//For Semi Automatic Guns
						if (KeyMappings.FIRE.isDown() && gun.getGunType() == GunType.SEMI_AUTOMATIC) fire += 1;
						if (!(KeyMappings.FIRE.isDown()) && gun.getGunType() == GunType.SEMI_AUTOMATIC) fire = 0;
						if (fire > 5) {
							fire = 5;
						}
						if(fire == 1) {
							Combat.getInstance().channel.sendTo(new ServerboundGunPacket(GunAction.FIRE), mc.player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
						}
						//For Automatic Guns
						if (KeyMappings.FIRE.isDown() && gun.getGunType() == GunType.AUTOMATIC) {
							fireR++;
							if(fireR > gun.getFireRate()) {
								fireR = 0;
								Combat.getInstance().channel.sendTo(new ServerboundGunPacket(GunAction.FIRE), mc.player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);

							}
						}

						while (KeyMappings.RELOAD.consumeClick()) {
							Combat.getInstance().channel.sendTo(new ServerboundGunPacket(GunAction.RELOAD), mc.player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
						}
					}
				}

				if (clientPlayer.getMainHandItem().getItem() instanceof AbstractMagicCastingItem || clientPlayer.getOffhandItem().getItem() instanceof AbstractMagicCastingItem) {
					if (!AbstractSpellBookItem.getMainSpellBook(clientPlayer).isEmpty()) {
						int time = clientPlayer.getUseItem() != stackMain ? (clientPlayer.getUseItem() != stackOff ? 0 : (stackOff.getUseDuration() - clientPlayer.getUseItemRemainingTicks())) : (stackMain.getUseDuration() - clientPlayer.getUseItemRemainingTicks());
						float q = (float)time/(float)AbstractSpellBookItem.getMainSpellBookItem(clientPlayer).getCurrentSpell(clientPlayer).getCastTime();

						while (KeyMappings.NEXT_SPELL.consumeClick() && q == 0) {
							changeSpell(clientPlayer, true);
						}

						while (KeyMappings.PREV_SPELL.consumeClick() && q == 0) {
							changeSpell(clientPlayer, false);
						}

					}
				}

				if (Combat.RPG_CONFIG.enableLevelingSystem) {
					while (KeyMappings.PLAYER_LEVELS.consumeClick()) {
						mc.setScreen(new PlayerLevelsScreen(mc));
					}

					while (KeyMappings.TOGGLE_LIMITER.consumeClick()) {
						new ServerboundSetLimiterPacket().send();
					}
				}

				if (ModHelper.isCuriosLoaded() && !CuriosCompat.getSlotsForType(clientPlayer, "back", 0).isEmpty()) {
					while (KeyMappings.OPEN_BACK_ITEM.consumeClick()) {
						new ServerboundBackItemPacket().send();
					}
					while (KeyMappings.STORE_ITEM.consumeClick()) {
						new ServerboundStoreItemPacket().send();
					}
				}
				
//				clientPlayer.setForcedPose(Pose.SWIMMING);
				boolean isProne = KeyMappings.PRONE.isDown();
				new ServerboundPronePacket(isProne).send();
				if (isProne && clientPlayer.getForcedPose() != Pose.SWIMMING) clientPlayer.setForcedPose(Pose.SWIMMING);
				else if (!isProne && clientPlayer.getForcedPose() == Pose.SWIMMING) clientPlayer.setForcedPose(null);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void changeSpell(LocalPlayer clientPlayer, boolean forward) {
		ItemStack stack = AbstractSpellBookItem.getMainSpellBook(clientPlayer);
		AbstractSpellBookItem book = AbstractSpellBookItem.getMainSpellBookItem(clientPlayer);
		book.scrollSpell(forward, stack);
		Combat.getInstance().channel.sendTo(new ServerboundSpellbookNBTPacket(stack.getTag(), clientPlayer.getUUID()), mc.player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
		mc.player.displayClientMessage(book.getCurrentSpell(stack).getColoredName(SpellStats.getSpellKnowledge(clientPlayer, book.getCurrentSpell(stack))), true);
	}
}
