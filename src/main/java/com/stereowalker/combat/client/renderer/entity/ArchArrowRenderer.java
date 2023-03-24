package com.stereowalker.combat.client.renderer.entity;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.world.entity.projectile.ArchArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArchArrowRenderer extends ArrowRenderer<ArchArrow> {
   public static final ResourceLocation[] RES_ARROW = new ResourceLocation[] {Combat.getInstance().location("textures/entity/projectiles/explosive_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/flame_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/freeze_arrow.png"), Combat.getInstance().location("textures/entity/projectiles/teleport_arrow.png")};

   public ArchArrowRenderer(EntityRendererProvider.Context p_173964_) {
      super(p_173964_);
   }

   @Override
	public ResourceLocation getTextureLocation(ArchArrow entity) {
      return RES_ARROW[entity.getArchType().ordinal()];
   }
}