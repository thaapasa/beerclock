package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.ui.shapes.VerticalSliceShape

@Composable
fun Rating(
    rating: Double?,
    modifier: Modifier = Modifier,
    onSelect: ((rating: Double) -> Unit)? = null,
) {
    var rowModifier = Modifier.wrapContentHeight().fillMaxWidth()
    if (rating == null) {
        rowModifier = rowModifier.alpha(0.5f)
    }
    Row(
        modifier = rowModifier.then(modifier),
        horizontalArrangement = Arrangement.Center,
    ) {
        (0..4).map {
            Box(modifier = Modifier.wrapContentSize().clip(RoundedCornerShape(50)).clickable {
                onSelect?.invoke(it + 1.0)
            }) {
                RatingStar(fill = ((rating ?: 0.0) - it).coerceIn(0.0, 1.0))
            }
        }
    }
}

@Composable
fun RatingStar(fill: Double, modifier: Modifier = Modifier) {
    when {
        fill <= 0 -> AppIcon.STAR.icon(
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.alpha(0.8f)
        )

        fill >= 1 -> AppIcon.STAR_FILLED.icon(tint = MaterialTheme.colorScheme.primary)

        else -> {
            Box {
                AppIcon.STAR_FILLED.icon(
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clip(VerticalSliceShape(0.0..fill))
                )
                AppIcon.STAR.icon(
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clip(VerticalSliceShape(fill..1.0)).alpha(0.8f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingField(
    value: Double?,
    onValueChange: (rating: Double) -> Unit,
    label: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    enabled: Boolean = true,
    isError: Boolean = false,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextFieldDefaults.DecorationBox(
        value = value?.toString() ?: "",
        visualTransformation = visualTransformation,
        innerTextField = { Rating(value, onSelect = onValueChange) },
        placeholder = null,
        label = label,
        singleLine = true,
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        container = {
            OutlinedTextFieldDefaults.ContainerBox(
                enabled,
                isError,
                interactionSource,
                colors,
                shape
            )
        }
    )
}
