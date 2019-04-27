package info.nukoneko.cuc.android.kidspos.model.api

sealed class RequestStatus<T> {
    class IDLE<T> : RequestStatus<T>()
    class REQUESTING<T> : RequestStatus<T>()
    data class SUCCESS<T>(val value: T) : RequestStatus<T>()
    data class FAILURE<T>(val error: Throwable) : RequestStatus<T>()
}