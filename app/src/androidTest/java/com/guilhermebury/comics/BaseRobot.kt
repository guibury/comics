package com.guilhermebury.comics

import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.guilhermebury.comics.espresso.action.NestedScrollToAction
import com.guilhermebury.comics.espresso.matcher.atPosition
import com.guilhermebury.comics.espresso.waitUntilViewIsDisplayed
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

open class BaseRobot<T> {

    fun assertText(@IdRes viewId: Int, text: String): BaseRobot<T> {
        onView(withId(viewId)).check(matches(withText(text)))
        return this
    }

    fun checkIfViewIsDisplayed(@IdRes viewId: Int): BaseRobot<T> {
        onView(allOf(withId(viewId)))
            .check(matches(isDisplayed()))
        return this
    }

    fun checkIfViewIsNotDisplayed(@IdRes viewId: Int): BaseRobot<T> {
        onView(allOf(withId(viewId)))
            .check(matches(not(isDisplayed())))
        return this
    }

    fun waitUntilViewIsDisplayed(@IdRes viewId: Int): BaseRobot<T> {
        waitUntilViewIsDisplayed(withId(viewId))
        return this
    }

    fun <VH: RecyclerView.ViewHolder> scrollToRecyclerViewPosition(
        @IdRes recyclerViewId: Int, @IntRange(from = 0) position: Int
    ) {
        onView(withId(recyclerViewId))
            .perform(RecyclerViewActions.actionOnItemAtPosition<VH>(position, NestedScrollToAction))
    }

    fun checkIfTextIsDisplayedAtRecyclerViewPosition(
        @IdRes viewId: Int,
        position: Int,
        text: String
    ): BaseRobot<T> {
        onView(withId(viewId))
            .check(
                matches(
                    atPosition(
                        position,
                        allOf(hasDescendant(withText(text)), isDisplayed())
                    )
                )
            )
        return this
    }
}