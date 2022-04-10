package com.sunnyoaklabs.manodienynas.di

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.sunnyoaklabs.manodienynas.ManoDienynasDatabase
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.core.util.StandardDispatchers
import com.sunnyoaklabs.manodienynas.core.util.validator.Validator
import com.sunnyoaklabs.manodienynas.core.util.validator.ValidatorImpl
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.local.DataSourceImpl
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.repository.RepositoryImpl
import com.sunnyoaklabs.manodienynas.data.util.*
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.*
import com.sunnyoaklabs.manodienynas.presentation.main.fragment_view_model.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventsFragmentViewModel(
        app: Application,
        getEvents: GetEvents,
        getEventsPage: GetEventsPage,
        validator: Validator
    ): EventsFragmentViewModel {
        return EventsFragmentViewModel(app, getEvents, getEventsPage, validator)
    }

    @Provides
    @Singleton
    fun provideMarksFragmentViewModel(
        getMarks: GetMarks,
        getMarksByCondition: GetMarksByCondition,
        getMarksEventItem: GetMarksEventItem,
        getAttendance: GetAttendance,
        getClassWork: GetClassWork,
        getClassWorkByCondition: GetClassWorkByCondition,
        getHomeWork: GetHomeWork,
        getHomeWorkByCondition: GetHomeWorkByCondition,
        getControlWork: GetControlWork,
        getControlWorkByCondition: GetControlWorkByCondition,
        validator: Validator
    ): MarksFragmentViewModel {
        return MarksFragmentViewModel(
            getMarks,
            getMarksByCondition,
            getMarksEventItem,
            getAttendance,
            getClassWork,
            getClassWorkByCondition,
            getHomeWork,
            getHomeWorkByCondition,
            getControlWork,
            getControlWorkByCondition,
            validator
        )
    }

    @Provides
    @Singleton
    fun provideMessagesFragmentViewModel(
        app: Application,
        getMessagesGotten: GetMessagesGotten,
        getMessagesGottenByCondition: GetMessagesGottenByCondition,
        getMessagesSent: GetMessagesSent,
        getMessagesSentByCondition: GetMessagesSentByCondition,
        getMessagesStarred: GetMessagesStarred,
        getMessagesStarredByCondition: GetMessagesStarredByCondition,
        getMessagesDeleted: GetMessagesDeleted,
        getMessagesDeletedByCondition: GetMessagesDeletedByCondition,
        getMessageIndividual: GetMessageIndividual,
        validator: Validator
    ): MessagesFragmentViewModel {
        return MessagesFragmentViewModel(
            app,
            getMessagesGotten,
            getMessagesGottenByCondition,
            getMessagesSent,
            getMessagesSentByCondition,
            getMessagesStarred,
            getMessagesStarredByCondition,
            getMessagesDeleted,
            getMessagesDeletedByCondition,
            getMessageIndividual,
            validator
        )
    }

    @Provides
    @Singleton
    fun provideTermsFragmentViewModel(
        getTerm: GetTerm,
        getTermMarkDialogItem: GetTermMarkDialogItem,
        validator: Validator
    ): TermsFragmentViewModel {
        return TermsFragmentViewModel(
            getTerm,
            getTermMarkDialogItem,
            validator
        )
    }

    @Provides
    @Singleton
    fun provideMoreFragmentViewModel(
        getHoliday: GetHoliday,
        getParentMeetings: GetParentMeetings,
        getSchedule: GetSchedule,
        //getCalendar: GetCalendar,
        //getCalendarEvent: GetCalendarEvent
        validator: Validator
    ): MoreFragmentViewModel {
        return MoreFragmentViewModel(
            getHoliday,
            getParentMeetings,
            getSchedule,
            //getCalendar,
            //getCalendarEvent,
            validator
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepository(
        dataSource: DataSource,
        api: BackendApi,
        converter: Converter
    ): Repository {
        return RepositoryImpl(api, dataSource, converter)
    }

    @Provides
    @Singleton
    fun provideBackendApi(): BackendApi {
        return BackendApi.create()
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return StandardDispatchers()
    }

    @Provides
    @Singleton
    fun provideConverter(): Converter {
        return Converter(
            webScrapper = JsoupWebScrapper(),
            jsonParser = GsonParser(Gson()),
            jsonFormatter = JsonFormattedImpl(),
            dataSourceObjectParser = DataSourceObjectParserImpl(GsonParser(Gson()))
        )
    }

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = ManoDienynasDatabase.Schema,
            context = app,
            name = "manodienynas.db"
        )
    }

    @Provides
    @Singleton
    fun provideDataSource(
        driver: SqlDriver,
        provider: DispatcherProvider,
        converter: Converter
    ): DataSource {
        return DataSourceImpl(ManoDienynasDatabase(driver), provider, converter)
    }

    @Provides
    @Singleton
    fun provideGetSettingsUseCase(repository: Repository): GetSettings {
        return GetSettings(repository)
    }

    @Provides
    @Singleton
    fun provideGetSessionCookiesUseCase(repository: Repository): GetSessionCookies {
        return GetSessionCookies(repository)
    }

    @Provides
    @Singleton
    fun provideGetEventsUseCase(repository: Repository): GetEvents {
        return GetEvents(repository)
    }

    @Provides
    @Singleton
    fun provideGetEventsPageUseCase(repository: Repository): GetEventsPage {
        return GetEventsPage(repository)
    }

    @Provides
    @Singleton
    fun provideGetMarksByConditionUseCase(repository: Repository): GetMarksByCondition {
        return GetMarksByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMarksEventItemByConditionUseCase(repository: Repository): GetMarksEventItem {
        return GetMarksEventItem(repository)
    }

    @Provides
    @Singleton
    fun provideGetAttendanceUseCase(repository: Repository): GetAttendance {
        return GetAttendance(repository)
    }

    @Provides
    @Singleton
    fun provideGetCalendarUseCase(repository: Repository): GetCalendar {
        return GetCalendar(repository)
    }

    @Provides
    @Singleton
    fun provideGetCalendarEventUseCase(repository: Repository): GetCalendarEvent {
        return GetCalendarEvent(repository)
    }

    @Provides
    @Singleton
    fun provideGetClassWorkUseCase(repository: Repository): GetClassWork {
        return GetClassWork(repository)
    }

    @Provides
    @Singleton
    fun provideGetClassWorkByConditionUseCase(repository: Repository): GetClassWorkByCondition {
        return GetClassWorkByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesGottenByConditionUseCase(repository: Repository): GetMessagesGottenByCondition {
        return GetMessagesGottenByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesSentByConditionUseCase(repository: Repository): GetMessagesSentByCondition {
        return GetMessagesSentByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesStarredByConditionUseCase(repository: Repository): GetMessagesStarredByCondition {
        return GetMessagesStarredByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesDeletedByConditionUseCase(repository: Repository): GetMessagesDeletedByCondition {
        return GetMessagesDeletedByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetControlWorkUseCase(repository: Repository): GetControlWork {
        return GetControlWork(repository)
    }

    @Provides
    @Singleton
    fun provideGetControlWorkByConditionUseCase(repository: Repository): GetControlWorkByCondition {
        return GetControlWorkByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetHolidayUseCase(repository: Repository): GetHoliday {
        return GetHoliday(repository)
    }

    @Provides
    @Singleton
    fun provideGetHomeWorkUseCase(repository: Repository): GetHomeWork {
        return GetHomeWork(repository)
    }

    @Provides
    @Singleton
    fun provideGetHomeWorkByConditionUseCase(repository: Repository): GetHomeWorkByCondition {
        return GetHomeWorkByCondition(repository)
    }

    @Provides
    @Singleton
    fun provideGetMarksUseCase(repository: Repository): GetMarks {
        return GetMarks(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessageIndividualUseCase(repository: Repository): GetMessageIndividual {
        return GetMessageIndividual(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesDeletedUseCase(repository: Repository): GetMessagesDeleted {
        return GetMessagesDeleted(repository)
    }

    @Provides
    @Singleton
    fun provideGetTermMarkDialogItemUseCase(repository: Repository): GetTermMarkDialogItem {
        return GetTermMarkDialogItem(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesGottenUseCase(repository: Repository): GetMessagesGotten {
        return GetMessagesGotten(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesSentUseCase(repository: Repository): GetMessagesSent {
        return GetMessagesSent(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesStarredUseCase(repository: Repository): GetMessagesStarred {
        return GetMessagesStarred(repository)
    }

    @Provides
    @Singleton
    fun provideGetParentMeetingsUseCase(repository: Repository): GetParentMeetings {
        return GetParentMeetings(repository)
    }

    @Provides
    @Singleton
    fun provideGetScheduleUseCase(repository: Repository): GetSchedule {
        return GetSchedule(repository)
    }

    @Provides
    @Singleton
    fun provideGetTermUseCase(repository: Repository): GetTerm {
        return GetTerm(repository)
    }

    @Provides
    @Singleton
    fun provideValidator(): Validator {
        return ValidatorImpl()
    }
}

