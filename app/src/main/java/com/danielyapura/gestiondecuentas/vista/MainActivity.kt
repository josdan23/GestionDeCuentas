package com.danielyapura.gestiondecuentas.vista

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielyapura.gestiondecuentas.InfoActivity
import com.danielyapura.gestiondecuentas.R
import com.danielyapura.gestiondecuentas.data.Repositorio
import com.danielyapura.gestiondecuentas.model.Movimiento
import com.danielyapura.gestiondecuentas.utils.getSimpleFecha
import com.danielyapura.gestiondecuentas.utils.mensaje
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.spinner_layout.view.*
import java.util.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    private lateinit var listaMovimientos: RecyclerView;
    private lateinit var adaptador: Adapter
    private lateinit var datos: List<Movimiento>
    private var isFiltroEnabled = false
    private var fechaFiltro = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar2)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val spinner = findViewById<Spinner>(R.id.action_bar_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.opciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        agregarMovimientoBt.setOnClickListener {
            val intent = Intent(this, NuevoMovimientoActivity::class.java)
            startActivity(intent)
        }

        configurarLista()

        fecha_chip.setOnClickListener{
            mostrarCalendario()
        }

        todos_chip.setOnClickListener{
            actualizarLista {
                Repositorio.getInstance(this)?.getMovimientos()!!
            }
        }
    }


    private fun configurarLista() {
        val viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adaptador = Adapter(this)

        listaMovimientos = findViewById<RecyclerView>(R.id.listaMovimientosRv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = adaptador
        }
    }

    private fun mostrarSaldo() {
        var saldo = Repositorio.getInstance(this)?.getSaldo()
        saldoTv.text = String.format("$ %.2f", saldo)
    }

    private fun mostrarTotalDebe(){
        var total = Repositorio.getInstance(this)?.getTotalDebe()
        total_debe_tv.text = String.format("$ %.2f", total)
    }

    private fun mostrarTotalHaber(){
        var total = Repositorio.getInstance(this)?.getTotalHaber()
        total_haber_tv.text = String.format("$ %.2f", total)
    }


    override fun onResume() {
        super.onResume()
        actualizarLista {
            Repositorio.getInstance(this)?.getMovimientos()!!
        }
        mostrarSaldo()
        mostrarTotalDebe()
        mostrarTotalHaber()
    }

    private fun actualizarLista( f:()->List<Movimiento>)  {
        datos = f()

        if(datos.size != 0)
            noHayRegistrosTv.visibility = View.INVISIBLE

        adaptador.setDatos(datos)
        adaptador.notifyDataSetChanged()
        listaMovimientos.smoothScrollToPosition(0)
    }

    private fun mostrarCalendario() {
        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, months, day ->
            fechaFiltro.set(year, months, day)

            actualizarLista {
                Repositorio.getInstance(this)?.getMovimientosPorFecha(fechaFiltro)!!
            }
        }

        DatePickerDialog(
            this,
            listener,
            fechaFiltro.get(Calendar.YEAR),
            fechaFiltro.get(Calendar.MONTH),
            fechaFiltro.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_info -> {
                startActivity(Intent(this, InfoActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}