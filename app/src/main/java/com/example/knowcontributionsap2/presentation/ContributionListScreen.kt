package com.example.knowcontributionsap2.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

    var showDialog by remember { mutableStateOf(false) }
    var contributionDelete by remember { mutableStateOf<ContributionsEntity?>(null) }

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
                    Text(text = "RD " + contributions.monto.toString(), modifier = Modifier.weight(0.25f))
                    Text(text = contributions.descripcion, modifier = Modifier.weight(0.25f))


                    IconButton(onClick = {
                        showDialog = true
                        contributionDelete = contributions
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

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning",
                            tint = Color(0xFFDAA504)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Eliminar Contribución",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                text = {
                    Text(
                        "¿Estás seguro de eliminar esta contribución?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onContributionDelete(contributionDelete!!)
                            showDialog = false
                            Toast.makeText(context, "Contribución eliminada", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Red
                        )
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}



