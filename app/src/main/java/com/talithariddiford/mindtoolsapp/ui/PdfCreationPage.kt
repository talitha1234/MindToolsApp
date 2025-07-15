package com.talithariddiford.mindtoolsapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.talithariddiford.mindtoolsapp.GeneralTopBar
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.talithariddiford.mindtoolsapp.R




@Composable
fun PdfCreationPage(
    modifier: Modifier = Modifier,
    onSaved: () -> Unit = {}
) {
    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { GeneralTopBar(stringResource(R.string.document)) },
            bottomBar = {
                BottomAppBar {
                    Spacer(modifier = Modifier.weight(1f))
                    FloatingActionButton(onClick = {
                        // For now just trigger onSaved
                        onSaved()
                    }) {
                        Icon(Icons.Rounded.Check, contentDescription = stringResource(R.string.save_selection))
                    }
                }
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
                    text = "PDF creation screen (coming soon)",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PdfCreationPagePreview() {
    MindToolsAppTheme {
        PdfCreationPage()
    }
}
