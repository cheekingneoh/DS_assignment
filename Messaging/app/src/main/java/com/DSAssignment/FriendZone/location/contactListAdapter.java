package com.DSAssignment.FriendZone.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.DSAssignment.FriendZone.DataStructures.LinkedList;
import com.DSAssignment.FriendZone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class contactListAdapter extends RecyclerView.Adapter {

    private LinkedList ls;
    private Context c;

    private TextView name;
    private TextView lastMessage;
    private ImageView profilePicture;
    private OnCardListener onCardListener;

    public contactListAdapter(LinkedList l, Context c,OnCardListener onCardListener){
        this.ls=l;
        this.c=c;
        this.onCardListener=onCardListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.contact_card,parent,false);
        return new contactCard(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        contactsFound temp=(contactsFound) ls.get(position);
        name.setText(temp.getName());
        lastMessage.setText(temp.getGender());
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference profilePIC=storage.getReference().child(temp.getUid());
        final long ONE_MEGABYTE=1024*1024;
        profilePIC.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                profilePicture.setImageBitmap(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                profilePicture.setImageResource(R.drawable.default_profile_pic);
            }
        });


    }

    public class contactCard extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnCardListener onCardListener;

        public contactCard(@NonNull View itemView,OnCardListener onCardListener) {
            super(itemView);
            name=itemView.findViewById(R.id.contact_name);
            lastMessage=itemView.findViewById(R.id.weightage_Display);
            profilePicture=itemView.findViewById(R.id.profile_picture);
            this.onCardListener=onCardListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }

}

