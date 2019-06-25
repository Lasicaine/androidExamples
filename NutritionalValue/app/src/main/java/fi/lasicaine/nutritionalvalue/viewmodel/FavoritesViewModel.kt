package fi.lasicaine.nutritionalvalue.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fi.lasicaine.nutritionalvalue.data.db.AppDatabase
import fi.lasicaine.nutritionalvalue.data.db.DB
import fi.lasicaine.nutritionalvalue.data.db.dbScope
import fi.lasicaine.nutritionalvalue.model.Food
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(app: Application): AndroidViewModel(app) {

    private val dao by lazy { AppDatabase.getInstance(getApplication()).favoritesDao() }

    suspend fun getFavorites(): LiveData<List<Food>> = withContext(DB) { dao.loadAll() }

    suspend fun getAllIds(): List<String> = withContext(DB) { dao.loadAllIds() }

    fun add(favorite: Food) = dbScope.launch { dao.insert(favorite) }

    fun delete(favorite: Food) = dbScope.launch { dao.delete(favorite) }
}