package ba.unsa.etf.rma.spirala1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private var gamesList = GameData.getAll()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        var homeButton = findViewById<Button>(R.id.home_button)
        var detailsButton = findViewById<Button>(R.id.details_button)

        homeButton.isEnabled = false

        if(!intent.hasExtra("last_game"))
        detailsButton.isEnabled = false

        games = findViewById(R.id.game_list)
        games.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        gamesAdapter = GameListAdapter(arrayListOf()) { game ->
            showGameDetails(game)
        }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)


        detailsButton.setOnClickListener {
            val extras = intent.extras
            if (extras != null) {
                val intent = Intent(this, GameDetailsActivity::class.java).apply {
                    putExtra("game_title", extras.getString("last_game"))
                }
                startActivity(intent)
            }
        }
    }

    private fun showGameDetails(game: Game) {
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title", game.title)
        }
        startActivity(intent)
    }

}