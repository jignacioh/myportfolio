package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.listener.OnTrailerClickListener;
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
public class DetailsMovieFragment extends BaseFragment implements Callback<ListTrailer>,OnTrailerClickListener {
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

        RecyclerView rvComplexDetail;
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

        if (getArguments().containsKey(ARG_ITEM_MOVIE)) {
            movie = getArguments().getParcelable(ARG_ITEM_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        View rootView = inflater.inflate(R.layout.fragment_movie_body, container, false);
        apiService = ApiClient.getClient().create(MovieRestAPI.class);

        listGeneric=new ArrayList<>();
        listGeneric.add(movie);

        rvComplexDetail=(RecyclerView) rootView.findViewById(R.id.rvComplexDetail);
        rvComplexDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvComplexDetail.setHasFixedSize(true);
        mainMovieRecyclerViewAdapter=new MainMovieRecyclerViewAdapter(listGeneric,getActivity(),mCallbacks,this);
        rvComplexDetail.setAdapter(mainMovieRecyclerViewAdapter);

        Call<ListTrailer> call;
        if (NetworkUtil.isOnline(getContext())) {
            //swipeContainer.setRefreshing(true);
            call = apiService.loadTrailersMovie(movie.getId()+"",ApiClient.TAG_API);
            call.enqueue(DetailsMovieFragment.this);

        }
        return rootView;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (DetailsMovieFragment.Callbacks) context;
    }

    @Override
    public void onFailure(Call<ListTrailer> call, Throwable t) {

    }

    @Override
    public void onTrailerClick(int position) {

    }

    public interface Callbacks {
        public void onItemSelected(ArrayList<Object> list,int position,View view);
        public void onItemCheckFavorite(boolean isFavorite);
    }

}
