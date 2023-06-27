package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.sql.Timestamp

@Dao
interface ReviewDao {
    @Query("SELECT * FROM gamereview")
    suspend fun getAll(): List<GameReview>

    @Insert
    suspend fun insertAll(vararg gameReviews: GameReview)

    @Query("SELECT COUNT(*) AS broj_reviews FROM gamereview WHERE online = :isOnline")
    suspend fun getOfflineReviewsCount(isOnline: Boolean = false): Int

    @Query("SELECT * FROM gamereview WHERE online = :isOnline")
    suspend fun getOfflineReviews(isOnline: Boolean = false): List<GameReview>

    @Query("DELETE FROM gamereview")
    suspend fun deleteAll()

    @Query("DELETE FROM gamereview WHERE online=:isOnline")
    suspend fun deleteOnline(isOnline: Boolean = true)
}