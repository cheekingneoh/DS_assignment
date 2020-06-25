package com.DSAssignment.FriendZone.Messaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DSAssignment.FriendZone.DataStructures.Queue;
import com.DSAssignment.FriendZone.R;
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
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    RecyclerView MessageRecycler;
    MessageListAdapter MessageAdapter;

    Queue messageList=new Queue();
    
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String otherEndUid=getIntent().getStringExtra("OtherUID");
        name=getIntent().getStringExtra("name");
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
        encryption encrypt=new encryption(chatID);
        final FirebaseFirestore database=FirebaseFirestore.getInstance();

        final DocumentReference docRef=database.collection("chats").document(chatID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        q.setHead(documentSnapshot.toObject(messages.class));
                Log.d("plz be the problem",q.toString());
                MessageRecycler = findViewById(R.id.reyclerview_message_list);
        MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser,name,chatID);
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
                String time=new SimpleDateFormat("HH:mm").format(calendar.getTime());
                encryption encrypt=new encryption(chatID);
                contentInput=encrypt.changeWord(contentInput);
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
                MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser,name,chatID);
                MessageRecycler.setAdapter(MessageAdapter);
                MessageRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Queue tempQ=new Queue();
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists()){
                                tempQ.setHead(documentSnapshot.toObject(messages.class));
                                if(tempQ.size()!=q.size()){
                                    updateMessage(chatID,tempQ,q);
                                    q.setHead(documentSnapshot.toObject(messages.class));
                                    MessageRecycler = findViewById(R.id.reyclerview_message_list);
                                    MessageAdapter = new MessageListAdapter(MainActivity.this,q,currentUser,name,chatID);
                                    MessageRecycler.setAdapter(MessageAdapter);
                                    MessageRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                }
                            }
                        }
                    }
                });
            }
        },0,5000);



    }

    public void updateMessage(String ChatID, Queue after, Queue before){
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        DocumentReference docRef=database.collection("chats").document(ChatID);

    }

}
