package com.danielyapura.gestiondecuentas.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    private val FORMAT = "yyyy-MM-dd HH:mm:ss.sss"

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return SimpleDateFormat(FORMAT).parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return SimpleDateFormat(FORMAT).format(date)
    }
}