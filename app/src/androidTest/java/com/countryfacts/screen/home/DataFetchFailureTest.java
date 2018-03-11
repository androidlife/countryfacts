package com.countryfacts.screen.home;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.countryfacts.R;
import com.countryfacts.network.Urls;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 */
@RunWith(AndroidJUnit4.class)
public class DataFetchFailureTest {
    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class, true, false);

    private MockWebServer mockWebServer;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();
        try {
            //we want to test for failure case
            mockWebServer.enqueue(new MockResponse().setResponseCode(404));
            mockWebServer.start();
            Urls.BASE = mockWebServer.url("/").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void shutDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dataFetchFailure() {
        //for mockserver to properly work we won't launch the activity
        // on the fly, but create an intent to launch by ourselves
        Intent intent = new Intent();
        homeActivityActivityTestRule.launchActivity(intent);
        //this is for first install, if there is runtime permissions
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject allowPermissions = uiDevice.findObject(new UiSelector().text("ALLOW"));
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        onView(withId(R.id.tv_error)).check(matches(isDisplayed()));
    }
}
