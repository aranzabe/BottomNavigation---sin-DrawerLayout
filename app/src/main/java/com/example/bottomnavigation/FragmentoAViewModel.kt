package com.example.bottomnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentoAViewModel : ViewModel() {
    private val _textoF1 = MutableLiveData<String>()
    val textoF1: LiveData<String> = _textoF1

    fun actualizarTextoF1(nuevoTexto: String) {
        _textoF1.value = nuevoTexto
    }
}