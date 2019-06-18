package fi.lasicaine.nutrilicious.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors


val NETWORK = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

val networkScope = CoroutineScope(NETWORK)