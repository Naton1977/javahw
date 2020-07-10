package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HomeWorkThread {
    public static int linesCount;
    public static final Object locker1 = new Object();
    public static FileWriter fw;
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) throws InterruptedException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final File file = new File("alice1.txt");
        final LineNumberReader lnr = new LineNumberReader(new FileReader(file));
        linesCount = 0;
        while (null != lnr.readLine()) {
            linesCount++;
        }
        System.out.println("Koличecтвo cтpok в фaйлe: " + linesCount);

        FileReader fr = new FileReader("alice.txt");
        FileReader fr1 = new FileReader("alice1.txt");
        BufferedReader br = new BufferedReader(fr);
        HomeWorkThread.fw = new FileWriter("copyalice.txt", true);
        List<ReadWriter> rw = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rw.add(new ReadWriter(br));
        }
        for(ReadWriter lst: rw){

        }

        for (int i = 0; i < 10; i++) {
            executorService.execute(rw.get(i));
        }
        executorService.shutdown();

    }
}
