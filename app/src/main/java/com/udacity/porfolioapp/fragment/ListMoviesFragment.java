package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.porfolioapp.util.NetworkUtil;
import com.udacity.porfolioapp.listener.OnMovieClickListener;
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

/**
 * Created by Juan PC
 */
public class ListMoviesFragment extends BaseFragment implements Callback<ListMovie>,OnMovieClickListener,View.OnClickListener {

    // TODO: Customize parameter argument names
    public static final String ARG_COLUMN_COUNT = "column-count";
    public static final int TYPE_POPULAR_MOVIES= R.id.fabPopular;
    private final static String API_KEY = "b3420c7e4ccc91fd03c3cd0ff60d9a92";


    private RecyclerView rvMovies;
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton fab,fabPopular,fabRated;
    private TextView tvType;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private LinearLayout llMessage;
    private Button btReload;

    private Callbacks mCallbacks ;
    private List<Movie> listMovies;
    private MovieRestAPI apiService;
    private OnListFragmentInteractionListener mListener;
    private MovieRecyclerViewAdapter mrvAdapter;

    private int mColumnCount = 2;

    private int idTypeSort;
    private Boolean isFabOpen = false;



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
            fabPopular.startAnimation(fab_close);
            fabRated.startAnimation(fab_close);
            fabPopular.setClickable(false);
            fabRated.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fabPopular.startAnimation(fab_open);
            fabRated.startAnimation(fab_open);
            fabPopular.setClickable(true);
            fabRated.setClickable(true);
            isFabOpen = true;

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmovies_list, container, false);
        apiService = ApiClient.getClient().create(MovieRestAPI.class);
        idTypeSort=TYPE_POPULAR_MOVIES;
        Context context = view.getContext();

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fabPopular = (FloatingActionButton)view.findViewById(R.id.fabPopular);
        fabRated = (FloatingActionButton)view.findViewById(R.id.fabRated);
        tvType=(TextView)view.findViewById(R.id.tvType);
        llMessage=(LinearLayout) view.findViewById(R.id.llMessage);
        btReload=(Button) view.findViewById(R.id.btRetry);

        fab_open = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(context.getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fabPopular.setOnClickListener(this);
        fabRated.setOnClickListener(this);
        btReload.setOnClickListener(this);

        rvMovies= (RecyclerView) view.findViewById(R.id.rvMovies);
        if (mColumnCount <= 1) {
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rvMovies.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }


        listMovies=new ArrayList<>();
        swipeContainer=(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        rvMovies.setHasFixedSize(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sortByPreference(idTypeSort);
                }
            });
        swipeContainer.setRefreshing(true);


        sortByPreference(TYPE_POPULAR_MOVIES);

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
    }

    @Override
    public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
        tvType.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.VISIBLE);
        listMovies.clear();
        listMovies.addAll(response.body().movieList);
        swipeContainer.setRefreshing(false);
        mrvAdapter = new MovieRecyclerViewAdapter(getContext(),mCallbacks, listMovies);
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setAdapter(mrvAdapter);

        changeTypeSortText();
    }

    @Override
    public void onFailure(Call<ListMovie> call, Throwable t) {
        swipeContainer.setRefreshing(false);
        tvType.setVisibility(View.GONE);
        rvMovies.setVisibility(View.GONE);
        llMessage.setVisibility(View.VISIBLE);
    }
    private void changeTypeSortText() {
        if (idTypeSort==R.id.fabPopular){
            tvType.setText(getResources().getString(R.string.lb_popular));
        }else if (idTypeSort==R.id.fabRated){
            tvType.setText(getResources().getString(R.string.lb_rated));
        }
    }
    @Override
    public void onMovieClick(int position) {
        mListener.onFragmentInteraction(Uri.parse(ListMoviesFragment.class.getSimpleName()),listMovies.get(position));
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btRetry){
            sortByPreference(idTypeSort);
        }else
        if (view.getId()==R.id.fab){
            animateFAB();
        }else {
            idTypeSort = view.getId();
            sortByPreference(idTypeSort);
        }
    }

    private void sortByPreference(int idTypeSort) {
        Call<ListMovie> call;
        if (NetworkUtil.isOnline(getContext())) {
            swipeContainer.setRefreshing(true);
            //tvType.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.VISIBLE);
            llMessage.setVisibility(View.GONE);
            switch (idTypeSort) {
                case R.id.fabPopular:
                    rvMovies.setVisibility(View.GONE);
                    call = apiService.loadHighRatedMovies(API_KEY);
                    call.enqueue(this);
                    break;
                case R.id.fabRated:
                    rvMovies.setVisibility(View.GONE);
                    call = apiService.loadMostPopularMovies(API_KEY);
                    call.enqueue(this);
                    break;
            }
        }else {
            swipeContainer.setRefreshing(false);
            tvType.setVisibility(View.GONE);
            rvMovies.setVisibility(View.GONE);
            llMessage.setVisibility(View.VISIBLE);
        }
    }


    public interface OnListFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri, Object object);
    }
    public interface Callbacks {
        public void onItemSelected(ArrayList<Movie> list,int position,View view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
