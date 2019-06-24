package fi.lasicaine.nutrilicious.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fi.lasicaine.nutrilicious.data.DetailsRepository
import fi.lasicaine.nutrilicious.model.FoodDetails

class DetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = DetailsRepository(app)
    suspend fun getDetails(foodId: String): FoodDetails? = repo.getDetails(foodId)
}