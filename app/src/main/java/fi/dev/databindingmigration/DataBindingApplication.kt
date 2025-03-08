package fi.dev.databindingmigration

import android.app.Application
import fi.dev.databindingmigration.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DataBindingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DataBindingApplication)
            modules(appModule)
        }
    }
}