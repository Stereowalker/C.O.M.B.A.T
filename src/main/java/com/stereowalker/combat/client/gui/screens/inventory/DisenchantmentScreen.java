package com.stereowalker.combat.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.inventory.DisenchantmentMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisenchantmentScreen extends AbstractContainerScreen<DisenchantmentMenu> {
	private static final ResourceLocation BACKGROUND_TEXTURE = Combat.getInstance().location("textures/gui/container/disenchantment.png");
	private float sliderProgress;
	private BookModel bookModel;
	/**
	 * The index of the first recipe to display.
	 * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
	 * row, this value would be 4 (representing the index of the first slot on the second row).
	 */
	private int recipeIndexOffset;
	private boolean hasItemsInInputSlot;

	public DisenchantmentScreen(DisenchantmentMenu containerIn, Inventory playerInv, Component titleIn) {
		super(containerIn, playerInv, titleIn);
		//      containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
	}

	@Override
	public void render(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		this.renderTooltip(matrixStack, p_render_1_, p_render_2_);
	}

	@Override
	@SuppressWarnings("unused")
	protected void renderBg(PoseStack pPoseStack, float partialTicks, int mouseX, int mouseY) {
		this.renderBackground(pPoseStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		int i = this.leftPos;
		int j = this.topPos;
		this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		int k = (int)(41.0F * this.sliderProgress);
		//      this.blit(i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
		int l = this.leftPos + 52;
		int i1 = this.topPos + 14;
		int j1 = this.recipeIndexOffset + 12;
		//
		MutableComponent mutablecomponent1;;
		int col = this.minecraft.player.experienceLevel < this.menu.getDisenchantCost() ? ChatFormatting.RED.getColor() : 8453920;
		if (this.menu.getDisenchantCost() > 0) {
			if (i1 == 1) {
				mutablecomponent1 = Component.translatable("container.enchant.level.one");
			} else {
				mutablecomponent1 = Component.translatable("container.enchant.level.many", this.menu.getDisenchantCost());
			}
			this.font.drawShadow(pPoseStack, mutablecomponent1, i + 60, j + 30, col);
		}
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		if (this.hasItemsInInputSlot) {
			int i = this.leftPos + 52;
			int j = this.topPos + 14;
			int k = this.recipeIndexOffset + 12;

			for(int l = this.recipeIndexOffset; l < k; ++l) {
				int i1 = l - this.recipeIndexOffset;
				double d0 = p_mouseClicked_1_ - (double)(i + i1 % 4 * 16);
				double d1 = p_mouseClicked_3_ - (double)(j + i1 / 4 * 18);
				if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.menu.clickMenuButton(this.minecraft.player, l)) {
					//					Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
					this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
					return true;
				}
			}

			i = this.leftPos + 119;
			j = this.topPos + 9;
			if (p_mouseClicked_1_ >= (double)i && p_mouseClicked_1_ < (double)(i + 12) && p_mouseClicked_3_ >= (double)j && p_mouseClicked_3_ < (double)(j + 54)) {
			}
		}

		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
}