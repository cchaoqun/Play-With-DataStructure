package com.ccq.Red_Black_Tree;


/**红黑树
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-13:02
 */

public class RedBlackTree<K extends Comparable<K>, V>{
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 内部结点类
     */
    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public boolean color;
        public Node(K _key, V _value){
            key = _key;
            value = _value;
            left = null;
            right = null;
            //插入的节点必须是红色的。而且，二叉查找树中新插入的节点都是放在叶子节点上。
            color = RED;
        }
    }

    /**
     * 红黑树的根结点
     */
    private Node root;
    /**
     * 红黑树中结点数量
     */
    private int size;

    public RedBlackTree(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int getSize(){
        return size;
    }

    public boolean contains(K key){
        return getNode(root,key)!=null;
    }

    public V get(K key){
        Node node = getNode(root, key);
        return node==null?null:node.value;
    }
    public void set(K key, V value){
        Node node = getNode(root, key);
        if(node==null){
            throw new IllegalArgumentException("no such key");
        }
        node.value = value;
    }


    //==============================旋转 && 变色===============================

    /**
     *  左旋
     //   node                     x
     //  /   \     左旋转         /  \
     // T1   x   --------->   node   T3
     //     / \              /   \
     //    T2 T3            T1   T2
     * @param node
     * @return
     */
    private Node leftRotate(Node node){
        Node curRight = node.right;
        //左旋
        node.right = curRight.left;
        curRight.left = node;
        curRight.color = node.color;
        node.color = RED;
        return curRight;
    }

    /**
     * 右旋
     //     node                   x
     //    /   \     右旋转       /  \
     //   x    T2   ------->   y   node
     //  / \                       /  \
     // y  T1                     T1  T2
     * @param node
     * @return
     */
    private Node rightRotate(Node node){
        Node curLeft = node.left;
        //右旋
        node.left = curLeft.right;
        curLeft.right = node;
        curLeft.color = node.color;
        node.color = RED;
        return curLeft;
    }

    /**
     * 将node颜色变成红色
     * 左右子节点都变成黑色
     * @param node
     */
    private void flipColor(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 判断当前结点是否是红色
     * @param node
     * @return
     */
    public boolean isRed(Node node){
        if(node==null){
            return BLACK;
        }
        return node.color;
    }

    //==============================向红黑树中插入节点===============================
    public Node add(Node node, K key, V value){
        //递归终止条件
        if(node==null){
            size++;
            return new Node(key,value);
        }
        //左右子树递归
        if(key.compareTo(node.key)<0){
            node.left = add(node.left, key, value);
        }else if(key.compareTo(node.key)>0){
            node.right = add(node.right, key, value);
        }else /*key.compareTo(node.key)==0*/{
            node.value = value;
        }
        //插入节点后, 查看 是否需要旋转来保证红黑树的性质
        /**==========维护红黑树性质 Start==========*/

        //判断是否需要左旋转
        if(isRed(node.right) && !isRed(node.left)){
            node = leftRotate(node);
        }
        //判断是否需要右旋转
        if(isRed(node.left) && isRed(node.left.left)){
            node = rightRotate(node);
        }
        //判断颜色是否需要翻转
        if(isRed(node.left) && isRed(node.right)){
            flipColor(node);
        }
        /**==========维护红黑树性质 Start==========*/
        return node;
    }

    /**
     * 插入一个结点
     * @param key
     * @param value
     */
    public void add(K key, V value){
        root = add(root,key,value);
        root.color = BLACK;
    }

    //==============================从红黑树中查找节点===============================
    private void checkEmpty(){
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
    }

    /**
     * 根据key获取对应的红黑树中的结点
     * @param node
     * @param key
     * @return
     */
    public Node getNode(Node node, K key){
        if(node==null){
            return null;
        }
        if(key.compareTo(node.key)<0){
            return getNode(node.left, key);
        }else if(key.compareTo(node.key)>0){
            return getNode(node.right, key);
        }else{
            return node;
        }
    }

    /**
     * 查找以node为根节点红黑树的最小节点
     * @param node
     * @return
     */
    private Node minimum(Node node){
        checkEmpty();
        if(node.left==null){
            return node;
        }
        return minimum(node.left);
    }

    /**
     * 查找红黑树的最小值
     * @return
     */
    public V minimum(){
        checkEmpty();
        return minimum(root).value;
    }

    /**
     * 查找红黑树的最大值
     * @return
     */
    public V maximum(){
        checkEmpty();
        return maximum(root).value;
    }

    /**
     * 查找以node为根节点红黑树的最大节点
     * @param node
     * @return
     */
    private Node maximum(Node node){
        checkEmpty();
        if(node.right==null){
            return node;
        }
        return maximum(node.right);
    }

    //==============================从红黑树中删除节点===============================

    /**
     * 删除以node为根的红黑树的最大节点
     * @param node
     * @return
     */
    private Node removeMax(Node node){
        if(node.right==null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 删除红黑树的最大值
     * @return
     */
    public V removeMax(){
        // 获取最大值
        V maximum = maximum();
        // 删除最大结点
        removeMax(root);
        return maximum;
    }

    /**
     * 删除以node为根的红黑树的最小节点
     * @param node
     * @return
     */
    private Node removeMin(Node node){
        if(node.left==null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 删除红黑树的最小值
     * @return
     */
    public V removeMin(){
        V minimum = minimum();
        removeMin(root);
        return minimum;
    }

    private Node remove(Node node, K key){
        if(node==null){
            return null;
        }
        if(key.compareTo(node.key)<0){
            node.left = remove(node.left, key);
            return node;
        }else if(key.compareTo(node.key)>0){
            node.right = remove(node.right, key);
            return node;
        }else /*key.compareTo(node.key)==0*/{
            if(node.left==null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }else if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }else{
                //1、删除后用后继节点替代该位置(后继节点即待删除节点右子树中的最小节点)
                //获得后继节点
                Node rightMin = minimum(node.right);
                //// 删除后继节点，并让待删除节点的右子树成为后继节点的右子树
                rightMin.right = removeMin(node.right);
                // 让待删除节点的左子树成为后继节点的左子树
                rightMin.left = node.left;
                // 将待删除节点的左、右子节点置为空
                node.left = node.right = null;
                return rightMin;
                /**
                 // 2、删除后用前驱节点替代该位置(前驱节点即待删除节点左子树中的最大节点)
                 // 获得前驱节点
                 Node predecessor = maximize(node.left);
                 // 删除前驱节点，并让待删除节点的左子树成为前驱节点的左子树
                 predecessor.left = removeMax(node.left);
                 // 让待删除节点的右子树成为前驱节点的右子树
                 predecessor.right = node.right;
                 // 将待删除节点的左、右子节点置为空
                 node.left = node.right = null;
                 return predecessor;
                 */
            }
        }
    }










































































































}
