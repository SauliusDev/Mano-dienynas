package com.sunnyoaklabs.manodienynas.domain.model

data class Term(
    val id: Long,
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
)
