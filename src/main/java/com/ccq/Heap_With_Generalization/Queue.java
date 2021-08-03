package com.ccq.Heap_With_Generalization;

/**
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-12:09
 */

public interface Queue<E> {
    /**
     * 获取队列大小
     * @return
     */
    int getSize();

    /**
     * 查看队列是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 将一个元素插入队尾
     * @param e
     */
    void enqueue(E e);

    /**
     * 将队首元素移除队列
     * @return
     */
    E dequeue();

    /**
     * 获取队首元素
     * @return
     */
    E getFront();
}
