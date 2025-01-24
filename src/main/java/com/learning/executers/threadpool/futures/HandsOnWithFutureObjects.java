package com.learning.executers.threadpool.futures;

import java.util.concurrent.*;

public class HandsOnWithFutureObjects {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        var threadPool = Executors.newSingleThreadExecutor();

        futureHappyScenario(threadPool);
        futureHappyScenarioWithTimeout(threadPool);
        futureHappyScenarioWithTimeoutFailure(threadPool);
    }

    private static void futureHappyScenarioWithTimeoutFailure(ExecutorService threadPool) throws ExecutionException, InterruptedException, TimeoutException {
        Future<Integer> random = threadPool.submit(() -> {
            Thread.sleep(1000);
            return ThreadLocalRandom.current().nextInt(0,10);
        });
        System.out.println(random.get(500, TimeUnit.MILLISECONDS));
    }

    private static void futureHappyScenarioWithTimeout(ExecutorService threadPool) throws ExecutionException, InterruptedException, TimeoutException {
        Future<Integer> random = threadPool.submit(() -> {
            Thread.sleep(1000);
            return ThreadLocalRandom.current().nextInt(0,10);
        });
        System.out.println(random.get(1200, TimeUnit.MILLISECONDS));
    }

    private static void futureHappyScenario(ExecutorService threadPool) throws InterruptedException, ExecutionException {
        Future<Integer> random = threadPool.submit(() -> ThreadLocalRandom.current().nextInt(0,10));
        System.out.println(random.get());
    }
}
