package com.example.feature.folders.impl.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.R
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.yellowAccent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFolderDialog(
    onDismissRequest: () -> Unit,
    onSaveRequest: (String, isSync: Boolean) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val textFieldValue = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val visualTransformation = remember { VisualTransformation.None }
    val focusRequester = remember { FocusRequester() }

    val isSync = remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.fillMaxHeight(0.90f),
        sheetState = sheetState,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cancel),
                color = yellowAccent,
                modifier = Modifier.clickable {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        Log.d("FOLDER_DIALOG", "cancel")
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                })
            Text(
                text = stringResource(R.string.new_folder),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.done),
                color = yellowAccent, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onSaveRequest(textFieldValue.value, isSync.value)
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                }
            )
        }

        BasicTextField(
            value = textFieldValue.value,
            onValueChange = { value ->
                textFieldValue.value = value
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .focusRequester(focusRequester),
            textStyle = TextStyle(fontSize = 16.sp),
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = textFieldValue.value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                placeholder = { Text(stringResource(R.string.foldername)) },
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Synchronization")
            Spacer(modifier = Modifier.width(16.dp))
            Switch(
                checked = isSync.value,
                onCheckedChange = { isSync.value = it }
            )
        }

        LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
            focusRequester.requestFocus()
        }

    }
}
