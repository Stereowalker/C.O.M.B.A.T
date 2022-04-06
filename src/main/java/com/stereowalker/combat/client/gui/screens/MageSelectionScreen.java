package com.stereowalker.combat.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.network.protocol.game.ServerboundMageSetupPacket;
import com.stereowalker.unionlib.client.gui.widget.button.Slider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;

public class MageSelectionScreen extends Screen {
	private final Screen previousScreen;

	int rValue = 128;
	int bValue = 128;
	int gValue = 128;
	int maxColorValue = 255;
	SpellCategory elementalAffinity = null;
	SpellCategory primevalAffinity = null;

	Button fire, wate, ligh, eart, wind, rest, conj, spac, mind, natu, enha;
	Slider[] sliders = new Slider[3];

	public MageSelectionScreen(Minecraft mc, Screen previousScreen) {
		super(new TranslatableComponent(Combat.getInstance().getModid()+".config.title"));
		this.previousScreen = previousScreen;
		this.minecraft = mc;
	}

	@Override
	protected void init() {
		fire = this.addRenderableWidget(new Button(this.width / 2 - 204, this.height / 6, 80, 20, SpellCategory.FIRE.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.FIRE;
		}));

		wate = this.addRenderableWidget(new Button(this.width / 2 - 122, this.height / 6, 80, 20, SpellCategory.WATER.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.WATER;
		}));

		ligh = this.addRenderableWidget(new Button(this.width / 2 - 40, this.height / 6, 80, 20, SpellCategory.LIGHTNING.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.LIGHTNING;
		}));

		eart = this.addRenderableWidget(new Button(this.width / 2 + 42, this.height / 6, 80, 20, SpellCategory.EARTH.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.EARTH;
		}));

		wind = this.addRenderableWidget(new Button(this.width / 2 + 124, this.height / 6, 80, 20, SpellCategory.WIND.getDisplayName(), (onPress) -> {
			this.elementalAffinity = SpellCategory.WIND;
		}));

		rest = this.addRenderableWidget(new Button(this.width / 2 - 81, this.height / 6 + 24, 80, 20, SpellCategory.RESTORATION.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.RESTORATION;
		}));

		conj = this.addRenderableWidget(new Button(this.width / 2 + 1, this.height / 6 + 24, 80, 20, SpellCategory.CONJURATION.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.CONJURATION;
		}));

		spac = this.addRenderableWidget(new Button(this.width / 2 - 163, this.height / 6 + 48, 80, 20, SpellCategory.SPACE.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.SPACE;
		}));

		mind = this.addRenderableWidget(new Button(this.width / 2 - 81, this.height / 6 + 48, 80, 20, SpellCategory.MIND.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.MIND;
		}));

		natu = this.addRenderableWidget(new Button(this.width / 2 + 1, this.height / 6 + 48, 80, 20, SpellCategory.NATURE.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.NATURE;
		}));

		enha = this.addRenderableWidget(new Button(this.width / 2 + 83, this.height / 6 + 48, 80, 20, SpellCategory.ENHANCEMENT.getDisplayName(), (onPress) -> {
			this.primevalAffinity = SpellCategory.ENHANCEMENT;
		}));


		sliders[0] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 72, 300, 20, (float)(rValue/maxColorValue)) {
			@Override
			protected void applyValue() {
				rValue = (int)((float)this.value * maxColorValue);
			}
		});

		sliders[1] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 96, 300, 20, (float)(gValue/maxColorValue)) {
			@Override
			protected void applyValue() {
				gValue = (int)((float)this.value * maxColorValue);
			}
		});

		sliders[2] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 120, 300, 20, (float)(bValue/maxColorValue)) {
			@Override
			protected void applyValue() {
				bValue = (int)((float)this.value * maxColorValue);
			}
		});


		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, new TranslatableComponent("gui.done"), (onPress) -> {
			if (elementalAffinity != null && primevalAffinity != null) {
				this.minecraft.setScreen(this.previousScreen);
				new ServerboundMageSetupPacket(rValue, gValue, bValue, elementalAffinity, primevalAffinity).send();
			}
		}));
//		super.init();
	}
	
	@Override
	public void tick() {
		fire.active = elementalAffinity != SpellCategory.FIRE;
		wind.active = elementalAffinity != SpellCategory.WIND;
		eart.active = elementalAffinity != SpellCategory.EARTH;
		ligh.active = elementalAffinity != SpellCategory.LIGHTNING;
		wate.active = elementalAffinity != SpellCategory.WATER;
		conj.active = primevalAffinity != SpellCategory.CONJURATION;
		rest.active = primevalAffinity != SpellCategory.RESTORATION;
		mind.active = primevalAffinity != SpellCategory.MIND;
		natu.active = primevalAffinity != SpellCategory.NATURE;
		spac.active = primevalAffinity != SpellCategory.SPACE;
		enha.active = primevalAffinity != SpellCategory.ENHANCEMENT;
		
		int i = 0;
		for (Slider slider : sliders) {
			String s = (int)((float)slider.getSliderValue() * maxColorValue) + "";
			TranslatableComponent component = new TranslatableComponent(Combat.getInstance().getModid()+"."+(i==0?"red":i==1?"green":"blue")+"_slider" + ": " + s);
			component.withStyle(component.getStyle().withColor(TextColor.fromRgb(rValue << 16 | gValue << 8 | bValue)));
			
			slider.setMessage(component);
			i++;
		}
		super.tick();
	}

	@Override
	public void render(PoseStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
		this.renderBackground(matrixStack);
		GuiComponent.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
	}

}
