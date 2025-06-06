package com.example.vill0990_a1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
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

    public class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return messages.size();
        }

        public String getItem(int position){
            return messages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            //Inflater is to convert xml in layout to View objects in exec time
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View result;

            if (position % 2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, parent, false);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, parent, false);
            }

            TextView message = result.findViewById(R.id.messageText);
            message.setText(getItem(position));
            return result;
        }
    }
}