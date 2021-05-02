package com.stereowalker.combat.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.inventory.container.ManaGeneratorContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ManaGeneratorScreen extends ContainerScreen<ManaGeneratorContainer>{
	private static final ResourceLocation GUI_TEXTURE = Combat.getInstance().location("textures/gui/container/mana_generator.png");

	public ManaGeneratorScreen(ManaGeneratorContainer crateContainer, PlayerInventory playerInventory, ITextComponent title) {
		super(crateContainer, playerInventory, title);
		++this.ySize;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void tick() {
		super.tick();
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
		if (((ManaGeneratorContainer)this.container).hasEnergy()) {
			int k = ((ManaGeneratorContainer)this.container).getEnergyScaled();
			this.blit(matrixStack, this.guiLeft + 144, this.guiTop + 7 + 69 - k, 176, 69 - k, 16, k + 1);
		}
	}

}