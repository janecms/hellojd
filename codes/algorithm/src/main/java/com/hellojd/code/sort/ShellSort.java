package com.hellojd.code.sort;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/23.
 */
public class ShellSort extends  AbstractSortTemplate {
    @Override
    public void sort(Comparable[] a) {
        int n =a.length;
        int h = 1;
        while (h < n/3){
            h = 3*h + 1;
            System.out.println(h);
        }
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            h /= 3;
        }
    }

    public static void main(String[] args) {
        Integer[] arr=new Integer[10];
        Random random =new Random();
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(100);
        }
        ShellSort selectSort = new ShellSort();
        long s=System.currentTimeMillis();
        selectSort.sort(arr);
        long s2=System.currentTimeMillis();
        System.out.println(s2-s);
        assert  selectSort.isSorted(arr);
        selectSort.show(arr);
    }
}
