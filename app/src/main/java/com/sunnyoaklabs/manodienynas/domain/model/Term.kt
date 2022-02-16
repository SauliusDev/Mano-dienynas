package com.sunnyoaklabs.manodienynas.domain.model

data class Term(
    val subject: String,
    val abbreviationMarks: List<String>,
    val abbreviationMissedLessons: List<String>,
    val average: List<String>,
    val derived: List<String>,
    val derivedInfoUrl: List<String>,
    val credit: List<String>,
    val creditInfoUrl: List<String>,
    val additionalWorks: List<String>,
    val exams: List<String>,
    val yearDescription: String,
    val yearMark: String,
    val yearAdditionalWorks: String,
    val yearExams: String,
    val termRange: List<TermRange>,
    val id: Long? = null
)
