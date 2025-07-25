package org.keyart.example.core.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;
import org.keyart.example.common.commands.ThirstCommand;
import org.keyart.example.common.entity.AnkiEntity;
import org.keyart.example.core.custom.thirst.PlayerThirst;
import org.keyart.example.core.custom.thirst.PlayerThirstProvider;
import org.keyart.example.core.network.ENetworks;
import org.keyart.example.core.network.packets.ThirstDataSyncS2CPacket;
import org.keyart.example.core.registry.BlockRegistry;
import org.keyart.example.core.registry.EntityRegistry;
import org.keyart.example.core.registry.ItemRegistry;
import org.keyart.example.core.registry.VillagerRegistry;

import java.util.List;


public class EModEvents {
    @Mod.EventBusSubscriber(modid = Example.MODID)
    public static class ForgeBusEvents {
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

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player player) {
                if (!event.getObject().getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()) {
                    event.addCapability(ResourceLocation.fromNamespaceAndPath(Example.MODID, "properties"), new PlayerThirstProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
            event.getOriginal().invalidateCaps();
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerThirst.class);
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.SERVER) {
                event.player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    if (thirst.getThirst() > 0 && event.player.tickCount % 200 == 0 && event.phase == TickEvent.Phase.END && ((ServerPlayer) event.player).gameMode.isSurvival()) {
                        thirst.subThirst(1);
                        ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), ((ServerPlayer) event.player));
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if (!(event.getLevel().isClientSide)) {
                if (event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            ThirstCommand.register(event.getDispatcher());
        }
    }

    @Mod.EventBusSubscriber(modid = Example.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(EntityRegistry.ANKI.get(), AnkiEntity.createAttributes().build());
        }
    }
}
