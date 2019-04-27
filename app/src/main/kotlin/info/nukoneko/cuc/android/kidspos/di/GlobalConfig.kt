package info.nukoneko.cuc.android.kidspos.di

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import info.nukoneko.cuc.android.kidspos.event.EventBus
import info.nukoneko.cuc.android.kidspos.event.SystemEvent
import info.nukoneko.cuc.android.kidspos.model.entity.Staff
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import info.nukoneko.cuc.android.kidspos.util.Config
import info.nukoneko.cuc.android.kidspos.util.Config.Companion.DEFAULT_SERVER_INFO
import info.nukoneko.cuc.android.kidspos.util.Mode

open class GlobalConfig(context: Context, private val eventBus: EventBus) : Config {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    private val gson = GsonBuilder().create()

    override var currentServerAddress: String
        get() = preference.getString(
                KEY_SERVER_INFO,
                DEFAULT_SERVER_INFO
        ) ?: DEFAULT_SERVER_INFO
        set(value) {
            preference.edit().putString(KEY_SERVER_INFO, value).apply()
            eventBus.post(SystemEvent.ServerAddressChanged(value))
        }

    override var currentRunningMode: Mode
        get() = Mode.nameOf(preference.getString(
                KEY_RUNNING_MODE,
                Mode.PRACTICE.name
        ))
        set(value) {
            preference.edit().putString(KEY_RUNNING_MODE, value.name).apply()
            eventBus.post(SystemEvent.RunningModeChanged(value))
        }

    override var currentStore: Store?
        get() {
            return preference.getString(KEY_STORE, null)?.let {
                return gson.fromJson(it, Store::class.java)
            }
        }
        set(value) {
            preference.edit().putString(
                    KEY_STORE,
                    gson.toJson(value)
            ).apply()
            eventBus.post(SystemEvent.SelectShop(value))
        }

    override var currentStaff: Staff?
        get() {
            return preference.getString(KEY_STAFF, null)?.let {
                return gson.fromJson(it, Staff::class.java)
            }
        }
        set(value) {
            preference.edit().putString(
                    KEY_STAFF,
                    gson.toJson(value)
            ).apply()
        }

    companion object {
        const val KEY_SERVER_INFO = "setting_server_info"
        const val KEY_RUNNING_MODE = "setting_running_mode"
        const val KEY_STORE = "store"
        const val KEY_STAFF = "staff"
    }
}