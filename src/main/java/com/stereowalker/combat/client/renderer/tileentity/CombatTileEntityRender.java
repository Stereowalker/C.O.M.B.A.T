package com.stereowalker.combat.client.renderer.tileentity;

import com.stereowalker.combat.tileentity.CTileEntityType;

import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class CombatTileEntityRender {
	public static void registerRenders(){
		ClientRegistry.bindTileEntityRenderer(CTileEntityType.DISENCHANTING_TABLE, DisenchantmentTableTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(CTileEntityType.PODIUM, PodiumTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(CTileEntityType.SIGN, SignTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(CTileEntityType.CONNECTOR, ConnectorTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(CTileEntityType.MYTHRIL_CHARGER, MythrilChargerTileEntityRenderer::new);
	}
}