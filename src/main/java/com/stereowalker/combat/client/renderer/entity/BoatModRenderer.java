package com.stereowalker.combat.client.renderer.entity;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.BoatModModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoatModRenderer extends EntityRenderer<BoatMod> {
   private final Map<BoatMod.Type, Pair<ResourceLocation, BoatModModel>> boatResources;

   public BoatModRenderer(EntityRendererProvider.Context p_173936_) {
      super(p_173936_);
      this.shadowRadius = 0.8F;
      this.boatResources = Stream.of(BoatMod.Type.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
         return p_173938_;
      }, (p_173941_) -> {
         return Pair.of(Combat.getInstance().location("textures/entity/boat/" + p_173941_.getName() + ".png"), new BoatModModel(p_173936_.bakeLayer(CModelLayers.createModdedBoatModelName(p_173941_))));
      }));
   }

   public void render(BoatMod pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      pMatrixStack.pushPose();
      pMatrixStack.translate(0.0D, 0.375D, 0.0D);
      pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - pEntityYaw));
      float f = (float)pEntity.getHurtTime() - pPartialTicks;
      float f1 = pEntity.getDamage() - pPartialTicks;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      if (f > 0.0F) {
         pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)pEntity.getHurtDir()));
      }

      float f2 = pEntity.getBubbleAngle(pPartialTicks);
      if (!Mth.equal(f2, 0.0F)) {
         pMatrixStack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), pEntity.getBubbleAngle(pPartialTicks), true));
      }

      Pair<ResourceLocation, BoatModModel> pair = getModelWithLocation(pEntity);
      ResourceLocation resourcelocation = pair.getFirst();
      BoatModModel boatmodel = pair.getSecond();
      pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
      pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
      boatmodel.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer vertexconsumer = pBuffer.getBuffer(boatmodel.renderType(resourcelocation));
      boatmodel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!pEntity.isUnderWater()) {
         VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RenderType.waterMask());
         boatmodel.waterPatch().render(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
      }

      pMatrixStack.popPose();
      super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
   }

   /**
    * Returns the location of an entity's texture.
    */
   @Deprecated // forge: override getModelWithLocation to change the texture / model
   public ResourceLocation getTextureLocation(BoatMod pEntity) {
      return getModelWithLocation(pEntity).getFirst();
   }

   public Pair<ResourceLocation, BoatModModel> getModelWithLocation(BoatMod boat) { return this.boatResources.get(boat.getModdedBoatType()); }
}