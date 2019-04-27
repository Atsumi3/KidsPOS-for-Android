package info.nukoneko.cuc.android.kidspos.ui.main.storelist

import android.view.View
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.nukoneko.cuc.android.kidspos.ext.CoroutinePlugin
import info.nukoneko.cuc.android.kidspos.model.api.APIService
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import info.nukoneko.cuc.android.kidspos.util.Config
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StoreListViewModelTest {
    private lateinit var apiService: APIService
    private lateinit var config: Config
    private lateinit var viewModel: StoreListViewModel

    @Before
    fun setup() {
        apiService = mock()
        config = mock()
        viewModel = StoreListViewModel(apiService, config)
        CoroutinePlugin.ioDispatcherHandler = {
            Dispatchers.Unconfined
        }
    }

    @Test
    fun fetchSuccessWithNoData() = runBlocking {
        val dataObserver: Observer<List<Store>> = mock {
            viewModel.data.observeForever(this.mock)
        }
        val errorVisibilityObserver: Observer<Int> = mock {
            viewModel.errorButtonVisibility.observeForever(this.mock)
        }
        val listVisibilityObserver: Observer<Int> = mock {
            viewModel.listVisibility.observeForever(this.mock)
        }
        val response: List<Store> = listOf()
        `when`(apiService.fetchStoresAsync()).thenReturn(CompletableDeferred(response))

        verify(errorVisibilityObserver, times(1)).onChanged(View.GONE)
        verify(listVisibilityObserver, times(1)).onChanged(View.GONE)
        viewModel.onResume()
        verify(errorVisibilityObserver, atLeastOnce()).onChanged(View.VISIBLE)
        verify(listVisibilityObserver, atLeastOnce()).onChanged(View.GONE)
        verify(dataObserver, times(1)).onChanged(response)
    }

    @Test
    fun fetchSuccessWithHasData() = runBlocking {
        val dataObserver: Observer<List<Store>> = mock {
            viewModel.data.observeForever(this.mock)
        }
        val errorVisibilityObserver: Observer<Int> = mock {
            viewModel.errorButtonVisibility.observeForever(this.mock)
        }
        val listVisibilityObserver: Observer<Int> = mock {
            viewModel.listVisibility.observeForever(this.mock)
        }

        val response: List<Store> = listOf(
                Store(0, "Dummy1"),
                Store(1, "Dummy2")
        )
        `when`(apiService.fetchStoresAsync()).thenReturn(CompletableDeferred(response))

        verify(errorVisibilityObserver, times(1)).onChanged(View.GONE)
        verify(listVisibilityObserver, times(1)).onChanged(View.GONE)
        viewModel.onResume()
        verify(errorVisibilityObserver, atLeastOnce()).onChanged(View.GONE)
        verify(listVisibilityObserver, atLeastOnce()).onChanged(View.VISIBLE)
        verify(dataObserver, times(1)).onChanged(response)
    }

    @Test
    fun fetchFailure() = runBlocking {
        val alertMessageObservable: Observer<String> = mock {
            viewModel.presentErrorAlert.observeForever(this.mock)
        }

        val errorMessage = "error message."
        `when`(apiService.fetchStoresAsync()).thenThrow(Error(errorMessage))
        viewModel.onResume()
        verify(alertMessageObservable, times(1)).onChanged(errorMessage)
    }

    @Test
    fun fetchRetry() = runBlocking {
        val listVisibilityObserver: Observer<Int> = mock {
            viewModel.listVisibility.observeForever(this.mock)
        }

        val response: List<Store> = listOf(
                Store(0, "Dummy1"),
                Store(1, "Dummy2")
        )
        `when`(apiService.fetchStoresAsync()).thenReturn(CompletableDeferred(response))
        verify(listVisibilityObserver, atLeastOnce()).onChanged(View.GONE)
        viewModel.onReload(null)
        verify(listVisibilityObserver, atLeastOnce()).onChanged(View.VISIBLE)
    }

    @Test
    fun selectedStore() {
        val dismissViewObserver: Observer<Unit> = mock {
            viewModel.dismissView.observeForever(this.mock)
        }

        val store = Store(1, "Dummy")

        verify(dismissViewObserver, never()).onChanged(ArgumentMatchers.any())
        viewModel.onSelect(store)
        verify(dismissViewObserver, times(1)).onChanged(ArgumentMatchers.any())
        verify(config, times(1)).currentStore = store
    }

    @Test
    fun closeClick() {
        val dismissViewObserver: Observer<Unit> = mock {
            viewModel.dismissView.observeForever(this.mock)
        }

        verify(dismissViewObserver, never()).onChanged(ArgumentMatchers.any())
        viewModel.onClose(null)
        verify(dismissViewObserver, times(1)).onChanged(ArgumentMatchers.any())
    }
}