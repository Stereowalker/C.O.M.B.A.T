package com.stereowalker.rankup.client.gui.widget.list;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.Rankup;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.client.gui.screen.PlayerLevelsScreen;
import com.stereowalker.rankup.network.client.CUpgradeLevelsPacket;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
public class StatsRowList extends AbstractOptionList<StatsRowList.Row> {
	public StatsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
//		this.centerListVertically = false;
//		this.func_244605_b(false);
//		this.func_244606_c(false);
	}

	public int addStat(Stat p_214333_1_) {
		return this.addEntry(StatsRowList.Row.create(this.x0, p_214333_1_));
	}

	public void addStat(Collection<Stat> p_214335_1_) {
		for(int i = 0; i < p_214335_1_.size(); i ++) {
			this.addStat((Stat) p_214335_1_.toArray()[i]);
		}

	}

	public int getRowWidth() {
		return 100;
	}

	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 99;
	}

	public Optional<Widget> func_238518_c_(double p_238518_1_, double p_238518_3_) {
		for(StatsRowList.Row optionsrowlist$row : this.getEventListeners()) {
			for(Widget widget : optionsrowlist$row.widgets) {
				if (widget.isMouseOver(p_238518_1_, p_238518_3_)) {
					return Optional.of(widget);
				}
			}
		}

		return Optional.empty();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Row extends AbstractOptionList.Entry<StatsRowList.Row> {
		private final List<Widget> widgets;
		private final Stat stat;

		private Row(List<Widget> widgetsIn, Stat statIn) {
			this.widgets = widgetsIn;
			this.stat = statIn;
		}

		/**
		 * Creates an options row with button for the specified option
		 */
		public static StatsRowList.Row create(int guiWidth, Stat stat) {
			return new StatsRowList.Row(ImmutableList.of(addUpgrade(guiWidth + 170, stat, Rankup.statsManager.STATS.get(stat).isEnabled())), stat);
		}

		public static Button addStat(int xPos, Stat stat, boolean isEnabled) {
			Button newButton;
			PlayerLevelsScreen screen = new PlayerLevelsScreen(Minecraft.getInstance(), stat);
			newButton = new ImageButton(xPos, 0, 20, 20, 0, 0, 20, isEnabled && stat != null ? stat.getButtonTexture() : stat.getLockedButtonTexture(), 20, 40, (p_213088_1_) -> {
				Minecraft.getInstance().displayGuiScreen(screen);
			});
			newButton.active = isEnabled;
			return newButton;
		}

		public static Button addUpgrade(int xPos, Stat stat, boolean isEnabled) {
			Button upgradeButton;
			boolean upgradeActive;
			int cost = stat.getExperienceCost(Minecraft.getInstance().player);
			if (EntityHelper.getActualExperienceTotal(Minecraft.getInstance().player) >= cost) {
				upgradeActive = true;
			} else {
				upgradeActive = false;
			}
			upgradeButton = new Button(xPos, 0, 48, 20, new StringTextComponent("Upgrade"), (p_214328_1_) -> {
				Combat.getInstance().channel.sendTo(new CUpgradeLevelsPacket(stat, Minecraft.getInstance().player.getUniqueID()), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
			});
			upgradeButton.active = upgradeActive && isEnabled;
			return upgradeButton;
		}

		public void render(MatrixStack matrixStack, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
			PlayerEntity player = Minecraft.getInstance().player;
			this.widgets.forEach((widget) -> {
				widget.y = p_230432_3_;
				widget.render(matrixStack, mouseX, mouseY, partialTicks);
				IFormattableTextComponent normalStatDisplay = (stat.getName().appendString(": "+stat.getCurrentPoints(player)));
				IFormattableTextComponent lockedStatDisplay = (stat.getName().appendString(": Locked"));
				IFormattableTextComponent bonusStatDisplay = (stat.getName().appendString(": "+stat.getCurrentPoints(player))).appendSibling(new StringTextComponent(" +"+stat.getAdditionalPoints(player)).mergeStyle(TextFormatting.GREEN));
				IFormattableTextComponent debuffStatDisplay = (stat.getName().appendString(": "+stat.getCurrentPoints(player))).appendSibling(new StringTextComponent(" "+stat.getAdditionalPoints(player)).mergeStyle(TextFormatting.RED));
				
				AbstractGui.drawString(matrixStack, Minecraft.getInstance().fontRenderer, !Rankup.statsManager.STATS.get(stat).isEnabled() ? lockedStatDisplay : stat.getAdditionalPoints(player) > 0 ? bonusStatDisplay : stat.getAdditionalPoints(player) < 0 ? debuffStatDisplay : normalStatDisplay, widget.x-170, p_230432_3_+5, 0xffffff);
			});
		}

		public List<? extends IGuiEventListener> getEventListeners() {
			return this.widgets;
		}
	}
}
