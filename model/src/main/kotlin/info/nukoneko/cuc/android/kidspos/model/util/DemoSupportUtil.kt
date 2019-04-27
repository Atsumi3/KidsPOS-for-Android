package info.nukoneko.cuc.android.kidspos.model.util

import info.nukoneko.cuc.android.kidspos.model.entity.Item
import info.nukoneko.cuc.android.kidspos.model.entity.Staff
import kotlin.random.Random

object DemoSupportUtil {
    fun createDummyItem(barcode: String): Item {
        return Item(-1, barcode, "Dummy${Random.nextInt(100)}", 100, 1, 1)
    }

    fun createDummyStaff(barcode: String): Staff {
        return Staff(barcode, "Dummy${Random.nextInt(100)}")
    }
}