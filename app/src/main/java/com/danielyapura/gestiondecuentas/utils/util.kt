package com.danielyapura.gestiondecuentas.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getSimpleFecha(): String {
    return "${this.get(Calendar.DAY_OF_MONTH)}/${this.get(Calendar.MONTH)}/${this.get(Calendar.YEAR)}"
}

fun mensaje(mensaje: String, context: Context) {
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}

fun dateToString(date: Date): String {
    return SimpleDateFormat("dd/MM/yyyy").format(date)
}