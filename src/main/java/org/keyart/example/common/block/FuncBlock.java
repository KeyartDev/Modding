package org.keyart.example.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FuncBlock extends Block {
    public FuncBlock() {
        super(Properties.of()
                .sound(SoundType.TUFF)
                .strength(10, 1200)
                .instabreak()
                .speedFactor(0.8f));
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity.tickCount % 40 != 0) return;

        if (!pLevel.isClientSide) {
            if (pEntity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, true));
            }
            if (pEntity instanceof Monster monster) {
                monster.hurt(pLevel.damageSources().magic(), 1);
                monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
                monster.setSecondsOnFire(5);
            }
        } else {
            if (pEntity instanceof Player player) {
                for (int x=0;x<8;x++) {
                    pLevel.addParticle(ParticleTypes.HEART,
                            pPos.getX() + 0.5 + (Math.random()-0.5),
                            pPos.getY() + 1.1 + (double) x / 10,
                            pPos.getZ() + 0.5 + (Math.random()-0.5),
                            0, 0.1, 0);
                }
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("Блок для тестирования прегрузки функции \"stepOn\". На игрока накладывает регенерацию, а монстрам наносит урон и подсвечивает их.").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
