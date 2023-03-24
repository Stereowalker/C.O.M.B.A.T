package com.stereowalker.combat.client.renderer;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.stereowalker.combat.Combat;
import com.stereowalker.combat.client.model.BackpackModel;
import com.stereowalker.combat.client.model.QuiverModel;
import com.stereowalker.combat.client.model.RoundShieldModel;
import com.stereowalker.combat.client.model.SpearModel;
import com.stereowalker.combat.client.model.geom.CModelLayers;
import com.stereowalker.combat.world.item.CItems;

import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CombatBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
	public BackpackModel backpack;
	public QuiverModel quiver;
	private SpearModel spear;
	private RoundShieldModel modelRoundShield;
	private ShieldModel modelTowerShield;
		@SuppressWarnings("deprecation")
		public static final Material LOCATION_ROUND_SHIELD_BASE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(Combat.MODID, "textures/entity/shield_base.png"));
		@SuppressWarnings("deprecation")
		public static final Material LOCATION_ROUND_SHIELD_NO_PATTERN = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(Combat.MODID, "textures/entity/shield_base_nopattern.png"));


	public CombatBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
		super(pBlockEntityRenderDispatcher, pEntityModelSet);
	}

	@Override
	public void onResourceManagerReload(ResourceManager pResourceManager) {
		this.modelTowerShield = new ShieldModel(this.entityModelSet.bakeLayer(ModelLayers.SHIELD));
		this.modelRoundShield = new RoundShieldModel(this.entityModelSet.bakeLayer(CModelLayers.ROUND_SHIELD));
		this.spear = new SpearModel(this.entityModelSet.bakeLayer(CModelLayers.SPEAR));
		this.backpack = new BackpackModel(this.entityModelSet.bakeLayer(CModelLayers.BACKPACK));
		this.quiver = new QuiverModel(this.entityModelSet.bakeLayer(CModelLayers.QUIVER));
	}

	@Override
	public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Item item = itemStackIn.getItem();
		if (item instanceof BlockItem) {

		} else {
			if (item == CItems.ROUND_SHIELD) {
				boolean flag = itemStackIn.getTagElement("BlockEntityTag") != null;
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				Material material = flag ? LOCATION_ROUND_SHIELD_BASE : LOCATION_ROUND_SHIELD_NO_PATTERN;
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				if (flag) {
					List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(itemStackIn), BannerBlockEntity.getItemPatterns(itemStackIn));
					BannerRenderer.renderPatterns(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, this.modelRoundShield.plate(), material, false, list);
				} else {
					this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				}
				matrixStackIn.popPose();
			} else if (item == CItems.IRON_TOWER_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelTowerShield.renderType(Combat.getInstance().location("textures/entity/iron_tower_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelTowerShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.IRON_ROUND_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/iron_round_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.GOLDEN_TOWER_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelTowerShield.renderType(Combat.getInstance().location("textures/entity/golden_tower_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelTowerShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.GOLDEN_ROUND_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/golden_round_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.BRONZE_TOWER_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelTowerShield.renderType(Combat.getInstance().location("textures/entity/bronze_tower_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelTowerShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.BRONZE_ROUND_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/bronze_round_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.STEEL_TOWER_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelTowerShield.renderType(Combat.getInstance().location("textures/entity/steel_tower_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelTowerShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.STEEL_ROUND_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/steel_round_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.LOZYNE_TOWER_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelTowerShield.renderType(Combat.getInstance().location("textures/entity/lozyne_tower_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelTowerShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelTowerShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.LOZYNE_ROUND_SHIELD) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(0, 1.5, 0);
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferIn, this.modelRoundShield.renderType(Combat.getInstance().location("textures/entity/lozyne_round_shield_base_nopattern.png")), false, itemStackIn.hasFoil());
				this.modelRoundShield.handle().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				this.modelRoundShield.plate().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			} else if (item == CItems.SPEAR) {
				matrixStackIn.pushPose();
				matrixStackIn.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(bufferIn, this.spear.renderType(Combat.Locations.SPEAR_TEXTURE), false, itemStackIn.hasFoil());
				this.spear.renderToBuffer(matrixStackIn, ivertexbuilder1, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
				matrixStackIn.popPose();
			}
		}
	}
}