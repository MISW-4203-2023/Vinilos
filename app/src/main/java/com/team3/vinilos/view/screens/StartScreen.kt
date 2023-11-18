package com.team3.vinilos.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R

@Composable
fun StartScreen(
    onCollectorEntry: () -> Unit,
    onGuessEntry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding( vertical = 24.dp )
            .verticalScroll(state)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.login_title),
            fontSize = 32.sp,
            modifier = modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.as_text),
            //fontSize = 24.sp,
            modifier = modifier.padding(bottom = 48.dp)
        )
        Button(
            onClick = { onCollectorEntry() },
            modifier = modifier
                .width(240.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                stringResource(id = R.string.collectors_title),
                fontSize = 20.sp
            )
        }
        OutlinedButton(
            onClick = { onGuessEntry() },
            modifier = modifier.width(240.dp)
        ) {
            Text(
                stringResource(R.string.guess_title),
                fontSize = 20.sp
            )
        }
    }
}