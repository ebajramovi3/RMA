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

    suspend fun getSavedGames(): List<Game>{
        return emptyList()
    }

    suspend fun saveGame(game: Game): Game{
        var mainObject = JSONObject();            // Host object
        var requestObject = JSONObject();         // Included object

            requestObject.put("igdb_id", game.id)
            requestObject.put("name", game.title)

            mainObject.put("game", requestObject)

        val gson = Gson()


        var i = AccountApiConfig.retrofit.saveGame(getHash(), mainObject.toString())
        i.body()?.name?.let { Log.d(null, it) }
        return game
    }

    suspend fun removeGame(id: Int): Boolean? {
        return AccountApiConfig.retrofit.deleteGame(getHash(), id).body()
    }




}