package com.DSAssignment.FriendZone.Messaging;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DSAssignment.FriendZone.DataStructures.Queue;
import com.DSAssignment.FriendZone.R;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Queue q;
    private Context c;
    private String senderID="sender";
    private String User;
    private encryption decrypt;
    private String OtherName;
    private String chatId;


    public MessageListAdapter(Context b,Queue a,String user, String n, String chatID){
        this.q=a;
        this.c=b;
        this.User=user;
        OtherName=n;
        chatId=chatID;
        decrypt=new encryption(chatId);
        Log.d("user",q.toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        if(viewType==0){
            View view=inflater.inflate(R.layout.sent_messages,parent,false);
            return new sentMessage(view);
        }
        else{
            View view=inflater.inflate(R.layout.recieved_messages,parent,false);
            return new receivedMessages(view);
        }
    }

    @Override
    public int getItemViewType(int a){
        messages temp=q.get(a);
        Log.d("Message adapter",User);
        if(temp.getSenderID().equals(User.toString()))
            return 0;
        else
            return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        messages temp=q.get(position);
        if(holder.getItemViewType()==0){
            ((sentMessage)holder).bind(temp);
        }else{
            ((receivedMessages)holder).bind(temp);
        }
    }

    @Override
    public int getItemCount() {
        return q.size();
    }

    public class sentMessage extends RecyclerView.ViewHolder{
        TextView content, time;
        public sentMessage(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.text_message_body);
            time=itemView.findViewById(R.id.text_message_time);
        }

        public void bind(messages temp){
            content.setText(decrypt.encrp(temp.getContent()));
            time.setText(temp.getTime());
        }
    }

    public class receivedMessages extends RecyclerView.ViewHolder{
        TextView name, content, time;
        ImageView profilePic;
        public receivedMessages(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.text_message_name);
            content=itemView.findViewById(R.id.text_message_body);
            time=itemView.findViewById(R.id.text_message_time);
            profilePic=itemView.findViewById(R.id.image_message_profile);
        }

        public void bind(messages temp){
            name.setText(OtherName);
            content.setText(decrypt.encrp(temp.getContent()));
            time.setText(temp.getTime());
            profilePic.setImageResource(R.drawable.default_profile_pic);
        }
    }


}