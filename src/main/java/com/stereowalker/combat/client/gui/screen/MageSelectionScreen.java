package com.stereowalker.combat.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellCategory;
import com.stereowalker.combat.network.client.play.CMageSetupPacket;
import com.stereowalker.unionlib.client.gui.widget.button.Slider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;

public class MageSelectionScreen extends Screen {
	private final Screen previousScreen;

	int rValue = 128;
	int bValue = 128;
	int gValue = 128;
	int maxColorValue = 255;
	SpellCategory elementalAffinity = null;
	SpellCategory lifeAffinity = null;
	SpellCategory specialAffinity = null;

	Button fire, wate, ligh, eart, wind, rest, conj, spac, mind, natu, enha;
	Slider[] sliders = new Slider[3];

	public MageSelectionScreen(Minecraft mc, Screen previousScreen) {
		super(new TranslationTextComponent(Combat.getInstance().getModid()+".config.title"));
		this.previousScreen = previousScreen;
		this.minecraft = mc;
	}

	@Override
	protected void init() {
		fire = this.addButton(new Button(this.width / 2 - 204, this.height / 6, 80, 20, SpellCategory.FIRE.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.FIRE;
		}));

		wate = this.addButton(new Button(this.width / 2 - 122, this.height / 6, 80, 20, SpellCategory.WATER.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.WATER;
		}));

		ligh = this.addButton(new Button(this.width / 2 - 40, this.height / 6, 80, 20, SpellCategory.LIGHTNING.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.LIGHTNING;
		}));

		eart = this.addButton(new Button(this.width / 2 + 42, this.height / 6, 80, 20, SpellCategory.EARTH.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.EARTH;
		}));

		wind = this.addButton(new Button(this.width / 2 + 124, this.height / 6, 80, 20, SpellCategory.WIND.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.WIND;
		}));

		rest = this.addButton(new Button(this.width / 2 - 81, this.height / 6 + 24, 80, 20, SpellCategory.RESTORATION.getDisplayName(), (onPress) -> {
			this.lifeAffinity = SpellCategory.RESTORATION;
		}));

		conj = this.addButton(new Button(this.width / 2 + 1, this.height / 6 + 24, 80, 20, SpellCategory.CONJURATION.getDisplayName(), (onPress) -> {
			this.lifeAffinity = SpellCategory.CONJURATION;
		}));

		spac = this.addButton(new Button(this.width / 2 - 163, this.height / 6 + 48, 80, 20, SpellCategory.SPACE.getDisplayName(), (onPress) -> {
			this.specialAffinity = SpellCategory.SPACE;
		}));

		mind = this.addButton(new Button(this.width / 2 - 81, this.height / 6 + 48, 80, 20, SpellCategory.MIND.getDisplayName(), (onPress) -> {
			this.specialAffinity = SpellCategory.MIND;
		}));

		natu = this.addButton(new Button(this.width / 2 + 1, this.height / 6 + 48, 80, 20, SpellCategory.NATURE.getDisplayName(), (onPress) -> {
			this.specialAffinity = SpellCategory.NATURE;
		}));

		enha = this.addButton(new Button(this.width / 2 + 83, this.height / 6 + 48, 80, 20, SpellCategory.ENHANCEMENT.getDisplayName(), (onPress) -> {
			this.specialAffinity = SpellCategory.ENHANCEMENT;
		}));


		sliders[0] = this.addButton(new Slider(this.width / 2 - 150, this.height / 6 + 72, 300, 20, (float)(rValue/maxColorValue)) {
			@Override
			protected void /*applyValue*/func_230972_a_() {
				rValue = (int)((float)this.sliderValue * maxColorValue);
			}
		});

		sliders[1] = this.addButton(new Slider(this.width / 2 - 150, this.height / 6 + 96, 300, 20, (float)(gValue/maxColorValue)) {
			@Override
			protected void /*applyValue*/func_230972_a_() {
				gValue = (int)((float)this.sliderValue * maxColorValue);
			}
		});

		sliders[2] = this.addButton(new Slider(this.width / 2 - 150, this.height / 6 + 120, 300, 20, (float)(bValue/maxColorValue)) {
			@Override
			protected void /*applyValue*/func_230972_a_() {
				bValue = (int)((float)this.sliderValue * maxColorValue);
			}
		});


		this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslationTextComponent("gui.done"), (onPress) -> {
			if (elementalAffinity != null && lifeAffinity != null && specialAffinity != null) {
				this.minecraft.displayGuiScreen(this.previousScreen);
				new CMageSetupPacket(rValue, gValue, bValue, elementalAffinity, lifeAffinity, specialAffinity).send();
			}
		}));
		super.init();
	}
	
	@Override
	public void tick() {
		fire.active = elementalAffinity != SpellCategory.FIRE;
		wind.active = elementalAffinity != SpellCategory.WIND;
		eart.active = elementalAffinity != SpellCategory.EARTH;
		ligh.active = elementalAffinity != SpellCategory.LIGHTNING;
		wate.active = elementalAffinity != SpellCategory.WATER;
		conj.active = lifeAffinity != SpellCategory.CONJURATION;
		rest.active = lifeAffinity != SpellCategory.RESTORATION;
		mind.active = specialAffinity != SpellCategory.MIND;
		natu.active = specialAffinity != SpellCategory.NATURE;
		spac.active = specialAffinity != SpellCategory.SPACE;
		enha.active = specialAffinity != SpellCategory.ENHANCEMENT;
		
		int i = 0;
		for (Slider slider : sliders) {
			String s = (int)((float)slider.getSliderValue() * maxColorValue) + "";
			TranslationTextComponent component = new TranslationTextComponent(Combat.getInstance().getModid()+"."+(i==0?"red":i==1?"green":"blue")+"_slider" + ": " + s);
			component.mergeStyle(component.getStyle().setColor(Color.fromInt(rValue << 16 | gValue << 8 | bValue)));
			
			slider.setMessage(component);
			i++;
		}
		super.tick();
	}

	@Override
	public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		AbstractGui.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
	}

}
