package com.project.thread.ticket;

public class TicketRunnable implements Runnable {

    private static Integer ticket = 100;

    @Override
    public void run() {
        synchronized (this) { //这里的this指的是对象
            while (true) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "票数：" + ticket);
                    ticket--;
                } else {
                    System.out.println("票卖完了");
                    break;
                }
            }
        }
    }
}
