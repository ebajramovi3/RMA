package ba.unsa.etf.rma.spirala1

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
): UserImpression()