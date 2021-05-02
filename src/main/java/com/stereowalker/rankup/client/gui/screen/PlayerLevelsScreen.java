package com.stereowalker.rankup.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.client.gui.widget.list.StatsRowList;
import com.stereowalker.rankup.skill.client.gui.screen.PlayerSkillsScreen;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.stat.StatEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerLevelsScreen extends Screen {
	private static final ResourceLocation BACKGROUND = Combat.getInstance().location("textures/gui/upgradeableattribute/background.png");
	private static final ResourceLocation WINDOW = Combat.getInstance().location("textures/gui/upgradeableattribute/window.png");
	private static final ResourceLocation BARS = Combat.getInstance().location("textures/gui/upgradeableattribute/bars.png");
	private Stat level;
	private StatsRowList statsRowList;
	int offset = -5;

	public PlayerLevelsScreen(Minecraft mc, Stat level) {
		super(NarratorChatListener.EMPTY);
		this.minecraft = mc;
		this.level = level;
	}

	public PlayerLevelsScreen(Minecraft mc) {
		this(mc, null);
	}

	@Override
	public void init() {
		this.addButton(new Button(0, 0, 40, 20, new StringTextComponent("SKILLS"), (p_213088_1_) -> {
			minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}));
		int xPos = (this.width-226)/2;
		int yPos = (this.height-76)/2;
		
		int xSize = 226;
		int ySize = 76;
		this.statsRowList = new StatsRowList(this.minecraft, xSize, 0, yPos, yPos+ySize, 21);
		this.statsRowList.setLeftPos(xPos);
		this.statsRowList.addStat(CombatRegistries.STATS.getValues());
		this.children.add(this.statsRowList);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (KeyBindings.PLAYER_LEVELS.matchesKey(p_keyPressed_1_, p_keyPressed_2_)) {
			this.minecraft.displayGuiScreen((Screen)null);
			this.minecraft.mouseHelper.grabMouse();
			return true;
		} else {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
	}

	public void removed() {
	}

	@Override
	public void renderBackground(MatrixStack matrixStack) {
		super.renderBackground(matrixStack);
	}

	public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int text, int x, int y) {
		IReorderingProcessor ireorderingprocessor = font.func_241878_f();
		fontRenderer.func_238422_b_(matrixStack, ireorderingprocessor, (float)(text - fontRenderer.func_243245_a(ireorderingprocessor) / 2), (float)x, y);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.statsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
		int i = (this.width - 256) / 2;
		int j = (this.height - 173) / 2;
		this.renderWindow(matrixStack, i, j);
		if (level != null) {
			drawCenteredString(matrixStack, this.font, new StringTextComponent("").appendSibling(minecraft.player.getDisplayName()).appendString("'s Stats ["+I18n.format(level.getTranslationKey())+"]"), this.width / 2, this.height/ 2 - 55, 0x505050);
		} else {
			drawCenteredString(matrixStack, this.font, new StringTextComponent("").appendSibling(minecraft.player.getDisplayName()).appendString("'s Stats"), this.width / 2, this.height/ 2 - 80, 0xF0F0F0);
		}

		this.minecraft.getTextureManager().bindTexture(BARS);
		int barWidth = 226;
		int k = (int)(PlayerAttributeLevels.getExperience(minecraft.player) * ((float)barWidth+1.0F) / StatEvents.getMaxExperience(PlayerAttributeLevels.getLevel(minecraft.player)));
		k = MathHelper.clamp(k, 0, barWidth+1);
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, 40, barWidth, 5);
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, 45, k, 5);
		int ex = 0;
		if (PlayerAttributeLevels.getLevel(minecraft.player) > 900) {
			ex = 80;
		} else if (PlayerAttributeLevels.getLevel(minecraft.player) > 700) {
			ex = 90;
		} else if (PlayerAttributeLevels.getLevel(minecraft.player) > 500) {
			ex = 100;
		} else if (PlayerAttributeLevels.getLevel(minecraft.player) > 300) {
			ex = 110;
		} else if (PlayerAttributeLevels.getLevel(minecraft.player) > 100) {
			ex = 120;
		} else if (PlayerAttributeLevels.getLevel(minecraft.player) > 0) {
			ex = 130;
		} 

		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultBlendFunc();
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, ex, barWidth, 5);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		AbstractGui.drawCenteredString(matrixStack, this.font, "Level: " +PlayerAttributeLevels.getLevel(minecraft.player), this.width / 2 - 94, this.height/ 2 - 55 + offset, 0xFFFFFF);

		AbstractGui.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for(Widget widget : this.buttons) {
			if (widget.isHovered()) {
				widget.renderToolTip(matrixStack, mouseX, mouseY);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void renderWindow(MatrixStack matrixStack, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
//		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
//		for(int i = 0; i < 15; i++) {
//			for(int j = 0; j < 10; j++) {
//				this.blit(matrixStack, i*16+x+7, j*16+y+5, 0, 0, 16, 16);
//			}
//		}
		this.minecraft.getTextureManager().bindTexture(WINDOW);
		this.blit(matrixStack, x, y, 0, 0, 256, 173);
	}
}