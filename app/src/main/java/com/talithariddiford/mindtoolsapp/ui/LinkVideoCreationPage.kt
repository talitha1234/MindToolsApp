package com.talithariddiford.mindtoolsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import com.talithariddiford.mindtoolsapp.viewmodel.LinkVideoCreationViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LinkVideoCreationPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSave: (title: String, url: String) -> Unit = { _, _ -> },
    viewModel: LinkVideoCreationViewModel = koinViewModel()
) {
    var videoUrl by rememberSaveable { mutableStateOf("") }
    var videoTitle by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { GeneralTopBar(stringResource(R.string.link_video), navController = navController) },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Spacer(Modifier.weight(1f))
                        FloatingActionButton(
                            onClick = {
                                isError = !viewModel.isValidURL(videoUrl)
                                if (!isError) onSave(videoTitle, videoUrl)
                            }
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
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    text = stringResource(R.string.enter_video_details),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = videoTitle,
                    onValueChange = { videoTitle = it },
                    label = { Text(stringResource(R.string.video_title)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = videoUrl,
                    singleLine = true,
                    shape = MaterialTheme.shapes.large,
                    onValueChange = {
                        videoUrl = it
                        isError = false
                    },
                    label = {
                        if (isError) {
                            Text(stringResource(R.string.invalid_url))
                        } else {
                            Text(stringResource(R.string.video_url))
                        }
                    },
                    isError = isError,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            isError = !viewModel.isValidURL(videoUrl)
                            if (!isError) onSave(videoTitle, videoUrl)
                        }
                    )
                )
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun LinkVideoCreationPagePreview() {
    val fakeViewModel = object : LinkVideoCreationViewModel() {
        override fun isValidURL(url: String): Boolean = true
    }

    val navController = rememberNavController()

    MindToolsAppTheme {
        LinkVideoCreationPage(
            navController = navController,
            viewModel = fakeViewModel
        )
    }
}
