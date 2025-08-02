package com.talithariddiford.mindtoolsapp.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.OndemandVideo
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.packt.chapterseven.navigation.Screens
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.ui.ActivityToolsPage
import com.talithariddiford.mindtoolsapp.ui.ActivityTypePage
import com.talithariddiford.mindtoolsapp.ui.FilterByMoodPage
import com.talithariddiford.mindtoolsapp.ui.LinkVideoCreationPage
import com.talithariddiford.mindtoolsapp.ui.PdfCreationPage
import com.talithariddiford.mindtoolsapp.ui.PhoneCallActivityCreationPage
import com.talithariddiford.mindtoolsapp.viewmodel.LinkVideoCreationViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel


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
            // Get both view models
            val creationViewModel = koinViewModel<LinkVideoCreationViewModel>()
            val activitiesViewModel = koinViewModel<ActivitiesViewModel>()

            LinkVideoCreationPage(
                navController = navHostController,
                viewModel = creationViewModel,
                onSave = { title, url ->
                    activitiesViewModel.addActivity(
                        Activity(
                            id = UUID.randomUUID().toString(),
                            title = title, // Update if needed
                            icon = Icons.Rounded.OndemandVideo,
                            iconName = "ondemand_video",
                            mindToolResource = url,
                            helpfulnessByMood = Mood.entries.associateWith { 3 }
                        )
                    )
                }
            )
        }

        composable(Screens.AddPhoneCallCreationPage.route) {
            val activitiesViewModel = koinViewModel<ActivitiesViewModel>()

            PhoneCallActivityCreationPage(
                navController = navHostController,
                onSave = { name, number ->
                    Log.d("AppNavigation", "onSave called with name: $name, number: $number")
                    activitiesViewModel.addActivity(
                        Activity(
                            id = UUID.randomUUID().toString(),
                            title = name,
                            icon = Icons.Rounded.Call,
                            iconName = "call",
                            mindToolResource = "tel:$number",
                            helpfulnessByMood = Mood.entries.associateWith { 3 }
                        )
                    )
                }
            )
        }

        composable(Screens.AddPdfCreationPage.route) {
            PdfCreationPage(navController = navHostController)
        }


    }
}
