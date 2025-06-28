package com.example.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

object ExtraShapes {
    val FirstItemShape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
    val LastItemShape = RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp)
}