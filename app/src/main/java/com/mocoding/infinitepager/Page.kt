package com.mocoding.infinitepager

import androidx.compose.ui.graphics.Brush
import java.util.UUID

data class Page(
    val id: String = UUID.randomUUID().toString(),
    val brush: Brush,
)
