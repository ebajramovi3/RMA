package ba.unsa.etf.rma.spirala1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private var lastGameTitle: String? = null
    private lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        navView.setupWithNavController(navController)
        navHostFragment.apply {
            bundleOf("last_game" to lastGameTitle)
        }

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        navView.menu.findItem(R.id.gameDetailsItem).isEnabled = false

        navView.setOnItemSelectedListener { item ->
            viewModel.getSelectedData().observe(this) {
                lastGameTitle = it
            }

            val visibleFragment = getVisibleFragment()

            when(item.itemId  ) {
                R.id.gameDetailsItem -> {
                    if(visibleFragment is HomeFragment) {
                        val action =
                            HomeFragmentDirections.actionHomeItemToGameDetailsItem(lastGameTitle!!)
                        navController.navigate(action)
                    }

                }
                else -> {
                    if(visibleFragment is GameDetailsFragment){
                        val action = GameDetailsFragmentDirections.actionGameDetailsItemToHomeItem()
                        navController.navigate(action)
                    }

                    if (!navView.menu.findItem(R.id.gameDetailsItem).isEnabled && lastGameTitle != null)
                        navView.menu.findItem(R.id.gameDetailsItem).isEnabled = true
                }
            }
            true
        }
    }

    private fun getVisibleFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(0)
    }
}