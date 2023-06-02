package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("platform") var platform: String,
    @SerializedName("releaseDate") var releaseDate: String,
    @SerializedName("rating") var rating: Double,
    @SerializedName("coverImage") var coverImage: String,
    @SerializedName("esrbRating") var esrbRating: String,
    @SerializedName("developer") var developer: String,
    @SerializedName("publisher") var publisher: String,
    @SerializedName("genre") var genre: String,
    @SerializedName("description") var description: String,
    @SerializedName("userImpressions") var userImpressions: List<UserImpression>
)
