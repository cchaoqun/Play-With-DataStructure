package com.ccq.Heap_Without_Generalization;

/**
 * @author Chaoqun Cheng
 * @date 2021-06-2021/6/2-15:31
 */

public class MaxHeap {

    //保存数据的数组
    private int data[];
    //当前堆中的数量
    private int size;
    //堆的最大数量
    private int maxSize;

    public MaxHeap(){

    }

    public MaxHeap(int maxSize){
        //第一个位置不插入数据, 这样size代表了当前拥有的元素个数
        this.size = 0;
        this.maxSize = maxSize;
        this.data = new int[maxSize+1];
    }

    //heapify建堆
    public MaxHeap(int arr[], int maxSize){
        this.size = arr.length;
        this.maxSize = maxSize;
        this.data = new int[maxSize];
        //数组元素放入堆
        for(int i=0; i<arr.length; i++){
            data[i+1] = arr[i];
        }
        //自底向上percolate down
        for(int i=size; i>0; i--){
            percolateDown(i);
        }

    }

    //向堆中插入一个元素
    public void insert(int val){
        if(size==maxSize){
            System.out.println("Full Heap_Without_Generalization");
            return ;
        }
        //插入到数组的最后位置
        data[size+1] = val;
        size++;
        percolateUp(size);
    }


    //删除堆的元素
    public int delete(){
        //将数组的最后一个元素放入开头的位置, 并且percolate down 直到合适的位置
        if(size==0){
            System.out.println("Empty Heap_Without_Generalization");
            return -1;
        }
        int top = data[1];
        //最后一个元素放入堆顶
        data[1] = data[size];
        //大小-1
        size--;
        //新放入堆顶的元素percolate直到合适的位置
        percolateDown(1);
        return top;
    }


    //percolate up

    /**
     * 如果index所在的元素值>父节点的值, 不停地向上与父节点交换
     * 父节点的下标索引为 index/2
     * @param index 插入的元素在data[]中的下标索引
     */
    private void percolateUp(int index){
        //父节点在data[]中的下标为 index/2
        while(index/2 > 0){
            int parent = data[index/2];
            if(parent<data[index]){
                //交换
                int temp = data[index];
                data[index] = data[index/2];
                data[index/2] = temp;
                //交换到了父节点的位置, 需要将index更新为父节点的下标
                index = index / 2;
            }
        }
    }


    //percolate down

    /**
     * 如果index所在的元素值<子节点中较大的那个的值,就向下交换
     * 左子节点的下标索引为 index*2  右子节点为index*2+1
     * @param index 当前需要向下交换的元素的下标
     */
    private void percolateDown(int index){
        //左子节点在data[]中的下标索引
        while(index*2<=this.size){
            int son = index*2;
            //找到左右子节点中较大的那个
            if(son+1<=size && data[son+1]>data[son]){
                son = son+1;
            }
            if(data[son]<data[index]){
                break;
            }
            //父节点与子节点中较大的那个交换
            int temp = data[index];
            data[index] = data[son];
            data[son] = temp;
            index = son;
        }
    }


}

//小顶堆
class MinHeap{
    private int[] data;
    private int size;
    private int maxSize;

    public MinHeap(){

    }
    public MinHeap(int maxSize){
        this.maxSize = maxSize*2;
        this.size = 0;
        this.data = new int[this.maxSize];
    }

    public MinHeap(int[] arr){
        this(arr.length);
        this.size = arr.length;
        //所有元素入堆
        for(int i=0; i<arr.length; i++){
            this.data[i+1] = arr[i];
        }
        //保证堆满足有序的要求
        heapify();
    }

    //从最后一个元素开始percolate down, 保证有序
    public void heapify(){
        for(int i=size; i>=1; i--){
            shiftDown(i);
        }
    }

    //返回堆顶元素
    public int peek() throws Exception {
        if(size==0){
            throw new Exception("Empty Heap_Without_Generalization");
        }
        return this.data[1];
    }

    //添加元素
    public void offer(int val) throws Exception {
        if(this.size==maxSize){
            throw new Exception("Full Heap_Without_Generalization, can not offer");
        }
        size++;
        //添加到最后的位置
        this.data[size] = val;
        //然后向上与父节点交换直到合适的位置
        shiftUp(size);
    }

    //返回堆顶元素
    public int poll() throws Exception {
        if(this.size==0){
            throw new Exception("Empty Heap_Without_Generalization, can not poll");
        }
        //要返回的元素
        int top = this.data[1];
        //最后一个元素放入堆顶
        this.data[1] = this.data[size];
        //减少了一个元素
        size--;
        //堆顶的元素向下与较小的子节点交换直到合适的位置
        shiftDown(1);
        return top;
    }

    //index所在的结点向上交换
    public void shiftUp(int index){
        //需要shiftup的结点
        int cur = this.data[index];
        //当存在父节点
        while(index/2 >=1){
            //当前结点值>父节点则无需交换了
            if(cur>=data[index/2]){
                break;
            }
            //父节点放入当前位置
            this.data[index] = this.data[index/2];
            //索引变成父节点
            index /= 2;
        }
        //当前index位置为cur应该放入的位置
        this.data[index] = cur;
    }

    //index位置向下与较小的子节点交换
    public void shiftDown(int index){
        int cur = this.data[index];
        //保证有子节点
        while(index*2<=size){
            //左子节点索引
            int son = index*2;
            //如果右子节点存在且<左子节点
            if(index*2+1<=size && this.data[son+1]<this.data[son]){
                son += 1;
            }
            //当前元素小于子节点中最小的那个不需要再交换了
            if(cur<=this.data[son]){
                break;
            }
            //较小的子节点放入当前位置,
            this.data[index] = this.data[son];
            //索引变成较小子节点的位置
            index = son;
        }
        this.data[index] = cur;
    }


}
