package info.nukoneko.cuc.android.kidspos.extensions

import androidx.lifecycle.MutableLiveData

fun <T : Any?> mutableLiveDataOf(defaultValue: T): MutableLiveData<T> {
    return MutableLiveData<T>().also {
        it.value = defaultValue
    }
}