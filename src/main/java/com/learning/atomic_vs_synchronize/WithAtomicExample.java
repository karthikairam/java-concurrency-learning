package com.learning.atomic_vs_synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class AtomicSharedCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }
}

public class WithAtomicExample {

    public static void main(String[] args) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(2);
        var counter = new AtomicSharedCounter();

        submitIncrementerTask(executorService, counter);
        submitIncrementerTask(executorService, counter);

        // It will only shut down after all the submitted tasks have completed.
        // It is needed for the awaitTermination to work properly.
        executorService.shutdown();
        boolean isSuccess = executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Successful execution: " + isSuccess);
        System.out.println("Final count:"+counter.getCounter());
    }

    private static void submitIncrementerTask(ExecutorService executorService, AtomicSharedCounter counter) {
        executorService.execute(() -> {
            for (int index=0; index < 50000; index++) {
                counter.increment();
            }
        });
    }

}
