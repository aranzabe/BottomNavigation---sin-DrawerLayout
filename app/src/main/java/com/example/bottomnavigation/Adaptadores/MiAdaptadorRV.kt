package Adaptadores

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import Modelo.Usuario
import com.example.bottomnavigation.MainViewModel
import com.example.bottomnavigation.databinding.ItemCardBinding
import com.example.bottomnavigation.FragmentoBViewModel



/**
 * Adaptador de RecyclerView para mostrar una lista de usuarios
 * con soporte para clic corto (seleccionar) y largo (eliminar).
 */
class MiAdaptadorRV(
    private val context: Context,
    private var listaUsuarios: MutableList<Usuario>,
    private val viewModel: MainViewModel,
    private val viewModelFb: FragmentoBViewModel
) : RecyclerView.Adapter<MiAdaptadorRV.UsuarioViewHolder>() {

    // --- ViewHolder usando ViewBinding ---
    class UsuarioViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    // --- Inflar layout de cada ítem ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    // --- Enlazar datos con la vista ---
    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]

        // Asignar los valores a los TextView del layout
        holder.binding.txtId.text = "ID: ${usuario.id}"
        holder.binding.txtNombre.text = "Nombre: ${usuario.nombre}"
        holder.binding.txtEdad.text = "Edad: ${usuario.edad}"

        // --- CLICK NORMAL → seleccionar usuario ---
        holder.itemView.setOnClickListener {
            viewModel.setUsuario(usuario)
            Toast.makeText(context, "Seleccionado: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
        }

        // --- CLICK LARGO → eliminar usuario ---
        holder.itemView.setOnLongClickListener {
            Toast.makeText(context, "Eliminando: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
            viewModelFb.delUsuario(usuario)
            removeUsuarioPorId(usuario.id)
            true
        }
    }

    override fun getItemCount(): Int = listaUsuarios.size

    // --- Métodos auxiliares para gestionar la lista ---
    @SuppressLint("NotifyDataSetChanged")
    fun setUsuarios(nuevaLista: List<Usuario>) {
        listaUsuarios.clear()
        listaUsuarios.addAll(nuevaLista)
        notifyDataSetChanged() // repinta toda la lista
    }

    fun addUsuario(usuario: Usuario) {
        listaUsuarios.add(usuario)
        notifyItemInserted(listaUsuarios.size - 1)
    }

    fun removeUsuarioPorId(id: Int?) {
        val index = listaUsuarios.indexOfFirst { it.id == id }
        if (index != -1) {
            listaUsuarios.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateUsuario(usuario: Usuario) {
        val index = listaUsuarios.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            listaUsuarios[index] = usuario
            notifyItemChanged(index)
        }
    }
}
