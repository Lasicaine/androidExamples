package fi.lasicaine.nutrilicious.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fi.lasicaine.nutrilicious.R
import fi.lasicaine.nutrilicious.data.network.networkScope
import fi.lasicaine.nutrilicious.model.Food
import fi.lasicaine.nutrilicious.view.common.UI
import fi.lasicaine.nutrilicious.viewmodel.SearchViewModel
import fi.lasicaine.nutrilicious.view.common.getViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private var lastSearch = ""

    private var lastResults = emptyList<Food>()  // Import Food!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        searchViewModel = getViewModel(SearchViewModel::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearchRecyclerView()
        setUpSwipeRefresh()
    }

    private fun setUpSearchRecyclerView() = with(rvFoods) {
        adapter = SearchListAdapter(emptyList())
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        setHasFixedSize(true)
    }

    private fun setUpSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            updateListFor(lastSearch)
        }
    }

    fun updateListFor(searchTerm: String) {
        lastSearch = searchTerm
        swipeRefresh?.isRefreshing = true

        networkScope.launch {
            val foods = searchViewModel.getFoodsFor(searchTerm)
            lastResults = foods

            withContext(UI) {
                (rvFoods?.adapter as? SearchListAdapter)?.setItems(foods)
                swipeRefresh?.isRefreshing = false
            }
        }
    }
}