package info.nukoneko.cuc.android.kidspos.ui.main.storelist

import androidx.databinding.Observable
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ItemStoreViewModelTest {

    @Test
    fun shouldGetStoreName() {
        val store = Gson().fromJson("{\"id\": 1, \"name\": \"ぬいぐるみ\"}", info.nukoneko.cuc.android.kidspos.model.entity.Store::class.java)
        val viewModel = ItemStoreListContentViewModel(store, null)
        assertEquals(store.name, viewModel.storeName)
    }

    @Test
    fun shouldNotifyPropertyChangeWhenSetStore() {
        val store = Gson().fromJson("{\"id\": 1, \"name\": \"ぬいぐるみ\"}", info.nukoneko.cuc.android.kidspos.model.entity.Store::class.java)

        val viewModel = ItemStoreListContentViewModel(store, null)
        val mockCallback: Observable.OnPropertyChangedCallback = mock(Observable.OnPropertyChangedCallback::class.java)
        viewModel.addOnPropertyChangedCallback(mockCallback)

        viewModel.setStore(store)
        verify<Observable.OnPropertyChangedCallback>(mockCallback).onPropertyChanged(any(Observable::class.java), anyInt())
    }
}