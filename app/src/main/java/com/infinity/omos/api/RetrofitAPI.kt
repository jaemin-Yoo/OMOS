package com.infinity.omos.api

import com.infinity.omos.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  수정 필요
 */
object RetrofitAPI {
    private var movieInstance : Retrofit? = null
    private var instance : Retrofit? = null

    fun getMovieInstnace() : Retrofit {
        if(movieInstance == null){
            movieInstance = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return movieInstance!!
    }

    fun getInstnace() : Retrofit {
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}