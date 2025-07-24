package org.keyart.example.core.network.packets;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import org.keyart.example.core.custom.thirst.PlayerThirstProvider;
import org.keyart.example.core.network.ENetworks;

import java.util.function.Supplier;

public class DrinkWaterC2SPacket {
    public DrinkWaterC2SPacket() {

    }

    public DrinkWaterC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE SERVER!!!
            ServerPlayer serverPlayer = context.getSender();
            ServerLevel serverLevel = serverPlayer.serverLevel().getLevel();

            if (serverLevel.getBlockState(serverPlayer.getOnPos()).is(Blocks.DIAMOND_BLOCK)) {
                serverPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    thirst.addThirst(1);
                    serverPlayer.sendSystemMessage(Component.literal("Жажда: " + thirst.getThirst())
                            .withStyle(ChatFormatting.DARK_AQUA));
                    ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), serverPlayer);
                });

            } else {
                serverPlayer.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                    serverPlayer.sendSystemMessage(Component.literal("Жажда: " + thirst.getThirst())
                            .withStyle(ChatFormatting.DARK_AQUA));
                    ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), serverPlayer);
                });
            }
        });

        return true;
    }
}
