package com.DSAssignment.FriendZone.DataStructures;

public class ListNode<T> {
    private T node;
    private ListNode link;

    public ListNode(T n, ListNode l){
        this.node=n;
        this.link=l;
    }

    public void setLink(ListNode l){
        this.link =l;
    }

    public void setContent(T n){
        this.node=n;
    }

    public ListNode getLink(){
        return this.link;
    }

    public T getContent(){
        return this.node;
    }

    public String ToString(){
        return (this.node+" --> ");
    }
}
