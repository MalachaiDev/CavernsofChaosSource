package net.chance.cavernsofchaos.block;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.block.advanced.*;
import net.chance.cavernsofchaos.block.blockentity.ancientChestBlock;
import net.chance.cavernsofchaos.block.blockentity.bugEggBlock;
import net.chance.cavernsofchaos.block.blockentity.dartTrapBlock;

import net.chance.cavernsofchaos.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Cavernsofchaos.MODID);
    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    public static final  RegistryObject<Block> GUILDSTONE = registerBlock("guildstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE).requiresCorrectToolForDrops().strength(2.0F,6.0F)));;
    public static final  RegistryObject<Block> MOSSYGUILDSTONE = registerBlock("mossyguildstone",
            () -> new GrassBlock(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));
    public static final  RegistryObject<Block> GEMSTONE_ORE = registerBlock("gemstoneore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GILDED_BLACKSTONE)));;
    public static final  RegistryObject<Block> GEMSTONEBLOCK = registerBlock("gemstoneblock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS)));;
    public static final  RegistryObject<Block> GOLDEN_BRICKS = registerBlock("goldbricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> NITRO_CRATE = registerBlock("nitrog",
            () -> new nitroblock(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final  RegistryObject<Block> PUNJI = registerBlock("punji",
            () -> new punjiblock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final  RegistryObject<Block> FAKESTONE = registerBlock("fakestone",
            () -> new fakestone(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final  RegistryObject<Block> SLOTMACHINE = registerBlock("slotmachine",
            () -> new slotmachine(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final  RegistryObject<Block> COINPILE = registerBlock("coinpile",
            () -> new coinpile(BlockBehaviour.Properties.of().instabreak().sound(SoundType.CHAIN)));
    public static final  RegistryObject<Block> COINSTACK = registerBlock("coinstack",
            () -> new coinstack(BlockBehaviour.Properties.of().strength(0.2f,0).sound(SoundType.CHAIN)));
    public static final  RegistryObject<Block> COINMOUND = registerBlock("coinmound",
            () -> new FallingBlock(BlockBehaviour.Properties.of().strength(0.6f,0).sound(SoundType.CHAIN)));
    public static final  RegistryObject<Block> GLOWSTICKBLOCK = registerBlock("glowstick",
            () -> new glowstickblock(BlockBehaviour.Properties.of().instabreak().noCollission().sound(SoundType.LILY_PAD).lightLevel((p_50884_) -> {
                return 10;
            })));
    public static final  RegistryObject<Block> APOTHICARYTABLE = registerBlock("apothicary_table",
            () -> new apothicaryblock(BlockBehaviour.Properties.of().strength(2f,0).sound(SoundType.STONE)));
    public static final  RegistryObject<Block> BUGEGG = registerBlock("bugegg",
            () -> new bugEggBlock(BlockBehaviour.Properties.of().strength(0.6f,0).sound(SoundType.SLIME_BLOCK)));
    public static final  RegistryObject<Block> BILLBASS = registerBlock("billbass",
            () -> new billbass(BlockBehaviour.Properties.of().strength(0.6f,0).sound(SoundType.WOOD)));
    public static final  RegistryObject<Block> ANCIENTCHEST = registerBlock("ancient_chest",
            () -> new ancientChestBlock(BlockBehaviour.Properties.of().strength(2f,2).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final  RegistryObject<Block> BEARTRAP = registerBlock("beartrap",
            () -> new beartrapBlock(BlockBehaviour.Properties.of().strength(2f,2).sound(SoundType.CHAIN)));
    public static final  RegistryObject<Block> DARTTRAP = registerBlock("darttrap",
            () -> new dartTrapBlock(BlockBehaviour.Properties.of().strength(2f,2).sound(SoundType.STONE)));




    public static final  RegistryObject<Block> GOLDEN_WALL = registerBlock("goldwall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> GOLDEN_STAIRS = registerBlock("goldstairs",
            () -> new StairBlock(() ->ModBlocks.GOLDEN_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> GOLDEN_SLAB = registerBlock("goldslab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> CARVED_GOLDEN_BRICKS = registerBlock("carvedguildstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> GUILD_TILE = registerBlock("guildtile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));;
    public static final  RegistryObject<Block> PLANKED_STONE = registerBlock("plankstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));;
    public static final  RegistryObject<Block> CRATE = registerBlock("crate",
            () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).destroyTime(0.5f)));;
    public static final  RegistryObject<Block> CAVEPOT = registerBlock("cavepot",
            () -> new cavepot(BlockBehaviour.Properties.of().instabreak().sound(SoundType.GLASS)));;
    public static final  RegistryObject<Block> BLUESHROOM = registerBlock("blueshroom",
            () -> new BushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).lightLevel((p_50892_) -> {
                return 2;
            }).hasPostProcess(ModBlocks::always).pushReaction(PushReaction.DESTROY)));

    private static  <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockitem(name, toReturn);
        return toReturn;
    }


    private static <T extends  Block>RegistryObject<Item> registerBlockitem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void  register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
