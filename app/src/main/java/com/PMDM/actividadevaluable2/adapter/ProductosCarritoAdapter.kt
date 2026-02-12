package com.PMDM.actividadevaluable2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.PMDM.actividadevaluable2.databinding.ItemCarritoBinding
import com.PMDM.actividadevaluable2.model.Carrito
import com.PMDM.actividadevaluable2.model.Producto
import com.bumptech.glide.Glide

class ProductosCarritoAdapter(
    var listaProductos: ArrayList<Producto>,
    var contexto: Context,
    private val onProductoEliminado: () -> Unit
): RecyclerView.Adapter<ProductosCarritoAdapter.MyHolder>() {

    inner class MyHolder(var binding: ItemCarritoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val binding = ItemCarritoBinding.inflate(LayoutInflater.from(contexto), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        val item: Producto = listaProductos[position]
        holder.binding.nombreTv.text = item.title
        holder.binding.precioTv.setText("${item.price}€")
        Glide.with(contexto).load(item.thumbnail).into(holder.binding.imageRv)

        holder.binding.btnEliminarDelCarrito.setOnClickListener {
            Carrito.eliminar(item)
            notifyItemRemoved(position)
            onProductoEliminado()
            Toast.makeText(contexto, "${item.title} eliminado del carrito.", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    //Numero de elementos que hay en la lista
    override fun getItemCount(): Int {
        return listaProductos.size
    }




}