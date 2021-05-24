package com.danielyapura.gestiondecuentas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
data class Movimiento(
    val tipoMovimiento: String,
    val descripcion: String?,
    val cantidad: Float = 0.0f,
    val fecha: Date
) {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var id: Int = 0

    fun getTipoMovimiento(tipoMovimientoString: String): TipoMovimiento {
        return TipoMovimiento.valueOf(tipoMovimientoString)
    }
}