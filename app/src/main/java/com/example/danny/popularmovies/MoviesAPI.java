package com.example.danny.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by danny on 16/06/17.
 */

public interface MoviesAPI {

    @GET("movie/popular")
    Call<MoviesResult> getMoviesPopular(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id, @Query("api_key") String apiKey);
}
