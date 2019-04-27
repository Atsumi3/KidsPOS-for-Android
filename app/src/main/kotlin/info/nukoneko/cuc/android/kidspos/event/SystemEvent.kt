package info.nukoneko.cuc.android.kidspos.event

import info.nukoneko.cuc.android.kidspos.model.entity.Sale
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import info.nukoneko.cuc.android.kidspos.util.Mode

sealed class SystemEvent : Event {
    data class SentSaleSuccess(val sale: info.nukoneko.cuc.android.kidspos.model.entity.Sale?) : SystemEvent()

    data class SelectShop(val store: info.nukoneko.cuc.android.kidspos.model.entity.Store?) : SystemEvent()

    data class ServerAddressChanged(val newServerAddress: String) : SystemEvent()

    data class RunningModeChanged(val newRunningMode: Mode) : SystemEvent()
}