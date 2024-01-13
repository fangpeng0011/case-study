package com.project.thread.ticket;

public class TicketRunnableTest {
    public static void main(String[] args) {

        TicketRunnable ticketRunnable = new TicketRunnable();
        Thread thread1 = new Thread(ticketRunnable, "thread1");
        Thread thread2 = new Thread(ticketRunnable, "thread2");
        Thread thread3 = new Thread(ticketRunnable, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
