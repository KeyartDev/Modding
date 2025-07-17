package org.keyart.example.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MysticClockItem extends Item {
    private MobEffectInstance lastEffect;
    private static final List<MobEffect> EFFECTS = List.of(
            MobEffects.REGENERATION,
            MobEffects.ABSORPTION,
            MobEffects.SATURATION,
            MobEffects.LUCK
    );

    public MysticClockItem() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (slotIndex != selectedIndex && slotIndex != 40) return;

        if (player.tickCount % 200 != 0) return;

        if (!level.isClientSide) {
            int numberEffect = level.random.nextInt(4);
            MobEffect effect = EFFECTS.get(numberEffect);
            int amplifier = level.random.nextInt(3);
            MobEffectInstance mobEffectInstance = new MobEffectInstance(effect, 100, amplifier, true, true);
            player.addEffect(mobEffectInstance);
            lastEffect = mobEffectInstance;
        } else {
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
            Vec3 pos = player.position();
            for (int x=0;x<15;x++) {
                player.level().addParticle(ParticleTypes.END_ROD,
                        pos.x + (level.random.nextDouble() - 0.5),
                        pos.y + (level.random.nextDouble() + level.random.nextInt(2)),
                        pos.z + (level.random.nextDouble() - 0.5),
                        0, 0, 0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Предмет для тестирования перегрузки функции \"onInventoryTick\". Каждые 10 секунд накладывает случайный положительный эффект:\n" +
                "- Регенерация;\n" +
                "- Поглощение;\n" +
                "- Насыщение;\n" +
                "- Удача.").withStyle(ChatFormatting.GRAY));

        pTooltipComponents.add(Component.literal("Последний наложенный эффект: ").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.UNDERLINE));
        if (lastEffect != null) {
            addBaseEffectInfo(lastEffect, pTooltipComponents);
        } else {
            pTooltipComponents.add(Component.literal("Нет").withStyle(ChatFormatting.GRAY));
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    private void addBaseEffectInfo(MobEffectInstance effect, List<Component> tooltip) {
        String duartion = formatDuration(effect.getDuration()/20);

        ChatFormatting chatFormatting = ChatFormatting.BLUE;
        MobEffectCategory category = effect.getEffect().getCategory();
        if (category == MobEffectCategory.HARMFUL) {
            chatFormatting = ChatFormatting.RED;
        }

        Component effectLine = Component.translatable(effect.getDescriptionId())
                .append(" ")
                .append(Component.literal(toRoman(effect.getAmplifier() + 1)))
                .append(" (" + duartion + ")")
                .withStyle(chatFormatting);

        tooltip.add(effectLine);
    }


    private String toRoman(int number) {
        if (number < 1 || number > 3999)
            return String. valueOf(number); // На случай невалидных значений

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1800, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder roman = new StringBuilder();
        int i = 0;

        while (number > 0) {
            if (number >= arabicValues[i]){
                roman.append (romanSymbols[i]);
                number -= arabicValues[i];
            } else {
                i++;
            }
        }

        return roman.toString();
    }



    private String formatDuration(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
