package net.chance.cavernsofchaos.event;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.block.ModBlocks;
import net.chance.cavernsofchaos.entity.ModEntities;
import net.chance.cavernsofchaos.entity.client.ModModelLayers;
import net.chance.cavernsofchaos.entity.client.boulderGolemModel;
import net.chance.cavernsofchaos.entity.custom.*;
import net.chance.cavernsofchaos.item.ModItems;
import net.chance.cavernsofchaos.particle.ModParticles;
import net.chance.cavernsofchaos.particle.custom.LeafParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Cavernsofchaos.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LIZAGER.get(), LizagerEntity.createAttributes().build());
        event.put(ModEntities.TORCHWRAITH.get(), TorchwraithEntity.createAttributes().build());
        event.put(ModEntities.FLYSKULL.get(), flyskullEntity.createAttributes().build());
        event.put(ModEntities.BOULDER_GOLEM.get(), BoulderGolemEntity.createAttributes().build());
        event.put(ModEntities.MIMIC.get(), MimicEntity.createAttributes().build());
        event.put(ModEntities.CAVESLIME.get(), CaveSlime.createAttributes().build());
        }
    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.CAVESLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaveSlime::checkCaveSlimeSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.FLYSKULL.get(), SpawnPlacements.Type.NO_RESTRICTIONS,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, flyskullEntity::checkCaveSpawnRules,SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TORCHWRAITH.get(), SpawnPlacements.Type.NO_RESTRICTIONS,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TorchwraithEntity::checkCaveSpawnRules,SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.BOULDER_GOLEM.get(), SpawnPlacements.Type.NO_RESTRICTIONS,Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BoulderGolemEntity::checkCaveSpawnRules,SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
