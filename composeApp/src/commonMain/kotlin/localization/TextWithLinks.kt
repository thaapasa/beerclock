package fi.tuska.beerclock.localization

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import fi.tuska.beerclock.logging.getLogger

private val logger = getLogger("TextWithLinks")

open class PlainText(val text: String) {
    @Composable
    open fun append(target: AnnotatedString.Builder) {
        target.append(text)
    }
}

class LinkText(val url: String, text: String) : PlainText(text) {
    @Composable
    override fun append(target: AnnotatedString.Builder) {
        target.pushStringAnnotation(tag = "URL", annotation = url)
        target.withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(text)
        }
        target.pop()
    }
}


typealias TextContent = Array<PlainText>

@Composable
fun TextWithLinks(
    content: TextContent,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: ((url: String) -> Unit)? = null,
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)) {
            content.forEach {
                it.append(this)
            }
        }
    }

    ClickableText(
        text,
        style = MaterialTheme.typography.bodyMedium,
        onClick = { offs ->
            text.getStringAnnotations(tag = "URL", start = offs, end = offs).firstOrNull()
                ?.let { annotation ->
                    (onClick ?: ::openUrl)(annotation.item)
                }
        })
}

fun openUrl(url: String) {
    logger.info("Click on $url")
}
