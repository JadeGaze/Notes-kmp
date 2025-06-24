package com.example.feature.folders.impl.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.feature.folders.impl.presentation.model.FolderUiModel

@Composable
fun FolderItem(modifier: Modifier = Modifier, data: FolderUiModel, isDivider: Boolean = false) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(IntrinsicSize.Max)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Outlined.Folder,
                contentDescription = ""
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = data.name
            )
            Text(text = data.notesNumber.toString())
            Image(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier.padding(8.dp)
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