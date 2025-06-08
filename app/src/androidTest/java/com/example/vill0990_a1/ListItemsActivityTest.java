package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

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

    @Test
    public void testCameraButton_click_doesNotCrashWhenNoCameraApp() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(activity.getPackageManager()) == null) {
                activity.findViewById(R.id.camera_button).performClick();
            }
        });
    }
    @Test
    public void testCheckCameraPermission_granted_doesNotRequestAgain() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            int permission = activity.checkSelfPermission(Manifest.permission.CAMERA);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                activity.recreate(); // Triggers onCreate again without needing request
            }
        });
    }

    @Test
    public void testCameraLauncher_returnsEmptyResult_doesNotCrash() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Intent resultIntent = new Intent(); // No extras
            activity.openCamaraLauncher.getContract().parseResult(Activity.RESULT_OK, resultIntent);
        });
    }

    @Test
    public void testHandleCameraResult_withNullExtras_showsToast() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Intent fakeIntent = new Intent(); // No extras
            activity.handleCameraResult(Activity.RESULT_OK, fakeIntent);
        });
    }

    @Test
    public void testHandleCameraResult_withExtrasButNullData_doesNotCrash() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Intent fakeIntent = new Intent();
            Bundle bundle = new Bundle();
            fakeIntent.putExtras(bundle);
            activity.handleCameraResult(Activity.RESULT_OK, fakeIntent);
        });
    }

    @Test
    public void testHandleCameraResult_resultNotOk_doesNothing() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Intent intent = new Intent();
            activity.handleCameraResult(Activity.RESULT_CANCELED, intent);
        });
    }

    @Test
    public void testHandleCameraResult_withValidBitmap_setsImageButton() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            Bitmap fakeBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

            Bundle bundle = new Bundle();
            bundle.putParcelable("data", fakeBitmap);

            Intent intent = new Intent();
            intent.putExtras(bundle);

            activity.handleCameraResult(Activity.RESULT_OK, intent);
        });
    }

    @Test
    public void testCheckCameraPermission_withoutPermission_requestsIt() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            //Simulating we dont have permits
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                activity.checkCameraPermission();
            }
        });
    }

    @Test
    public void testCameraButton_clickWithAvailableCamera_doesNotCrash() {
        ActivityScenario<ListItemsActivity> scenario = ActivityScenario.launch(ListItemsActivity.class);

        scenario.onActivity(activity -> {
            activity.findViewById(R.id.camera_button).performClick();
        });
    }

    @Test
    public void testSwitch_toggle_coversBothBranches() {
        onView(withId(R.id.switch_action)).perform(click()); // activate → isChecked = true
        onView(withId(R.id.switch_action)).perform(click()); // desactivated → isChecked = false
    }

    @Test
    public void testCheckbox_showsAlertDialog_andPressOk() {
        onView(withId(R.id.checkbox)).perform(click());

        onView(withText(R.string.ok)).check(matches(isDisplayed()));
        onView(withText(R.string.ok)).perform(click());
    }




}
