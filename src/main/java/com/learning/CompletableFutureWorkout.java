package com.learning;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class CompletableFutureWorkout {
    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, 100)
                .forEach(value ->
                        CompletableFuture.supplyAsync(() -> getOrder(value))
                                .thenApply(s -> {
                                    System.out.println(s + " " + Thread.currentThread().getName());
                                    return s.toUpperCase(Locale.ROOT);
                                })
                                .thenAccept(s -> System.out.println(s + " " + Thread.currentThread().getName()))
                );

        Thread.sleep(500);

        ThreadLocal.withInitial(() -> new HashMap<String, String>());
    }


    private static String getOrder(Integer value) {
        return "Order-" + value;
    }
}
