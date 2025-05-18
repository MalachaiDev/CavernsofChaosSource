package net.malachai.cavernsofchaos.event;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.client.*;
import net.malachai.cavernsofchaos.particle.ModParticles;
import net.malachai.cavernsofchaos.particle.custom.LeafParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cavernsofchaos.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.LIZAGER_LAYER, lizagermodel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TORCHWRAITH_LAYER, TorchwraithModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FLYSKULL_LAYER, flyskullmodel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BOULDERGOLEM_LAYER, boulderGolemModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MIMIC_LAYER, MimicModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CAVESLIME_OUTER, CaveSlimeModel::createOuterBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CAVESLIME_LAYER, CaveSlimeModel::createInnerBodyLayer);
    }
    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.LEAF_PARTICLES.get(), LeafParticles.Provider::new);
    }
}