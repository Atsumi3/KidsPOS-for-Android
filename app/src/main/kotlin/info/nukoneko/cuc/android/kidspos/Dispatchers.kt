package info.nukoneko.cuc.android.kidspos

import info.nukoneko.cuc.android.kidspos.ext.CoroutinePlugin

object Dispatchers {
    val Main get() = CoroutinePlugin.mainDispatcher
    val IO get() = CoroutinePlugin.ioDispatcher
    val Default get() = CoroutinePlugin.defaultDispatcher
}