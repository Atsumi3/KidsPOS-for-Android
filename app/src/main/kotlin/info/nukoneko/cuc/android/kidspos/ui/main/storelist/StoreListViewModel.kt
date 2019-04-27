package info.nukoneko.cuc.android.kidspos.ui.main.storelist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.nukoneko.cuc.android.kidspos.ext.CoroutinePlugin
import info.nukoneko.cuc.android.kidspos.extensions.mutableLiveDataOf
import info.nukoneko.cuc.android.kidspos.model.api.APIService
import info.nukoneko.cuc.android.kidspos.model.api.RequestStatus
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import info.nukoneko.cuc.android.kidspos.util.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class StoreListViewModel(
        private val api: APIService,
        private val config: Config) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = CoroutinePlugin.mainDispatcher

    private val _data = MutableLiveData<List<Store>>()
    val data: LiveData<List<Store>> = _data

    private val _presentErrorAlert = MutableLiveData<String>()
    val presentErrorAlert: LiveData<String> = _presentErrorAlert

    private val _dismissView = MutableLiveData<Unit>()
    val dismissView: LiveData<Unit> = _dismissView

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int> = _progressVisibility
    private val _listVisibility = mutableLiveDataOf(View.GONE)
    val listVisibility: LiveData<Int> = _listVisibility
    private val _errorButtonVisibility = mutableLiveDataOf(View.GONE)
    val errorButtonVisibility: LiveData<Int> = _errorButtonVisibility

    private var listFetchRequestStatus: RequestStatus<List<Store>> = RequestStatus.IDLE()
        set(value) {
            field = value
            when (value) {
                is RequestStatus.IDLE -> {
                    _progressVisibility.postValue(View.GONE)
                    _listVisibility.postValue(View.GONE)
                    _errorButtonVisibility.postValue(View.GONE)
                }
                is RequestStatus.REQUESTING -> {
                    _progressVisibility.postValue(View.VISIBLE)
                    _listVisibility.postValue(View.GONE)
                    _errorButtonVisibility.postValue(View.GONE)
                }
                is RequestStatus.SUCCESS -> {
                    _progressVisibility.postValue(View.GONE)
                    _data.postValue(value.value)
                    if (value.value.isNotEmpty()) {
                        _listVisibility.postValue(View.VISIBLE)
                        _errorButtonVisibility.postValue(View.GONE)
                    } else {
                        _listVisibility.postValue(View.GONE)
                        _errorButtonVisibility.postValue(View.VISIBLE)
                    }
                }
                is RequestStatus.FAILURE -> {
                    _progressVisibility.postValue(View.GONE)
                    _errorButtonVisibility.postValue(View.VISIBLE)
                    _presentErrorAlert.postValue(value.error.localizedMessage)
                }
            }
        }

    fun onResume() {
        fetchStores()
    }

    fun onSelect(store: Store) {
        config.currentStore = store
        _dismissView.postValue(Unit)
    }

    fun onReload(@Suppress("UNUSED_PARAMETER") view: View?) {
        fetchStores()
    }

    fun onClose(@Suppress("UNUSED_PARAMETER") view: View?) {
        _dismissView.postValue(Unit)
    }

    private fun fetchStores() {
        if (listFetchRequestStatus is RequestStatus.REQUESTING) {
            return
        }
        listFetchRequestStatus = RequestStatus.REQUESTING()
        launch {
            try {
                listFetchRequestStatus = RequestStatus.SUCCESS(requestFetchStores())
            } catch (e: Throwable) {
                listFetchRequestStatus = RequestStatus.FAILURE(e)
            }
        }
    }

    private suspend fun requestFetchStores() = withContext(CoroutinePlugin.ioDispatcher) {
        api.fetchStoresAsync()
    }.await()
}