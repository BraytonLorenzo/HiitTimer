package com.example.hiittimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hiittimer.ui.theme.HiitTimerTheme
import java.util.Scanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiitTimerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabataCounter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TabataCounter(modifier: Modifier = Modifier) {
    var timeLeft by remember { mutableStateOf(0L) }
    var isWorkTime by remember { mutableStateOf(true) }
    var isRunning by remember { mutableStateOf(false) }

    val counterDown = remember {
        CounterDown(
            workTime = 20, // Tiempo de trabajo en segundos
            restTime = 10, // Tiempo de descanso en segundos
            numSeries = 8, // Número de series
            onTick = { time, work ->
                timeLeft = time
                isWorkTime = work
            },
            onFinish = {
                isRunning = false
            }
        )
    }
    //Scanner sc = new Scanner(System.in);
    //int i = sc.nextInt();

    Box(modifier = Modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row {
                        Text(text = "sets")
                    }
                    Row {
                        Text(text = "6")
                    }
                }
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row {
                        Text(text = "Trabaja")
                    }
                    Row {
                        Text(
                            text = if (isRunning) {
                                if (isWorkTime) "Trabajo: $timeLeft" else "Descanso: $timeLeft"
                            } else {
                                "Presiona para iniciar"
                            }
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Column {
                    Row {
                        Text(text = "Descanso")
                    }
                    Row {
                        Text(
                            text = if (isRunning) {
                                if (isWorkTime) "Trabajo: $timeLeft" else "Descanso: $timeLeft"
                            } else {
                                "Presiona para iniciar"
                            }
                        )
                    }
                }
            }
            Row(modifier = Modifier.padding(16.dp)) {
                Button(onClick = {
                    if (isRunning) {
                        counterDown.pause()
                        isRunning = false
                    } else {
                        counterDown.start()
                        isRunning = true
                    }
                }) {
                    Text(text = if (isRunning) "Pausar" else "Iniciar")
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HiitTimerTheme {
        TabataCounter()
    }
}