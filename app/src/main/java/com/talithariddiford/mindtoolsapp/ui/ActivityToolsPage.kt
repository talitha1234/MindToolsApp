package com.talithariddiford.mindtoolsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
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
    val activities = previewActivities ?: viewModel.loadActivities()
    ActivityListUI(
        activities = activities,
        modifier = modifier
    )
}


@Composable
fun ActivityListUI(
    activities: List<Activity>,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current

    LazyColumn(modifier = modifier) {
        items(activities) { activity ->
            ActivityRow(
                activity = activity,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                onClick = {
                    val uri = activity.payload.toUri()
                    val intent = when {
                        uri.scheme == "tel" ->
                            Intent(Intent.ACTION_DIAL, uri)

                        uri.scheme?.startsWith("http") == true ->
                            Intent(Intent.ACTION_VIEW, uri)

                        uri.path?.endsWith(".pdf") == true -> {
                            Toast.makeText(ctx, "PDF preview not available yet", Toast.LENGTH_SHORT)
                                .show()
                            null
                        }

                        else -> null
                    }
                    intent?.let { ctx.startActivity(it) }
                }
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

//@Preview(
//    name = "Activity Tools Page Preview",
//    showBackground = true,
//    widthDp = 360,
//    heightDp = 640
//)
//@Composable
//fun ActivityToolsPagePreview() {
//    MindToolsAppTheme {
//        ActivityToolsPage(
//            previewActivities = ActivitiesRepositoryImpl().loadActivities()
//        )
//    }
//}



