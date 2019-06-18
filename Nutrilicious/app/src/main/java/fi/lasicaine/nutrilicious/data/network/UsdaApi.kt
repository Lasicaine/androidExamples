package fi.lasicaine.nutrilicious.data.network

import fi.lasicaine.nutrilicious.data.network.dto.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UsdaApi {

    @GET("search?format=json")
    fun getFoods(
        @Query("q") searchTerm: String,     // Only non-optional parameter
        @Query("sort") sortBy: Char = 'r',  // Sorts by relevance by default
        @Query("ds") dataSource: String = "Standard Reference",
        @Query("offset") offset: Int = 0
    ): Call<SearchWrapper<List<FoodDto>>>                   // Allows to retrieve raw JSON for now
}