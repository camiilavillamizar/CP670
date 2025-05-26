package com.example.vill0990_a1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    EditText passwordInput;

    SharedPreferences preferences;
    final String TAG = "LoginActivity";
    final String EMAIL = "email";
    final String LOGIN_EMAIL = "LoginEmail";
    final String DEFAULT_EMAIL = "email@domain.com";
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
        preferences = getSharedPreferences(LOGIN_EMAIL, MODE_PRIVATE);

        //Referencing elements
        loginButton = findViewById(R.id.login_button);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        //Assigning Shared preferences 'LoginEmail' value to email input
        assigningEmailAcordingToSharedPreferences();

        /*-----HANDLING INVALID EMAILS AND NULL PASSWORDS-----*/

        //Disabling the button if they are not valid
        loginButton.setEnabled(false);

        //Activating listeners to check each time user is typing
        activatingEmailListener();
        activatingPasswordListener();


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

    private void assigningEmailAcordingToSharedPreferences(){
        //Reading the velue of the stored email address in SharedPreferences and assign it to emailInput
        String storedEmail = preferences.getString(EMAIL, DEFAULT_EMAIL); //DefaultValue
        emailInput.setText(storedEmail);
    }
    public void onLogin(android.view.View view){

        //Saving email in SharedPreferences
        savingEmailInSharedPreferences();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validEmail(String email){
        return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validPassword(String password){
        return !password.isEmpty();
    }

    private void savingEmailInSharedPreferences(){
        String email = emailInput.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();

        Log.i(TAG, "email saved " + email);
    }


    private void validateInputs(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        boolean isValidEmail = validEmail(email);
        boolean isValidPassword = validPassword(password);

        loginButton.setEnabled(isValidPassword && isValidEmail);
    }

    //Listeners of email and password
    private void activatingEmailListener(){
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (!validEmail(email)) {
                    emailInput.setError(getString(R.string.invalid_email));
                } else {
                    emailInput.setError(null);
                }
                validateInputs();
            }
        });
    }

    private void activatingPasswordListener(){
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString().trim();
                if (!validPassword(password)) {
                    passwordInput.setError(getString(R.string.empty_password));
                } else {
                    passwordInput.setError(null);
                }
                validateInputs();
            }
        });
    }

}
