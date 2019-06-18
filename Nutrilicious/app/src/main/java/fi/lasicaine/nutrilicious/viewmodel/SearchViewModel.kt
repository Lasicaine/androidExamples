package fi.lasicaine.nutrilicious.viewmodel

import androidx.lifecycle.ViewModel
import fi.lasicaine.nutrilicious.data.network.NETWORK
import fi.lasicaine.nutrilicious.data.network.dto.FoodDto
import fi.lasicaine.nutrilicious.data.network.dto.SearchWrapper
import fi.lasicaine.nutrilicious.data.network.usdaApi
import fi.lasicaine.nutrilicious.model.Food
import kotlinx.coroutines.withContext
import retrofit2.Call

class SearchViewModel: ViewModel() {

    suspend fun getFoodsFor(searchTerm: String): List<Food> {
        val request: Call<SearchWrapper<List<FoodDto>>> = usdaApi.getFoods(searchTerm)
        val foodDtos: List<FoodDto> = withContext(NETWORK) { doRequest(request)}
        return foodDtos.map(::Food)
    }

    private fun doRequest(req: Call<SearchWrapper<List<FoodDto>>>): List<FoodDto> =
            req.execute().body()?.list?.item ?: emptyList()
}