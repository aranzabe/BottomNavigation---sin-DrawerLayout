package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bottomnavigation.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.getValue
import kotlin.toString

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configura la Toolbar como ActionBar
        setSupportActionBar(binding.miMaterialToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //******************* Para iniciar el navController que me permitirá navegar de un fragmento a otro. Obtener NavController desde NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.miFragContainer) as NavHostFragment
        navController = navHostFragment.navController

        //*********** Para la Navigation Bottom Bar *****************
        val navView: BottomNavigationView = binding.miBottomNav
        //Pasamos cada ID del menú para que sean tenidos en cuenta como la selección hecha cada vez que pulsamos en uno de ellos.
        //Recuerda que en el xml de main definimos esto 'app:menu="@menu/mi_menu"' para la bottom navigation bar y asociarla.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_fragmento_a, R.id.navigation_fragmento_b
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // Evitar que al re-seleccionar la misma opción recargue el fragmento
        navView.setOnItemReselectedListener { /* no-op para evitar reload */ }
        //*********** Para la Navigation Bottom Bar *****************
        //En el archivo mi_nav.xml los id de los fragmentos en el nav_graph debe coincidir con los id de los elementos en mi_menu.xml.
        //Es decir para que funcione esto de arriba los fragmentos se llamarán:
        // -- en mi_nav: navigation_fragmento_a
        // -- en menu: navigation_fragmento_a
        //Lo de aquí abajo es para jugar con el título de la Toolbar. Se dispara automáticamente cuando cambia la navegación de un Fragmento a otro.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_fragmento_a -> {
                    //supportActionBar?.title = "Fragmento A"
                    supportActionBar?.hide()
                }
                R.id.navigation_fragmento_b -> {
                    supportActionBar?.hide()
                    //supportActionBar?.title = "Fragmento B"
                }
            }
        }


        binding.btnCargarF1.setOnClickListener {
            navController.navigate(R.id.navigation_fragmento_a)
        }

        binding.btCargarF2.setOnClickListener {
            navController.navigate(R.id.navigation_fragmento_b)
        }

        mainViewModel.textoCompartido.observe(this) {
            binding.txtMain.text = it
        }

        mainViewModel.myResponseUsuarioMain.observe(this) {
            binding.txtMain.text = it.toString()
        }
    }

    //Importante: permitir que el botón "up" funcione con NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}