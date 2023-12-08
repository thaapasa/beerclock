package fi.tuska.beerclock.images

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import fi.tuska.beerclock.settings.Gender
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class AppIcon(private val path: String) {
    MALE("drawable/icons/male.xml"),
    FEMALE("drawable/icons/female.xml"),
    BEER("drawable/icons/sports_bar.xml"),
    DRINK("drawable/icons/local_bar.xml"),
    HISTORY("drawable/icons/history.xml"),
    GRAPH("drawable/icons/graph.xml"),
    CLOCK("drawable/icons/clock.xml"),
    CALENDAR("drawable/icons/calendar.xml"),
    CLOSE("drawable/icons/close.xml"),
    EDIT("drawable/icons/edit.xml"),
    DELETE("drawable/icons/delete.xml"),
    LANGUAGE("drawable/icons/language.xml"),
    PERSON("drawable/icons/person.xml"),
    BOLT("drawable/icons/bolt.xml"),
    GLASS_FULL("drawable/icons/glass_full.xml"),
    WATER_DROP("drawable/icons/water_drop.xml"),
    MOON("drawable/icons/moon.xml"),
    CHEVRON_LEFT("drawable/icons/chevron_left.xml"),
    CHEVRON_RIGHT("drawable/icons/chevron_right.xml");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter {
        return painterResource(this.path)
    }

    @Composable
    fun icon(
        contentDescription: String = name,
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current
    ) {
        Icon(
            painter = painter(),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }

    @Composable
    fun iconButton(
        contentDescription: String = name,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        IconButton(onClick = onClick, content = { icon(contentDescription) }, modifier = modifier)
    }

    companion object {
        fun forGender(gender: Gender): AppIcon {
            return when (gender) {
                Gender.MALE -> MALE;
                Gender.FEMALE -> FEMALE;
            }
        }
    }
}
