package com.sunnyoaklabs.manodienynas.data.util

import android.util.Log
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.ATTENDANCE_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.CHANGED_MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.HOMEWORK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.core.util.EventTypes.MARK_EVENT_TYPE
import com.sunnyoaklabs.manodienynas.domain.model.*
import org.jsoup.Jsoup

class JsoupWebScrapper() : WebScrapper {

    override fun toPerson(html: String): Person {
        val document = Jsoup.parse(html)
        val element = document.getElementById("act-user-info-text")
        val name = element.getElementsByTag("a").attr("title")
        val role = element.getElementsByTag("span").attr("title")
        val elementSchoolWrapper = document.getElementsByClass("school-wrapper")
        val schoolsInfo: MutableList<SchoolInfo> = mutableListOf()
        val elementSchoolsDetails = document.getElementsByClass("additional-school-user-details")
        for (i in elementSchoolWrapper.indices) {
            val roleName = elementSchoolsDetails[i].getElementsByClass("role-name").text()
            val schoolName =
                elementSchoolsDetails[i].getElementsByClass("additional-school-name overflowed-text ")
                    .text()
            val schoolId = elementSchoolWrapper[i].attr("data-school_id")
            schoolsInfo.add(SchoolInfo(roleName, schoolName, schoolId))
        }
        return Person(
            name = name,
            role = role,
            schoolsNames = schoolsInfo
        )
    }

    override fun toEvents(html: String): List<Event> {
        val document = Jsoup.parse(html)
        val eventsList: MutableList<Event> = mutableListOf()
        val elementAllEvents = document.getElementsByClass("row event-row")
        val elementEventItems = elementAllEvents[0].getElementsByAttributeValueContaining(
            "class",
            "panel panel-default event-holder"
        )
        for (i in elementEventItems.indices) {
            var isAnnouncement = false
            val elementEventItem = elementEventItems[i]
            val eventId = elementEventItem.attr("id").removePrefix("event_")
            val title = try { elementEventItem.getElementsByClass("trigger")[0].text() }
            catch (e: Exception) {
                isAnnouncement = true
                elementEventItem.getElementsByClass("creator-name")[0].text()
            }
            val pupilInfo = try { elementEventItem.getElementsByClass("pupilInfo")[0].text() } catch (e: Exception) {""}
            val createDate = try {
                if (!isAnnouncement) elementEventItem.getElementsByClass("pull-right")[0].text()
                else ""
            } catch (e: Exception) { "" }
            val createDateText = try { elementEventItem.getElementsByClass("create-date")[0].text() } catch (e: Exception) {""}
            val eventHeader = try { elementEventItem.getElementsByClass("event-header")[0].text() } catch (e: Exception) {""}
            val eventText = try {
                if (title == MARK_EVENT_TYPE || title == HOMEWORK_EVENT_TYPE || title == CHANGED_MARK_EVENT_TYPE || title == ATTENDANCE_EVENT_TYPE) {
                    elementEventItem.getElementsByClass("event-text")[0].getElementsByTag("span").text()
                } else {
                    val thingToRemove = elementEventItem.getElementsByClass("event-text")[0].getElementsByTag("a")[0].text()
                    val item = elementEventItem.getElementsByClass("event-text")[0].text()
                    if (!isAnnouncement) item.substring(0, item.length-thingToRemove.length)
                    else item
                }
            } catch (e: Exception) {
                elementEventItem.getElementsByClass("event-text")[0].text()
            }
            val creatorName = elementEventItem.getElementsByTag("h4").text()
            eventsList.add(
                Event(
                    eventId,
                    title,
                    pupilInfo,
                    createDate,
                    createDateText,
                    eventHeader,
                    eventText,
                    creatorName
                )
            )
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
            val lesson =
                elementPupils[i].getElementsByClass("noTextWrap setRowHeight listSetHeight mark_subject")
                    .attr("title").replace("  ", " ")
            val teacher = elementPupils[i].getElementsByTag("a").text()
            val average = try {
                elementRows[i].getElementsByClass("vidurkioth setRowHeight")[0].text()
            }catch (e: Exception) {
                ""
            }
            val markEventList: MutableList<MarkEvent> = mutableListOf()
            for (h in elementMarks.indices) {
                val elementMark = elementMarks[h]
                val infoUrl = try {
                    elementMark.getElementsByTag("input").attr("value")
                } catch (e: Exception) {
                    ""
                }
                val mark = try {
                    elementMark.getElementsByClass("span-mark-value")[0].getElementsByTag("span")
                } catch (e: Exception) {
                    null
                }
                mark?.let {
                    for (j in mark.indices) {
                        val markText = mark[j].text()
                        if (markText.isNotBlank()) {
                            if (markText.length <= 2 || markText == "įsk" || markText == "nsk" || markText == "atl") {
                                markEventList.add(MarkEvent("", mark[j].text(), infoUrl))
                            }
                        }
                    }
                }
                val info = try {
                    elementMark.getElementsByClass("span-mark-info")[0].getElementsByTag("span")
                } catch (e: Exception) {
                    null
                }
                info?.let {
                    for (j in info.indices) {
                        if (info[j].text().isNotBlank()) {
                            markEventList.add(MarkEvent("", info[j].text(), infoUrl))
                        }
                    }
                }
            }
            markList.add(Mark(lesson, teacher, average, markEventList))
        }
        return markList
    }

