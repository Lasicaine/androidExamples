package fi.lasicaine.nutrilicious.data.db

import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

val DB = Executors.newSingleThreadExecutor().asCoroutineDispatcher()