package com.stereowalker.rankup.client.gui.screens.stats;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.rankup.client.gui.screens.skill.PlayerSkillsScreen;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
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
		super(new TextComponent("").append(mc.player.getDisplayName()).append("'s Stats"));
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
		this.addWidget(this.statsRowList);

		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 144, 200, 20, new TranslatableComponent("gui.show_skills"), (onPress) -> {
			minecraft.setScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}));
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslatableComponent("gui.done"), (onPress) -> {
			this.minecraft.setScreen(null);
		}));
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (KeyMappings.PLAYER_LEVELS.matches(p_keyPressed_1_, p_keyPressed_2_)) {
			this.minecraft.setScreen((Screen)null);
			this.minecraft.mouseHandler.grabMouse();
			return true;
		} else {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
	}

	public void removed() {
	}

	public static void drawCenteredString(PoseStack matrixStack, Font fontRenderer, Component font, int text, int x, int y) {
		FormattedCharSequence ireorderingprocessor = font.getVisualOrderText();
		fontRenderer.draw(matrixStack, ireorderingprocessor, (float)(text - fontRenderer.width(ireorderingprocessor) / 2), (float)x, y);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderDirtBackground(0);
		this.statsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
		GuiComponent.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		this.offset = -1;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BARS);
		int barWidth = 226;
		int k = (int)(PlayerAttributeLevels.getExperience(minecraft.player) * ((float)barWidth+1.0F) / StatEvents.getMaxExperience(PlayerAttributeLevels.getLevel(minecraft.player)));
		k = Mth.clamp(k, 0, barWidth+1);
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 65 + offset, 0, 40, barWidth, 5);
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 65 + offset, 0, 45, k, 5);
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
		RenderSystem.defaultBlendFunc();
		this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 65 + offset, 0, ex, barWidth, 5);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		GuiComponent.drawCenteredString(matrixStack, this.font, "Level: " +PlayerAttributeLevels.getLevel(minecraft.player), this.width / 2 - 94, this.height/ 2 - 75 + offset, 0xFFFFFF);
		if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS)
			GuiComponent.drawCenteredString(matrixStack, this.font, "Upgrade Points: "+PlayerAttributeLevels.getUpgradePoints(minecraft.player), this.width / 2, this.height/ 2 - 55 + offset, 16777215);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for(Widget widget : this.renderables) {
			if (widget instanceof AbstractWidget)
				if (((AbstractWidget)widget).isHoveredOrFocused()) {
					((AbstractWidget)widget).renderToolTip(matrixStack, mouseX, mouseY);
					break;
				}
		}
	}
}