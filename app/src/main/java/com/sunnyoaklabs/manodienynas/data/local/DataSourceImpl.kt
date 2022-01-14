package com.sunnyoaklabs.manodienynas.data.local

import com.sunnyoaklabs.manodienynas.domain.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private  val dispatcher: CoroutineDispatcher,
    // todo db:
): DataSource {

    // private val queries = db.personEntityQueries

    override suspend fun getEventById(id: Long): Event? {
        TODO("Not yet implemented")
    }

    override fun getAllEvents(): Flow<List<Event>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEventById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun getMarksById(id: Long): Marks? {
        TODO("Not yet implemented")
    }

    override fun getAllMarks(): Flow<List<Marks>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMarksById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(marks: Marks) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(controlWork: ControlWork) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(term: Term) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(termLegend: TermLegend) {
        TODO("Not yet implemented")
    }

    override suspend fun getAttendanceById(id: Long): Attendance? {
        TODO("Not yet implemented")
    }

    override fun getAllAttendance(): Flow<List<Attendance>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttendanceById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAttendance(attendance: Attendance) {
        TODO("Not yet implemented")
    }

    override suspend fun getClassWorkById(id: Long): ClassWork? {
        TODO("Not yet implemented")
    }

    override fun getAllClassWork(): Flow<List<ClassWork>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteClassWorkById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertClassWork(classWork: ClassWork) {
        TODO("Not yet implemented")
    }

    override suspend fun getHomeWorkById(id: Long): HomeWork? {
        TODO("Not yet implemented")
    }

    override fun getAllHomeWork(): Flow<List<HomeWork>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHomeWorkById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertHomeWork(homeWork: HomeWork) {
        TODO("Not yet implemented")
    }

    override suspend fun getControlWorkById(id: Long): ControlWork? {
        TODO("Not yet implemented")
    }

    override fun getAllControlWork(): Flow<List<ControlWork>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteControlWorkById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getTermById(id: Long): Term? {
        TODO("Not yet implemented")
    }

    override fun getAllTerm(): Flow<List<Term>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTermById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getTermLegendById(id: Long): TermLegend? {
        TODO("Not yet implemented")
    }

    override fun getAllTermLegend(): Flow<TermLegend> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTermLegendById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesGottenById(id: Long): Message? {
        TODO("Not yet implemented")
    }

    override fun getMessagesGottenAll(): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessagesGottenById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessagesGotten(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesSentById(id: Long): Message? {
        TODO("Not yet implemented")
    }

    override fun getAllMessagesSent(): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessagesSentById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessagesSent(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesStarredById(id: Long): Message? {
        TODO("Not yet implemented")
    }

    override fun getAllMessagesStarred(): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessagesStarredById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessagesStarred(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesDeletedById(id: Long): Message? {
        TODO("Not yet implemented")
    }

    override fun getAllMessagesDeleted(): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessagesDeletedById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessagesDeleted(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessageIndividualById(id: Long): MessageIndividual? {
        TODO("Not yet implemented")
    }

    override fun getAllMessageIndividual(): Flow<List<MessageIndividual>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessageIndividualById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessageIndividual(messageIndividual: MessageIndividual) {
        TODO("Not yet implemented")
    }

    override suspend fun getHolidayById(id: Long): Holiday? {
        TODO("Not yet implemented")
    }

    override fun getAllHoliday(): Flow<List<Holiday>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHolidayById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertHoliday(holiday: Holiday) {
        TODO("Not yet implemented")
    }

    override suspend fun getParentMeetingsById(id: Long): ParentMeetings? {
        TODO("Not yet implemented")
    }

    override fun getAllParentMeetings(): Flow<List<ParentMeetings>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteParentMeetingsById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertParentMeetings(parentMeetings: ParentMeetings) {
        TODO("Not yet implemented")
    }

    override suspend fun getScheduleById(id: Long): Schedule? {
        TODO("Not yet implemented")
    }

    override fun getAllSchedule(): Flow<List<Schedule>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteScheduleById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    override suspend fun getCalendarById(id: Long): Calendar? {
        TODO("Not yet implemented")
    }

    override fun getAllCalendar(): Flow<List<Calendar>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCalendarById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCalendar(calendar: Calendar) {
        TODO("Not yet implemented")
    }

}