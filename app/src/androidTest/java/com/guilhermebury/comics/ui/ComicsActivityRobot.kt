package com.guilhermebury.comics.ui

import android.content.Intent
import androidx.test.core.app.launchActivity
import com.guilhermebury.comics.R
import com.guilhermebury.comics.ui.Adapter.ItemViewHolder
import com.guilhermebury.comics.BaseRobot

class ComicsActivityRobot : BaseRobot<ComicsActivityTest>() {

    infix fun checkIf(assertion: ComicsActivityResult.() -> Unit) = ComicsActivityResult().apply(assertion)
}

class ComicsActivityResult : BaseRobot<ComicsActivityTest>() {
    private val recyclerViewId = R.id.comics_list
    private val errorText = R.id.comics_error_text

    fun comicsActivityOnSuccessIsCorrect() {
        waitUntilViewIsDisplayed(recyclerViewId)
        checkIfViewIsNotDisplayed(errorText)
        checkIfViewIsDisplayed(recyclerViewId)
        checkIfTextIsDisplayedAtRecyclerViewPosition(
            viewId = recyclerViewId,
            position = 0,
            text = "Spider-Man 01"
        )
        checkIfTextIsDisplayedAtRecyclerViewPosition(
            viewId = recyclerViewId,
            position = 1,
            text = "Hulk 01"
        )
        scrollToRecyclerViewPosition<ItemViewHolder>(recyclerViewId, 2)
        checkIfTextIsDisplayedAtRecyclerViewPosition(
            viewId = recyclerViewId,
            position = 2,
            text = "Captain America 01"
        )
    }

    fun comicsActivityOnFailureIsCorrect() {
        waitUntilViewIsDisplayed(errorText)
        checkIfViewIsNotDisplayed(recyclerViewId)
        checkIfViewIsDisplayed(errorText)
        assertText(
            viewId = errorText,
            text = "No internet connection"
        )
    }

}

internal fun onLaunch(
    intent: Intent? = null,
    func: (ComicsActivityRobot.() -> Unit) = { }
): ComicsActivityRobot {
    launchActivity<ComicsActivity>(intent = intent)
    return ComicsActivityRobot().apply { func() }
}