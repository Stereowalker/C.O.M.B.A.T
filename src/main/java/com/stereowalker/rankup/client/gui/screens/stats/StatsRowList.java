package com.stereowalker.rankup.client.gui.screens.stats;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.rankup.api.stat.Stat;
import com.stereowalker.rankup.client.gui.screens.PlayerStatusRowList;
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

@OnlyIn(Dist.CLIENT)
public class StatsRowList extends PlayerStatusRowList<StatsRowList.Row> {
	int maxNameWidth;
	public StatsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
	}

	public int addStat(ResourceKey<Stat> statKey) {
		Component component = new TranslatableComponent(Util.makeDescriptionId("stat", statKey.location()));
        int i = this.minecraft.font.width(component);
        if (i > this.maxNameWidth) {
           this.maxNameWidth = i;
        }
        int guiWidth = this.x0;
        if (Combat.rankupInstance.CLIENT_STAT_SETTINGS.get(statKey) != null) {
        	return this.addEntry(new StatsRowList.Row(ImmutableList.of(Row.addUpgrade(guiWidth + 170, statKey, Combat.rankupInstance.CLIENT_STAT_SETTINGS.get(statKey).isEnabled())), statKey));
        } else {
        	Combat.getInstance().getLogger().warn("The stat {} does not exist in the registry", statKey);
        	return this.addEntry(new StatsRowList.Row(ImmutableList.of(Row.addUpgrade(guiWidth + 170, statKey, false)), statKey));
		}
	}

	public void addStat(Registry<Stat> registry) {
		for(java.util.Map.Entry<ResourceKey<Stat>, Stat> s : registry.entrySet()) {
			this.addStat(s.getKey());
		}

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
				new ServerboundUpgradeLevelsPacket(statKey.location()).send();
			});
			upgradeButton.active = upgradeActive && isEnabled;
			return upgradeButton;
		}

		@SuppressWarnings("resource")
		@Override
		public void render(PoseStack matrixStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
			Player player = Minecraft.getInstance().player;
			Stat stat = Minecraft.getInstance().level.registryAccess().registry(CombatRegistries.STATS_REGISTRY).get().get(statKey);
			StatSettings settings = Combat.rankupInstance.CLIENT_STAT_SETTINGS.get(statKey);
			int points = stat.getAdditionalPoints(player);
			String na = Util.makeDescriptionId("stat", statKey.location());
			
			MutableComponent normalStatDisplay = (new TranslatableComponent(": "+stat.getCurrentPoints(player)));
			if (Combat.RPG_CONFIG.enableTraining && stat.getEffortPoints(player) > 0)
				normalStatDisplay.append(new TextComponent(" +"+stat.getEffortPoints(player)).withStyle(ChatFormatting.YELLOW));
			MutableComponent bonusStatDisplay = normalStatDisplay.copy().append(new TextComponent(" +"+points).withStyle(ChatFormatting.GREEN));
			MutableComponent debuffStatDisplay = normalStatDisplay.copy().append(new TextComponent(" "+points).withStyle(ChatFormatting.RED));
			
			this.widgets.forEach((widget) -> {
				widget.y = top;
				widget.x = width - 20;
				if (Combat.RPG_CONFIG.levelUpType == LevelType.UPGRADE_POINTS) widget.render(matrixStack, mouseX, mouseY, partialTicks);
			});
			GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, new TranslatableComponent(na), left, top+5, 0xffffff);
			GuiComponent.drawString(matrixStack, Minecraft.getInstance().font, 
					settings == null ? new TranslatableComponent(": Error") : 
						!settings.isEnabled() ? new TranslatableComponent(": Locked") : 
							points > 0 ? bonusStatDisplay :
								points < 0 ? debuffStatDisplay : normalStatDisplay, left+5+(StatsRowList.this.maxNameWidth), top+5, 0xffffff);
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
