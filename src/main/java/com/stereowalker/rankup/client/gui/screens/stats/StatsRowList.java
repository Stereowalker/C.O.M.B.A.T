package com.stereowalker.rankup.client.gui.screens.stats;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.ServerboundUpgradeLevelsPacket;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
public class StatsRowList extends ContainerObjectSelectionList<StatsRowList.Row> {
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

	@Override
	public int getRowWidth() {
		return 100;
	}

	@Override
	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 99;
	}

	public Optional<AbstractWidget> getMouseOver(double p_238518_1_, double p_238518_3_) {
		for(StatsRowList.Row optionsrowlist$row : this.children()) {
			for(AbstractWidget widget : optionsrowlist$row.widgets) {
				if (widget.isMouseOver(p_238518_1_, p_238518_3_)) {
					return Optional.of(widget);
				}
			}
		}

		return Optional.empty();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Row extends ContainerObjectSelectionList.Entry<StatsRowList.Row> {
		private final List<AbstractWidget> widgets;
		private final Stat stat;

		private Row(List<AbstractWidget> widgetsIn, Stat statIn) {
			this.widgets = widgetsIn;
			this.stat = statIn;
		}

		/**
		 * Creates an options row with button for the specified option
		 */
		public static StatsRowList.Row create(int guiWidth, Stat stat) {
			return new StatsRowList.Row(ImmutableList.of(addUpgrade(guiWidth + 170, stat, Combat.rankupInstance.CLIENT_STATS.get(stat).isEnabled())), stat);
		}

		public static Button addStat(int xPos, Stat stat, boolean isEnabled) {
			Button newButton;
			PlayerLevelsScreen screen = new PlayerLevelsScreen(Minecraft.getInstance());
			newButton = new ImageButton(xPos, 0, 20, 20, 0, 0, 20, isEnabled && stat != null ? stat.getButtonTexture() : stat.getLockedButtonTexture(), 20, 40, (p_213088_1_) -> {
				Minecraft.getInstance().setScreen(screen);
			});
			newButton.active = isEnabled;
			return newButton;
		}

		public static Button addUpgrade(int xPos, Stat stat, boolean isEnabled) {
			Button upgradeButton;
			boolean upgradeActive;
			boolean useXP = false;//TODO: Send that info from the server to the client
			if (useXP) {
				int cost = stat.getExperienceCost(Minecraft.getInstance().player);
				upgradeActive = EntityHelper.getActualExperienceTotal(Minecraft.getInstance().player) >= cost;
			} else {
				upgradeActive = PlayerAttributeLevels.getUpgradePoints(Minecraft.getInstance().player) > 0;
			}
			upgradeButton = new Button(xPos, 0, 20, 20, new TextComponent("+"), (p_214328_1_) -> {
				Combat.getInstance().channel.sendTo(new ServerboundUpgradeLevelsPacket(stat, Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
			});
			upgradeButton.active = upgradeActive && isEnabled;
			return upgradeButton;
		}

		@Override
		public void render(PoseStack matrixStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
			Player player = Minecraft.getInstance().player;
			this.widgets.forEach((widget) -> {
				widget.y = top;
				if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS)
					widget.render(matrixStack, mouseX, mouseY, partialTicks);
				MutableComponent normalStatDisplay = (stat.getName().append(": "+stat.getCurrentPoints(player)));
				if (Combat.RPG_CONFIG.enableTraining)
					normalStatDisplay = (stat.getName().append(": "+stat.getCurrentPoints(player))).append(new TextComponent(" +"+stat.getEffortPoints(player)).withStyle(ChatFormatting.YELLOW));
				MutableComponent lockedStatDisplay = (stat.getName().append(": Locked"));
				MutableComponent bonusStatDisplay = (new TextComponent("").append(normalStatDisplay)).append(new TextComponent(" +"+stat.getAdditionalPoints(player)).withStyle(ChatFormatting.GREEN));
				MutableComponent debuffStatDisplay = (new TextComponent("").append(normalStatDisplay)).append(new TextComponent(" "+stat.getAdditionalPoints(player)).withStyle(ChatFormatting.RED));
				
				
				if (stat.getAdditionalPoints(player) == 0) {
					GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, !Combat.rankupInstance.CLIENT_STATS.get(stat).isEnabled() ? lockedStatDisplay : normalStatDisplay, widget.x-160, top+5, 0xffffff);
				}
				else if (stat.getAdditionalPoints(player) > 0) {
					GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, !Combat.rankupInstance.CLIENT_STATS.get(stat).isEnabled() ? lockedStatDisplay : bonusStatDisplay, widget.x-160, top+5, 0xffffff);
				}
				else if (stat.getAdditionalPoints(player) < 0) {
					GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, !Combat.rankupInstance.CLIENT_STATS.get(stat).isEnabled() ? lockedStatDisplay : debuffStatDisplay, widget.x-160, top+5, 0xffffff);
				}
			});
		}

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