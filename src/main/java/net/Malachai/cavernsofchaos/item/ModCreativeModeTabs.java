package net.malachai.cavernsofchaos.item;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cavernsofchaos.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("cavernsofchaos",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.GUILDSTONE.get()))
                        .title(Component.translatable("creativetab.cavernsofchaos"))
                        .displayItems((pParameters, pOutput) -> {

                            pOutput.accept(ModBlocks.GUILDSTONE.get());
                            pOutput.accept(ModBlocks.MOSSYGUILDSTONE.get());
                            pOutput.accept(ModBlocks.GEMSTONE_ORE.get());
                            pOutput.accept(ModBlocks.GEMSTONEBLOCK.get());
                            pOutput.accept(ModBlocks.GOLDEN_BRICKS.get());
                            pOutput.accept(ModBlocks.GOLDEN_STAIRS.get());
                            pOutput.accept(ModBlocks.GOLDEN_SLAB.get());
                            pOutput.accept(ModBlocks.GOLDEN_WALL.get());
                            pOutput.accept(ModBlocks.CARVED_GOLDEN_BRICKS.get());
                            pOutput.accept(ModBlocks.GUILD_TILE.get());
                            pOutput.accept(ModBlocks.PLANKED_STONE.get());
                            pOutput.accept(ModBlocks.CRATE.get());
                            pOutput.accept(ModBlocks.CAVEPOT.get());
                            pOutput.accept(ModBlocks.NITRO_CRATE.get());
                            pOutput.accept(ModBlocks.PUNJI.get());
                            pOutput.accept(ModBlocks.FAKESTONE.get());
                            pOutput.accept(ModBlocks.SLOTMACHINE.get());
                            pOutput.accept(ModBlocks.BUGEGG.get());
                            pOutput.accept(ModBlocks.ANCIENTCHEST.get());
                            pOutput.accept(ModBlocks.BEARTRAP.get());
                            pOutput.accept(ModBlocks.DARTTRAP.get());
                            pOutput.accept(ModBlocks.APOTHICARYTABLE.get());

                            pOutput.accept(ModItems.BOOMSTICK.get());
                            pOutput.accept(ModItems.BUCKSHOT.get());
                            pOutput.accept(ModItems.GEMSTONES.get());
                            pOutput.accept(ModItems.ANCIENTCOINS.get());
                            pOutput.accept(ModBlocks.COINSTACK.get());
                            pOutput.accept(ModBlocks.COINMOUND.get());
                            pOutput.accept(ModItems.GLOWSTICKITEM.get());
                            pOutput.accept(ModItems.NITROBOTTLE.get());
                            pOutput.accept(ModBlocks.BLUESHROOM.get());
                            pOutput.accept(ModBlocks.BILLBASS.get());
                        })
                        .build());

    public static void  register(IEventBus eventBus) {
                CREATIVE_MODE_TABS.register(eventBus);
    };
}
