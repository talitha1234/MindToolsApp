package com.talithariddiford.mindtoolsapp



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton


import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindToolsAppTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    MindToolsApp(innerPadding)
                }

            }

        }
    }
}


@Composable
fun MindToolsApp(innerPadding: PaddingValues) {
    MoodSelectionPage(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding))
}


@Composable
fun MindToolsPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { MindToolsTopBar() },
        bottomBar = { MindToolsBottomBar() },

    ) { paddingValues ->
        ActivityList(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindToolsTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.mind_tools),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun MindToolsBottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* TODO: Handle Add */ }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add),
                    tint = MaterialTheme.colorScheme.onSurface

                )
            }
            IconButton(onClick = { /* TODO: Handle Search */ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search_activities),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: Handle filter */ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Tune,
                    contentDescription = stringResource(R.string.filter_activities),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.filter_by_mood),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}




@Composable
fun ActivityList(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

            .padding(8.dp),
        columns = GridCells.Fixed(1)
    ) {
        items(30) {
            Text(
                modifier = Modifier
                    .padding(30.dp),

                text = stringResource(R.string.activity_number, it),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground

            )

        }
    }
}




@Composable
fun MoodSelectionPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { GeneralTopBar(stringResource(R.string.mood_title)) },
        bottomBar = {MoodSelectionBottomBar()},

        ) { paddingValues ->
        MoodList(modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MoodList(modifier: Modifier = Modifier) {
    val checkedStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(8.dp),
        columns = GridCells.Fixed(1)

    ) {
        items(8) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.mood_number, index),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = checkedStates.getOrDefault(index, false),
                    onCheckedChange = { checked ->
                        checkedStates[index] = checked
                    }
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTopBar(topTitle: String) {
    TopAppBar(
        title = {
            Text(
                text = topTitle,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* handle back click */ }) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )

}

@Composable
fun MoodSelectionBottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* TODO: Handle Home */ }) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = stringResource(R.string.home),
                    tint = MaterialTheme.colorScheme.onPrimary
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
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
        }
    )
}


@Composable
fun ActivityTypePage() {
    Scaffold(
        topBar = { GeneralTopBar(stringResource(R.string.choose_activity_type)) },
        bottomBar = { MoodSelectionBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttonModifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)

            Button(
                onClick = { /* TODO */ },
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.text_based),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = { /* TODO */ },
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.link_video),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = { /* TODO */ },
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.phone_call),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Button(
                onClick = { /* TODO */ },
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.document),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun ActivityTypePagePreview() {
    MindToolsAppTheme {
        ActivityTypePage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun MoodSelectionPagePreview() {
    MindToolsAppTheme {
        MoodSelectionPage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun MindToolsPagePreview() {
    MindToolsAppTheme {
        MindToolsPage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun MindToolsAppPreview() {
    MindToolsAppTheme {
        MoodSelectionPage(modifier = Modifier.fillMaxSize())
    }
}


