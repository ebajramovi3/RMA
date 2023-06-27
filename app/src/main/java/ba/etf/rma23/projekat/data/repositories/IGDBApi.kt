package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.unsa.etf.rma.spirala1.BuildConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IGDBApi {
    @Headers(
        "Accept: application/json",
        "Client-ID: z7m2bhsg2rlkhbcz1je5fa8zwfp8el",
        "Authorization: Bearer qp3ssei7vyrps6mdg5p3f1ckaceo85"
    )
    @GET("games/")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = "name,release_dates.human,age_ratings.rating,game_engines.name,genres.name,involved_companies.developer,platforms.name,cover.url,rating,summary",
        @Query("limit") limit: Int = 10
    ): Response<List<GetResponseGame>>

    @Headers(
        "Accept: application/json",
        "Client-ID: z7m2bhsg2rlkhbcz1je5fa8zwfp8el",
        "Authorization: Bearer qp3ssei7vyrps6mdg5p3f1ckaceo85"
    )
    @GET("games/{id}")
    suspend fun getGamesById(
        @Path("id") id: Int,
        @Query("fields") fields: String = "name,release_dates.human,age_ratings.rating,game_engines.name,genres.name,involved_companies.developer,platforms.name,cover.url,rating,summary",
    ): Response<List<GetResponseGame>>

    @Headers(
        "Accept: application/json",
        "Client-ID: z7m2bhsg2rlkhbcz1je5fa8zwfp8el",
        "Authorization: Bearer qp3ssei7vyrps6mdg5p3f1ckaceo85"
    )

    @GET("games")
    suspend fun getGamesSafe(
        @Query("search") name: String,
        @Query("age_ratings.rating") ageRating: Int
    ): Response<List<GetResponseGame>>

    @Headers(
        "Accept: application/json",
        "Client-ID: z7m2bhsg2rlkhbcz1je5fa8zwfp8el",
        "Authorization: Bearer qp3ssei7vyrps6mdg5p3f1ckaceo85"
    )
    @GET("games")
    suspend fun sortGames(
        @Query("sort") sort: String
    ): Response<List<GetResponseGame>>
}