package com.stereowalker.combat.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.stereowalker.combat.world.level.block.entity.DisenchantmentTableBlockEntity;

import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisenchantmentTableRenderer implements BlockEntityRenderer<DisenchantmentTableBlockEntity> {
	/** The texture for the book above the enchantment table. */
	@SuppressWarnings("deprecation")
	public static final Material TEXTURE_BOOK = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/enchanting_table_book"));
	private final BookModel bookModel;

	public DisenchantmentTableRenderer(BlockEntityRendererProvider.Context pContext) {
		this.bookModel = new BookModel(pContext.bakeLayer(ModelLayers.BOOK));
	}

	@Override
	public void render(DisenchantmentTableBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5D, 0.75D, 0.5D);
		float f = (float)tileEntityIn.time + partialTicks;
		matrixStackIn.translate(0.0D, (double)(0.1F + Mth.sin(f * 0.1F) * 0.01F), 0.0D);

		float f1;
		for(f1 = tileEntityIn.rot - tileEntityIn.oRot; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
			;
		}

		while(f1 < -(float)Math.PI) {
			f1 += ((float)Math.PI * 2F);
		}

		float f2 = tileEntityIn.oRot + f1 * partialTicks;
		matrixStackIn.mulPose(Vector3f.YP.rotation(-f2));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
		//Flip Book
		//      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		//      matrixStackIn.translate(-0.35D, 0.0D, 0);
		//End
		float f3 = Mth.lerp(partialTicks, tileEntityIn.oFlip, tileEntityIn.flip);
		float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
		float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
		float f6 = Mth.lerp(partialTicks, tileEntityIn.oOpen, tileEntityIn.open);
		this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
		VertexConsumer ivertexbuilder = TEXTURE_BOOK.buffer(bufferIn, RenderType::entitySolid);
		this.bookModel.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.popPose();
	}
}