package com.nubank.allan.billscreen.view;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.nubank.allan.billscreen.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by doisl_000 on 2/13/2016.
 */
@RunWith(JUnit4.class)
@LargeTest
public class ErrorActivityTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<ErrorActivity> mActivityRule = new ActivityTestRule<>(ErrorActivity.class);

    @Test
    public void testErrorActivityDisplaysTextCorrectly(){
        Intent intent = new Intent();
        intent.putExtra("error_code", "404");
        intent.putExtra("error_message", "message");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mActivityRule.launchActivity(intent);
        onView(withId(R.id.errorCode)).check(matches(withText("404")));
        onView(withId(R.id.errorText)).check(matches(withText("message")));
        onView(withId(R.id.retryButton)).check(matches(withText("Tentar novamente")));
    }

    @Test
    public void testRetryButton(){
        Intent intent = new Intent();
        intent.putExtra("error_code", "404");
        intent.putExtra("error_message", "message");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mActivityRule.launchActivity(intent);
        onView(withId(R.id.retryButton)).perform(click());

        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("ABR")));
        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("MAI")));
        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("JUN")));
        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeRight());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("MAI")));
        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeRight());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("ABR")));
        onView(allOf(withId(R.id.viewpager), isDisplayed())).perform(swipeRight());
        onView(allOf(withId(R.id.tabTitle), isSelected())).check(matches(withText("MAR")));
    }

}