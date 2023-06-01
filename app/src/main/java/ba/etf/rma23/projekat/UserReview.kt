package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class UserReview(
    @SerializedName("username") override var username: String,
    @SerializedName("timestamp") override var timestamp: Long,
    @SerializedName("review") var review: String
): UserImpression()