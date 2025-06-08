package com.example.vill0990_a1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;

import static org.hamcrest.CoreMatchers.not;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private static final String LOGIN_EMAIL_PREF = "LoginEmail";
    private static final String EMAIL_KEY = "email";

    @Before
    public void clearSharedPreferences() {
        // Clear SharedPreferences before each test
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_EMAIL_PREF, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    @Test
    public void testInitialState_buttonDisabled() {
        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.login_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void testValidEmailAndPassword_enablesLoginButton() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);

        //Type valid email
        onView(withId(R.id.email_input)).perform(replaceText(""));
        onView(withId(R.id.email_input)).perform(typeText("user@example.com"), closeSoftKeyboard());

        //Type valid password
        onView(withId(R.id.password_input)).perform(typeText("mypassword"), closeSoftKeyboard());

        //Checking that button is enabled
        onView(withId(R.id.login_button)).check(matches(isEnabled()));
    }

    @Test
    public void testInvalidEmail_disablesButtonAndShowsError() {
        ActivityScenario.launch(LoginActivity.class);

        //Type invalid email
        onView(withId(R.id.email_input)).perform(replaceText(""));
        onView(withId(R.id.email_input)).perform(typeText("invalid-email"), closeSoftKeyboard());

        //Type valid password
        onView(withId(R.id.password_input)).perform(typeText("mypassword"), closeSoftKeyboard());

        //Button should remain disabled
        onView(withId(R.id.login_button)).check(matches(not(isEnabled())));

    }

    @Test
    public void testEmptyPassword_disablesButtonAndShowsError() {
        ActivityScenario.launch(LoginActivity.class);

        //Type valid email
        onView(withId(R.id.email_input)).perform(replaceText(""));
        onView(withId(R.id.email_input)).perform(typeText("user@example.com"), closeSoftKeyboard());

        //Type empty password
        onView(withId(R.id.password_input)).perform(clearText(), closeSoftKeyboard());

        //Button should remain disabled
        onView(withId(R.id.login_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void testLoginButton_click_savesEmailAndOpensMainActivity() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.email_input)).perform(replaceText(""));
        onView(withId(R.id.email_input)).perform(replaceText("user@example.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(replaceText("validpassword"), closeSoftKeyboard());

        // Button is now enabled
        onView(withId(R.id.login_button)).check(matches(isEnabled()));

        // Click the login button → triggers onLogin()
        onView(withId(R.id.login_button)).perform(click());


        //Verify the email is stored
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences("LoginEmail", Context.MODE_PRIVATE);
        String storedEmail = preferences.getString("email", "");
        assert(storedEmail.equals("user@example.com"));
    }

}
