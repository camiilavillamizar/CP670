package com.example.vill0990_a1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vill0990_a1.adapters.ChatAdapter;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    final String TAG = "ChatActivity";
    Button sendButton;
    EditText textInput;
    ListView listView;
    ArrayList<String> messages = new ArrayList<>();

    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Inside onCreate");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeElements();
        settingSendButtonListener();

        //Creating the adapter for listView
        messageAdapter = new ChatAdapter(this, messages);
        listView.setAdapter(messageAdapter);
    }

    protected void onResume(){
        super.onResume();
        Log.i(TAG, "Inside onResume");
    }
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "Inside onStart");
    }
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "Inside onPause");
    }
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "Inside onStop");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "Inside onDestroy");
    }

    private void initializeElements(){
        sendButton = findViewById(R.id.send_button);
        textInput = findViewById(R.id.edit_text);
        listView = findViewById(R.id.list_view);
    }

    private void settingSendButtonListener(){
        sendButton.setOnClickListener(v -> {

            messages.add(textInput.getText().toString());

            //restart the process of getCount() and getView()
            messageAdapter.notifyDataSetChanged();

            textInput.setText("");
        });
    }

}