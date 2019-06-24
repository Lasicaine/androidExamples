package fi.lasicaine.nutrilicious.view.common

import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
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

fun AppCompatActivity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.snackbar(
    msg: String,
    view: View = activity!!.findViewById(android.R.id.content)) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}