package fi.tuska.beerclock.ui.shapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * Defines a shape that covers a vertical slice of the entire shape, covering the given
 * range of x values (range is from 0 to 1).
 *
 * So, to create a slice that covers the left half of an image you can use
 * `VerticalSliceShape(0f..0.5f)`.
 */
class VerticalSliceShape(private val range: ClosedFloatingPointRange<Double>) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            addRect(
                Rect(
                    range.start.toFloat() * size.width,
                    0f,
                    range.endInclusive.toFloat() * size.width,
                    size.height
                )
            )
        }
        return Outline.Generic(path)
    }
}
