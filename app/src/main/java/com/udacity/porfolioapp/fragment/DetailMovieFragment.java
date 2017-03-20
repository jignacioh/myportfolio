package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.service.MovieRestAPI;

import java.util.List;

/**
 * Created by Juan PC
 */
public class DetailMovieFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_MOVIE = "movie";
    public static final String BASE_IMAGE="http://image.tmdb.org/t/p/w500/";
    public static final String ARG_CHECK_REVIEW = "checkreview";
    public static final String ARG_CHECK_DETAIL = "checkdetail";

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

    private RadioButton rdbDetail,rdbReview;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean checkDetail=true,checkReview=false;

    public DetailMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = getArguments().getParcelable(ARG_ITEM_MOVIE);
        }
        if (savedInstanceState!=null){
            checkDetail=savedInstanceState.getBoolean(ARG_CHECK_DETAIL);
            checkReview=savedInstanceState.getBoolean(ARG_CHECK_REVIEW);
            movie=savedInstanceState.getParcelable(ARG_ITEM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setRetainInstance(true);
        rdbDetail=(RadioButton)rootView.findViewById(R.id.rdbDetail);
        rdbReview=(RadioButton)rootView.findViewById(R.id.rdbReview);


        rdbDetail.setOnCheckedChangeListener(this);
        rdbReview.setOnCheckedChangeListener(this);

        if (checkDetail) {
            rdbDetail.setChecked(checkDetail);
        }else {
            rdbReview.setChecked(checkReview);
        }

        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        Fragment fragment=null;
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        if (rdbDetail.isChecked()){
            checkDetail=true;
            checkReview=false;
            fragment=DetailsMovieFragment.newInstance(movie);
        }else if(rdbReview.isChecked()){
            checkDetail=false;
            checkReview=true;
            fragment=ReviewsMovieFragment.newInstance(movie);
        }
        fragmentTransaction.replace(R.id.flContentBody,fragment);

        fragmentTransaction.commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ARG_CHECK_REVIEW,checkDetail);
        outState.putBoolean(ARG_CHECK_DETAIL,checkReview);
        outState.putParcelable(ARG_ITEM_MOVIE,movie);
        super.onSaveInstanceState(outState);
    }
}