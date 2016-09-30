package com.example.aksha.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class FastDictionary implements GhostDictionary {

    private TrieNode root;

    public FastDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        root = new TrieNode();
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            root.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return root.isWord(word);
    }

}
