package fi.tuska.beerclock.localization

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

open class PlainText(val text: String) {
    @Composable
    open fun Append(target: AnnotatedString.Builder) {
        target.append(text)
    }
}

class LinkText(val url: String, text: String) : PlainText(text) {
    @Composable
    override fun Append(target: AnnotatedString.Builder) {
        target.pushStringAnnotation(tag = "URL", annotation = url)
        target.withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            withLink(LinkAnnotation.Url(url)) { append(text) }
        }
        target.pop()
    }
}


typealias TextContent = Array<PlainText>

@Composable
fun TextWithLinks(
    content: TextContent,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)) {
            content.forEach {
                it.Append(this)
            }
        }
    }

    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
    )
}
