package net.chance.cavernsofchaos.event;

import net.chance.cavernsofchaos.Cavernsofchaos;
import net.chance.cavernsofchaos.block.ModBlocks;
import net.chance.cavernsofchaos.item.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
@Mod.EventBusSubscriber(modid = Cavernsofchaos.MODID)
public class ModEvents{






    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event){
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModBlocks.COINSTACK.get(), 5),
                new ItemStack(Items.EMERALD,1),
                10,2,0.2f));

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModBlocks.COINMOUND.get(), 1),
                new ItemStack(Items.EMERALD,2),
                6,2,0.2f));

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModBlocks.COINSTACK.get(), 32),
                new ItemStack(ModItems.BOOMSTICK.get(),1),
                1,2,0.2f));

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModBlocks.COINSTACK.get(), 2),
                new ItemStack(ModItems.BUCKSHOT.get(),2),
                6,2,0.2f));
        
        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModBlocks.COINSTACK.get(), 16),
                new ItemStack(ModBlocks.BILLBASS.get(),2),
                1,2,0.2f));

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModItems.ANCIENTCOINS.get(), 3),
                new ItemStack(ModBlocks.BLUESHROOM.get(),1),
                15,2,0.2f));

        genericTrades.add((pTrader, pRandom)-> new MerchantOffer(
                new ItemStack(ModItems.GEMSTONES.get(), 2),
                new ItemStack(ModItems.ANCIENTCOINS.get(),1),
                99,2,0.2f));

    }
}
