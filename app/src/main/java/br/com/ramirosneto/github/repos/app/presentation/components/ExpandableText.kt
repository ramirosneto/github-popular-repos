package br.com.ramirosneto.github.repos.app.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 2,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var canExpand by remember { mutableStateOf(false) }

    Text(
        text = text,
        modifier = modifier
            .clickable { isExpanded = isExpanded.not() }
            .animateContentSize(animationSpec = tween(100)),
        maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            canExpand = textLayoutResult.hasVisualOverflow
        }
    )
}