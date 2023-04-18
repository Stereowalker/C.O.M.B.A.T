package com.stereowalker.rankup.client.gui.screens.stats;

import org.jline.reader.Widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.combat.client.RenderBars;
import com.stereowalker.rankup.client.gui.screens.jobs.PlayerJobsScreen;
import com.stereowalker.rankup.client.gui.screens.skill.PlayerSkillsScreen;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatEvents;
import com.stereowalker.unionlib.util.ScreenHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerLevelsScreen extends Screen {
	private static final ResourceLocation BARS = Combat.getInstance().location("textures/gui/upgradeableattribute/bars.png");
	private StatsRowList statsRowList;
	int offset = -5;

	public PlayerLevelsScreen(Minecraft mc) {
		super(Component.literal("").append(mc.player.getDisplayName()).append("'s Stats"));
		this.minecraft = mc;
	}

	@Override
	public void init() {
		int xPos = (this.width-226)/2;
		int yPos = 80;

		int ySize = /*106*/(this.height - 58) - yPos;
		//		this.statsRowList = new StatsRowList(this.minecraft, xSize, 0, yPos, yPos+ySize, 21);
		this.statsRowList = new StatsRowList(this.minecraft, this.width/2, this.height, yPos, yPos+ySize, 21);
		//		this.statsRowList.setLeftPos(xPos);
		this.statsRowList.addStat(this.minecraft.level.registryAccess().registryOrThrow(CombatRegistries.STATS_REGISTRY));
		this.addWidget(this.statsRowList);

		Button b = this.addRenderableWidget(ScreenHelper.buttonBuilder(Component.translatable("gui.show_stats"), (onPress) -> {
			minecraft.setScreen(new PlayerLevelsScreen(minecraft));
		}).bounds(this.width / 2 - 125, this.height - 48, 80, 20).build());
		this.addRenderableWidget(ScreenHelper.buttonBuilder(Component.translatable("gui.show_skills"), (onPress) -> {
			minecraft.setScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}).bounds(this.width / 2 - 40, this.height - 48, 80, 20).build());
		this.addRenderableWidget(ScreenHelper.buttonBuilder(Component.translatable("gui.show_jobs"), (onPress) -> {
			minecraft.setScreen(new PlayerJobsScreen(minecraft));
		}).bounds(this.width / 2 + 45, this.height - 48, 80, 20).build());
		b.active = false;
		this.addRenderableWidget(ScreenHelper.buttonBuilder(CommonComponents.GUI_DONE, (onPress) -> {
			this.minecraft.setScreen(null);
		}).bounds(this.width / 2 - 125, this.height - 23, 250, 20).build());
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
	public void render(PoseStack pPoseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderDirtBackground(0);
		this.statsRowList.render(pPoseStack, mouseX, mouseY, partialTicks);
		
		GuiComponent.drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 20, 16777215);
		GuiComponent.drawCenteredString(pPoseStack, this.font, "Level: " +PlayerAttributeLevels.getLevel(minecraft.player), this.width / 2 - 94, 40, 0xFFFFFF);
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BARS);
		int barWidth = 226;
		float val = (float)PlayerAttributeLevels.getExperience(minecraft.player)/ (float)StatEvents.getMaxExperience(PlayerAttributeLevels.getLevel(minecraft.player));
		RenderBars.drawBar(this, pPoseStack, this.width / 2 - (barWidth/2), 50, barWidth, RenderBars.Color.GREEN, RenderBars.Divisor.FOUR, val);

		if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS)
			GuiComponent.drawCenteredString(pPoseStack, this.font, "Upgrade Points: "+PlayerAttributeLevels.getUpgradePoints(minecraft.player), this.width / 2, 65, 16777215);
		
		super.render(pPoseStack, mouseX, mouseY, partialTicks);
		for(Renderable widget : this.renderables) {
			if (widget instanceof AbstractWidget)
				if (((AbstractWidget)widget).isHoveredOrFocused()) {
//					((AbstractWidget)widget).renderToolTip(pPoseStack, mouseX, mouseY);
					break;
				}
		}
	}
}