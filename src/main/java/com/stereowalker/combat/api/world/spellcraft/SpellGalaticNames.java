package com.stereowalker.combat.api.world.spellcraft;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellGalaticNames {
	private static final ResourceLocation GALACTIC_ALT_FONT = new ResourceLocation("minecraft", "alt");
	private static final Style GALACTIC_STYLE = Style.EMPTY.withFont(GALACTIC_ALT_FONT);
	private static final SpellGalaticNames INSTANCE = new SpellGalaticNames();

	private SpellGalaticNames() {
	}

	public static SpellGalaticNames getInstance() {
		return INSTANCE;
	}

	public MutableComponent getGalacticSpellName(Font fontRenderer, int maxWidth, MutableComponent name) {
		return (MutableComponent) fontRenderer.getSplitter().headByWidth(name.withStyle(GALACTIC_STYLE), maxWidth, Style.EMPTY);
	}
}
