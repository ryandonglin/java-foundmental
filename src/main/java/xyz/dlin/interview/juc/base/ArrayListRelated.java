package xyz.dlin.interview.juc.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2018/2/13.
 */
public class ArrayListRelated {


    public static void main(String[] args) {
        ArrayListRelated arrayListRelated = new ArrayListRelated();

        List<Integer> list = new ArrayList<Integer>();

        System.out.println("Default capacity of new ArrayList command is : " + arrayListRelated.getCapacity((ArrayList) list));
        System.out.println("Default size of new ArrayList command is : " + list.size());

        ((ArrayList) list).add(0);

        System.out.println("After inserting the first element, the capacity of arrayList is : " + arrayListRelated.getCapacity((ArrayList) list));
        System.out.println("After inserting the first element, the size of arrayList is : " + list.size());

        for (int i = 1; i <= 9; i++) {
            ((ArrayList) list).add(i);
        }

        System.out.println("After inserting the 11th element, the capacity of arrayList is : " + arrayListRelated.getCapacity((ArrayList) list));
        System.out.println("After inserting the 11th element, the size of arrayList is : " + list.size());

    }

    public void whyArrayListCapacityGrowthBy1_5() {



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
}
