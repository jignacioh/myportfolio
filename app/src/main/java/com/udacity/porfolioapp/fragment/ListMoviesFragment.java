package com.udacity.porfolioapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.OnMovieClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;
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
public class ListMoviesFragment extends Fragment implements Callback<ListMovie>,OnMovieClickListener {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listmovies_list, container, false);

        Context context = view.getContext();
        rvMovies= (RecyclerView) view.findViewById(R.id.rvMovies);
        if (mColumnCount <= 1) {
            rvMovies.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rvMovies.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        retrofit = new Retrofit.Builder()
                    .baseUrl("https://waspiest-assignment.000webhostapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        listMovies=new ArrayList<>();

        //lLayout = new GridLayoutManager(context, 2);
        swipeContainer=(SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        rvMovies.setHasFixedSize(true);
        // rvMovies.setLayoutManager(lLayout);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    rvMovies.setVisibility(View.GONE);
                    MovieRestAPI stackOverflowAPI = retrofit.create(MovieRestAPI.class);
                    Call<ListMovie> call = stackOverflowAPI.loadMovies();
                    call.enqueue(ListMoviesFragment.this);
                }
            });
        swipeContainer.setRefreshing(true);
        MovieRestAPI stackOverflowAPI = retrofit.create(MovieRestAPI.class);
        Call<ListMovie> call = stackOverflowAPI.loadMovies();
        call.enqueue(ListMoviesFragment.this);

        /*
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.setter);
        button.setSize(FloatingActionButton.SIZE_MINI);
        button.setColorNormalResId(R.color.pink);
        button.setColorPressedResId(R.color.pink_pressed);
        button.setIcon(R.drawable.ic_fab_star);
        button.setStrokeVisible(false);

        final View actionB = findViewById(R.id.action_b);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);

        final FloatingActionButton removeAction = (FloatingActionButton) findViewById(R.id.button_remove);
        removeAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
            }
        });

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));
        ((FloatingActionButton) findViewById(R.id.setter_drawable)).setIconDrawable(drawable);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
            }
        });

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        findViewById(R.id.button_gone).setVisibility(View.GONE);

        final FloatingActionButton actionEnable = (FloatingActionButton) findViewById(R.id.action_enable);
        actionEnable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
            }
        });

        FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Added once");
        rightLabels.addButton(addedOnce);

        FloatingActionButton addedTwice = new FloatingActionButton(this);
        addedTwice.setTitle("Added twice");
        rightLabels.addButton(addedTwice);
        rightLabels.removeButton(addedTwice);
        rightLabels.addButton(addedTwice);

        */
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
