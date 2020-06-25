package com.DSAssignment.FriendZone.Messaging;

import com.DSAssignment.FriendZone.DataStructures.HashTable;

public class storeWord {
    public static HashTable<String, String> HT = new HashTable<>();

    public storeWord(){
        createHash();
    }

    public static HashTable<String, String> getHT() {
        return HT;
    }

    public static void createHash(){
        HT.put("alive","dead");
        HT.put("beautiful","ugly");
        HT.put("big","small");
        HT.put("bitter","sweet");
        HT.put("cheap","expensive");
        HT.put("clean","dirty");
        HT.put("curly","straight");
        HT.put("difficult","easy");
        HT.put("good","bad");
        HT.put("early","late");
        HT.put("fat","thin");
        HT.put("full","empty");
        HT.put("hot","cold");
        HT.put("happy","sad");
        HT.put("hardworking","lazy");
        HT.put("modern","traditional");
        HT.put("new","old");
        HT.put("nice","nasty");
        HT.put("intelligent","stupid");
        HT.put("interesting","boring");
        HT.put("light","heavy");
        HT.put("polite","rude");
        HT.put("poor","rich");
        HT.put("quiet","noisy");
        HT.put("right","wrong");
        HT.put("safe","dangerous");
        HT.put("short","long");
        HT.put("small","big");
        HT.put("soft","hard");
        HT.put("single","married");
        HT.put("true","false");
        HT.put("well","ill");
        HT.put("white","black");
        HT.put("handsome", "ugly");
        HT.put("dead", "alive");
        HT.put("ugly", "beautiful");
        HT.put("small", "big");
        HT.put("sweet", "bitter");
        HT.put("expensive", "cheap");
        HT.put("dirty", "clean");
        HT.put("straight", "curly");
        HT.put("easy", "difficult");
        HT.put("bad", "good");
        HT.put("late", "early");
        HT.put("thin", "fat");
        HT.put("empty", "full");
        HT.put("cold", "hot");
        HT.put("sad", "happy");
        HT.put("unhappy", "happy");
        HT.put("lazy", "hardworking");
        HT.put("traditional", "modern");
        HT.put("nasty", "nice");
        HT.put("stupid", "intelligent");
        HT.put("boring", "interesting");
        HT.put("impolite", "polite");
        HT.put("rude", "polite");
        HT.put("rich", "poor");
        HT.put("noisy", "quiet");
        HT.put("wrong", "right");
        HT.put("dangerous", "safe");
        HT.put("long", "short");
        HT.put("big", "small");
        HT.put("hard", "soft");
        HT.put("married", "single");
        HT.put("false", "true");
        HT.put("ill", "well");
        HT.put("unwell", "well");
        HT.put("black", "white");
    }
}
