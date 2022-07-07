package com.stereowalker.rankup.client.gui.screens.skill;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.KeyMappings;
import com.stereowalker.rankup.client.gui.screens.jobs.JobsRowList;
import com.stereowalker.rankup.client.gui.screens.jobs.PlayerJobsScreen;
import com.stereowalker.rankup.client.gui.screens.stats.PlayerLevelsScreen;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;
import com.stereowalker.unionlib.client.gui.components.OverlayImageButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerSkillsScreen extends Screen {
	int page;
	private static final ResourceLocation BACKGROUND = Combat.getInstance().location("textures/gui/upgradeableattribute/background.png");
	private static final ResourceLocation WINDOW = Combat.getInstance().location("textures/gui/upgradeableattribute/window.png");
	private List<Skill> skills;
	private Skill viewedSkill;
	private SkillsRowList skillList;

	private Button next;
	private Button prev;
	int offset = 40;

	public PlayerSkillsScreen(Minecraft mc, int page, Skill skill) {
		super(new TextComponent("").append(mc.player.getDisplayName()).append("'s Skills"));
		this.minecraft = mc;
		this.page = page;
		List<Skill> playerSkills = new ArrayList<Skill>();
		for (Skill skill2 : CombatRegistries.SKILLS) {
			if (PlayerSkills.hasSkill(minecraft.player, skill2)) {
				playerSkills.add(skill2);
			}
		}
		if (playerSkills.contains(skill)) {
			this.viewedSkill = skill;
		} else {
			this.viewedSkill = null;
		}
		this.skills = playerSkills;
	}

	public PlayerSkillsScreen(Minecraft mc) {
		this(mc, 0, null);
	}

	@Override
	public void init() {
		int yPos = 80;

		int ySize = /*106*/(this.height - 58) - 80;
		this.skillList = new SkillsRowList(this.minecraft, this.width/2, this.height, yPos, yPos+ySize, 40, this);
		this.skillList.addStat(this.skills);
		this.addWidget(this.skillList);
		
		int defX = -118;
		int xPos = defX;
		int yPos1 = -42;
//		prev = this.addRenderableWidget(new Button(this.width / 2 + xPos + (24*0), this.height/ 2 + yPos1, 20, 20, new TextComponent("<"), (p_213088_1_) -> {
//			minecraft.setScreen(new PlayerSkillsScreen(minecraft, page-1, viewedSkill));
//		}) {
//			public void renderToolTip(PoseStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
//				if (page > 0)PlayerSkillsScreen.this.renderTooltip(matrixStack, new TextComponent("+ "+page+" More"), p_renderToolTip_1_, p_renderToolTip_2_);
//			}
//		});
		int max = Math.min(skills.size(), 8);
//		for (int i = 0; i < max; i++) {
//			this.addSkill(xPos + (24*(i+1)), yPos1, (Skill) skills.toArray()[page+i], ((Skill) skills.toArray()[page+i]).isEnabled());
//		}
//		next = this.addRenderableWidget(new Button(this.width / 2 + xPos + (24*9), this.height/ 2 + yPos1, 20, 20, new TextComponent(">"), (p_213088_1_) -> {
//			minecraft.setScreen(new PlayerSkillsScreen(minecraft, page+1, viewedSkill));
//		}) {
//			public void renderToolTip(PoseStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
//				if (page+8 < skills.size())PlayerSkillsScreen.this.renderTooltip(matrixStack, new TextComponent("+ "+(skills.size() - (page+8))+" More"), p_renderToolTip_1_, p_renderToolTip_2_);
//			}
//		});

//		this.prev.active = page > 0;
//		this.next.active = page+8 < skills.size();
//
//		if (level != null) {
//			int cost = level.getExperienceCost(minecraft.player);
//			if (EntityHelper.getActualExperienceTotal(minecraft.player) >= cost && PlayerAttributeLevels.getLevel(minecraft.player, level) < level.getMaxLevel().getMax()) {
//				upgradeActive = true;
//			} else {
//				upgradeActive = false;
//			}
//			upgradeButton = this.addRenderableWidget(new Button(this.width / 2 - 24, this.height/ 2 - 18 + offset, 48, 20, new TextComponent("Upgrade"), (p_214328_1_) -> {
//				Combat.CHANNEL.sendTo(new CUpgradeLevelsPacket(this.level, Minecraft.getInstance().player.getUniqueID()), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
//				minecraft.setScreen(new PlayerSkillsScreen(minecraft, page, level));
//			}));
//			upgradeButton.active = upgradeActive;
//		}
		

		this.addRenderableWidget(new Button(this.width / 2 - 125, this.height - 48, 80, 20, new TranslatableComponent("gui.show_stats"), (onPress) -> {
			minecraft.setScreen(new PlayerLevelsScreen(minecraft));
		}));
		Button b = this.addRenderableWidget(new Button(this.width / 2 - 40, this.height - 48, 80, 20, new TranslatableComponent("gui.show_skills"), (onPress) -> {
			minecraft.setScreen(new PlayerSkillsScreen(minecraft, 0, null));
		}));
		this.addRenderableWidget(new Button(this.width / 2 + 45, this.height - 48, 80, 20, new TranslatableComponent("gui.show_jobs"), (onPress) -> {
			minecraft.setScreen(new PlayerJobsScreen(minecraft));
		}));
		b.active = false;
		this.addRenderableWidget(new Button(this.width / 2 - 125, this.height - 23, 250, 20, CommonComponents.GUI_DONE, (onPress) -> {
			this.minecraft.setScreen(null);
		}));
		
	}

	public void addSkill(int xMod, int yMod, Skill skill, boolean isEnabled) {
		Button newButton;
		newButton = this.addRenderableWidget(new OverlayImageButton(this.width / 2 + xMod, this.height/ 2 + yMod, 20, 20, 0, 0, 20, 20, isEnabled && skill != null ? skill.getButtonTexture() : skill.getLockedButtonTexture(), 20, 20, (p_213088_1_) -> {
			minecraft.setScreen(new PlayerSkillsScreen(minecraft, page, skill));
		}, title) {
			public void renderToolTip(PoseStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
				PlayerSkillsScreen.this.renderTooltip(matrixStack, new TranslatableComponent(skill.getTranslationKey()), p_renderToolTip_1_, p_renderToolTip_2_);
			}
		});
		newButton.active = isEnabled;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (KeyMappings.PLAYER_LEVELS.matches(p_keyPressed_1_, p_keyPressed_2_)) {
			this.minecraft.setScreen((Screen)null);
			this.minecraft.mouseHandler.grabMouse();
			return true;
		} else {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
	}

	public void removed() {
	}

	@Override
	public void renderBackground(PoseStack matrixStack) {
		super.renderBackground(matrixStack);
		int i = (this.width - 256) / 2;
		int j = (this.height - 123) / 2;
		this.renderWindow(matrixStack, i, j);
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderDirtBackground(0);
		this.skillList.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		GuiComponent.drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 20, 16777215);
		GuiComponent.drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		for(Widget widget : this.renderables) {
			if (widget instanceof AbstractWidget)
				if (((AbstractWidget)widget).isHoveredOrFocused()) {
					((AbstractWidget)widget).renderToolTip(pPoseStack, pMouseX, pMouseY);
					break;
				}
		}
	}

	public void renderWindow(PoseStack matrixStack, int x, int y) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 7; j++) {
				this.blit(matrixStack, i*16+x+7, j*16+y+5, 0, 0, 16, 16);
			}
		}
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, WINDOW);
		this.blit(matrixStack, x, y, 0, 0, 256, 123);
	}
}