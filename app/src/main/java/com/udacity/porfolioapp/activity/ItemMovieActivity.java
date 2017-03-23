package com.udacity.porfolioapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.HomeMovieFragment;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.model.provider.MovieContentProvider;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */
public class ItemMovieActivity extends BaseActivity implements
            DetailsMovieFragment.Callbacks,
            ReviewsMovieFragment.Callbacks{

    private Uri todoUri;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_movie);

        Movie movie = getIntent().getParcelableExtra(HomeMovieFragment.ARG_ITEM_MOVIE);
        getSupportActionBar().setTitle(movie.getNameMovie());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(HomeMovieFragment.ARG_ITEM_MOVIE, getIntent().getParcelableExtra(HomeMovieFragment.ARG_ITEM_MOVIE));
            HomeMovieFragment fragment = new HomeMovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment).commit();
        }
        Bundle extras = getIntent().getExtras();
        todoUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
                .getParcelable(MovieContentProvider.CONTENT_ITEM_TYPE);
        if (extras != null) {
            todoUri = extras
                    .getParcelable(MovieContentProvider.CONTENT_ITEM_TYPE);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrailerSelected(ArrayList<Object> list, int position, View view) {
        Trailer trailer= (Trailer) list.get(position);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getValueString(R.string.link_yt)+ trailer.getKey()));
        startActivity(i);

    }

    @Override
    public void onItemCheckFavorite(boolean isFavorite) {
        movie = getIntent().getParcelableExtra(HomeMovieFragment.ARG_ITEM_MOVIE);
        if (isFavorite){
           //getAppDaoSession().getMovieDao().insertOrReplace(movie);
            saveMovie(movie);
        }else {
            getAppDaoSession().getMovieDao().deleteByKey(movie.getId());
        }

    }

    private void saveMovie(Movie movie) {

        ContentValues values = new ContentValues();
        values.put(Movie.MovieEntry.COLUMN_ID, movie.getId());
        values.put(Movie.MovieEntry.COLUMN_AVERAGE, movie.getVoteAverage());
        values.put(Movie.MovieEntry.COLUMN_BACKDROP, movie.getImageMovie());
        values.put(Movie.MovieEntry.COLUMN_DATE, movie.getYearMovie());
        values.put(Movie.MovieEntry.COLUMN_OVERVIEW, movie.getDescriptionMovie());
        values.put(Movie.MovieEntry.COLUMN_POPULAR, movie.getPopularity());
        values.put(Movie.MovieEntry.COLUMN_POSTER, movie.getUrlMovie());
        values.put(Movie.MovieEntry.COLUMN_TITLE, movie.getNameMovie());
        values.put(Movie.MovieEntry.COLUMN_VOTE, movie.getVoteCount());


        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(
                    MovieContentProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }

    @Override
    public void onReviewSelected(ArrayList<Object> list, int position, View view) {
        Review review= (Review) list.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(review.getUrl()));
        startActivity(intent);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveMovie(movie);
        outState.putParcelable(MovieContentProvider.CONTENT_ITEM_TYPE, todoUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMovie(movie);
    }
    /*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { Movie.MovieEntry.COLUMN_ID,
                Movie.MovieEntry.COLUMN_TITLE,
                Movie.MovieEntry.COLUMN_OVERVIEW,
                Movie.MovieEntry.COLUMN_BACKDROP,
                Movie.MovieEntry.COLUMN_AVERAGE,
                Movie.MovieEntry.COLUMN_DATE,
                Movie.MovieEntry.COLUMN_POSTER,
                Movie.MovieEntry.COLUMN_VOTE,
                Movie.MovieEntry.COLUMN_POPULAR};
        CursorLoader cursorLoader = new CursorLoader(this,
                MovieContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    */
}