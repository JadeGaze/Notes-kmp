package com.example.feature.folders.impl.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.SectionData

@Composable
fun SectionHeader(sectionData: SectionData, isExpanded: Boolean, onHeaderClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { onHeaderClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1.0f),
            text = sectionData.header,
        )
        val rotate = if (isExpanded) 90F else 0F
        Image(
            modifier = Modifier.rotate(rotate),
            painter = painterResource(R.drawable.outline_keyboard_arrow_right_24),
            contentDescription = ""
        )
    }

}