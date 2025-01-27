package com.learning;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Hello world!");

        List<String> servers = List.of("Server1", "Server2", "Server3");
        RoundRobinStrategy strategy = new RoundRobinStrategy();
        System.out.printf("Counter value before: %s\n", strategy.getCounter().get());

        // Simulate integer overflow
        Field counterField = RoundRobinStrategy.class.getDeclaredField("counter");
        counterField.setAccessible(true);
        AtomicInteger counter = (AtomicInteger) counterField.get(strategy);
        counter.set(Integer.MAX_VALUE - 1);

        System.out.printf("Counter value after: %s\n", strategy.getCounter().get());
        System.out.printf("Flood Mod: %d\n", Math.floorMod(-399999992, 10));
    }

    private static class RoundRobinStrategy {
        private final AtomicInteger counter = new AtomicInteger(0);

        public AtomicInteger getCounter() {
            return counter;
        }
    }
}