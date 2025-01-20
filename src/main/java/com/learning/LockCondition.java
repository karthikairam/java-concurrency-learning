package com.learning;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockCondition {
    public static void main(String[] args) throws InterruptedException {
        var lock = new ReentrantLock();
        var conditionMet = lock.newCondition();

        conditionMet.wait();
    }

    //write example use case for ReentrantLock example below
    private static void reentrantLockExample() throws IOException {
        ReentrantLock reentrantLock = new ReentrantLock();

        reentrantLock.lock();
        try {
            // give a valid example for reentrant lock
            File file = new File("file.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    //ReentrantReadWriteLock example below
    private static void reentrantReadWriteLockExample() throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        reentrantReadWriteLock.readLock().lock();
        try {
            File file = new File("file.txt");
            if (!file.exists()) {
                reentrantReadWriteLock.readLock().unlock();
                reentrantReadWriteLock.writeLock().lock();
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                } finally {
                    reentrantReadWriteLock.writeLock().unlock();
                }
                reentrantReadWriteLock.readLock().lock();
            }
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}

