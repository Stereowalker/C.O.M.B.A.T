package com.stereowalker.rankup.client.gui.screens.stats;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.network.protocol.game.ServerboundUpgradeLevelsPacket;
import com.stereowalker.rankup.world.stat.LevelType;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;
import com.stereowalker.rankup.world.stat.StatSettings;
import com.stereowalker.unionlib.util.EntityHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;

@OnlyIn(Dist.CLIENT)
public class StatsRowList extends ContainerObjectSelectionList<StatsRowList.Row> {
	int maxNameWidth;
	public StatsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
		this.x0+=(widthIn/2);
		this.x1+=(widthIn/2);
	}

	public int addStat(ResourceKey<Stat> statKey) {
		Component component = new TranslatableComponent(Util.makeDescriptionId("stat", statKey.location()));
        int i = this.minecraft.font.width(component);
        if (i > this.maxNameWidth) {
           this.maxNameWidth = i;
        }
        int guiWidth = this.x0;
        return this.addEntry(new StatsRowList.Row(ImmutableList.of(Row.addUpgrade(guiWidth + 170, statKey, Combat.rankupInstance.CLIENT_STATS.get(statKey).isEnabled())), statKey));
	}

	public void addStat(Registry<Stat> registry) {
		for(java.util.Map.Entry<ResourceKey<Stat>, Stat> s : registry.entrySet()) {
			this.addStat(s.getKey());
		}

	}

	@Override
	public int getRowWidth() {
		return 800;
	}

	@Override
	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 80;
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
	public class Row extends ContainerObjectSelectionList.Entry<StatsRowList.Row> {
		private final List<AbstractWidget> widgets;
		private final ResourceKey<Stat> statKey;

		private Row(List<AbstractWidget> widgetsIn, ResourceKey<Stat> statIn) {
			this.widgets = widgetsIn;
			this.statKey = statIn;
		}

		@SuppressWarnings("resource")
		public static Button addStat(int xPos, ResourceKey<Stat> statKey, boolean isEnabled) {
			Stat stat = Minecraft.getInstance().level.registryAccess().registry(CombatRegistries.STATS_REGISTRY).get().get(statKey);
			Button newButton;
			PlayerLevelsScreen screen = new PlayerLevelsScreen(Minecraft.getInstance());
			newButton = new ImageButton(xPos, 0, 20, 20, 0, 0, 20, isEnabled && stat != null ? stat.getButtonTexture() : stat.getLockedButtonTexture(), 20, 40, (p_213088_1_) -> {
				Minecraft.getInstance().setScreen(screen);
			});
			newButton.active = isEnabled;
			return newButton;
		}

		@SuppressWarnings("resource")
		public static Button addUpgrade(int xPos, ResourceKey<Stat> statKey, boolean isEnabled) {
			Button upgradeButton;
			boolean upgradeActive;
			boolean useXP = false;//TODO: Send that info from the server to the client
			if (useXP) {
				int cost = Stat.getExperienceCost(Minecraft.getInstance().player, statKey);
				upgradeActive = EntityHelper.getActualExperienceTotal(Minecraft.getInstance().player) >= cost;
			} else {
				upgradeActive = PlayerAttributeLevels.getUpgradePoints(Minecraft.getInstance().player) > 0;
			}
			upgradeButton = new Button(xPos, 0, 20, 20, new TextComponent("+"), (p_214328_1_) -> {
				Combat.getInstance().channel.sendTo(new ServerboundUpgradeLevelsPacket(statKey.location(), Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
			});
			upgradeButton.active = upgradeActive && isEnabled;
			return upgradeButton;
		}

		@SuppressWarnings("resource")
		@Override
		public void render(PoseStack matrixStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
			Player player = Minecraft.getInstance().player;
			this.widgets.forEach((widget) -> {
				widget.y = top;
				Stat stat = Minecraft.getInstance().level.registryAccess().registry(CombatRegistries.STATS_REGISTRY).get().get(statKey);
				if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS) widget.render(matrixStack, mouseX, mouseY, partialTicks);
				String na = Util.makeDescriptionId("stat", statKey.location());
				MutableComponent normalStatDisplay = (new TranslatableComponent(": "+stat.getCurrentPoints(player)));
				StatSettings settings = Combat.rankupInstance.CLIENT_STATS.get(statKey);
				if (Combat.RPG_CONFIG.enableTraining && stat.getEffortPoints(player) > 0)
					normalStatDisplay.append(new TextComponent(" +"+stat.getEffortPoints(player)).withStyle(ChatFormatting.YELLOW));
				int points = stat.getAdditionalPoints(player);
				MutableComponent bonusStatDisplay = normalStatDisplay.copy().append(new TextComponent(" +"+points).withStyle(ChatFormatting.GREEN));
				MutableComponent debuffStatDisplay = normalStatDisplay.copy().append(new TextComponent(" "+points).withStyle(ChatFormatting.RED));
				GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, new TranslatableComponent(na), widget.x-160, top+5, 0xffffff);
				GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, 
						!settings.isEnabled() ? new TranslatableComponent(": Locked") : 
							points > 0 ? bonusStatDisplay :
								points < 0 ? debuffStatDisplay : normalStatDisplay, widget.x-155+(StatsRowList.this.maxNameWidth), top+5, 0xffffff);
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
