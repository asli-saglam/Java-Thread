package com.mycompany.threadcarpmasi2;
import java.util.Random;

public class ThreadCarpmasi2 {
    static final int BOYUT = 5;
    static int step_i = 0;

    static int[][] A = new int[BOYUT][BOYUT];
    static int[][] B = new int[BOYUT][BOYUT];
    static int[][] C = new int[BOYUT][BOYUT];
    static long[] executionTimes = new long[BOYUT];

    static class Multithread implements Runnable {
        int i;

        Multithread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            long startTime = System.nanoTime();
            try {
                for (int j = 0; j < BOYUT; j++) {
                    for (int k = 0; k < BOYUT; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            executionTimes[i] = endTime - startTime;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 0; i < BOYUT; i++) {
            for (int j = 0; j < BOYUT; j++) {
                A[i][j] = rand.nextInt(100);
                B[i][j] = rand.nextInt(100);
            }
        }

        System.out.println("Matris A:");
        for (int i = 0; i < BOYUT; i++) {
            for (int j = 0; j < BOYUT; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Matris B:");
        for (int i = 0; i < BOYUT; i++) {
            for (int j = 0; j < BOYUT; j++) {
                System.out.print(B[i][j] + " ");
            }
            System.out.println();
        }

        Thread[] t1 = new Thread[BOYUT];

        for (int i = 0; i < BOYUT; i++) {
            t1[i] = new Thread(new Multithread(step_i++));
            t1[i].start();
        }

        for (int i = 0; i < BOYUT; i++) {
            try {
                t1[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("C Matrisi:");
        for (int i = 0; i < BOYUT; i++) {
            for (int j = 0; j < BOYUT; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Thread çalışma süreleri (nanonsaniye cinsinden):");
        for (int i = 0; i < BOYUT; i++) {
            System.out.println("Thread " + i + ": " + executionTimes[i]);
        }
    }
}
