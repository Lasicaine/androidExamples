package fi.lasicaine.nutritionalvalue.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fi.lasicaine.nutritionalvalue.data.DetailsRepository
import fi.lasicaine.nutritionalvalue.model.FoodDetails

class DetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = DetailsRepository(app)
    suspend fun getDetails(foodId: String): FoodDetails? = repo.getDetails(foodId)
}