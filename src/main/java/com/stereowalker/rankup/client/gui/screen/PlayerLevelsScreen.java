package com.stereowalker.rankup.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.rankup.client.gui.widget.list.StatsRowList;
import com.stereowalker.rankup.skill.client.gui.screen.PlayerSkillsScreen;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.stat.StatEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerLevelsScreen extends Screen {
	private static final ResourceLocation BACKGROUND = Combat.getInstance().location("textures/gui/upgradeableattribute/background.png");
	private static final ResourceLocation WINDOW = Combat.getInstance().location("textures/gui/upgradeableattribute/window.png");
	private static final ResourceLocation BARS = Combat.getInstance().location("textures/gui/upgradeableattribute/bars.png");
	private StatsRowList statsRowList;
	int offset = -5;

	public PlayerLevelsScreen(Minecraft mc) {
		super(new StringTextComponent("").appendSibling(mc.player.getDisplayName()).appendString("'s Stats"));
		this.minecraft = mc;
	}

	@Override
	public void init() {
		int xPos = (this.width-226)/2;
		int yPos = (this.height-86)/2;

		int xSize = 226;
		int ySize = 86;
		//		this.statsRowList = new StatsRowList(this.minecraft, xSize, 0, yPos, yPos+ySize, 21);
		this.statsRowList = new StatsRowList(this.minecraft, this.width, this.height, yPos, yPos+ySize, 21);
		//		this.statsRowList.setLeftPos(xPos);
		this.statsRowList.addStat(CombatRegistries.STATS.getValues());
		this.children.add(this.statsRowList);

		this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 144, 200, 20, new TranslationTextComponent("gui.show_skills"), (onPress) -> {
			minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}));
		this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslationTextComponent("gui.done"), (onPress) -> {
			this.minecraft.displayGuiScreen(null);
		}));
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

	public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int text, int x, int y) {
		IReorderingProcessor ireorderingprocessor = font.func_241878_f();
		fontRenderer.func_238422_b_(matrixStack, ireorderingprocessor, (float)(text - fontRenderer.func_243245_a(ireorderingprocessor) / 2), (float)x, y);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderDirtBackground(0);
		this.statsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
		this.offset = -15;
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
}