package com.stereowalker.combat.client.model;

import com.google.common.collect.ImmutableList;
import com.stereowalker.combat.world.entity.vehicle.BoatMod;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BoatModModel extends ListModel<BoatMod> {
   private static final String LEFT_PADDLE = "left_paddle";
   private static final String RIGHT_PADDLE = "right_paddle";
   private static final String WATER_PATCH = "water_patch";
   private static final String BOTTOM = "bottom";
   private static final String BACK = "back";
   private static final String FRONT = "front";
   private static final String RIGHT = "right";
   private static final String LEFT = "left";
   private final ModelPart leftPaddle;
   private final ModelPart rightPaddle;
   private final ModelPart waterPatch;
   private final ImmutableList<ModelPart> parts;

   public BoatModModel(ModelPart p_170462_) {
      this.leftPaddle = p_170462_.getChild("left_paddle");
      this.rightPaddle = p_170462_.getChild("right_paddle");
      this.waterPatch = p_170462_.getChild("water_patch");
      this.parts = ImmutableList.of(p_170462_.getChild("bottom"), p_170462_.getChild("back"), p_170462_.getChild("front"), p_170462_.getChild("right"), p_170462_.getChild("left"), this.leftPaddle, this.rightPaddle);
   }

   /**
    * Sets this entity's model rotation angles
    */
   public void setupAnim(BoatMod pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      animatePaddle(pEntity, 0, this.leftPaddle, pLimbSwing);
      animatePaddle(pEntity, 1, this.rightPaddle, pLimbSwing);
   }

   public ImmutableList<ModelPart> parts() {
      return this.parts;
   }

   public ModelPart waterPatch() {
      return this.waterPatch;
   }

   private static void animatePaddle(BoatMod p_170465_, int p_170466_, ModelPart p_170467_, float p_170468_) {
      float f = p_170465_.getRowingTime(p_170466_, p_170468_);
      p_170467_.xRot = Mth.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, (Mth.sin(-f) + 1.0F) / 2.0F);
      p_170467_.yRot = Mth.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
      if (p_170466_ == 1) {
         p_170467_.yRot = (float)Math.PI - p_170467_.yRot;
      }

   }
}