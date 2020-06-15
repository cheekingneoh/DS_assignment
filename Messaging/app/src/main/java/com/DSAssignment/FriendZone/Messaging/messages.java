package com.DSAssignment.FriendZone.Messaging;

public class messages {
    private String content;
    private String SenderID;
    private String time;
    private messages link;

    public messages(String m, String ID, String t, messages l){
        this.content=m;
        this.SenderID=ID;
        this.time=t;
        this.link=l;
    }
    public messages(){

    }

    public String getContent(){
        return this.content;
    }

    public String getSenderID() {
        return SenderID;
    }

    public String getTime(){
        return time;
    }

    public messages getLink(){
        return link;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLink(messages l){
        this.link=l;
    }

    public String toString(){
        return content+"  "+SenderID+" -->";
    }
}
