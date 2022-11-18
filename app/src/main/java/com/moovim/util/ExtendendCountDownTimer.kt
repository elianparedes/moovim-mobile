package com.moovim.util

import android.os.CountDownTimer

/**
 * Implementation based on
 * https://github.com/huynguyennovem/Android-CountDownTimer-Ext
 */
abstract class ExtendedCountDownTimer(var millisInFuture: Long, var countDownInterval: Long) {

    private lateinit var countDownTimer: CountDownTimer
    private var remainingMillis: Long = 0
    private var isPaused: Boolean = true

    init {
        this.remainingMillis = millisInFuture
    }

    @Synchronized
    fun start() {
        if (isPaused){
            countDownTimer = object : CountDownTimer(remainingMillis, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingMillis = millisUntilFinished
                    onTimerTick(millisUntilFinished)
                }

                override fun onFinish() {
                    onTimerFinish()
                    restart()
                }

            }.apply {
                start()
            }

            isPaused = false
        }
    }

    fun pause() {
        if (!isPaused) {
            countDownTimer.cancel()
        }
        isPaused = true
    }

    fun restart() {
        if (!isPaused){
            countDownTimer.cancel()
            remainingMillis = millisInFuture
            isPaused = true
        }

    }

    abstract fun onTimerTick(millisUntilFinished: Long)
    abstract fun onTimerFinish()

}
