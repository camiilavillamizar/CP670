package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.Manifest;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityTest {

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Before
    public void launchActivity() {
        ActivityScenario.launch(ListItemsActivity.class);
    }

    @Test
    public void testUIElements_areVisible() {
        onView(withId(R.id.camera_button)).check(matches(isDisplayed()));
        onView(withId(R.id.switch_action)).check(matches(isDisplayed()));
        onView(withId(R.id.checkbox)).check(matches(isDisplayed()));
    }

    @Test
    public void testSwitch_toggle_doesNotCrash() {
        onView(withId(R.id.switch_action)).perform(click());
        // No Toast check; ensures no crash happens
    }

    @Test
    public void testCheckbox_showsAlertDialog_andCancel() {
        // Open the dialog via checkbox
        onView(withId(R.id.checkbox)).perform(click());

        // Verify dialog UI
        onView(withText(R.string.dialog_message)).check(matches(isDisplayed()));
        onView(withText(R.string.ok)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));

        // Click Cancel so the activity stays open
        onView(withText(R.string.cancel)).perform(click());

        // Confirm the activity is still active
        onView(withId(R.id.checkbox)).check(matches(isDisplayed()));
    }
}
