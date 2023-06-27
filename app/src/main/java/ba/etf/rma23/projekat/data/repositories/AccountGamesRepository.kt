package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {
    private var hash: String = "f513d7bf-74cd-4c04-bfa5-3d83b82cc552"
    private var age: Int = 21

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

    fun getAge(): Int{
        return age
    }

    suspend fun getSavedGames(): List<Game> {
        var response = AccountApiConfig.retrofit.getSavedGames(getHash()).body() ?: emptyList()
        val games: MutableList<Game> = mutableListOf()
        for(i in response.indices){
            var gamesByName = GamesRepository.getGamesByName(response[i].name)
            for (j in gamesByName.indices) {
                var game = gamesByName.get(j)
                if(game.id == response[i].igdb) {
                    games.add(game)
                    break
                }
            }
            //var game = GamesRepository.getGamesByName(response[i].name).get(0)
            //games.add(game)
        }
        return games
    }

    suspend fun saveGame(game: Game): Game{
        var body = AccountApi.InputClass(AccountApi.SendGame(game.id, game.title))
        AccountApiConfig.retrofit.saveGame(getHash(), body)
        return game
    }

    suspend fun removeGame(id: Int): Boolean {
       if(AccountApiConfig.retrofit.deleteGame(getHash(), id).body()?.success != null) {
           return true
       }
        return false
    }

    suspend fun getGamesContainingString(query: String): List<Game> {
        return withContext(Dispatchers.IO) {
            val games = getSavedGames()
            val filteredGames = games.filter { game ->
                game.title.contains(query, ignoreCase = true)
            }
            return@withContext filteredGames
        }
    }

    suspend fun removeNonSafe():Boolean{
        var ageRating = arrayOf(3, 7, 12, 16, 18, 1, 3, 1, 10, 13, 17, 18)
        if(getAge() == 0)
            return true
        val games = getSavedGames()
        for(i in games.indices)
        {
            if(games[i].esrbRating == "null")
                continue
            var value = (games[i].esrbRating.toDouble() - 1).toInt()
            if(value > 12)
                continue
            if(ageRating[value] > getAge())
               if(!removeGame(games[i].id))
                   return false
        }
        return true
    }

}