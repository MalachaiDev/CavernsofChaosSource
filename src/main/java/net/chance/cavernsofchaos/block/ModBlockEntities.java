package net.chance.cavernsofchaos.block;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.block.blockentity.ancientChestBlockEntity;
import net.chance.cavernsofchaos.block.blockentity.bugEggBlockEntity;
import net.chance.cavernsofchaos.block.blockentity.dartTrapBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cavernsofchaos.MODID);

    public static final RegistryObject<BlockEntityType<ancientChestBlockEntity>> ANCIENT_CHEST = BLOCK_ENTITIES.register("ancient_chest", () -> BlockEntityType.Builder.of(ancientChestBlockEntity::new, ModBlocks.ANCIENTCHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<bugEggBlockEntity>> BUG_NEST = BLOCK_ENTITIES.register("bug_nest", () -> BlockEntityType.Builder.of(bugEggBlockEntity::new, ModBlocks.BUGEGG.get()).build(null));

    public static final RegistryObject<BlockEntityType<dartTrapBlockEntity>> DART_TRAP = BLOCK_ENTITIES.register("dart_trap", () -> BlockEntityType.Builder.of(dartTrapBlockEntity::new, ModBlocks.DARTTRAP.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}