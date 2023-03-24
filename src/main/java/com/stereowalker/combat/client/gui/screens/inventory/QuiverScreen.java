package com.stereowalker.combat.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.inventory.QuiverMenu;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class QuiverScreen extends AbstractContainerScreen<QuiverMenu>{
	private static final ResourceLocation GUI_TEXTURE = Combat.getInstance().location("textures/gui/container/quiver.png");

	public QuiverScreen(QuiverMenu crateContainer, Inventory playerInventory, Component title) {
		super(crateContainer, playerInventory, title);
		++this.imageHeight;
		this.inventoryLabelY = this.imageHeight - 134; 
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partiaTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partiaTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

}