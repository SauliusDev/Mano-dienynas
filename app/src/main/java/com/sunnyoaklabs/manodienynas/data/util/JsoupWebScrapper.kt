package com.sunnyoaklabs.manodienynas.data.util

import android.util.Log
import com.sunnyoaklabs.manodienynas.domain.model.*
import org.jsoup.Jsoup

class JsoupWebScrapper(): WebScrapper {

    override fun toUser(html: String): User {
        val document = Jsoup.parse(html)
        val element = document.getElementById("act-user-info-text")
        val name = element.getElementsByTag("a").attr("title")
        val role = element.getElementsByTag("span").attr("title")
        val elementSchoolWrapper = document.getElementsByClass("school-wrapper")
        val schoolsInfo: MutableList<SchoolInfo> = mutableListOf()
        val elementSchoolsDetails = document.getElementsByClass("additional-school-user-details")
        for (i in elementSchoolWrapper.indices) {
            val roleName = elementSchoolsDetails[i].getElementsByClass("role-name").text()
            val schoolName = elementSchoolsDetails[i].getElementsByClass("additional-school-name overflowed-text ").text()
            val schoolId = elementSchoolWrapper[i].attr("data-school_id")
            schoolsInfo.add(SchoolInfo(roleName, schoolName, schoolId))
        }
        return User(
            name = name,
            role = role,
            schoolsNames = schoolsInfo
        )
    }

    override fun toEvents(html: String): List<Event> {
        val document = Jsoup.parse(html)
        val eventsList: MutableList<Event> = mutableListOf()
        val elementAllEvents = document.getElementsByClass("row event-row")
        val elementEventItems = elementAllEvents[0].getElementsByAttributeValueContaining("class","panel panel-default event-holder")
        for (i in elementEventItems.indices) {
            val elementEventItem = elementEventItems[i]
            val title = elementEventItem.getElementsByClass("trigger")[0].text()
            val pupilInfo = elementEventItem.getElementsByClass("pupilInfo")[0].text()
            val createDate = elementEventItem.getElementsByClass("pull-right")[0].text()
            val createDateText = elementEventItem.getElementsByClass("create-date")[0].text()
            val eventHeader = elementEventItem.getElementsByClass("event-header")[0].text()
            val eventText = if (title == "Atsiskaitymai") { elementEventItem.getElementsByClass("event-text")[0].removeClass("btn btn-default pull-right").text() }
            else { elementEventItem.getElementsByClass("event-text")[0].getElementsByTag("span").text() }
            val creatorName = elementEventItem.getElementsByTag("h4").text()
            eventsList.add(Event(title, pupilInfo, createDate, createDateText, eventHeader, eventText, creatorName))
        }
        return eventsList
    }

    override fun toMarks(html: String): List<Mark> {
        val document = Jsoup.parse(html)
        val markList: MutableList<Mark> = mutableListOf()
        val elementPupilRows = document.getElementById("pupilListTable")
        val elementMarkRows = document.getElementById("marksScroll")
        val elementPupils = elementPupilRows.getElementsByTag("tr")
        val elementRows = elementMarkRows.getElementsByTag("tr")
        elementPupils.removeFirst()
        elementRows.removeFirst()
        elementRows.removeFirst()
        for (i in elementPupils.indices) {
            val elementMarks = elementRows[i].getElementsByTag("td")
            val lesson = elementPupils[i].getElementsByClass("noTextWrap setRowHeight listSetHeight mark_subject").attr("title")
            val teacher = elementPupils[i].getElementsByTag("a").text()
            val average = elementMarks.last().lastElementSibling().getElementsByTag("a").text()
            val markEventList: MutableList<MarkEvent> = mutableListOf()
            for (h in elementMarks.indices) {
                val elementMark = elementMarks[h]
                val mark = elementMark.getElementsByClass("span-mark-value wd-type-1").text()
                val info = elementMark.getElementsByClass("span-mark-info wd-type-1").text()
                val infoUrl = elementMark.getElementsByTag("input").attr("value")
                if (mark.isNotBlank()) {
                    markEventList.add(MarkEvent("", mark, infoUrl))
                }
                if (info.isNotBlank()) {
                    markEventList.add(MarkEvent("", info, infoUrl))
                }
            }
            markList.add(Mark(lesson, teacher, average, markEventList))
        }
        return markList
    }

    override fun toAttendance(html: String): List<Attendance> {
        val document = Jsoup.parse(html)
        val attendanceList: MutableList<Attendance> = mutableListOf()
        val elementPupilsTable = document.getElementById("pupilListTable")
        val elementAttendanceTable = document.getElementById("marksScroll")
        val elementPupilsRows = elementPupilsTable.getElementsByTag("tr")
        val elementAttendanceRows = elementAttendanceTable.getElementsByTag("tr")
        elementPupilsRows.removeFirst()
        elementAttendanceRows.removeAt(1)
        for (i in elementPupilsRows.indices) {
            val elementPupilRow = elementPupilsRows[i]
            val elementAttendanceRow = elementAttendanceRows[i+1]
            val lessonTitleUnformatted = elementPupilRow.getElementsByClass("noTextWrap setRowHeight listSetHeight mark_subject").text()
            val teacher = elementPupilRow.getElementsByTag("a").text()
            val lessonTitle = lessonTitleUnformatted.substring(0, lessonTitleUnformatted.length-teacher.length)
            val attendanceItemList: MutableList<Int> = mutableListOf()
            val attendanceRangeList: MutableList<AttendanceRange> = mutableListOf()
            val elementAttendanceRanges = elementAttendanceRows[0].getElementsByTag("th")
            for (h in elementAttendanceRanges.indices) {
                val attendanceUnformatted = elementAttendanceRanges[h].text()
                val isShown = elementAttendanceRanges[h].attr("style")
                if (attendanceUnformatted != "Visi metai" && isShown != "display: none;") {
                    val attendanceRange = attendanceUnformatted.split("(")[0]
                    val attendanceRangeDate = "("+attendanceUnformatted.split("(")[1]
                    attendanceRangeList.add(AttendanceRange(attendanceRange, attendanceRangeDate))
                } else {
                    attendanceRangeList.add(AttendanceRange(attendanceUnformatted, attendanceUnformatted))
                }
            }
            val elementAttendanceRowSingle = elementAttendanceRow.getElementsByTag("td")
            for (h in elementAttendanceRowSingle.indices) {
                if (elementAttendanceRowSingle[h].attr("style") != "display: none;") {
                    attendanceItemList.add(elementAttendanceRowSingle[h].text().toInt())
                }
            }
            attendanceList.add(Attendance(lessonTitle, teacher, attendanceItemList, attendanceRangeList))
        }
        return attendanceList
    }

