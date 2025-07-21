package org.keyart.example.common.block.ore;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SomeBlockOre extends DropExperienceBlock {
    public SomeBlockOre() {
        super(Properties.copy(Blocks.IRON_ORE).requiresCorrectToolForDrops(), UniformInt.of(1, 3));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.literal("Руда из которой выпадает 1-2 фрагмента и опыт.").withStyle(ChatFormatting.GRAY));
    }
}
