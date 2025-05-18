package net.malachai.cavernsofchaos.worldgen.biome;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;

public class ModBiomes {
    public static final ResourceKey<Biome> GUILDED_CHASM = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(Cavernsofchaos.MODID, "guilded_chasm"));
    public static final ResourceKey<Biome> SUNKEN_FOREST = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(Cavernsofchaos.MODID, "sunken_forest"));;
    public static final ResourceKey<Biome> ANDESITE_QUARRY = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(Cavernsofchaos.MODID, "andesite_quarry"));;
    public static final ResourceKey<Biome> BLUE_HOLE = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(Cavernsofchaos.MODID, "bluehole"));;

    public static void  bootstrap(BootstapContext<Biome> context) {
        context.register(GUILDED_CHASM, guildedChasm(context));
        context.register(SUNKEN_FOREST, sunkenforest(context));
        context.register(ANDESITE_QUARRY, andesitequarry(context));
    }

    private static Biome guildedChasm(BootstapContext<Biome> context) {
        return null;
    }
    private static Biome sunkenforest(BootstapContext<Biome> context) {
        return null;
    }
    private static Biome andesitequarry(BootstapContext<Biome> context) {
        return null;
    }
    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome testBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);


        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }
}
