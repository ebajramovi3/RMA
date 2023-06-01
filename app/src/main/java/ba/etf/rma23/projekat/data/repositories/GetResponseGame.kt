package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.Date

class GetResponseGame (
    @SerializedName("id") var id: Int?,
    @SerializedName ("name") var name: String?,
    @SerializedName ("age_ratings") var esrb: List<RatingResponse>?,
    @SerializedName ("release_dates") var release_date: List<DateResponse>?,
    @SerializedName ("game_engines") var publisher: List<NameResponse>?,
    @SerializedName ("genres") var genre: List<NameResponse>?,
    @SerializedName ("involved_companies") var developer: List<DeveloperResponse>?,
    @SerializedName ("platforms") var platform: List<NameResponse>?,
    @SerializedName ("rating") var rating: Double?,
    @SerializedName ("summary") var description: String?,
    @SerializedName ("cover") var cover: URLResponse?
){
    data class DateResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("human") var human: String
    )

    data class RatingResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("rating") var rating: Double
    )

    data class NameResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("name") var name: String
    )

    data class DeveloperResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("developer") var developer: String
    )

    data class URLResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("url") var url: String
    )
}