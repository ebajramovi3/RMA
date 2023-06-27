package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AppDatabase
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.unsa.etf.rma.spirala1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var lastGameId: Int? = null
    private lateinit var viewModel: DataViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        var scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            var db = AppDatabase.getInstance(this@MainActivity)
            var review = GameReview(0, null, 0, true, "", "")
            db.reviewDao().insertAll(review)
            db.reviewDao().deleteOnline()
        }
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

        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
            doOnPortrait()
         else
           doOnLandscape()

    }

    private fun doOnPortrait(){
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        navController.setGraph(R.navigation.nav)
        navView.setupWithNavController(navController)

        if (lastGameId == null)
            navView.menu.findItem(R.id.gameDetailsItem).isEnabled = false

        viewModel.getSelectedData().observe(this) {
            lastGameId = it
        }
        navView.setOnItemSelectedListener { item ->

            if (!navView.menu.findItem(R.id.gameDetailsItem).isEnabled && lastGameId != null)
                navView.menu.findItem(R.id.gameDetailsItem).isEnabled = true

            val visibleFragment = getVisibleFragment()
            when (item.itemId) {
                R.id.gameDetailsItem -> {
                    if (visibleFragment is HomeFragment) {
                        val action =
                            HomeFragmentDirections.actionHomeItemToGameDetailsItem(lastGameId!!)
                        navController.navigate(action)
                    } else if(visibleFragment is FavoritesFragment){
                        val action =
                            FavoritesFragmentDirections.actionFavoritesItemToGameDetailsItem(
                                lastGameId!!
                            )
                        navController.navigate(action)
                    }
                }

                R.id.homeItem -> {
                    if (visibleFragment is GameDetailsFragment) {
                        val action =
                            GameDetailsFragmentDirections.actionGameDetailsItemToHomeItem()
                        navController.navigate(action)
                    } else if(visibleFragment is FavoritesFragment) {
                        val action =
                            FavoritesFragmentDirections.actionFavoritesItemToHomeItem()
                        navController.navigate(action)
                    }

                    if (!navView.menu.findItem(R.id.gameDetailsItem).isEnabled && lastGameId != null)
                        navView.menu.findItem(R.id.gameDetailsItem).isEnabled = true
                }
                else -> {
                    if (visibleFragment is GameDetailsFragment) {
                        val action =
                            GameDetailsFragmentDirections.actionGameDetailsItemToFavoritesFragment()
                        navController.navigate(action)
                    } else if (visibleFragment is HomeFragment) {
                        val action =
                            HomeFragmentDirections.actionHomeItemToFavoritesItem()
                        navController.navigate(action)
                    }

                }
            }
            true
        }
    }

    private fun doOnLandscape(){
        val id = navController.currentDestination?.id
        navController.popBackStack(id!!,true)
        navController.navigate(R.id.gameDetailsItem, bundleOf("game_id" to 1905))
    }
}