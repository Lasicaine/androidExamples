package fi.lasicaine.nutrilicious.view.common

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlin.reflect.KClass

fun <T: ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T {
    return ViewModelProviders.of(this).get(modelClass.java)
}

fun <T: ViewModel> Fragment.getViewModel(modelClass: KClass<T>): T {
    return ViewModelProviders.of(this).get(modelClass.java)
}

fun AppCompatActivity.replaceFragment(viewGroupId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(viewGroupId, fragment)
        .commit()
}

fun AppCompatActivity.addFragmentToState(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
}