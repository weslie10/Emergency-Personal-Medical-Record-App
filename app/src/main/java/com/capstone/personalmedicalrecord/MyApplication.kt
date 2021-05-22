package com.capstone.personalmedicalrecord

import android.app.Application
import com.capstone.personalmedicalrecord.core.di.databaseModule
import com.capstone.personalmedicalrecord.core.di.networkModule
import com.capstone.personalmedicalrecord.core.di.repositoryModule
import com.capstone.personalmedicalrecord.di.useCaseModule
import com.capstone.personalmedicalrecord.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}