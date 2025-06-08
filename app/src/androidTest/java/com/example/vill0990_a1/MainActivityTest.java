package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
}
