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
    suspend fun getGamesByName(name:String):List<Game>{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(name)
            val list = response.body() ?: emptyList()
            val games: MutableList<Game> = mutableListOf()

            for(i in list.indices){
                val id = list[i].id
                val title = list[i].name
                val esrbRating = list[i].esrb?.get(0)?.rating.toString()
                val releaseDate = list[i].release_date?.get(0)?.human
                val publisher = list[i].publisher?.get(0)?.name
                val genre = list[i].genre?.get(0)?.name
                val developer = list[i].developer?.get(0)?.developer
                val platform = list[i].platform?.get(0)?.name
                val rating = (list[i].rating?.times(10))?.roundToInt()?.div(10.0)
                val description = list[i].description
                val coverImage = list[i].cover?.url
                val game: Game = Game(id,title,platform,releaseDate.toString(),rating,coverImage,esrbRating,developer,publisher,genre,description, listOf())

                games.add(game)
            }
            return@withContext games
        }

    }
}