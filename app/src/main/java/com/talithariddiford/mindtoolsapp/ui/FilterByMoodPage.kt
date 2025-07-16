package com.talithariddiford.mindtoolsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.talithariddiford.mindtoolsapp.GeneralTopBar
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme

@Composable
fun MoodSelectionPage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { GeneralTopBar(stringResource(R.string.mood_title)) },
        bottomBar = { MoodSelectionBottomBar() }
    ) { paddingValues ->
        MoodList(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MoodList(
    modifier: Modifier = Modifier,
    selectedMoods: Map<Mood, Boolean> = emptyMap(),
    onMoodToggle: (Mood, Boolean) -> Unit = { _, _ -> }
) {
    val moods = Mood.values()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        moods.forEach { mood ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(mood.label),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = selectedMoods[mood] == true,
                        onCheckedChange = { onMoodToggle(mood, it) }
                    )
                }
            }
        }
    }
}


@Composable
fun MoodSelectionBottomBar(
    modifier: Modifier = Modifier,
    onMoodSave: () -> Unit = {}
) {
    BottomAppBar(
        modifier = modifier,
        actions = { },
        floatingActionButton = {
            FloatingActionButton(onClick = onMoodSave) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(R.string.save_selection)
                )
            }
        }
    )
}



@Preview
@Composable
fun MoodSelectionPagePreview() {
    MindToolsAppTheme {
        MoodSelectionPage()
    }
}
