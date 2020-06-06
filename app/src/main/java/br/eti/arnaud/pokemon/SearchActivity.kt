package br.eti.arnaud.pokemon

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import java.util.*


abstract class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private var searchTimer: Timer = Timer()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        searchView = (menu?.findItem(R.id.action_search)?.actionView as SearchView)
        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener({
                onSearchTextSubmit(it)
            }, {
                searchTimer.cancel()
                searchTimer = Timer()
                searchTimer.schedule(SEARCH_DELAY) {
                    runOnUiThread {
                        onSearchTextChange(it)
                    }
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    abstract fun onSearchTextSubmit(query: String)
    abstract fun onSearchTextChange(query: String)

    companion object {
        private const val SEARCH_DELAY: Long = 300
    }
}

