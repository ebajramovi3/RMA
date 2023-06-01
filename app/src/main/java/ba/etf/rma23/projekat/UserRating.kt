package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("username") override var username: String,
    @SerializedName("timestamp") override var timestamp: Long,
    @SerializedName("rating") var rating: Double
): UserImpression()
