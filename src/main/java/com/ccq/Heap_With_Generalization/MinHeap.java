package com.ccq.Heap_With_Generalization;

/** 最小堆
 * 完全二叉树实现、树中的根结点都表示树中的最小元素结点
 * 0开始:
 *      LeftChild = index*2+1
 *      RightChild = index*2+2
 *      Parent = (index-1)/2
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-11:20
 */

public class MinHeap<E extends Comparable<E>> {

    //数组存放在自定义的Array数组中
    private Array<E> data;

    /**
     * 指定初始容量的构造器
     * @param capacity
     */
    public MinHeap(int capacity){
        data = new Array<>(capacity);
    }

    /**
     * 默认初始容量创建底层数组
     */
    public MinHeap(){
        data = new Array<>();
    }

    /**
     * 传入数组建堆
     * @param array
     */
    public MinHeap(E[] array){
        this(array.length);
        //所有元素入堆
        for(int i=0; i<array.length; i++){
            data.addLast(array[i]);
        }
        heapify();
    }

    /**
     * 建堆
     */
    private void heapify(){
        //最后一层的不需要shiftdown 从最后一个元素的父节点开始即可
        for(int i=parent(data.getSize()-1); i>=0; i--){
            shiftDown(i);
        }
    }

    //================================获取底层数组大小===========================

    /**
     * 获取数组大小
     * @return
     */
    public int size(){
        return data.getSize();
    }

    /**
     * 查看数组是否有元素
     * @return
     */
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //================================获取父节点 左右子节点===========================

    /**
     * 获取index位置的父节点在数组中的下标
     * @param index
     * @return
     */
    public int parent(int index){
        if(index==0){
            throw new IllegalArgumentException("根结点没有父节点");
        }
        return (index-1)/2;
    }

    /**
     * 获取index位置左子节点在数组中的下标
     * @param index
     * @return
     */
    public int leftChild(int index){
        return index*2+1;
    }

    /**
     * 获取index位置右子节点在数组中的下标
     * @param index
     * @return
     */
    public int rightChild(int index){
        return index*2+2;
    }



    //================================元素上升下沉===========================

    /**
     * 将index位置的元素上移到适合的位置
     * 这个位置应该使得这个元素大于父节点的值
     * @param index
     */
    public void shiftUp(int index){
        //只要没有到达根位置, 并且index父节点的值>index位置的值
        while(index>0 && data.get(parent(index)).compareTo(data.get(index))>0){
            //交换父节点和index位置的元素
            data.swap(index, parent(index));
            //更新index为父节点的index
            index = parent(index);
        }
    }

    /**
     * 将index位置的元素下沉到适合的位置
     * 这个位置应该使得这个元素小于左右子节点中最小的那个
     * @param index
     */
    public void shiftDown(int index){
        //只要index还有左子节点
        while(index>=0 && leftChild(index)<data.getSize()){
            //左子节点的下标
            int left = leftChild(index);
            //比较左右子节点选出最小的那个
            if(left+1<data.getSize() && data.get(left+1).compareTo(data.get(left))<0){
                left = rightChild(index);
            }
            //如果index位置的元素已经小于左右子节点中的最小的, 无需下沉
            if(data.get(index).compareTo(data.get(left))<0){
                break;
            }
            //下沉, 与左右子节点中最小的那个交换
            data.swap(index, left);
            //更新下标
            index = left;
        }
    }

    //================================向堆中插入元素===========================
    /**
     * 向堆中插入元素
     * @param e
     */
    public void add(E e){
        //每次都加入到数组的最后
        data.addLast(e);
        //然后将这个元素上浮到合适的位置
        shiftUp(data.getSize()-1);
    }

    //================================获取堆中的最小值===========================

    /**
     * 获取堆顶的元素
     * @return
     */
    public E findMin(){
        if(data.isEmpty()){
            throw new IllegalArgumentException("堆空");
        }
        return data.get(0);
    }

    /**
     * 获取堆顶的元素并且将这个元素从堆中删除
     * @return
     */
    public E extractMin(){
        //获取堆顶元素
        E top = findMin();
        //将数组最后一个位置的元素和堆顶元素交换
        data.swap(0, data.getSize()-1);
        //删除交换后最后一个元素
        data.removeLast();
        //将交换到堆顶的元素下沉到合适的位置
        shiftDown(0);
        //之前堆顶的元素
        return top;
    }







































































}
