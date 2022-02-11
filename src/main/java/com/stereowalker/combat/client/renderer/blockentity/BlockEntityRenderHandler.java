package com.stereowalker.combat.client.renderer.blockentity;

import com.stereowalker.combat.world.level.block.entity.CBlockEntityType;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRenderHandler {
	public static void registerRenders(){
		BlockEntityRenderers.register(CBlockEntityType.DISENCHANTING_TABLE, DisenchantmentTableRenderer::new);
		BlockEntityRenderers.register(CBlockEntityType.PODIUM, PodiumRenderer::new);
		BlockEntityRenderers.register(CBlockEntityType.SIGN, SignRenderer::new);
		BlockEntityRenderers.register(CBlockEntityType.CONNECTOR, ConnectorRenderer::new);
		BlockEntityRenderers.register(CBlockEntityType.MYTHRIL_CHARGER, MythrilChargerRenderer::new);
	}
}