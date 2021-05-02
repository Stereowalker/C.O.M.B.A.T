package com.stereowalker.combat.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.inventory.container.AbstractElectricFurnaceContainer;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractElectricFurnaceScreen<T extends AbstractElectricFurnaceContainer> extends ContainerScreen<T> implements IRecipeShownListener {
//	private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
	public final AbstractRecipeBookGui recipeGui;
	private boolean widthTooNarrow;
	private final ResourceLocation guiTexture;

	public AbstractElectricFurnaceScreen(T screenContainer, AbstractRecipeBookGui recipeGuiIn, PlayerInventory inv, ITextComponent titleIn, ResourceLocation guiTextureIn) {
		super(screenContainer, inv, titleIn);
		this.recipeGui = recipeGuiIn;
		this.guiTexture = guiTextureIn;
	}

	@Override
	public void init() {
		super.init();
		this.widthTooNarrow = this.width < 379;
		this.recipeGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.container);
		this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
		//      this.addButton((new ImageButton(this.guiLeft + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, field_214089_l, (p_214087_1_) -> {
		//         this.recipeGui.initSearchBar(this.widthTooNarrow);
		//         this.recipeGui.toggleVisibility();
		//         this.guiLeft = this.recipeGui.updateScreenPosition(this.widthTooNarrow, this.width, this.xSize);
		//         ((ImageButton)p_214087_1_).setPosition(this.guiLeft + 20, this.height / 2 - 49);
		//      })));
	}

	@Override
	public void tick() {
		super.tick();
		this.recipeGui.tick();
	}

	@Override
	public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		if (this.recipeGui.isVisible() && this.widthTooNarrow) {
			this.drawGuiContainerBackgroundLayer(matrixStack, p_render_3_, p_render_1_, p_render_2_);
			this.recipeGui.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		} else {
			this.recipeGui.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
			this.recipeGui./*renderGhostRecipe*/func_230477_a_(matrixStack, this.guiLeft, this.guiTop, true, p_render_3_);
		}

		this.renderHoveredTooltip(matrixStack, p_render_1_, p_render_2_);
		this.recipeGui./*renderTooltip*/func_238924_c_(matrixStack, this.guiLeft, this.guiTop, p_render_1_, p_render_2_);
	}

	//   /**
	//    * Draw the foreground layer for the GuiContainer (everything in front of the items)
	//    */
	//   @Override
	//   protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
	//      this.font./*drawString*/func_238422_b_(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
	//      this.font./*drawString*/func_238422_b_(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
	//   }

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(this.guiTexture);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

		int l = ((AbstractElectricFurnaceContainer)this.container).getCookProgressionScaled();
		this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
		
		if (((AbstractElectricFurnaceContainer)this.container).hasEnergy()) {
			int k = ((AbstractElectricFurnaceContainer)this.container).getEnergyScaled();
			this.blit(matrixStack, this.guiLeft + 144, this.guiTop + 7 + 69 - k +1 , 176, 69 - k + 32, 16, k);
		}
	}

	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		if (this.recipeGui.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
			return true;
		} else {
			return this.widthTooNarrow && this.recipeGui.isVisible() ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		}
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		super.handleMouseClick(slotIn, slotId, mouseButton, type);
		this.recipeGui.slotClicked(slotIn);
	}

	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return this.recipeGui.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? false : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
		boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
		return this.recipeGui.func_195604_a(mouseX, mouseY, this.guiLeft, this.guiTop, this.xSize, this.ySize, mouseButton) && flag;
	}

	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		return this.recipeGui.charTyped(p_charTyped_1_, p_charTyped_2_) ? true : super.charTyped(p_charTyped_1_, p_charTyped_2_);
	}

	public void recipesUpdated() {
		this.recipeGui.recipesUpdated();
	}

	public RecipeBookGui getRecipeGui() {
		return this.recipeGui;
	}

	@Override
	public void onClose() {
		this.recipeGui.removed();
		super.onClose();
	}
}