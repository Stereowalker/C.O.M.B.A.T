package com.stereowalker.rankup.client.gui.screens.jobs;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.RenderBars;
import com.stereowalker.rankup.api.job.Job;
import com.stereowalker.rankup.world.job.JobProfile;
import com.stereowalker.rankup.world.job.PlayerJobs;

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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JobsRowList extends ContainerObjectSelectionList<JobsRowList.Row> {
	int maxNameWidth;
	PlayerJobsScreen jobsScreen;
	int width;
	public JobsRowList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn, PlayerJobsScreen jobsScreen) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
		this.x0+=(widthIn/2);
		this.x1+=(widthIn/2);
		this.width = widthIn;
		this.jobsScreen = jobsScreen;
	}

	public void addStat(Registry<Job> registry) {
		for(java.util.Map.Entry<ResourceKey<Job>, Job> s : registry.entrySet()) {
			ResourceKey<Job> jobKey = s.getKey();
			if (PlayerJobs.getJobProfile(this.minecraft.player, jobKey).hasJob()) {
				Component component = new TranslatableComponent(Util.makeDescriptionId("job", jobKey.location()));
				int i = this.minecraft.font.width(component);
				if (i > this.maxNameWidth) {
					this.maxNameWidth = i;
				}
				this.addEntry(new JobsRowList.Row(ImmutableList.of(Row.addUpgrade(jobKey)), jobKey));
			}
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
		for(JobsRowList.Row optionsrowlist$row : this.children()) {
			for(AbstractWidget widget : optionsrowlist$row.widgets) {
				if (widget.isMouseOver(p_238518_1_, p_238518_3_)) {
					return Optional.of(widget);
				}
			}
		}

		return Optional.empty();
	}

	@OnlyIn(Dist.CLIENT)
	public class Row extends ContainerObjectSelectionList.Entry<JobsRowList.Row> {
		private final List<AbstractWidget> widgets;
		private final ResourceKey<Job> jobKey;

		private Row(List<AbstractWidget> widgetsIn, ResourceKey<Job> statIn) {
			this.widgets = widgetsIn;
			this.jobKey = statIn;
		}

		public static Button addUpgrade(ResourceKey<Job> statKey) {
			Button upgradeButton;
			upgradeButton = new Button(0, 0, 20, 20, new TextComponent("+"), (p_214328_1_) -> {
//				Combat.getInstance().channel.sendTo(new ServerboundUpgradeLevelsPacket(statKey.location(), Minecraft.getInstance().player.getUUID()), Minecraft.getInstance().player.connection.getConnection(), NetworkDirection.PLAY_TO_SERVER);
			});
			return upgradeButton;
		}

		@SuppressWarnings("resource")
		@Override
		public void render(PoseStack pPoseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
			Player player = Minecraft.getInstance().player;
			Job job = Minecraft.getInstance().level.registryAccess().registry(CombatRegistries.JOBS_REGISTRY).get().get(jobKey);
			JobProfile jobProfile = PlayerJobs.getJobProfile(player, jobKey);
			float val = jobProfile.getExperience();
			float max = job.levels.get(jobProfile.getLevel()-1);
			this.widgets.forEach((widget) -> {
				widget.y = top;
			});
			String na = Util.makeDescriptionId("job", jobKey.location());
			GuiComponent.drawString(pPoseStack, Minecraft.getInstance().font, new TranslatableComponent(na), left, top+5, 0xffffff);
			
			int d = Mth.ceil(left+(width*0.5f));
			Component component;
			if (jobProfile.getLevel() > 9)
				component = new TextComponent("Level "+jobProfile.getLevel()).append("   ");
			else
				component = new TextComponent("Level "+jobProfile.getLevel()).append("  ");
			GuiComponent.drawString(pPoseStack, Minecraft.getInstance().font, component, d-Minecraft.getInstance().font.width(component), top+5, 0xffffff);
			RenderBars.drawBar(jobsScreen, pPoseStack, left+width/2, top + 7, width/2 - 5, RenderBars.Color.BLUE, RenderBars.Divisor.FOUR, val / max);
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
