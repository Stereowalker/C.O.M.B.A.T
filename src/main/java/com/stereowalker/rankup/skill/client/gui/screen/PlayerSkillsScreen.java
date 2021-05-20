package com.stereowalker.rankup.skill.client.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.registries.CombatRegistries;
import com.stereowalker.combat.client.keybindings.KeyBindings;
import com.stereowalker.rankup.client.gui.screen.PlayerLevelsScreen;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.skill.api.Skill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerSkillsScreen extends Screen {
	int page;
	private static final ResourceLocation BACKGROUND = Combat.getInstance().location("textures/gui/upgradeableattribute/background.png");
	private static final ResourceLocation WINDOW = Combat.getInstance().location("textures/gui/upgradeableattribute/window.png");
	private List<Skill> skills;
	private Skill viewedSkill;

	private Button next;
	private Button prev;
	int offset = 40;

	public PlayerSkillsScreen(Minecraft mc, int page, Skill skill) {
		super(NarratorChatListener.EMPTY);
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
		this.addButton(new Button(0, 0, 40, 20, new StringTextComponent("LEVELS"), (p_213088_1_) -> {
			minecraft.displayGuiScreen(new PlayerLevelsScreen(minecraft));
		}));
		int defX = -118;
		int xPos = defX;
		int yPos = -42;
		prev = this.addButton(new Button(this.width / 2 + xPos + (24*0), this.height/ 2 + yPos, 20, 20, new StringTextComponent("<"), (p_213088_1_) -> {
			minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, page-1, viewedSkill));
		}) {
			public void renderToolTip(MatrixStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
				if (page > 0)PlayerSkillsScreen.this.renderTooltip(matrixStack, new StringTextComponent("+ "+page+" More"), p_renderToolTip_1_, p_renderToolTip_2_);
			}
		});
		int max = Math.min(skills.size(), 8);
		for (int i = 0; i < max; i++) {
			this.addStat(xPos + (24*(i+1)), yPos, (Skill) skills.toArray()[page+i], ((Skill) skills.toArray()[page+i]).isEnabled());
		}
		next = this.addButton(new Button(this.width / 2 + xPos + (24*9), this.height/ 2 + yPos, 20, 20, new StringTextComponent(">"), (p_213088_1_) -> {
			minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, page+1, viewedSkill));
		}) {
			public void renderToolTip(MatrixStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
				if (page+8 < skills.size())PlayerSkillsScreen.this.renderTooltip(matrixStack, new StringTextComponent("+ "+(skills.size() - (page+8))+" More"), p_renderToolTip_1_, p_renderToolTip_2_);
			}
		});

		this.prev.active = page > 0;
		this.next.active = page+8 < skills.size();
