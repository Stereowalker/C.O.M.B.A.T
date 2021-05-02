package com.stereowalker.combat.client.renderer.tileentity;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.renderer.entity.model.RoundShieldModel;
import com.stereowalker.combat.client.renderer.entity.model.SpearModel;
import com.stereowalker.combat.item.CItems;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CombatItemStackTileEntityRender extends ItemStackTileEntityRenderer{
	public static ItemStackTileEntityRenderer instance = new CombatItemStackTileEntityRender();
	private final SpearModel spear = new SpearModel();
	private final RoundShieldModel modelRoundShield = new RoundShieldModel();
	private final ShieldModel modelTowerShield = new ShieldModel();
	@SuppressWarnings("deprecation")
	public static final RenderMaterial LOCATION_ROUND_SHIELD_BASE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, Combat.getInstance().location("textures/entity/shield_base.png"));
	@SuppressWarnings("deprecation")
	public static final RenderMaterial LOCATION_ROUND_SHIELD_NO_PATTERN = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, Combat.getInstance().location("textures/entity/shield_base_nopattern.png"));

	@Override
	public void /*render*/func_239207_a_(ItemStack itemStackIn, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Item item = itemStackIn.getItem();
		if (item instanceof BlockItem) {

		} else {
			if (item == CItems.ROUND_SHIELD) {
				boolean flag = itemStackIn.getChildTag("BlockEntityTag") != null;
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				RenderMaterial material = flag ? LOCATION_ROUND_SHIELD_BASE : LOCATION_ROUND_SHIELD_NO_PATTERN;
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				if (flag) {
					List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(ShieldItem.getColor(itemStackIn), BannerTileEntity.getPatternData(itemStackIn));
					BannerTileEntityRenderer.func_230180_a_(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.modelRoundShield.func_228293_a_(), material, false, list);
				} else {
					this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				}
				matrixStackIn.pop();
			} else if (item == CItems.IRON_TOWER_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelTowerShield.getRenderType(Combat.getInstance().location("textures/entity/iron_tower_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelTowerShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.IRON_ROUND_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/iron_round_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.GOLDEN_TOWER_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelTowerShield.getRenderType(Combat.getInstance().location("textures/entity/golden_tower_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelTowerShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.GOLDEN_ROUND_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/golden_round_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.BRONZE_TOWER_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelTowerShield.getRenderType(Combat.getInstance().location("textures/entity/bronze_tower_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelTowerShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.BRONZE_ROUND_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/bronze_round_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.STEEL_TOWER_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelTowerShield.getRenderType(Combat.getInstance().location("textures/entity/steel_tower_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelTowerShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.STEEL_ROUND_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/steel_round_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.LOZYNE_TOWER_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelTowerShield.getRenderType(Combat.getInstance().location("textures/entity/lozyne_tower_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelTowerShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.LOZYNE_ROUND_SHIELD) {
				matrixStackIn.push();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelRoundShield.getRenderType(Combat.getInstance().location("textures/entity/lozyne_round_shield_base_nopattern.png")), false, itemStackIn.hasEffect());
				this.modelRoundShield.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			} else if (item == CItems.SPEAR) {
				matrixStackIn.push();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				IVertexBuilder ivertexbuilder1 = ItemRenderer.getBuffer(bufferIn, this.spear.getRenderType(SpearModel.TEXTURE_LOCATION), false, itemStackIn.hasEffect());
				this.spear.render(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.pop();
			}
		}
	}
}