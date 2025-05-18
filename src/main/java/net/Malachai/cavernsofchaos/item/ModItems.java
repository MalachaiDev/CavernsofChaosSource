package net.malachai.cavernsofchaos.item;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.malachai.cavernsofchaos.item.advanced.ancientcoins;
import net.malachai.cavernsofchaos.item.advanced.boomstickItem;
import net.malachai.cavernsofchaos.item.advanced.glowstickitem;
import net.malachai.cavernsofchaos.item.advanced.nitrobottleitem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.Items.registerItem;

public class ModItems  {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Cavernsofchaos.MODID);

    public static final RegistryObject<Item> GEMSTONES = ITEMS.register("gemstones",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BUCKSHOT = ITEMS.register("buckshot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<BlockItem> ANCIENTCOINS = ITEMS.register("ancientcoins",
            () -> new ancientcoins(ModBlocks.COINPILE.get(), (new Item.Properties()).stacksTo(99)));
    public static final RegistryObject<Item> GLOWSTICKITEM = ITEMS.register("glowstickitem",
            () -> new glowstickitem(new Item.Properties()));
    public static final RegistryObject<Item> NITROBOTTLE = ITEMS.register("nitrobottle",
            () -> new nitrobottleitem(new Item.Properties()));
    public static final RegistryObject<Item> BOOMSTICK = ITEMS.register("boomstick",
            () -> new boomstickItem(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
