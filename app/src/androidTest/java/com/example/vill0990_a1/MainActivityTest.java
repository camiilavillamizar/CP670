package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        Intents.init(); // to monitor intent launches
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testStartChatButton_isDisplayed() {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.startChatButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testStartChatButton_launchesChatWindow() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.startChatButton)).perform(androidx.test.espresso.action.ViewActions.click());

        //Verifying that an intent to ChatWindow was launched
        Intents.intended(IntentMatchers.hasComponent(ChatWindow.class.getName()));
    }

    @Test
    public void testButton_launchesListItemsActivity() {
        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.button)).perform(androidx.test.espresso.action.ViewActions.click());

        //Verifying that an intent to ListItemsActivity was launched
        Intents.intended(IntentMatchers.hasComponent(ListItemsActivity.class.getName()));
    }
    @Test
    public void testActivityResultLauncher_receivesResultAndShowsToast() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            // Simulate result from launched activity
            Intent resultData = new Intent();
            resultData.putExtra("Response", "Hello!"); // Simulate the response string

            // Simulate RESULT_OK returned
            activity.activityResultLauncher.getContract()
                    .parseResult(Activity.RESULT_OK, resultData);
        });

    }

    @Test
    public void testHandleActivityResult_resultOk_withData() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            Intent data = new Intent();
            data.putExtra("Response", "Hola");

            activity.handleActivityResult(Activity.RESULT_OK, data);
        });
    }

    @Test
    public void testHandleActivityResult_resultOk_noData() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            activity.handleActivityResult(Activity.RESULT_OK, null);
        });
    }

    @Test
    public void testHandleActivityResult_resultCanceled_doesNothing() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            Intent data = new Intent();
            data.putExtra("Response", "Ignored");
            activity.handleActivityResult(Activity.RESULT_CANCELED, data);
        });
    }

}
