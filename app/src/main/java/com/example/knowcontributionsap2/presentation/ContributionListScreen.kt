package com.example.knowcontributionsap2.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.knowcontributionsap2.data.local.entities.ContributionsEntity



@Composable
fun ContributionList(
    contributions: List<ContributionsEntity>,
    onContributionClick: (ContributionsEntity) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(contributions) { contributions ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onContributionClick(contributions) }
                        .padding(4.dp)
                ) {
                    Text(text = contributions.contributionId.toString(), modifier = Modifier.weight(0.10f))
                    Text(text = contributions.nombre, modifier = Modifier.weight(0.400f))
                    Text(text = contributions.monto.toString(), modifier = Modifier.weight(0.40f))
                    Text(text = contributions.descripcion, modifier = Modifier.weight(0.10f))
                }
            }
        }
    }
}