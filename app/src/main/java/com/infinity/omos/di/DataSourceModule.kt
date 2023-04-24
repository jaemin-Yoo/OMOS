package com.infinity.omos.di

import com.infinity.omos.source.local.user.UserLocalDataSource
import com.infinity.omos.source.local.user.UserLocalDataSourceImpl
import com.infinity.omos.source.remote.auth.AuthRemoteDataSource
import com.infinity.omos.source.remote.auth.AuthRemoteDataSourceImpl
import com.infinity.omos.source.remote.user.UserRemoteDataSource
import com.infinity.omos.source.remote.user.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    @Singleton
    fun bindAuthRemoteDataSource(
        authDataRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}