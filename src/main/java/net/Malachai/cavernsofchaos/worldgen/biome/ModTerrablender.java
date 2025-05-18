package net.malachai.cavernsofchaos.worldgen.biome;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void  registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(Cavernsofchaos.MODID, "overworld"), 5));
    }
}
