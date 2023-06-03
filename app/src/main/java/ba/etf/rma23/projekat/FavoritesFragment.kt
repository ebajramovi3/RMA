package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private var gamesList: List<Game> = emptyList()
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            R.layout.favorites_fragment, container,
            false
        )
        games = view.findViewById(R.id.favorites_list)
        games.layoutManager = LinearLayoutManager(activity)

        gamesAdapter = GameListAdapter(emptyList()) { game ->
            showGameDetails(game)
        }
        games.adapter = gamesAdapter

            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                gamesList = AccountGamesRepository.getSavedGames()
                GameData.favoriteGames.removeIf { _ -> true }
                GameData.favoriteGames.addAll(gamesList)
                withContext(Dispatchers.Main) {
                    gamesAdapter.updateGames(gamesList)
                }
            }

        return view
    }

    private fun showGameDetails(game: Game) {
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation != Configuration.ORIENTATION_LANDSCAPE) {
            viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
            game.id.let { viewModel.setData(it) }

            var action = game.id.let { FavoritesFragmentDirections.actionFavoritesItemToGameDetailsItem(it) }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }
}