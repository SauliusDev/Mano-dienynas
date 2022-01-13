package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.TermLegend

data class TermLegendDto(
    val abbreviation: String,
    val description: String
) {
    fun toTermLegend(): TermLegend {
        return TermLegend(
            abbreviation = abbreviation,
            description = description
        )
    }
}
