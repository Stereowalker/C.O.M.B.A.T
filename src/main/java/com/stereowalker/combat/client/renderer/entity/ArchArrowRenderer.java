package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.entity.projectile.ArchArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArchArrowRenderer extends ArrowRenderer<ArchArrowEntity> {
   public static final ResourceLocation[] RES_ARROW = new ResourceLocation[] {Combat.getInstance().location("textures/entity/projectiles/explosive_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/flame_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/freeze_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/teleport_arrow.png")};

   public ArchArrowRenderer(EntityRendererManager manager) {
      super(manager);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   public ResourceLocation getEntityTexture(ArchArrowEntity entity) {
      return RES_ARROW[entity.getArchType().ordinal()];
   }
}