package com.ccq.Heap_With_Generalization;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-12:15
 */

public class HeapSort {
    public static void main(String[] args) {
        int n = 100000;

        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] =maxHeap.extractMax();
        }

        for (int i = 1; i < n; i++) {
            if(arr[i-1] < arr[i]){
                throw new IllegalArgumentException("Error");
            }
        }

        MinHeap<Integer> minHeap = new MinHeap<>();
        random = new Random();
        for (int i = 0; i < n; i++) {
            minHeap.add(random.nextInt(Integer.MAX_VALUE));
        }

        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] =minHeap.extractMin();
        }

//        System.out.println(Arrays.toString(arr));

        for (int i = 1; i < n; i++) {
            if(arr[i-1] > arr[i]){
                throw new IllegalArgumentException("Error");
            }
        }
    }
}
