package com.ccq.AVL_Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** 二分查找树
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/4-0:00
 */

public class AVLTree<K extends Comparable<K>,V> {
    private class Node{
        public K key;
        public V value;
        public int height;
        public Node left, right;
        public Node(K _key, V _value){
            key = _key;
            value = _value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;
    public AVLTree(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size==0;
    }
    public boolean contains(K key) {
        return getNode(root,key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node != null ? node.value : null;
    }

    public void set(K key, V value) {
        Node node = getNode(root, key);
        if(node == null){
            throw new IllegalArgumentException("Set failed. key is not exists!");
        }
        node.value = value;
    }

    public int getSize() {
        return size;
    }

    /**
     * 根据key获取Node
     * @param node
     * @param key
     * @return
     */
    private Node getNode(Node node, K key){
        if(node==null){
            return null;
        }
        if(key.compareTo(node.key) == 0){
            return node;
        }else if(key.compareTo(node.key) < 0){
            return getNode(node.left,key);
        }else{
            return getNode(node.right,key);
        }
    }

    //=====================================AVL二叉树的平衡属性===================================
    /**
     * 获取某个节点的高度
     * @param node
     * @return
     */
    private int getHeight(Node node){
        if(node==null){
            return 0;
        }
        return node.height;
    }

    /**
     * 获取某个节点的平衡因子
     * @param node
     * @return
     */
    private int getBalanceFactor(Node node){
        if(node==null){
            return 0;
        }
        return getHeight(node.left)-getHeight(node.right);
    }

    /**
     * 查看AVL平衡二叉树是否是二分搜索树
     * 通过中序遍历得到二叉树的key的集合, 如果是升序的则是二分搜索树
     * @return
     */
    public boolean isBinarySearchTree(){
        List<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for(int i=1; i<keys.size(); i++){
            if(keys.get(i-1).compareTo(keys.get(i))>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 归查看以node为根节点的AVL平衡二叉树是否是平衡二叉树
     * @param node
     * @return
     */
    private boolean isBalanced(Node node){
        if(node==null){
            return true;
        }
        if(Math.abs(getBalanceFactor(node))>1){
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * 查看AVL平衡二叉树是否是平衡二叉树
     * @return
     */
    public boolean isBalanced(){
        return isBalanced(root);
    }

    //=====================================AVL二叉树的旋转===================================

    /**右旋转操作
     // 对节点y进行向右旋转操作，返回旋转后新的根节点x
     //        y                              x
     //       / \                           /   \
     //      x   T4     向右旋转 (y)        z     y
     //     / \       - - - - - - - ->    / \   / \
     //    z   T3                       T1  T2 T3 T4
     //   / \
     // T1   T2
     * @return
     */
    private Node rightRotate(Node node){
        Node x = node.left;
        Node T3 = x.right;
        //右旋
        x.right = node;
        node.left = T3;
        //更新height
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    /**左旋转操作
     // 对节点y进行向左旋转操作，返回旋转后新的根节点x
     //    y                             x
     //  /  \                          /   \
     // T1   x      向左旋转 (y)       y     z
     //     / \   - - - - - - - ->   / \   / \
     //   T2  z                     T1 T2 T3 T4
     //      / \
     //
     * @param node
     * @return
     */
    private Node leftRotate(Node node){
        Node x = node.right;
        Node T2 = x.left;
        //左旋转操作
        x.left = node;
        node.right = T2;
        //更新height
        updateHeight(node);
        updateHeight(x);
        return x;
    }

    //=====================================遍历AVL二叉树===================================
    /**
     * 中序遍历以node为根节点的AVL平衡二叉树 递归
     * @param node
     * @param keys
     */
    private void inOrder(Node node, List<K> keys){
        if(node==null){
            return;
        }
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    /**
     * 中序遍历以node为根节点的AVL平衡二叉树 迭代
     * @param node
     * @param keys
     */
    private void inOrderByStack(Node node, List<K> keys){
        if(node==null){
            return;
        }
        Deque<Node> stack = new ArrayDeque<>();
        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            keys.add(node.key);
            node = node.right;
        }
    }


    //=====================================AVL二叉树的插入操作===================================

    /**
     * 旋转之后更新node的高度属性
     * @param node
     */
    private void updateHeight(Node node){
        node.height = Math.max(getHeight(node.left), getHeight(node.right))+1;
    }

    /**
     * 向node为根元素的AVL平衡二叉树中插入元素
     * @param node
     * @param key
     * @param value
     * @return
     */
    private Node add(Node node, K key, V value){
        //递归结束
        if(node==null){
            size++;
            return new Node(key,value);
        }
        //递归找到插入节点的位置插入节点, 存在就更新结点值
        if(key.compareTo(node.key)<0){
            node.left = add(node.left, key, value);
        }else if(key.compareTo(node.key)>0){
            node.right = add(node.right, key, value);
        }else{
            node.value = value;
        }
        //维护平衡
        return keepBalance(node);
    }

    /**
     * 向AVL平衡二叉树中插入元素
     * @param key
     * @param value
     */
    public void add(K key, V value){
        root = add(root, key, value);
    }

    /**
     * 维护node结点的平衡
     * @param node
     * @return
     */
    private Node keepBalance(Node node){
        if(node==null){
            return null;
        }
        //更新height
        updateHeight(node);
        /**========== 维护平衡 Start ==========*/
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        /**----------单旋转 LL RR -------------*/
        //LL左孩子节点的左侧产生不平衡 node.left.left.height>node.left.right.height
        if(balanceFactor>1 && getBalanceFactor(node.left)>=0){
            //右旋转
            return rightRotate(node);
        }
        //RR右孩子节点的右侧产生不平衡 node.right.right.height > node.right.left.height
        if(balanceFactor<-1 && getBalanceFactor(node.right)<=0){
            //左旋转
            return leftRotate(node);
        }
        /**----------双旋转 LR RL -------------*/
        //LR左孩子节点的右侧产生不平衡 node.left.right.height > node.left.left.height
        if(balanceFactor>1 && getBalanceFactor(node.left)<0){
            //node.left左旋 变成LL的情况
            node.left = leftRotate(node.left);
            //node.left左旋之后node右旋
            return rightRotate(node);
        }
        //RL右孩子节点的左侧产生不平衡
        if(balanceFactor<-1 && getBalanceFactor(node.right)>0){
            //node.right右旋变成RR
            node.right = rightRotate(node.right);
            //node.right右旋之后 node左旋
            return leftRotate(node);
        }
        /**========== 维护平衡 End ==========*/
        return node;

    }



    //=====================================AVL二叉树的查询操作===================================
    private void checkEmpty(){
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
    }
    /**
     * 查找AVL平衡二叉树的最小值
     * @return
     */
    public V minimum(){
        checkEmpty();
        return minimum(root).value;
    }

    /***
     * 查找以node为根节点AVL平衡二叉树的最小节点
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
     * 查找AVL平衡二叉树的最大值
     * @return
     */
    public V maxmize(){
        checkEmpty();
        return maxmize(root).value;
    }

    /***
     * 查找以node为根节点AVL平衡二叉树的最大节点
     * @param node
     * @return
     */
    private Node maxmize(Node node){
        checkEmpty();
        if(node.right==null){
            return node;
        }
        return maxmize(node.right);
    }

    //=====================================AVL二叉树的删除操作===================================

    /**
     * 删除以node为根的AVL平衡二叉树的最大节点
     * @param node
     * @return
     */
    private Node removeMax(Node node){
        if(node.right==null){
            Node leftNode  = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 删除AVL平衡二叉树的最大值
     * @return
     */
    public V removeMax(){
        V maximum = maxmize();
        removeMax(root);
        return maximum;
    }

    /**
     * 删除以node为根的AVL平衡二叉树的最小节点
     * @param node
     * @return
     */
    private Node removeMin(Node node){
        if(node.left==null){
            Node rightNode  = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 删除AVL平衡二叉树的最小值
     * @return
     */
    public V removeMin(){
        V minimum = minimum();
        removeMin(root);
        return minimum;
    }

    /**
     * 删除以node为根的AVL平衡二叉树中的指定元素
     * @param node
     * @param key
     * @return
     */
    private Node remove(Node node, K key){
        if(node == null){
            return null;
        }
        Node resultNode = null;

        if(key.compareTo(node.key) < 0){
            node.left = remove(node.left, key);
            resultNode = node;
        }else if(key.compareTo(node.key) > 0){
            node.right = remove(node.right, key);
            resultNode = node;
        }else /*if(key.compareTo(node.key) == 0)*/{
            //删除右子树为空的情况
            if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                resultNode = leftNode;
            }
            //删除左子树为空的情况
            else if(node.left==null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                resultNode = rightNode;
            }
            // 删除左子树、右子树均不为空的情况
            else{
                // 1、删除后用后继节点替代该位置(后继节点即待删除节点右子树中的最小节点)
                Node successor = minimum(node.right);
                // 删除后继节点，并让待删除节点的右子树成为后继节点的右子树
                successor.right = removeMin(node.right);
                // 让待删除节点的左子树成为后继节点的左子树
                successor.left = node.left;
                // 将待删除节点的左、右子节点置为空
                node.left = node.right = null;
                resultNode = successor;
                /**
                 // 2、删除后用前驱节点替代该位置(前驱节点即待删除节点左子树中的最大节点)
                 // 获得前驱节点
                 Node predecessor = maximize(node.left);
                 // 删除前驱节点，并让待删除节点的左子树成为前驱节点的左子树
                 predecessor.left = removeMax(node);
                 // 让待删除节点的右子树成为前驱节点的右子树
                 predecessor.right = node.right;
                 // 将待删除节点的左、右子节点置为空
                 node.left = node.right = null;
                 return predecessor;
                 */
            }
        }
        //维护平衡
        return keepBalance(resultNode);
    }

    public V remove(K key){
        Node node = getNode(root, key);
        if(node!=null){
            root = remove(root,key);
            return node.value;
        }
        return null;
    }


}
