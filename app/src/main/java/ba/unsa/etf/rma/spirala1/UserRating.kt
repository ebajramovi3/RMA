package ba.unsa.etf.rma.spirala1

data class UserRating(
    override val username: String,
    override val timestamp: Long,
    val rating: Double
): UserImpression(username, timestamp)
