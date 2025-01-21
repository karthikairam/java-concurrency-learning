package com.learning.atomic_vs_synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NaiveSharedCounter {
    private int counter;

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}

public class WithoutAtomicExample {

    public static void main(String[] args) {
        var executorService = Executors.newFixedThreadPool(2);
        var counter = new NaiveSharedCounter();

        submitIncrementerTask(executorService, counter);
        submitIncrementerTask(executorService, counter);

        //Final count will not be 100,000 (i.e 2 * 50000) - because of visibility problem and atomic operations.
        System.out.println("Final count:"+counter.getCounter());
    }

    private static void submitIncrementerTask(ExecutorService executorService, NaiveSharedCounter counter) {
        executorService.execute(() -> {
            for (int index=0; index <= 50000; index++) {
                counter.increment();
            }
        });
    }

}
