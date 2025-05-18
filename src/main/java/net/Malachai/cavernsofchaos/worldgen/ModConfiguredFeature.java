package net.malachai.cavernsofchaos.worldgen;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeature {

    public static final ResourceKey<ConfiguredFeature<?, ?>> GEMSTONE_ORE_KEY = registerKey("gemstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRATES_KEY = registerKey("crates");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest guildstonereplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> gemstoneores = List.of(OreConfiguration.target(guildstonereplaceable,
                ModBlocks.GEMSTONE_ORE.get().defaultBlockState()));

        register(context, GEMSTONE_ORE_KEY, Feature.ORE,new OreConfiguration(gemstoneores, 9));

    }




    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Cavernsofchaos.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
