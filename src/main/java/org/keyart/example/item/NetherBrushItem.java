package org.keyart.example.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherBrushItem extends Item {
    public NetherBrushItem() {
        super(new Properties()
                .rarity(Rarity.RARE)
                .stacksTo(1)
                .durability(25));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();

        if (!level.isClientSide) {
            BlockState state = level.getBlockState(pos);
            InteractionHand hand = pContext.getHand();
            if (player == null)
                return InteractionResult.FAIL;

            if (state.is(Blocks.STONE)) {
                level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }

            if (state.is(Blocks.DEEPSLATE)) {
                level.setBlock(pos, Blocks.BASALT.defaultBlockState(), 3);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            }
        } else {
            player.playSound(SoundEvents.BRUSH_GENERIC, 1f, 1f);
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    pos.getX() + 0.5d,
                    pos.getY() + 1.0d,
                    pos.getZ() + 0.5d,
                    (level.random.nextDouble() - 0.5) * 0.1, 0.1, (level.random.nextDouble() - 0.5) * 0.1);
        }
        return super.useOn(pContext);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents
                .add(Component
                        .literal("Кисть для тестировния перегрузки функции \"useOn\". Превращает обычные блоки в адские:\n" +
                                "- Камень -> Незерак\n" +
                                "- Сланец -> Базальт.").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
