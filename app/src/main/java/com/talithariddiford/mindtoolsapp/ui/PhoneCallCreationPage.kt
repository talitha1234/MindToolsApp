package com.talithariddiford.mindtoolsapp.ui

import PhoneCallViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme
import kotlinx.coroutines.launch

@Composable
fun PhoneCallActivityCreationPage(
    navController: NavHostController,
    viewModel: PhoneCallViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
    onSave: (String, String) -> Unit = { _, _ -> }
) {
    val contactName by viewModel.contactName.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val isValid by viewModel.isValid.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = { GeneralTopBar(stringResource(R.string.phone_call), navController) },
            bottomBar = {
                BottomAppBar {
                    Spacer(Modifier.weight(1f))
                    FloatingActionButton(
                        onClick = {
                            if (isValid) {
                                viewModel.saveActivity { name, number ->
                                    onSave(name, number)
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Activity added")
                                        navController.popBackStack()
                                    }
                                }
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Please enter both contact name and phone number")
                                }
                            }
                        }
                    ) {
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
                OutlinedTextField(
                    value = contactName,
                    onValueChange = viewModel::onContactNameChanged,
                    label = { Text(stringResource(R.string.contact_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors()
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = viewModel::onPhoneNumberChanged,
                    label = { Text(stringResource(R.string.phone_number)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors()
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PhoneCallActivityPageCreationPreview() {
    val navController = rememberNavController()
    PhoneCallActivityCreationPage(navController = navController)
}