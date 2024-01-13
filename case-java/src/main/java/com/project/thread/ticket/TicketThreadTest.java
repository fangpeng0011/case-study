package com.project.thread.ticket;

public class TicketThreadTest {
    public static void main(String[] args) {

        Thread thread1 = new TicketThread();
        Thread thread2 = new TicketThread();
        Thread thread3 = new TicketThread();
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
