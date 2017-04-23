package com.hellojd.code.sort;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/23.
 */
public class Selection  extends AbstractSortTemplate {
    @Override
    public void sort(Comparable[] a) {
        //将数组按升序排列
        int N=a.length;
        //当前索引左边部分都是有序的
        for(int i=0;i<N;i++){
            int min=i;//最小元素的索引
            for(int j=i+1;j<N;j++){
                if(less(a[j],a[min])){
                    min=j;
                }
            }
            exch(a,min,i);
        }
    }

    public static void main(String[] args) {
        Integer[] arr=new Integer[1000];
        Random random =new Random();
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(10000);
        }
        Selection selectSort = new Selection();
        long s=System.currentTimeMillis();
        selectSort.sort(arr);
        long s2=System.currentTimeMillis();
        System.out.println(s2-s);
        assert  selectSort.isSorted(arr);
        selectSort.show(arr);
    }
}
