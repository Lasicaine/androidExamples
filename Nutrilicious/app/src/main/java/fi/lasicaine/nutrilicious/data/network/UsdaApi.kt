package fi.lasicaine.nutrilicious.data.network

import fi.lasicaine.nutrilicious.data.network.dto.*
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

    @GET("V2/reports?format=json")
    fun getDetails(
        @Query("ndbno") id: String,
        @Query("type") detailsType: Char = 'b'
    ): Call<DetailsWrapper<DetailsDto>>
}