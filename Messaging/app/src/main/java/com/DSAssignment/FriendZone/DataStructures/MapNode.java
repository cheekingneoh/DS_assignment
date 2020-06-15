package com.DSAssignment.FriendZone.DataStructures;

public class MapNode<K,V> {
    private K key;
    private V value;
    private MapNode link;

    public MapNode(){
        key = null;
        value = null;
        link = null;
    }

    public MapNode(K a, V b , MapNode c){
        key = a;
        value = b;
        link = c;
    }

    public void setKey(K a){key = a;}
    public K getKey(){return key;}
    public void setValue(V a){value = a;}
    public V getValue(){return value;}
    public void setLink(MapNode a){link = a;}
    public MapNode getLink(){return link;}
    public String toString(){return key + ":"+ value + "-->";}
}
