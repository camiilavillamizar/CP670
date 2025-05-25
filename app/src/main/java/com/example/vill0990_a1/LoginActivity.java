package com.example.vill0990_a1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;

    EditText emailInput;

    SharedPreferences preferences;
    final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Inside onCreate");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Inizialiting preferences
        preferences = getSharedPreferences("LoginEmail", MODE_PRIVATE);

        //Referencing the login button
        loginButton = findViewById(R.id.login_button);

        //Reading the velue of the stored email address in SharedPreferences and assign it to emailInput
        emailInput = findViewById(R.id.email_input);
        String storedEmail = preferences.getString("email", "email@domain.com"); //DefaultValue
        emailInput.setText(storedEmail);


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

    public void onLogin(android.view.View view){
        String email = emailInput.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();

        Log.i(TAG, "email saved " + email);
    }
}