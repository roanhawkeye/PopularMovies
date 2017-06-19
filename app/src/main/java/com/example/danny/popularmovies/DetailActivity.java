package com.example.danny.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by danny on 19/06/17.
 */

public class DetailActivity extends AppCompatActivity{

    private static final String TAG = "DetailActivity";

    private TextView movieTitle;
    private TextView movieDuration;
    private TextView movieRelease;
    private TextView movieOverview;
    private ImageView movieImage;

    private Movie movie;


    MoviesAPI moviesAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        movieTitle = (TextView) findViewById(R.id.movie_title);
        movieDuration = (TextView) findViewById(R.id.movie_duration);
        movieRelease = (TextView) findViewById(R.id.movie_release);
        movieOverview = (TextView) findViewById(R.id.movie_overview);
        movieImage = (ImageView) findViewById(R.id.movie_image);

        moviesAPI = MovieService.getInstance().getMoviesAPI();


        displayDetails();
    }

    private void displayDetails() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if(id > 0){
            Log.d(TAG, "displayDetails: Rendering Details of movie id: " + id);
            Call<Movie> movieCall = moviesAPI.getMovieById(id, MovieService.getAPIKey());
            movieCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Log.d(TAG, "onResponse: response code: " + response.code());
                    renderMovie(response);
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e(TAG, "something went wrong : " + t.getMessage());
                }
            });
        }
    }

    private void renderMovie(Response<Movie> response) {
        Log.d(TAG, "renderMovie: Rendering movie - " + response.body().getTitle() );
        movie = response.body();
        movieTitle.setText(movie.getTitle());
        movieRelease.setText(movie.getRelease_date().substring(0,4));
        movieDuration.setText(movie.getRuntime() + " mins");
        movieOverview.setText(movie.getOverview());

        //load image from url
        Picasso.with(DetailActivity.this).load(buildImageUrl(movie)).into(movieImage);

    }

    private String buildImageUrl(Movie movie){
        Log.d(TAG, "buildImageUrl: building image url for image: " + movie.getPoster_path());
        return "http://image.tmdb.org/t/p/w185/" +  movie.getPoster_path();
    }

}

