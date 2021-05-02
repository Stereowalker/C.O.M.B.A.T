package com.stereowalker.combat.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.api.spell.SpellCategory.ClassType;
import com.stereowalker.combat.client.renderer.entity.model.SpellCircleModel;
import com.stereowalker.combat.entity.CombatEntityStats;
import com.stereowalker.combat.entity.magic.AbstractSpellCircleEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SpellCircleRenderer extends EntityRenderer<AbstractSpellCircleEntity>{
	public static final ResourceLocation elemental_outer_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_outer_ring.png");
	public static final ResourceLocation life_outer_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_outer_ring.png");
	
	public static final ResourceLocation elemental_inner_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_inner_ring.png");
	public static final ResourceLocation life_inner_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_inner_ring.png");
	
	public static final ResourceLocation elemental_middle_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/elemental_middle_ring.png");
	public static final ResourceLocation life_middle_ring_textures = Combat.getInstance().location("textures/entity/spell_circle/life_middle_ring.png");
	
	private final SpellCircleModel spellCircleOuterModel = new SpellCircleModel();

	protected SpellCircleRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(AbstractSpellCircleEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int packedLightIn) {
		matrixStack.push();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, 0.035D, 0.0D);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - (entityYaw * 3)));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));
		IVertexBuilder ivertexbuilder = typeBuffer.getBuffer(this.spellCircleOuterModel.getRenderType(this.getEntityTexture(entity)));
		this.spellCircleOuterModel.render(matrixStack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, entity.getSpell().getSpell().getCategory().getrCOlor(), entity.getSpell().getSpell().getCategory().getgCOlor(), entity.getSpell().getSpell().getCategory().getbCOlor(), 1.0F);
		matrixStack.pop();
		matrixStack.push();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, 0.070D, 0.0D);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F + (entityYaw * 2)));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));
		IVertexBuilder ivertexbuilder2 = typeBuffer.getBuffer(this.spellCircleOuterModel.getRenderType(this.getMiddleRingTexture(entity)));
		this.spellCircleOuterModel.render(matrixStack, ivertexbuilder2, packedLightIn, OverlayTexture.NO_OVERLAY, CombatEntityStats.getManaColorR(entity.getOwner())/255.0f, CombatEntityStats.getManaColorG(entity.getOwner())/255.0f, CombatEntityStats.getManaColorB(entity.getOwner())/255.0f, 1.0F);
		matrixStack.pop();
		matrixStack.push();
		matrixStack.scale(entity.getRadius()/8.0F, 1.0F, entity.getRadius()/8.0F);
		matrixStack.translate(0.0D, 0.105D, 0.0D);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - (entityYaw * 1)));
		matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0F));
		IVertexBuilder ivertexbuilder3 = typeBuffer.getBuffer(this.spellCircleOuterModel.getRenderType(this.getInnerRingTexture(entity)));
//		this.spellCircleOuterModel.render(matrixStack, ivertexbuilder3, packedLightIn, OverlayTexture.NO_OVERLAY, entity.getSpell().getSpell().getCategory().getrCOlor(), entity.getSpell().getSpell().getCategory().getgCOlor(), entity.getSpell().getSpell().getCategory().getbCOlor(), 1.0F);
		this.spellCircleOuterModel.render(matrixStack, ivertexbuilder3, packedLightIn, OverlayTexture.NO_OVERLAY, CombatEntityStats.getManaColorR(entity.getOwner())/255.0f, CombatEntityStats.getManaColorG(entity.getOwner())/255.0f, CombatEntityStats.getManaColorB(entity.getOwner())/255.0f, 1.0F);
		matrixStack.pop();
		super.render(entity, entityYaw, partialTicks, matrixStack, typeBuffer, packedLightIn);
	}

	public ResourceLocation getInnerRingTexture(AbstractSpellCircleEntity entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == ClassType.LIFE) {
			return life_inner_ring_textures;
		}
		else return elemental_inner_ring_textures;
	}
	
	public ResourceLocation getMiddleRingTexture(AbstractSpellCircleEntity entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == ClassType.LIFE) {
			return life_middle_ring_textures;
		}
		else return elemental_middle_ring_textures;
	}
	
	@Override
	public ResourceLocation getEntityTexture(AbstractSpellCircleEntity entity) {
		if (entity.getSpell().getSpell().getCategory().getClassType() == ClassType.LIFE) {
			return life_outer_ring_textures;
		}
		else return elemental_outer_ring_textures;
	}

}
