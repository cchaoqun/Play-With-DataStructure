package com.ccq.AVL_Tree;

import com.ccq.util.FileOperation;

import java.util.ArrayList;

/**
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/4-11:01
 */

public class TestAVL {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        FileOperation.readFile("D:\\Self_Learned_Course\\DataStructure_SelfRealize\\src\\main\\java\\com\\ccq\\util\\pride-and-prejudice.txt",words);
        System.out.println("Total words: " + words.size());
        AVLTree<String,Integer> wordsMap = new AVLTree();
        for (String word : words) {
            if(wordsMap.contains(word)){
                wordsMap.set(word,wordsMap.get(word)+1);
            }else {
                wordsMap.add(word,1);
            }
        }
        System.out.println("Total different words: " + wordsMap.getSize());
        System.out.println("Frequency of PRIDE "+wordsMap.get("pride"));
        System.out.println("Frequency of is "+wordsMap.get("is"));
        System.out.println("Frequency of of "+wordsMap.get("of"));
        System.out.println("isBinarySearchTree:"+wordsMap.isBinarySearchTree());
        System.out.println("isBalanced:"+wordsMap.isBalanced());
        for (String word : words) {
            wordsMap.remove(word);
            if(!wordsMap.isBinarySearchTree() || !wordsMap.isBalanced()){
                throw new RuntimeException("Error");
            }
        }
    }
}
