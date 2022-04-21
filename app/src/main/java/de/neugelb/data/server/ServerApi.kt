package de.neugelb.data.server

import de.neugelb.domain.entity.response.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Movies

    @GET("search/movie")
    suspend fun searchMovie(@Query("page") page: Int, @Query("query") query: String)
}