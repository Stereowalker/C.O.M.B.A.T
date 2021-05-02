package com.stereowalker.combat.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.inventory.container.AlloyFurnaceContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlloyFurnaceScreen extends ContainerScreen<AlloyFurnaceContainer>{
	private static final ResourceLocation GUI_TEXTURE = Combat.getInstance().location("textures/gui/container/alloy_furnace.png");

	public AlloyFurnaceScreen(AlloyFurnaceContainer crateContainer, PlayerInventory playerInventory, ITextComponent title) {
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
		if (((AlloyFurnaceContainer)this.container).func_217061_l()) {
			int k = ((AlloyFurnaceContainer)this.container).func_217059_k();
			this.blit(matrixStack, this.guiLeft + 38, this.guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = ((AlloyFurnaceContainer)this.container).func_217060_j();
		this.blit(matrixStack, this.guiLeft + 79, this.guiTop + 34, 176, 14, l + 1, 16);
	}

}