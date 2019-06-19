package fi.lasicaine.nutrilicious.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import fi.lasicaine.nutrilicious.model.Food

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun loadAll(): LiveData<List<Food>>

    @Query("SELECT id FROM favorites")
    fun loadAllIds(): List<String>

    @Insert(onConflict = IGNORE)
    fun insert(food: Food)

    @Delete
    fun delete(food: Food)
}