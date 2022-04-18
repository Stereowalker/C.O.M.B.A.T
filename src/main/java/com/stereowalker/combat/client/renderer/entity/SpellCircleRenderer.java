package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.world.spellcraft.SpellCategory;
import com.stereowalker.combat.client.model.SpellCircleModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.CombatEntityStats;
import com.stereowalker.combat.world.entity.magic.AbstractSpellCircle;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SpellCircleRenderer extends EntityRenderer<AbstractSpellCircle>{
	public static final ResourceLocation elemental_outer_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_outer_ring.png");
	public static final ResourceLocation life_outer_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_outer_ring.png");
	
	public static final ResourceLocation elemental_inner_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_inner_ring.png");
	public static final ResourceLocation life_inner_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_inner_ring.png");
	
	public static final ResourceLocation elemental_middle_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_middle_ring.png");
	public static final ResourceLocation life_middle_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_middle_ring.png");
	
	private final SpellCircleModel spellCircleOuterModel;

	protected SpellCircleRenderer(EntityRendererProvider.Context p_174420_) {
		super(p_174420_);
		this.spellCircleOuterModel = new SpellCircleModel(p_174420_.bakeLayer(CModelLayers.SPELL_CIRCLE));
	}

	@Override
	public void render(AbstractSpellCircle entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource typeBuffer, int packedLightIn) {
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, packedLightIn);
		matrixStack.pushPose();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, -1.105D, 0.0D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - (entityYaw * 3)));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		VertexConsumer ivertexbuilder = typeBuffer.getBuffer(this.spellCircleOuterModel.renderType(this.getTextureLocation(entity)));
		this.spellCircleOuterModel.renderToBuffer(matrixStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, entity.getSpell().getSpell().getCategory().getrCOlor(), entity.getSpell().getSpell().getCategory().getgCOlor(), entity.getSpell().getSpell().getCategory().getbCOlor(), 1.0F);
		matrixStack.popPose();
		matrixStack.pushPose();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, -1.070D, 0.0D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F + (entityYaw * 2)));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		VertexConsumer ivertexbuilder2 = typeBuffer.getBuffer(this.spellCircleOuterModel.renderType(this.getMiddleRingTexture(entity)));
		this.spellCircleOuterModel.renderToBuffer(matrixStack, ivertexbuilder2, packedLightIn, OverlayTexture.NO_OVERLAY, CombatEntityStats.getManaColorR(entity.getOwner())/255.0f, CombatEntityStats.getManaColorG(entity.getOwner())/255.0f, CombatEntityStats.getManaColorB(entity.getOwner())/255.0f, 1.0F);
		matrixStack.popPose();
		matrixStack.pushPose();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, -1.035D, 0.0D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - (entityYaw * 1)));
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		VertexConsumer ivertexbuilder3 = typeBuffer.getBuffer(this.spellCircleOuterModel.renderType(this.getInnerRingTexture(entity)));
//		this.spellCircleOuterModel.renderToBuffer(matrixStack, ivertexbuilder3, packedLightIn, OverlayTexture.NO_OVERLAY, entity.getSpell().getSpell().getCategory().getrCOlor(), entity.getSpell().getSpell().getCategory().getgCOlor(), entity.getSpell().getSpell().getCategory().getbCOlor(), 1.0F);
		this.spellCircleOuterModel.renderToBuffer(matrixStack, ivertexbuilder3, packedLightIn, OverlayTexture.NO_OVERLAY, CombatEntityStats.getManaColorR(entity.getOwner())/255.0f, CombatEntityStats.getManaColorG(entity.getOwner())/255.0f, CombatEntityStats.getManaColorB(entity.getOwner())/255.0f, 1.0F);
		matrixStack.popPose();
	}

	public ResourceLocation getInnerRingTexture(AbstractSpellCircle entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == SpellCategory.ClassType.PRIMEVAL) {
			return life_inner_ring_textures;
		}
		else return elemental_inner_ring_textures;
	}
	
	public ResourceLocation getMiddleRingTexture(AbstractSpellCircle entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == SpellCategory.ClassType.PRIMEVAL) {
			return life_middle_ring_textures;
		}
		else return elemental_middle_ring_textures;
	}
	
	@Override
	public ResourceLocation getTextureLocation(AbstractSpellCircle entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == SpellCategory.ClassType.PRIMEVAL) {
			return life_outer_ring_textures;
		}
		else return elemental_outer_ring_textures;
	}

}
