package com.talithariddiford.mindtoolsapp.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.packt.chapterseven.navigation.Screens
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import com.talithariddiford.mindtoolsapp.util.getIconByName
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import org.koin.androidx.compose.koinViewModel



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivityToolsPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    previewActivities: List<Activity>? = null
) {
    Scaffold(
        modifier = modifier,
        topBar = { MindToolsTopBar() },
        bottomBar = {
            MindToolsBottomBar(
                onAddClicked = {
                    navController.navigate(Screens.AddActivityPage.route)
                },
                onTuneClicked = {
                    navController.navigate(Screens.AddFilterByMoodPage.route)
                }
            )
        }
    ) { paddingValues ->
        if (previewActivities != null) {
            // Preview mode: skip ViewModel and use only preview data
            ActivityListUI(
                activities = previewActivities,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onActivityClick = {}
            )
        } else {
            // Runtime: use ViewModel from Koin
            val viewModel: ActivitiesViewModel = koinViewModel()
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
    previewActivities: List<Activity>? = null,
    viewModel: ActivitiesViewModel
) {
    val ctx = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val activities = previewActivities ?: uiState.activities

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
    }

    var pendingActivity by remember { mutableStateOf<Activity?>(null) }
    var dialogVisible by remember { mutableStateOf(false) }
    var selectedMoods by remember { mutableStateOf<Set<Mood>>(emptySet()) }

    // Show mood dialog if needed
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

    ActivityListUI(
        activities = activities,
        modifier = modifier,
        onActivityClick = {
            // Always open dialog for mood selection
            pendingActivity = it
            dialogVisible = true
            selectedMoods = viewModel.selectedMoods // Or emptySet() if you want to reset
        }
    )
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
                contentDescription = stringResource(activity.titleRes)
            )

            Text(
                text = stringResource(activity.titleRes),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
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
    onTuneClicked: () -> Unit = {}
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
            IconButton(onClick = { /* TODO: Handle Search */ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search_activities)
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onTuneClicked,
            ){
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


@Preview(showBackground = true)
@Composable
fun ActivityToolsPagePreview() {
    MindToolsAppTheme {
        val sampleActivities = listOf(
            Activity(
                id = "1",
                titleRes = R.string.cbt,
                icon = getIconByName("attachment"),
                iconName = "attachment",
                mindToolResource = "file:///android_asset/guide.pdf",
                helpfulnessByMood = emptyMap()
            ),
            Activity(
                id = "2",
                titleRes = R.string.sleep_video,
                icon = getIconByName("ondemand_video"),
                iconName = "ondemand_video",
                mindToolResource = "https://youtube.com",
                helpfulnessByMood = emptyMap()
            ),
            Activity(
                id = "3",
                titleRes = R.string.call_lifeline,
                icon = getIconByName("call"),
                iconName = "call",
                mindToolResource = "tel:+61131114",
                helpfulnessByMood = emptyMap()
            )
        )

        val mockNavController = rememberNavController()
        ActivityToolsPage(
            navController = mockNavController,
            previewActivities = sampleActivities
        )
    }
}






