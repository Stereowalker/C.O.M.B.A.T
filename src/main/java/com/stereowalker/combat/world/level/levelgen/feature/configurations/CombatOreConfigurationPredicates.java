package com.stereowalker.combat.world.level.levelgen.feature.configurations;

import com.stereowalker.combat.world.level.block.CBlocks;

import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class CombatOreConfigurationPredicates {
    public static final RuleTest MEZEPINE = new BlockMatchTest(CBlocks.MEZEPINE);
    public static final RuleTest SLYAPHY = new BlockMatchTest(CBlocks.SLYAPHY);
}