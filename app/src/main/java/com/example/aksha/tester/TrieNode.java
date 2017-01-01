package com.example.aksha.tester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

 class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String value;
    private Random random = new Random();
    ArrayList<String> list;

     TrieNode() {
        children = new HashMap<>();
        value = null;
    }

    // This is just a sample for commit
    //This has nothing to do with anything over here
    //LOL

     void add(String word) {
        TrieNode crawl = this;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char ch = word.charAt(i);
            if (crawl.children.containsKey(ch)) {
                crawl = crawl.children.get(ch);
            } else {
                crawl.children.put(ch, new TrieNode());
                TrieNode temp = crawl.children.get(ch);
                if (i == n - 1) {
                    temp.value = word;
                }
                crawl = temp;
            }
        }
    }

     boolean isWord(String word) {
        TrieNode crawl = this;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            char ch = word.charAt(i);
            if (crawl.children.get(ch) == null) {
                return false;
            } else {
                crawl = crawl.children.get(ch);
                if (i == n - 1 && crawl.value != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
