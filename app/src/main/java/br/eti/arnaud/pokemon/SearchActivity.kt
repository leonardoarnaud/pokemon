package br.eti.arnaud.pokemon

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import java.util.*

abstract class SearchActivity : AppCompatActivity() {

    private var searchTimer: Timer = Timer()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
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

    abstract fun onSearchTextSubmit(text: String)
    abstract fun onSearchTextChange(text: String)

    companion object {
        private const val SEARCH_DELAY: Long = 300
    }
}

