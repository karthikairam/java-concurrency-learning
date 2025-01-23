package com.learning.concurrent.collections.queues;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueues {

    public static void main(String[] args) {
        testDelayedWorkerQueue();
    }

    public static void testDelayedWorkerQueue() {
        var reminderSchedulerQueue = new DelayQueue<ReminderTask>();

        // Set the tasks with reminder
        var task1 = new ReminderTask("Good Morning, Have a great day", Duration.ofSeconds(2));
        var task2 = new ReminderTask("Breakfast time, switch on the bread toaster", Duration.ofSeconds(4));
        reminderSchedulerQueue.put(task1);
        reminderSchedulerQueue.put(task2);

        //process the queue as the time expires, the tasks will arrive
        while(!reminderSchedulerQueue.isEmpty()) {
            try {
                var reminderTask = reminderSchedulerQueue.take();
                System.out.printf("Reminder message %s at %s.\n", reminderTask.message, LocalDateTime.now());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("All the reminder tasks are completed");
    }

    public static class ReminderTask implements Delayed {

        private final String message;
        private final long triggerTime; // in milliseconds

        public ReminderTask(String message, Duration triggerTime) {
            this.message = message;
            this.triggerTime = System.currentTimeMillis() + triggerTime.toMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            var remainingTime = triggerTime - System.currentTimeMillis();
            return unit.convert(remainingTime, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {

            if(!(other instanceof ReminderTask otherTask)) {
                throw new IllegalArgumentException("Given object is not ReminderTask type.");
            }

            return Long.compare(this.triggerTime, otherTask.triggerTime);
        }
    }
}
