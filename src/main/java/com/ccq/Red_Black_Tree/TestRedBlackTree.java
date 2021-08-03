package com.ccq.Red_Black_Tree;

import java.util.ArrayList;

/**
 * @author Chaoqun Cheng
 * @date 2021-08-2021/8/3-14:42
 */

public class TestRedBlackTree {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        FileOperation.readFile("D:\\Self_Learned_Course\\DataStructure_SelfRealize\\src\\main\\java\\com\\ccq\\Red_Black_Tree\\pride-and-prejudice.txt",words);
        System.out.println("Total words: " + words.size());
        RedBlackTree<String,Integer> wordsMap = new RedBlackTree();
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
    }
}
