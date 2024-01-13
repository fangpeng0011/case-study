package com.project.thread.ticket;
import java.util.concurrent.locks.ReentrantLock;

public class TicketThread extends Thread {

    private static Integer ticket = 100;

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        sellTicket();
    }

    private void sellTicket() {
        try {
            while (true) {
                lock.lock();
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "票数：" + ticket);
                    ticket--;
                } else {
                    System.out.println("票卖完了");
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
