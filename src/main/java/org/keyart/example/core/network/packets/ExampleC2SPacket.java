package org.keyart.example.core.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExampleC2SPacket {
    public ExampleC2SPacket() {

    }

    public ExampleC2SPacket(FriendlyByteBuf buf) {

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
                serverPlayer.sendSystemMessage(Component.literal("Вы на нужном блоке!"));
                serverPlayer.addItem(new ItemStack(Items.DIAMOND));
            } else {
                serverPlayer.sendSystemMessage(Component.literal("Вы НЕ на нужном блоке!"));
            }
        });

        return true;
    }
}
