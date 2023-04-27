package ba.unsa.etf.rma.spirala1

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.stream.IntStream.range

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {
    @get:Rule
    var homeRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    /**
     * Test omogućava provjeru ispravnog rada aplikacije u lanscape orijentaciji.
     * Prvo se naredbama hasDescendant i isDisplayed provjerava da li su zaista
     * uporedo postavljeni i home fragment i game details fragment. Nakon toga vrši se
     * provjera da li je prikazana prva igrica iz liste. To se postiže naredbom hasDescendant.
     * Također se vrši provjera da li je se u ovoj orijentaciji pojavljuje Bottom Navigation koristeći
     * naredbe not i hasDescendent. For petljom se prolazi kroz spisak svih igrica. Tu se vrši provjera
     * uzastopnog odabira igrica, te provjera da li se na game details fragment šalju ispravni podaci.
     */
    @Test
    fun testLandscape() {

        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        val firstGame = GameData.getAll()[0]
        onView(isRoot()).check(matches(hasDescendant(withId(R.id.nav_host_fragment))))
        onView(withId(R.id.search_query_edittext)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.game_list)).check(matches(isDisplayed()))
        onView(withId(R.id.cover_imageview)).check(matches(isDisplayed()))
        onView(withId(R.id.publisher_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.developer_textview)).check(matches(isDisplayed()))

        onView(isRoot()).check(matches(hasDescendant(withText(firstGame.title))))
        onView(isRoot()).check(matches(hasDescendant(withText(firstGame.genre))))
        onView(isRoot()).check(matches(hasDescendant(withText(firstGame.description))))
        onView(isRoot()).check(matches(hasDescendant(withText(firstGame.platform))))
        onView(isRoot()).check(matches(hasDescendant(withText(firstGame.publisher))))

        onView(isRoot()).check(matches(not(hasDescendant(withId(R.id.bottom_nav)))))
        for (i in range(0, GameData.getAll().size - 1)) {
            var game = GameData.getAll()[i]
            onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
                hasDescendant(withText(game.title))),click()))

            onView(isRoot()).check(matches(hasDescendant(withText(game.title))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.genre))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.description))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.platform))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.publisher))))
        }
    }

    /***
     * Test provjerava ispravnost rada aplikacije u portrait orijentaciji. Prvo provjerava
     * da li je dugme details onemogućeno. Nakon toga nalazi zadnju igricu u listi,
     * te je odabira. Test zatim provjarava da li su ispravni podaci prikazani, naredbom
     * hasDescendant(withText(podatakZaProvjeru)).Nakon toga odabira se home dugme, koje vraća na home
     * fragment. Da li je zaista vraćeno na home fragment provjeravamo time da li je search prikazan.
     * Provjerava se da li je sada omogućeno details dugme, te se provjerava ispravnost prilikom
     * klika na njega, tj. da li će biti prikazana ispravna igrica.
     * Postupak odabira, provjere prikazane igrice, te ispravnosti rada details dugmeta se ponavlja za
     * svaku igricu iz liste.
     */
    @Test
    fun testPortrait() {
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)

        /*activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

         */

        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))
        var game = GameData.getAll()[GameData.getAll().size - 1]
        onView(withId(R.id.gameDetailsItem)).check(matches(isDisplayed()))
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(GameData.getAll().size - 1))
            .check(
                matches(
                    hasDescendant(withText(game.title))
                )
            )

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(game.title))
        ),click()))


        onView(isRoot()).check(matches(hasDescendant(withId(R.id.item_title_textview))))
        onView(withId(R.id.cover_imageview)).check(matches(isDisplayed()))
        onView(isRoot()).check(matches(hasDescendant(withText(game.title))))

        onView(withId(R.id.homeItem)).perform(click())
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))
        onView(withId(R.id.search_query_edittext)).check(matches(isDisplayed()))
        onView(withId(R.id.gameDetailsItem)).perform(click())

        onView(isRoot()).check(matches(hasDescendant(withText(game.title))))
        onView(isRoot()).check(matches(hasDescendant(withText(game.platform))))

        for(i in range(0, GameData.getAll().size - 1)) {
            onView(withId(R.id.homeItem)).perform(click())
            game = GameData.getAll()[i]
            onView(withId(R.id.gameDetailsItem)).check(matches(isDisplayed()))
            onView(withId(R.id.game_list)).perform(
                RecyclerViewActions.scrollToPosition<ViewHolder>(
                    i
                )
            )
                .check(
                    matches(
                        hasDescendant(withText(game.title))
                    )
                )

            onView(withId(R.id.game_list)).perform(
                RecyclerViewActions.actionOnItem<ViewHolder>(
                    allOf(
                        hasDescendant(withText(game.title)),
                        hasDescendant(withText(game.releaseDate)),
                        hasDescendant(withText(game.platform))
                    ), click()
                )
            )
            onView(withId(R.id.homeItem)).perform(click())
            onView(withId(R.id.gameDetailsItem)).perform(click())

            onView(isRoot()).check(matches(hasDescendant(withText(game.title))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.description))))
            onView(isRoot()).check(matches(hasDescendant(withText(game.genre))))
        }
    }

    /**
     * Test provjerava da li je uredu novi raspored elemenata u game details fragmentu.
     * Slika igrice je sada prebačena u gornji lijevi ugao, dok su naslov , datum objavljivanja
     * kao i žanr stavljeni sa desne strane. ESRB rating je stavljen ispod slike igrice, a
     * opis igrice ispod liste ratinga. Nakon toga je provjereno da li su podaci vidljivi
     * na ekranu.
     */
    @Test
    fun gameDetailsElements(){
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(GameData.getAll()[4].title))
        ),click()))

        onView(withId(R.id.cover_imageview)).check(isLeftOf(withId(R.id.item_title_textview)))
        onView(withId(R.id.item_title_textview)).check(isAbove(withId(R.id.release_date_textview)))
        onView(withId(R.id.esrb_rating_textview)).check(isAbove(withId(R.id.platform_textview)))
        onView(withId(R.id.description_textview)).check(isBelow(withId(R.id.user_impressions_list)))
        onView(withId(R.id.release_date_textview)).check(isAbove(withId(R.id.genre_textview)))

        onView(withId(R.id.item_title_textview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.cover_imageview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.esrb_rating_textview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.release_date_textview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.developer_textview)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.publisher_textview)).check(matches(isCompletelyDisplayed()))
    }

    /**
     * Test omogućava provjeru stanja prilikom promjene orijentacije. Ukoliko je aplikacija
     * bila u landscape orijentaciji, te u njoj je bila zabrana neka igrica, prilikom vraćanja
     * na portrait orijentaciju, game details dugme treba biti onemogućeno. Provjera to se postiže
     * naredbama isEnabled, odnosno not i isEnabled. Provjera ispravnog prelaska sa jedne orijentacije
     * na drugu vršimo provjerom da li su postavljena oba host framenta(nav_host_fragment i nav_host_fragment_1).
     * Nav_host_fragment u portrait orijentaciji vrši razmjenu između home i game details fragmenata, dok
     * u lanscape orijentaciji vrši razmjenu game details fragmenta. Nav_host_fragment_1 se koristi
     * isključivo u landscape orijentaciji.
     */
    @Test
    fun switchOrientations(){
        val activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(isRoot()).check(matches(not(hasDescendant(withId(R.id.bottom_nav)))))
        onView(isRoot()).check(matches(hasDescendant(withId(R.id.nav_host_fragment))))
        onView(isRoot()).check(matches(hasDescendant(withId(R.id.nav_host_fragment_1))))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(GameData.getAll()[2].title))),click()))

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(isRoot()).check(matches(hasDescendant(withId(R.id.bottom_nav))))
        onView(withId(R.id.gameDetailsItem)).check(matches(not(isEnabled())))
        onView(isRoot()).check(matches(hasDescendant(withId(R.id.nav_host_fragment))))
        onView(isRoot()).check(matches(not(hasDescendant(withId(R.id.nav_host_fragment_1)))))

    }
}