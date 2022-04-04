package com.sunnyoaklabs.manodienynas.core.util.validator

class ValidatorImpl : Validator {

    override fun validateIsLoading(
        isLoadingNetwork: Boolean,
        isLoadingLocal: Boolean,
        dataList: List<Any>,
    ): Boolean {
        return isLoadingNetwork
            &&
            (
                (isLoadingLocal && dataList.isEmpty()) || (isLoadingLocal && dataList.isEmpty())
            )
    }

    override fun validateIsEmpty(
        isLoadingNetwork: Boolean,
        isLoadingLocal: Boolean,
        dataList: List<Any>,
    ): Boolean {
        return !isLoadingNetwork && !isLoadingLocal && dataList.isEmpty()
    }

}