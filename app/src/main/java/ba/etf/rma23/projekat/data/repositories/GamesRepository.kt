package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameData
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getAge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Math.round
import java.util.Collections.emptyList
import java.util.Date
import kotlin.math.roundToInt

object GamesRepository {
    private var gamesDisplayed = emptyList<Game>()
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
        val favorites = savedGames.associateBy { it.id }
        val sortedGames = mutableListOf<Game>()

        for (game in savedGames)
            if (gamesDisplayed.contains(game))
                sortedGames.add(game)

        sortedGames.sortBy { it.title }
        val remainingGames = gamesDisplayed.filterNot { it.id in favorites.keys }
        remainingGames.sortedBy { it.title }
        sortedGames.addAll(remainingGames)

        return sortedGames
    }

    suspend fun getGamesSafe(name:String):List<Game>{
        var ageRating = arrayOf(3, 7, 12, 16, 18, 1, 3, 1, 10, 13, 17, 18)
        if(getAge()==0)
            return listOf()
        val games = getGamesByName(name)
        val safeGames: MutableList<Game> = mutableListOf()

        for(i in games.indices)
        {
            if(games[i].esrbRating == "null")
                safeGames.add(games[i])
            else
            {
                var value = (games[i].esrbRating.toDouble() - 1).toInt()
                if(value > 12 || ageRating[value] < getAge())
                    safeGames.add(games[i])
            }
        }
        GameData.setGamesData(safeGames)
        return safeGames
    }

}