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
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivityToolsPage(
    modifier: Modifier = Modifier,
    previewActivities: List<Activity>? = null
) {
    Scaffold(
        modifier = modifier,
        topBar = { MindToolsTopBar() },
        bottomBar = { MindToolsBottomBar() }
    ) { paddingValues ->
        ActivityListScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            previewActivities = previewActivities
        )
    }
}


@Composable
fun ActivityListScreen(
    modifier: Modifier = Modifier,
    previewActivities: List<Activity>? = null,
    viewModel: ActivitiesViewModel = koinViewModel()
) {
    val ctx = LocalContext.current
    val activities = previewActivities ?: viewModel.loadActivities()

    var pendingActivity by remember { mutableStateOf<Activity?>(null) }

    // Show mood dialog if needed
    if (viewModel.showMoodDialog.value && pendingActivity != null) {
        AlertDialog(
            onDismissRequest = {
                viewModel.showMoodDialog.value = false
                pendingActivity = null
            },
            title = { Text("Select Mood") },
            text = {
                Column {
                    Mood.entries.forEach { mood ->
                        TextButton(onClick = {
                            viewModel.confirmMoodAndLaunch(pendingActivity!!, ctx, mood)
                            pendingActivity = null
                        }) {
                            Text(stringResource(mood.label))
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = {
                    viewModel.showMoodDialog.value = false
                    pendingActivity = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Show the activity list
    ActivityListUI(
        activities = activities,
        modifier = modifier,
        onActivityClick = {
            pendingActivity = it
            viewModel.onActivitySelected(it, ctx)
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
fun MindToolsBottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { /* TODO: Handle Add */ }) {
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
                onClick = { /* TODO: Handle filter */ }
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

//@Preview
//@Composable
//fun ActivityToolsPagePreview() {
//    MindToolsAppTheme {
//        ActivityToolsPage()
//    }
//}



