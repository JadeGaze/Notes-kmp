package com.example.feature.folders.impl.presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.shared.core.common.SectionData
import com.example.shared.core.common.SectionItem
import com.example.shared.feature.folders.ui.model.FolderUiModel

@Composable
fun ExpandableList(
    modifier: Modifier = Modifier,
    sectionData: List<SectionData>,
    onItemClicked: (String) -> Unit,
) {
    val isExpandedMap = rememberSavableSnapshotStateMap {
        List(sectionData.size) { index: Int -> index to true }.toMutableStateMap()
    }

    LazyColumn(
        modifier = modifier
    ) {
        sectionData.onEachIndexed { index, sectionData ->
            this.section(
                sectionData = sectionData,
                isExpanded = isExpandedMap[index] ?: true,
                onItemClicked = onItemClicked
            ) {
                isExpandedMap[index] = !(isExpandedMap[index] ?: true)
            }
        }
    }
}


fun LazyListScope.section(
    sectionData: SectionData,
    isExpanded: Boolean,
    onItemClicked: (String) -> Unit,
    onHeaderClick: () -> Unit,
) {
    item {
        SectionHeader(
            sectionData = sectionData,
            isExpanded = isExpanded,
            onHeaderClick = onHeaderClick
        )
    }
    itemsIndexed(sectionData.items) { index, item ->
        val modifier = when (index) {
            0 -> {
                Modifier.clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
            }

            sectionData.items.lastIndex -> {
                Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp))
            }

            else -> {
                Modifier
            }
        }.clickable {
            onItemClicked(
                when (item) {
                    is FolderUiModel -> item.id
                    else -> ""
                }
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(ANIMATION_DURATION)) + expandVertically(
                animationSpec = tween(ANIMATION_DURATION)
            ),
            exit = fadeOut(animationSpec = tween(ANIMATION_DURATION)) + shrinkVertically(
                animationSpec = tween(ANIMATION_DURATION)
            )
        ) {
            ExpandedListItem(
                modifier = modifier,
                data = item,
                isDivider = index != sectionData.items.lastIndex
            )
        }
    }
}

private const val ANIMATION_DURATION = 500

@Composable
fun <K, V> rememberSavableSnapshotStateMap(init: () -> SnapshotStateMap<K, V>): SnapshotStateMap<K, V> =
    rememberSaveable(saver = snapshotStateMapSaver(), init = init)

fun <K, V> snapshotStateMapSaver() = Saver<SnapshotStateMap<K, V>, Any>(
    save = { state -> state.toList() },
    restore = { value ->
        @Suppress("UNCHECKED_CAST")
        (value as? List<Pair<K, V>>)?.toMutableStateMap() ?: mutableStateMapOf()
    }
)

@Composable
fun ExpandedListItem(modifier: Modifier = Modifier, data: SectionItem, isDivider: Boolean = false) {
    when (data) {
        is FolderUiModel -> {
            FolderItem(
                modifier = modifier,
                data = data,
                isDivider = isDivider
            )
        }
    }
}