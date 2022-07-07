package com.stereowalker.rankup.client.gui.screens.skill;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.rankup.network.protocol.game.ServerboundActivateSkillPacket;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.unionlib.client.gui.components.OverlayImageButton;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkillsRowList extends ContainerObjectSelectionList<SkillsRowList.Row> {
	int maxNameWidth;
	int width;
	PlayerSkillsScreen screen;
	public SkillsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn, PlayerSkillsScreen screen) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
		this.x0+=(widthIn/2);
		this.x1+=(widthIn/2);
		this.width = widthIn;
		this.screen = screen;
	}

	public void addStat(List<Skill> skills) {
		for(Skill skill : skills) {
			int i = this.minecraft.font.width(skill.getName());
			if (i > this.maxNameWidth) {
				this.maxNameWidth = i;
			}
			this.addEntry(new SkillsRowList.Row(ImmutableList.of(addSkill(skill, skill.isEnabled()), activateSkill(skill)), skill));
		}

	}

	public Button addSkill(Skill skill, boolean isEnabled) {
		Button upgradeButton;
		upgradeButton = new OverlayImageButton(0, 0, 30, 30, 0, 0, 30, 30, isEnabled && skill != null ? skill.getButtonTexture() : skill.getLockedButtonTexture(), 30, 30, (p_213088_1_) -> {

		}, new TextComponent(""));
		upgradeButton.active = false;
		return upgradeButton;
	}

	public Button activateSkill(Skill skill) {
		if (skill.isActiveSkill()) {
			return new Button(0, 0, 60, 20, new TextComponent("Activate"), (p_214328_1_) -> {
				new ServerboundActivateSkillPacket(skill).send();
			});
		} else {
			Button upgradeButton;
			upgradeButton = new Button(0, 0, 60, 20, new TextComponent("Passive"), (p_214328_1_) -> {
			});
			upgradeButton.active = false;
			return upgradeButton;
		}
	}

	@Override
	public int getRowWidth() {
		return this.width;
	}

	@Override
	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 80;
	}

	public Optional<AbstractWidget> getMouseOver(double p_238518_1_, double p_238518_3_) {
		for(SkillsRowList.Row optionsrowlist$row : this.children()) {
			for(AbstractWidget widget : optionsrowlist$row.widgets) {
				if (widget.isMouseOver(p_238518_1_, p_238518_3_)) {
					return Optional.of(widget);
				}
			}
		}

		return Optional.empty();
	}

	@OnlyIn(Dist.CLIENT)
	public class Row extends ContainerObjectSelectionList.Entry<SkillsRowList.Row> {
		private final List<AbstractWidget> widgets;
		private final Skill skill;

		private Row(List<AbstractWidget> widgetsIn, Skill statIn) {
			this.widgets = widgetsIn;
			this.skill = statIn;
		}

		@Override
		public void render(PoseStack matrixStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
			Player player = SkillsRowList.this.minecraft.player;
			final int hoverY = index == 0 ? Math.max(top+15, mouseY) : mouseY;
			final int hoverX = Math.min(left+(width/2), mouseX);
			this.widgets.get(0).x = left;
			this.widgets.get(1).x = left+width - 80;

			List<FormattedText> cpmos = this.findOptimalLines(ComponentUtils.mergeStyles(new TranslatableComponent(skill.getTranslationKey()+".desc"), Style.EMPTY.withColor(ChatFormatting.GOLD)), Mth.ceil((float)width*0.75f));

			if (skill.isActiveSkill()) 
				if (PlayerSkills.isSkillActive(player, skill))
					this.widgets.get(1).setMessage(new TextComponent("Deactivate"));
				else
					this.widgets.get(1).setMessage(new TextComponent("Activate"));
			else
				this.widgets.get(1).setMessage(new TextComponent("Passive"));

			this.widgets.forEach((widget) -> {
				widget.y = top + 20 - (widget.getHeight()/2);
			});
			this.widgets.get(0).render(matrixStack, mouseX, mouseY, partialTicks);
			this.widgets.get(1).render(matrixStack, mouseX, mouseY, partialTicks);
			if (this.widgets.get(0).isHoveredOrFocused()) {
				SkillsRowList.this.screen.renderComponentTooltip(matrixStack, cpmos, hoverX, hoverY, SkillsRowList.this.minecraft.font);
			}
			GuiComponent.drawString(matrixStack, SkillsRowList.this.minecraft.font, this.skill.getName(), left + 40, top+16, 0xffffff);
		}

		//Methods I "borrowed" from the advancement system
		private static float getMaxWidth(StringSplitter pManager, List<FormattedText> pText) {
			return (float)pText.stream().mapToDouble(pManager::stringWidth).max().orElse(0.0D);
		}
		private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
		private List<FormattedText> findOptimalLines(Component pComponent, int pMaxWidth) {
			StringSplitter stringsplitter = SkillsRowList.this.minecraft.font.getSplitter();
			List<FormattedText> list = null;
			float f = Float.MAX_VALUE;

			for(int i : TEST_SPLIT_OFFSETS) {
				List<FormattedText> list1 = stringsplitter.splitLines(pComponent, pMaxWidth - i, Style.EMPTY);
				float f1 = Math.abs(getMaxWidth(stringsplitter, list1) - (float)pMaxWidth);
				if (f1 <= 10.0F) {
					return list1;
				}

				if (f1 < f) {
					f = f1;
					list = list1;
				}
			}

			return list;
		}
		//

		@Override
		public List<? extends GuiEventListener> children() {
			return this.widgets;
		}

		@Override
		public List<? extends NarratableEntry> narratables() {
			return this.widgets;
		}
	}
}
