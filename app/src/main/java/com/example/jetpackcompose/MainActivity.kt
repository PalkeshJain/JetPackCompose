package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var name by remember { mutableStateOf("") }
            var names by remember { mutableStateOf(listOf<String>()) }
            var warningMessage by remember { mutableStateOf("") }
            var isEditing by remember { mutableStateOf(false) }
            var selectedTaskIndex by remember { mutableIntStateOf(-1) }

            JetpackComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Enter Task Name ",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                warningMessage = ""
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (warningMessage.isNotEmpty()) {
                            Text(
                                text = warningMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            if (name.isBlank()) {
                                warningMessage = "Please enter task name."
                            } else {
                                if (isEditing && selectedTaskIndex >= 0) {
                                    // Update the existing task
                                    names = names.toMutableList().apply {
                                        this[selectedTaskIndex] = name
                                    }
                                    isEditing = false
                                } else {
                                    // Add a new task
                                    names = names + name
                                }
                                name = ""
                                warningMessage = ""
                                selectedTaskIndex = -1
                            }
                        }) {
                            Text(text = if (isEditing) "Update" else "Add")
                        }
                    }
                    LazyColumn {
                        items(names) { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = task,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        selectedTaskIndex = names.indexOf(task)
                                        name = task
                                        isEditing = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Blue
                                    )
                                ) {
                                    Text(text = "Edit", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        names = names - task
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red
                                    )
                                ) {
                                    Text(text = "Delete", color = Color.White)
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
