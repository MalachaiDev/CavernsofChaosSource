package net.chance.cavernsofchaos.worldgen.biome;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void  registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(Cavernsofchaos.MODID, "overworld"), 5));
    }
}
