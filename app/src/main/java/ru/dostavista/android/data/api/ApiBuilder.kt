package ru.dostavista.android.data.api

interface ApiBuilder {

    fun <T> createApi(api: Class<T>): T

}
