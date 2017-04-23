package com.hellojd.code.sort;

import java.util.Random;

/**
 * Created by Administrator on 2017/4/23.
 */
public class Insertion extends  AbstractSortTemplate {
    @Override
    public void sort(Comparable[] a) {
        //将数组按升序排列
        int N=a.length;
        //当前索引左边部分都是有序的
        for(int i=1;i<N;i++){
            //将a[i]插入到a[i-1]....a[i-3]....
            for(int j=i;j>0&& less(a[j],a[j-1]);j--){
                this.exch(a,j,j-1);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr=new Integer[1000];
        Random random =new Random();
        for(int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(10000);
        }
        Insertion sorter = new Insertion();
        long s=System.currentTimeMillis();
        sorter.sort(arr);
        long s2=System.currentTimeMillis();
        System.out.println(s2-s);
        assert  sorter.isSorted(arr);
        sorter.show(arr);
    }
}
