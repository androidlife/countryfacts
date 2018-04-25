package com.countryfacts.screen.home;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.countryfacts.R;
import com.countryfacts.screen.home.utils.CustomIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test to validate that we are getting success result
 * from the server and our list is populated from the fetched
 * data
 */

@RunWith(AndroidJUnit4.class)
public class DataFetchSuccessTest {
    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);

    private static final int TIMEOUT_ESPRESSO = 60;
    private static final int TIMEOUT_FETCH = 8;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    private CustomIdlingResource idlingResource;

    @Before
    public void init() {
        idlingResource = new CustomIdlingResource();
        IdlingPolicies.setMasterPolicyTimeout(TIMEOUT_ESPRESSO, TIMEOUT_UNIT);
        IdlingPolicies.setIdlingResourceTimeout(TIMEOUT_FETCH, TIMEOUT_UNIT);
    }

    @After
    public void clear() {
        IdlingRegistry.getInstance().unregister(idlingResource);

    }

    @Test
    public void dataFetchSuccess() {
        //this is for first install or when user hasn't given any permission for sdcard access
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject allowPermissions = uiDevice.findObject(new UiSelector().text("ALLOW"));
        if (allowPermissions.exists()) {
            try {
                allowPermissions.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        //since it may take some time to fetch data from server
        //we are waiting for some time to get response and populate on our view
        IdlingRegistry.getInstance().register(idlingResource);
        idlingResource.startCountdown(TIMEOUT_FETCH, TIMEOUT_UNIT);
        onView(withId(R.id.rv_info)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_info)).perform(RecyclerViewActions.scrollToPosition(12));

    }
}
