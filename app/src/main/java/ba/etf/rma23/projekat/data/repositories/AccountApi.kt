package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountApi {
    @POST("{aid}/game")
    suspend fun saveGame(
        @Path("aid") id: String,
        @Body inputClass: InputClass
    ) : Response<ResponseClass>

    @DELETE("{aid}/game/{gid}")
    suspend fun deleteGame(
        @Path("aid") id: String,
        @Path("gid") igdb_id: Int
    ) : Response<String>

    @GET("{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String,
        @Query("fields") fields: String = "id,igdb_id,name,updatedAt,createdAt"
    ): Response<List<ResponseClass>>

    data class SendGame(
        @SerializedName("igdb_id") var id: Int?,
        @SerializedName("name") var name: String?
        )

    data class ResponseClass(
        @SerializedName("id") var id: Int,
        @SerializedName("igdb_id") var igdb: Int,
        @SerializedName("name") var name: String,
        @SerializedName("updatedAt") var updatedAt: String,
        @SerializedName("createdAt") var createdAt: String
    )

    data class InputClass(
       @SerializedName("game") var game: SendGame
    )
}