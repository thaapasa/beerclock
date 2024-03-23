package fi.tuska.beerclock.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Country
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FlagImage(
    country: Country,
    contentDescription: String = country.name,
    modifier: Modifier = Modifier.width(20.dp),
) {
    Image(
        painter = painterResource(country.drawable),
        contentDescription = contentDescription,
        modifier = modifier
    )
}
