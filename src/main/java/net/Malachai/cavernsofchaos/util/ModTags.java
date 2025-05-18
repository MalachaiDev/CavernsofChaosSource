package net.malachai.cavernsofchaos.util;

import net.malachai.cavernsofchaos.Cavernsofchaos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> GUILDEDBLOCKS = tag("me");
        public static final TagKey<Block> weak_lights = tag("weak_lights");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Cavernsofchaos.MODID, name));
        }

        public static class Items {

            private static TagKey<Item> tag(String name) {
                return ItemTags.create(new ResourceLocation(Cavernsofchaos.MODID, name));
            }
        }
    }
}
