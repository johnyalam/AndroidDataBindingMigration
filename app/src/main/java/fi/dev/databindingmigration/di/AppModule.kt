package fi.dev.databindingmigration.di

import fi.dev.databindingmigration.data.remote.NetworkClient
import fi.dev.databindingmigration.data.repository.PostRepository
import fi.dev.databindingmigration.presentation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Provide HttpLoggingInterceptor
    single { NetworkClient.httpLoggingInterceptor }

    // Provide OkHttpClient
    single { NetworkClient.okHttpClient }

    // Provide Retrofit instance
    single { NetworkClient.retrofit }

    // Provide ApiService
    single { NetworkClient.apiService}

    // Singleton
    single { PostRepository(get()) }

    viewModel { MainViewModel(get()) }
}