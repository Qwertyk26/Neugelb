package de.neugelb.presentation.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.JsonElement

fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any?, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) =
    viewLifecycleOwner.observe(liveData, body)

fun JsonElement.toValidString(): String? = when {
    this.isJsonPrimitive -> this.asString
    this.isJsonArray -> this.asJsonArray.fold(
        ""
    ) { data, element -> element.toValidString()?.let { data.plus("") } ?: data }
    this.isJsonObject -> this.asJsonObject.toValidString()
    else -> null
}