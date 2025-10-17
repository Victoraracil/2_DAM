package com.dam;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class SetPerformance {
    private static final int ADD_COUNT = 100_000;
    private static final int CHECK_COUNT = 50_000;
    private static final int REMOVE_COUNT = 50_000;

    public static void setperformance() {
        Random rand = new Random();

        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();

        //1. ADD ITEMS
        System.out.println("=== ADDING ELEMENTS ===");
        Instant start = Instant.now();
        for (int i = 0; i < ADD_COUNT; i++) {
            hashSet.add(rand.nextInt());
        }
        Instant end = Instant.now();
        Duration dur = Duration.between(start, end);
        System.out.printf("HashSet: adding %d elements took %dms%n", ADD_COUNT, dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < ADD_COUNT; i++) {
            treeSet.add(rand.nextInt());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("TreeSet: adding %d elements took %dms%n", ADD_COUNT, dur.toMillis());


        //2. CONTAINS CHECK
        System.out.println("\n=== CHECKING CONTAINS ===");
        start = Instant.now();
        for (int i = 0; i < CHECK_COUNT; i++) {
            hashSet.contains(rand.nextInt());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("HashSet: checking %d elements took %dms%n", CHECK_COUNT, dur.toMillis());

        start = Instant.now();
        for (int i = 0; i < CHECK_COUNT; i++) {
            treeSet.contains(rand.nextInt());
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("TreeSet: checking %d elements took %dms%n", CHECK_COUNT, dur.toMillis());


        //3. REMOVE ITEMS
        System.out.println("\n=== REMOVING ELEMENTS ===");
        start = Instant.now();
        Iterator<Integer> itHash = hashSet.iterator();
        int count = 0;
        while (itHash.hasNext() && count < REMOVE_COUNT) {
            itHash.next();
            itHash.remove();
            count++;
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("HashSet: removing %d elements took %dms%n", REMOVE_COUNT, dur.toMillis());

        start = Instant.now();
        Iterator<Integer> itTree = treeSet.iterator();
        count = 0;
        while (itTree.hasNext() && count < REMOVE_COUNT) {
            itTree.next();
            itTree.remove();
            count++;
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("TreeSet: removing %d elements took %dms%n", REMOVE_COUNT, dur.toMillis());


        //4. ITERATE THROUGH ALL ITEMS
        System.out.println("\n=== ITERATING THROUGH ELEMENTS ===");
        start = Instant.now();
        for (Integer value : hashSet) {
            //Iteration
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("HashSet: iterating through %d elements took %dms%n", hashSet.size(), dur.toMillis());

        start = Instant.now();
        for (Integer value : treeSet) {
            //Iteration
        }
        end = Instant.now();
        dur = Duration.between(start, end);
        System.out.printf("TreeSet: iterating through %d elements took %dms%n", treeSet.size(), dur.toMillis());

        System.out.println("\n=== TEST COMPLETE ===");
    }
}
