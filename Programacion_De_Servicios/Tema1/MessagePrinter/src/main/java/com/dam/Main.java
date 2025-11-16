package com.dam;

public class Main {
    public static void main(String[] args) {
        //First thread using a class MessagePrinter (implements Runnable)
        Thread thread1 = new Thread(new MessagePrinterRunnable("Hello from the MessagePrinterRunnable class!"));

        //Second thread using a lambda expression
        Thread thread2 = new Thread(() -> System.out.println("Hello from the lambda Runnable!"));

        //Start both threads
        thread1.start();
        thread2.start();
    }
}