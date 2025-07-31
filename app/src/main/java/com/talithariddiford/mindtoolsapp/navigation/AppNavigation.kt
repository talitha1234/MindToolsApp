package com.talithariddiford.mindtoolsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.packt.chapterseven.navigation.Screens

import com.talithariddiford.mindtoolsapp.ui.ActivityToolsPage
import com.talithariddiford.mindtoolsapp.ui.ActivityTypePage
import com.talithariddiford.mindtoolsapp.ui.FilterByMoodPage
import com.talithariddiford.mindtoolsapp.ui.LinkVideoCreationPage
import com.talithariddiford.mindtoolsapp.ui.PdfCreationPage
import com.talithariddiford.mindtoolsapp.ui.PhoneCallActivityCreationPage

@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.ActivityToolsPage.route
    ) {
        composable(Screens.ActivityToolsPage.route) {
            ActivityToolsPage(navController = navHostController)
        }

        composable(Screens.AddActivityPage.route) {
            ActivityTypePage(navController = navHostController)
        }
        composable(Screens.AddFilterByMoodPage.route) {
            FilterByMoodPage(navController = navHostController)
        }

        composable(Screens.AddLinkVideoCreationPage.route) {
            LinkVideoCreationPage(navController = navHostController)
        }
        composable(Screens.AddPhoneCallCreationPage.route) {
            PhoneCallActivityCreationPage(navController = navHostController)
        }
        composable(Screens.AddPdfCreationPage.route) {
            PdfCreationPage(navController = navHostController)
        }


    }
}
