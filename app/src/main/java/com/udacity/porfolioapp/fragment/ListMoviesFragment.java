package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.udacity.porfolioapp.OnMovieClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.service.ApiClient;
import com.udacity.porfolioapp.service.MovieRestAPI;
import com.udacity.porfolioapp.ui.adapter.MovieRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListMoviesFragment extends Fragment implements Callback<ListMovie>,OnMovieClickListener,View.OnClickListener {

    // TODO: Customize parameter argument names
    public static final String ARG_COLUMN_COUNT = "column-count";
    private GridLayoutManager lLayout;
    private RecyclerView rvMovies;
    private SwipeRefreshLayout swipeContainer;
    Retrofit retrofit;
    private Callbacks mCallbacks ;
    private List<Movie> listMovies;
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private MovieRecyclerViewAdapter mrvAdapter;
    private final static String API_KEY = "b3420c7e4ccc91fd03c3cd0ff60d9a92";

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListMoviesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListMoviesFragment newInstance(int columnCount) {
        ListMoviesFragment fragment = new ListMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }else{
            mColumnCount=2;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmovies_list, container, false);

        Context context = view.getContext();

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton)view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)view.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


        rvMovies= (RecyclerView) view.findViewById(R.id.rvMovies);
        if (mColumnCount <= 1) {
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rvMovies.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }



        listMovies=new ArrayList<>();

        //lLayout = new GridLayoutManager(context, 2);
        swipeContainer=(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        rvMovies.setHasFixedSize(true);
        // rvMovies.setLayoutManager(lLayout);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    rvMovies.setVisibility(View.GONE);
                    MovieRestAPI apiService =
                            ApiClient.getClient().create(MovieRestAPI.class);

                    Call<ListMovie> call = apiService.loadMovies();
                    call.enqueue(ListMoviesFragment.this);
                }
            });
        swipeContainer.setRefreshing(true);



        MovieRestAPI apiService =
                ApiClient.getClient().create(MovieRestAPI.class);

        Call<ListMovie> call = apiService.loadAllMovies(API_KEY);
        call.enqueue(ListMoviesFragment.this);



        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Restore the previously serialized activated item position.

        /*if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }*/
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (!(context instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
        rvMovies.setVisibility(View.VISIBLE);
        listMovies.clear();
        listMovies.addAll(response.body().movieList);
        swipeContainer.setRefreshing(false);
        mrvAdapter = new MovieRecyclerViewAdapter(getContext(),mCallbacks, listMovies);
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setAdapter(mrvAdapter);
    }

    @Override
    public void onFailure(Call<ListMovie> call, Throwable t) {
        rvMovies.setVisibility(View.GONE);
    }
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically give items the 'activated' state when touched.
    }

    public void changeNumberofRow(int mColumnCount) {
        this.mColumnCount=mColumnCount;
        mrvAdapter.notifyDataSetChanged();

    }

    @Override
    public void onMovieClick(int position) {
        mListener.onFragmentInteraction(Uri.parse(ListMoviesFragment.class.getSimpleName()),listMovies.get(position));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                break;
            case R.id.fab2:

                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri, Object object);
    }
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(ArrayList<Movie> list,int position);
    }
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(ArrayList<Movie> list,int position) {

        }
    };
}
