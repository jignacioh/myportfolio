package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.service.ApiClient;
import com.udacity.porfolioapp.service.MovieRestAPI;
import com.udacity.porfolioapp.ui.adapter.MovieRecyclerViewAdapter;
import com.udacity.porfolioapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan PC
 */
public class ListMoviesFragment extends BaseFragment implements Callback<ListMovie>,View.OnClickListener {

    // TODO: Customize parameter argument names
    public static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_TYPE_FILTER = "type";
    public static final String ARG_LIST_MOVIES = "movies";
    public static final int TYPE_POPULAR_MOVIES= R.id.fabPopular;
    private final static String API_KEY = "b3420c7e4ccc91fd03c3cd0ff60d9a92";

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fabPopular)
    FloatingActionButton fabPopular;
    @BindView(R.id.fabRated)
    FloatingActionButton fabRated;
    @BindView(R.id.fabFavorite)
    FloatingActionButton fabFavorite;

    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.llMessage)
    LinearLayout llMessage;
    @BindView(R.id.btRetry)
    Button btReload;

    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private Callbacks mCallbacks ;
    private List<Movie> listMovies;
    private MovieRestAPI apiService;
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
            fabFavorite.startAnimation(fab_close);
            fabPopular.setClickable(false);
            fabRated.setClickable(false);
            fabFavorite.setClickable(false);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fabPopular.startAnimation(fab_open);
            fabRated.startAnimation(fab_open);
            fabFavorite.startAnimation(fab_open);
            fabPopular.setClickable(true);
            fabRated.setClickable(true);
            fabFavorite.setClickable(true);
            isFabOpen = true;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmovies_list, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        if (savedInstanceState==null){
            listMovies=new ArrayList<>();
            idTypeSort=TYPE_POPULAR_MOVIES;
        }else {
            listMovies=savedInstanceState.getParcelableArrayList(ARG_LIST_MOVIES);
            idTypeSort=savedInstanceState.getInt(ARG_TYPE_FILTER);
        }
        apiService = ApiClient.getClient().create(MovieRestAPI.class);

        fab_open = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fabPopular.setOnClickListener(this);
        fabRated.setOnClickListener(this);
        fabFavorite.setOnClickListener(this);
        btReload.setOnClickListener(this);

        if (getBoolean(R.bool.isTablet)){
            rvMovies.setLayoutManager(new GridLayoutManager(view.getContext(), getNumberRowsTabletOrientation()));
        }else {
            rvMovies.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }


        swipeContainer=(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        rvMovies.setHasFixedSize(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    sortByPreference(idTypeSort);
                }
            });
        swipeContainer.setRefreshing(true);

        if (listMovies.size()==0) {
            sortByPreference(TYPE_POPULAR_MOVIES);
        }else {
            tvType.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.VISIBLE);
            swipeContainer.setRefreshing(false);
            mrvAdapter = new MovieRecyclerViewAdapter(getContext(),mCallbacks, listMovies);
            rvMovies.setItemAnimator(new DefaultItemAnimator());
            rvMovies.setAdapter(mrvAdapter);
            changeTypeSortText();
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        tvMessage.setText(getString(R.string.msg_no_internet));
        btReload.setVisibility(View.VISIBLE);

    }
    private void changeTypeSortText() {
        if (idTypeSort==R.id.fabPopular){
            tvType.setText(getString(R.string.lb_popular));
        }else if (idTypeSort==R.id.fabRated){
            tvType.setText(getString(R.string.lb_rated));
        }else if (idTypeSort==R.id.fabFavorite){
            tvType.setText(getString(R.string.lb_favorite));
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btRetry){
            sortByPreference(idTypeSort);
        }else
        if (view.getId()==R.id.fab){
            animateFAB();
        }else if(view.getId()==R.id.fabFavorite){
            loadFavorites();
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
            tvMessage.setText(getString(R.string.msg_no_internet));
            btReload.setVisibility(View.VISIBLE);
        }
    }

    private void loadFavorites() {
        listMovies.clear();
        listMovies=getFavoritesMovies();
        if (listMovies!=null && listMovies.size()!=0){
            tvType.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.VISIBLE);
            swipeContainer.setRefreshing(false);
            mrvAdapter = new MovieRecyclerViewAdapter(getContext(),mCallbacks, listMovies);
            rvMovies.setItemAnimator(new DefaultItemAnimator());
            rvMovies.setAdapter(mrvAdapter);
            tvType.setText(getString(R.string.lb_favorite));
        }else{
            swipeContainer.setRefreshing(false);
            tvType.setVisibility(View.GONE);
            rvMovies.setVisibility(View.GONE);
            llMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(getString(R.string.lb_popular));
            btReload.setVisibility(View.GONE);
        }
    }

    public interface Callbacks {
        void onItemSelected(ArrayList<Movie> list,int position,View view);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_LIST_MOVIES, (ArrayList<? extends Parcelable>) listMovies);
        outState.putInt(ARG_TYPE_FILTER,idTypeSort);
        super.onSaveInstanceState(outState);
    }
}
