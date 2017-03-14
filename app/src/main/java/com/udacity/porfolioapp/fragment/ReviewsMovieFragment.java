package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
public class ReviewsMovieFragment extends BaseFragment implements Callback<ListReview>,View.OnClickListener{

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
    private ReviewsMovieFragment.Callbacks mCallbacks ;



    /**
     * The dummy content this fragment is presenting.
     */
    private Movie movie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private RecyclerView rvReviews;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    private LinearLayout llMessageNoInternet;
    private LinearLayout llMessageNoReview;
    private ProgressBar pbLoadReview;
    private Button btRetry;

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
        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = getArguments().getParcelable(ARG_ITEM_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_review, container, false);
        setRetainInstance(true);
        apiService= ApiClient.getClient().create(MovieRestAPI.class);
        llMessageNoInternet=(LinearLayout) rootView.findViewById(R.id.llMessageNoConection);
        llMessageNoReview=(LinearLayout) rootView.findViewById(R.id.llMessageNoReviews);
        btRetry=(Button)rootView.findViewById(R.id.btRetry);
        pbLoadReview=(ProgressBar)rootView.findViewById(R.id.pbLoadReview);
        rvReviews=(RecyclerView) rootView.findViewById(R.id.rvReviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvReviews.setHasFixedSize(true);
        reviewRecyclerViewAdapter=new ReviewRecyclerViewAdapter(listReviews,mCallbacks,getActivity());
        rvReviews.setAdapter(reviewRecyclerViewAdapter);

        fillReviewsList();

        btRetry.setOnClickListener(this);

        return rootView;
    }

    private void fillReviewsList() {
        Call<ListReview> call;
        if (NetworkUtil.isOnline(getContext())) {
            pbLoadReview.setVisibility(View.VISIBLE);
            call = apiService.loadReviewsMovie(movie.getId()+"",ApiClient.TAG_API);
            call.enqueue(ReviewsMovieFragment.this);

        }else {
            pbLoadReview.setVisibility(View.GONE);
            rvReviews.setVisibility(View.GONE);
            llMessageNoReview.setVisibility(View.GONE);
            llMessageNoInternet.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResponse(Call<ListReview> call, Response<ListReview> response) {
        listReviews.addAll(response.body().reviewList);
        pbLoadReview.setVisibility(View.GONE);
        if (listReviews!=null && listReviews.size()!=0){
            llMessageNoInternet.setVisibility(View.GONE);
            llMessageNoReview.setVisibility(View.GONE);
            rvReviews.setVisibility(View.VISIBLE);
            reviewRecyclerViewAdapter.notifyDataSetChanged();
        }else{
            llMessageNoReview.setVisibility(View.VISIBLE);
            rvReviews.setVisibility(View.GONE);
            llMessageNoInternet.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<ListReview> call, Throwable t) {
        pbLoadReview.setVisibility(View.GONE);
        rvReviews.setVisibility(View.GONE);
        llMessageNoReview.setVisibility(View.GONE);
        llMessageNoInternet.setVisibility(View.VISIBLE);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ReviewsMovieFragment.Callbacks) context;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btRetry){
            llMessageNoReview.setVisibility(View.GONE);
            rvReviews.setVisibility(View.GONE);
            llMessageNoInternet.setVisibility(View.GONE);
            fillReviewsList();
        }
    }

    public interface Callbacks {
        public void onReviewSelected(ArrayList<Object> list,int position,View view);
    }
}
