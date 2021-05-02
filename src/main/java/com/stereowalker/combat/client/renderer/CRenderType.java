package com.stereowalker.combat.client.renderer;

import com.stereowalker.combat.Combat;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CRenderType {
	public static final ResourceLocation RES_ITEM_GLINT = Combat.getInstance().location("textures/misc/legendary_item_glint.png");
	private static final RenderType ARMOR_GLINT = RenderType.makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType ARMOR_ENTITY_GLINT = RenderType.makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType GLINT_TRANSLUCENT = RenderType.makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).target(RenderState.ITEM_ENTITY_TARGET).build(false));
	private static final RenderType GLINT = RenderType.makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType GLINT_DIRECT = RenderType.makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType ENTITY_GLINT = RenderType.makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).target(RenderState.ITEM_ENTITY_TARGET).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));
	private static final RenderType ENTITY_GLINT_DIRECT = RenderType.makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));

	public static RenderType getArmorGlint() {
		return ARMOR_GLINT;
	}

	public static RenderType getArmorEntityGlint() {
		return ARMOR_ENTITY_GLINT;
	}

	public static RenderType getGlintTranslucent() {
		return GLINT_TRANSLUCENT;
	}

	public static RenderType getGlint() {
		return GLINT;
	}

	public static RenderType getGlintDirect() {
		return GLINT_DIRECT;
	}

	public static RenderType getEntityGlint() {
		return ENTITY_GLINT;
	}

	public static RenderType getEntityGlintDirect() {
		return ENTITY_GLINT_DIRECT;
	}
//	
//	public static void refisterRenders() {
//		final SortedMap<RenderType, BufferBuilder> fixedBuffers = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (p_228485_1_) -> {
//		      p_228485_1_.put(Atlases.getSolidBlockType(), this.fixedBuilder.getBuilder(RenderType.getSolid()));
//		      p_228485_1_.put(Atlases.getCutoutBlockType(), this.fixedBuilder.getBuilder(RenderType.getCutout()));
//		      p_228485_1_.put(Atlases.getBannerType(), this.fixedBuilder.getBuilder(RenderType.getCutoutMipped()));
//		      p_228485_1_.put(Atlases.getTranslucentCullBlockType(), this.fixedBuilder.getBuilder(RenderType.getTranslucent()));
//		      put(p_228485_1_, Atlases.getShieldType());
//		      put(p_228485_1_, Atlases.getBedType());
//		      put(p_228485_1_, Atlases.getShulkerBoxType());
//		      put(p_228485_1_, Atlases.getSignType());
//		      put(p_228485_1_, Atlases.getChestType());
//		      put(p_228485_1_, RenderType.getTranslucentNoCrumbling());
//		      put(p_228485_1_, RenderType.getArmorGlint());
//		      put(p_228485_1_, RenderType.getArmorEntityGlint());
//		      put(p_228485_1_, RenderType.getGlint());
//		      put(p_228485_1_, RenderType.getGlintDirect());
//		      put(p_228485_1_, RenderType.getGlintTranslucent());
//		      put(p_228485_1_, RenderType.getEntityGlint());
//		      put(p_228485_1_, RenderType.getEntityGlintDirect());
//		      put(p_228485_1_, RenderType.getWaterMask());
//		      ModelBakery.DESTROY_RENDER_TYPES.forEach((p_228488_1_) -> {
//		         put(p_228485_1_, p_228488_1_);
//		      });
//		   });
//		Minecraft.getInstance().getRenderTypeBuffers().fixedBuffers.put(key, value)
//	}
}
