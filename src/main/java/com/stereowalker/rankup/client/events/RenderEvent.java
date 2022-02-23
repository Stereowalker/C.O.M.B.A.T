package com.stereowalker.rankup.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.stereowalker.combat.Combat;
import com.stereowalker.old.combat.config.Config;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.world.stat.PlayerAttributeLevels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT)
public class RenderEvent {
	public static final ResourceLocation GUI_ICONS = Combat.getInstance().location("textures/gui/icons.png");

	static Minecraft mc = Minecraft.getInstance();
	static Gui gui() {
		return mc.gui;
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void renderLayer(RenderLivingEvent<?, ?> event) {
		if (PlayerSkills.hasSkill(mc.player, Skills.INSIGHT) && Config.RPG_COMMON.enableLevelingSystem.get() && event.getEntity() != Minecraft.getInstance().player) {
			event.getMatrixStack().translate(0, 0.25, 0);
			renderName(event.getRenderer(), event.getEntity(), new TextComponent("Level "+PlayerAttributeLevels.getLevel(event.getEntity())), event.getMatrixStack(), event.getBuffers(), event.getLight());
			event.getMatrixStack().translate(0, -0.25, 0);
		}
	}

	protected static <T extends LivingEntity, M extends EntityModel<T>> void renderName(LivingEntityRenderer<T, M> livingRenderer, LivingEntity livingEntity, Component displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		double d0 = livingRenderer.entityRenderDispatcher.distanceToSqr(livingEntity);
		if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(livingEntity, d0)) {
			boolean flag = !livingEntity.isDiscrete();
			float f = livingEntity.getBbHeight() + 0.5F;
			int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.0D, (double)f, 0.0D);
			matrixStackIn.mulPose(livingRenderer.entityRenderDispatcher.cameraOrientation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrixStackIn.last().pose();
			float f1 = mc.options.getBackgroundOpacity(0.25F);
			int j = (int)(f1 * 255.0F) << 24;
			Font fontrenderer = livingRenderer.getFont();
			float f2 = (float)(-fontrenderer.width(displayNameIn) / 2);
			fontrenderer.drawInBatch(displayNameIn, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag, j, packedLightIn);
			if (flag) {
				fontrenderer.drawInBatch(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
			}

			matrixStackIn.popPose();
		}
	}
}
