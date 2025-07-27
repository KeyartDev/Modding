package org.keyart.example.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.core.utils.ExecuteDelayed;

import java.util.List;

public class MagicWandItem extends Item {
    public MagicWandItem() {
        super(new Properties()
                .stacksTo(1)
                .durability(100)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        Item item = this;

        ExecuteDelayed.DelayedTask delayedTask = new ExecuteDelayed.DelayedTask(100) {
            @Override
            public void run() {
                if (!pLevel.isClientSide) {
                    pPlayer.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            100,
                            1,
                            false,
                            true
                    ));
                    stack.hurtAndBreak(1, pPlayer, (entity) -> {
                        entity.broadcastBreakEvent(pUsedHand); //Показывает анимацию поломки всем игрокам
                    });

                    pPlayer.getCooldowns().addCooldown(item, 200);
                } else {
                    pPlayer.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1.0f, 1.0f);
                }
                super.run();
            }
        };
        ExecuteDelayed.addTask(delayedTask);

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Посох для тестирования перегрузки функции \"use\". Накладывает эффект регенерации на 5 секунд. Откат: 10 секунд.").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
