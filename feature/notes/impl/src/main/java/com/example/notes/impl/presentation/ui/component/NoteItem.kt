package com.example.notes.impl.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.shared.feature.note.ui.models.NoteItemUiModel
import com.example.designsystem.theme.Typography

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: NoteItemUiModel,
    isDivider: Boolean = false,
    onItemClick: (String, String) -> Unit,
) {
    Column {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable {
                    onItemClick(note.id, note.folder.id)
                },
        ) {
            Text(
                text = note.title,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = Typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
            Row {
                Text(
                    text = note.createDate,
                    style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = note.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                )
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_folder_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = note.folder.name,
                    style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
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
