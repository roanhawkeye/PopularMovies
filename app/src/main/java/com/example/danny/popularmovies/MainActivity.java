package com.example.danny.popularmovies;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private GridView gridView;
    private MoviesAPI moviesAPI;

    List<MoviesResult.ResultsBean> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView =  (GridView) findViewById(R.id.grid_view);

        Log.d(TAG, "onCreate");

        moviesAPI = MovieService.getInstance().getMoviesAPI();
        
        populateGrid();

    }



    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume:");
    }


    private void populateGrid() {

        Log.d(TAG, "populateGrid: populating grid");

        Call<MoviesResult> moviesResultCall = moviesAPI.getMoviesPopular(MovieService.getAPIKey());

        moviesResultCall.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                Log.d(TAG, "onResponse: Response code: " + response.code());
                if(response.isSuccessful()){
                    renderMovies(response);
                }
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                Log.d(TAG, "onFailure: Response body: " + t.getMessage());
            }
        });

    }

    private void renderMovies(Response<MoviesResult> response) {
        movies = new ArrayList<MoviesResult.ResultsBean>();
        MoviesResult moviesResult = response.body();
        movies = moviesResult.getResults();
        MoviesAdapter moviesAdapter = new MoviesAdapter(MainActivity.this, movies);
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "You click on position: " + position);
                Log.i(TAG, "You click on movie: " + movies.get(position).getTitle());
                Log.i(TAG, "You click on id: " + movies.get(position).getId());

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movies.get(position).getId());
                startActivity(intent);
            }
        });
    }


}
