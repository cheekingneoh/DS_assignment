package com.DSAssignment.FriendZone.DataStructures;

import android.util.Log;

public class LinkedList<T> {
    ListNode head;

    public LinkedList(){
        head=null;
    }

    public void add(T t){
        ListNode addition=new ListNode(t,null);
        if(head==null){
            head=addition;
        }
        else{
            ListNode currentNode=head;
            while(currentNode.getLink()!=null){
                currentNode =currentNode.getLink();
            }
            currentNode.setLink(addition);
        }
    }

    public T delete(){
        if(head==null){
            return null;
        }
        else{
            ListNode currentNode=head.getLink();
            ListNode previousNode=head;
            if(currentNode.getLink()!=null){
                previousNode=currentNode;
                currentNode=currentNode.getLink();
            }
            ListNode temp=currentNode;
            previousNode.setLink(null);
            return (T)temp.getContent();
        }
    }

    public int size(){
        int counter=0;
        if(head==null){
            return counter;
        }
        else{
            ListNode currentNode=head;
            while(currentNode!=null){
                currentNode=currentNode.getLink();
                counter++;
            }
            return counter;
        }
    }

    public T get(int a){
        int count=0;
        if(head==null){
            return null;
        }
        else{
            if(a>(size()-1)){
                return null;
            }
            else if(a<size()){
                ListNode currentNode=head;
                while(count!=a){
                    currentNode=currentNode.getLink();
                    count++;
                }
                return (T)currentNode.getContent();
            }
            else{
                return null;
            }
        }
    }

    public void set(int a, T b){
        if(a==0){
            ListNode addition=new ListNode(b,head.getLink());
            head=addition;
        }
        else if(a== size()){
            add(b);
        }
        else if(a> size()){
            Log.d("Error with insert","Invalid index");
        }
        else{
            ListNode addition=new ListNode(b,null);
            ListNode currentNode=head;
            for(int i=0;i<a;i++){
                currentNode=currentNode.getLink();
            }
            ListNode temp=currentNode.getLink();
            currentNode.setLink(addition);
            addition.setLink(temp);
        }
    }
}
