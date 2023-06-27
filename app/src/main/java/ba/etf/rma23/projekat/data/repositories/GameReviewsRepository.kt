package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GameReviewsRepository {

    suspend fun getReviewsForGame(igdbId: Int): List<GameReview> {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getGameReviews(igdbId)
            return@withContext response.body() ?: emptyList()
        }
    }

    suspend fun getOfflineReviews(context: Context): List<GameReview> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var gamereviews = db.reviewDao().getOfflineReviews()

            return@withContext gamereviews
        }
    }

    suspend fun sendOfflineReviews(context: Context): Int {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val offlineReviews = db.reviewDao().getOfflineReviews()
            db.reviewDao().deleteAll()
            var sentReviews = 0

            offlineReviews.forEach { review ->
                if(sendReview(context, review)) {
                    sentReviews++
                }
                else
                db.reviewDao().insertAll(review)
            }

            return@withContext sentReviews
        }
    }

    suspend fun sendReview(context: Context,review: GameReview): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val isFavorite = isGameFavorite(review.igdb_id)

                if (isFavorite) {
                    val response = AccountApiConfig.retrofit.postGameReview(
                        AccountGamesRepository.getHash(), review.igdb_id,
                        AccountApi.ReviewRequestBody(review.review, review.rating)
                    )
                    if (response.code() in 200..300) {
                        return@withContext true
                    } else {
                        review.online = false
                        saveOfflineReview(context, review)
                        return@withContext false
                    }
                } else {
                        saveGame(context, review)
                        return@withContext sendReview(context, review)
                }
            } catch (exception: Exception) {
                review.online = false
                saveOfflineReview(context, review)
                return@withContext false
            }
        }
    }

    private suspend fun isGameFavorite(igdbId: Int): Boolean {
            val favoriteGames = AccountGamesRepository.getSavedGames()
            return favoriteGames.any { game -> game.id== igdbId }
    }

    private suspend fun saveOfflineReview(context: Context,review: GameReview) {
        val db = AppDatabase.getInstance(context)
        review.online = false
        db.reviewDao().insertAll(review)
    }

    private suspend fun saveGame(context: Context, gameReview: GameReview): Boolean {
        try {
            val game = GamesRepository.getGameById(gameReview.igdb_id)
            AccountGamesRepository.saveGame(game)
            return true
        } catch(exception:Exception) {
            return false
        }
    }


}