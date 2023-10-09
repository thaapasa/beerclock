package fi.tuska.beerclock.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuska.beerclock.common.localization.strings
import fi.tuska.beerclock.common.settings.Gender

@Composable
fun GenderSelector(selectedValue: Gender, onSelectGender: (gender: Gender) -> Unit) {
    val options = Gender.values().toList()
    var expanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.size(250.dp, 32.dp).clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(4.dp))
            .clickable { expanded = !expanded },
    ) {
        Text(
            text = strings.forGender(selectedValue),
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Icon(
            Icons.Filled.ArrowDropDown, "contentDescription", Modifier.align(Alignment.CenterEnd)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onSelectGender(selectionOption)
                    expanded = false
                }) {
                    Text(text = strings.forGender(selectionOption))
                }
            }
        }
    }
}