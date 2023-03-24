package com.stereowalker.combat.mixins.client;

import java.nio.file.Path;
import java.util.OptionalLong;
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
import net.minecraft.client.gui.screens.worldselection.WorldPreset;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

	@Shadow private String initName;
//	@Shadow public final WorldGenSettingsComponent worldGenSettingsComponent;
	@Shadow public boolean hardCore;
	private long worldSeed;

	private CreateWorldScreenMixin(@Nullable Screen pLastScreen, DataPackConfig pDataPacks, WorldGenSettingsComponent pWorldGenSettingsComponent) {
		super(new TranslatableComponent("selectWorld.create"));
		this.initName = I18n.get("selectWorld.newWorld");
//		this.worldGenSettingsComponent = pWorldGenSettingsComponent;
		this.worldSeed = pWorldGenSettingsComponent.makeSettings(this.hardCore).seed();
	}

	@Inject(at = @At(value = "HEAD"), method = "init()V")
	public void changeName (CallbackInfo info) {
		if (Minecraft.getInstance().getLanguageManager().getSelected().getName().equals("English")) {
			this.initName = RandomWorldNameGenerator.generateRandomWorldName(this.worldSeed == 0 ? new Random() : new Random(this.worldSeed), 0.1f);
		}
	}

}
