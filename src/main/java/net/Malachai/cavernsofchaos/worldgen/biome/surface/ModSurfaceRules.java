package net.chance.cavernsofchaos.worldgen.biome.surface;

import net.chance.cavernsofchaos.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.chance.cavernsofchaos.block.ModBlocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class ModSurfaceRules {




    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource GUILD_STONE = makeStateRule(ModBlocks.GUILDSTONE.get());
    private static final SurfaceRules.RuleSource MOSSYCOBBLE = makeStateRule(Blocks.MOSSY_COBBLESTONE);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource PRISMARINE = makeStateRule(Blocks.PRISMARINE);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    public static SurfaceRules.RuleSource makeRules() {




        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60),1);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, SurfaceRules.sequence(

                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT),
                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, DIRT)
                //SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, DIRT)

        )));







        return SurfaceRules.sequence(


                //Surface rules For the guilded chasm
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.GUILDED_CHASM), SurfaceRules.sequence(
                        grassSurface,
                        GUILD_STONE,
                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60),1), BEDROCK)
                )),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SUNKEN_FOREST), SurfaceRules.sequence(
                        grassSurface
                        //SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, MOSSYCOBBLE),
                        //SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, MOSSYCOBBLE)

                )),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ANDESITE_QUARRY), SurfaceRules.sequence(
                        grassSurface,
                        ANDESITE,
                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60),1), BEDROCK)

                ))

        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }

}
