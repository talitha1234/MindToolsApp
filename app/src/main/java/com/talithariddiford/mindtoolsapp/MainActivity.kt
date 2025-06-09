package com.talithariddiford.mindtoolsapp



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindToolsApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MindToolsApp() {
    MindToolsPage(modifier = Modifier
        .fillMaxSize())
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
            Text(text = "Mind Tools")
        }
    )
}

@Composable
fun MindToolsBottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* TODO: Handle Add */ }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add"
                )
            }
            IconButton(onClick = { /* TODO: Handle Search */ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Activities"
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: Handle filter */ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Tune,
                    contentDescription = "Filter Activities"
                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Filter by Mood"
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
            .background(Color.LightGray)
            .padding(8.dp),
        columns = GridCells.Fixed(1)
    ) {
        items(30) {
            Text(
                modifier = Modifier
                    .padding(30.dp),

                text = "Activity number $it",
                style = TextStyle(
                    fontSize = 24.sp,
                    shadow = Shadow(
                        color = Color.Unspecified, blurRadius = 3f))
            )

        }
    }
}

@Composable
fun MoodSelectionPage(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier,

            ) {
            IconButton(onClick = { /* handle click */ }) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(R.string.mood_title),
                style = TextStyle(
                    fontSize = 24.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 3f
                    )
                )
            )
        }
    }
}

@Composable
fun MoodSelectionPage() {
    Scaffold(
        topBar = { GeneralTopBar("Mood Filter") },
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
            .background(Color.Gray)
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
                    text = "Mood $index",
                    style = TextStyle(
                        fontSize = 24.sp,
                        shadow = Shadow(
                            color = Color.Unspecified, blurRadius = 3f)
                    ),
                    modifier = Modifier.weight(1f)
                )
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
            Text(text = topTitle)
        },
        navigationIcon = {
            IconButton(onClick = { /* handle back click */ }) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun MoodSelectionBottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* TODO: Handle Home */ }) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Home"
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Handle Save */ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Selection"
                )
            }
        }
    )
}


@Composable
fun ActivityTypePage() {
    Scaffold(
        topBar = { GeneralTopBar("Choose Activity Type") },
        bottomBar = {MoodSelectionBottomBar()}

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)

                .fillMaxSize()
            .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Button(onClick = { /* TODO: Handle Text Based */ }) {
                Text("Text Based")
            }

            Button(onClick = { /* TODO: Handle Link/Video */ }) {
                Text("Link/Video")
            }
            Button(onClick = { /* TODO: Handle Phone Call */ }) {
                Text("Phone Call")
            }
            Button(onClick = { /* TODO: Handle Document */ }) {
                Text("Document")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ActivityTypePagePreview() {
    ActivityTypePage()
}

@Composable
@Preview(showBackground = true)
fun MoodSelectionPagePreview() {
    MoodSelectionPage()
}

@Composable
@Preview(showBackground = true)
fun MindToolsPagePreview(){
    MindToolsPage()
}

