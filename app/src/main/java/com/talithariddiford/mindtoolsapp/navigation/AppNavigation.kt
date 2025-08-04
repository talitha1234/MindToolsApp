package com.talithariddiford.mindtoolsapp.navigation

import ActivitiesViewModel
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



@Composable
fun AppNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    val activitiesViewModel = koinViewModel<ActivitiesViewModel>() // Only one instance
    NavHost(
        navController = navHostController,
        startDestination = Screens.ActivityToolsPage.route
    ) {
        composable(Screens.ActivityToolsPage.route) {
            ActivityToolsPage(navController = navHostController, viewModel = activitiesViewModel)
        }

        composable(Screens.AddActivityPage.route) {
            ActivityTypePage(navController = navHostController)
        }

        composable(Screens.AddFilterByMoodPage.route) {
            FilterByMoodPage(navController = navHostController, viewModel = activitiesViewModel)
        }

        composable(Screens.AddLinkVideoCreationPage.route) {
            val creationViewModel = koinViewModel<LinkVideoCreationViewModel>()
            LinkVideoCreationPage(
                navController = navHostController,
                viewModel = creationViewModel,
                onSave = { title, url ->
                    activitiesViewModel.addActivity(
                        Activity(
                            id = UUID.randomUUID().toString(),
                            title = title,
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

