package net.malachai.cavernsofchaos.event;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.entity.ModEntities;
import net.malachai.cavernsofchaos.entity.custom.*;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
