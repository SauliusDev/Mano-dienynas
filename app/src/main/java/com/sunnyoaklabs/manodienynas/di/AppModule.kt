package com.sunnyoaklabs.manodienynas.di

import com.google.gson.Gson
import com.sunnyoaklabs.manodienynas.core.util.DispatcherProvider
import com.sunnyoaklabs.manodienynas.core.util.StandardDispatchers
import com.sunnyoaklabs.manodienynas.data.util.Converter
import com.sunnyoaklabs.manodienynas.data.util.GsonParser
import com.sunnyoaklabs.manodienynas.data.util.JsonFormatterImpl
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
    fun provideDispatcherProvider(): DispatcherProvider {
        return StandardDispatchers()
    }

    @Provides
    @Singleton
    fun provideConverter(): Converter {
        return Converter(
            jsonFormatter = JsonFormatterImpl(),
            jsonParser = GsonParser(Gson())
        )
    }
}