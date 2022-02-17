package com.sunnyoaklabs.manodienynas.di

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.sunnyoaklabs.manodienynas.ManoDienynasDatabase
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.core.util.StandardDispatchers
import com.sunnyoaklabs.manodienynas.data.local.DataSource
import com.sunnyoaklabs.manodienynas.data.local.DataSourceImpl
import com.sunnyoaklabs.manodienynas.data.remote.BackendApi
import com.sunnyoaklabs.manodienynas.data.repository.RepositoryImpl
import com.sunnyoaklabs.manodienynas.data.util.*
import com.sunnyoaklabs.manodienynas.domain.repository.Repository
import com.sunnyoaklabs.manodienynas.domain.use_case.GetEvents
import com.sunnyoaklabs.manodienynas.domain.use_case.GetSessionCookies
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
    fun provideBackendApi(
        converter: Converter
    ): BackendApi {
        return BackendApi.create(converter)
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
    fun provideGetSessionCookiesUseCase(repository: Repository): GetSessionCookies {
        return GetSessionCookies(repository)
    }

    @Provides
    @Singleton
    fun provideGetEventsUseCase(repository: Repository, converter: Converter): GetEvents {
        return GetEvents(repository, converter)
    }
}