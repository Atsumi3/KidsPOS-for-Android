package info.nukoneko.cuc.android.kidspos.event

import info.nukoneko.cuc.android.kidspos.model.entity.Item
import info.nukoneko.cuc.android.kidspos.model.entity.Staff

sealed class BarcodeEvent : Event {
    data class ReadReceiptFailed(val error: Throwable) : BarcodeEvent()
    data class ReadItemSuccess(val item: info.nukoneko.cuc.android.kidspos.model.entity.Item) : BarcodeEvent()
    data class ReadItemFailed(val error: Throwable) : BarcodeEvent()
    data class ReadStaffSuccess(val staff: info.nukoneko.cuc.android.kidspos.model.entity.Staff) : BarcodeEvent()
    data class ReadStaffFailed(val error: Throwable) : BarcodeEvent()
}