//
//		if (level != null) {
//			int cost = level.getExperienceCost(minecraft.player);
//			if (EntityHelper.getActualExperienceTotal(minecraft.player) >= cost && PlayerAttributeLevels.getLevel(minecraft.player, level) < level.getMaxLevel().getMax()) {
//				upgradeActive = true;
//			} else {
//				upgradeActive = false;
//			}
//			upgradeButton = this.addButton(new Button(this.width / 2 - 24, this.height/ 2 - 18 + offset, 48, 20, new StringTextComponent("Upgrade"), (p_214328_1_) -> {
//				Combat.CHANNEL.sendTo(new CUpgradeLevelsPacket(this.level, Minecraft.getInstance().player.getUniqueID()), Minecraft.getInstance().player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_SERVER);
//				minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, page, level));
//			}));
//			upgradeButton.active = upgradeActive;
//		}
		
	}

	public void addStat(int xMod, int yMod, Skill skill, boolean isEnabled) {
		Button newButton;
		newButton = this.addButton(new ImageButton(this.width / 2 + xMod, this.height/ 2 + yMod, 20, 20, 0, 0, 20, isEnabled && skill != null ? skill.getButtonTexture() : skill.getLockedButtonTexture(), 20, 40, (p_213088_1_) -> {
			minecraft.displayGuiScreen(new PlayerSkillsScreen(minecraft, page, skill));
		}) {
			public void renderToolTip(MatrixStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
				PlayerSkillsScreen.this.renderTooltip(matrixStack, new TranslationTextComponent(skill.getTranslationKey()), p_renderToolTip_1_, p_renderToolTip_2_);
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
		if (KeyBindings.PLAYER_LEVELS.matchesKey(p_keyPressed_1_, p_keyPressed_2_)) {
			this.minecraft.displayGuiScreen((Screen)null);
			this.minecraft.mouseHelper.grabMouse();
			return true;
		} else {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
	}

	public void removed() {
	}

	@Override
	public void renderBackground(MatrixStack matrixStack) {
		super.renderBackground(matrixStack);
		int i = (this.width - 256) / 2;
		int j = (this.height - 123) / 2;
		this.renderWindow(matrixStack, i, j);
	}

//	@Override
//	public void drawCenteredString(MatrixStack matrixStack, FontRenderer p_drawCenteredString_1_, String p_drawCenteredString_2_, int p_drawCenteredString_3_, int p_drawCenteredString_4_, int p_drawCenteredString_5_) {
//		p_drawCenteredString_1_.drawString(matrixStack, p_drawCenteredString_2_, (float)(p_drawCenteredString_3_ - p_drawCenteredString_1_.getStringWidth(p_drawCenteredString_2_) / 2), (float)p_drawCenteredString_4_, p_drawCenteredString_5_);
//	}

	@Override
	public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		if (viewedSkill != null) {
			AbstractGui.drawCenteredString(matrixStack, this.font, "Skills ["+I18n.format(viewedSkill.getTranslationKey())+"]", this.width / 2, this.height/ 2 - 55, 0x404040);
//			this.minecraft.getTextureManager().bindTexture(BARS);
//			int barWidth = 226;
//			int k = (int)(PlayerAttributeLevels.getLevel(minecraft.player, level) * ((float)barWidth+1.0F) / level.getMaxLevel().getMax());
//			k = MathHelper.clamp(k, 0, barWidth+1);
//			this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, 40, barWidth, 5);
//			this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, 45, k, 5);
//			int ex = 0;
//			if (level.getMaxLevel().equals(MaxLevel.LEVEL_32)) {
//				ex = 80;
//			} else if (level.getMaxLevel().equals(MaxLevel.LEVEL_28)) {
//				ex = 90;
//			} else if (level.getMaxLevel().equals(MaxLevel.LEVEL_16)) {
//				ex = 100;
//			} else if (level.getMaxLevel().equals(MaxLevel.LEVEL_14)) {
//				ex = 110;
//			} else if (level.getMaxLevel().equals(MaxLevel.LEVEL_8)) {
//				ex = 120;
//			} else if (level.getMaxLevel().equals(MaxLevel.LEVEL_4)) {
//				ex = 130;
//			} 
//			
//			RenderSystem.enableBlend();
//			RenderSystem.enableAlphaTest();
//			RenderSystem.defaultBlendFunc();
//			this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 45 + offset, 0, ex, barWidth, 5);
//			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//			
//			int exp = (int)(EntityHelper.getActualExperienceTotal(minecraft.player) * ((float)barWidth+1.0F) / level.getExperienceCost(minecraft.player));
//			exp = MathHelper.clamp(exp, 0, barWidth+1);
//			this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 25 + offset, 0, 30, barWidth, 5);
//			if (PlayerAttributeLevels.getLevel(minecraft.player, level) >= level.getMaxLevel().getMax()) {
//				this.drawCenteredString(matrixStack, this.font, "Current Level: MAX", this.width / 2, this.height/ 2 - 55 + offset, 0xFFFFFF);
//			}
//			else {
//				this.blit(matrixStack, this.width / 2 - (barWidth/2), this.height/ 2 - 25 + offset, 0, 35, exp, 5);
//				this.drawCenteredString(matrixStack, this.font, "Current Level: " +level.getCurrentLevel(minecraft.player) +"/"+ level.getMaxLevel().getMax(), this.width / 2 , this.height/ 2 - 55 + offset, 0xFFFFFF);
//				int remainingXp = level.getExperienceCost(minecraft.player) - EntityHelper.getActualExperienceTotal(minecraft.player);
//				if (remainingXp < 0) {
//					remainingXp = 0;
//				}
//				this.drawCenteredString(matrixStack, this.font, "Experience Points Remaining: " +remainingXp, this.width / 2, this.height/ 2 - 35 + offset, 0xFFFFFF);
//			}
		} else {
			AbstractGui.drawCenteredString(matrixStack, this.font, "Skills", this.width / 2, this.height/ 2 - 55, 0x404040);
		}
		AbstractGui.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
		for(Widget widget : this.buttons) {
			if (widget.isHovered()) {
				widget.renderToolTip(matrixStack, p_render_1_, p_render_2_);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void renderWindow(MatrixStack matrixStack, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		this.minecraft.getTextureManager().bindTexture(BACKGROUND);
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 7; j++) {
				this.blit(matrixStack, i*16+x+7, j*16+y+5, 0, 0, 16, 16);
			}
		}
		this.minecraft.getTextureManager().bindTexture(WINDOW);
		this.blit(matrixStack, x, y, 0, 0, 256, 123);
	}
}