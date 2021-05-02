package com.stereowalker.combat.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.config.Config;
import com.stereowalker.unionlib.client.gui.widget.button.Slider;
import com.stereowalker.unionlib.config.UnionValues.BooleanValue;
import com.stereowalker.unionlib.config.UnionValues.DoubleValue;
import com.stereowalker.unionlib.config.UnionValues.IntValue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CombatConfigScreen extends Screen {
	private final Screen previousScreen;

	public CombatConfigScreen(Minecraft mc, Screen previousScreen) {
		super(new TranslationTextComponent(Combat.getInstance().getModid()+".config.title"));
		this.previousScreen = previousScreen;
		this.minecraft = mc;
	}

	@Override
	public void init() {
		int xMod = -155;
		int yMod = 0;
		this.addBooleanOption(Config.MAGIC_COMMON.toggle_affinities, xMod, yMod);
		this.addBooleanOption(Config.CLIENT.toggle_custom_main_menu, xMod, yMod+=24);
		this.addBooleanOption(Config.COMMON.load_guns, xMod, yMod+=24);
		this.addBooleanOption(Config.COMMON.debug_mode, xMod, yMod+=24);
		this.addBooleanOption(Config.CLIENT.enable_enchantment_descriptions, xMod, yMod+=24);
		this.addBooleanOption(Config.RPG_COMMON.enableLevelingSystem, xMod=5, yMod=0);
		this.addSliderOption(Config.MAGIC_COMMON.scrollDropChance, xMod, yMod+=24, 1.0F);
		this.addSliderOption(Config.MAGIC_COMMON.scrollDropChanceFromKill, xMod, yMod+=24, 1.0F);
		this.addSliderOption(Config.MAGIC_COMMON.abominationChance, xMod, yMod+=24, 1.0F);
		this.addSliderOption(Config.MAGIC_COMMON.rareAbominationChance, xMod, yMod+=24, 1.0F);
		this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslationTextComponent("gui.done"), (onPress) -> {
			this.minecraft.displayGuiScreen(this.previousScreen);
		}));
	}
	
	public void addBooleanOption(BooleanValue config, int xMod, int yMod) {
		String toggle;
		if (config.get()) toggle = "options.on";
		else toggle = "options.off";
		this.addButton(new Button(this.width / 2 + xMod, this.height / 6 + yMod, 150, 20, new TranslationTextComponent(Combat.getInstance().getModid()+"."+config.getPath().get(1)).appendString(" : ").appendSibling(new TranslationTextComponent(toggle)), (onPress) -> {
			config.set(!config.get());
			this.minecraft.displayGuiScreen(this);
		}));
	}
	
	public void addSliderOption(IntValue config, int xMod, int yMod, float maxValue) {
		this.addButton(new Slider(this.width / 2 + xMod, this.height / 6 + yMod, 150, 20, (float)(config.get()/maxValue)) {
			@Override
			protected void /*updateMessage*/func_230979_b_() {
				String s = (int)((float)this.sliderValue * maxValue) + "";
				this.setMessage(new StringTextComponent(Combat.getInstance().getModid()+"."+config.getPath().get(1) + ": " + s));
			}

			@Override
			protected void /*applyValue*/func_230972_a_() {
				config.set((int)((float)this.sliderValue * maxValue));
			}
		});
	}
	
	public void addSliderOption(DoubleValue config, int xMod, int yMod, float maxValue) {
		this.addButton(new Slider(this.width / 2 + xMod, this.height / 6 + yMod, 150, 20, (float)(config.get()/maxValue)) {
			@Override
			protected void /*updateMessage*/func_230979_b_() {
				String s = ((float)this.sliderValue * maxValue) + "";
				this.setMessage(new StringTextComponent(Combat.getInstance().getModid()+"."+config.getPath().get(1) + ": " + s));
			}

			@Override
			protected void /*applyValue*/func_230972_a_() {
				config.set((double)((float)this.sliderValue * maxValue));
			}
		});
	}

	public void removed() {
		this.minecraft.gameSettings.saveOptions();
	}

	@Override
	public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		AbstractGui.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
	}
}