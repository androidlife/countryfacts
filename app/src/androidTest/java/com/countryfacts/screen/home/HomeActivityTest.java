package com.countryfacts.screen.home;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 */

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);
}
