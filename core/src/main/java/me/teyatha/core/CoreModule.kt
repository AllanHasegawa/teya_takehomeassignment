package me.teyatha.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIo

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {
    @Provides
    @Singleton
    fun randomiser(): Randomiser = KotlinRandomiser()

    @Provides
    @Singleton
    @DispatcherIo
    fun dispatcherIo(): CoroutineDispatcher = Dispatchers.IO
}