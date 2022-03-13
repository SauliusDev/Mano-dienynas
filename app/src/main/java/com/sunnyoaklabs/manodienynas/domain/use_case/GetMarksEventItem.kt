package com.sunnyoaklabs.manodienynas.domain.use_case

import com.sunnyoaklabs.manodienynas.core.util.Resource
import com.sunnyoaklabs.manodienynas.domain.model.MarksEventItem
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetMarksEventItem(
    private val repository: Repository
) {

    operator fun invoke(infoUrl: String): Flow<Resource<MarksEventItem>> {
        return repository.getMarksEventItem(infoUrl)
    }

}