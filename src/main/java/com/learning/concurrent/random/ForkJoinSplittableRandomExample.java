package com.learning.concurrent.random;

import java.util.SplittableRandom;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSplittableRandomExample {

    public static void main(String[] args) {

        /*
            Fork/Join pool has to be used for divide and conquer (recursive) problems.
            Ex: Tree Traversal - Parallel DFS, High Scale Fibonacci series, etc
         */
        ForkJoinRandomTask task = new ForkJoinRandomTask(1_000_000, new SplittableRandom());
        long sum = task.compute();
        System.out.println("Sum of random numbers: " + sum);
    }
}

class ForkJoinRandomTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10_000;
    private final int range;
    private final SplittableRandom random;

    public ForkJoinRandomTask(int range, SplittableRandom random) {
        this.range = range;
        this.random = random;
    }

    @Override
    protected Long compute() {
        if (range <= THRESHOLD) {
            // Direct computation for small tasks
            long sum = 0;
            for (int i = 0; i < range; i++) {
                sum += random.nextInt(10); // Generate numbers between 0 and 9
            }
            return sum;
        } else {
            // Split the task and create independent random streams
            int half = range / 2;
            ForkJoinRandomTask task1 = new ForkJoinRandomTask(half, random.split());
            ForkJoinRandomTask task2 = new ForkJoinRandomTask(half, random.split());

            // Fork tasks
            // This will fork into new task which gets executed in to another thread (from the fork/join pool)
            task1.fork();

            // this will compute in the current thread. Because, to utilize the current thread
            long result2 = task2.compute();

            /* Below join is a blocking call if fork is not completed, and
               the forked task (in a Thread from Fork/Join pool) has to finish and return result.
             */
            long result1 = task1.join();

            // Finally merge the results into one and return up the recursive tree.
            return result1 + result2;
        }
    }
}
