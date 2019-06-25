package fi.lasicaine.nutritionalvalue.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import fi.lasicaine.nutritionalvalue.R
import fi.lasicaine.nutritionalvalue.view.common.UI
import fi.lasicaine.nutritionalvalue.view.common.getViewModel
import fi.lasicaine.nutritionalvalue.view.common.uiScope
import fi.lasicaine.nutritionalvalue.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        favoritesViewModel = getViewModel(FavoritesViewModel::class)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeFavorites()
    }

    private fun setUpRecyclerView() {
        (activity as? MainActivity)?.setUpRecyclerView(rvFavorites, emptyList())
    }

    private fun observeFavorites() = uiScope.launch {
        val favorites = favoritesViewModel.getFavorites()
        favorites.observe(this@FavoritesFragment, Observer { foods ->
            foods?.let {
                launch(UI) { (rvFavorites.adapter as? SearchListAdapter)?.setItems(foods) }
            }
        })
    }
}