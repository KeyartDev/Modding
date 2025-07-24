package org.keyart.example.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.keyart.example.core.custom.thirst.PlayerThirstProvider;
import org.keyart.example.core.network.ENetworks;
import org.keyart.example.core.network.packets.ThirstDataSyncS2CPacket;

public class ThirstCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("thirst").requires(sender -> sender.hasPermission(0))
                .then(Commands.literal("get").executes(commandContext -> {
                    ServerPlayer player = commandContext.getSource().getPlayerOrException();
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                        player.sendSystemMessage(Component.literal("Ваша жажда: " + thirst.getThirst()).withStyle(ChatFormatting.DARK_AQUA));
                    });

                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("count", IntegerArgumentType.integer(0, 10))
                                        .executes(commandContext -> {
                                            ServerPlayer player = EntityArgument.getPlayer(commandContext, "target");
                                            int count = IntegerArgumentType.getInteger(commandContext, "count");

                                            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(thirst -> {
                                                thirst.setThirst(count);
                                                ENetworks.sendToPlayer(new ThirstDataSyncS2CPacket(thirst.getThirst()), player);
                                            });

                                            player.sendSystemMessage(Component.literal("Ваша жажда установлена на " + count + "!")
                                                    .withStyle(ChatFormatting.DARK_AQUA));



                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                ));
    }
}
