package com.udacity.porfolioapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailMovieFragment;
import com.udacity.porfolioapp.model.DaoSession;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */
public class ItemMovieActivity extends AppCompatActivity implements  DetailMovieFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_movie);

        // Show the Up button in the action bar.
        Movie movie= (Movie) getIntent().getParcelableExtra(DetailMovieFragment.ARG_ITEM_MOVIE);
        getSupportActionBar().setTitle(movie.getNameMovie());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailMovieFragment.ARG_ITEM_MOVIE, getIntent().getParcelableExtra(DetailMovieFragment.ARG_ITEM_MOVIE));
            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //NavUtils.navigateUpTo(this, new Intent(this, ListMoviesActivity.class));
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(ArrayList<Object> list, int position, View view) {
        Trailer trailer= (Trailer) list.get(position);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.youtube.com/watch?v="+ trailer.getKey()));
        startActivity(i);

    }

    @Override
    public void onItemCheckFavorite(boolean isFavorite) {
        Movie movie = (Movie) getIntent().getParcelableExtra(DetailMovieFragment.ARG_ITEM_MOVIE);
        if (isFavorite){
            long movie_id = getAppDaoSession().getMovieDao().insert(movie);
            Log.i("OK"," insert");
        }else {
            Log.i("OK","remove");
            getAppDaoSession().getMovieDao().deleteByKey(movie.getId());
        }
    }
    private DaoSession getAppDaoSession() {
        return ((BaseContextApplication)getApplication()).getDaoSession();
    }
}