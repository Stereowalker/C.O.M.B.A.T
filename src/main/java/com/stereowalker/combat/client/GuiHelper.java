package com.stereowalker.combat.client;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.events.RenderEvent;
import com.stereowalker.combat.world.entity.ai.attributes.CAttributes;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;

@OnlyIn(Dist.CLIENT)
public class GuiHelper {
	public static final ResourceLocation GUI_ICONS = Combat.getInstance().location("textures/gui/icons.png");
	public static final ResourceLocation FROSTBITE_OVERLAY = Combat.getInstance().location("textures/misc/frostbite_overlay.png");
	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays() {
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, "Mana Bar", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
			AttributeInstance iattributemaxHealth = gui.getCameraPlayer().getAttribute(Attributes.MAX_HEALTH);
			AttributeInstance iattributemaxMana = gui.getCameraPlayer().getAttribute(CAttributes.MAX_MANA);
			double maxHealth = ModHelper.isMantleLoaded() ? Math.min(iattributemaxHealth.getValue(), 20) : iattributemaxHealth.getValue();
			int absorption = ModHelper.isMantleLoaded() ? Math.min(Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount()), 20) : Mth.ceil(gui.getCameraPlayer().getAbsorptionAmount());
			double totalHealth = maxHealth + absorption;
			boolean needsAir = false;
			int l6 = gui.getCameraPlayer().getAirSupply();
			int j7 = gui.getCameraPlayer().getMaxAirSupply();
			if (gui.getCameraPlayer().isEyeInFluid(FluidTags.WATER) || l6 < j7) {
				int j8 = Mth.ceil((double)(l6 - 2) * 10.0D / (double)j7);
				int l8 = Mth.ceil((double)l6 * 10.0D / (double)j7) - j8;

				for(int k5 = 0; k5 < j8 + l8; ++k5) {
					if (k5 < j8) {
						needsAir = true;
					} else {
						needsAir = true;
					}
				}
			}
			int x = gui.screenWidth / 2 - 91;
			int k1 = gui.screenHeight - 39;
			float f = (float)maxHealth;
			int l1 = absorption;
			int i2 = Mth.ceil((f + (float)l1) / 2.0F / 10.0F);
			int j2 = Math.max(10 - (i2 - 2), 3);
			int k2 = k1 - (i2 - 1) * j2 - 10;
			int j3 = gui.getCameraPlayer().getArmorValue();
			RenderEvent.renderManaOverlay(mStack, needsAir, iattributemaxMana, gui.getCameraPlayer(), totalHealth, x, j3, k2);
		});
	}
}
