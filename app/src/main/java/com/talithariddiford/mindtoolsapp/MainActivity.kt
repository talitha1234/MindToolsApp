package com.talithariddiford.mindtoolsapp



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import com.talithariddiford.mindtoolsapp.views.ActivityListScreen



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindToolsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MindToolsApp(innerPadding = innerPadding)
                }


            }

        }
    }
}


@Composable
fun MindToolsApp(innerPadding: PaddingValues) {
    ActivityToolsPage(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding))
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivityToolsPage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { MindToolsTopBar() },
        bottomBar = { MindToolsBottomBar() },

    ) { paddingValues ->
        ActivityListScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
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
                    contentDescription = stringResource(R.string.add),


                )
            }
            IconButton(onClick = { /* TODO: Handle Search */ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search_activities),

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

                )
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.filter_by_mood),

                )
            }
        }
    )
}












@Composable
fun MoodSelectionPage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { GeneralTopBar(stringResource(R.string.mood_title)) },
        bottomBar = {MoodSelectionBottomBar()},

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
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.mood_number, index))
                    Spacer(Modifier.weight(1f))
                    Checkbox(
                        checked = checkedStates.getOrDefault(index, false),
                        onCheckedChange = { checkedStates[index] = it }
                    )
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTopBar(topTitle: String, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = topTitle,

            )
        },
        navigationIcon = {
            IconButton(onClick = { /* handle back click */ }) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back),

                )
            }
        },
    )

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


@Composable
fun ActivityTypePage(modifier: Modifier = Modifier) {
    Scaffold(
        modifier   = modifier,
        topBar     = { GeneralTopBar(stringResource(R.string.choose_activity_type)) },
        bottomBar  = { MoodSelectionBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement   = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium), Alignment.CenterVertically),
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {

            val buttonModifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height))

            Button(onClick = { /*…*/ }, modifier = buttonModifier) {
                Text(
                    text  = stringResource(R.string.text_based),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(onClick = { /*…*/ }, modifier = buttonModifier) {
                Text(
                    text  = stringResource(R.string.link_video),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(onClick = { /*…*/ }, modifier = buttonModifier) {
                Text(
                    text  = stringResource(R.string.phone_call),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(onClick = { /*…*/ }, modifier = buttonModifier) {
                Text(
                    text  = stringResource(R.string.document),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}



@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
//@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun ActivityTypePagePreview() {
    MindToolsAppTheme {
        ActivityTypePage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
//@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun MoodSelectionPagePreview() {
    MindToolsAppTheme {
        MoodSelectionPage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
//@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun ActivityToolsPagePreview() {
    MindToolsAppTheme {
        ActivityToolsPage()
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
//@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun MindToolsAppPreview() {
    MindToolsAppTheme {
        MoodSelectionPage(modifier = Modifier.fillMaxSize())
    }
}


@Preview(name = "Phone", device = "spec:width=411dp,height=891dp", showBackground = true)
//@Preview(name = "Tablet", device = "spec:width=800dp,height=1280dp", showBackground = true)
@Composable
fun ActivityToolsPageDarkPreview() {
    MindToolsAppTheme(darkTheme = true) {
        ActivityToolsPage()
    }
}

@Preview
@Composable
fun MindToolsBottomBarPreview(){
    MindToolsBottomBar()
}