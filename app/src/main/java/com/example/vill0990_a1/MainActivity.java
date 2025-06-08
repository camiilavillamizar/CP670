package com.example.vill0990_a1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;

    MaterialButton startChatButton;

    Button button;
    final String TAG = "MainActivity";
    final String RESPONSE = "Response";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Inside onCreate");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button = findViewById(R.id.button);
        startChatButton = findViewById(R.id.startChatButton);

        //As startActivityForResult is deprecated, now we use ActivityResultLauncher
        settingUpActivityResultLauncher();

        //Button listener
        settingButtonListener();

        //New ChatView
        settingButtonStartChatListener();

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

    private void settingUpActivityResultLauncher(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleActivityResult(result.getResultCode(), result.getData())

        );
    }
    private void settingButtonListener(){
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListItemsActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    private void settingButtonStartChatListener(){
        startChatButton.setOnClickListener(v -> {
            Log.i(TAG, "User clicked Start Chat");

            Intent intent = new Intent(this, ChatWindow.class);
            activityResultLauncher.launch(intent);
        });
    }

    public void handleActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.i(TAG, "Returned to MainActivity.onActivityResult");

            if (data != null) {
                String messagePassed = data.getStringExtra(RESPONSE);
                Toast toast = Toast.makeText(this, getString(R.string.listItems_passed) + messagePassed, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}