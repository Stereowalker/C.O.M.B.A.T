package com.stereowalker.combat.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory.ClassType;
import com.stereowalker.combat.network.protocol.game.ServerboundMageSetupPacket;
import com.stereowalker.unionlib.client.gui.widget.button.Slider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
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
		super(new TranslatableComponent(Combat.getInstance().getModid()+".mage_creation.title"));
		this.previousScreen = previousScreen;
		this.minecraft = mc;
	}

	public Button create(int x, int y, SpellCategory cat) {
		return this.addRenderableWidget(new Button(this.width / 2 + x, this.height / 6 + y, 80, 20, cat.getColoredDisplayName(), (onPress) -> {
			if (cat.getClassType() == ClassType.ELEMENTAL)
				this.elementalAffinity = cat;
			if (cat.getClassType() == ClassType.PRIMEVAL)
				this.primevalAffinity = cat;
		}));
	}

	@Override
	protected void init() {
		fire = create(-204, 14, SpellCategory.FIRE);
		wate = create(-122, 14, SpellCategory.WATER);
		ligh = create( -40, 14, SpellCategory.LIGHTNING);
		eart = create( +42, 14, SpellCategory.EARTH);
		wind = create(+124, 14, SpellCategory.WIND);
		
		rest = create(-204, 57, SpellCategory.RESTORATION);
		conj = create(-122, 57, SpellCategory.CONJURATION);
		spac = create( -40, 57, SpellCategory.SPACE);
		natu = create( +42, 57, SpellCategory.NATURE);
		enha = create(+124, 57, SpellCategory.ENHANCEMENT);

		sliders[0] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 96, 300, 20, (float)(rValue)/(float)(maxColorValue)) {
			@Override
			protected void applyValue() {
				rValue = (int)((float)this.value * maxColorValue);
			}
		});

		sliders[1] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 120, 300, 20, (float)(gValue)/(float)(maxColorValue)) {
			@Override
			protected void applyValue() {
				gValue = (int)((float)this.value * maxColorValue);
			}
		});

		sliders[2] = this.addRenderableWidget(new Slider(this.width / 2 - 150, this.height / 6 + 144, 300, 20, (float)(bValue)/(float)(maxColorValue)) {
			@Override
			protected void applyValue() {
				bValue = (int)((float)this.value * maxColorValue);
			}
		});


		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (onPress) -> {
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
		natu.active = primevalAffinity != SpellCategory.NATURE;
		spac.active = primevalAffinity != SpellCategory.SPACE;
		enha.active = primevalAffinity != SpellCategory.ENHANCEMENT;

		int i = 0;
		for (Slider slider : sliders) {
			String s = (int)((float)slider.getSliderValue() * maxColorValue) + "";
			MutableComponent component = new TranslatableComponent("gui."+(i==0?"red":i==1?"green":"blue")+"_slider").append(": " + s);
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
		GuiComponent.drawCenteredString(matrixStack, this.font, new TranslatableComponent("gui.elemental_affinity"), this.width / 2, this.height / 6, 16777215);
		GuiComponent.drawCenteredString(matrixStack, this.font, new TranslatableComponent("gui.primeval_affinity"), this.width / 2, this.height / 6 + 43, 16777215);
		GuiComponent.drawCenteredString(matrixStack, this.font, new TranslatableComponent("gui.mana_color"), this.width / 2, this.height / 6 + 82, 16777215);
		super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
	}

}
