package com.stereowalker.combat.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.world.inventory.AbstractElectricFurnaceMenu;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
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
public abstract class AbstractElectricFurnaceScreen<T extends AbstractElectricFurnaceMenu> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
//	private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	public final AbstractFurnaceRecipeBookComponent recipeGui;
	private boolean widthTooNarrow;
	private final ResourceLocation guiTexture;

	public AbstractElectricFurnaceScreen(T screenContainer, AbstractFurnaceRecipeBookComponent recipeGuiIn, Inventory inv, Component titleIn, ResourceLocation guiTextureIn) {
		super(screenContainer, inv, titleIn);
		this.recipeGui = recipeGuiIn;
		this.guiTexture = guiTextureIn;
	}

	@Override
	public void init() {
		super.init();
		this.widthTooNarrow = this.width < 379;
		this.recipeGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
		this.leftPos = this.recipeGui.updateScreenPosition(this.width, this.imageWidth);
		//      this.addButton((new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, field_214089_l, (p_214087_1_) -> {
		//         this.recipeGui.initSearchBar(this.widthTooNarrow);
		//         this.recipeGui.toggleVisibility();
		//         this.leftPos = this.recipeGui.updateScreenPosition(this.widthTooNarrow, this.width, this.imageWidth);
		//         ((ImageButton)p_214087_1_).setPosition(this.leftPos + 20, this.height / 2 - 49);
		//      })));
	}

	@Override
	public void containerTick() {
		super.containerTick();
		this.recipeGui.tick();
	}

	@Override
	public void render(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		if (this.recipeGui.isVisible() && this.widthTooNarrow) {
			this.renderBg(matrixStack, p_render_3_, p_render_1_, p_render_2_);
			this.recipeGui.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		} else {
			this.recipeGui.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			this.recipeGui.renderGhostRecipe(matrixStack, this.leftPos, this.topPos, true, p_render_3_);
		}

		this.renderTooltip(matrixStack, p_render_1_, p_render_2_);
		this.recipeGui.renderTooltip(matrixStack, this.leftPos, this.topPos, p_render_1_, p_render_2_);
	}

	//   /**
	//    * Draw the foreground layer for the GuiContainer (everything in front of the items)
	//    */
	//   @Override
	//   protected void drawGuiContainerForegroundLayer(PoseStack matrixStack, int mouseX, int mouseY) {
	//      this.font./*drawString*/func_238422_b_(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
	//      this.font./*drawString*/func_238422_b_(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
	//   }

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, this.guiTexture);
		int i = this.leftPos;
		int j = this.topPos;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		int l = ((AbstractElectricFurnaceMenu)this.menu).getCookProgressionScaled();
		this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
		
		if (((AbstractElectricFurnaceMenu)this.menu).hasEnergy()) {
			int k = ((AbstractElectricFurnaceMenu)this.menu).getEnergyScaled();
			this.blit(matrixStack, this.leftPos + 144, this.topPos + 7 + 69 - k +1 , 176, 69 - k + 32, 16, k);
		}
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		if (this.recipeGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
			return true;
		} else {
			return this.widthTooNarrow && this.recipeGui.isVisible() ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		}
	}

	@Override
	protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.slotClicked(slotIn, slotId, mouseButton, type);
		this.recipeGui.slotClicked(slotIn);
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return this.recipeGui.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? false : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
		boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.imageWidth) || mouseY >= (double)(guiTopIn + this.imageHeight);
		return this.recipeGui.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
	}

	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		return this.recipeGui.charTyped(p_charTyped_1_, p_charTyped_2_) ? true : super.charTyped(p_charTyped_1_, p_charTyped_2_);
	}

	@Override
	public void recipesUpdated() {
		this.recipeGui.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() {
		return this.recipeGui;
	}
}