package ba.unsa.etf.rma.spirala1

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment: Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private var gamesList = GameData.getAll()
    private lateinit var viewModel: DataViewModel

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
            viewModel.setData(game.title)

            var action = HomeFragmentDirections.actionHomeItemToGameDetailsItem(game.title)
            findNavController().navigate(action)
        }
    }

    private fun populateViewForOrientation(inflater: LayoutInflater, viewGroup: ViewGroup?) {
        viewGroup!!.removeAllViewsInLayout()
        var subview = inflater.inflate(R.layout.home_fragment, viewGroup, false)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            var action = HomeFragmentDirections.actionHomeItemToGameDetailsItem(GameData.getAll()[0].title)
            findNavController().navigate(action)
        }
    }
}