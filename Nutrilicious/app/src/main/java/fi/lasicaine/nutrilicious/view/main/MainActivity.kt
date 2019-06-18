package fi.lasicaine.nutrilicious.view.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import fi.lasicaine.nutrilicious.R
import fi.lasicaine.nutrilicious.data.network.networkScope
import fi.lasicaine.nutrilicious.data.network.usdaApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

import fi.lasicaine.nutrilicious.model.Food
import fi.lasicaine.nutrilicious.view.common.UI
import fi.lasicaine.nutrilicious.view.common.getViewModel
import fi.lasicaine.nutrilicious.viewmodel.SearchViewModel
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_my_foods -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSearchRecyclerView()

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        searchViewModel = getViewModel(SearchViewModel::class)

        networkScope.launch {
            val foods = searchViewModel.getFoodsFor("raw")

            withContext(UI) {
                (rvFoods.adapter as SearchListAdapter).setItems(foods)
            }
        }
    }

    private fun setUpSearchRecyclerView() = with(rvFoods) {
        adapter = SearchListAdapter(emptyList())
        layoutManager = LinearLayoutManager(this@MainActivity)
        addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        setHasFixedSize(true)
    }

}