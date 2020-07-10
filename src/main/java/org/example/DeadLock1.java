package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock1 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    private static class Thread1 extends Thread {
        public void run() {
            boolean yourLock = DeadLock1.lock2.tryLock();
            try {
                DeadLock1.lock1.lock();
                System.out.println("Thread 1: Has Lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 1: Waiting for Lock 2");
                try{
                    DeadLock1.lock2.lock();
                    System.out.println("Thread 1: No DeadLock");
                } finally {
                    DeadLock1.lock1.unlock();
                    DeadLock1.lock2.unlock();
                }
            } finally {
                DeadLock1.lock2.unlock();
            }
        }
    }


    private static class Thread2 extends Thread {
        public void run() {
            try {
                DeadLock1.lock2.lock();
                System.out.println("Thread 2: Has Lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 2: Waiting for Lock 1");
                try {
                    DeadLock1.lock1.lock();
                    System.out.println("Thread 2: No DeadLock");
                } finally {
                    DeadLock1.lock1.unlock();
                }
            } finally {
                DeadLock1.lock2.unlock();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }
}
