package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.listener.OnTrailerClickListener;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.ListTrailer;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.service.ApiClient;
import com.udacity.porfolioapp.service.MovieRestAPI;
import com.udacity.porfolioapp.ui.adapter.MainMovieRecyclerViewAdapter;
import com.udacity.porfolioapp.ui.adapter.MovieRecyclerViewAdapter;
import com.udacity.porfolioapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan PC
 */
public class DetailMovieFragment extends Fragment implements Callback<ListTrailer>,OnTrailerClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_MOVIE = "movie";
    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w500/";
    private Callbacks mCallbacks ;

    //private DetailMovieFragment.Callbacks mCallbacks ;
    private List<Trailer> listTrailer;
    private MovieRestAPI apiService;
    /**
     * The dummy content this fragment is presenting.
     */
    private Movie movie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private ImageView ivImageMovie;
    private TextView tvPopularityMovie;
    private TextView tvSummaryMovie;
    private TextView tvVotosMovie;
    private TextView tvRatedMovie;
    private TextView tvYearMovie;
    RecyclerView rvComplexDetail;
    private List<Object> listGeneric;
    private MainMovieRecyclerViewAdapter mainMovieRecyclerViewAdapter;

    public DetailMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = (Movie) getArguments().getParcelable(ARG_ITEM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        apiService = ApiClient.getClient().create(MovieRestAPI.class);
        /*ivImageMovie=(ImageView)rootView.findViewById(R.id.ivDetailMovie);
        tvPopularityMovie=(TextView) rootView.findViewById(R.id.tvPopularityMovie);
        tvSummaryMovie=(TextView)rootView.findViewById(R.id.tvSummaryMovie);
        tvRatedMovie=(TextView) rootView.findViewById(R.id.tvRatedMovie);
        tvVotosMovie=(TextView)rootView.findViewById(R.id.tvVotosMovie);
        tvYearMovie=(TextView)rootView.findViewById(R.id.tvYearMovie);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivImageMovie.setTransitionName("profile");
        }
        Glide.with(this).load(BASE_IMAGE+movie.getUrlMovie()).placeholder(R.drawable.placeholder).crossFade().into( ivImageMovie);

        tvPopularityMovie.setText("Popularity: "+movie.getPopularity());
        tvSummaryMovie.setText("Summary: "+movie.getDescriptionMovie());
        tvYearMovie.setText("Release date: "+movie.getYearMovie());
        tvVotosMovie.setText("Votes: "+movie.getVoteCount());
        tvRatedMovie.setText("Rated: "+movie.getVoteAverage());
        */
        listGeneric=new ArrayList<Object>();
        listGeneric.add(movie);

        rvComplexDetail=(RecyclerView) rootView.findViewById(R.id.rvComplexDetail);
        rvComplexDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvComplexDetail.setHasFixedSize(true);
        mainMovieRecyclerViewAdapter=new MainMovieRecyclerViewAdapter(listGeneric,getActivity(),mCallbacks);
        rvComplexDetail.setAdapter(mainMovieRecyclerViewAdapter);

        Call<ListTrailer> call;
        if (NetworkUtil.isOnline(getContext())) {
            //swipeContainer.setRefreshing(true);
            call = apiService.loadTrailersMovie(movie.getId()+"",ApiClient.TAG_API);
            call.enqueue(DetailMovieFragment.this);

        }
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (DetailMovieFragment.Callbacks) context;
    }
    @Override
    public void onTrailerClick(int position) {

    }

    @Override
    public void onResponse(Call<ListTrailer> call, Response<ListTrailer> response) {
        listGeneric.addAll(response.body().trailerList);
        //swipeContainer.setRefreshing(false);
        //mrvAdapter = new MovieRecyclerViewAdapter(getContext(),mCallbacks, listMovies);
        //rvMovies.setItemAnimator(new DefaultItemAnimator());
        //rvMovies.setAdapter(mrvAdapter);
        mainMovieRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<ListTrailer> call, Throwable t) {
        Log.i("error","error");
    }
    public interface Callbacks {
        public void onItemSelected(ArrayList<Object> list,int position,View view);
        public void onItemCheckFavorite(boolean isFavorite);
    }
    /*try {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }catch(ActivityNotFoundException e) {

        // youtube is not installed.Will be opened in other available apps

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
        startActivity(i);
    }*/
}