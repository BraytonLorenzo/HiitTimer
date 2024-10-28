package com.example.hiittimer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiittimer.ui.theme.HiitTimerTheme
import java.util.Scanner
import kotlin.text.toIntOrNull

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiitTimerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabataCounter(
                        context = this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TabataCounter(context: Context, modifier: Modifier = Modifier) {
    var timeLeft by remember { mutableStateOf(0L) }
    var isWorkTime by remember { mutableStateOf(true) }
    var isRunning by remember { mutableStateOf(false) }
    var sets by remember { mutableStateOf("") }
    var setsInt by remember { mutableStateOf(0) }
    var muchWorkTime by remember { mutableStateOf("") }
    var muchWorkTimeInt by remember { mutableStateOf(0) }
    var muchRestTime by remember { mutableStateOf("") }
    var muchRestTimeInt by remember { mutableStateOf(0) }

    val counterDown = remember {
        CounterDown(
            context = context,
            workTime = muchWorkTimeInt,
            restTime = muchRestTimeInt,
            numSeries = setsInt,
            onTick = { time, work ->
                timeLeft = time
                isWorkTime = work
            },
            onFinish = {
                isRunning = false
            }
        )
    }

    Box(modifier = Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isRunning) {
                if (isWorkTime) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Trabajo", fontSize = 30.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("$timeLeft", fontSize = 30.sp)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Descanso", fontSize = 30.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("$timeLeft", fontSize = 30.sp)
                    }
                }
            } else {
                Row(modifier = Modifier.padding(30.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = "sets",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        Row {
                            TextField(
                                value = sets,
                                onValueChange = { newValue ->
                                    sets = newValue
                                    setsInt = newValue.toIntOrNull() ?: 0
                                },
                                label = { Text("Sets") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }
                Row(modifier = Modifier.padding(30.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = "Trabaja",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        Row {
                            TextField(
                                value = muchWorkTime,
                                onValueChange = { newValue ->
                                    muchWorkTime = newValue
                                    muchWorkTimeInt = newValue.toIntOrNull() ?: 0
                                },
                                label = { Text("tiempodetrabajo") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }
                Row(modifier = Modifier.padding(30.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row {
                            Text(
                                text = "Descanso",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        Row {
                            TextField(
                                value = muchRestTime,
                                onValueChange = { newValue ->
                                    muchRestTime = newValue
                                    muchRestTimeInt = newValue.toIntOrNull() ?: 0
                                },
                                label = { Text("tiempodedescanso") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }
            }


            Row(modifier = Modifier.padding(30.dp)) {
                Button(onClick = {
                    if (isRunning) {
                        counterDown.pause()
                        isRunning = false
                    } else {
                        counterDown.numSeries = setsInt
                        counterDown.workTime = muchWorkTimeInt
                        counterDown.restTime = muchRestTimeInt
                        counterDown.start()
                        isRunning = true
                    }
                }) {
                    Text(
                        text = if (isRunning) "Stop" else "Inicio",
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
}
