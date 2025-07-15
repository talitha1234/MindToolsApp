package com.talithariddiford.mindtoolsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.talithariddiford.mindtoolsapp.GeneralTopBar

import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme


@Composable
fun LinkVideoCreationPage(
    modifier: Modifier = Modifier.Companion,
    onSave: (String) -> Unit = {}
) {
    var videoUrl by rememberSaveable { mutableStateOf("") }

    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { GeneralTopBar(stringResource(R.string.link_video)) },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Spacer(Modifier.Companion.weight(1f))
                        FloatingActionButton(
                            onClick = { onSave(videoUrl) }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = stringResource(R.string.save_selection)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.Companion
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.enter_video_url),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = videoUrl,
                    onValueChange = { videoUrl = it },
                    label = { Text(stringResource(R.string.video_url)) },
                    modifier = Modifier.Companion.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors()
                )
            }
        }
    }
}

@Preview
@Composable
fun LinkVideoCreationPagePreview() {
    LinkVideoCreationPage()
}