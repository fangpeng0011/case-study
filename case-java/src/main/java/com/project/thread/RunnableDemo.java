package com.project.thread;

/**
 *     实现多线程方式二：实现Runnable接口【应用】
 */
public class RunnableDemo implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("测试MyThread方法:" + i);
        }
    }
}
