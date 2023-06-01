package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.HomeFragmentDirections
import ba.unsa.etf.rma.spirala1.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    private var lastGameTitle: String? = null
    private lateinit var viewModel: DataViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            doOnPortrait()
        else
           doOnLandscape()
    }

    private fun getVisibleFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(0)
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
       super.onConfigurationChanged(newConfig)
        setContentView(R.layout.home_activity)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            doOnPortrait()
         else
           doOnLandscape()

    }

    private fun doOnPortrait(){
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        navController.setGraph(R.navigation.nav)
        navView.setupWithNavController(navController)

        if (lastGameTitle == null)
            navView.menu.findItem(R.id.gameDetailsItem).isEnabled = false

        navView.setOnItemSelectedListener { item ->
            viewModel.getSelectedData().observe(this) {
                lastGameTitle = it
            }

            val visibleFragment = getVisibleFragment()
            when (item.itemId) {
                R.id.gameDetailsItem -> {
                    if (visibleFragment is HomeFragment) {
                        val action =
                            HomeFragmentDirections.actionHomeItemToGameDetailsItem(lastGameTitle!!)
                        navController.navigate(action)
                    }
                }

                else -> {
                    if (visibleFragment is GameDetailsFragment) {
                        val action =
                            GameDetailsFragmentDirections.actionGameDetailsItemToHomeItem()
                        navController.navigate(action)
                    }

                    if (!navView.menu.findItem(R.id.gameDetailsItem).isEnabled && lastGameTitle != null)
                        navView.menu.findItem(R.id.gameDetailsItem).isEnabled = true
                }
            }
            true
        }
    }

    private fun doOnLandscape(){
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!,true)
        navController.navigate(R.id.gameDetailsItem, bundleOf("game_title" to GameData.getAll()[0].title))
    }
}