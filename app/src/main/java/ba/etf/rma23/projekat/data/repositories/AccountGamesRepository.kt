package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.projekat.Game
import com.google.gson.Gson
import org.json.JSONObject


object AccountGamesRepository {
    private var hash = "f513d7bf-74cd-4c04-bfa5-3d83b82cc552"
    private var age = 21

    fun setHash(acHash: String): Boolean{
        return true
    }

    fun getHash(): String{
        return hash
    }

    fun setAge(age: Int): Boolean{
        if(age < 3 || age > 100)
            return false
        this.age = age
        return true
    }

    suspend fun getSavedGames(): List<Game> {
        var response = AccountApiConfig.retrofit.getSavedGames(getHash()).body() ?: emptyList()
        val games: MutableList<Game> = mutableListOf()
        for(i in response.indices){
            var game = GamesRepository.getGamesByName(response[i].name).get(0)
            games.add(game)
        }
        return games
    }

    suspend fun saveGame(game: Game): Game{
        var body = AccountApi.InputClass(AccountApi.SendGame(game.id, game.title))

        var i = AccountApiConfig.retrofit.saveGame(getHash(), body)
        return game
    }

    suspend fun removeGame(id: Int): String {
        return AccountApiConfig.retrofit.deleteGame(getHash(), id).body() ?: ""
    }




}