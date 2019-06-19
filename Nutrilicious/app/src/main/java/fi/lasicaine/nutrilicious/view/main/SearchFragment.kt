package fi.lasicaine.nutrilicious.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fi.lasicaine.nutrilicious.R
import fi.lasicaine.nutrilicious.model.Food
import fi.lasicaine.nutrilicious.view.common.UI
import fi.lasicaine.nutrilicious.view.common.bgScope
import fi.lasicaine.nutrilicious.viewmodel.SearchViewModel
import fi.lasicaine.nutrilicious.view.common.getViewModel
import fi.lasicaine.nutrilicious.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var favoritesViewModel: FavoritesViewModel

    private var lastSearch = ""

    private var lastResults = emptyList<Food>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        searchViewModel = getViewModel(SearchViewModel::class)
        favoritesViewModel = getViewModel(FavoritesViewModel::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearchRecyclerView()
        setUpSwipeRefresh()
        (rvFoods?.adapter as? SearchListAdapter)?.setItems(lastResults)
    }

    private fun setUpSearchRecyclerView() {
        (activity as? MainActivity)?.setUpRecyclerView(rvFoods)
    }

    private fun setUpSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            updateListFor(lastSearch)
        }
    }

    fun updateListFor(searchTerm: String) {
        lastSearch = searchTerm
        swipeRefresh?.isRefreshing = true

        bgScope.launch {
            val favoritesIds: List<String> = favoritesViewModel.getAllIds()
            val foods: List<Food> = searchViewModel.getFoodsFor(searchTerm)
                .onEach { if (favoritesIds.contains(it.id)) it.isFavorite = true }
            lastResults = foods

            withContext(UI) {
                (rvFoods?.adapter as? SearchListAdapter)?.setItems(foods)
                swipeRefresh?.isRefreshing = false
            }
        }
    }
}