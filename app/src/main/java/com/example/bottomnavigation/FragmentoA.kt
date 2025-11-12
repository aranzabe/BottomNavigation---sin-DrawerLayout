package com.example.bottomnavigation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.bottomnavigation.databinding.FragmentFragmentoABinding
import kotlin.getValue
import kotlin.toString

class FragmentoA : Fragment() {

    private var _binding: FragmentFragmentoABinding? = null
    private val binding get() = _binding!!
    private val fragmentoAViewModel: FragmentoAViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragmentoA()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Iniciamos binding.
        _binding = FragmentFragmentoABinding.inflate(inflater, container, false)
        val root: View = binding.root

        fragmentoAViewModel.textoF1.observe(viewLifecycleOwner) {
            binding.edFA.setText(it)
        }

        //Con esto podemos ver como este fragmento puede acceder al Viewmodel compartido y si cambio algo desde FragmentB (seleccionamos a una persona de la RV) afectaría a este observe.
        mainViewModel.myResponseUsuarioMain.observe(viewLifecycleOwner) {
            binding.edFA.setText(it.toString())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPulsarFA.setOnClickListener(){
            val text = binding.edFA.text.toString()
            mainViewModel.setTextoCompartido(text)

        }

        binding.btAtoB.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_fragmentoA_to_fragmentoB)
        }
    }
}