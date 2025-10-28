package com.amirmousavi_dev.persian_date_picker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.ui.PersianDatePickerDialog
import com.amirmousavi_dev.persian_date_picker.ui.theme.Persian_Date_PickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Persian_Date_PickerTheme {
                var isDatePickerVisible by rememberSaveable { mutableStateOf(false) }
                var selectedDate by rememberSaveable { mutableStateOf("") }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            isDatePickerVisible = true
                        },
                    ) {
                        Text(text = "Show Date Picker")

                    }

                    Text(text = selectedDate)


                    if (isDatePickerVisible)
                    PersianDatePickerDialog(
                        onDismissRequest = {
                            isDatePickerVisible = false
                        },
                        onConfirm = {
                            selectedDate = it.dateString
                            isDatePickerVisible = false
                        }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Persian_Date_PickerTheme {
        Greeting("Android")
    }
}