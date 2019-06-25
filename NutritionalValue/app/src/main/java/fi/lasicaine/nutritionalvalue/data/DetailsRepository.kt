package fi.lasicaine.nutritionalvalue.data

import android.content.Context
import fi.lasicaine.nutritionalvalue.data.db.AppDatabase
import fi.lasicaine.nutritionalvalue.data.db.DB
import fi.lasicaine.nutritionalvalue.data.db.dbScope
import fi.lasicaine.nutritionalvalue.data.network.NETWORK
import fi.lasicaine.nutritionalvalue.data.network.dto.DetailsDto
import fi.lasicaine.nutritionalvalue.data.network.dto.DetailsWrapper
import fi.lasicaine.nutritionalvalue.data.network.usdaApi
import fi.lasicaine.nutritionalvalue.model.FoodDetails
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class DetailsRepository(ctx: Context) {
    private val detailsDao by lazy { AppDatabase.getInstance(ctx).detailsDao() }

    fun add(details: FoodDetails) = dbScope.launch { detailsDao.insert(details) }

    suspend fun getDetails(id: String): FoodDetails? {
        return withContext(DB) { detailsDao.loadById(id) }
            ?: withContext(NETWORK) { fetchDetailsFromApi(id) }
                .also{ if (it != null) this.add(it) }
    }

    private suspend fun fetchDetailsFromApi(id: String): FoodDetails? {
        val request: Call<DetailsWrapper<DetailsDto>> = usdaApi.getDetails(id)

        val detailsDto: DetailsDto = withContext(NETWORK) {
            request.execute().body()?.foods?.get(0)?.food
        } ?: return null

        return FoodDetails(detailsDto)
    }
}