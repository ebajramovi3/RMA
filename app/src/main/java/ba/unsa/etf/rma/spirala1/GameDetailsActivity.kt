package ba.unsa.etf.rma.spirala1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala1.GameData.Companion.getAll

class GameDetailsActivity : AppCompatActivity() {
    private lateinit var game: Game
    private lateinit var title : TextView
    private lateinit var releaseDate : TextView
    private lateinit var coverImage : ImageView
    private lateinit var platform: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var impressionsView: RecyclerView
    private lateinit var impressionsAdapter: UserImpressionsListAdapter
    private lateinit var homeButton: Button
    private lateinit var detailsButton: Button
    private lateinit var impressions: List<UserImpression>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_details)

        title = findViewById(R.id.game_title_textview)
        coverImage = findViewById(R.id.cover_imageview)
        releaseDate = findViewById(R.id.release_date_textview)
        platform = findViewById(R.id.platform_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        description = findViewById(R.id.description_textview)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)

        detailsButton.isEnabled = false

        genre = findViewById(R.id.genre_textview)
        impressionsView = findViewById(R.id.user_impressions_list)
        impressionsView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)

        impressionsAdapter = UserImpressionsListAdapter(listOf())
        impressionsView.adapter = impressionsAdapter

        val extras = intent.extras
        if (extras != null) {
            game = GameData.getDetails(extras.getString("game_title",""))!!
            impressions = game.userImpressions.sortedByDescending { it.timestamp }
            populateDetails()
        } else {
            finish()
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("last_game", game.title)
            }
            startActivity(intent)
        }
    }

    private fun populateDetails() {

        title.text= game.title
        releaseDate.text= game.releaseDate
        genre.text=game.genre
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