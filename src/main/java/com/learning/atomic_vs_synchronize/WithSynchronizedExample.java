package com.learning.atomic_vs_synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SynchronizedSharedCounter {
    private int counter;

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }
}

public class WithSynchronizedExample {

    public static void main(String[] args) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(2);
        var counter = new SynchronizedSharedCounter();

        submitIncrementerTask(executorService, counter);
        submitIncrementerTask(executorService, counter);

        // It will only shut down after all the submitted tasks have completed.
        // It is needed for the awaitTermination to work properly.
        executorService.shutdown();
        boolean isSuccess = executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Successful execution: " + isSuccess);

        //Final count will be 100,000 (i.e 2 * 50000) - but since it is using synchronized keyword it will be a bit slow.
        System.out.println("Final count:"+counter.getCounter());
    }

    private static void submitIncrementerTask(ExecutorService executorService, SynchronizedSharedCounter counter) {
        executorService.execute(() -> {
            for (int index=0; index < 50000; index++) {
                counter.increment();
            }
        });
    }

}
