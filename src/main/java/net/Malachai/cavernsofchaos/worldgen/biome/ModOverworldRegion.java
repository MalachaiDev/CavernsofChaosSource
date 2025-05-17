package net.chance.cavernsofchaos.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;







import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD,weight);
    }




    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();


        // Overlap Vanilla's parameters with our own for our GUILDED_CHASM biome.
        // The parameters for this biome are chosen arbitrarily.
       new ParameterPointListBuilder()
              .temperature(Temperature.FULL_RANGE)
              .humidity(Humidity.FULL_RANGE)
              .continentalness(Continentalness.INLAND)
              .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
              .depth(Depth.FLOOR)
              .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
              .build().forEach(point -> builder.add(point, ModBiomes.GUILDED_CHASM));
       new  ParameterPointListBuilder()
               .temperature(Temperature.FULL_RANGE)
                .humidity(Humidity.FULL_RANGE)
                .continentalness(Continentalness.FAR_INLAND)
                .erosion(Erosion.EROSION_0, Erosion.EROSION_3)
               .depth(Depth.FLOOR)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
                .build().forEach(point -> builder.add(point, ModBiomes.SUNKEN_FOREST));
       new  ParameterPointListBuilder()
                .temperature(Temperature.COOL,Temperature.NEUTRAL)
                .humidity(Humidity.DRY)
                .continentalness(Continentalness.FAR_INLAND)
                .erosion(Erosion.EROSION_0, Erosion.EROSION_3)
                .depth(Depth.FLOOR,Depth.UNDERGROUND)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
                .build().forEach(point -> builder.add(point, ModBiomes.ANDESITE_QUARRY));

        // Add our points to the mapper
        builder.build().forEach(mapper::accept);
    }
}
