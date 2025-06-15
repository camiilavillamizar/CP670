package com.example.vill0990_a1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vill0990_a1.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    private String latestMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(getString(R.string.toolbar_title));

        /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        *
         */

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.personalized_notification), Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

        settingUpFirstMessage();
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    *
     */

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param m The options menu in which you place your items.
     *
     * @return
     */
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * We dont need to call it, android do it automatically because the name
     * of the functions
     * @param mi The menu item that was selected.
     *
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        if (id == R.id.action_1) {
            Log.d("TestToolbar", "action_1 selected");
            showSnackBarSelectedOption();
        } else if (id == R.id.action_2) {
            Log.d("TestToolbar", "action_2 selected");
            openDialog();
        } else if (id == R.id.action_3) {
            Log.d("TestToolbar", "action_3 selected");
            openCustomDialog();
        } else if (id == R.id.action_about) {
            Log.d("TestToolbar", "action_about selected");
            showAboutToast();
        }
        if(id != R.id.action_1 && id != R.id.action_2 && id != R.id.action_3 && id != R.id.action_about){
            return false;
        } else{
            return true;
        }
    }

    public void settingUpFirstMessage(){
        latestMessage = getString(R.string.selected_option) + " " + 1;
    }
    public void showAboutToast(){
        String message = getString(R.string.about);
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void showSnackBarSelectedOption(){
        Snackbar.make(binding.getRoot(), latestMessage, Snackbar.LENGTH_LONG).show();
    }

    public void openDialog(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_message_toolbar))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show();

    }

    public void openCustomDialog(){
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        EditText editMessage = dialogView.findViewById(R.id.edit_message);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.custom_dialog_title))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newMessage = editMessage.getText().toString();

                        //saving the message in lastest message var
                        latestMessage = newMessage;
                        Snackbar.make(binding.getRoot(), getString(R.string.message_successful_message_update), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }
}