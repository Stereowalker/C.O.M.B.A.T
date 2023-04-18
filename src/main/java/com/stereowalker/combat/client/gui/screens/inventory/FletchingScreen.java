package com.stereowalker.combat.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.inventory.FletchingMenu;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FletchingScreen extends AbstractContainerScreen<FletchingMenu> implements RecipeUpdateListener {
	public static final ResourceLocation FLETCHING_TABLE_GUI_TEXTURES = Combat.getInstance().location("textures/gui/container/fletching_table.png");
	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
	private boolean widthTooNarrow;

	public FletchingScreen(FletchingMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		this.widthTooNarrow = this.width < 379;
		this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
		this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
		this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (p_214076_1_) -> {
			this.recipeBookComponent.toggleVisibility();
			this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
			((ImageButton)p_214076_1_).setPosition(this.leftPos + 5, this.height / 2 - 49);
		}));
		this.addWidget(this.recipeBookComponent);
		this.setInitialFocus(this.recipeBookComponent);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		this.recipeBookComponent.tick();
	}

	@Override
	public void render(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
			this.renderBg(matrixStack, p_render_3_, p_render_1_, p_render_2_);
			this.recipeBookComponent.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		} else {
			this.recipeBookComponent.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			this.recipeBookComponent.renderGhostRecipe(matrixStack, this.leftPos, this.topPos, true, p_render_3_);
		}

		this.renderTooltip(matrixStack, p_render_1_, p_render_2_);
		this.recipeBookComponent.renderTooltip(matrixStack, this.leftPos, this.topPos, p_render_1_, p_render_2_);
		//      this.func_212932_b(this.recipeBookGui);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, FLETCHING_TABLE_GUI_TEXTURES);
		int i = this.leftPos;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
		return (!this.widthTooNarrow /* || !this.recipeBookGui.isVisible() */) && super.isHovering(x, y, width, height, mouseX, mouseY);
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		if (this.recipeBookComponent.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
			return true;
		} else {
			return this.widthTooNarrow  && this.recipeBookComponent.isVisible()  ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		}
	}

	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
		boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.imageWidth) || mouseY >= (double)(guiTopIn + this.imageHeight);
		return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
	}

	@Override
	protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.slotClicked(slotIn, slotId, mouseButton, type);
		this.recipeBookComponent.slotClicked(slotIn);
	}

	@Override
	public void recipesUpdated() {
		this.recipeBookComponent.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() {
		return  this.recipeBookComponent ;
	}
}