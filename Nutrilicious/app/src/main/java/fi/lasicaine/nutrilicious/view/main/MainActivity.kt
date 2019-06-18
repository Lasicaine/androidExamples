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
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

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

        networkScope.launch {
            val dtos = usdaApi.getFoods("raw").execute()?.body()?.list?.item!!
            val foods: List<Food> = dtos.map(::Food)

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