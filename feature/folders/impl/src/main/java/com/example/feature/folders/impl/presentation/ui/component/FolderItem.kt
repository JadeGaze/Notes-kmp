package com.example.feature.folders.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.shared.feature.folders.ui.model.FolderUiModel

@Composable
fun FolderItem(modifier: Modifier = Modifier, data: FolderUiModel, isDivider: Boolean = false) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .height(IntrinsicSize.Max)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(id = data.iconId?: R.drawable.outline_folder_24),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = data.name,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = data.notesNumber.toString(), color = MaterialTheme.colorScheme.primary)
            Icon(
                painter = painterResource(id = R.drawable.outline_keyboard_arrow_right_24),
                contentDescription = "",
                modifier = Modifier.padding(8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        if (isDivider) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Black)
            )
        }
    }
}