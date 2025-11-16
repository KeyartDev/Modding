package org.keyart.example.core.utils;

import lombok.Getter;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.keyart.example.Example;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * В параметр нужно передать объект класса {@link ExecuteDelayed.DelayedTask}
 */
@Mod.EventBusSubscriber(modid = Example.MODID)
public class ExecuteDelayed {
    private static ArrayList<DelayedTask> tasks = new ArrayList<>();

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tasks.forEach(DelayedTask::onTick);
            tasks.removeIf(DelayedTask::isDone);
        }
    }


    /**
     * В параметр нужно передать объект класса {@link ExecuteDelayed.DelayedTask}
     */
    public static void addTask(DelayedTask task) {
        tasks.add(task);
    }

    public static void addTask(Supplier<DelayedTask> task) {
        tasks.add(task.get());
    }

    public static void clearTasks() {
        tasks.clear();
    }



    public static class DelayedTask {
        private int delay;
        @Getter private boolean isDone = false;
        private boolean isStarted = false;

        /**
         * Для создания, перегрузить метод {@code run}, написав в нём отложенный код. Необходимо обязательно вызвать в конце {@code super.run()}!
         */
        public DelayedTask(int delay) {
            this.delay = delay;
        }

        public void run() {
            this.isDone = true;
        }

        public void onTick() {
            if (!isStarted) {
                if (this.delay <= 0) {
                    this.run();
                    this.isStarted = true;
                } else
                    this.delay--;
            }
        }
    }
}
