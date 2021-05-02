package com.stereowalker.combat.api.spell;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpellGalaticNames {
	private static final ResourceLocation GALACTIC_ALT_FONT = new ResourceLocation("minecraft", "alt");
	private static final Style GALACTIC_STYLE = Style.EMPTY.setFontId(GALACTIC_ALT_FONT);
	private static final SpellGalaticNames INSTANCE = new SpellGalaticNames();

	private SpellGalaticNames() {
	}

	public static SpellGalaticNames getInstance() {
		return INSTANCE;
	}

	public IFormattableTextComponent getGalacticSpellName(FontRenderer fontRenderer, int maxWidth, IFormattableTextComponent name) {
		return (IFormattableTextComponent) fontRenderer.getCharacterManager().func_238358_a_(name.mergeStyle(GALACTIC_STYLE), maxWidth, Style.EMPTY);
	}
}
