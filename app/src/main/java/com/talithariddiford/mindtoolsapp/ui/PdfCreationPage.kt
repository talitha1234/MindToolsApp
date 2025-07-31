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
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.talithariddiford.mindtoolsapp.R




@Composable
fun PdfCreationPage(
    modifier: Modifier = Modifier,
    onSaved: () -> Unit = {},
    navController: NavHostController
) {
    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = {
                GeneralTopBar(
                    topTitle = stringResource(R.string.document),
                    navController = navController
                )
            },
            bottomBar = {
                BottomAppBar {
                    Spacer(modifier = Modifier.weight(1f))
                    FloatingActionButton(onClick = { onSaved() }) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = stringResource(R.string.save_selection)
                        )
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
    val navController = rememberNavController()
    MindToolsAppTheme {
        PdfCreationPage(navController = navController)
    }
}
