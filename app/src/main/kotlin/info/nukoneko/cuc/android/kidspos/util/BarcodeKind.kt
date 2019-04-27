package info.nukoneko.cuc.android.kidspos.util

enum class BarcodeKind(val prefix: String) {
    STAFF("00"),
    ITEM("01"),
    SALE("02"),
    UNKNOWN("99");

    companion object {
        fun prefixOf(prefix: String?): BarcodeKind {
            return values().singleOrNull { it.prefix == prefix } ?: UNKNOWN
        }
    }
}
//1001000000