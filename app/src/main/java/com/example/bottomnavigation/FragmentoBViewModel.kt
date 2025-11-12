package com.example.bottomnavigation

import Modelo.Usuario
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.collections.orEmpty
import kotlin.collections.toMutableList

class FragmentoBViewModel : ViewModel() {
    private val _myResponseList = MutableLiveData<List<Usuario>>()
    val myResponseList: LiveData<List<Usuario>> get() = _myResponseList

    private val _myResponse = MutableLiveData<Usuario>()
    val myResponse: LiveData<Usuario> get() = _myResponse

    fun addUsuario(usuario: Usuario) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.add(usuario)
        _myResponseList.value = currentList
    }
    fun delUsuario(usuario: Usuario) {
        val currentList = _myResponseList.value.orEmpty().toMutableList()
        currentList.remove(usuario)
        _myResponseList.value = currentList
    }
}