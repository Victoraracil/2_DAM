package com.dam;

public class FibonacciTask extends Thread {
    private int n;

    // Constructor receives the limit N
    public FibonacciTask(int n) {
        this.n = n;
    }

    // Method executed when thread starts
    @Override
    public void run() {
        System.out.println("Fibonacci sequence up to " + n + ":");

        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;

            try {
                // Simulate a delay to visualize the thread work
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
        System.out.println("\nThread finished.");
    }
}
