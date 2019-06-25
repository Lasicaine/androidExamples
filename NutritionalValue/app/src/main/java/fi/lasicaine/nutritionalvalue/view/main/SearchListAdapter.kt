package fi.lasicaine.nutritionalvalue.view.main

import androidx.recyclerview.widget.RecyclerView
import fi.lasicaine.nutritionalvalue.R
import fi.lasicaine.nutritionalvalue.model.Food
import android.view.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_item.*

class SearchListAdapter(
    private var items: List<Food>,
    private val onItemClick: (Food) -> Unit,
    private var onStarClick: (Food, Int) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    fun setItems(newItems: List<Food>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(food: Food) {
            tvFoodName.text = food.name
            tvFoodType.text = food.type

            val image = if (food.isFavorite) {
                R.drawable.favorite_heart
            } else {
                R.drawable.add_to_favorite_heart
            }
            ivStar.setImageResource(image)
            ivStar.setOnClickListener { onStarClick(food, this.layoutPosition) }
            containerView.setOnClickListener { onItemClick(food) }
        }
    }
}