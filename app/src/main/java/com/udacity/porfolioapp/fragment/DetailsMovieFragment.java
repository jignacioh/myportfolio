package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.ListTrailer;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.model.Trailer;
import com.udacity.porfolioapp.service.ApiClient;
import com.udacity.porfolioapp.service.MovieRestAPI;
import com.udacity.porfolioapp.ui.adapter.MainMovieRecyclerViewAdapter;
import com.udacity.porfolioapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class DetailsMovieFragment extends BaseFragment implements Callback<ListTrailer> {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        public static final String ARG_ITEM_ID = "item_id";
        public static final String ARG_ITEM_MOVIE = "movie";
        private Callbacks mCallbacks ;
        private MovieRestAPI apiService;
        /**
         * The dummy content this fragment is presenting.
         */
        private Movie movie;
        private List<Trailer> lTrailerList;
        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */

        private RecyclerView rvComplexDetail;
        private ProgressBar pbLoadTrailer;
        private List<Object> listGeneric;
        private MainMovieRecyclerViewAdapter mainMovieRecyclerViewAdapter;

    public static Fragment newInstance(Movie movie) {
        Fragment fragment=new DetailsMovieFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ARG_ITEM_MOVIE,movie);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lTrailerList=new ArrayList<>();
        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = getArguments().getParcelable(ARG_ITEM_MOVIE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie",movie);
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) lTrailerList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        View rootView = inflater.inflate(R.layout.fragment_movie_body, container, false);
        setRetainInstance(true);
        apiService = ApiClient.getClient().create(MovieRestAPI.class);

        pbLoadTrailer=(ProgressBar)rootView.findViewById(R.id.pbLoadTrailer);
        rvComplexDetail=(RecyclerView) rootView.findViewById(R.id.rvComplexDetail);
        rvComplexDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvComplexDetail.setHasFixedSize(true);
        mainMovieRecyclerViewAdapter=new MainMovieRecyclerViewAdapter(listGeneric,getActivity(),mCallbacks,this);
        rvComplexDetail.setAdapter(mainMovieRecyclerViewAdapter);

        Call<ListTrailer> call;
        listGeneric=new ArrayList<>();


        if (savedInstanceState==null){
            Log.i("OnSavedInstance","NULL");
            listGeneric.add(movie);
            if (NetworkUtil.isOnline(getContext())) {
                //swipeContainer.setRefreshing(true);
                pbLoadTrailer.setVisibility(View.VISIBLE);
                call = apiService.loadTrailersMovie(movie.getId()+"",ApiClient.TAG_API);
                call.enqueue(DetailsMovieFragment.this);

            }
        }else {
            Log.i("OnSavedInstance","NOT NULL");
            movie=savedInstanceState.getParcelable("movie");
            lTrailerList=savedInstanceState.getParcelableArrayList("list");
            listGeneric.add(movie);
            listGeneric.addAll(lTrailerList);
        }
        mainMovieRecyclerViewAdapter=new MainMovieRecyclerViewAdapter(listGeneric,getActivity(),mCallbacks,this);
        rvComplexDetail.setAdapter(mainMovieRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onResponse(Call<ListTrailer> call, Response<ListTrailer> response) {
        pbLoadTrailer.setVisibility(View.GONE);
        lTrailerList.clear();
        lTrailerList=response.body().trailerList;
        listGeneric.addAll(lTrailerList);
        mainMovieRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (DetailsMovieFragment.Callbacks) context;
    }

    @Override
    public void onFailure(Call<ListTrailer> call, Throwable t) {
        pbLoadTrailer.setVisibility(View.GONE);
    }

    public interface Callbacks {
        void onItemSelected(ArrayList<Object> list,int position,View view);
        void onItemCheckFavorite(boolean isFavorite);
    }

}
