package com.example.hiittimer

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer

class CounterDown(
    private val context: Context,
    var workTime: Int,
    var restTime: Int,
    var numSeries: Int,
    var onTick: (Long, Boolean) -> Unit,
    var onFinish: () -> Unit
) {
    private var isRunning = false
    private var currentSeries = 0
    private var isWorkTime = true
    private var timer: CountDownTimer? = null
    private val workSound: MediaPlayer = MediaPlayer.create(context, R.raw.trabajosonido)
    private val restSound: MediaPlayer = MediaPlayer.create(context, R.raw.descansosonido)
    private val finishSound: MediaPlayer = MediaPlayer.create(context, R.raw.finalsonido)

    fun start() {
        if (!isRunning) {
            isRunning = true
            currentSeries = 1
            isWorkTime = true
            startTimer(workTime)
        }
    }

    fun pause() {
        if (isRunning) {
            isRunning = false
            timer?.cancel()
        }
    }

    private fun startTimer(duration: Int) {
        timer = object : CountDownTimer(duration * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished / 1000, isWorkTime)
            }

            override fun onFinish() {
                if (isWorkTime) {
                    restSound.start()
                    isWorkTime = false
                    startTimer(restTime)
                } else {
                    currentSeries++
                    if (currentSeries <= numSeries) {
                        workSound.start()
                        isWorkTime = true
                        startTimer(workTime)
                    } else {
                        finishSound.start()
                        isRunning = false
                        onFinish()
                    }
                }
            }
        }.start()
    }
}