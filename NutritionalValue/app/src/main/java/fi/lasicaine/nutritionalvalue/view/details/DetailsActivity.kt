package fi.lasicaine.nutritionalvalue.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fi.lasicaine.nutritionalvalue.R
import fi.lasicaine.nutritionalvalue.model.FoodDetails
import fi.lasicaine.nutritionalvalue.model.Nutrient
import fi.lasicaine.nutritionalvalue.model.NutrientType
import fi.lasicaine.nutritionalvalue.model.RDI
import fi.lasicaine.nutritionalvalue.view.common.UI
import fi.lasicaine.nutritionalvalue.view.common.bgScope
import fi.lasicaine.nutritionalvalue.view.common.getViewModel
import fi.lasicaine.nutritionalvalue.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val FOOD_ID_EXTRA = "NDBNO"

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        detailsViewModel = getViewModel(DetailsViewModel::class)
        val foodId = intent.getStringExtra(FOOD_ID_EXTRA)
        updateUiWith(foodId)
    }

    private fun updateUiWith(foodId: String) {
        if (foodId.isBlank()) return

        setLoading(true)
        bgScope.launch {
            val details = detailsViewModel.getDetails(foodId)
            withContext(UI) {
                setLoading(false)
                bindUi(details)
            }
        }
    }


    private fun bindUi(details: FoodDetails?) {
        if (details != null) {
            tvFoodName.text = "${details.name} (100g)"
            tvProximates.text = makeSection(details, NutrientType.PROXIMATES)
            tvMinerals.text = makeSection(details, NutrientType.MINERALS)
            tvVitamins.text = makeSection(details, NutrientType.VITAMINS)
            tvLipids.text = makeSection(details, NutrientType.LIPIDS)
        } else {
            tvFoodName.text = getString(R.string.no_data)
        }
    }

    private fun makeSection(details: FoodDetails, forType: NutrientType) =
        details.nutrients.filter { it.type == forType }
            .joinToString(separator = "\n", transform = ::renderNutrient)

    private fun renderNutrient(nutrient: Nutrient): String = with(nutrient) {
        val name = name.substringBefore(",")  // = whole name if no comma
        val amount = amountPer100g.value.render()
        val unit = amountPer100g.unit
        val percent = getPercentOfRdi(nutrient).render()
        val rdiNote = if (percent.isNotEmpty()) "($percent% of RDI)" else ""
        "$name: $amount$unit $rdiNote"
    }

    private fun Double.render() = if (this >= 0.0) "%.2f".format(this) else ""

    private fun getPercentOfRdi(nutrient: Nutrient): Double {
        val nutrientAmount: Double = nutrient.amountPer100g.normalized()
        val rdi: Double = RDI[nutrient.id]?.normalized() ?: return -1.0

        return nutrientAmount / rdi * 100
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            //content.visibility = View.GONE
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
            //content.visibility = View.VISIBLE
        }
    }
}