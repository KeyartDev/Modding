package org.keyart.example.core.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.core.registry.BlockRegistry;
import org.keyart.example.core.registry.ItemRegistry;
import org.keyart.example.core.registry.VillagerRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = Example.MODID)
public class EEvents {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ItemRegistry.STRAWBERRY.get(), 1), 10, 8, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(ItemRegistry.STRAWBERRY_SEEDS.get(), 3), 20, 9, 0.035f));
        }

        if (event.getType() == VillagerRegistry.FUNC_EXPERT.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ItemRegistry.IT_ITEM.get(), 1), 1, 15, 0.02f
            ));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 30),
                    new ItemStack(ItemRegistry.MYSTIC_CLOCK.get(), 1), 1, 25, 0.02f
            ));
        }
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 3),
                new ItemStack(BlockRegistry.SEVEN_COLOR.get(), 1), 5, 5, 0.2f
        ));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 1),
                new ItemStack(ItemRegistry.SOME_PICKAXE.get(), 1), 1, 10, 0.2f
        ));
    }
}
