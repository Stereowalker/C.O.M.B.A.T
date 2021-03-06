package com.stereowalker.combat.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.inventory.container.CardboardBoxContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CardboardBoxScreen extends ContainerScreen<CardboardBoxContainer>{
	private static final ResourceLocation GUI_TEXTURE = Combat.getInstance().location("textures/gui/container/crate.png");

	public CardboardBoxScreen(CardboardBoxContainer crateContainer, PlayerInventory playerInventory, ITextComponent title) {
		super(crateContainer, playerInventory, title);
		++this.ySize;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partiaTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partiaTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}