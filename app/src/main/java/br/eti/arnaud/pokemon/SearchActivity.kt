package br.eti.arnaud.pokemon

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import br.eti.arnaud.pokemon.ui.BaseActivity
import java.util.*


abstract class SearchActivity : BaseActivity() {

    private var currentQuery: String = ""
    private lateinit var searchView: SearchView
    private var searchTimer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            currentQuery = it.getString(QUERY) ?: ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        searchView = (menu?.findItem(R.id.action_search)?.actionView as SearchView)
        searchView.setQuery(currentQuery, currentQuery.isNotEmpty())
        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener({
                onSearchTextSubmit(it)
                hideKeyboard(searchView)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(QUERY, searchView.query.toString())
    }


    companion object {
        private const val SEARCH_DELAY: Long = 300
        private const val QUERY: String = "query"
    }
}

