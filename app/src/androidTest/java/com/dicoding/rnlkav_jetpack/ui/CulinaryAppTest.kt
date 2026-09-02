package com.dicoding.rnlkav_jetpack.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dicoding.rnlkav_jetpack.data.CulinaryRepository
import com.dicoding.rnlkav_jetpack.ui.theme.CulinaryTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CulinaryAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        CulinaryRepository.resetInstance()
        composeTestRule.setContent {
            CulinaryTheme {
                CulinaryApp()
            }
        }
    }

    @Test
    fun app_startDestination_isHome() {
        composeTestRule.onNodeWithTag("culinary_list").assertIsDisplayed()
    }

    @Test
    fun app_navigation_toDetailAndBack() {
        composeTestRule.onNodeWithTag("culinary_item_1").performClick()
        composeTestRule.onNodeWithTag("detail_content").assertIsDisplayed()
        composeTestRule.onNodeWithTag("back_button").performClick()
        composeTestRule.onNodeWithTag("culinary_list").assertIsDisplayed()
    }

    @Test
    fun app_favorite_toggleWorks() {
        // Go to Detail
        composeTestRule.onNodeWithTag("culinary_item_1").performClick()
        
        // Favorite the item
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        
        // Go back to Home
        composeTestRule.onNodeWithTag("back_button").performClick()
        
        // Navigate to Favorite Page via Bottom Bar
        composeTestRule.onNodeWithTag("nav_item_favorite").performClick()
        
        // Check if item 1 is there in Favorite screen
        composeTestRule.onNodeWithTag("culinary_item_1").assertIsDisplayed()
        
        // Go to Detail from Favorite screen to unfavorite
        composeTestRule.onNodeWithTag("culinary_item_1").performClick()
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        
        // Go back to Favorite screen
        composeTestRule.onNodeWithTag("back_button").performClick()
        
        // Assert Empty State is shown in Favorite screen
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun app_search_positiveCase() {
        composeTestRule.onNode(hasSetTextAction()).performTextReplacement("Rendang")
        
        // Assert Rendang is visible and Sate Maranggi is hidden
        composeTestRule.onNodeWithTag("culinary_item_1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("culinary_item_2").assertDoesNotExist()
    }

    @Test
    fun app_search_negativeCase() {
        // Type something that doesn't exist
        composeTestRule.onNode(hasSetTextAction()).performTextReplacement("UnknownFoodNameXYZ")
        
        // Assert empty state
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun app_navigation_toAboutPage() {
        composeTestRule.onNodeWithTag("about_button").performClick()
        composeTestRule.onNodeWithTag("about_page_content").assertIsDisplayed()
    }
}
