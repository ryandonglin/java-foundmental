package xyz.dlin.interview.juc.concurrent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ryan on 2018/2/12.
 */
public class ConcurrnetListProblem {

    private static List<Integer> integers = new LinkedList<Integer>();

    public static void addElem(Integer i) {
        integers.add(i);
    }


    static class ConcurrnetList extends Thread {

        private CountDownLatch countDownLatch;

        public ConcurrnetList(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    integers.add(i);
                    Thread.sleep(1000);
                }
                countDownLatch.countDown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(5);
        Thread t1 = new ConcurrnetList(latch);
        Thread t2 = new ConcurrnetList(latch);
        Thread t3 = new ConcurrnetList(latch);
        Thread t4 = new ConcurrnetList(latch);
        Thread t5 = new ConcurrnetList(latch);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            latch.await();

            integers.add(1000);

            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
            System.out.println(integers.size());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


}
