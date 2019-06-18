package fi.lasicaine.nutrilicious.view.common

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlin.reflect.KClass

fun <T: ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T {
    return ViewModelProviders.of(this).get(modelClass.java)
}