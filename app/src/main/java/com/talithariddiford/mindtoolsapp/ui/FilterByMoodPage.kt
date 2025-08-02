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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterByMoodPage(
    navController: NavHostController,
    viewModel: ActivitiesViewModel = koinViewModel(), // Inject ViewModel
    modifier: Modifier = Modifier
) {
    var selectedMoods by rememberSaveable {
        mutableStateOf(Mood.entries.associateWith { viewModel.filterMoods.contains(it) })
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Capture stringResource value here (inside @Composable scope)
    val filteredByMoodMsg = stringResource(R.string.tools_filtered_by_mood)

    // Define onSave as a lambda that uses the pre-captured string
    val onSave = {
        val moodsSelected = selectedMoods.filterValues { it }.keys.toSet()
        viewModel.setFilterMoods(moodsSelected)

        coroutineScope.launch {
            // Use pre-captured string here
            snackbarHostState.showSnackbar(filteredByMoodMsg)
        }

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

@Preview
@Composable
fun MoodSelectionPagePreview() {
    val navController = rememberNavController()
    MindToolsAppTheme {
        FilterByMoodPage(navController = navController)
    }
}
