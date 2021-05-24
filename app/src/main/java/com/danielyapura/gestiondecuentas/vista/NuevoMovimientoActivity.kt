package com.danielyapura.gestiondecuentas.vista

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import com.danielyapura.gestiondecuentas.R
import com.danielyapura.gestiondecuentas.data.Repositorio
import com.danielyapura.gestiondecuentas.model.Movimiento
import com.danielyapura.gestiondecuentas.model.TipoMovimiento
import com.danielyapura.gestiondecuentas.utils.mensaje
import kotlinx.android.synthetic.main.activity_nuevo_movimiento.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class NuevoMovimientoActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private var fechaSeleccionada = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_movimiento)

        configurarToolbar()
        configurarFechaEditText()
        configurarCantidadEditText()

        agregarBt.setOnClickListener {
            agregarMovimiento()
        }

    }

    private fun configurarFechaEditText() {

        mostrarFechaEditText()

        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, months, day ->
            fechaSeleccionada.set(year, months, day)
            mostrarFechaEditText()
        }

        dateTv.setOnClickListener {
            DatePickerDialog(
                this,
                listener,
                fechaSeleccionada.get(Calendar.YEAR),
                fechaSeleccionada.get(Calendar.MONTH),
                fechaSeleccionada.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun mostrarFechaEditText(){
        dateTv.setText(String.format("%d/%d/%d",
            fechaSeleccionada.get(Calendar.DAY_OF_MONTH),
            fechaSeleccionada.get(Calendar.MONTH),
            fechaSeleccionada.get(Calendar.YEAR)))
    }

    private fun configurarToolbar() {
        toolbar.title = "Nuevo movimiento"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun agregarMovimiento(){

        if (descripcionEt.text.isEmpty()){
            mensaje("Completar todos los campos", this)
            return
        }

        if (cantidadEt.text.isEmpty()){
            mensaje("Completar todos los campos", this)
            return
        }

        val movimiento = Movimiento(
            tipoMovimientoSeleccionado().toString(),
            descripcionEt.text.toString(),
            cantidadEt.text.toString().toFloat(),
            fechaSeleccionada.time)

        Repositorio.getInstance(this)?.guardar(movimiento)

        finish()
        overridePendingTransition(0, 0);
    }

    fun tipoMovimientoSeleccionado(): TipoMovimiento {

        radioGroup = findViewById(R.id.radioGroup)

        if (radioGroup.checkedRadioButtonId == R.id.debeRb)
            return TipoMovimiento.DEBE
        else
            return  TipoMovimiento.HABER
    }

    private fun configurarCantidadEditText() {

        var filtroDosDigitosDecimales = object : InputFilter {

            private var patron: Pattern = Pattern.compile("[0-9]*+((\\.[0-9]?)?)||(\\.)?");

            override fun filter(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Spanned?,
                p4: Int,
                p5: Int
            ): CharSequence? {

                var matcher: Matcher = patron.matcher(p3)
                if (!matcher.matches())
                    return "";
                return null;
            }
        }

        cantidadEt.filters = arrayOf(filtroDosDigitosDecimales)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish();
        return false
    }
}

