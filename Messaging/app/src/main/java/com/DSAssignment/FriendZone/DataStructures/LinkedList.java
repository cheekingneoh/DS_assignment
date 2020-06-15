package com.DSAssignment.FriendZone.DataStructures;

public class LinkedList<T> {
    ListNode head;

    public LinkedList(){
        head=null;
    }

    public void insert(T t){
        ListNode addition=new ListNode(t,null);
        if(head==null){
            head=addition;
        }
        else{
            ListNode currentNode=head;
            if(currentNode.getLink()!=null){
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

    public int getSize(){
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
            if(a>(getSize()-1)){
                return null;
            }
            else{
                ListNode currentNode=head;
                while(count!=a){
                    currentNode=currentNode.getLink();
                }
                return (T)currentNode.getContent();
            }
        }
    }
}
