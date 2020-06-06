package br.eti.arnaud.pokemon

import android.os.Bundle
import android.widget.Toast
import br.eti.arnaud.pokemon.ui.main.MainFragment

class MainActivity : SearchActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSearchTextSubmit(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onSearchTextChange(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


}