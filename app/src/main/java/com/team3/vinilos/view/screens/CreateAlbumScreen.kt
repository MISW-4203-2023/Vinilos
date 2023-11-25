package com.team3.vinilos.view.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.viewModel.AlbumAddUiState
import com.team3.vinilos.viewModel.AlbumAddViewModel
import java.text.SimpleDateFormat
import java.util.Date
import com.team3.vinilos.view.theme.md_theme_light_error


@Composable
fun AlbumCreateScreen(state: AlbumAddViewModel, navigateUp: () -> Unit) {
    AlbumCreate(state = state, navigateUp = navigateUp)
}

@Composable
fun AlbumCreate(state: AlbumAddViewModel, navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    var createAlbum by remember { mutableStateOf(CreateAlbum(name = "")) }

    val context = LocalContext.current
    val genero = arrayOf("Classical", "Salsa", "Rock", "Folk")
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }
    var isNameValid by remember { mutableStateOf(true) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Crear Album",
            style = MaterialTheme.typography.headlineMedium
        )


        OutlinedTextField(
            value = createAlbum.name,
            onValueChange = {
                createAlbum = createAlbum.copy(name = it)
                isNameValid = it.isNotBlank()
            },
            label = { Text("Nombre") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = !isNameValid,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )


        OutlinedTextField(
            value = createAlbum.cover ?: "",
            onValueChange = {
                createAlbum = createAlbum.copy(cover = it)
            },
            label = { Text("Portada") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = createAlbum.recordLabel ?: "",
            onValueChange = {
                createAlbum = createAlbum.copy(recordLabel = it)
            },
            label = { Text("Disquera") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = createAlbum.description ?: "",
            onValueChange = {
                createAlbum = createAlbum.copy(description = it)
            },
            label = { Text("Descripción") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
        )
        CustomDatepicker(onValueChange = {createAlbum = createAlbum.copy(releaseDate = it)})

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
        ) {
            TextField(
                value = genero[selectedItemIndex],
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genero.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                            )
                        },
                        onClick = {
                            selectedItemIndex = index
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = navigateUp ) {
                Text("Cancelar")
            }
            Button(onClick = {
                if (isNameValid) {
                    state.addAlbum(album = createAlbum)
                    Toast.makeText(context, "Album " + createAlbum.name + " creado exitosamente!", Toast.LENGTH_SHORT).show()
                    navigateUp()
                } else {
                    Toast.makeText(context, "El nombre es obligatorio", Toast.LENGTH_LONG).show()
                }

            }) {
                Text("Guardar")
            }
        }
    }
}

@Preview
@Composable
private fun AlbumCreatePreview() {
    CustomDatepicker(onValueChange = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatepicker(onValueChange: (Date) -> Unit) {

    // set the initial date
    val datePickerState = rememberDatePickerState()

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate: Long? by remember {
        mutableStateOf(null)
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onValueChange(Date(datePickerState.selectedDateMillis!!))
                    selectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(text = "Seleccione la fecha")
                },

                )
        }
    }
    val formatter = SimpleDateFormat("MMMM dd yyyy")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .height(56.dp)
    ) {


        Text(
            text = selectedDate?.let { formatter.format(Date(it)) } ?: "Seleccione",
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 18.dp)
        )
        IconButton(
            onClick = { showDatePicker = true },
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(Icons.Filled.DateRange, contentDescription = "Calendario")
        }
    }


}