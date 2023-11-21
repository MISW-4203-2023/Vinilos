package com.team3.vinilos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.view.theme.md_theme_dark_onPrimary
import com.team3.vinilos.view.theme.md_theme_dark_primary
import com.team3.vinilos.viewModel.CollectorsUiState

@Composable
fun CollectorsScreen(state: CollectorsUiState, retryAction: () -> Unit) {

    when (state) {
        is CollectorsUiState.Loading -> LoadingScreen()
        is CollectorsUiState.Success -> CollectorsList(collectorList = state.collectors)
        is CollectorsUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun CollectorsList(collectorList: List<Collector>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.testTag("collector_list")) {
        items(collectorList) { collector ->
            CollectorCard(collector = collector)
            Divider()
        }
    }
}


@Composable
fun CollectorCard(collector: Collector, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(collector.name) },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = stringResource(
                    R.string.go_to_collector
                )
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(md_theme_dark_onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = collector.name.first().toString().uppercase(),
                    color = md_theme_dark_primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
}


@Preview
@Composable
private fun CollectorsPreview() {
    CollectorsList(Datasource().loadCollectors(10))
}