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

enum class AppIcon(private val path: String) : Image {
    ADD_CIRCLE("drawable/icons/add_circle.xml"),
    BEER("drawable/icons/sports_bar.xml"),
    BOLT("drawable/icons/bolt.xml"),
    CALENDAR("drawable/icons/calendar.xml"),
    CAR("drawable/icons/car.xml"),
    CHEVRON_LEFT("drawable/icons/chevron_left.xml"),
    CHEVRON_RIGHT("drawable/icons/chevron_right.xml"),
    CLOCK("drawable/icons/clock.xml"),
    CLOSE("drawable/icons/close.xml"),
    DELETE("drawable/icons/delete.xml"),
    DRINK("drawable/icons/local_bar.xml"),
    EDIT("drawable/icons/edit.xml"),
    FEMALE("drawable/icons/female.xml"),
    GLASS_FULL("drawable/icons/glass_full.xml"),
    GRAPH("drawable/icons/graph.xml"),
    HISTORY("drawable/icons/history.xml"),
    INPUT("drawable/icons/input.xml"),
    LANGUAGE("drawable/icons/language.xml"),
    MALE("drawable/icons/male.xml"),
    MOON("drawable/icons/moon.xml"),
    PERSON("drawable/icons/person.xml"),
    SAVE_AS("drawable/icons/save_as.xml"),
    SEARCH("drawable/icons/search.xml"),
    WATER_DROP("drawable/icons/water_drop.xml");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter {
        return painterResource(this.path)
    }

    @Composable
    fun icon(
        contentDescription: String = name,
        modifier: Modifier = Modifier,
        tint: Color = LocalContentColor.current,
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
        onClick: () -> Unit,
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
