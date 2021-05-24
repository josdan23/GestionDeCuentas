package com.danielyapura.gestiondecuentas.data

import androidx.room.*
import com.danielyapura.gestiondecuentas.model.Movimiento
import java.util.*

@Dao
interface MovimientoDao {

    @Insert
    fun insert(movimiento: Movimiento)

    @Delete
    fun delete(movimiento: Movimiento)

    @Update
    fun update(movimiento: Movimiento)

    @Query("SELECT * FROM Movimiento ORDER BY fecha DESC")
    fun getMovimientos(): List<Movimiento>

    @Query("SELECT * FROM Movimiento WHERE date(fecha) = date(:fechaFiltro) ORDER BY fecha DESC")
    fun getMovimientosByDate(fechaFiltro: Date): List<Movimiento>

}