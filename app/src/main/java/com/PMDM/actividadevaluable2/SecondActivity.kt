package com.PMDM.actividadevaluable2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.PMDM.actividadevaluable2.adapter.ProductosCarritoAdapter
import com.PMDM.actividadevaluable2.databinding.SecondActivityBinding
import com.PMDM.actividadevaluable2.model.Carrito
import com.PMDM.actividadevaluable2.model.Producto

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding
    private lateinit var adapterCarrito: ProductosCarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        //Quitar la barra superior de bateria, wifi, hora...
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContentView(binding.root)

        // Llamamos a los métodos de inicialización en orden
        initViews()
        initListeners()
        actualizarInterfaz()
    }

    private fun initViews() {
        // Inicializamos el adapter con la lambda para actualizar el subtotal
        adapterCarrito = ProductosCarritoAdapter(Carrito.listaSeleccionados, this) {
            actualizarInterfaz()
        }

        binding.rvProductosCarrito.apply {
            adapter = adapterCarrito
            layoutManager = LinearLayoutManager(this@SecondActivity)
        }
    }

    private fun initListeners() {
        binding.btnVaciarCarrito.setOnClickListener {
            vaciarCarrito()
        }

        binding.btnConfirmarCompra.setOnClickListener {
            realizarCompra()
        }
    }

    private fun actualizarInterfaz() {
        val total = calcularTotal()
        binding.subtotal.text = String.format("%.2f€", total)

        // Si el carrito esta vacio ocultamos botones
        val carritoVacio = Carrito.listaSeleccionados.isEmpty()
        binding.btnConfirmarCompra.isEnabled = !carritoVacio
        binding.btnVaciarCarrito.isEnabled = !carritoVacio
    }

    // --- LÓGICA DE NEGOCIO ---

    private fun calcularTotal(): Double {
        return Carrito.listaSeleccionados.sumOf { it.price ?: 0.0 }
    }

    private fun vaciarCarrito() {
        Carrito.listaSeleccionados.clear()
        adapterCarrito.notifyDataSetChanged()
        actualizarInterfaz()
        Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show()
    }

    private fun realizarCompra() {
        if (Carrito.listaSeleccionados.isNotEmpty()) {
            val total = calcularTotal()
            val totalFormateado = String.format("%.2f", total)
            Carrito.listaSeleccionados.clear()
            adapterCarrito.notifyDataSetChanged()
            actualizarInterfaz()
            Toast.makeText(this, "Enhorabuena, compra por valor de ${totalFormateado}€ realizada", Toast.LENGTH_LONG).show()
        }
    }
}