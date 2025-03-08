package fi.dev.databindingmigration.di

import fi.dev.databindingmigration.activity.MainViewModel
import fi.dev.databindingmigration.activity.repository.CompanyRepository
import org.koin.dsl.module

val appModule = module {
    single { CompanyRepository() }  // Singleton
    factory { MainViewModel(get()) }  // New instance every time
}