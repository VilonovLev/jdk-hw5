package org.example;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        final int COUNT_PHILOSOPHER = random.nextInt(4,5);
        final int COUNT_MEALS = random.nextInt(2,3);
        final CountDownLatch READY_FOR_MEAL = new CountDownLatch(COUNT_PHILOSOPHER);
        final Lock MEAL_LOCK = new ReentrantLock();

        for (int i = 0; i < COUNT_PHILOSOPHER; i++) {
            int y = i;
            Thread thread = new Thread(() -> {
                READY_FOR_MEAL.countDown();
                int count = COUNT_MEALS;
                System.out.println(String.format("I am philosopher: %d", y));
                while (count > 0) {
                    try {
                        if (MEAL_LOCK.tryLock()) {
                            System.out.println(String.format("philosopher %d START eat", y));
                            sleep(500);
                            System.out.println(String.format("philosopher %d END eat", y));
                            MEAL_LOCK.unlock();
                            count--;
                        }
                        System.out.println(String.format("philosopher %d ponder", y));
                        sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        }

    }
}