package com.stereowalker.rankup.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.config.Config;
import com.stereowalker.rankup.skill.Skills;
import com.stereowalker.rankup.skill.api.PlayerSkills;
import com.stereowalker.rankup.stat.PlayerAttributeLevels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
	static IngameGui gui() {
		return mc.ingameGUI;
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void renderLayer(RenderLivingEvent<?, ?> event) {
		if (PlayerSkills.hasSkill(Minecraft.getInstance().player, Skills.INSIGHT) && Config.RPG_COMMON.enableLevelingSystem.get() && event.getEntity() != Minecraft.getInstance().player) {
			event.getMatrixStack().translate(0, 0.25, 0);
			renderName(event.getRenderer(), event.getEntity(), new StringTextComponent("Level "+PlayerAttributeLevels.getLevel(event.getEntity())), event.getMatrixStack(), event.getBuffers(), event.getLight());
			event.getMatrixStack().translate(0, -0.25, 0);
		}
	}

	protected static <T extends LivingEntity, M extends EntityModel<T>> void renderName(LivingRenderer<T, M> livingRenderer, LivingEntity livingEntity, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		double d0 = livingRenderer.getRenderManager().squareDistanceTo(livingEntity);
		if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(livingEntity, d0)) {
			boolean flag = !livingEntity.isDiscrete();
			float f = livingEntity.getHeight() + 0.5F;
			int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
			matrixStackIn.push();
			matrixStackIn.translate(0.0D, (double)f, 0.0D);
			matrixStackIn.rotate(livingRenderer.getRenderManager().getCameraOrientation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
			float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
			int j = (int)(f1 * 255.0F) << 24;
			FontRenderer fontrenderer = livingRenderer.getFontRendererFromRenderManager();
			float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
			fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag, j, packedLightIn);
			if (flag) {
				fontrenderer.func_243247_a(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
			}

			matrixStackIn.pop();
		}
	}
}
