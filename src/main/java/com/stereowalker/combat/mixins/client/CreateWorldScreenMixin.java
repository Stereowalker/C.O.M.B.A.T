package com.stereowalker.combat.mixins.client;

import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stereowalker.combat.util.RandomWorldNameGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.DataPackConfig;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

	@Shadow private String initName;
	@Shadow public final WorldGenSettingsComponent worldGenSettingsComponent;
	@Shadow public boolean hardCore;

	private CreateWorldScreenMixin(@Nullable Screen pLastScreen, DataPackConfig pDataPacks, WorldGenSettingsComponent pWorldGenSettingsComponent) {
		super(Component.translatable("selectWorld.create"));
		this.initName = I18n.get("selectWorld.newWorld");
		this.worldGenSettingsComponent = pWorldGenSettingsComponent;
	}

	@Inject(at = @At(value = "HEAD"), method = "init()V")
	public void changeName (CallbackInfo info) {
		if (Minecraft.getInstance().getLanguageManager().getSelected().getName().equals("English")) {
			this.initName = RandomWorldNameGenerator.generateRandomWorldName(this.worldGenSettingsComponent.seed.isEmpty() ? new Random() : new Random(this.worldGenSettingsComponent.seed.getAsLong()), 0.1f);
		}
	}

}
