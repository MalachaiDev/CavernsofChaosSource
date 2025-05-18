package net.malachai.cavernsofchaos.particle;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
public static final  DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
        DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Cavernsofchaos.MODID);


    public static final  RegistryObject<SimpleParticleType> LEAF_PARTICLES =
            PARTICLE_TYPES.register("leaf_particle", ()-> new SimpleParticleType(true));



    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}