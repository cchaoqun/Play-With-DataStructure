package com.ccq.Heap_With_Generalization;

/**数组, 基于数组实现的堆
 * 带有泛型
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-10:32
 */

public class Array<E> {
    //泛型数组
    private E[] data;
    //数组中元素的个数
    private int size;
    //默认初始容量
    private static final int  DEFAULT_CAPACITY = 10;

    /**
     * 指定初始数组容量的构造方法
     * @param capacity
     */
    public Array(int capacity){
        //创建对象数组并将其转换为泛型E[]
        data = (E[])new Object[capacity];
        //当前数组中还没有元素, size=0
        size = 0;
    }

    /**
     * 默认构造器, 默认初始容量为10
     */
    public Array(){
        //调用带参构造器, 默认初始容量为10
        this(DEFAULT_CAPACITY);
    }

    //================================获取数组大小===========================
    public int getSize(){
        return size;
    }

    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size==0;
    }




    //================================检查下标越界===========================
    /**检查对应插入下标是否在数组范围内[0,size]
     *
     * @param index
     */
    private void validateInsertIndex(int index){
        if(index<0 || index>size){
            throw new IllegalArgumentException("索引超出数组范围, index需要在[0,size]范围内");
        }
    }

    /**
     * 增删查对应位置index是否在范围内[0,size-1]
     * @param index
     */
    private void validateQueryIndex(int index){
        if(index<0 || index>=size){
            throw new IllegalArgumentException("获取位置不在数组范围内");
        }
    }

    //================================扩容收缩===========================
    /**改变数组大小为指定的容量,
     * 实际上新建了一个数组
     * 将旧的数据复制到新的数组
     * 让旧的数组指针指向新的数组
     *
     * @param newCapacity
     */
    private void resize(int newCapacity){
        //创建指定容量的新的数组
        E[] newData = (E[])new Object[newCapacity];
        //将旧的数组的元素复制到新的数组上
        for(int i=0; i<size; i++){
            newData[i] = data[i];
        }
        //旧的数组指针指向新的数组
        data = newData;
    }

    /**
     * 检查是否需要扩容
     * 当size==data.length的时候需要将容量扩容为原来的两倍
     */
    private void checkResize(){
        if(size==data.length){
            resize(2*data.length);
        }
    }

    /**
     * 将[index,size-1]范围的元素向后移动一个位置
     * @param index
     */
    private void moveBack(int index){
        for(int i=size-1; i>=index; i--){
            data[i+1] = data[i];
        }
    }

    /**
     * 将[index,size-1]范围的元素向前移动一个位置
     * @param index
     */
    private void moveForward(int index){
        for(int i=index+1; i<size; i++){
            data[i-1] = data[i];
        }
    }


    //================================检查元素存在及下标===========================
    /**
     * 查看指定元素e是否存在于数组中
     * @param e
     * @return
     */
    public boolean contains(E e){
        for(int i=0; i<size; i++){
            if(data[i].equals(e)){
                return true;
            }
        }
        return false;
    }

    /**
     * 查看指定元素e在数组中的位置索引 不存在返回-1
     * @param e
     * @return
     */
    public int indexOf(E e){
        for(int i=0; i<size; i++){
            if(data[i].equals(e)){
                return i;
            }
        }
        return -1;
    }


    //================================插入元素===========================

    /**向数组的index位置插入一个元素e
     *
     * @param index
     * @param e
     */
    public void add(int index, E e){
        //检查下标越界
        validateInsertIndex(index);
        //检查是否需要扩容
        checkResize();
        //将[index,size-1]范围内的元素向后移动一个位置
        moveBack(index);
        //将e插入到index位置
        data[index] = e;
        size++;
    }

    /**
     * 向数组头部插入一个元素
     * @param e
     */
    public void addFirst(E e){
        add(0,e);
    }

    /**
     * 向数组尾部插入一个元素
     * @param e
     */
    public void addLast(E e){
        add(size, e);
    }

    //================================获取元素===========================

    /**
     * 获取指定指定位置的元素
     * @param index
     * @return
     */
    public E get(int index){
        validateQueryIndex(index);
        return data[index];
    }

    /**
     * 获取第一个元素
     * @return
     */
    public E getFirst(){
        return get(0);
    }

    /**
     * 获取最后一个元素
     * @return
     */
    public E getLast(){
        return get(size-1);
    }


    //================================修改元素===========================

    /**
     * 修改指定位置index的元素为e
     * @param index
     * @param e
     */
    public void set(int index, E e){
        validateQueryIndex(index);
        data[index] = e;
    }

    //================================删除元素===========================

    /**
     * 删除指定位置index的元素
     * @param index
     * @return
     */
    public E remove(int index){
        //检查index合法
        validateQueryIndex(index);
        //删除位置的元素
        E e = data[index];
        //[index,size-1]前移一个位置
        moveForward(index);
        //容量-1
        size--;
        //修改对象引用, 垃圾回收
        data[size] = null;

        //收缩数组大小 条件为 数组元素数量为数组大小的1/4 并且数组大小>1
        if(size==data.length/4 && data.length/2!=0){
            resize(data.length/2);
        }
        return e;
    }

    /**
     * 删除数组中第一个元素
     * @return
     */
    public E removeFirst(){
        return remove(0);
    }

    /**
     * 删除数组中最后一个元素
     * @return
     */
    public E removeLast(){
        return remove(size-1);
    }

    /**
     * 删除指定元素
     * @param e
     */
    public void removeElement(E e){
        //获取元素在数组中的下标
        int index = indexOf(e);
        //如果存在这个元素 就将其删除
        if(index!=-1){
            remove(index);
        }
    }

    //================================附加功能===========================

    /**
     * 交换数组中两个元素的位置
     * @param i
     * @param j
     */
    public void swap(int i, int j){
        validateQueryIndex(i);
        validateInsertIndex(j);
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 重写toString()
     * @return
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Array: size = %d , capacity = %d\n",size,data.length));
        sb.append("[");
        for(int i=0; i<size; i++){
            sb.append(data[i]);
            sb.append(", ");
        }
        //删除最后的 ", "
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }






















































































}
