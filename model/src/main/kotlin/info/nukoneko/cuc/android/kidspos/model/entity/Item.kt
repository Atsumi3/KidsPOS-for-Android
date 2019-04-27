package info.nukoneko.cuc.android.kidspos.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(val id: Int, val barcode: String,
                val name: String, val price: Int,
                val storeId: Int, val genreId: Int) : Parcelable
