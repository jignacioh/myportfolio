package com.udacity.porfolioapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.HomeMovieFragment;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */
public class ItemMovieActivity extends BaseActivity implements  DetailsMovieFragment.Callbacks,ReviewsMovieFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_movie);

        Movie movie= getIntent().getParcelableExtra(HomeMovieFragment.ARG_ITEM_MOVIE);
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
        Movie movie = getIntent().getParcelableExtra(HomeMovieFragment.ARG_ITEM_MOVIE);
        if (isFavorite){
           getAppDaoSession().getMovieDao().insertOrReplace(movie);
        }else {
            getAppDaoSession().getMovieDao().deleteByKey(movie.getId());
        }
    }

    @Override
    public void onReviewSelected(ArrayList<Object> list, int position, View view) {
        Review review= (Review) list.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(review.getUrl()));
        startActivity(intent);
    }
}