package com.DSAssignment.FriendZone.DataStructures;

import com.DSAssignment.FriendZone.Messaging.messages;

public class Queue<T> {
    private messages head;

    public Queue(){
        head=null;
    }

    public void setHead(messages h){
        this.head=h;
    }

    public void enqueue(String m, String ID, String t, messages l){
        messages addition=new messages(m,ID,t,null);;
        if(head==null){
            head=addition;
        }
        else{
            messages currentNode=head;
            while(currentNode.getLink()!=null){
                currentNode=currentNode.getLink();
            }
            currentNode.setLink(addition);
        }
    }

    public messages dequeue(){
        if(head==null){
            return null;
        }
        else if(head.getLink()==null){
            messages temp=head;
            return temp;
        }
        else{
            messages temp=head;
            head=head.getLink();
            return temp;
        }
    }

    public String showQueue(){
        messages currentNode=head;
        String temp="";
        if(head==null){
            return ("This queue is empty");
        }
        else{
            while(currentNode!=null){
//                System.out.print(currentNode.toString());
                temp+=currentNode.toString();
                currentNode=currentNode.getLink();
            }
            return temp;
        }
    }

    public messages peek(){
        return head;
    }

    public int size(){
        int count=0;
        messages current=head;
        while(current!=null){
            count++;
            current=current.getLink();
        }
        return count;
    }

    public messages get(int a){
        int current=0;
        messages currentMessage=head;
        while(current!=(a)){
            currentMessage=currentMessage.getLink();
            current++;
        }
        return currentMessage;
    }
}
