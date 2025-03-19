package br.com.ramirosneto.github.repos.app.presentation.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var device: UiDevice

    @Before
    fun setup() {
        Intents.init()
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testExchangeListScreenDisplayed() {
        composeTestRule.onNodeWithText("Java popular repos").assertIsDisplayed()
    }

    @Test
    fun testExchangeItemClickNavigatesToDetails() {
        composeTestRule.waitUntil(10_000) {
            composeTestRule.onAllNodesWithTag("RepositoryItem").fetchSemanticsNodes().isNotEmpty()
        }

        val repositoryItem = composeTestRule.onAllNodesWithTag("RepositoryItem")
        repositoryItem[0].performClick()

        composeTestRule.onNodeWithText("PullRequestBottomSheet").assertIsDisplayed()
    }

    @Test
    fun testClickOnRefreshIcon() {
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
    }
}