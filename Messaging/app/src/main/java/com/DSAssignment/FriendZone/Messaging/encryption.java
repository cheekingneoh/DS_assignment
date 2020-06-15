package com.DSAssignment.FriendZone.Messaging;
import android.util.Log;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Collections;

public class encryption {
    public static ArrayList<Character> cL = new ArrayList<>();
    public static ArrayList<Character> sl = new ArrayList<>();
    public static Dictionary<Character,Character> encrypWord = new Hashtable<Character, Character>();
    public static Dictionary<Character,Character> encrypSymbol = new Hashtable<Character, Character>();
    
    public encryption(){
        generateAscii();
        createEncrypDictionary();
    }

    static int inx = -1;
    public static void createEncrypDictionary(){
        for(int i = 0 ; i < 111; i++) {
            encrypWord.put(cL.get(++inx), cL.get(++inx));
        }
        inx = -1;
        for(int i = 0 ; i < 111 ; i++){
            encrypWord.put(sl.get(++inx),sl.get(++inx));
        }
        inx = 222;
        for(int i = 0 ; i < 111 ; i++) {
            encrypWord.put(cL.get(--inx), cL.get(--inx));
        }
        inx = 222;
        for(int i = 0 ; i < 111 ; i++){
            encrypWord.put(sl.get(--inx),sl.get(--inx));
        }
    }

    public static void generateAscii(){
        for(int i = 32 ; i <= 255 ; i++){
            char ascii = (char)(i);
            cL.add(ascii);
        }
        for(int i = 32 ; i <= 255 ; i++){
            char ascii = (char)(i);
            sl.add(ascii);
        }
    }

    static String swiftWord = "";
    static String tmp = "";

    public String changeWord(String sentence){
        String[] str = sentence.split(" ");
        storeWord.createHash();
        for(int i = 0 ; i < str.length; i++){
            if(retIndex(str[i].length(),str[i]) == -1){
                if(storeWord.HT.containsKey(str[i].toLowerCase())){
                    str[i] = storeWord.HT.get(str[i]);
                    str[i]=encrp(str[i]);
                }
                else{
                    swiftWord = encrp(str[i]);
                    str[i] = swiftWord;
                }
            }
            else{
                int inx = retIndex(str[i].length(),str[i]);
                tmp = str[i].substring(0, inx);
                if(storeWord.HT.containsKey(tmp.toLowerCase())){
                    tmp = storeWord.HT.get(tmp);
                }
                str[i] = tmp + str[i].substring(inx,str[i].length());
                str[i]=encrp(str[i]);
            }
        }
        String temp = "";
        for(int i = 0 ; i < str.length; i++){
            temp += str[i]+"!";
        }
        return temp;
    }
    
    public String encrp(String word){
         String swiftWord = "";
         for(int i = 0 ; i < word.length(); i++){
            swiftWord += encrypWord.get(word.charAt(i));               
     }       
         return swiftWord;
  }
    
    public int retIndex(int num , String word){
        for(int j = 0 ; j < num;j++){
            if(Character.isLetterOrDigit(word.charAt(j))){
                continue;
            }
            else
                return j;
        }
        return -1;
    }
}