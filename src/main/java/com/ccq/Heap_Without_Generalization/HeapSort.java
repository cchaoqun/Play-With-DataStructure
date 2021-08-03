package com.ccq.Heap_Without_Generalization;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/2-16:18
 */

public class HeapSort {
    //堆排序
    public void heapSort(int[] arr){
        MaxHeap heap = new MaxHeap(arr, arr.length+1);
        for(int i=arr.length-1; i>=0; i--){
            arr[i] = heap.delete();
        }
    }

    @Test
    public void testHeapSort(){
        Random random = new Random();
        int[] arr = new int[100];
        for(int i=0; i<arr.length; i++){
            arr[i] = random.nextInt(1000);
        }
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
        for(int i=1; i<arr.length; i++){
            if(arr[i]<arr[i-1]){
                System.out.println(false);
            }
        }
        System.out.println(true);
    }
}
