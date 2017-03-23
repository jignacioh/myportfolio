package com.udacity.porfolioapp.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.fragment.HomeMovieFragment;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.MovieContract;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.model.provider.MovieContentProvider;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Juan PC
 */
public class ListMoviesActivity extends BaseActivity implements
        ListMoviesFragment.Callbacks,
        DetailsMovieFragment.Callbacks,
        ReviewsMovieFragment.Callbacks{

    private boolean mTwoPane=false;
    public static final String ARG_FRAG_LIST="ListMovieFragment";
    public static final String ARG_MOVIE_SELECTED="movieSelected";
    private FrameLayout frameLayoutMainView,frameLayoutDetailContainer;
    private Movie movieSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);
        ButterKnife.bind(this);
        if (savedInstanceState!=null){
            movieSelected=savedInstanceState.getParcelable(ARG_MOVIE_SELECTED);
        }
        getSupportActionBar().setTitle(getResources().getString(R.string.lb_my_movies));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            Bundle bundle=new Bundle();
            bundle.putInt(ListMoviesFragment.ARG_COLUMN_COUNT,getResources().getInteger(R.integer.gallery_columns));
            attachFragment(savedInstanceState,bundle);
        }
        if (getResources().getBoolean(R.bool.isTablet)){
            frameLayoutMainView=(FrameLayout)findViewById(R.id.main_view);
            frameLayoutDetailContainer=(FrameLayout)findViewById(R.id.movie_detail_container);
            if (isOrientationHorizontal(Configuration.ORIENTATION_LANDSCAPE)){
                frameLayoutMainView.setLayoutParams(new
                        LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 2));
                frameLayoutDetailContainer.setLayoutParams(new
                        LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 2));
            }else {
                frameLayoutMainView.setLayoutParams(new
                        LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
                frameLayoutDetailContainer.setLayoutParams(new
                        LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 0.66f));
            }
        }
    }


    private void attachFragment(Bundle savedInstanceState, Bundle bundle) {
        if (savedInstanceState == null) {
            ListMoviesFragment fragment = new ListMoviesFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_view, fragment,ARG_FRAG_LIST).commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(ArrayList<Movie> list, int position,View view) {
        movieSelected = list.get(position);
        if (mTwoPane) {
            getSupportActionBar().setTitle(movieSelected.getNameMovie());
            Bundle arguments = new Bundle();
            arguments.putParcelable(HomeMovieFragment.ARG_ITEM_MOVIE, movieSelected);
            HomeMovieFragment fragment = new HomeMovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment).commit();

        } else {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Intent intentD = new Intent(this, ItemMovieActivity.class);
                Pair<View, String> pair1 = Pair.create(view, view.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.getTransitionName());
                Uri todoUri = Uri.parse(MovieContentProvider.CONTENT_URI + "/" + position);
                intentD.putExtra(MovieContentProvider.CONTENT_ITEM_TYPE, todoUri);
                intentD.putExtra(HomeMovieFragment.ARG_ITEM_MOVIE,movieSelected);
                startActivity(intentD, options.toBundle());
            }
            else {
                Intent detailIntent = new Intent(this, ItemMovieActivity.class);
                detailIntent.putExtra(HomeMovieFragment.ARG_ITEM_MOVIE,movieSelected);
                startActivity(detailIntent);
            }
        }
    }

    @Override
    public void onFavoriteSelected(Cursor cursor, int position, View view) {
        cursor.moveToPosition(position);
        movieSelected=new Movie(cursor);



        if (mTwoPane) {
            getSupportActionBar().setTitle(movieSelected.getNameMovie());
            Bundle arguments = new Bundle();
            arguments.putParcelable(HomeMovieFragment.ARG_ITEM_MOVIE,  movieSelected);
            HomeMovieFragment fragment = new HomeMovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment).commit();

        } else {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Intent intentD = new Intent(this, ItemMovieActivity.class);
                Pair<View, String> pair1 = Pair.create(view, view.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.getTransitionName());
                Uri todoUri = Uri.parse(MovieContentProvider.CONTENT_URI + "/" + position);
                intentD.putExtra(MovieContentProvider.CONTENT_ITEM_TYPE, todoUri);
                intentD.putExtra(HomeMovieFragment.ARG_ITEM_MOVIE,movieSelected);
                startActivity(intentD, options.toBundle());
            }
            else {
                Intent detailIntent = new Intent(this, ItemMovieActivity.class);
                detailIntent.putExtra(HomeMovieFragment.ARG_ITEM_MOVIE,movieSelected);
                startActivity(detailIntent);
            }
        }
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

        if (isFavorite){
            //getAppDaoSession().getMovieDao().insertOrReplace(movie);
            String selectionArgs[]=new String[]{movieSelected.getNameMovie()};
            Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_TITLE + "=?", selectionArgs, null);
            if (cursor.getCount()==0) {
                saveMovie(movieSelected);
            }
        }else {
            deleteMovie(movieSelected);
            //getAppDaoSession().getMovieDao().deleteByKey(movie.getId());
        }

    }

    private void deleteMovie(Movie movieSelected) {
        // Build appropriate uri with String row id appended
        String stringId = Long.toString(movieSelected.getId());
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();

        // COMPLETED (2) Delete a single row of data using a ContentResolver
        getContentResolver().delete(uri, null, null);

        // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
        //getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);
    }

    private void saveMovie(Movie movieSelected) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movieSelected.getId());
        values.put(MovieContract.MovieEntry.COLUMN_AVERAGE, movieSelected.getVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movieSelected.getImageMovie());
        values.put(MovieContract.MovieEntry.COLUMN_DATE, movieSelected.getYearMovie());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieSelected.getDescriptionMovie());
        values.put(MovieContract.MovieEntry.COLUMN_POPULAR, movieSelected.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER, movieSelected.getUrlMovie());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movieSelected.getNameMovie());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE, movieSelected.getVoteCount());
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
        outState.putParcelable(ARG_MOVIE_SELECTED,movieSelected);
        super.onSaveInstanceState(outState);
    }

}
