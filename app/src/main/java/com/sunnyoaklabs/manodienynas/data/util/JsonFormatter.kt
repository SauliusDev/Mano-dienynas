package com.sunnyoaklabs.manodienynas.data.util

import com.sunnyoaklabs.manodienynas.core.util.Resource
import java.util.concurrent.Flow

interface JsonFormatter {

    fun <T> fromMarksJson(json: String): Flow<Resource<List<>>>
}