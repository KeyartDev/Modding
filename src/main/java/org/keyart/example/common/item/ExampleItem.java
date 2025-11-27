package org.keyart.example.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import org.keyart.example.common.client.particle.ModParticle;

import java.awt.*;
import java.util.List;

import static org.keyart.example.core.utils.ExecuteDelayed.withDelay;

public class ExampleItem extends Item {

    public ExampleItem() {
        super(new Properties()
                .stacksTo(64)
                .rarity(Rarity.RARE)
                .fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.keybind("item.mod_id.example_item.desc").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, TooltipFlag.NORMAL);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            int delay = 20;

            for (int i = 0; i < 4; i++) {
                withDelay(() -> makeParticle(pPlayer, pLevel, pPlayer.getOnPos().above().north()), 0);
                withDelay(() -> makeParticle(pPlayer, pLevel, pPlayer.getOnPos().above().east()), delay);
                withDelay(() -> makeParticle(pPlayer, pLevel, pPlayer.getOnPos().above().south()), delay*2);
                withDelay(() -> makeParticle(pPlayer, pLevel, pPlayer.getOnPos().above().west()), delay*3);
            }
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    public static void makeParticle(Player player, Level level, BlockPos pos) {
        ParticleOptions particle = new ModParticle.Options((new Color(255, 0, 0)).getRGB(), 0.3F, 30, 0, 0.9F);

        level.addParticle(particle, pos.getX()+0.5f, pos.getY()+1.0f, pos.getZ()+0.5f,
                0, 0, 0);

        level.playSound(player, pos, SoundEvents.NOTE_BLOCK_CHIME.get(), SoundSource.AMBIENT);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() == null)
            return super.useOn(pContext);

        BlockPos pos = pContext.getClickedPos();
        BlockState state = pContext.getLevel().getBlockState(pos);

        if (state.is(Blocks.DIRT)) {
            pContext.getLevel().setBlock(pos, Blocks.SANDSTONE.defaultBlockState(), 0);
            pContext.getPlayer().playSound(SoundEvents.ENDERMAN_TELEPORT);
        }

        return super.useOn(pContext);
    }
}
