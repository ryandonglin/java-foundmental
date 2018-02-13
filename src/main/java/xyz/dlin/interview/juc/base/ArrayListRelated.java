package xyz.dlin.interview.juc.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ryan on 2018/2/13.
 */
public class ArrayListRelated {

    ArrayList<Integer> testThreadUnsafe = new ArrayList<Integer>();


    public static void main(String[] args) {
        ArrayListRelated arrayListRelated = new ArrayListRelated();

        //arrayListRelated.whyArrayListCapacityGrowthBy1_5();

        arrayListRelated.whyArrayListIsThreadUnsafe();

    }

    public void whyArrayListCapacityGrowthBy1_5() {

        List<Integer> list = new ArrayList<Integer>();

        System.out.println("Default capacity of new ArrayList command is : " + getCapacity((ArrayList) list));
        System.out.println("Default size of new ArrayList command is : " + list.size());

        ((ArrayList) list).add(0);

        System.out.println("After inserting the first element, the capacity of arrayList is : " + getCapacity((ArrayList) list));
        System.out.println("After inserting the first element, the size of arrayList is : " + list.size());

        for (int i = 1; i <= 10; i++) {
            ((ArrayList) list).add(i);
        }

        System.out.println("After inserting the 11th element, the capacity of arrayList is : " + getCapacity((ArrayList) list));
        System.out.println("After inserting the 11th element, the size of arrayList is : " + list.size());

        for (int i = 11; i <= 15; i++) {
            ((ArrayList) list).add(i);
        }

        System.out.println("After inserting the 16th element, the capacity of arrayList is : " + getCapacity((ArrayList) list));
        System.out.println("After inserting the 16th element, the size of arrayList is : " + list.size());

    }

    public void whyArrayListIsThreadUnsafe() {


        CountDownLatch latch = new CountDownLatch(5);

        ArrayListThreadUnsafeThread thread1 = new ArrayListThreadUnsafeThread(latch);
        ArrayListThreadUnsafeThread thread2 = new ArrayListThreadUnsafeThread(latch);
        ArrayListThreadUnsafeThread thread3 = new ArrayListThreadUnsafeThread(latch);
        ArrayListThreadUnsafeThread thread4 = new ArrayListThreadUnsafeThread(latch);
        ArrayListThreadUnsafeThread thread5 = new ArrayListThreadUnsafeThread(latch);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            latch.await();
            for (Integer integer: testThreadUnsafe) {
                System.out.print(integer + " ");
            }
            System.out.println();
            System.out.println("After instering, the size of ArrayList should be 50, but the actual is " + testThreadUnsafe.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private int getCapacity (ArrayList list) {

        Class clz = ((Object)list).getClass();

        Field f;

        try {
            f = clz.getDeclaredField("elementData");
            f.setAccessible(true);

            Object[] o = (Object[]) f.get(list);

            return o.length;

        } catch (NoSuchFieldException ex) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }


    class ArrayListThreadUnsafeThread extends Thread {

        private CountDownLatch latch;

        public ArrayListThreadUnsafeThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {

            try {
                for (int i = 0; i < 10; i++) {
                    testThreadUnsafe.add(i);
                    //Thread.sleep(50);
                }
                latch.countDown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
