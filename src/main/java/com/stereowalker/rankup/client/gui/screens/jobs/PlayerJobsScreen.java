package com.stereowalker.rankup.client.gui.screens.jobs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.rankup.client.gui.screens.skill.PlayerSkillsScreen;
import com.stereowalker.rankup.client.gui.screens.stats.PlayerLevelsScreen;
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
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerJobsScreen extends Screen {
	private static final ResourceLocation BARS = Combat.getInstance().location("textures/gui/upgradeableattribute/bars.png");
	private JobsRowList jobsList;
	int offset = -5;

	public PlayerJobsScreen(Minecraft mc) {
		super(new TextComponent("").append(mc.player.getDisplayName()).append("'s Jobs"));
		this.minecraft = mc;
	}

	@Override
	public void init() {
		int yPos = 80;

		int ySize = /*106*/(this.height - 58) - yPos;
		this.jobsList = new JobsRowList(this.minecraft, this.width/2, this.height, yPos, yPos+ySize, 21, this);
		this.jobsList.addStat(this.minecraft.level.registryAccess().registryOrThrow(CombatRegistries.JOBS_REGISTRY));
		this.addWidget(this.jobsList);

		this.addRenderableWidget(new Button(this.width / 2 - 125, this.height - 48, 80, 20, new TranslatableComponent("gui.show_stats"), (onPress) -> {
			minecraft.setScreen(new PlayerLevelsScreen(minecraft));
		}));
		this.addRenderableWidget(new Button(this.width / 2 - 40, this.height - 48, 80, 20, new TranslatableComponent("gui.show_skills"), (onPress) -> {
			minecraft.setScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}));
		Button b = this.addRenderableWidget(new Button(this.width / 2 + 45, this.height - 48, 80, 20, new TranslatableComponent("gui.show_jobs"), (onPress) -> {
			minecraft.setScreen(new PlayerJobsScreen(minecraft));
		}));
		b.active = false;
		this.addRenderableWidget(new Button(this.width / 2 - 125, this.height - 23, 250, 20, CommonComponents.GUI_DONE, (onPress) -> {
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
	public void render(PoseStack pPoseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderDirtBackground(0);
		this.jobsList.render(pPoseStack, mouseX, mouseY, partialTicks);
		
		GuiComponent.drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 20, 16777215);
		GuiComponent.drawCenteredString(pPoseStack, this.font, "Level: " +PlayerAttributeLevels.getLevel(minecraft.player), this.width / 2 - 94, 40, 0xFFFFFF);
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BARS);
		int barWidth = 226;
		int k = (int)(PlayerAttributeLevels.getExperience(minecraft.player) * ((float)barWidth+1.0F) / StatEvents.getMaxExperience(PlayerAttributeLevels.getLevel(minecraft.player)));
		int barPos = 50;
		k = Mth.clamp(k, 0, barWidth+1);
		this.blit(pPoseStack, this.width / 2 - (barWidth/2), barPos, 0, 40, barWidth, 5);
		this.blit(pPoseStack, this.width / 2 - (barWidth/2), barPos, 0, 45, k, 5);
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
		this.blit(pPoseStack, this.width / 2 - (barWidth/2), barPos, 0, ex, barWidth, 5);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		super.render(pPoseStack, mouseX, mouseY, partialTicks);
		for(Widget widget : this.renderables) {
			if (widget instanceof AbstractWidget)
				if (((AbstractWidget)widget).isHoveredOrFocused()) {
					((AbstractWidget)widget).renderToolTip(pPoseStack, mouseX, mouseY);
					break;
				}
		}
	}
}