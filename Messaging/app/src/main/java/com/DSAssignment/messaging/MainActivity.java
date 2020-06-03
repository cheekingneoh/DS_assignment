package com.DSAssignment.messaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    RecyclerView MessageRecycler;
    MessageListAdapter MessageAdapter;
    Queue messageList=new Queue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String otherEndUid=getIntent().getStringExtra("OtherUID");
        Log.d("Testing",otherEndUid);

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        final String currentUser=user.getUid().toString();
        Log.d("Chat ID",currentUser);

        //declaring the chat ID
        final String chatID;
        if(currentUser.compareTo(otherEndUid)>0){
            chatID=otherEndUid+currentUser;
        }
        else {
            chatID = currentUser + otherEndUid;
        }
        final Queue q=new Queue();
        final FirebaseFirestore database=FirebaseFirestore.getInstance();

        DocumentReference docRef=database.collection("chats").document(chatID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                q.setHead(documentSnapshot.toObject(messages.class));
                Log.d("plz be the problem",q.toString());
                MessageRecycler = findViewById(R.id.reyclerview_message_list);
                MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser);
                MessageRecycler.setAdapter(MessageAdapter);
                MessageRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        Button sendBTN=findViewById(R.id.button_chatbox_send);
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input=findViewById(R.id.edittext_chatbox);
                String contentInput=input.getText().toString();
                Calendar calendar=Calendar.getInstance(Locale.getDefault());
                String time=new SimpleDateFormat("HH:mm").format(calendar.getTime());;
                q.enqueue(contentInput,currentUser,time,null);
                database.collection("chats").document(chatID).set(q.peek()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Log.d("writing to database","Successful");
                        else
                            Log.d("writing to database","unsuccessful");
                    }
                });
                input.setText("");
                MessageRecycler = findViewById(R.id.reyclerview_message_list);
                MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser);
                MessageRecycler.setAdapter(MessageAdapter);
                MessageRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });



        //testing the chat interface
//        messageList.enqueue("hi there","Olaf","8:29",null);
//        messageList.enqueue("Plz work","Olaf","8:29",null);
//        messageList.enqueue("yes?","sender","8:29",null);

//        MessageRecycler = findViewById(R.id.reyclerview_message_list);
//        MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser);
//        MessageRecycler.setAdapter(MessageAdapter);
//        MessageRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}