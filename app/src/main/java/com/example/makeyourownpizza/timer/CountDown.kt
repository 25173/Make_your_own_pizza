package com.example.makeyourownpizza.timer

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.example.makeyourownpizza.R
import com.example.makeyourownpizza.utility.SharedPreference
import java.util.*
import java.util.concurrent.TimeUnit


class CountDown(view: View, context: Context) {

    private val sharePref = SharedPreference(context)
    val timer: TextView = view.findViewById(R.id.timer)
    private var mCountDownTimer: CountDownTimer? = null
    var mTimerRunning = sharePref.getValueBool("Running")
    var mTimeLeftInMillis = sharePref.getValueLong("timeRemainLeft")


    fun StartTimer(){
        mCountDownTimer = object: CountDownTimer(mTimeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                val timeRemaining = timeString(mTimeLeftInMillis)
//                show the timer
                timer.text = timeRemaining
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timer.text = "Done"
                resetTimer()

            }
        }.start()
         mTimerRunning = true
    }


    fun onPause(){
        mCountDownTimer!!.cancel()
        mTimerRunning = false
    }

    private fun resetTimer(){
        mTimeLeftInMillis = RESET_TIME_IN_MILLIS
        StartTimer()
    }

    // Method to get days hours minutes seconds from milliseconds
    private fun timeString(millisUntilFinished:Long):String{
        var millisUntilFinishe:Long = millisUntilFinished

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinishe)
        millisUntilFinishe -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinishe)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d: %02d" ,
            minutes,seconds
        )
    }

    companion object {
        private const val RESET_TIME_IN_MILLIS:Long = 1000 * 60 * 15
    }
}