package fi.lasicaine.nutrilicious.view.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import fi.lasicaine.nutrilicious.R
import kotlinx.android.synthetic.main.activity_main.*

import fi.lasicaine.nutrilicious.model.Food

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
    }

    private fun setUpSearchRecyclerView() = with(rvFoods) {
        adapter = SearchListAdapter(sampleData())
        layoutManager = LinearLayoutManager(this@MainActivity)
        addItemDecoration(DividerItemDecoration(
            this@MainActivity, LinearLayoutManager.VERTICAL
        ))
        setHasFixedSize(true)
    }

    private fun sampleData() = listOf(
        Food("Gingerbread", "Candy and Sweets", false),
        Food("Nougat", "Candy and Sweets", true),
        Food("Apple", "Fruits and Vegetables", false),
        Food("Banana", "Fruits and Vegetables", true)
    )
}
