package com.team3.vinilos.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.team3.vinilos.R
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.viewModel.AlbumAddUiState
import java.text.SimpleDateFormat
import java.util.Date
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale


@Composable
fun AlbumCreateScreen(
    state: StateFlow<AlbumAddUiState>,
    addAlbum: (success: () -> Unit) -> Unit,
    updateField: (createAlbum: CreateAlbum) -> Unit,
    navigateUp: () -> Unit,
    onSuccess: () -> Unit
) {

    val albumAddUiState by state.collectAsState()
    AlbumCreate(
        state = albumAddUiState,
        addAlbum = addAlbum,
        updateField = updateField,
        navigateUp = navigateUp,
        onSuccess = onSuccess
    )
}

@Composable
fun AlbumCreate(
    state: AlbumAddUiState,
    addAlbum: (() -> Unit) -> Unit,
    updateField: (createAlbum: CreateAlbum) -> Unit,
    navigateUp: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val genres = stringArrayResource(id = R.array.genres_array)
    val recordLabels = stringArrayResource(id = R.array.record_labels_array)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Crear Album",
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.createAlbum.name ?: "",
            onValueChange = {
                updateField(state.createAlbum.copy(name = it))
            },
            singleLine = true,
            label = { Text("Nombre") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = state.createAlbum.name.isNullOrBlank() && state.hasSent,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )

        if (state.createAlbum.name.isNullOrBlank() && state.hasSent) {
            ErrorMessage("El nombre es obligatorio.")
        }

        OutlinedTextField(
            value = state.createAlbum.cover ?: "",
            onValueChange = {
                updateField(state.createAlbum.copy(cover = it))
            },
            singleLine = true, label = { Text("Portada") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = (state.createAlbum.cover.isNullOrBlank()
                    || !state.createAlbum.isValidUrl(
                state.createAlbum.cover
            )) && state.hasSent,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
        )

        if (state.createAlbum.cover.isNullOrBlank() && state.hasSent) {
            ErrorMessage("La portada es obligatoria.")
        }
        if (!state.createAlbum.isValidUrl(state.createAlbum.cover ?: "") && state.hasSent) {
            ErrorMessage("La portada debe ser una url válida.")
        }

        // Record Label Selection
        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            Text(
                "Sello discográfico:",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            recordLabels.forEach { recordLabel ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = recordLabel == state.createAlbum.recordLabel,
                            onClick = { updateField(state.createAlbum.copy(recordLabel = recordLabel)) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = recordLabel == state.createAlbum.recordLabel,
                        onClick = null
                    )
                    Text(
                        recordLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        if (state.createAlbum.recordLabel.isNullOrBlank() && state.hasSent) {
            ErrorMessage("Seleccione el sello discográfico")
        }

        OutlinedTextField(
            value = state.createAlbum.description ?: "",
            onValueChange = {
                updateField(state.createAlbum.copy(description = it))
            },
            label = { Text("Descripción") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = state.createAlbum.description.isNullOrBlank() && state.hasSent,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
        )

        if (state.createAlbum.description.isNullOrBlank() && state.hasSent) {
            ErrorMessage("La descripción es obligatoria")
        }

        CustomDatePicker(state.hasSent, onValueChange = {
            updateField(state.createAlbum.copy(releaseDate = it))
        })

        if (state.createAlbum.releaseDate == null && state.hasSent) {
            ErrorMessage("Seleccione la fecha de lanzamiento")
        }
        // Genre Selection
        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            Text(
                "Género Musical:",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            genres.forEach { genre ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = genre == state.createAlbum.genre,
                            onClick = { updateField(state.createAlbum.copy(genre = genre)) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = genre == state.createAlbum.genre,
                        onClick = null
                    )
                    Text(
                        genre,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        if (state.createAlbum.genre.isNullOrBlank() && state.hasSent) {
            ErrorMessage("Seleccione un género")
        }

        if (!state.loading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = navigateUp) {
                    Text("Cancelar")
                }
                Button(onClick = {
                    addAlbum {
                        onSuccess()
                    }
                }) {
                    Text("Guardar")
                }
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Guardando...", modifier = modifier.padding(end = 16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.width(48.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        if (!state.errorMessage.isNullOrBlank()) {
            ErrorMessage(state.errorMessage)
        }
    }
}

@Preview
@Composable
private fun AlbumCreatePreview() {
    CustomDatePicker(false, onValueChange = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(hasSent: Boolean, onValueChange: (Date) -> Unit) {

    val datePickerState = rememberDatePickerState()
    val formatter = SimpleDateFormat("MMM dd yyyy", Locale("es", "co"))
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate: Long? by remember {
        mutableStateOf(null)
    }
    val offsetHours = (1000L * 60 * 60 * 5)
    if (showDatePicker) {

        val scrollState = rememberScrollState()
        DatePickerDialog(
            modifier = Modifier.verticalScroll(scrollState),
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onValueChange(Date(datePickerState.selectedDateMillis!! + offsetHours))
                    selectedDate = datePickerState.selectedDateMillis!! + offsetHours

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
                    Text(
                        text = "Seleccione la fecha de lanzamiento",
                        modifier = Modifier.padding(top = 12.dp, start = 24.dp)
                    )
                },
                headline = {
                    Text(
                        text = datePickerState.selectedDateMillis?.let { formatter.format(Date(it + offsetHours)) }
                            ?: "",
                        modifier = Modifier.padding(bottom = 12.dp, start = 24.dp)
                    )
                }
            )
        }

    }
    OutlinedTextField(
        value = selectedDate?.let { formatter.format(Date(it)) } ?: "",
        onValueChange = {},
        label = { Text("Fecha de lanzamiento") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        readOnly = true,
        trailingIcon = {
            IconButton(
                onClick = { showDatePicker = true },
            ) {
                Icon(Icons.Filled.DateRange, contentDescription = "Calendario")
            }
        },
        isError = (selectedDate == null) && hasSent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )


}

@Composable
fun ErrorMessage(message: String) {
    Text(
        message,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}