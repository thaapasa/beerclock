package fi.tuska.beerclock.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class FlagIcon(private val path: String) {
    AT("drawable/flags/at.webp"),
    AU("drawable/flags/au.webp"),
    CA("drawable/flags/ca.webp"),
    DK("drawable/flags/dk.webp"),
    ES("drawable/flags/es.webp"),
    FI("drawable/flags/fi.webp"),
    FR("drawable/flags/fr.webp"),
    GB("drawable/flags/gb.webp"),
    HU("drawable/flags/hu.webp"),
    IE("drawable/flags/ie.webp"),
    IS("drawable/flags/is.webp"),
    IT("drawable/flags/it.webp"),
    JP("drawable/flags/jp.webp"),
    NL("drawable/flags/nl.webp"),
    NZ("drawable/flags/nz.webp"),
    PL("drawable/flags/pl.webp"),
    PT("drawable/flags/pt.webp"),
    SE("drawable/flags/se.webp"),
    US("drawable/flags/us.webp");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter {
        return painterResource(this.path)
    }

    @Composable
    fun image(contentDescription: String = name, modifier: Modifier = Modifier.width(20.dp)) {
        Image(
            painter = painter(),
            contentDescription = contentDescription,
            modifier = modifier
        )
    }


    companion object {
        fun forCountry(isoCountryCode: String): FlagIcon? = try {
            FlagIcon.valueOf(isoCountryCode)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}
