package com.stereowalker.combat.client.gui.screens.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.Spell;
import com.stereowalker.combat.api.world.spellcraft.SpellUtil;
import com.stereowalker.combat.network.protocol.game.ServerboundHeldItemStackNBTPacket;
import com.stereowalker.combat.network.protocol.game.ServerboundSpellbookNBTPacket;
import com.stereowalker.combat.world.item.AbstractMagicCastingItem;
import com.stereowalker.combat.world.item.AbstractSpellBookItem;
import com.stereowalker.combat.world.item.CItems;
import com.stereowalker.combat.world.item.GrimoireItem;
import com.stereowalker.combat.world.item.NecronomiconItem;
import com.stereowalker.combat.world.item.ScrollItem;
import com.stereowalker.combat.world.spellcraft.SpellStats;
import com.stereowalker.combat.world.spellcraft.Spells;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
public class SpellBookScreen extends Screen {
	public static final SpellBookScreen.IBookInfo EMPTY_BOOK = new SpellBookScreen.IBookInfo() {
		/**
		 * Returns the size of the book
		 */
		public int getPageCount() {
			return 0;
		}

		/**
		 * Gets the text from the supplied page number
		 */
		public Component iGetPageText(int pageNum) {
			return new TextComponent("");
		}
	};
	public static final ResourceLocation GRIMOIRE_TEXTURES = Combat.getInstance().location("textures/gui/grimoire.png");
	public static final ResourceLocation NECRONOMICON_TEXTURES = Combat.getInstance().location("textures/gui/necronomicon.png");
	private SpellBookScreen.IBookInfo bookInfo;
	private int currPage;
	private boolean bookInHands;
	/** Holds a copy of the page text, split into page width lines */
	private List<FormattedCharSequence> cachedPageLines = Collections.emptyList();
	private int cachedPage = -1;
	private Component field_243344_s = TextComponent.EMPTY;
	private PageButton buttonNextPage;
	private PageButton buttonPreviousPage;
	/** Determines if a sound is played when the page is turned */
	private final boolean pageTurnSounds;
	private ItemStack spellbook;
	private Button addSpellButton;

	public SpellBookScreen(SpellBookScreen.IBookInfo bookInfoIn, ItemStack book, boolean bookInHand) {
		this(bookInfoIn, true, book, bookInHand);
	}

	public SpellBookScreen(ItemStack book, boolean openedViaKeybind) {
		this(EMPTY_BOOK, false, book, openedViaKeybind);
	}

	private SpellBookScreen(SpellBookScreen.IBookInfo bookInfoIn, boolean pageTurnSoundsIn, ItemStack book, boolean openedViaKeybind) {
		super(NarratorChatListener.NO_TITLE);
		this.bookInfo = bookInfoIn;
		this.pageTurnSounds = pageTurnSoundsIn;
		this.spellbook = book;
		this.bookInHands = openedViaKeybind;
	}

	public void func_214155_a(SpellBookScreen.IBookInfo p_214155_1_) {
		this.bookInfo = p_214155_1_;
		this.currPage = Mth.clamp(this.currPage, 0, p_214155_1_.getPageCount());
		this.updateButtons();
		this.cachedPage = -1;
	}

