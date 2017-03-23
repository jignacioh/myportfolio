package com.udacity.porfolioapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.fragment.HomeMovieFragment;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.MovieContract;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */
public class ItemMovieActivity extends BaseActivity implements
            DetailsMovieFragment.Callbacks,
            ReviewsMovieFragment.Callbacks{

    private Movie movie;
    public static final String ARG_IS_FAV="isFavorito";
    public boolean isFavorite;

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
        this.isFavorite=isFavorite;

        if (isFavorite){
           //getAppDaoSession().getMovieDao().insertOrReplace(movie);
            String selectionArgs[]=new String[]{movie.getNameMovie()};
            Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_TITLE + "=?", selectionArgs, null);
            if (cursor.getCount()==0) {
                saveMovie(movie);
            }
        }else {
            deleteMovie(movie);
            //getAppDaoSession().getMovieDao().deleteByKey(movie.getId());
        }

    }

    private void deleteMovie(Movie movie) {

        // Build appropriate uri with String row id appended
        String stringId = Long.toString(movie.getId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();

        // COMPLETED (2) Delete a single row of data using a ContentResolver
        getContentResolver().delete(uri, null, null);

        // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
        //getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);
    }

    private void saveMovie(Movie movie) {



        // COMPLETED (7) Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getImageMovie());
        values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getYearMovie());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getDescriptionMovie());
        values.put(MovieContract.MovieEntry.COLUMN_POPULAR, movie.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER, movie.getUrlMovie());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getNameMovie());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVoteCount());
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);

        // COMPLETED (8) Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
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
        outState.putBoolean(ARG_IS_FAV,isFavorite);
        super.onSaveInstanceState(outState);
       // saveMovie(movie);
       // outState.putParcelable(MovieContentProvider.CONTENT_ITEM_TYPE, todoUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // saveMovie(movie);
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