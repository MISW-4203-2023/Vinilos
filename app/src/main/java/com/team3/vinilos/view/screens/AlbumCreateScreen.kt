package com.team3.vinilos.view.screens
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team3.vinilos.R
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.viewModel.AlbumAddUiState


@Composable
fun AlbumCreateScreen(state: AlbumAddUiState, retryAction: () -> Unit, goToAdd: () -> Unit) {

    when (state) {
        is AlbumAddUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is AlbumAddUiState.Success -> AlbumCreate(goToAdd= { })
        is AlbumAddUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun AlbumCreate(goToAdd : () ->  Unit, modifier: Modifier = Modifier) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var cover by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var etiqueta by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val genero = arrayOf("Classical", "Salsa", "Rock", "Folk")
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex  by remember { mutableStateOf(0) }


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
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cover,
            onValueChange = { cover = it },
            label = { Text("Cover") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = etiqueta,
            onValueChange = { etiqueta = it },
            label = { Text("Etiqueta") },
            modifier = Modifier.fillMaxWidth()
        )

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                val album = Album(
                    0,
                    name = name.text,
                    cover = cover.text,
                    description = description.text,
                    recordLabel = etiqueta.text,
                    genre = genero[selectedItemIndex]
                )
                Toast.makeText(context,"Album: "+ album,Toast.LENGTH_SHORT)

            }) {
                Text("Guardar")
            }
            OutlinedButton(onClick = { /* Acciones al hacer clic en el botón Cancelar */ }) {
                Text("Cancelar")
            }
        }
    }
}

@Preview
@Composable
private fun AlbumCreatePreview() {
    AlbumCreate(goToAdd= { })
}