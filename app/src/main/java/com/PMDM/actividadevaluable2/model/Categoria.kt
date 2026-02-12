package com.PMDM.actividadevaluable2.model

data class Categoria(var name: String ? = null){
    override fun toString(): String {
        return name ?: ""
    }
}