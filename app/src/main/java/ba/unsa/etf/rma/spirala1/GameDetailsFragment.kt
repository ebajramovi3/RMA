package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    private lateinit var impressions: List<UserImpression>

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

        impressionsView = view.findViewById(R.id.user_impressions_list)

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        impressionsView.layoutManager = layoutManager
        impressionsAdapter = UserImpressionsListAdapter(listOf())
        impressionsView.adapter = impressionsAdapter

        game = GameData.getDetails(arguments?.getString("game_title", "")!!)

        impressions = game.userImpressions.sortedByDescending { it.timestamp }

        populateDetails()

        return view
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

        val context: Context = coverImage.context
        var id: Int = context.resources
            .getIdentifier(game.coverImage, "drawable", context.packageName)
        if (id == 0) id = context.resources
            .getIdentifier("game_picture", "drawable", context.packageName)
        coverImage.setImageResource(id)

        impressionsAdapter.updateUserImpressions(impressions)
    }

}