package com.appfactorycoding.section.search.ui

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.appfactorycoding.R
import com.appfactorycoding.section.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class SearchFragmentTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun check_the_wholeUi_Testing_for_app() {
        hiltRule.inject()
        activityRule.scenario
        onView(withId(R.id.hint)).check(matches(isDisplayed()))

        onView(withId(R.id.search))
            .perform(click())
            .perform(typeText("art"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        Thread.sleep(4000)
        onView(withId(R.id.recyclerList))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
 /*       onView(withId(R.id.title)).check(matches(withText(containsString("Title : Celestial dancer ()"))))
        onView(withId(R.id.department)).check(matches(withText(containsString("Department : Asian Art"))))*/
    }//we can also write other tests
}


