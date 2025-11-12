package Modelo

import Modelo.Usuario
import java.lang.Math.random
import kotlin.random.Random

object Factoria {
    private val nombres= arrayOf("Asier","José María","Juan Ramón","Ángel","Ismael","Hamza","Óscar","Sergio","Mar","Adolfo","José Pascual","Celia","Juan María","Paula")

    fun generaUsuario(): Usuario {
        return Usuario(Random.nextInt(1000, 9999),nombres.random(), Random.nextInt(1, 100))
    }
}