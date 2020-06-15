package com.DSAssignment.FriendZone.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.DSAssignment.FriendZone.DataStructures.LinkedList;
import com.DSAssignment.FriendZone.R;


public class contactListAdapter extends RecyclerView.Adapter {

    private LinkedList ls;
    private Context c;

    private TextView name;
    private TextView lastMessage;
    private ImageView profilePicture;

    public contactListAdapter(LinkedList l, Context c){
        this.ls=l;
        this.c=c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.contact_card,parent,false);
        return new contactCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        contactCardInfo temp=(contactCardInfo)ls.get(position);
        name.setText(temp.getName());
        lastMessage.setText(temp.getLastMessage());
    }

    public class contactCard extends RecyclerView.ViewHolder{
        public contactCard(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.contact_name);
            lastMessage=itemView.findViewById(R.id.last_message);
            profilePicture=itemView.findViewById(R.id.profile_picture);
        }
    }

    @Override
    public int getItemCount() {
        return ls.getSize();
    }
}
