package com.ccq.Binary_Search_Tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/4-0:23
 */

public class BinarySearchTree<E extends Comparable<E>> {
    /**
     * 结点类
     */
    private class Node{
        public E e;
        public Node left, right;
        public Node(E _e){
            this.e = _e;
            left = null;
            right = null;
        }
    }
    private Node root;
    private int size;
    public BinarySearchTree(){
        root = null;
        size = 0;
    }

    public BinarySearchTree(Node node){
        root = node;
        size++;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    //===================================插入元素======================================

    /**
     * 向node为根元素的二分搜索树中插入元素
     * 如果存在就更新值
     * @param node
     * @param e
     * @return
     */
    private Node add(Node node, E e){
        if(node==null){
            size++;
            return new Node(e);
        }
        if(e.compareTo(node.e)<0){
            node.left = add(node.left, e);
            return node;
        }else if(e.compareTo(node.e)>0){
            node.right = add(node.right, e);
            return node;
        }else{
            node.e = e;
        }
        return node;
    }

    /**
     * 向二分搜索树中添加元素
     * @param e
     */
    public void add(E e){
        if(root==null){
            root = new Node(e);
        }
        add(root, e);
    }

    //===================================查找元素======================================

    /**
     * 判断以node为根元素的二分搜索树是否包含某个元素
     * @param node
     * @param e
     * @return
     */
    private boolean contains(Node node, E e){
        if(node==null){
            return false;
        }
        if(e.compareTo(node.e)<0){
            return contains(node.left, e);
        }else if(e.compareTo(node.e)>0){
            return contains(node.right, e);
        }else{
            return true;
        }
    }

    /**
     * 判断二分搜索树是否包含某个元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        return contains(root, e);
    }

    private void checkEmpty(){
        if(isEmpty()){
            throw new IllegalArgumentException("BinarySearchTree is empty !");
        }
    }

    /**
     * 查找以node为根节点二分搜索树的最小节点
     * @param node
     * @return
     */
    private Node findMin(Node node){
        checkEmpty();
        if(node==null){
            return null;
        }
        if(node.left==null){
            return node;
        }
        return findMin(node.left);
    }

    /**
     * 查找二分搜索树的最小值
     * @return
     */
    public E findMin(){
        checkEmpty();
        Node min = findMin(root);
        return min==null?null:min.e;
    }

    /**
     * 查找以node为根节点二分搜索树的最大节点
     * @param node
     * @return
     */
    private Node findMax(Node node){
        checkEmpty();
        if(node==null){
            return null;
        }
        if(node.right==null){
            return node;
        }
        return findMin(node.right);
    }

    /**
     * 查找二分搜索树的最大值
     * @return
     */
    public E findMax(){
        checkEmpty();
        Node max = findMax(root);
        return max==null?null:max.e;
    }


    //===================================遍历二叉树======================================
    /**
     * 前序遍历二分搜索树
     */
    public void preOrder(){
        preOrder(root);
    }

    /**
     * 前序遍历以node为根节点的二分搜索树
     * 深度优先遍历，递归实现
     * @param node
     */
    private void preOrder(Node node){
        if(node == null){
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    /**
     * 前序遍历二分搜索树
     * 深度优先遍历，非递归实现，利用栈结构
     */
    public void preOrderByStack(){
        if(root==null){
            return;
        }
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node curNode = stack.pop();
            System.out.println(curNode.e);
            if(curNode.right!=null){
                stack.push(curNode.right);
            }
            if(curNode.left!=null){
                stack.push(curNode.left);
            }
        }
    }

    /**
     * BFS
     */
    public void levelOrder(){
        if(root==null){
            return;
        }
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur);
            if(cur.left!=null){
                queue.offer(cur.left);
            }
            if(cur.right!=null){
                queue.offer(cur.right);
            }
        }
    }

    //===================================删除二叉树的结点======================================

    /**
     * 删除以node为根的二分搜索树的最大节点
     * @param node
     * @return
     */
    private Node removeMax(Node node){
        if(node==null){
            return null;
        }
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
     * 删除二分搜索树的最大值
     * @return
     */
    public E removeMax(){
        E max = findMax();
        removeMax(root);
        return max;
    }

    /**
     * 删除以node为根的二分搜索树的最小节点
     * @param node
     * @return
     */
    private Node removeMin(Node node){
        if(node==null){
            return null;
        }
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
     * 删除二分搜索树的最小值
     * @return
     */
    public E removeMin(){
        E min = findMin();
        removeMin(root);
        return min;
    }

    public void remove(E e){
        root = remove(root,e);
    }

    private Node remove(Node node, E e){
        if(node==null){
            return null;
        }
        if(e.compareTo(node.e)<0){
            node.left = remove(node.left, e);
            return node;
        }else if(e.compareTo(node.e)>0){
            node.right = remove(node.right, e);
            return node;
        }else{
            if(node.right==null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }else if(node.left==null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }else{
                //删除后用前驱节点替代该位置(前驱节点即待删除节点左子树中的最大节点)
                Node successor = findMax(node.left);
                //除前驱节点，并让待删除节点的左子树成为前驱节点的左子树
                successor.left = removeMax(node.left);
                // 让待删除节点的右子树成为前驱节点的右子树
                successor.right = node.right;
                // 将待删除节点的左、右子节点置为空
                node.left = node.right = null;
                return successor;
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        generateBSTString(root,0,result);
        return result.toString();
    }

    /**
     * 生成二分搜索树的字符
     */
    private void generateBSTString(Node node, int depth, StringBuilder result) {
        if (node == null) {
            result.append(generateBSTString(depth)+"null\n");
            return;
        }
        result.append(generateBSTString(depth)+node.e+"\n");
        generateBSTString(node.left,depth+1,result);
        generateBSTString(node.right,depth+1,result);
    }

    /**
     * 生成表示深度的字符
     */
    private String generateBSTString(int depth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            result.append("--");
        }
        return result.toString();
    }


}
