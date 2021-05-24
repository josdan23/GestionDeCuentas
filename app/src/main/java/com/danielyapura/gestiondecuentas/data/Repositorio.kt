package com.danielyapura.gestiondecuentas.data

import android.content.Context
import androidx.room.Room
import com.danielyapura.gestiondecuentas.model.Movimiento
import com.danielyapura.gestiondecuentas.model.TipoMovimiento
import java.util.*

class Repositorio {

    private var db: MovimientosDB? = null

    private constructor(contexto: Context){
        db = Room.databaseBuilder(contexto, MovimientosDB::class.java, "MovimientosDb")
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        private var INSTANCIA: Repositorio? = null

        fun getInstance(context: Context): Repositorio? {

            if (INSTANCIA == null){
                INSTANCIA =
                    Repositorio(context)
            }
            return INSTANCIA
        }
    }

    fun guardar(movimiento: Movimiento){
        db?.getMovimientoDao()?.insert(movimiento)
    }

    fun getMovimientos(): List<Movimiento>? {
        return db?.getMovimientoDao()?.getMovimientos()
    }

    fun getSaldo(): Float {
        var saldo = 0f
        db?.getMovimientoDao()?.getMovimientos()?.forEach {
            when(TipoMovimiento.valueOf(it.tipoMovimiento)){
                TipoMovimiento.DEBE -> saldo -= it.cantidad
                TipoMovimiento.HABER -> saldo += it.cantidad
            }
            println(it)
        }
        return saldo
    }

    fun getTotalDebe(): Float {
        var total = 0f
        db?.getMovimientoDao()?.getMovimientos()?.forEach {
            when(TipoMovimiento.valueOf(it.tipoMovimiento)){
                TipoMovimiento.DEBE -> total += it.cantidad
            }
            println(it)
        }
        return total
    }

    fun getTotalHaber(): Float {
        var total = 0f
        db?.getMovimientoDao()?.getMovimientos()?.forEach {
            when(TipoMovimiento.valueOf(it.tipoMovimiento)){
                TipoMovimiento.HABER -> total += it.cantidad
            }
            println(it)
        }
        return total
    }


    fun getMovimientosPorFecha(calendar: Calendar): List<Movimiento>? {

        println("FECHA CALENDARIO: ${calendar.time}")
        return db?.getMovimientoDao()?.getMovimientosByDate(calendar.time)
    }

    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}