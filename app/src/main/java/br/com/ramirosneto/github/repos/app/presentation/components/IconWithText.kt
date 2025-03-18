package br.com.ramirosneto.github.repos.app.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconWithText(icon: Any, text: String) {
    Row(modifier = Modifier.padding(end = 4.dp)) {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )

            is Painter -> Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}