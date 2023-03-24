package com.stereowalker.combat.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderBars {
	public static void drawBar(Screen screen, PoseStack pPoseStack, int barX, int barY, int barWidth, Color color, Divisor div, float value) {
		ResourceLocation BARS = Combat.getInstance().location("textures/gui/upgradeableattribute/bars.png");
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BARS);
		barWidth = Mth.clamp(barWidth, 0, 226);
		int halfBar = barWidth/2;
		int k = Mth.floor(value*barWidth);
		k = Mth.clamp(k, 0, barWidth+1);
		int barH = barX+halfBar;
		screen.blit(pPoseStack, barX, barY, 0, color.y, halfBar, 5);
		screen.blit(pPoseStack, barH, barY, 226 - halfBar, color.y, halfBar, 5);
		screen.blit(pPoseStack, barX, barY, 0, color.fill, Math.min(halfBar, k), 5);
		screen.blit(pPoseStack, barH, barY, 226 - halfBar, color.fill, Math.min(halfBar, k-halfBar), 5);
		if (div == Divisor.FOUR && barWidth > 96) {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			screen.blit(pPoseStack, barX, barY, 0, 130, 13, 5);
			screen.blit(pPoseStack, barX+Mth.floor(((float)barWidth)*0.5f)-14, barY, 99, 130, 26, 5);
			screen.blit(pPoseStack, barX+Mth.floor(((float)barWidth)*0.25f)-13, barY, 43, 130, 25, 5);
			screen.blit(pPoseStack, barX+Mth.floor(((float)barWidth)*0.75f)-13, barY, 43, 130, 25, 5);
		}
	}
	
	public enum Divisor {
		NONE,
		FOUR;
	}
	
	public enum Color {
		PINK(0, 5),
		BLUE(10, 15),
		RED(20, 25),
		GREEN(30, 35),
		YELLOW(40, 45),
		PURPLE(50, 55),
		WHITE(60, 65);
		
		int y;
		int fill;
		Color(int y, int fill) {
			this.y = y;
			this.fill = fill;
		}
	}
}
