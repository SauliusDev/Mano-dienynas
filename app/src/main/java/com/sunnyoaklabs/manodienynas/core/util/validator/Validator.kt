package com.sunnyoaklabs.manodienynas.core.util.validator

import android.app.Application

interface Validator {

    fun validateIsLoading(
        isLoadingNetwork: Boolean,
        isLoadingLocal: Boolean,
        dataList: List<Any>,
    ): Boolean

    fun validateIsEmpty(
        isLoadingNetwork: Boolean,
        isLoadingLocal: Boolean,
        dataList: List<Any>,
    ): Boolean

    fun hasInternetConnection(application: Application): Boolean

}