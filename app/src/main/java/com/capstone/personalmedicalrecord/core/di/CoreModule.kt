package com.capstone.personalmedicalrecord.core.di

import androidx.room.Room
import com.capstone.personalmedicalrecord.core.data.Repository
import com.capstone.personalmedicalrecord.core.data.source.local.LocalDataSource
import com.capstone.personalmedicalrecord.core.data.source.local.room.Database
import com.capstone.personalmedicalrecord.core.data.source.remote.RemoteDataSource
import com.capstone.personalmedicalrecord.core.domain.repository.IRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<Database>().noteDao() }
    factory { get<Database>().patientDao() }
    factory { get<Database>().recordDao() }
    factory { get<Database>().staffDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java, "Medical.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
//    single {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(get())
//            .build()
//        retrofit.create(ApiService::class.java)
//    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get(), get(), get()) }
    single { RemoteDataSource() }
    single<IRepository> { Repository(get(), get()) }
}