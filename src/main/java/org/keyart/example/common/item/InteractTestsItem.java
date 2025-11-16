package org.keyart.example.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.keyart.example.core.registry.ModSounds;
import org.keyart.example.core.utils.CooldownUtils;

import java.util.List;

public class InteractTestsItem extends Item {
    public InteractTestsItem() {
        super(new Properties()
                .stacksTo(1)
                .durability(5)
                .fireResistant()
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        Vec3 pos = pPlayer.position();
        double RADIUS = 1.5;

        if (CooldownUtils.isOnCooldown(pPlayer, stack.getItem()))
            return InteractionResultHolder.fail(stack);

        if (!pLevel.isClientSide) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0, true,true));
            CooldownUtils.addCooldown(pPlayer, stack.getItem(), 600);
        } else {
            for (double x = 0; x < 360*3; x++) {
                double oneX = RADIUS * Math.cos(Math.toRadians(x));
                double oneZ = RADIUS * Math.sin(Math.toRadians(x));

                double posX = oneX + pPlayer.position().x;
                double posY = Math.floor(x / 360) + 0.1d + pPlayer.position().y;
                double posZ = oneZ + pPlayer.position().z;


                pLevel.addParticle(ParticleTypes.END_ROD,
                        posX, posY, posZ,
                        (RADIUS+pLevel.random.nextDouble()/10)*Math.cos(Math.toRadians(x)) - oneX,
                        -0.05,
                        (RADIUS+pLevel.random.nextDouble()/10)*Math.sin(Math.toRadians(x)) - oneZ
                );
            }
            pPlayer.playSound(ModSounds.PIP_SOUND.get(), 1f, 1f);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.literal("Предмет для тестирования функций взаимодействия с миром/сущностями/игроком.").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("item.mod_id.it_item.desc",  CooldownUtils.getCooldownInSeconds(Minecraft.getInstance().player, this.asItem()))
                .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.UNDERLINE)
        );
    }


}
