package com.example.danny.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 16/06/17.
 */

public class MoviesAdapter extends BaseAdapter {

    public static final String TAG = MoviesAdapter.class.getSimpleName();

    private Context _context;
    private List<MoviesResult.ResultsBean> _thumbsIds;

    public MoviesAdapter(Context c, List<MoviesResult.ResultsBean> moviesUrls){
        _context = c;
        _thumbsIds = moviesUrls;
    }

    @Override
    public int getCount() {
        return _thumbsIds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: Loading Images from picasso");

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(_context);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 800));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        Picasso.with(_context).load(buildImageUrl(_thumbsIds.get(position))).into(imageView);
        return imageView;
    }

    private String buildImageUrl(MoviesResult.ResultsBean movie){
        Log.d(TAG, "buildImageUrl: building image url for image: " + movie.getPoster_path());
        return "http://image.tmdb.org/t/p/w185/" +  movie.getPoster_path();
    }
}
