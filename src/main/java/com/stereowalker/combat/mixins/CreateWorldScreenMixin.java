package com.stereowalker.combat.mixins;

import java.nio.file.Path;
import java.util.OptionalLong;
import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stereowalker.combat.RandomWorldNameGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldOptionsScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

	@Shadow private String worldName;
	@Shadow public final WorldOptionsScreen field_238934_c_;
	private long worldSeed;

	public CreateWorldScreenMixin(@Nullable Screen p_i242064_1_, WorldSettings p_i242064_2_, DimensionGeneratorSettings p_i242064_3_, @Nullable Path p_i242064_4_, DatapackCodec p_i242064_5_, DynamicRegistries.Impl p_i242064_6_) {
		this(p_i242064_1_, p_i242064_5_, new WorldOptionsScreen(p_i242064_6_, p_i242064_3_, BiomeGeneratorTypeScreens.func_239079_a_(p_i242064_3_), OptionalLong.of(p_i242064_3_.getSeed())));
		this.worldName = p_i242064_2_.getWorldName();
		this.worldSeed = p_i242064_3_.getSeed();
	}

	private CreateWorldScreenMixin(@Nullable Screen p_i242063_1_, DatapackCodec p_i242063_2_, WorldOptionsScreen p_i242063_3_) {
		super(new TranslationTextComponent("selectWorld.create"));
		this.worldName = I18n.format("selectWorld.newWorld");
		this.field_238934_c_ = p_i242063_3_;
		this.worldSeed = 0;
	}

	@Inject(at = @At(value = "HEAD"), method = "init()V")
	public void changeName (CallbackInfo info) {
		if (Minecraft.getInstance().getLanguageManager().getCurrentLanguage().getName().equals("English")) {
			this.worldName = RandomWorldNameGenerator.generateRandomWorldName(this.worldSeed == 0 ? new Random() : new Random(this.worldSeed), 0.1f);
		}
	}

}
