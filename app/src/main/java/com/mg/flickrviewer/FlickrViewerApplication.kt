package com.mg.flickrviewer

import android.app.Application
import com.mg.flickrviewer.api.FlickrService
import com.mg.flickrviewer.repository.FlickrRepository
import com.mg.flickrviewer.ui.list.RecentPhotosViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class FlickrViewerApplication : Application() {

    private lateinit var retrofit: Retrofit

    companion object {

        @JvmStatic
        private lateinit var application: FlickrViewerApplication

        fun get(): FlickrViewerApplication {
            return application
        }
    }

    init {
        application = this
    }

    private val apiModule = module {
        single { retrofit.create(FlickrService::class.java) }
    }

    private val repositoryModule = module {
        factory { FlickrRepository(get()) }

    }

    private val viewModelModule = module {
        viewModel { RecentPhotosViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        retrofit = configureRetrofit(BuildConfig.FLICKR_URL)

        startKoin {
            androidLogger()
            androidContext(this@FlickrViewerApplication)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    private fun configureRetrofit(
        baseUrl: String,
        interceptors: List<Interceptor> = emptyList(),
        networkInterceptors: List<Interceptor> = emptyList()
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val client = OkHttpClient.Builder().run {
            networkInterceptors.forEach {
                addNetworkInterceptor(it)
            }
            interceptors.forEach {
                addInterceptor(it)
            }

//            addInterceptor(ApiErrorService(this@FlickrViewerApplication))
            addInterceptor(loggingInterceptor)
//            protocols(listOf(Protocol.HTTP_1_1))
            build()
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }
}