package com.stereowalker.combat.compat.terrablender;

import com.mojang.datafixers.util.Pair;
import com.stereowalker.combat.world.level.biome.CBiomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.List;
import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class CombatR extends Region
{
    public CombatR(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
    	mapper.accept(Pair.of(Climate.parameters(Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.1F, 0.15F), Climate.Parameter.span(-0.11F, 0.3F), Climate.Parameter.span(-0.7799F, -0.375F), Climate.Parameter.point(1F), Climate.Parameter.span(-1F, -0.9333F), 0), CBiomes.MAGIC_FOREST));
    	mapper.accept(Pair.of(Climate.parameters(Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.1F, 0.15F), Climate.Parameter.span(-0.11F, 0.3F), Climate.Parameter.span(-0.7799F, -0.375F), Climate.Parameter.point(0F), Climate.Parameter.span(-1F, -0.9333F), 0), CBiomes.MAGIC_FOREST));
    }
}