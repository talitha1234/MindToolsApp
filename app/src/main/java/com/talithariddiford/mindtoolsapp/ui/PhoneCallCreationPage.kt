package com.talithariddiford.mindtoolsapp.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.talithariddiford.mindtoolsapp.GeneralTopBar
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.ui.theme.MindToolsAppTheme

@Composable
fun PhoneCallActivityCreationPage(
    modifier: Modifier = Modifier,
    onSave: (String, String) -> Unit = { _, _ -> }
) {
    var contactName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    MindToolsAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = { GeneralTopBar(stringResource(R.string.phone_call)) },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Spacer(Modifier.weight(1f))
                        FloatingActionButton(
                            onClick = { onSave(contactName, phoneNumber) }
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
                    text = stringResource(R.string.enter_contact_details),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text(stringResource(R.string.contact_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors() // Use theme defaults
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(stringResource(R.string.phone_number)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors() // Use theme defaults
                )
            }
        }
    }
}



@Preview
@Composable
fun PhoneCallActivityPageCreationPreview() {
    PhoneCallActivityCreationPage()
}