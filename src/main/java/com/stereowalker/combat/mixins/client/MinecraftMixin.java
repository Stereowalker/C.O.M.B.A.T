package com.stereowalker.combat.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.WindowEventHandler;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.CombatBlockEntityWithoutLevelRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler {

	@Shadow @Final private ReloadableResourceManager resourceManager;
	@Shadow @Final private EntityModelSet entityModels;
	@Shadow @Final private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

	public MinecraftMixin(String p_18765_) {
		super(p_18765_);
	}

	@Inject(method = "createSearchTrees", at = @At("HEAD"))
	public void init_inject(CallbackInfo ci) {
		Combat.itemStackRender = new CombatBlockEntityWithoutLevelRenderer(this.blockEntityRenderDispatcher, this.entityModels);
		this.resourceManager.registerReloadListener(Combat.itemStackRender);
	}
	
	

}
