package com.udacity.porfolioapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.ListReview;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.service.ApiClient;
import com.udacity.porfolioapp.service.MovieRestAPI;
import com.udacity.porfolioapp.ui.adapter.ReviewRecyclerViewAdapter;
import com.udacity.porfolioapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ReviewsMovieFragment extends BaseFragment implements Callback<ListReview>{

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_MOVIE = "movie";
    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w500/";

    //private DetailMovieFragment.Callbacks mCallbacks ;
    private List<Trailer> listTrailer;
    private List<Review> listReviews;
    private MovieRestAPI apiService;
    /**
     * The dummy content this fragment is presenting.
     */
    private Movie movie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private RecyclerView rvReviews;
    private List<Object> listGeneric;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;


    public static Fragment newInstance(Movie movie) {
        Fragment fragment=new ReviewsMovieFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ARG_ITEM_MOVIE,movie);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listReviews=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        apiService= ApiClient.getClient().create(MovieRestAPI.class);

        rvReviews=(RecyclerView) rootView.findViewById(R.id.rvReviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviews.setHasFixedSize(true);
        reviewRecyclerViewAdapter=new ReviewRecyclerViewAdapter(listReviews);
        rvReviews.setAdapter(reviewRecyclerViewAdapter);

        Call<ListReview> call;
        if (NetworkUtil.isOnline(getContext())) {
            //swipeContainer.setRefreshing(true);
            call = apiService.loadReviewsMovie(movie.getId()+"",ApiClient.TAG_API);
            call.enqueue(ReviewsMovieFragment.this);

        }


        return rootView;
    }


    @Override
    public void onResponse(Call<ListReview> call, Response<ListReview> response) {

    }

    @Override
    public void onFailure(Call<ListReview> call, Throwable t) {

    }
}
