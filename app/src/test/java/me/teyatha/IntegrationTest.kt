package me.teyatha

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [35])
class IntegrationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loadsAlbumListAndNavigatesToTheDetailsPageThenReturnToList() {
        with(composeTestRule) {
            onNodeWithText("iTunes Store").assertIsDisplayed()
            onNodeWithText("Songs You Know By Heart: Jimmy Buffett's Greatest Hit(s)")
                .run {
                    assertIsDisplayed()
                    performClick()
                }
            onNodeWithText("$9.99").assertIsDisplayed()
            onNodeWithText("October 14, 1985").assertIsDisplayed()
            activity.onBackPressedDispatcher.onBackPressed()
            onNodeWithText("iTunes Store").assertIsDisplayed()
        }
    }
}