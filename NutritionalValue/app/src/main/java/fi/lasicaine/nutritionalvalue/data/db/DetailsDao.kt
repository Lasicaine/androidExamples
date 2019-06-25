package fi.lasicaine.nutritionalvalue.data.db

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Query
import fi.lasicaine.nutritionalvalue.model.FoodDetails

@Dao
interface DetailsDao {
    @Query("SELECT * FROM details WHERE id = :ndbno")
    fun loadById(ndbno: String): FoodDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: FoodDetails)
}