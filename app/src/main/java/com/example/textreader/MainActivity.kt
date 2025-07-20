package com.example.textreader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        var fileContent by remember { mutableStateOf("File text will appear here.") }

        val filePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val reader = BufferedReader(InputStreamReader(inputStream))
                fileContent = reader.readText()
                reader.close()
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Button(onClick = {
                filePickerLauncher.launch("text/plain")
            }) {
                Text(text = "Pick a Text File")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = fileContent)
        }
    }
}
