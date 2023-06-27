package ba.etf.rma23.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.unsa.etf.rma.spirala1.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDetailsFragment : Fragment() {
    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var impressionsView: RecyclerView
    private lateinit var impressionsAdapter: UserImpressionsListAdapter
    private var impressions: List<UserImpression> = emptyList()
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var addImpression: Button
    private lateinit var reviewEditText: EditText
    private lateinit var ratingBarImpression: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.game_details_fragment, container, false)

        title = view.findViewById(R.id.item_title_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        platform = view.findViewById(R.id.platform_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        description = view.findViewById(R.id.description_textview)
        genre = view.findViewById(R.id.genre_textview)
        addButton = view.findViewById(R.id.add_button)
        removeButton = view.findViewById(R.id.remove_button)
        addImpression = view.findViewById(R.id.send_impressions)
        reviewEditText = view.findViewById(R.id.review_edittext)
        ratingBarImpression = view.findViewById(R.id.rating_bar_impression)

        impressionsView = view.findViewById(R.id.user_impressions_list)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        impressionsView.layoutManager = HeightWrappingLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL)
        impressionsAdapter = UserImpressionsListAdapter(listOf())
        impressionsView.adapter = impressionsAdapter
        game = GameData.getGame(arguments?.getInt("game_id", 0)!!)
        addButton.isEnabled = true

        val favGames = GameData.favoriteGames
        for(i in favGames.indices){
            if(favGames[i].id == game.id) {
                addButton.isEnabled = false
            }
        }

        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            var impressionsFromServer = GameReviewsRepository.getReviewsForGame(game.id)

            impressionsFromServer.forEach { gameReview ->
                addReview(gameReview)
            }
            impressions.sortedByDescending { it.timestamp }
            impressionsAdapter.updateUserImpressions(impressions)
        }

            var rating: Int? = null;
            ratingBarImpression.setOnRatingBarChangeListener { _, newRating, _  ->
                rating = newRating.toInt();
            }

            addImpression.setOnClickListener {
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    var reviewText: String? = reviewEditText.text.toString()
                    if(reviewText == "")
                        reviewText = null

                    var review = GameReview(
                        rating,
                        reviewText,
                        game.id,
                        true,
                        "",
                        System.currentTimeMillis().toString()
                    )
                    GameReviewsRepository.sendReview(
                        requireContext(), review
                    )

                    addReview(review)
                    impressionsAdapter.updateUserImpressions(impressions)
                }

                var navController = findNavController()
                val id = navController.currentDestination?.id
                navController.popBackStack(id!!,true)
                navController.navigate(R.id.gameDetailsItem, bundleOf("game_id" to game.id))

            }

        populateDetails()
        addButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                AccountGamesRepository.saveGame(game)
                GameData.favoriteGames.add(game)

            }
            addButton.isEnabled = false
            removeButton.isEnabled = true
        }

        removeButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                AccountGamesRepository.removeGame(game.id)
                GameData.favoriteGames.removeIf { it -> it.id == game.id }
            }
            addButton.isEnabled = true
            removeButton.isEnabled = false
        }

        return view
    }

    private fun addReview(gameReview: GameReview) {
        if (gameReview.review != null && gameReview.review != "")
            impressions += UserReview(
                gameReview.student!!,
                gameReview.timestamp!!.toLong(), gameReview.review!!
            )
        if (gameReview.rating != null)
            impressions += UserRating(
                gameReview.student!!,
                gameReview.timestamp!!.toLong(),
                gameReview.rating!!.toDouble()
            )

        impressions += game.userImpressions
    }

    private fun populateDetails() {

        title.text = game.title
        releaseDate.text = game.releaseDate
        genre.text = game.genre
        platform.text = game.platform
        esrbRating.text = game.esrbRating
        developer.text = game.developer
        publisher.text = game.publisher
        description.text = game.description

        val imageUrl = game.coverImage
        Picasso.get().load("https:" + imageUrl).fit().into(coverImage);

        impressionsAdapter.updateUserImpressions(impressions)
    }

}