    override fun toMarkEventItem(html: String): MarksEventItem {
        val document = Jsoup.parse(html)
        val table = document.getElementsByClass("new_class_sms")[0]
        val items = table.getElementsByTag("tr")
        return MarksEventItem(
            items[0].text(),
            items[1].text(),
            items[2].text(),
            items[3].text(),
            items[4].text()
        )
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
            val elementAttendanceRow = elementAttendanceRows[i + 1]
            val lessonTitleUnformatted =
                elementPupilRow.getElementsByClass("noTextWrap setRowHeight listSetHeight mark_subject")
                    .text()
            val teacher = elementPupilRow.getElementsByTag("a").text()
            val lessonTitle =
                lessonTitleUnformatted.substring(0, lessonTitleUnformatted.length - teacher.length-1)
            val attendanceItemList: MutableList<Int> = mutableListOf()
            val attendanceRangeList: MutableList<AttendanceRange> = mutableListOf()
            val elementAttendanceRanges = elementAttendanceRows[0].getElementsByTag("th")
            for (h in elementAttendanceRanges.indices) {
                val attendanceUnformatted = elementAttendanceRanges[h].text()
                val isShown = elementAttendanceRanges[h].attr("style")
                if (attendanceUnformatted != "Visi metai" && isShown != "display: none;") {
                    val attendanceRange = attendanceUnformatted.split("(")[0]
                    val attendanceRangeDate = "(" + attendanceUnformatted.split("(")[1]
                    attendanceRangeList.add(AttendanceRange(attendanceRange, attendanceRangeDate))
                } else {
                    attendanceRangeList.add(
                        AttendanceRange(
                            attendanceUnformatted,
                            attendanceUnformatted
                        )
                    )
                }
            }
            val elementAttendanceRowSingle = elementAttendanceRow.getElementsByTag("td")
            for (h in elementAttendanceRowSingle.indices) {
                if (elementAttendanceRowSingle[h].attr("style") != "display: none;") {
                    attendanceItemList.add(elementAttendanceRowSingle[h].text().toInt())
                }
            }
            attendanceList.add(
                Attendance(
                    lessonTitle,
                    teacher,
                    attendanceItemList,
                    attendanceRangeList
                )
            )
        }
        return attendanceList
    }

    override fun toClassWork(html: String): List<ClassWork> {
        val document = Jsoup.parse(html)
        val classWorkList: MutableList<ClassWork> = mutableListOf()
        val elementClassWorkTable =
            document.getElementsByClass("classhomework_table fullWidth hoverTr")
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
            classWorkList.add(
                ClassWork(
                    month,
                    monthDay,
                    weekDay,
                    lesson,
                    teacher,
                    description,
                    dateAddition,
                    attachmentsUrl
                )
            )
        }
        return classWorkList
    }

    override fun toHomeWork(html: String): List<HomeWork> {
        val document = Jsoup.parse(html)
        val homeWorkList: MutableList<HomeWork> = mutableListOf()
        val elementHomeWorkTable =
            document.getElementsByClass("classhomework_table fullWidth hoverTr")
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
            val dueDate = elements[4].text()
            val attachmentsUrl = elements[6].getElementsByTag("a").attr("href")
            homeWorkList.add(
                HomeWork(
                    month,
                    monthDay,
                    weekDay,
                    lesson,
                    teacher,
                    description,
                    dateAddition,
                    dueDate,
                    attachmentsUrl
                )
            )
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
        val elementTermTable = document.getElementById("formTable")
        val elementTermRows = elementTermTable.getElementsByTag("tr")
        val elementYearDescriptions = elementTermTable.getElementsByClass("apr")
        elementTermRows.removeAt(1)
        for (i in 1 until elementTermRows.size) {
            val elementTermRow = elementTermRows[i]
            val elements = elementTermRow.getElementsByTag("td")
            val elementsRanges = elementTermRows[0].getElementsByTag("th")
            val termRangeList: MutableList<TermRange> = mutableListOf()
            val subject = elements[0].text()
            val abbreviationMarksList: MutableList<String> = mutableListOf()
            val abbreviationMissedLessonsList: MutableList<String> = mutableListOf()
            val averageList: MutableList<String> = mutableListOf()
            val derivedList: MutableList<String> = mutableListOf()
            val derivedInfoUrlList: MutableList<String> = mutableListOf()
            val creditList: MutableList<String> = mutableListOf()
            val creditInfoUrlList: MutableList<String> = mutableListOf()
            val additionalWorksList: MutableList<String> = mutableListOf()
            val examsList: MutableList<String> = mutableListOf()
            var terms = 0
            for (h in elementsRanges.indices) {
                val termClassName = elementsRanges[h].className()
                val isShown = elementsRanges[h].attr("style")
                if (termClassName == "sem" && isShown != "display: none;") {
                    terms++
                    val termRange = elementsRanges[h].text().split("(")[0]
                    val termDate = "(" + elementsRanges[h].text().split("(")[1]
                    termRangeList.add(TermRange(termRange, termDate))
                } else if (isShown != "display: none;") {
                    termRangeList.add(TermRange(elementsRanges[h].text(), elementsRanges[h].text()))
                }
            }
            for (h in 0 until terms) {
                val unformattedMarksAndLessons =
                    elements[1 + 6 * h].getElementsByTag("span").text().split("Pr")
                val abbreviationMarks = unformattedMarksAndLessons[0]
                val abbreviationMissedLessons = "Pr" + unformattedMarksAndLessons[1]
                val average = elements[2 + 6 * h].text()
                val derived = elements[3 + 6 * h].getElementsByTag("a").text()
                val derivedInfoUrl = if (derived.isNotBlank()) {
                    elements[3 + 6 * h].getElementsByTag("a").attr("onclick").split("'")[1]
                } else {
                    ""
                }
                val credit = elements[4 + 6 * h].getElementsByTag("a").text()
                val creditInfoUrl = if (credit.isNotBlank()) {
                    elements[4 + 6 * h].getElementsByTag("a").attr("onclick").split("'")[1]
                } else {
                    ""
                }
                val additionalWorks = elements[5 + 6 * h].text()
                val exams = elements[6 + 6 * h].text()
                abbreviationMarksList.add(abbreviationMarks)
                abbreviationMissedLessonsList.add(abbreviationMissedLessons)
                averageList.add(average)
                derivedList.add(derived)
                derivedInfoUrlList.add(derivedInfoUrl)
                creditList.add(credit)
                creditInfoUrlList.add(creditInfoUrl)
                additionalWorksList.add(additionalWorks)
                examsList.add(exams)
            }
            val yearDescription = elementYearDescriptions[i].getElementsByTag("span").text()
            val yearMark = elements[elements.lastIndex - 2].text()
            val yearAdditionalWorks = elements[elements.lastIndex - 1].text()
            val yearExams = elements[elements.lastIndex].text()
            termList.add(
                Term(
                    subject,
                    abbreviationMarksList,
                    abbreviationMissedLessonsList,
                    averageList,
                    derivedList,
                    derivedInfoUrlList,
                    creditList,
                    creditInfoUrlList,
                    additionalWorksList,
                    examsList,
                    yearDescription,
                    yearMark,
                    yearAdditionalWorks,
                    yearExams,
                    termRangeList
                )
            )
        }
        return termList
    }

    override fun toMessages(html: String): List<Message> {
        val document = Jsoup.parse(html)
        val messageList: MutableList<Message> = mutableListOf()
        val elementMessagesTable =
            document.getElementsByClass("messageListTable hoverTr hover-table")
        val elementMessages = elementMessagesTable[1].getElementsByTag("tr")
        for (i in elementMessages.indices) {
            val elementMessage = elementMessages[i]
            val elements = elementMessage.getElementsByTag("td")
            val messageId = elements[0].getElementsByTag("input").attr("value")
            val isStarred =
                elements[1].getElementsByTag("a")[0].className() != "messageNotStarredLink"
            val wasSeen = elements[3].getElementsByTag("span")[0].className() == "readMessage"
            val date = elements[3].getElementsByTag("a").text()
            val theme = elements[4].getElementsByTag("a").text()
            val sender = elements[5].getElementById("message-receivers-$messageId").text()
            messageList.add(Message(messageId, isStarred, wasSeen, date, theme, sender))
        }
        return messageList
    }

    override fun toMessagesIndividual(html: String): MessageIndividual {
        val document = Jsoup.parse(html)
        val messageContainer = document.getElementsByClass("messageContainer receivedMessage")[0]
        val messageId =
            document.getElementsByClass("sideBannerBlock")[0].getElementById("messageParentId")
                .attr("value")
        val title =
            document.getElementsByClass("sideBannerBlock")[0].getElementsByClass("subTitle").text()
        val sender = messageContainer.getElementsByClass("messageInboxSenderLabel").text()
        val date = messageContainer.getElementsByClass("messageInboxDateLabel").text()
        val content = messageContainer.getElementsByClass("messageText").text()
        val recipients = messageContainer.getElementsByClass("recipients-block").text()
        val files: MutableList<MessageFile> = mutableListOf()
        val elementFiles =
            messageContainer.getElementsByClass("messageFilesContainer")[0].getElementsByTag("a")
        for (i in elementFiles.indices) {
            files.add(
                MessageFile(
                    elementFiles[i].attr("title"),
                    elementFiles[i].attr("href")
                )
            )
        }
        return MessageIndividual(messageId, title, sender, date, content, recipients, files)
    }

    override fun toHoliday(html: String): List<Holiday> {
        val document = Jsoup.parse(html)
        val holidayList: MutableList<Holiday> = mutableListOf()
        val elementHolidayTable =
            document.getElementsByClass("tableBack fullWidth hoverTr hover-table")[0]
        val elementHolidayRows = elementHolidayTable.getElementsByTag("tr")
        elementHolidayRows.removeFirst()
        for (i in elementHolidayRows.indices) {
            val elementHolidayRow = elementHolidayRows[i]
            val elements = elementHolidayRow.getElementsByTag("td")
            val name = elements[0].text()
            val rangeStart = elements[1].text()
            val rangeEnd = elements[2].text()
            holidayList.add(Holiday(name, rangeStart, rangeEnd))
        }
        return holidayList
    }

    override fun toParentMeetings(html: String): List<ParentMeeting> {
        val document = Jsoup.parse(html)
        val parentMeetingList: MutableList<ParentMeeting> = mutableListOf()
        val elementParentMeetingsTable = document.getElementsByClass("fullWidth")[0]
        val elementParentMeetingsRows = elementParentMeetingsTable.getElementsByTag("tr")
        elementParentMeetingsRows.removeFirst()
        for (i in elementParentMeetingsRows.indices) {
            val elementParentMeetingRow = elementParentMeetingsRows[i]
            val elements = elementParentMeetingRow.getElementsByTag("td")
            val className = elements[0].text()
            val description = elements[1].text()
            val date = elements[2].text()
            val location = elements[3].text()
            val attachmentUrlsList: MutableList<ParentMeetingFile> = mutableListOf()
            val elementAttachmentUrls = elements[4].getElementsByClass("a")
            for (h in elementAttachmentUrls.indices) {
                attachmentUrlsList.add(
                    ParentMeetingFile(
                        elementAttachmentUrls[h].attr("title"),
                        elementAttachmentUrls[h].attr("href")
                    )
                )
            }
            val creationDate = elements[5].text()
            parentMeetingList.add(
                ParentMeeting(
                    className,
                    description,
                    date,
                    location,
                    attachmentUrlsList,
                    creationDate
                )
            )
        }
        return parentMeetingList
    }

    override fun toSchedule(html: String): List<Schedule> {
        val document = Jsoup.parse(html)
        val scheduleList: MutableList<Schedule> = mutableListOf()
        val elementScheduleTable = document.getElementById("sheduleContTable")
        val elementScheduleRows = elementScheduleTable.getElementsByClass("schedule_day_table")
        val elementScheduleTimeTable = elementScheduleTable.getElementsByClass("schedule_time_table")[0]
        val elementScheduleTimeTableRows = elementScheduleTimeTable.getElementsByTag("tr")
        val timeList: MutableList<String> = mutableListOf()
        for (i in elementScheduleTimeTableRows.indices) {
            timeList.add(elementScheduleTimeTableRows[i].text())
        }
        for (i in elementScheduleRows.indices) {
            val elementScheduleRow = elementScheduleRows[i].getElementsByTag("tr")
            for (h in elementScheduleRow.indices) {
                val weekDay = i+1
                val timeRange = timeList[h]
                val lessonOrder = h+1
                val lesson = elementScheduleRow[h].getElementsByTag("a")[0].text()
                scheduleList.add(Schedule(weekDay.toLong(), timeRange, lessonOrder.toLong(), lesson))
            }
        }
        return scheduleList
    }

    override fun toCalendarEvent(html: String): CalendarEvent {
        // TODO(currently not implemented)
        val document = Jsoup.parse(html)
        val calendarEvent = CalendarEvent(
            url = "",
            teacher = "",
            theme = ""
        )
        return calendarEvent
    }
}