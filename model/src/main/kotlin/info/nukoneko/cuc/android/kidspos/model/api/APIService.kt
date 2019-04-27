package info.nukoneko.cuc.android.kidspos.model.api

import info.nukoneko.cuc.android.kidspos.model.entity.Item
import info.nukoneko.cuc.android.kidspos.model.entity.Sale
import info.nukoneko.cuc.android.kidspos.model.entity.Staff
import info.nukoneko.cuc.android.kidspos.model.entity.Store
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface APIService {

    @GET("store/list")
    fun fetchStoresAsync(): Deferred<List<Store>>

    @FormUrlEncoded
    @POST("sale/create")
    fun createSaleAsync(
            @Field("storeId") storeId: Int,
            @Field("staffBarcode") staffBarcode: String,
            @Field("deposit") deposit: Int,
            @Field("itemIds") itemIds: String): Deferred<Sale>

    @GET("item/{barcode}")
    fun getItemAsync(@Path("barcode") itemBarcode: String): Deferred<Item>

    @GET("staff/{barcode}")
    fun getStaffAsync(@Path("barcode") staffBarcode: String): Deferred<Staff>

    @GET("setting/status")
    fun getStatusAsync(): Deferred<Unit>
}