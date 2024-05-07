package com.example.knowcontributionsap2.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.knowcontributionsap2.data.local.entities.ContributionsEntity
import com.example.knowcontributionsap2.data.local.database.ContributionDb
import  com.example.knowcontributionsap2.data.local.dao.ContributionDao
import kotlinx.coroutines.GlobalScope

import com.example.knowcontributionsap2.MainActivity



@Composable
fun ContributionList(
    contributions: List<ContributionsEntity>,
    onContributionClick: (ContributionsEntity) -> Unit,
    onContributionDelete: (ContributionsEntity) -> Unit
){
    val context = LocalContext.current
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
                    Text(text = contributions.contributionId.toString() + ".", modifier = Modifier.weight(0.100f))
                    Text(text = contributions.nombre, modifier = Modifier.weight(0.25f))
                    Text(text = "£ " + contributions.monto.toString(), modifier = Modifier.weight(0.25f))
                    Text(text = contributions.descripcion, modifier = Modifier.weight(0.25f))

                    IconButton(onClick = {
                        onContributionDelete(contributions)
                        Toast.makeText(context, "Contribución eliminada", Toast.LENGTH_SHORT).show()
                    },
                        modifier = Modifier.height(23.dp),


                        ) {
                        Icon(
                            Icons.TwoTone.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFC95050)
                        )
                    }

                }
            }
        }
    }
}


