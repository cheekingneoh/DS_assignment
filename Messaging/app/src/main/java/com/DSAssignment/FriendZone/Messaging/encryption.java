package com.DSAssignment.FriendZone.Messaging;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ArrayList;
import com.DSAssignment.FriendZone.DataStructures.LinkedList;
import java.util.Random;

public class encryption {
    private ArrayList<Character> cL = new ArrayList<>();
    private static Dictionary<Character, Character> encrypWord = new Hashtable<Character, Character>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private String chatId;
    private String key;

    public encryption(final String chatID) {
        chatId=chatID;
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference().child("encryption");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(chatID).exists()){
                    key=(String)dataSnapshot.child(chatId).getValue();
                    loadKey(key);
                    Log.d("test condition",Character.toString(encrypWord.get('a')));
                }
                else{
                    generateKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void loadKey(String k){
        for(int i=0;i<k.length()-1;i=i+2){
            char temp1=k.charAt(i);
            char temp2=k.charAt(i+1);
            encrypWord.put(temp1,temp2);
            encrypWord.put(temp2,temp1);
        }
    }

    private static int inx = -1;

    public void generateKey() {
        generateAscii();
        String key="";
        Random rd=new Random();
        while(cL.size()>0){
            char temp1= cL.remove(rd.nextInt(cL.size()));
            char temp2= cL.remove(rd.nextInt(cL.size()));
            Log.d("temp 1", Character.toString(temp1));
            Log.d("temp 2", Character.toString(temp2));
            key+=Character.toString(temp1)+Character.toString(temp2);
            Log.d("key",key);
            encrypWord.put(temp1,temp2);
            encrypWord.put(temp2,temp1);
        }
        reference.child(chatId).setValue(key);
    }

    public void generateAscii() {
        for (int i = 32; i <= 255; i++) {
            char ascii = (char) (i);
            cL.add(ascii);
        }
    }


    public String encrp(String word) {
        Log.d("before test",word);
        String swiftWord = "";
        for (int i = 0; i < word.length(); i++) {
            Character temp=word.charAt(i);
            swiftWord += encrypWord.get(temp);
        }
        Log.d("after test",swiftWord);
        return swiftWord;
    }

    public int retIndex(int num, String word) {
        for (int j = 0; j < num; j++) {
            if (Character.isLetterOrDigit(word.charAt(j))) {
                continue;
            } else
                return j;
        }
        return -1;
    }

    static String swiftWord = "";
    static String tmp = "";

    public String changeWord(String sentence){
        String[] str = sentence.split(" ");
        storeWord.createHash();
        for(int i = 0 ; i < str.length; i++){
            if(retIndex(str[i].length(),str[i]) == -1){
                if(Character.isUpperCase(str[i].charAt(0))){
                    if(storeWord.HT.containsKey(str[i].toLowerCase())){
                        str[i] = storeWord.HT.get(str[i].toLowerCase());
                        System.out.println("Why: "+str[i]);
                        str[i] = str[i].substring(0,1).toUpperCase() + str[i].toString().substring(1).toLowerCase();
                        str[i]=encrp(str[i]);
                    }
                    else{
                        swiftWord = encrp(str[i]);
                        str[i] = swiftWord;
                    }
                }
                else{
                    if(storeWord.HT.containsKey(str[i].toLowerCase())){
                        str[i] = storeWord.HT.get(str[i]);
                        str[i]=encrp(str[i]);
                    }
                    else{
                        swiftWord = encrp(str[i]);
                        str[i] = swiftWord;
                    }
                }
            }
            else{
                int inx = retIndex(str[i].length(),str[i]);
                tmp = str[i].substring(0, inx);
                if(Character.isUpperCase(tmp.charAt(0))){
                    if(storeWord.HT.containsKey(tmp.toLowerCase())){
                        tmp = storeWord.HT.get(tmp.toLowerCase());
                        tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
                    }
                }
                else{
                    if(storeWord.HT.containsKey(tmp.toLowerCase())){
                        tmp = storeWord.HT.get(tmp);
                    }
                }
                str[i] = tmp + str[i].substring(inx,str[i].length());
                str[i]=encrp(str[i]);
            }
        }
        String temp = "";
        for(int i = 0 ; i < str.length; i++){
            temp += str[i]+encrypWord.get(' ');
        }
        return temp;
    }
}