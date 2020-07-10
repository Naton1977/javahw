package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadWriter implements Runnable {
    private BufferedReader bufferedReader;

    public ReadWriter(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }


    @Override
    public void run() {
        System.out.println(" Вошел : " + Thread.currentThread().getName());
        String str;
        try {
            while (true) {
                HomeWorkThread.readLock.lock();
                str = bufferedReader.readLine();
                if (str == null) {
                    break;
                }
                str = str + "\r\n";
                System.out.println(Thread.currentThread().getName());
                HomeWorkThread.readLock.unlock();
                HomeWorkThread.writeLock.lock();
                try {
                    HomeWorkThread.fw.write(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HomeWorkThread.writeLock.unlock();



                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
        }


        try {
            bufferedReader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            HomeWorkThread.fw.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
