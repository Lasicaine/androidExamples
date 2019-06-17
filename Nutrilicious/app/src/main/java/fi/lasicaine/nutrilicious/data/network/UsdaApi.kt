package fi.lasicaine.nutrilicious.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UsdaApi {
    @GET("search?format=json")
    fun getFoods(
        @Query("q") searchTerm: String,
        @Query("sort") sortBy: Char ='r',
        @Query("ds") dataSource: String = "Standard Reference",
        @Query("offset") offset: Int = 0
    ): Call<ResponseBody>
}