package com.stereowalker.combat.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.inventory.DisenchantmentMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisenchantmentScreen extends AbstractContainerScreen<DisenchantmentMenu> {
	private static final ResourceLocation BACKGROUND_TEXTURE = Combat.getInstance().location("textures/gui/container/disenchantment.png");
	private float sliderProgress;
	/** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
	@SuppressWarnings("unused")
	private boolean clickedOnSroll;
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
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		this.renderBackground(matrixStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		int i = this.leftPos;
		int j = this.topPos;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		int k = (int)(41.0F * this.sliderProgress);
		//      this.blit(i + 119, j + 15 + k, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
		int l = this.leftPos + 52;
		int i1 = this.topPos + 14;
		int j1 = this.recipeIndexOffset + 12;
		//      this.drawRecipesBackground(mouseX, mouseY, l, i1, j1);
		//      this.drawRecipesItems(l, i1, j1);
	}

	//   private void drawRecipesBackground(int mouseX, int mouseY, int left, int top, int recipeIndexOffsetMax) {
	//      for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i) {
	//         int j = i - this.recipeIndexOffset;
	//         int k = left + j % 4 * 16;
	//         int l = j / 4;
	//         int i1 = top + l * 18 + 2;
	//         int j1 = this.imageHeight;
	//         if (i == this.container.getSelectedRecipe()) {
	//            j1 += 18;
	//         } else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18) {
	//            j1 += 36;
	//         }
	//
	//         this.blit(k, i1 - 1, 0, j1, 16, 18);
	//      }
	//
	//   }

	//   private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax) {
	//      List<WoodcuttingRecipe> list = this.container.getRecipeList();
	//
	//      for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i) {
	//         int j = i - this.recipeIndexOffset;
	//         int k = left + j % 4 * 16;
	//         int l = j / 4;
	//         int i1 = top + l * 18 + 2;
	//         this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getRecipeOutput(), k, i1);
	//      }
	//
	//   }

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		this.clickedOnSroll = false;
		if (this.hasItemsInInputSlot) {
			int i = this.leftPos + 52;
			int j = this.topPos + 14;
			int k = this.recipeIndexOffset + 12;

			for(int l = this.recipeIndexOffset; l < k; ++l) {
				int i1 = l - this.recipeIndexOffset;
				double d0 = p_mouseClicked_1_ - (double)(i + i1 % 4 * 16);
				double d1 = p_mouseClicked_3_ - (double)(j + i1 / 4 * 18);
				if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.menu.clickMenuButton(this.minecraft.player, l)) {
					Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
					this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
					return true;
				}
			}

			i = this.leftPos + 119;
			j = this.topPos + 9;
			if (p_mouseClicked_1_ >= (double)i && p_mouseClicked_1_ < (double)(i + 12) && p_mouseClicked_3_ >= (double)j && p_mouseClicked_3_ < (double)(j + 54)) {
				this.clickedOnSroll = true;
			}
		}

		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	//   public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
	//      if (this.clickedOnSroll && this.canScroll()) {
	//         int i = this.topPos + 14;
	//         int j = i + 54;
	//         this.sliderProgress = ((float)p_mouseDragged_3_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
	//         this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
	//         this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
	//         return true;
	//      } else {
	//         return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
	//      }
	//   }

	//   public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
	//      if (this.canScroll()) {
	//         int i = this.getHiddenRows();
	//         this.sliderProgress = (float)((double)this.sliderProgress - p_mouseScrolled_5_ / (double)i);
	//         this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
	//         this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)i) + 0.5D) * 4;
	//      }
	//
	//      return true;
	//   }

	//   private boolean canScroll() {
	//      return this.hasItemsInInputSlot && this.container.getRecipeListSize() > 12;
	//   }
	//
	//   protected int getHiddenRows() {
	//      return (this.container.getRecipeListSize() + 4 - 1) / 4 - 3;
	//   }
	//
	//   /**
	//    * Called every time this screen's container is changed (is marked as dirty).
	//    */
	//   private void onInventoryUpdate() {
	//      this.hasItemsInInputSlot = this.container.hasItemsinInputSlot();
	//      if (!this.hasItemsInInputSlot) {
	//         this.sliderProgress = 0.0F;
	//         this.recipeIndexOffset = 0;
	//      }
	//
	//   }
}