    override fun toClassWork(html: String): List<ClassWork> {
        val document = Jsoup.parse(html)
        val classWorkList: MutableList<ClassWork> = mutableListOf()
        val elementClassWorkTable = document.getElementsByClass("classhomework_table fullWidth hoverTr")
        val elementClassWorkItems = elementClassWorkTable[0].getElementsByClass("simple_info_block")
        for (i in elementClassWorkItems.indices) {
            val elementClassWorkItem = elementClassWorkItems[i]
            val elements = elementClassWorkItem.getElementsByTag("td")
            val date = elements[0].text().split(" ")
            val month = date[0]
            val monthDay = elements[0].getElementsByClass("month_day").text()
            val weekDay = date[2]
            val lesson = elements[1].text()
            val teacher = elements[2].text()
            val description = elements[3].getElementsByTag("p").text()
            val dateAddition = elements[5].text()
            val attachmentsUrl = elements[4].getElementsByTag("a").attr("href")
            classWorkList.add(ClassWork(month, monthDay, weekDay, lesson, teacher, description, dateAddition, attachmentsUrl))
        }
        return classWorkList
    }

    override fun toHomeWork(html: String): List<HomeWork> {
        val document = Jsoup.parse(html)
        val homeWorkList: MutableList<HomeWork> = mutableListOf()
        val elementHomeWorkTable = document.getElementsByClass("classhomework_table fullWidth hoverTr")
        val elementHomeWorkItems = elementHomeWorkTable[0].getElementsByClass("simple_info_block")
        for (i in elementHomeWorkItems.indices) {
            val elementHomeWorkItem = elementHomeWorkItems[i]
            val elements = elementHomeWorkItem.getElementsByTag("td")
            val date = elements[0].text().split(" ")
            val month = date[0]
            val monthDay = elements[0].getElementsByClass("month_day").text()
            val weekDay = date[2]
            val lesson = elements[1].text()
            val teacher = elements[2].text()
            val description = elements[3].getElementsByTag("p").text()
            val dateAddition = elements[5].text()
            val attachmentsUrl = elements[4].getElementsByTag("a").attr("href")
            homeWorkList.add(HomeWork(month, monthDay, weekDay, lesson, teacher, description, dateAddition, attachmentsUrl))
        }
        return homeWorkList
    }

    override fun toControlWork(html: String): List<ControlWork> {
        val document = Jsoup.parse(html)
        val controlWorkList: MutableList<ControlWork> = mutableListOf()
        val elementControlWorkTable = document.getElementById("cWorksListTable")
        val elementControlWorks = elementControlWorkTable.getElementsByTag("tr")
        elementControlWorks.removeFirst()
        for (i in elementControlWorks.indices) {
            val elementControlWork = elementControlWorks[i]
            val elements = elementControlWork.getElementsByTag("td")
            val index = elements[0].text()
            val date = elements[1].text()
            val group = elements[3].text()
            val theme = elements[2].text()
            val description = elements[4].getElementsByClass("cw-desc").text()
            val dateAddition = elements[5].text()
            controlWorkList.add(ControlWork(index, date, group, theme, description, dateAddition))
        }
        return controlWorkList
    }

    override fun toTerm(html: String): List<Term> {
        val document = Jsoup.parse(html)
        val termList: MutableList<Term> = mutableListOf()

        return termList
    }

    override fun toMessages(html: String): List<Message> {
        val document = Jsoup.parse(html)
        val messageList: MutableList<Message> = mutableListOf()

        return messageList
    }

    override fun toMessagesIndividual(html: String): List<MessageIndividual> {
        val document = Jsoup.parse(html)
        val messageIndividualList: MutableList<MessageIndividual> = mutableListOf()

        return messageIndividualList
    }

    override fun toHoliday(html: String): List<Holiday> {
        val document = Jsoup.parse(html)
        val holidayList: MutableList<Holiday> = mutableListOf()

        return holidayList
    }

    override fun toParentMeeting(html: String): List<ParentMeeting> {
        val document = Jsoup.parse(html)
        val parentMeetingList: MutableList<ParentMeeting> = mutableListOf()

        return parentMeetingList
    }

    override fun toSchedule(html: String): List<Schedule> {
        val document = Jsoup.parse(html)
        val scheduleList: MutableList<Schedule> = mutableListOf()

        return scheduleList
    }

    override fun toCalendar(html: String): List<Calendar> {
        val document = Jsoup.parse(html)
        val calendarList: MutableList<Calendar> = mutableListOf()

        return calendarList
    }

}