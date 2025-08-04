package com.talithariddiford.mindtoolsapp.ui


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.packt.chapterseven.navigation.Screens
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood

import androidx.compose.runtime.rememberCoroutineScope
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import com.talithariddiford.mindtoolsapp.viewmodel.FeedbackResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivityToolsPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    previewActivities: List<Activity>? = null,
    viewModel: ActivitiesViewModel,
) {
    val onAddClicked = { navController.navigate(Screens.AddActivityPage.route) }
    val onFilterClicked = { navController.navigate(Screens.AddFilterByMoodPage.route) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
        topBar = { MindToolsTopBar() },
        bottomBar = {
            MindToolsBottomBar(
                onAddClicked = onAddClicked,
                onTuneClicked = onFilterClicked,
                onQuoteClicked = {
                    coroutineScope.launch {
                        viewModel.fetchRandomQuote()
                    }

                }
            )
        }
    ) { paddingValues ->
        if (previewActivities != null) {
            // Preview mode: just show previewActivities, no filtering here
            ActivityListUI(
                activities = previewActivities,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onActivityClick = {}
            )
        } else {
            ActivityListScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}


@Composable
fun ActivityListScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivitiesViewModel
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val feedbackPrompt by viewModel.showFeedbackPrompt.collectAsState()
    val filterMoods by viewModel.filterMoods.collectAsState()
    var pendingActivity by remember { mutableStateOf<Activity?>(null) }
    var dialogVisible by remember { mutableStateOf(false) }
    var selectedMoods by remember { mutableStateOf<Set<Mood>>(emptySet()) }
    val activities = uiState.activities
    val prompt = feedbackPrompt
    val filteredActivities = if (filterMoods.isEmpty()) {
        activities
    } else {
        activities.filter { activity ->
            filterMoods.any { mood -> (activity.helpfulnessByMood[mood] ?: 0) >= 3 }
        }
    }
    // 1. Log recompositions and filter values
    Log.d("ActivityListScreen", "Recomposed! filterMoods = $filterMoods")

    // 2. Log counts and filter effect
    Log.d(
        "ActivityListScreen",
        "All activities count = ${activities.size}, Filtered count = ${filteredActivities.size}, filterMoods = $filterMoods"
    )

    // 3. Log details of each filtered activity (for deep debugging)
    filteredActivities.forEach { activity ->
        Log.d(
            "ActivityListScreen",
            "Filtered activity: id=${activity.id}, title=${activity.title}, helpfulnessByMood=${activity.helpfulnessByMood}"
        )
    }

    filterMoods.forEach { mood ->
        Log.d(
            "MoodTest",
            "filterMoods element: $mood (${mood::class} @${System.identityHashCode(mood)})"
        )
    }

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
    }





    // Mood selection dialog
    if (dialogVisible && pendingActivity != null) {
        AlertDialog(
            onDismissRequest = {
                dialogVisible = false
                pendingActivity = null
            },
            title = { Text(stringResource(R.string.select_moods)) },
            text = {
                Column {

                    Mood.entries.forEach { mood ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedMoods = selectedMoods.toMutableSet().also {
                                        if (it.contains(mood)) it.remove(mood) else it.add(mood)
                                    }
                                }
                        ) {
                            Checkbox(
                                checked = selectedMoods.contains(mood),
                                onCheckedChange = {
                                    selectedMoods = selectedMoods.toMutableSet().also { set ->
                                        if (it) set.add(mood) else set.remove(mood)
                                    }
                                }
                            )
                            Text(stringResource(mood.label))
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (pendingActivity != null) {
                        viewModel.confirmMoodsAndLaunch(
                            pendingActivity!!,
                            ctx,
                            selectedMoods
                        )
                    }
                    dialogVisible = false
                    pendingActivity = null
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogVisible = false
                    pendingActivity = null
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }


    if (prompt?.second == true) {
        ActivityFeedbackDialog(
            onResult = { response ->
                viewModel.onFeedbackResponse(prompt.first ?: "", response)
            },
            onDismiss = {
                viewModel.onFeedbackResponse(prompt.first ?: "", FeedbackResponse.NO_CHANGE)
            }
        )
    }
    // Testing filter from this page and manually selecting a mood
//    Column(modifier = modifier.fillMaxSize()) {
//        Button(
//            onClick = { viewModel.setFilterMoods(setOf(Mood.TIRED)) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            Text(text = "Filter: Don't show Low Tired scored Activities")
//        }

//        Spacer(modifier = Modifier.height(8.dp))


        ActivityListUI(
            activities = filteredActivities,
            modifier = modifier,
            onActivityClick = {
                pendingActivity = it
                dialogVisible = true
                selectedMoods = viewModel.selectedMoods

            }
        )
//    }
}





@Composable
fun ActivityListUI(
    activities: List<Activity>,
    modifier: Modifier = Modifier,
    onActivityClick: (Activity) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(activities) { activity ->
            ActivityRow(
                activity = activity,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                onClick = { onActivityClick(activity) }
            )
        }
    }
}



@Composable
fun ActivityRow(
    activity: Activity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MindToolIcon(
                imageVector = activity.icon,
                contentDescription = activity.title // <-- show plain text
            )

            Text(
                text = activity.title, // <-- show user-typed title!
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
        // Display helpfulness ratings (for debugging)
//        activity.helpfulnessByMood.forEach { (mood, rating) ->
//            Text(
//                text = "${stringResource(mood.label)}: $rating",
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
    }
}

@Composable
fun MindToolIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindToolsTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.mindtools_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.mind_tools),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    )
}

@Composable
fun MindToolsBottomBar(
    modifier: Modifier = Modifier,
    onAddClicked: () -> Unit = {},
    onTuneClicked: () -> Unit = {},
    onQuoteClicked: () -> Unit = {}   // New callback for quote button
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = onAddClicked) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
            IconButton(onClick = onQuoteClicked) {  // replaced Search with Quote button
                Icon(
                    imageVector = Icons.Rounded.FormatQuote,
                    contentDescription = "Inspirational Quote"
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onTuneClicked,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Tune,
                    contentDescription = stringResource(R.string.filter_activities)
                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.filter_by_mood)
                )
            }
        }
    )
}



//@Preview(showBackground = true)
//@Composable
//fun ActivityToolsPagePreview() {
//    MindToolsAppTheme {
//        val sampleActivities = listOf(
//            Activity(
//                id = "1",
//                titleRes = R.string.cbt,
//                icon = getIconByName("attachment"),
//                iconName = "attachment",
//                mindToolResource = "file:///android_asset/guide.pdf",
//                helpfulnessByMood = emptyMap()
//            ),
//            Activity(
//                id = "2",
//                titleRes = R.string.sleep_video,
//                icon = getIconByName("ondemand_video"),
//                iconName = "ondemand_video",
//                mindToolResource = "https://youtube.com",
//                helpfulnessByMood = emptyMap()
//            ),
//            Activity(
//                id = "3",
//                titleRes = R.string.call_lifeline,
//                icon = getIconByName("call"),
//                iconName = "call",
//                mindToolResource = "tel:+61131114",
//                helpfulnessByMood = emptyMap()
//            )
//        )
//
//        val mockNavController = rememberNavController()
//        ActivityToolsPage(
//            navController = mockNavController,
//            previewActivities = sampleActivities
//        )
//    }
//}






