package com.udacity.porfolioapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailMovieFragment;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Juan PC
 */
public class ListMoviesActivity extends AppCompatActivity implements Callback<ListMovie>, ListMoviesFragment.Callbacks,ListMoviesFragment.OnListFragmentInteractionListener  {
    private GridLayoutManager lLayout;
    private RecyclerView rvMovies;
    private SwipeRefreshLayout swipeContainer;
    private List<Movie> listMovies;

    private boolean mTwoPane=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);
        getSupportActionBar().setTitle(getResources().getString(R.string.lb_my_movies));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            Bundle bundle=new Bundle();
            bundle.putInt(ListMoviesFragment.ARG_COLUMN_COUNT,3);
            ListMoviesFragment fragment = new ListMoviesFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_view, fragment).commit();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {

    }

    @Override
    public void onFailure(Call<ListMovie> call, Throwable t) {
        rvMovies.setVisibility(View.GONE);
    }


    @Override
    public void onFragmentInteraction(Uri uri, Object object) {
        if (uri == Uri.parse(ListMoviesFragment.class.getSimpleName())) {

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemSelected(ArrayList<Movie> list, int position,View view) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailMovieFragment.ARG_ITEM_MOVIE, (Parcelable) list.get(position));
            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment).commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            //Intent detailIntent = new Intent(this, ItemMovieActivity.class);
            //detailIntent.putExtra(DetailMovieFragment.ARG_ITEM_MOVIE,(Parcelable) list.get(position));
            //startActivity(detailIntent);


            //Intent intent = new Intent(this, ItemMovieActivity.class);
            // Pass data object in the bundle and populate details activity.
            //intent.putExtra(DetailMovieFragment.ARG_ITEM_MOVIE,(Parcelable) list.get(position));
            //ActivityOptionsCompat options = ActivityOptionsCompat.
            //        makeSceneTransitionAnimation(this, view, "profile");
            //startActivity(intent, options.toBundle());


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Intent intentD = new Intent(this, ItemMovieActivity.class);
                Pair<View, String> pair1 = Pair.create(view, view.getTransitionName());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.getTransitionName());
                intentD.putExtra(DetailMovieFragment.ARG_ITEM_MOVIE,(Parcelable) list.get(position));
                startActivity(intentD, options.toBundle());
            }
            else {
                Intent detailIntent = new Intent(this, ItemMovieActivity.class);
                detailIntent.putExtra(DetailMovieFragment.ARG_ITEM_MOVIE,(Parcelable) list.get(position));
                startActivity(detailIntent);
            }
        }
    }
}
