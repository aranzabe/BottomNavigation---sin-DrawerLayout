package com.example.bottomnavigation

import Modelo.Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _textocompartido = MutableLiveData<String>()
    val textoCompartido: LiveData<String> get() = _textocompartido
    private val _myResponseUsuarioMain = MutableLiveData<Usuario>()
    val myResponseUsuarioMain: LiveData<Usuario> get() = _myResponseUsuarioMain

    fun setUsuario(usuario: Usuario) {
        _myResponseUsuarioMain.value = usuario
    }

    fun setTextoCompartido(text: String) {
        _textocompartido.value = text
    }
}