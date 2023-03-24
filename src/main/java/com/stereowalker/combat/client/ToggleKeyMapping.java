package com.stereowalker.combat.client;

import java.util.function.BooleanSupplier;

import net.minecraftforge.client.settings.IKeyConflictContext;

public class ToggleKeyMapping extends net.minecraft.client.ToggleKeyMapping {

	public ToggleKeyMapping(String descriptionIn, IKeyConflictContext keyConflictContext, int codeIn, String categoryIn, BooleanSupplier getterIn) {
		super(descriptionIn, codeIn, categoryIn, getterIn);
		this.setKeyConflictContext(keyConflictContext);
	}

}
