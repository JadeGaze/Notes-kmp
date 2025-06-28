package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.component.status.ErrorIconShow
import com.example.designsystem.theme.AppShapes
import com.example.designsystem.theme.Typography

@Composable
fun FormField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    isError: Boolean,
    supportingText: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        singleLine = true,
        onValueChange = onValueChange,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = supportingText)
            }
        },
        trailingIcon = {
            ErrorIconShow(isError)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentInputText(
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation,
    interactionSource: InteractionSource,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .background(Color.LightGray, shape = AppShapes.medium),
        textStyle = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
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
}

@Composable
fun MediumTitleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier,
        textStyle = Typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary),
    )
}

@Composable
fun TextFieldFullScreen(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        value = value,
        onValueChange = onValueChange
    )
}