package com.talithariddiford.mindtoolsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.talithariddiford.mindtoolsapp.GeneralTopBar
import com.talithariddiford.mindtoolsapp.R
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
fun MoodList(modifier: Modifier = Modifier) {
    val checkedStates = remember { mutableStateMapOf<Int, Boolean>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        repeat(8) { index ->
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
                        text = stringResource(R.string.mood_number, index),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = checkedStates.getOrDefault(index, false),
                        onCheckedChange = { checkedStates[index] = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MoodSelectionBottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { /* TODO: Handle Home */ }) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = stringResource(R.string.home),

                    )

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Handle Save */ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(R.string.save_selection),

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
