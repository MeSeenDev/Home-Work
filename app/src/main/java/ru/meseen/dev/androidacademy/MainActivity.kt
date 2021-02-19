package ru.meseen.dev.androidacademy

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import ru.meseen.dev.androidacademy.data.base.query.impl.SearchViewQuery
import ru.meseen.dev.androidacademy.fragments.FragmentMoviesList
import ru.meseen.dev.androidacademy.fragments.SearchFragment
import ru.meseen.dev.androidacademy.support.FragmentsTags

class MainActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.add(
                R.id.activity_main_frame,
                FragmentMoviesList.getInstance(application),
                FragmentsTags.MOVIE_LIST_TAG.toString()
            ).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(textListener)
        return super.onCreateOptionsMenu(menu)
    }

    private val textListener = object :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            Log.d(TAG, "onQueryTextSubmit: $query")

            val redactorBottomSheet = SearchFragment.getInstance(
                application,
                SearchViewQuery(
                    path = "movie",
                    query = query ?: "Star",
                    language = "ru-RU",
                    region = "RU"
                )
            )
            searchView.clearFocus()
            searchView.setQuery("",true)
            searchView.onActionViewCollapsed()

            redactorBottomSheet.show(
                supportFragmentManager,
                FragmentsTags.SEARCH_VIEW_TAG.toString()
            )

            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean { // после enter
            Log.d(TAG, "onQueryTextChange: $newText")


            return true
        }

    }





}

