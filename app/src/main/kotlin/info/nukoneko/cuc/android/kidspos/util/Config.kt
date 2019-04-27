package info.nukoneko.cuc.android.kidspos.util

import info.nukoneko.cuc.android.kidspos.model.entity.Staff
import info.nukoneko.cuc.android.kidspos.model.entity.Store

interface Config {
    var currentServerAddress: String
    var currentRunningMode: Mode
    var currentStore: Store?
    var currentStaff: Staff?

    companion object {
        const val DEFAULT_SERVER_INFO = "http://192.168.0.220:8080"
    }
}