package com.talithariddiford.mindtoolsapp.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.talithariddiford.mindtoolsapp.R
import com.talithariddiford.mindtoolsapp.viewmodel.FeedbackResponse

@Composable
fun ActivityFeedbackDialog(
    onResult: (FeedbackResponse) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.feedback_title)) },
        text = { /* optional descriptive text here */ },
        confirmButton = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { onResult(FeedbackResponse.YES) }) {
                    Text(text = stringResource(R.string.feedback_yes))
                }
                TextButton(onClick = { onResult(FeedbackResponse.NO) }) {
                    Text(text = stringResource(R.string.feedback_no))
                }
                TextButton(onClick = { onResult(FeedbackResponse.NO_CHANGE) }) {
                    Text(text = stringResource(R.string.feedback_no_change))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.feedback_cancel))
            }
        }
    )
}



