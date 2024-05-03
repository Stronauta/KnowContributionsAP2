package com.example.knowcontributionsap2

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.knowcontributionsap2.ui.theme.KnowContributionsAP2Theme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KnowContributionsAP2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun InputNombre(){
    OutlinedTextField(
        label = { Text("Nombre") },
        value = "1",
        onValueChange = {  },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun InputMonto(){
    OutlinedTextField(
        label = { Text("Monto") },
        value = "1",
        onValueChange = {  },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun InputDescription(){
    OutlinedTextField(
        label = { Text("Descripción") },
        value = "1",
        onValueChange = {  },
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


@Preview(showBackground = true, name = "Ver inputs")
@Composable
fun InputsPreview() {
    KnowContributionsAP2Theme {
        Column {
            InputNombre()
            InputMonto()
            InputDescription()
            InputFecha(Calendar.getInstance()) {  }
        }
    }
}