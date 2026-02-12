package com.PMDM.actividadevaluable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.PMDM.actividadevaluable2.adapter.ProductosAdapter
import com.PMDM.actividadevaluable2.databinding.ActivityMainBinding
import com.PMDM.actividadevaluable2.model.Categoria
import com.PMDM.actividadevaluable2.model.Producto
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterCategorias: ArrayAdapter<Categoria>
    private lateinit var adapter: ProductosAdapter
    private var listaProductos: ArrayList<Producto> = ArrayList()
    private var listaCategorias: ArrayList<Categoria> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Configuración de pantalla completa
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        setContentView(binding.root)

        initViews()
        peticionesWeb()
    }

    private fun initViews() {
        // Toolbar
        setSupportActionBar(binding.toolbar)

        // Spinner (Categorías)
        adapterCategorias = ArrayAdapter(this, R.layout.item_spinner, listaCategorias)
        adapterCategorias.setDropDownViewResource(R.layout.item_spinner)
        binding.spCategorias.adapter = adapterCategorias

        // RecyclerView (Productos)
        adapter = ProductosAdapter(listaProductos, this)
        binding.rvProductos.adapter = adapter
        binding.rvProductos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun peticionesWeb() {
        realizarPeticionJsonProducts()
        realizarPeticionJsonCategories()
    }

    // --- MÉTODOS DE RED (VOLLEY) ---

    private fun realizarPeticionJsonProducts() {
        val url = "https://dummyjson.com/products"
        val request: JsonObjectRequest = JsonObjectRequest(url, {
            Log.v("conexion", "Conexion correcta, procesando la peticion: $it")
            procesarRespuestaProducts(it)
        }, {
            Log.v("conexion", "Error al realizar la peticion: $it")
        })
        Volley.newRequestQueue(this).add(request)
    }

    private fun procesarRespuestaProducts(param: JSONObject) {
        val productsArray: JSONArray = param.getJSONArray("products")
        val gson = Gson()
        for (i in 0 until productsArray.length()) {
            val productJSON: JSONObject = productsArray.getJSONObject(i)
            val producto: Producto = gson.fromJson(productJSON.toString(), Producto::class.java)
            adapter.addProducto(producto)
        }
    }

    private fun realizarPeticionJsonCategories() {
        val url = "https://dummyjson.com/products/categories"
        val request: JsonArrayRequest = JsonArrayRequest(url, {
            Log.v("conexion", "Conexion correcta, procesando la pteicion: $it")
            procesarRespuestaCategories(it)
        }, {
            Log.v("conexion", "Error al realizar la peticion: $it")
        })
        Volley.newRequestQueue(this).add(request)
    }

    private fun procesarRespuestaCategories(param: JSONArray) {
        val gson = Gson()
        val textoInicial = Categoria("TODAS LAS CATEGORIAS")
        listaCategorias.add(textoInicial)

        for (i in 0 until param.length()) {
            val categoriaJSON: JSONObject = param.getJSONObject(i)
            val categoria: Categoria = gson.fromJson(categoriaJSON.toString(), Categoria::class.java)
            listaCategorias.add(categoria)
        }
        adapterCategorias.notifyDataSetChanged()
    }

    // --- MENÚS ---

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemCarrito -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

}