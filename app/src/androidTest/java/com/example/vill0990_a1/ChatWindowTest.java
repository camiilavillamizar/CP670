package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatWindowTest {

    @Before
    public void launchActivity() {
        ActivityScenario.launch(ChatWindow.class);
    }

    @Test
    public void testUIElements_areDisplayed() {
        onView(withId(R.id.edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.send_button)).check(matches(isDisplayed()));
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testSendMessage_addsItemToListView() {
        //Type a message
        onView(withId(R.id.edit_text)).perform(typeText("Hello world"), closeSoftKeyboard());

        //Click send
        onView(withId(R.id.send_button)).perform(click());

        //Verify that message appears in ListView
        onView(withText("Hello world")).check(matches(isDisplayed()));
    }
}
