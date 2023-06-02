package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.unsa.etf.rma.spirala1.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment: Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private var gamesList = GameData.getAll()
    private lateinit var viewModel: DataViewModel
    private lateinit var searchButton: Button
    private lateinit var searchText: EditText
    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            R.layout.home_fragment, container,
            false
        )

        games = view.findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(activity)

        gamesAdapter = GameListAdapter(arrayListOf()) { game ->
            showGameDetails(game)
        }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)

        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            searchText = view.findViewById(R.id.search_query_edittext)
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch{
            var result = GamesRepository.getGamesByName(searchText.text.toString())
            when (result) {
                is List<Game> -> {
                    onSuccess(result)
                    gamesAdapter.updateGames(result)
                }
                else-> onError()
            }
        }

        }

        return view
    }

    private fun showGameDetails(game: Game) {
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            val navHostFragment =
                parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

            val navController = navHostFragment.navController
            val id = navController.currentDestination?.id
            navController.popBackStack(id!!,true)
            navController.navigate(R.id.gameDetailsItem, bundleOf("game_title" to game.title))

        } else {
            viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
            game.title?.let { viewModel.setData(it) }

            var action = game.title?.let { HomeFragmentDirections.actionHomeItemToGameDetailsItem(it) }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }

    fun onSuccess(games: List<Game>){
        val toast = Toast.makeText(context, "Game found", Toast.LENGTH_SHORT)
        toast.show()
        gamesAdapter.updateGames(games)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
}