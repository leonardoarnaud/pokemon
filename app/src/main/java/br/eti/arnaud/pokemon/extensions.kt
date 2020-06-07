package br.eti.arnaud.pokemon

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.util.*

fun SearchView.setOnQueryTextListener(
    textSubmit: (text: String) -> Unit,
    textChange: (text: String) -> Unit
) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { textSubmit(query) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { textChange(newText) }
            return true
        }
    })
}

fun Timer.schedule(delay: Long, task: () -> Unit) {
    schedule(object : TimerTask() {
        override fun run() {
            task()
        }
    }, delay)
}

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> =
        NonNullMediatorLiveData()
    mediator.addSource(this) {
        it?.let {
            mediator.value = it
        }
    }
    return mediator
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}