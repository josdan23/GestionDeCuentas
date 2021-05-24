package com.danielyapura.gestiondecuentas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danielyapura.gestiondecuentas.data.Converters
import com.danielyapura.gestiondecuentas.data.MovimientoDao
import com.danielyapura.gestiondecuentas.model.Movimiento

@Database(entities = arrayOf(Movimiento::class), version = 1)
@TypeConverters(Converters::class)
abstract class MovimientosDB() : RoomDatabase() {

    abstract fun getMovimientoDao(): MovimientoDao
}