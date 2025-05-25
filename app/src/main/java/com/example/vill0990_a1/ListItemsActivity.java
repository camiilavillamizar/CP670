package com.example.vill0990_a1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListItemsActivity extends AppCompatActivity {

    final String TAG = "ListItemsActivity";

    ImageButton cameraButton;
    Switch switchAction;

    private ActivityResultLauncher<Intent> openCamaraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Inside onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //declaring elements
        cameraButton = findViewById(R.id.camera_button);
        switchAction = findViewById(R.id.switch_action);

        //To access to the camera for the first time we need to ask for permissions
        checkCameraPermission();
        /*An activityResultLauncher is needed because we are changing the view for the camera
        * and then we go back to the list items view with a 'result'*/
        initializingCameraLauncher();

        //setting listeners
        settingButtonListener();
        settingSwitchListener();


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

    private void checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    private void initializingCameraLauncher(){
        openCamaraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Log.i(TAG, "Camera returned to ListItemsActivity");
                    }
                }
        );
    }

    private void settingButtonListener(){
        cameraButton.setOnClickListener(v ->{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //verifying available camera
            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                openCamaraLauncher.launch(takePictureIntent);
            } else {
                Toast.makeText(this, getString(R.string.camera_not_found), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "No camera app found");
            }
        });
    }

    private void settingSwitchListener(){
        switchAction.setOnCheckedChangeListener((buttonView, isChecked) ->{
            Toast toast;

            if(isChecked) toast = Toast.makeText(this, getText(R.string.switch_on), Toast.LENGTH_SHORT);
            else toast = Toast.makeText(this, getText(R.string.switch_off), Toast.LENGTH_LONG);

            toast.show();
        });
    }

    private void print(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }
}