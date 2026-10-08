package com.example.jetpackcomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposeapp.data.sampleCarreras
import com.example.jetpackcomposeapp.model.Materia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.flow.asStateFlow
class CarrerasViewModel : ViewModel() {
    private val _carreras = MutableStateFlow(sampleCarreras)
    val carreras: StateFlow<Map<String, List<Materia>>> =
        _carreras.asStateFlow()
    fun buscarMateriaPorCodigo(codigo: String): Materia? {
        return _carreras.value.values.flatten().firstOrNull { it.codigo ==
                codigo }
    }
}