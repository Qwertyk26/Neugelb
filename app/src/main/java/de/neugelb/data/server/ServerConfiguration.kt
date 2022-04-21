package de.neugelb.data.server

import android.content.Context
import android.os.Build
import com.google.gson.GsonBuilder
import de.neugelb.R
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerApiConfigurator {

    private val gson by lazy {
        GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()
    }

    //Build configuration for network calls
    fun configureServerApi(context: Context): ServerApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(context, chain) }
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ServerApi::class.java)
    }

    private fun addApiKeyToRequests(context: Context, chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val newUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            originalHttpUrl.newBuilder()
                .addQueryParameter("language", context.resources.configuration.locales[0].language)
        } else {
            originalHttpUrl.newBuilder()
                .addQueryParameter("language", context.resources.configuration.locale.language)
        }
            .addQueryParameter("api_key", context.getString(R.string.api_key)).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }
}