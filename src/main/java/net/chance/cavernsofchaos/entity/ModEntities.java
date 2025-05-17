package net.chance.cavernsofchaos.entity;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Cavernsofchaos.MODID);

    public static final RegistryObject<EntityType<LizagerEntity>> LIZAGER =
            ENTITY_TYPES.register("lizager", () -> EntityType.Builder.of(LizagerEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1.9f).build("lizager"));
    public static final RegistryObject<EntityType<CaveSlime>> CAVESLIME =
            ENTITY_TYPES.register("caveslime", () -> EntityType.Builder.of(CaveSlime::new, MobCategory.MONSTER)
                    .sized(0.5f,1.9f).build("caveslime"));
    public static final RegistryObject<EntityType<TorchwraithEntity>> TORCHWRAITH =
            ENTITY_TYPES.register("torchwraith", () -> EntityType.Builder.of(TorchwraithEntity::new, MobCategory.MONSTER)
                    .sized(1f,1f).build("torchwraith"));
    public static final RegistryObject<EntityType<flyskullEntity>> FLYSKULL =
            ENTITY_TYPES.register("flyskull", () -> EntityType.Builder.of(flyskullEntity::new, MobCategory.MONSTER)
                    .sized(1f,1f).build("flyskull"));
    public static final RegistryObject<EntityType<BoulderGolemEntity>> BOULDER_GOLEM =
            ENTITY_TYPES.register("bouldergolem", () -> EntityType.Builder.of(BoulderGolemEntity::new, MobCategory.MONSTER)
                    .sized(1f,1f).build("bouldergolem"));
    public static final RegistryObject<EntityType<MimicEntity>> MIMIC =
            ENTITY_TYPES.register("mimic", () -> EntityType.Builder.of(MimicEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1f).build("mimic"));

    public static final RegistryObject<EntityType<glowstickprojectile>> GLOWSTICK_PROJECTILE =
            ENTITY_TYPES.register("glowstickprojectile", () -> EntityType.Builder.<glowstickprojectile>of(glowstickprojectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("glowstickprojectile"));
    public static final RegistryObject<EntityType<nitrobottle_projectile>> NITROBOTTLE_PROJECTILE =
            ENTITY_TYPES.register("nitrobottle_projectile", () -> EntityType.Builder.<nitrobottle_projectile>of(nitrobottle_projectile::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build("nitrobottle_projectile"));
    public static final RegistryObject<EntityType<boomstick_projectile>> BOOMSTICK_PROJECTILE =
            ENTITY_TYPES.register("boomstick_projectile", () -> EntityType.Builder.<boomstick_projectile>of(boomstick_projectile::new, MobCategory.MISC)
                    .sized(0.1f,0.1f).build("boomstick_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }





}
