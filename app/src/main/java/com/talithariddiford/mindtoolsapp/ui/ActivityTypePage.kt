    package com.talithariddiford.mindtoolsapp.ui

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Button
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.res.dimensionResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.rememberNavController
    import com.packt.chapterseven.navigation.Screens
    import com.talithariddiford.mindtoolsapp.R
    import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme

    @Composable
    fun ActivityTypePage(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        onSelectType: (String) -> Unit = { type ->
            when (type) {
                "linkVideo" -> navController.navigate(Screens.AddLinkVideoCreationPage.route)
                "phoneCall" -> navController.navigate(Screens.AddPhoneCallCreationPage.route)
                "pdf" -> navController.navigate(Screens.AddPdfCreationPage.route)
            }
        }


    ) {
        MindToolsAppTheme {
            Scaffold(
                modifier = modifier,
                topBar = { GeneralTopBar(stringResource(R.string.choose_activity_type), navController) },

            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.padding_medium),
                        Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val buttonModifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_height))

                    Button(onClick = { onSelectType("linkVideo") }, modifier = buttonModifier) {
                        Text(
                            text = stringResource(R.string.link_video),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Button(onClick = { onSelectType("phoneCall") }, modifier = buttonModifier) {
                        Text(
                            text = stringResource(R.string.phone_call),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Button(onClick = { onSelectType("pdf") }, modifier = buttonModifier) {
                        Text(
                            text = stringResource(R.string.document),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun ActivityTypePagePreview() {
    MindToolsAppTheme {
        val navController = rememberNavController()
        ActivityTypePage(navController = navController)
    }
}

