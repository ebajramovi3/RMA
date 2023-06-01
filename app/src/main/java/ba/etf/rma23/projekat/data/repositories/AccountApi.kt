package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {
    @POST("{aid}/game")
    suspend fun saveGame(
        @Path("aid") id: String,
        @Body sendGame: String
    ) : Response<SendGame>

    data class SendGame(
        @SerializedName("igdb_id") var id: Int?,
        @SerializedName("name") var name: String?
    )

    @DELETE("{aid}/game/{gid}")
    suspend fun deleteGame(
        @Path("aid") id: String,
        @Path("gid") igdb_id: Int
    ) : Response<Boolean>


    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String
    ): Response<List<Game>>
}