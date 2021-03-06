package com.stereowalker.combat.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.stereowalker.combat.Combat;
import com.stereowalker.combat.compat.curios.CuriosCompat;
import com.stereowalker.combat.tileentity.AbstractEnergyContainerTileEntity;
import com.stereowalker.combat.tileentity.CustomEnergyStorage;
import com.stereowalker.unionlib.util.ModHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "combat")
public class AttachCapabilityEvent {
	@SubscribeEvent
	public static void attachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		if (ModHelper.isCuriosLoaded()) {
			if (CuriosCompat.shouldAttachCapability(event.getObject()))
				event.addCapability(CuriosCompat.getIdItem(), CuriosCompat.attachSupportForCurios(event.getObject()));
		}
	}
	
	@SubscribeEvent
	public static void attachTileEntityCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
		if (attachSupportForTE(event.getObject()) != null) {
			event.addCapability(Combat.getInstance().location("tile_entity"), attachSupportForTE(event.getObject()));
		}
	}
	
	public static ICapabilityProvider attachSupportForTE(TileEntity te) {
		if (te instanceof AbstractEnergyContainerTileEntity) {
			AbstractEnergyContainerTileEntity container = (AbstractEnergyContainerTileEntity) te;
			CustomEnergyStorage storage = new CustomEnergyStorage(container.getMaxEnergyStored(), container.canReceive()?10:0, container.canExtract()?10:0, container.getEnergyStored(), container);
			ICapabilityProvider provider = new ICapabilityProvider() {
				private final LazyOptional<IEnergyStorage> energyOpt = LazyOptional.of(() -> storage);
				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
						@Nullable Direction side) {
					return CapabilityEnergy.ENERGY.orEmpty(cap, energyOpt);
				}
			};
			return provider;
		}
		return null;
	}
}
