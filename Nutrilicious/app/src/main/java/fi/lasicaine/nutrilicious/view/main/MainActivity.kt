package fi.lasicaine.nutrilicious.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fi.lasicaine.nutrilicious.R
import fi.lasicaine.nutrilicious.model.Food
import fi.lasicaine.nutrilicious.view.common.addFragmentToState
import fi.lasicaine.nutrilicious.view.common.getViewModel
import kotlinx.android.synthetic.main.activity_main.*
import fi.lasicaine.nutrilicious.view.common.replaceFragment
import fi.lasicaine.nutrilicious.view.common.toast
import fi.lasicaine.nutrilicious.viewmodel.FavoritesViewModel

private const val SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT"

class MainActivity : AppCompatActivity() {

    private lateinit var searchFragment: SearchFragment

    private lateinit var favoritesViewModel: FavoritesViewModel

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                replaceFragment(R.id.mainView, searchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my_foods -> {
                replaceFragment(R.id.mainView, FavoritesFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun setUpRecyclerView(rv: RecyclerView, list: List<Food> = emptyList()) {
        with(rv) {
            adapter = setUpSearchListAdapter(rv, list)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context, LinearLayoutManager.VERTICAL
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun setUpSearchListAdapter(rv: RecyclerView, items: List<Food>) = SearchListAdapter(
        items,
        onStarClick = { food, layoutPosition ->
            toggleFavorite(food)
            rv.adapter?.notifyItemChanged(layoutPosition)
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recoverOrBuildSearchFragment()
        replaceFragment(R.id.mainView, searchFragment)

        favoritesViewModel = getViewModel(FavoritesViewModel::class)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun toggleFavorite(food: Food) {
        val wasFavoriteBefore = food.isFavorite
        food.isFavorite = food.isFavorite.not()

        if (wasFavoriteBefore) {
            favoritesViewModel.delete(food)
            toast("Removed ${food.name} from your favorites.")
        } else {
            favoritesViewModel.add(food)
            toast("Added ${food.name} as a new favorite of yours!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY) ?: return
            searchFragment.updateListFor(query)
        }
    }

    private fun recoverOrBuildSearchFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG) as? SearchFragment
        if (fragment == null) {
            setUpSearchFragment()
        } else {
            searchFragment = fragment
        }
    }

    private fun setUpSearchFragment() {
        searchFragment = SearchFragment()
        addFragmentToState(R.id.mainView, searchFragment, SEARCH_FRAGMENT_TAG)
    }

}