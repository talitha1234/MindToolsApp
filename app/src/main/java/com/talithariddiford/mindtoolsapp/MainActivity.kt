package com.talithariddiford.mindtoolsapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune


import androidx.compose.material3.IconButton

import androidx.compose.runtime.Composable
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
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        MindToolsTopBar()
        ActivityList()
    }
}

@Composable
fun MindToolsTopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.mind_tools),
            style = TextStyle(
                fontSize = 24.sp,
                shadow = Shadow(color = Color.Black, blurRadius = 3f)
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /* Add */ }) {
            Icon(Icons.Rounded.Add, contentDescription = "Add")
        }
        IconButton(onClick = { /* Search */ }) {
            Icon(Icons.Rounded.Search, contentDescription = "Search")
        }
        IconButton(onClick = { /* Filter */ }) {
            Icon(Icons.Rounded.Tune, contentDescription = "Filter")
        }
    }
}


@Composable
fun ActivityList() {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(8.dp),
        columns = GridCells.Fixed(1)
    ) {
        items(100) {
            Text(
                modifier = Modifier
                    .padding(30.dp),

                text = "Activity number $it",
                style = TextStyle(
                    fontSize = 24.sp,
                    shadow = Shadow(
                        color = Color.Unspecified, blurRadius = 3f)
            ))
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
@Preview(showBackground = true)
fun MoodSelectionPagePreview() {
    MoodSelectionPage()
}

@Composable
@Preview(showBackground = true)
fun MindToolsPagePreview(){
    MindToolsPage()
}
