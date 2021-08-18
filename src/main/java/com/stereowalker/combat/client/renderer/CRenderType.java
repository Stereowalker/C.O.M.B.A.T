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
	public static final ResourceLocation LEGENDARY_RES_ITEM_GLINT = Combat.getInstance().location("textures/misc/legendary_item_glint.png");
	private static final RenderType LEGENDARY_ARMOR_GLINT = RenderType.makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType LEGENDARY_ARMOR_ENTITY_GLINT = RenderType.makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType LEGENDARY_GLINT_TRANSLUCENT = RenderType.makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).target(RenderState.ITEM_ENTITY_TARGET).build(false));
	private static final RenderType LEGENDARY_GLINT = RenderType.makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType LEGENDARY_GLINT_DIRECT = RenderType.makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType LEGENDARY_ENTITY_GLINT = RenderType.makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).target(RenderState.ITEM_ENTITY_TARGET).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));
	private static final RenderType LEGENDARY_ENTITY_GLINT_DIRECT = RenderType.makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(LEGENDARY_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));

	public static final ResourceLocation MYTHRIL_RES_ITEM_GLINT = Combat.getInstance().location("textures/misc/powered_mythril_item_glint.png");
	private static final RenderType MYTHRIL_ARMOR_GLINT = RenderType.makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType MYTHRIL_ARMOR_ENTITY_GLINT = RenderType.makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType MYTHRIL_GLINT_TRANSLUCENT = RenderType.makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).target(RenderState.ITEM_ENTITY_TARGET).build(false));
	private static final RenderType MYTHRIL_GLINT = RenderType.makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType MYTHRIL_GLINT_DIRECT = RenderType.makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType MYTHRIL_ENTITY_GLINT = RenderType.makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).target(RenderState.ITEM_ENTITY_TARGET).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));
	private static final RenderType MYTHRIL_ENTITY_GLINT_DIRECT = RenderType.makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));

	public static final ResourceLocation ENCHANTED_MYTHRIL_RES_ITEM_GLINT = Combat.getInstance().location("textures/misc/enchanted_powered_mythril_item_glint.png");
	private static final RenderType ENCHANTED_MYTHRIL_ARMOR_GLINT = RenderType.makeType("armor_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_ARMOR_ENTITY_GLINT = RenderType.makeType("armor_entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).layer(RenderState.VIEW_OFFSET_Z_LAYERING).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_GLINT_TRANSLUCENT = RenderType.makeType("glint_translucent", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).target(RenderState.ITEM_ENTITY_TARGET).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_GLINT = RenderType.makeType("glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_GLINT_DIRECT = RenderType.makeType("glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.GLINT_TEXTURING).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_ENTITY_GLINT = RenderType.makeType("entity_glint", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).target(RenderState.ITEM_ENTITY_TARGET).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));
	private static final RenderType ENCHANTED_MYTHRIL_ENTITY_GLINT_DIRECT = RenderType.makeType("entity_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.getBuilder().texture(new RenderState.TextureState(ENCHANTED_MYTHRIL_RES_ITEM_GLINT, true, false)).writeMask(RenderState.COLOR_WRITE).cull(RenderState.CULL_DISABLED).depthTest(RenderState.DEPTH_EQUAL).transparency(RenderState.GLINT_TRANSPARENCY).texturing(RenderState.ENTITY_GLINT_TEXTURING).build(false));

	
	public static RenderType getLegendaryArmorGlint() {return LEGENDARY_ARMOR_GLINT;}
	public static RenderType getLegendaryArmorEntityGlint() {return LEGENDARY_ARMOR_ENTITY_GLINT;}
	public static RenderType getLegendaryGlintTranslucent() {return LEGENDARY_GLINT_TRANSLUCENT;}
	public static RenderType getLegendaryGlint() {return LEGENDARY_GLINT;}
	public static RenderType getLegendaryGlintDirect() {return LEGENDARY_GLINT_DIRECT;}
	public static RenderType getLegendaryEntityGlint() {return LEGENDARY_ENTITY_GLINT;}
	public static RenderType getLegendaryEntityGlintDirect() {return LEGENDARY_ENTITY_GLINT_DIRECT;}
	
	public static RenderType getMythrilArmorGlint() {return MYTHRIL_ARMOR_GLINT;}
	public static RenderType getMythrilArmorEntityGlint() {return MYTHRIL_ARMOR_ENTITY_GLINT;}
	public static RenderType getMythrilGlintTranslucent() {return MYTHRIL_GLINT_TRANSLUCENT;}
	public static RenderType getMythrilGlint() {return MYTHRIL_GLINT;}
	public static RenderType getMythrilGlintDirect() {return MYTHRIL_GLINT_DIRECT;}
	public static RenderType getMythrilEntityGlint() {return MYTHRIL_ENTITY_GLINT;}
	public static RenderType getMythrilEntityGlintDirect() {return MYTHRIL_ENTITY_GLINT_DIRECT;}
	
	public static RenderType getEncMythrilArmorGlint() {return ENCHANTED_MYTHRIL_ARMOR_GLINT;}
	public static RenderType getEncMythrilArmorEntityGlint() {return ENCHANTED_MYTHRIL_ARMOR_ENTITY_GLINT;}
	public static RenderType getEncMythrilGlintTranslucent() {return ENCHANTED_MYTHRIL_GLINT_TRANSLUCENT;}
	public static RenderType getEncMythrilGlint() {return ENCHANTED_MYTHRIL_GLINT;}
	public static RenderType getEncMythrilGlintDirect() {return ENCHANTED_MYTHRIL_GLINT_DIRECT;}
	public static RenderType getEnchantedMythrilEntityGlint() {return ENCHANTED_MYTHRIL_ENTITY_GLINT;}
	public static RenderType getEncMythrilEntityGlintDirect() {return ENCHANTED_MYTHRIL_ENTITY_GLINT_DIRECT;}
}
