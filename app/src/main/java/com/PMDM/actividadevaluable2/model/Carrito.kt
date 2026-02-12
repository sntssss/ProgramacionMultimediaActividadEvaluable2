package com.PMDM.actividadevaluable2.model

object Carrito {
    val listaSeleccionados: ArrayList<Producto> = ArrayList()

    fun agregar(producto: Producto) {
        listaSeleccionados.add(producto)
    }

    fun eliminar(producto: Producto){
        listaSeleccionados.remove(producto)
    }
}