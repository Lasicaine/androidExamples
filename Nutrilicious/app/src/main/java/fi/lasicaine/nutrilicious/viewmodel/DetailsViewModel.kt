package fi.lasicaine.nutrilicious.viewmodel

import androidx.lifecycle.ViewModel
import fi.lasicaine.nutrilicious.data.network.NETWORK
import fi.lasicaine.nutrilicious.data.network.dto.DetailsDto
import fi.lasicaine.nutrilicious.data.network.dto.DetailsWrapper
import fi.lasicaine.nutrilicious.data.network.usdaApi
import fi.lasicaine.nutrilicious.model.FoodDetails
import kotlinx.coroutines.withContext
import retrofit2.Call

class DetailsViewModel : ViewModel() {

    suspend fun getDetails(foodId: String): FoodDetails? {
        val request: Call<DetailsWrapper<DetailsDto>> = usdaApi.getDetails(foodId)

        val detailsDto: DetailsDto = withContext(NETWORK) {
            request.execute().body()?.foods?.get(0)?.food
        } ?: return null

        return FoodDetails(detailsDto)
    }
}