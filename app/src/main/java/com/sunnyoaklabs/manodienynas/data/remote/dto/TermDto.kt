package com.sunnyoaklabs.manodienynas.data.remote.dto

import com.sunnyoaklabs.manodienynas.domain.model.Term

data class TermDto(
    val subject: String,
    val abbreviationMarks: String,
    val abbreviationMissedLessons: String,
    val average: String,
    val derived: String,
    val credit: String,
    val additionalWorks: String,
    val exams: String,
    val yearDescription: String,
    val yearMark: String,
    val yearAdditionalWorks: String,
    val yearExams: String
) {
    fun toTerm(): Term {
        return Term(
            subject = subject,
            abbreviationMarks = abbreviationMarks,
            abbreviationMissedLessons = abbreviationMissedLessons,
            average = average,
            derived = derived,
            credit = credit,
            additionalWorks = additionalWorks,
            exams = exams,
            yearDescription = yearDescription,
            yearMark = yearMark,
            yearAdditionalWorks = yearAdditionalWorks,
            yearExams = yearExams
        )
    }
}
