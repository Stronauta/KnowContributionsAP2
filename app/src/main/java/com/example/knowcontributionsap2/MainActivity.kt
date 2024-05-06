package com.example.knowcontributionsap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.knowcontributionsap2.ui.theme.KnowContributionsAP2Theme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room


import com.example.knowcontributionsap2.data.local.entities.ContributionsEntity
import com.example.knowcontributionsap2.data.local.database.ContributionDb
import com.example.knowcontributionsap2.presentation.ContributionList

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.DelicateCoroutinesApi

import java.util.Date


@Suppress("NAME_SHADOWING")
class MainActivity : ComponentActivity() {
    private lateinit var contributionDb: ContributionDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contributionDb = Room.databaseBuilder(
            this,
            ContributionDb::class.java,
            "Contribution.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()
        setContent {
            KnowContributionsAP2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Contribuciones",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val contribution: List<ContributionsEntity> by getContributions().collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        )
                        var contributionsIds by remember { mutableStateOf("") }
                        var nombre by remember { mutableStateOf("") }
                        var monto by remember { mutableDoubleStateOf(0.0) }
                        var descripcion by remember { mutableStateOf("") }

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {

                                OutlinedTextField(
                                    maxLines = 1,
                                    label = { Text("Nombre") },
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                OutlinedTextField(
                                    maxLines = 1,
                                    label = { Text(text = "Monto") },
                                    value = monto.toString(),
                                    onValueChange = { n ->
                                        if (n.length <= 6) {
                                            monto = n.toDoubleOrNull() ?: 0.0
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    label = { Text("Descripción") },
                                    value = descripcion,
                                    onValueChange = { descripcion = it},
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(15.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            nombre = ""
                                            monto = 0.0
                                            descripcion = ""
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "new button"
                                        )
                                        Text(text = "Nuevo")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            saveContribution(
                                                ContributionsEntity(
                                                    contributionId = contributionsIds.toIntOrNull(),
                                                    nombre = nombre,
                                                    monto = monto,
                                                    descripcion = descripcion,
                                                    fecha = Date().toString()
                                                )
                                            )
                                            contributionsIds = ""
                                            nombre = ""
                                            monto = 0.0
                                            descripcion = ""

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "save button"
                                        )
                                        Text(text = "Guardar")
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        if (contribution.isNotEmpty()) {
                            ContributionList(
                                contributions = contribution,
                                onContributionClick = { contribution ->
                                    contributionsIds = contribution.contributionId.toString()
                                    nombre = contribution.nombre
                                    monto = contribution.monto
                                    descripcion = contribution.descripcion

                                }
                                ,
                                onContributionDelete = { contribution ->
                                    deleteContribution(contribution)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveContribution(contribution: ContributionsEntity) {
        GlobalScope.launch {
            contributionDb.contributionDao().save(contribution)
        }
    }

    private fun getContributions(): Flow<List<ContributionsEntity>> {
        return contributionDb.contributionDao().getAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteContribution(contribution: ContributionsEntity) {
        GlobalScope.launch {
            contributionDb.contributionDao().delete(contribution)
        }
    }

}




/*

@Composable
fun InputMonto(){
    var  monto by remember { mutableStateOf("") }

    OutlinedTextField(
        maxLines = 1,
        label = { Text("Monto") },
        value = monto,
        onValueChange = { n ->
            if (n.length <= 6) {
                monto = n
            }
        },
        modifier = Modifier.padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun InputDescription(){
    var descripcion by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text("Descripción") },
        value = descripcion,
        onValueChange = { descripcion = it},
        modifier = Modifier.padding(16.dp)
    )

}


@Composable
fun InputFecha(
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    var fechaSeleccionada by remember { mutableStateOf(selectedDate) }
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                fechaSeleccionada = calendar
                onDateSelected(calendar)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    Button(
        onClick = {
            datePickerDialog.show()
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Seleccionar Fecha")
    }

    Text(
        text = "Fecha seleccionada: ${fechaSeleccionada.formattedDate()}",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun Calendar.formattedDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(time)
}

*/




