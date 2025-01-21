package com.lohanna.catalogodefilmes.data.movies.di

import com.lohanna.catalogodefilmes.data.common.remote.RemoteService
import com.lohanna.catalogodefilmes.data.movies.remote.MoviesRemoteDataSource
import com.lohanna.catalogodefilmes.data.movies.repository.MoviesRepository
import com.lohanna.catalogodefilmes.data.movies.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MoviesDataModule {
    @Provides
    @ViewModelScoped
    fun provideRemoteService(): MoviesRemoteDataSource =
        RemoteService().create(MoviesRemoteDataSource::class.java)

    @Provides
    @ViewModelScoped
    fun provideRepository(remote: MoviesRemoteDataSource): MoviesRepository =
        MoviesRepositoryImpl(remote)
}