package com.pdfforge.app.di

import android.content.Context
import androidx.room.Room
import com.pdfforge.app.BuildConfig
import com.pdfforge.app.api.PdfForgeService
import com.pdfforge.app.db.PdfDao
import com.pdfforge.app.db.PdfDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePdfDatabase(@ApplicationContext context: Context): PdfDatabase {
        return Room.databaseBuilder(
            context,
            PdfDatabase::class.java,
            "pdf_forge.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePdfDao(database: PdfDatabase): PdfDao {
        return database.pdfDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providePdfForgeService(okHttpClient: OkHttpClient): PdfForgeService {
        val baseUrl = BuildConfig.BACKEND_URL.let {
            if (it.endsWith("/")) it else "$it/"
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PdfForgeService::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubService(okHttpClient: OkHttpClient): com.pdfforge.app.api.GithubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.pdfforge.app.api.GithubService::class.java)
    }
}
