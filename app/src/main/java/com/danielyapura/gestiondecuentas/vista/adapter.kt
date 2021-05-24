package com.danielyapura.gestiondecuentas.vista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.danielyapura.gestiondecuentas.R
import com.danielyapura.gestiondecuentas.model.Movimiento
import com.danielyapura.gestiondecuentas.model.TipoMovimiento
import com.danielyapura.gestiondecuentas.utils.dateToString
import com.danielyapura.gestiondecuentas.utils.getSimpleFecha

class Adapter(private val context: Context) :
        RecyclerView.Adapter<Adapter.MiViewHolder>() {

    class MiViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tipoMovimientoTv = itemView.findViewById<TextView>(R.id.tipoMovimientoItemTv)
        val fechaItemTv: TextView = itemView.findViewById(R.id.fechaItemTv)
        val descripcionItemTv: TextView = itemView.findViewById(R.id.descripcionItemTv)
        val montoItemTv: TextView = itemView.findViewById(R.id.montoItemTv)
        val iconTipoItemTv: ImageView = itemView.findViewById(R.id.iconTipoItemIv)
    }

    private lateinit var datos: List<Movimiento>

    fun setDatos(datos: List<Movimiento>){
        this.datos = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return MiViewHolder(view)
    }

    override fun getItemCount(): Int = datos.size

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {

        holder.fechaItemTv.text = dateToString(datos[position].fecha)
        holder.descripcionItemTv.text = datos[position].descripcion
        holder.montoItemTv.text = String.format("$ %.2f", datos[position].cantidad)
        holder.tipoMovimientoTv.text = datos[position].tipoMovimiento


        when(TipoMovimiento.valueOf(datos[position].tipoMovimiento)){
            TipoMovimiento.DEBE -> {
                holder.tipoMovimientoTv.setTextColor(ContextCompat.getColor(
                    context,
                    R.color.magenta
                ))
                holder.iconTipoItemTv.setImageDrawable(ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_arriba
                ))
            }
            TipoMovimiento.HABER -> {
                holder.tipoMovimientoTv.setTextColor(ContextCompat.getColor(context,
                    R.color.rojo
                ))
                holder.iconTipoItemTv.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_abajo
                ))
            }


        }
    }
}