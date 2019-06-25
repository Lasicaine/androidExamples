package fi.lasicaine.nutritionalvalue.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fi.lasicaine.nutritionalvalue.BuildConfig
import fi.lasicaine.nutritionalvalue.model.Food
import fi.lasicaine.nutritionalvalue.model.FoodDetails
import kotlinx.coroutines.CoroutineScope

val dbScope = CoroutineScope(DB)

@Database(entities = [Food::class, FoodDetails::class], version =1)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(ctx: Context): AppDatabase {
            if (INSTANCE == null) { INSTANCE = buildDatabase(ctx) }
            return INSTANCE!!
        }

        private fun buildDatabase(ctx: Context) = Room
            .databaseBuilder(ctx, AppDatabase::class.java, "NutritionValueDB")
            .apply { if (BuildConfig.DEBUG) fallbackToDestructiveMigration() }
            .build()
    }

    abstract fun favoritesDao(): FavoritesDao
    abstract fun detailsDao(): DetailsDao
}