package com.talithariddiford.mindtoolsapp.views

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepositoryImpl
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.res.stringResource
import com.talithariddiford.mindtoolsapp.R

// Stateless UI: just takes your model list and displays it
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
                            Toast.makeText(ctx, "PDF preview not available yet", Toast.LENGTH_SHORT).show()
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
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MindToolIcon(
                imageVector        = activity.icon,
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
        imageVector        = imageVector,
        contentDescription = contentDescription,
        modifier           = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small)
    )
}

// 2️⃣ Runtime wrapper: injects your ViewModel via Koin
@Composable
fun ActivityListScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivitiesViewModel = koinViewModel()
) {
    val activities = viewModel.loadActivities()
    ActivityListUI(
        activities = activities,
        modifier   = modifier.fillMaxSize()
    )
}

// 3️⃣ Preview: feeds in sample data so you can see it in the IDE
@Preview(
    name           = "Activity List Preview",
    showBackground = true,
    widthDp        = 360,
    heightDp       = 640
)
@Composable
fun ActivityListPreview() {
    MindToolsAppTheme {
        ActivityListUI(
            activities = ActivitiesRepositoryImpl().loadActivities(),
            modifier   = Modifier.fillMaxSize()
        )
    }
}