	/**
	 * Moves the book to the specified page and returns true if it exists, false otherwise
	 */
	public boolean showPage(int pageNum) {
		int i = Mth.clamp(pageNum, 0, this.bookInfo.getPageCount() - 1);
		if (i != this.currPage) {
			this.currPage = i;
			this.updateButtons();
			this.cachedPage = -1;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * I'm not sure why this exists. The function it calls is public and does all of the work
	 */
	protected boolean showPage2(int pageNum) {
		return this.showPage(pageNum);
	}

	@Override
	protected void init() {
		this.addDoneButton();
		this.addChangePageButtons();
		this.addEditSpellButton();
	}

	protected void addDoneButton() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, 210, 200, 20, new TranslatableComponent("gui.done"), (button) -> {
			this.saveNBTToServer();
			this.minecraft.setScreen((Screen)null);
		}));
	}

	protected void saveNBTToServer() {
		ItemStack mainStack = Minecraft.getInstance().player.getOffhandItem();
		ItemStack offStack = Minecraft.getInstance().player.getMainHandItem();
		if(mainStack.getItem() instanceof AbstractMagicCastingItem || (mainStack.getItem() instanceof AbstractSpellBookItem && bookInHands) || mainStack.getItem() instanceof ScrollItem) Combat.getInstance().channel.sendTo(new ServerboundHeldItemStackNBTPacket(mainStack.getTag(), false, Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
		if(offStack.getItem() instanceof AbstractMagicCastingItem || (offStack.getItem() instanceof AbstractSpellBookItem && bookInHands) || offStack.getItem() instanceof ScrollItem) Combat.getInstance().channel.sendTo(new ServerboundHeldItemStackNBTPacket(offStack.getTag(), true, Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
		if (!bookInHands) Combat.getInstance().channel.sendTo(new ServerboundSpellbookNBTPacket(spellbook.getTag(), Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
	}

	protected void addEditSpellButton() {
		for (InteractionHand hand : InteractionHand.values()) {
			if (Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof ScrollItem) {
				ItemStack scrollStack = Minecraft.getInstance().player.getItemInHand(hand);
				AbstractSpellBookItem book = (AbstractSpellBookItem)spellbook.getItem();
				addSpellButton = this.addRenderableWidget(new Button(this.width / 2 - 100, 187, 200, 20, new TranslatableComponent(Combat.getInstance().getModid()+".gui.add_spell"), (button) -> {
					book.setSpellInBook(spellbook, this.currPage, SpellUtil.getSpellFromItem(scrollStack));
					SpellUtil.addSpellToStack(scrollStack, Spells.NONE);
					book.openGrimoire(spellbook, this.currPage);
				}));
			}
		}
		this.updateButtons();
	}

	@Override
	public void onClose() {
		this.saveNBTToServer();
		super.onClose();
	}

	protected void addChangePageButtons() {
		int i = (this.width - 192) / 2;
		this.buttonNextPage = this.addRenderableWidget(new PageButton(i + 116, 159, true, (button) -> {
			this.nextPage();
		}, this.pageTurnSounds));
		this.buttonPreviousPage = this.addRenderableWidget(new PageButton(i + 43, 159, false, (button) -> {
			this.previousPage();
		}, this.pageTurnSounds));
		this.updateButtons();
	}

	private int getPageCount() {
		return this.bookInfo.getPageCount();
	}

	/**
	 * Moves the display back one page
	 */
	protected void previousPage() {
		if (this.currPage > 0) {
			--this.currPage;
		}

		this.updateButtons();
	}

	/**
	 * Moves the display forward one page
	 */
	protected void nextPage() {
		if (this.currPage < this.getPageCount() - 1) {
			++this.currPage;
		}

		this.updateButtons();
	}

	private void updateButtons() {
		this.buttonNextPage.visible = this.currPage < this.getPageCount() - 1;
		this.buttonPreviousPage.visible = this.currPage > 0;
		for (InteractionHand hand : InteractionHand.values()) {
			if (Minecraft.getInstance().player.getItemInHand(hand).getItem() instanceof ScrollItem) {
				ItemStack scrollStack = Minecraft.getInstance().player.getItemInHand(hand);
				AbstractSpellBookItem book = (AbstractSpellBookItem)spellbook.getItem();
				boolean flag = false;
				if (book.getSpell(spellbook, this.currPage) == Spells.NONE && SpellUtil.itemHasSpell(scrollStack)) {
					boolean alreadyExists = false;
					for (int i = 0; i < book.getSpellCapacity(); i++) {
						if(book.getSpell(spellbook, i) == SpellUtil.getSpellFromItem(scrollStack)) {
							alreadyExists = true;
							break;
						}
					}
					flag = !alreadyExists;
				}
				if (this.addSpellButton != null) this.addSpellButton.active = flag;
			}
		}
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
			return true;
		} else {
			switch(p_keyPressed_1_) {
			case 266:
				this.buttonPreviousPage.onPress();
				return true;
			case 267:
				this.buttonNextPage.onPress();
				return true;
			default:
				return false;
			}
		}
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.bookInfo instanceof GrimoireInfo) {
			RenderSystem.setShaderTexture(0, GRIMOIRE_TEXTURES);
		}
		if (this.bookInfo instanceof NecronomiconInfo) {
			RenderSystem.setShaderTexture(0, NECRONOMICON_TEXTURES);
		}
		int i = (this.width - 192) / 2;
		this.blit(matrixStack, i, 2, 0, 0, 192, 192);
		if (this.cachedPage != this.currPage) {
			FormattedText itextproperties = this.bookInfo.getPageText(this.currPage);
			this.cachedPageLines = this.font.split(itextproperties, 114);
			this.field_243344_s = new TranslatableComponent("book.pageIndicator", this.currPage + 1, Math.max(this.getPageCount(), 1));
		}

		this.cachedPage = this.currPage;
		int i1 = this.font.width(this.field_243344_s);
		this.font.draw(matrixStack, this.field_243344_s, (float)(i - i1 + 192 - 44), 18.0F, 0);
		int k = Math.min(128 / 9, this.cachedPageLines.size());

		for(int l = 0; l < k; ++l) {
			FormattedCharSequence ireorderingprocessor = this.cachedPageLines.get(l);
			this.font.draw(matrixStack, ireorderingprocessor, (float)(i + 36), (float)(32 + l * 9), 0);
		}

		Style style = this.func_238805_a_((double)mouseX, (double)mouseY);
		if (style != null) {
			this.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
		}

		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	private int func_214156_a(String p_214156_1_) {
		return this.font.width(this.font.isBidirectional() ? this.font.bidirectionalShaping(p_214156_1_) : p_214156_1_);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int p_231044_5_) {
		if (p_231044_5_ == 0) {
			Style style = this.func_238805_a_(mouseX, mouseY);
			if (style != null && this.handleComponentClicked(style)) {
				return true;
			}
		}

		return super.mouseClicked(mouseX, mouseY, p_231044_5_);
	}

	@Override
	public boolean handleComponentClicked(Style style) {
		ClickEvent clickevent = style.getClickEvent();
		if (clickevent == null) {
			return false;
		} else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
			String s = clickevent.getValue();

			try {
				int i = Integer.parseInt(s) - 1;
				return this.showPage2(i);
			} catch (Exception exception) {
				return false;
			}
		} else {
			boolean flag = super.handleComponentClicked(style);
			if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
				this.minecraft.setScreen((Screen)null);
			}

			return flag;
		}
	}

	@Nullable
	public Style func_238805_a_(double p_238805_1_, double p_238805_3_) {
		if (this.cachedPageLines == null) {
			return null;
		} else {
			int i = Mth.floor(p_238805_1_ - (double)((this.width - 192) / 2) - 36.0D);
			int j = Mth.floor(p_238805_3_ - 2.0D - 30.0D);
			if (i >= 0 && j >= 0) {
				int k = Math.min(128 / 9, this.cachedPageLines.size());
				if (i <= 114 && j < 9 * k + k) {
					int l = j / 9;
					if (l >= 0 && l < this.cachedPageLines.size()) {
						FormattedCharSequence ireorderingprocessor = this.cachedPageLines.get(l);
						return this.minecraft.font.getSplitter().componentStyleAtWidth(ireorderingprocessor, i);
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	public static List<String> nbtPagesToStrings(CompoundTag p_214157_0_) {
		ListTag listnbt = p_214157_0_.getList("pages", 8).copy();
		Builder<String> builder = ImmutableList.builder();

		for(int i = 0; i < listnbt.size(); ++i) {
			builder.add(listnbt.getString(i));
		}

		return builder.build();
	}

	@OnlyIn(Dist.CLIENT)
	public interface IBookInfo {
		/**
		 * Returns the size of the book
		 */
		int getPageCount();

		/**
		 * Gets the text from the supplied page number
		 */
		FormattedText iGetPageText(int pageNum);

		/**
		 * Gets the text from the supplied page number
		 */
		default FormattedText getPageText(int pageNum) {
			return pageNum >= 0 && pageNum < this.getPageCount() ? this.iGetPageText(pageNum) : FormattedText.EMPTY;
		}

		static SpellBookScreen.IBookInfo fromItem(ItemStack p_216917_0_) {
			Item item = p_216917_0_.getItem();
			if (item == CItems.GRIMOIRE) {
				return new SpellBookScreen.GrimoireInfo(p_216917_0_);
			} else {
				return (item == CItems.NECRONOMICON) ? new SpellBookScreen.NecronomiconInfo(p_216917_0_) :SpellBookScreen.EMPTY_BOOK;
			}
		}

		static Component spellBookPage(Spell spell) {
			if (spell.getCastType() == Spell.CastType.PROJECTILE) {
				return (spell.getName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell))
						.append(new TranslatableComponent("\nClass: ")).append(spell.getCategory().getColoredDisplayName())
						.append(new TranslatableComponent("\nRank: ")).append(spell.getRank().getDisplayName()))
						.append(new TranslatableComponent("\nCost per Shot: "+spell.getCost()))
						.append(new TranslatableComponent("\nDescription: "+spell.getDescription().getString()));
			}
			else if (spell.getCastType() == Spell.CastType.RAY) {
				return (spell.getName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell))
						.append(new TranslatableComponent("\nClass: ")).append(spell.getCategory().getColoredDisplayName())
						.append(new TranslatableComponent("\nRank: ")).append(spell.getRank().getDisplayName()))
						.append(new TranslatableComponent("\nCost per Second: "+spell.getCost()))
						.append(new TranslatableComponent("\nDescription: "+spell.getDescription().getString()));
			}
			else if (spell.getCastType() == Spell.CastType.SURROUND) {
				return (spell.getName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell))
						.append(new TranslatableComponent("\nClass: ")).append(spell.getCategory().getColoredDisplayName())
						.append(new TranslatableComponent("\nRank: ")).append(spell.getRank().getDisplayName()))
						.append(new TranslatableComponent("\nCost per Second: "+spell.getCost()))
						.append(new TranslatableComponent("\nCooldown/Active time: "+spell.getCooldown()+" seconds"))
						.append(new TranslatableComponent("\nDescription: "+spell.getDescription().getString()));
			}
			else {
				return (spell.getName(SpellStats.getSpellKnowledge(Minecraft.getInstance().player, spell))
						.append(new TranslatableComponent("\nClass: ")).append(spell.getCategory().getColoredDisplayName())
						.append(new TranslatableComponent("\nRank: ")).append(spell.getRank().getDisplayName()))
						.append(new TranslatableComponent("\nCost: "+spell.getCost()))
						.append(new TranslatableComponent("\nCooldown: "+spell.getCooldown()+" seconds"))
						.append(new TranslatableComponent("\nDescription: "+spell.getDescription().getString()));
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class GrimoireInfo implements SpellBookScreen.IBookInfo {
		private final List<Component> pages;

		public GrimoireInfo(ItemStack stack) {
			this.pages = func_216921_b(stack);
		}

		private static List<Component> func_216921_b(ItemStack stack) {
			GrimoireItem grimoire = (GrimoireItem) stack.getItem();
			List<Component> list = new ArrayList<Component>();
			for (int i = 0; i < 10; i++) {
				list.add(IBookInfo.spellBookPage(grimoire.getSpell(stack, i)));
			}
			return list;
		}

		/**
		 * Returns the size of the book
		 */
		public int getPageCount() {
			return 10;
		}

		/**
		 * Gets the text from the supplied page number
		 */
		public Component iGetPageText(int pageNum) {
			return this.pages.get(pageNum);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class NecronomiconInfo implements SpellBookScreen.IBookInfo {
		private final List<Component> pages;

		public NecronomiconInfo(ItemStack p_i50616_1_) {
			this.pages = func_216921_b(p_i50616_1_);
		}

		private static List<Component> func_216921_b(ItemStack stack) {
			NecronomiconItem necronomicon = (NecronomiconItem) stack.getItem();
			List<Component> list = new ArrayList<Component>();
			for (int i = 0; i < 20; i++) {
				list.add(IBookInfo.spellBookPage(necronomicon.getSpell(stack, i)));
			}
			return list;
		}

		/**
		 * Returns the size of the book
		 */
		public int getPageCount() {
			return 20;
		}

		/**
		 * Gets the text from the supplied page number
		 */
		public Component iGetPageText(int pageNum) {
			return this.pages.get(pageNum);
		}
	}
}