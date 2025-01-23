package com.learning.concurrent.collections.lists;

import java.util.concurrent.CopyOnWriteArrayList;

public class VerifyCopyOnWriteArrayList {

    public static void main(String[] args) {
        verifyCopyOnWriteArrayListReadOperation();
        // Now I need to verify the iterator example where the iterator is not reflecting the changes.
        // Also, iterator creates a snapshot of the list.
        verifyCopyOnWriteArrayListIterator();
    }

    private static void verifyCopyOnWriteArrayListReadOperation() {
        // The read operations such as get() and contains() reflects the changes on the subsequent calls.
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        System.out.println(list.get(0)); // Output: A
        list.add("C");
        System.out.println(list.get(2)); // Output: C
    }

    private static void verifyCopyOnWriteArrayListIterator() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        var iterator = list.iterator();
        list.add("C");
        iterator.forEachRemaining(System.out::println); // Output: A B
    }
}
