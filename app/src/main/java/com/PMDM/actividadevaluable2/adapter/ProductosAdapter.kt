package com.PMDM.actividadevaluable2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PMDM.actividadevaluable2.databinding.ItemRvProductoBinding
import com.PMDM.actividadevaluable2.model.Producto
import com.bumptech.glide.Glide

//CLASE SE SIRVE DE PUENTE ENTRE LOS DATOS Y LA LISTA VISUAL

//Nombre de la clase - hereda de RecyclerView.Adapter que es la clase base de android para gestionar las listas
// y el ProductosAdapter.Myholder es la clase interna que contiene la vista de cada elemento de la lista
class ProductosAdapter(var listaProductos: ArrayList<Producto>, var contexto: Context): RecyclerView.Adapter<ProductosAdapter.MyHolder>() {

    //Crea una clase interna/anidada que obtiene el parametro binding de la clase del xml creado anteriormente que es la vista de cada elemento de la lista
    // sirve tambien para con el binding acceder a los elementos de la vista (botones, imagenes...)
    inner class MyHolder(var binding: ItemRvProductoBinding): RecyclerView.ViewHolder(binding.root)


    //crea el holder (la vista de las filas), utilizando la inner class que a su vez utiliza el xml creado
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val binding = ItemRvProductoBinding.inflate(LayoutInflater.from(contexto), parent, false)
        return MyHolder(binding)
    }

    //busca en la posicion de la lista mostrando en el holder lo que hay en esa posicion -> PINTA CADA FILA
    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        val item: Producto = listaProductos[position]
        holder.binding.nombreTv.text = item.title
        holder.binding.precioTv.setText("${item.price}€")
        Glide.with(contexto).load(item.thumbnail).into(holder.binding.imageRv)

        holder.binding.btnAddToCart.setOnClickListener {
            com.PMDM.actividadevaluable2.model.Carrito.agregar(item)
            android.widget.Toast.makeText(contexto, "${item.title} añadido al carrito.", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    //Numero de elementos que hay en la lista
    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun addProducto(producto: Producto){
        this.listaProductos.add(producto)
        notifyItemInserted(listaProductos.size-1)
    }



}