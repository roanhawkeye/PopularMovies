package com.example.danny.popularmovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by danny on 19/06/17.
 */

public class MovieService {
    private static MovieService instance = null;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "65436fe185858e50f2b26b201d339562";
    private MoviesAPI moviesAPI;

    public static MovieService getInstance(){
        if(instance == null){
            instance = new MovieService();
            instance.buildAPI();
        }
        return instance;
    }

    private void buildAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesAPI = retrofit.create(MoviesAPI.class);
    }

    public static String getAPIKey(){
        return API_KEY;
    }


    public MoviesAPI getMoviesAPI(){
        return moviesAPI;
    }

}
