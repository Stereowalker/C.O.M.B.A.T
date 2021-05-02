package com.stereowalker.combat.client.keybindings;

import java.util.function.BooleanSupplier;

import net.minecraftforge.client.settings.IKeyConflictContext;

public class ToggleableKeyBinding extends net.minecraft.client.settings.ToggleableKeyBinding {

	public ToggleableKeyBinding(String descriptionIn, IKeyConflictContext keyConflictContext, int codeIn, String categoryIn, BooleanSupplier getterIn) {
		super(descriptionIn, codeIn, categoryIn, getterIn);
		this.setKeyConflictContext(keyConflictContext);
	}

}
