package com.learning.executers.threadpool;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class CachedThreadPoolExample {

    private static final AtomicInteger maxNumberOfThreadsUsed = new AtomicInteger(0);
    private static final Set<String> threadLookUp = new ConcurrentSkipListSet<>();

    private static final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        //Important thing we test here is that, whether it grows unbounded threads within.
        // Yes it does, and you have to be careful about it.
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        IntStream.rangeClosed(1,1_000_000).forEach(value -> cachedThreadPool.submit(getTask()));

        cachedThreadPool.shutdown();
        boolean success = cachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        if(success) {
            System.out.println("Max Thread used: " + maxNumberOfThreadsUsed + ", All the thread names:" + threadLookUp);
        } else
            System.out.println("Operation did not complete successfully");
    }

    private static Runnable getTask() {
        return () -> {
            var threadName = Thread.currentThread().getName();

            if(!threadLookUp.contains(threadName)) {
                lock.lock();
                if(!threadLookUp.contains(threadName)) { // Double check to avoid false positives (second one inside)
                    threadLookUp.add(threadName);
                    maxNumberOfThreadsUsed.incrementAndGet();
                }
                lock.unlock();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println("Task id: " + value + " is being executed by thread: " + Thread.currentThread().getName());
        };
    }
}
