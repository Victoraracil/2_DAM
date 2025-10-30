package com.dam;

import java.util.Scanner;

public class FibonacciThread {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of Fibonacci terms: ");
        int n = scanner.nextInt();

        // Create and start the thread
        FibonacciTask thread = new FibonacciTask(n);
        thread.start();

        // Wait for the thread to finish before exiting
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Main thread exiting.");
        scanner.close();
    }
}
