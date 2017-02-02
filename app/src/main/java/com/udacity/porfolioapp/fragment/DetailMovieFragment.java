package com.udacity.porfolioapp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.Movie;
/**
 * Created by Juan PC
 */
public class DetailMovieFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_MOVIE = "movie";
    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w500/";

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
        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ivImageMovie=(ImageView)rootView.findViewById(R.id.ivDetailMovie);
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

        return rootView;
    }
}