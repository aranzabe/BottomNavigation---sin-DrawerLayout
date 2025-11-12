package com.example.bottomnavigation

import Adaptadores.MiAdaptadorRV
import Modelo.Factoria
import Modelo.Usuario
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigation.databinding.FragmentFragmentoBBinding
import kotlin.getValue

class FragmentoB : Fragment() {


    private var _binding: FragmentFragmentoBBinding? = null
    private val binding get() = _binding!!

    //Los ViewModels.
    //Usamos by viewModels() para crear una instancia del viewmodel accesible solo por este fragmento.
    private val fragmentoBViewModel: FragmentoBViewModel by viewModels()
    //Usa by activityViewModels() para compartir el ViewModel de la Activity.
    private val mainViewModel: MainViewModel by activityViewModels()

    //Para la RV.
    var datosRepresentar : ArrayList<Usuario> = ArrayList()
    lateinit var customAdapter : MiAdaptadorRV

    companion object {
        fun newInstance() = FragmentoB()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoBBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicializar el RecyclerView y el adaptador
        setupRecyclerView()

        binding.btnAddUsuario.setOnClickListener() {
            fragmentoBViewModel.addUsuario(Factoria.generaUsuario())
        }

        binding.btBtoA.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_fragmentoB_to_fragmentoA)
        }

        fragmentoBViewModel.myResponseList.observe(viewLifecycleOwner) {
            datosRepresentar.clear()
            datosRepresentar.addAll(it)
            customAdapter.notifyDataSetChanged()
        }

        //Observamos cualquier cambio en el ViewModel compartido con main.
        mainViewModel.myResponseUsuarioMain.observe(viewLifecycleOwner) { usuario ->
            Toast.makeText(requireContext(), "Usuario modificado en el main desde FB", Toast.LENGTH_SHORT).show()
        }

        mainViewModel.textoCompartido.observe(viewLifecycleOwner){
            binding.etFB.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //**************************** Auxiliares ***************************
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.RVListaPersonas.layoutManager = linearLayoutManager
        //Le pasamos también el viewModelMain para poder pasar datos desde el fragmento B a la activity.
        customAdapter = MiAdaptadorRV(requireContext(), datosRepresentar, mainViewModel, fragmentoBViewModel)
        binding.RVListaPersonas.adapter = customAdapter
    }
}