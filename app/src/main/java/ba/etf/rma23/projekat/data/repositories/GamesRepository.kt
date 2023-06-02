package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Math.round
import java.util.Collections.emptyList
import java.util.Date
import kotlin.math.roundToInt

object GamesRepository {
    var gamesDisplayed: List<Game> = emptyList()
    suspend fun getGamesByName(name:String):List<Game>{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(name)
            val list = response.body() ?: emptyList()
            val games: MutableList<Game> = mutableListOf()

            for(i in list.indices){
                val id = list[i].id ?: 0
                val title = list[i].name ?: ""
                val esrbRating = list[i].esrb?.get(0)?.rating.toString()
                val releaseDate = list[i].release_date?.get(0)?.human
                val publisher = list[i].publisher?.get(0)?.name ?: ""
                val genre = list[i].genre?.get(0)?.name ?: ""
                val developer = list[i].developer?.get(0)?.developer ?: ""
                val platform = list[i].platform?.get(0)?.name ?: ""
                val rating = (list[i].rating?.times(10))?.roundToInt()?.div(10.0) ?: 0.0
                val description = list[i].description ?: ""
                val coverImage = list[i].cover?.url ?: ""
                val game: Game = Game(id,title,platform,releaseDate.toString(),rating,coverImage,esrbRating,developer,publisher,genre,description, listOf())

                games.add(game)
            }
            gamesDisplayed = games
            return@withContext games
        }
    }

    suspend fun sortGames():List<Game>{
        val savedGames = AccountGamesRepository.getSavedGames()
        val favorites = savedGames.associateBy { it.title }
        val sortedGames = mutableListOf<Game>()

        for (game in savedGames) {
            if (gamesDisplayed.contains(game)) {
                sortedGames.add(game)
            }
        }
        sortedGames.sortBy { it.title }
        val remainingGames = gamesDisplayed.filterNot { it.title in favorites.keys }
        remainingGames.sortedBy { it.title }
        sortedGames.addAll(remainingGames)

        return sortedGames
    }
}