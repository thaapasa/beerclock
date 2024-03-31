package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(50)).border(
                BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(50)
            ),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            content = content
        )
    }
}

@Composable
fun SegmentDivider(color: Color = MaterialTheme.colorScheme.outline) {
    HorizontalDivider(
        thickness = 1.dp,
        modifier = Modifier.width(1.dp).fillMaxHeight(),
        color = color
    )
}

@Composable
fun RowScope.TextSegment(
    text: String,
    color: Color = Color.Unspecified,
    background: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier,
) {
    Text(
        text,
        modifier = modifier.background(background).weight(1f).padding(8.dp)
            .align(Alignment.CenterVertically),
        color = color,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
    )
}
