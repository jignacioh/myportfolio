package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.Movie;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.ivDetailMovie)
    ImageView ivImageMovie;
    @BindView(R.id.tvDurationMovie)
    TextView tvDurationMovie;
    @BindView(R.id.tvSummaryMovie)
    TextView tvSummaryMovie;
    @BindView(R.id.tvVotosMovie)
    TextView tvVotosMovie;
    @BindView(R.id.tvRatedMovie)
    TextView tvRatedMovie;
    @BindView(R.id.tvYearMovie)
    TextView tvYearMovie;
    @BindView(R.id.showRatingBar)
    RatingBar rbRated;

    public DetailMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = (Movie) getArguments().getSerializable(ARG_ITEM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, rootView);
        ivImageMovie=(ImageView)rootView.findViewById(R.id.ivDetailMovie);
        tvDurationMovie=(TextView) rootView.findViewById(R.id.tvDurationMovie);
        tvSummaryMovie=(TextView)rootView.findViewById(R.id.tvSummaryMovie);
        tvRatedMovie=(TextView) rootView.findViewById(R.id.tvRatedMovie);
        tvVotosMovie=(TextView)rootView.findViewById(R.id.tvVotosMovie);
        tvYearMovie=(TextView)rootView.findViewById(R.id.tvYearMovie);

        rbRated=(RatingBar)rootView.findViewById(R.id.showRatingBar);
        Glide.with(this).load(BASE_IMAGE+movie.getImageMovie()).placeholder(R.drawable.placeholder).crossFade().into( ivImageMovie);

        tvDurationMovie.setText("Duration: "+movie.getDurationMovie());
        tvSummaryMovie.setText("Summary: "+movie.getDescriptionMovie());
        tvYearMovie.setText("Release date: "+movie.getYearMovie());
        tvVotosMovie.setText("Votes: "+movie.getVoteCount());
        tvRatedMovie.setText("Rated: "+movie.getVoteAverage());

        rbRated.setStepSize(5.0f);
        return rootView;
    }
}