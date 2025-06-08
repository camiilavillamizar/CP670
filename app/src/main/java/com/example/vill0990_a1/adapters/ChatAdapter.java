package com.example.vill0990_a1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.vill0990_a1.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<String> {

    private final List<String> messages;

    public ChatAdapter(Context ctx, List<String> messages) {
        super(ctx, 0);
        this.messages = messages;
    }

    public int getCount(){
        return messages.size();
    }

    public String getItem(int position){
        return messages.get(position);
    }

    @NonNull
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
