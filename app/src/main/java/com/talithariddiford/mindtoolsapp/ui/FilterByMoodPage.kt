package com.talithariddiford.mindtoolsapp.ui

import ActivitiesViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Mood
import kotlinx.coroutines.launch
import android.util.Log

@Composable
fun FilterByMoodPage(
    navController: NavHostController,
    viewModel: ActivitiesViewModel,
    modifier: Modifier = Modifier
) {
    // Compose recomposition logging
    Log.d("FilterByMoodPage", "Composable recomposed at ${System.currentTimeMillis()}")

    // Collect filter moods state from ViewModel (assuming it's a StateFlow<Set<Mood>>)
    val filterMoods by viewModel.filterMoods.collectAsState()

    // Local state for selections
    var selectedMoods by rememberSaveable {
        mutableStateOf(Mood.entries.associateWith { mood -> filterMoods.contains(mood) })
    }

    // Synchronize with filterMoods any time it changes!
    LaunchedEffect(filterMoods) {
        selectedMoods = Mood.entries.associateWith { mood -> filterMoods.contains(mood) }
        Log.d("FilterByMoodPage", "LaunchedEffect: filterMoods updated to $filterMoods; selectedMoods now ${selectedMoods.filterValues { it }.keys}")
    }

    // Debug text at the top of UI
    Column {
        Text(
            "DEBUG: filterMoods=${filterMoods.joinToString()} " +
                    "selectedMoods=${selectedMoods.filterValues { it }.keys.joinToString()}"
        )

    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val filteredByMoodMsg = stringResource(R.string.tools_filtered_by_mood)

    // Log and apply filter on save
    val onSave = {
        val moodsSelected = selectedMoods.filterValues { it }.keys.toSet()
        Log.d("FilterByMoodPage", "Saving filter: $moodsSelected")
        viewModel.setFilterMoods(moodsSelected)

        coroutineScope.launch {
            snackbarHostState.showSnackbar(filteredByMoodMsg)
        }
        Log.d("FilterByMoodPage", "Navigating back after save")
        navController.popBackStack()
    }

    Scaffold(
        modifier = modifier,
        topBar = { GeneralTopBar(stringResource(R.string.mood_title), navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            MoodSelectionBottomBar(onMoodSave = { onSave() })
        }
    ) { paddingValues ->
        MoodList(
            modifier = Modifier.padding(paddingValues),
            selectedMoods = selectedMoods,
            onMoodToggle = { mood, selected ->
                selectedMoods = selectedMoods.toMutableMap().apply { this[mood] = selected }
                Log.d("FilterByMoodPage", "Mood toggled: $mood set to $selected; selectedMoods now ${selectedMoods.filterValues { it }.keys}")
            }
        )
    }
}


@Composable
fun MoodList(
    modifier: Modifier = Modifier,
    selectedMoods: Map<Mood, Boolean> = emptyMap(),
    onMoodToggle: (Mood, Boolean) -> Unit = { _, _ -> }
) {
    val moods = Mood.entries.toTypedArray()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        moods.forEach { mood ->
            val isSelected = selectedMoods[mood] == true
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable { onMoodToggle(mood, !isSelected) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else
                        MaterialTheme.colorScheme.surface,
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

                    IconToggleButton(
                        checked = isSelected,
                        onCheckedChange = { onMoodToggle(mood, it) }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = if (isSelected)
                                stringResource(R.string.selected)
                            else
                                stringResource(R.string.not_selected),
                            tint = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
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

//@Preview
//@Composable
//fun MoodSelectionPagePreview() {
//    val navController = rememberNavController()
//    MindToolsAppTheme {
//        FilterByMoodPage(navController = navController)
//    }
//}
