package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.Calendar


data class Task(
    var id: Int? = null,
    var taskName: String? = null,
    var description: String? = null,
    var date: Long? = null,
    var isDone: Boolean? = false,
) : Serializable
{
    fun formatTime():String
    {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date!!
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        return "${calendar.get(Calendar.DAY_OF_MONTH)} / ${calendar.get(Calendar.MONTH)+1} / ${calendar.get(Calendar.YEAR)}"
    }
}


